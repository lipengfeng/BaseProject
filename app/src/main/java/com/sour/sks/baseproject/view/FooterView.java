package com.sour.sks.baseproject.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.sour.sks.baseproject.R;

import ww.com.core.ScreenUtil;

/**
 * Created by lenovo on 2016/2/1.
 */
public class FooterView extends LinearLayout {
    ProgressBar progressBar;
    TextView textView;

    public FooterView(Context context) {
        super(context);
        initView();
    }

    public FooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FooterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView() {
        setOrientation(HORIZONTAL);
        inflate(getContext(), R.layout.view_pull_foolter, this);
        ScreenUtil.scale(this);
        setGravity(Gravity.CENTER);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.textView);
    }

    public void setFooterText(CharSequence chart) {
        textView.setText(chart);
    }
}
