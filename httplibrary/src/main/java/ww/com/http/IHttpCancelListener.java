package ww.com.http;

/**
 * 请求状态
 */
public interface IHttpCancelListener {

	boolean isCancel();
	
	boolean isPause();
	
}
