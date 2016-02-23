package ww.com.http;

public interface IHttpRequest {
    void setHttpRequestComplateListener(
            IHttpRequestCompleteListener update);

    void requestCancel();

    void start();

    void refresh();

    void setHttpRequestType(RequestTypeEnum type);

    boolean isCancel();

    enum RequestTypeEnum {
        POST, GET, PROTOBUF;
    }
}
