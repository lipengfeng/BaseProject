package com.sour.sks.baseproject.api;

import ww.com.http.IHttpCancelListener;
import ww.com.http.IHttpLog;
import ww.com.http.IHttpRequest;
import ww.com.http.IHttpRequestCompleteListener;

/**
 * Created by ww on 2016/1/26.
 */
public class HttpCallback implements IHttpRequestCompleteListener {

    @Override
    public void onStart(IHttpRequest request) {

    }

    @Override
    public void onSuccess(IHttpRequest request, String url, byte[] obj) {

    }

    @Override
    public void onFail(IHttpRequest request, int errorCode, String url) {

    }

    @Override
    public void onEnd(IHttpRequest request) {

    }

    @Override
    public IHttpCancelListener getCancelListener() {
        return null;
    }

    @Override
    public IHttpLog getHttpLog() {
        return null;
    }
}