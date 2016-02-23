package com.sour.sks.baseproject.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sour.sks.baseproject.BaseApplication;
import com.sour.sks.baseproject.R;
import com.sour.sks.baseproject.titlebar.TitleBar;
import com.sour.sks.baseproject.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ww.com.core.ScreenUtil;
import ww.com.http.IHttpCancelListener;

/**
 * Created by ww on 2016/1/25.
 */
public abstract class BaseActivity extends AppCompatActivity implements IHttpCancelListener {
    protected String tag;
    protected BaseApplication baseApplication;
    protected Context mContext;
    protected TitleBar titleBar;
    @Nullable
    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    private boolean pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tag = this.getClass().getSimpleName();
        if (!ImageLoader.getInstance().isInited()) {
            BaseApplication.initImageLoader(getApplicationContext());
        }
        super.onCreate(savedInstanceState);

        mContext = this;

        baseApplication = BaseApplication.getInstance();

        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        scaleView();
    }

    protected void scaleView() {
        ScreenUtil.scale(ButterKnife.findById(this, android.R.id.content));
        View initedTitleBar = ButterKnife.findById(this, R.id.title_bar);
        if (initedTitleBar != null) {
            titleBar = TitleBar.getInstance(this).init(initedTitleBar);
        }
//        setSupportActionBar(getModifiedToolbar());
        initView();
        initData();
    }

    protected void setTitle(String title) {
        checkTitleBar();
        titleBar.setTitle(title);
    }

    protected Button getTitleButtonLeft(int resId) {
        checkTitleBar();
        return titleBar.getTitleButtonLeft(resId);
    }

    protected Button getTitleButtonRight(int resId) {
        checkTitleBar();
        return titleBar.getTitleButtonRight(resId);
    }

    protected TextView getTitleTextRight(String string) {
        checkTitleBar();
        return titleBar.getTitleTextRight(string);
    }

    protected TextView getTitleTextLeft(String string) {
        checkTitleBar();
        return titleBar.getTitleTextLeft(string);
    }

    private boolean checkTitleBar() {
        if (titleBar == null) {
            throw new RuntimeException("View does not have a title bar");
        } else {
            return true;
        }
    }

    @Nullable
    @OnClick(R.id.text_title)
    protected void onTitleClick(TextView v) {

    }

    @Nullable
    @OnClick(R.id.text_title_right)
    protected void onTitleTextRightClick(TextView v) {

    }

    @Nullable
    @OnClick(R.id.text_title_left)
    protected void onTitleTextLeftClick(TextView v) {

    }

    @Nullable
    @OnClick(R.id.btn_title_left)
    protected void onTitleButtonLeftClick(Button button) {

    }

    @Nullable
    @OnClick(R.id.btn_title_right)
    protected void onTitleButtonRightClick(Button button) {

    }

    public void showToast(String msg) {
        ToastUtil.showToast(this, msg);
    }

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
    }

    public void startActivity(Class<?> cls, long time) {
        final Intent intent = new Intent(mContext, cls);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        }, time);
    }

    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected abstract
    @LayoutRes
    int getLayoutResId();

    protected abstract void initView();

    protected abstract void initData();

    protected Toolbar getModifiedToolbar() {
        return toolbar;
    }

    @Override
    public boolean isCancel() {
        return isFinishing();
    }

    @Override
    public boolean isPause() {
        return pause;
    }

    @Override
    protected void onResume() {
        super.onResume();
        baseApplication.addRunActivity(this);
        pause = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        pause = true;
        //socketClient.removeISocketResponseListener();
    }

    @Override
    protected void onDestroy() {
        baseApplication.removeRunActivity(this);
        super.onDestroy();
    }

}
