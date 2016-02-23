package com.sour.sks.baseproject.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by ww on 2016/2/3.
 */
public class MySwipeRefreshLayout extends WWSwipeRefreshLayout {
    XRecyclerView recyclerView;
    FooterView footerView;

    public MySwipeRefreshLayout(Context context) {
        super(context);
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setRecyclerView(RecyclerView mRecyclerView) {
        this.recyclerView = (XRecyclerView) mRecyclerView;
        super.setRecyclerView(mRecyclerView);
    }

    public void setFooterView(FooterView footerView) {
        if (footerView == null)
            return;
        this.footerView = footerView;
    }

    @Override
    public void setRefreshFinished() {
        if (footerView != null) {
            recyclerView.removeFooterView(footerView);
        }
        super.setRefreshFinished();
    }

    @Override
    protected void onFooterRefreshing() {
        if (footerView != null) {
            recyclerView.addFooterView(footerView);
        }
        super.onFooterRefreshing();
    }
}