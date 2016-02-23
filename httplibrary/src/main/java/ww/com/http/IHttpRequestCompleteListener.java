package ww.com.http;

public interface IHttpRequestCompleteListener {
    void onStart(IHttpRequest request);

    void onSuccess(IHttpRequest request, String url, byte[] obj);

    void onFail(IHttpRequest request, int errorCode, String url);

    // 请求结束
    void onEnd(IHttpRequest request);

    IHttpCancelListener getCancelListener();

    IHttpLog getHttpLog();
}
