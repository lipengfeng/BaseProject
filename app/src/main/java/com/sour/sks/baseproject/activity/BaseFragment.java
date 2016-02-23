package com.sour.sks.baseproject.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sour.sks.baseproject.R;
import com.sour.sks.baseproject.titlebar.TitleBar;
import com.sour.sks.baseproject.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ww.com.core.ScreenUtil;
import ww.com.http.IHttpCancelListener;

/**
 * Created by ww on 2016/1/26.
 */
public abstract class BaseFragment extends Fragment implements IHttpCancelListener {
    private boolean pause = false;
    private boolean cancel = false;
    private String tag;
    private View contentView;
    private TitleBar titleBar;
    protected Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tag = this.getClass().getSimpleName();
        contentView = inflater.inflate(getLayoutResId(), container, false);
        ScreenUtil.scale(contentView);
        initTitle();
        ButterKnife.bind(this, contentView);
        initViews();
        initData();
        return contentView;
    }

    protected abstract void initViews();

    protected void initTitle() {
        if (contentView != null) {
            View initedTitleBar = ButterKnife.findById(contentView, R.id.title_bar);
            if (initedTitleBar != null) {
                titleBar = TitleBar.getInstance(mContext).init(initedTitleBar);
            }
        }
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    protected abstract void initData();

    protected abstract
    @LayoutRes
    int getLayoutResId();

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
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
    @OnClick(R.id.text_title_right)
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

    @Override
    public void onResume() {
        super.onResume();
        pause = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        pause = true;
    }

    @Override
    public boolean isCancel() {
        return cancel;
    }

    @Override
    public boolean isPause() {
        return pause;
    }

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
    }

    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onStart() {
        cancel = false;
        super.onStart();
    }

    @Override
    public void onStop() {
        cancel = true;
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public void showToast(String msg) {
        ToastUtil.showToast(mContext, msg);
    }

}
