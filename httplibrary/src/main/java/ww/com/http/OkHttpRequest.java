package ww.com.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okio.BufferedSink;

public class OkHttpRequest implements IHttpRequest, Callback {

    private static final int CONNECT_TIMEOUT = 4; // 秒
    private static final int READ_TIMEOUT = 10; // 秒

    public static final int HTTPREQUEST_COMPLETE = 0;
    public static final int HTTPREQUEST_TIMEOUT = 1;
    public static final int HTTPREQUEST_FAILURE = 2;
    public static final int HTTPREQUEST_CANCEL = 103;
    public static final int HTTPREQUEST_NETWORK_ERROR = 4;

    public static final int EXCEPTION_PARSER_TIMEOUT = 12;
    public static final int SHOW_DIALOG = 1;
    public static final int CLOSE_DIALOG = 2;

    private static OkHttpClient okHttpClient = new OkHttpClient();

    static {
        okHttpClient.setConnectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
    }

    private IHttpRequestCompleteListener completeListener;
    private AjaxParams params;
    private String mUrl;

    private Call mCall;

    protected RequestTypeEnum cRequestType = RequestTypeEnum.POST;

    private Request mRequest;
    private Handler mHandler;
    private IHttpLog mHttpLog;
    private IHttpCancelListener mHttpCancelListener;

    public OkHttpRequest(String url, AjaxParams params) {
        this.params = params;
        this.mUrl = url;
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (isRequestCancel()) {
                    return;
                }
                switch (msg.arg1) {
                    case 1:
                        switch (msg.arg2) {
                            case SHOW_DIALOG:
                                if (completeListener != null) {
                                    completeListener.onStart(OkHttpRequest.this);
                                }
                                break;
                            case CLOSE_DIALOG:
                                if (completeListener != null) {
                                    completeListener.onEnd(OkHttpRequest.this);
                                }
                                break;
                        }
                        break;

                    default:
                        int statusCode = HTTPREQUEST_COMPLETE;
                        switch (msg.what) {
                            case HTTPREQUEST_TIMEOUT:
                                statusCode = HTTPREQUEST_TIMEOUT;
                                break;
                            case HTTPREQUEST_CANCEL:
                                statusCode = HTTPREQUEST_CANCEL;
                                break;
                            case HTTPREQUEST_COMPLETE:
                                statusCode = HTTPREQUEST_COMPLETE;
                                break;
                            case HTTPREQUEST_FAILURE:
                                statusCode = HTTPREQUEST_FAILURE;
                                break;
                            case HTTPREQUEST_NETWORK_ERROR:
                                statusCode = HTTPREQUEST_NETWORK_ERROR;
                                break;
                            case EXCEPTION_PARSER_TIMEOUT:
                                statusCode = EXCEPTION_PARSER_TIMEOUT;
                                break;
                        }

                        if (completeListener != null) {
                            completeListener.onEnd(OkHttpRequest.this);
                            if (statusCode != HTTPREQUEST_COMPLETE) {
                                completeListener.onFail(OkHttpRequest.this, statusCode, mUrl);
                            } else {
                                completeListener.onSuccess(OkHttpRequest.this, mUrl, (byte[]) msg.obj);
                            }
                        }

                        break;
                }

            }
        };
    }

    @Override
    public void setHttpRequestComplateListener(
            IHttpRequestCompleteListener update) {
        completeListener = update;
        if (completeListener != null) {
            mHttpLog = completeListener.getHttpLog();
            mHttpCancelListener = completeListener.getCancelListener();
        }
    }

    public void start() {
        if (mHttpLog != null)
        mHttpLog.clear();
        mRequest = obtainRequest();
        start(mRequest);
    }

    public void refresh() {
        if (mRequest != null) {
            start(mRequest);
        }
    }

    private void start(Request request) {
        Message msg = mHandler.obtainMessage();
        msg.arg1 = 1;
        msg.arg2 = SHOW_DIALOG;
        mHandler.sendMessage(msg);

        mCall = okHttpClient.newCall(request);
        mCall.enqueue(this);
    }

    protected boolean isRequestCancel() {
        if (mHttpCancelListener != null) {
            return mHttpCancelListener.isCancel();
        }

        return false;
    }

    @Override
    public boolean isCancel() {
        if (mCall != null && mCall.isCanceled()) {
            return true;
        }

        if (isRequestCancel()) {
            return true;
        }
        return false;
    }

    @Override
    public void onFailure(Request arg0, IOException arg1) {

        System.out.println("onFailure >>>>>>>>>>> : " + arg1.toString());
        mHandler.sendEmptyMessage(HTTPREQUEST_FAILURE);
    }

    @Override
    public void onResponse(Response arg0) {
        byte[] result = null;
        if (arg0 != null && arg0.isSuccessful()) {
            try {
                result = arg0.body().bytes();
            } catch (IOException e) {
                onFailure(arg0.request(), e);
                return;
            }
        }
        if (result == null){
            mHandler.sendEmptyMessage(HTTPREQUEST_FAILURE);
            return;
        }
        mHandler.sendMessage(mHandler.obtainMessage(HTTPREQUEST_COMPLETE,
                result));

//        JSONObject json = null;
//        try {
//            // check json
//            json = new JSONObject(result);
//            // check success
//            mHandler.sendMessage(mHandler.obtainMessage(HTTPREQUEST_COMPLETE,
//                    json));
//        } catch (Exception e) {
//            e.printStackTrace();
//            mHandler.sendEmptyMessage(HTTPREQUEST_FAILURE);
//        }
    }

    @Override
    public void requestCancel() {
        if (mCall != null && !mCall.isCanceled()) {
            mCall.cancel();
            mCall = null;
        }
    }

    private Request obtainRequest() {
        if (mHttpLog != null) {
            mHttpLog.writeTo("server host: " + mUrl);
        }
        Request.Builder builder = new Request.Builder();
        Map<String, String> headers = params.getHeader();
        Set<String> names = headers.keySet();
        if (mHttpLog != null)
            mHttpLog.writeTo("Header:");
        for (String key : names) {
            String value = headers.get(key);
            builder.addHeader(key, value);
            if (mHttpLog != null)
                mHttpLog.writeTo(key + " : " + value);
        }
        List<AjaxParams.FileParams> fileParams = params.getFileParams();
        List<AjaxParams.BaseParams> baseParams = params.getBaseParams();
        switch (cRequestType) {
            case POST: {
                if (mHttpLog != null)
                    mHttpLog.writeTo("Post ===>>> Body: ");
                RequestBody body = null;
                StringBuffer buf = new StringBuffer();
                if (fileParams != null && !fileParams.isEmpty()) {
                    MultipartBuilder mb = new MultipartBuilder();
                    mb.type(MultipartBuilder.FORM);
                    if (mHttpLog != null)
                        mHttpLog.writeTo("Files: ");
                    for (AjaxParams.FileParams params : fileParams) {
                        String key = params.get_key();
                        String fileName = params.getFileName();

                        mb.addFormDataPart(
                                key,
                                fileName,
                                RequestBody.create(params.getMediaType(),
                                        params.getFile()));
                        if (mHttpLog != null)
                            mHttpLog.writeTo(key + " : " + fileName);
                    }

                    if (baseParams != null) {
                        if (mHttpLog != null)
                            mHttpLog.writeTo("params: ");
                        for (AjaxParams.BaseParams params : baseParams) {
                            String key = params.get_key();
                            String value = params.get_value();
                            mb.addFormDataPart(key, value);
                            if (mHttpLog != null)
                                mHttpLog.writeTo(key + " : " + value);
                        }
                    }

                    body = mb.build();
                } else {
                    FormEncodingBuilder fb = new FormEncodingBuilder();
                    if (baseParams != null) {
                        if (mHttpLog != null)
                            mHttpLog.writeTo("params: ");
                        for (AjaxParams.BaseParams params : baseParams) {
                            String key = params.get_key();
                            String value = params.get_value();
                            fb.add(key, value);
                            if (mHttpLog != null)
                                mHttpLog.writeTo(key + " : " + value);
                        }
                    }
                    body = fb.build();
                }
                builder.post(body);
            }

            break;

            case GET: {
                String p = getParameters(baseParams);
                if (!TextUtils.isEmpty(p)) {
                    if (!TextUtils.isEmpty(mUrl)) {
                        if (mUrl.contains("?")) {
                            mUrl += "&" + p;
                        } else {
                            mUrl += "?" + p;
                        }

                    }
                }
                if (mHttpLog != null)
                    mHttpLog.writeTo("GET ===>>>  " + mUrl);
            }
            case PROTOBUF:{
                if (mHttpLog != null)
                    mHttpLog.writeTo("PROTOBUF:");
                final byte[] bufs = params.getBufs();
                if (bufs != null){
                    RequestBody body = new RequestBody() {
                        @Override
                        public MediaType contentType() {
                            return MediaType.parse("content_proto");
                        }

                        @Override
                        public void writeTo(BufferedSink bufferedSink) throws IOException {
                            bufferedSink.write(bufs);
                        }
                    };
                    builder.post(body);
                }

            }

            break;

            default:
                break;
        }

        builder.url(mUrl);
        Request request = builder.build();
        return request;
    }

    public String getParameters(List<AjaxParams.BaseParams> params) {
        StringBuffer stringBuffer = new StringBuffer();
        if (params != null) {

            int size = params.size();
            try {
                for (int i = 0; i < size; i++) {
                    if (i == size - 1) {
                        stringBuffer.append(params.get(i).get_key());
                        stringBuffer.append("=");
                        stringBuffer.append(URLEncoder.encode(params.get(i)
                                .get_value(), "utf-8"));
                    } else {
                        stringBuffer.append(params.get(i).get_key());
                        stringBuffer.append("=");
                        stringBuffer.append(URLEncoder.encode(params.get(i)
                                .get_value(), "utf-8"));
                        stringBuffer.append("&");
                    }
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        return stringBuffer.toString();
    }

    @Override
    public void setHttpRequestType(RequestTypeEnum type) {
        cRequestType = type;
    }

}
