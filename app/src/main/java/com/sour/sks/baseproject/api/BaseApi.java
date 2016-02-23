package com.sour.sks.baseproject.api;


import com.sour.sks.baseproject.config.NetConfig;

import ww.com.http.AjaxParams;
import ww.com.http.IHttpRequest;
import ww.com.http.OkHttpRequest;

/**
 * Created by ww on 2016/1/26.
 */
public class BaseApi {

    protected static String getUrl(String action) {
        return String.format("%1$s%2$s", NetConfig.BASE_URL, action);
    }

    protected static AjaxParams getParams() {
        AjaxParams params = new AjaxParams();
        return params;
    }

    protected static final IHttpRequest post(String url, AjaxParams params, HttpCallback callback) {
        IHttpRequest request = new OkHttpRequest(url, params);
        request.setHttpRequestComplateListener(callback);
        request.start();
        return request;
    }

    protected static final IHttpRequest get(String url, AjaxParams params, HttpCallback callback) {
        IHttpRequest request = new OkHttpRequest(url, params);
        request.setHttpRequestType(IHttpRequest.RequestTypeEnum.GET);
        request.setHttpRequestComplateListener(callback);
        request.start();
        return request;
    }

}
