package ww.com.http;

/**
 * 请求日志
 */
public interface IHttpLog {
    /** 写入日志*/
    void writeTo(String log);

    /** 清除日志 */
    void clear();
}
