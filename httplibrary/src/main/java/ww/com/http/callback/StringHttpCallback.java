package ww.com.http.callback;

import java.nio.charset.Charset;

import ww.com.http.IHttpCancelListener;
import ww.com.http.IHttpLog;
import ww.com.http.IHttpRequest;

public abstract class StringHttpCallback extends BaseSampleCallback {

    public StringHttpCallback() {
    }

    public StringHttpCallback(IHttpCancelListener mHttpCancelListener, IHttpLog mHttpLog) {
        super(mHttpCancelListener, mHttpLog);
    }

    @Override
    public final void onSuccess(IHttpRequest request, String url, byte[] obj) {
        if (obj == null) {
            onSuccess(request, url, "");
        } else {
            onSuccess(request, url, new String(obj, Charset.forName("UTF-8")));
        }
    }

    public abstract void onSuccess(IHttpRequest request, String url, String result);
}
