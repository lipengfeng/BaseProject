package ww.com.http.callback;

import ww.com.http.IHttpCancelListener;
import ww.com.http.IHttpLog;
import ww.com.http.IHttpRequest;
import ww.com.http.IHttpRequestCompleteListener;

/**
 * Created by lenovo on 2016/1/21.
 */
public abstract class BaseSampleCallback implements IHttpRequestCompleteListener {
    private IHttpCancelListener mHttpCancelListener;
    private IHttpLog mHttpLog;

    public BaseSampleCallback() {
    }

    public BaseSampleCallback(IHttpCancelListener mHttpCancelListener, IHttpLog mHttpLog) {
        this();
        this.mHttpCancelListener = mHttpCancelListener;
        this.mHttpLog = mHttpLog;
    }

    @Override
    public void onStart(IHttpRequest request) {

    }

    @Override
    public void onEnd(IHttpRequest request) {

    }

    @Override
    public IHttpCancelListener getCancelListener() {
        return mHttpCancelListener;
    }

    @Override
    public IHttpLog getHttpLog() {
        return mHttpLog;
    }
}
