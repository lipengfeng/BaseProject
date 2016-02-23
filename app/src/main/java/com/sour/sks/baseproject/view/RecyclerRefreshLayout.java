package com.sour.sks.baseproject.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.sour.sks.baseproject.R;

import ww.com.core.Debug;

/**
 * Created by lenovo on 2016/2/1.
 */
public class RecyclerRefreshLayout extends FrameLayout {
    MySwipeRefreshLayout refreshLayout;
    XRecyclerView recyclerView;
    FrameLayout flayEmpty;
    FooterView footerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.AdapterDataObserver emptyObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (adapter != null) {
                Debug.d("adapter.getItemCount() : " + adapter.getItemCount());
                if (adapter.getItemCount() == 0) {
                    flayEmpty.setVisibility(View.VISIBLE);
                } else {
                    flayEmpty.setVisibility(View.INVISIBLE);
                }
            }
        }
    };

    public RecyclerRefreshLayout(Context context) {
        super(context);
        initView();
    }

    public RecyclerRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.view_recycler_refresh, this);
        flayEmpty = (FrameLayout) findViewById(R.id.flay_empty);
        recyclerView = (XRecyclerView) findViewById(R.id.recyclerView);
        refreshLayout = (MySwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        footerView = new FooterView(getContext());
        footerView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout.setFooterView(footerView);
        refreshLayout.setRecyclerView(recyclerView);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        recyclerView.setLayoutManager(layout);
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        recyclerView.setItemAnimator(animator);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        recyclerView.setAdapter(adapter);
        if (this.adapter != null) {
            this.adapter.registerAdapterDataObserver(emptyObserver);
        }
        emptyObserver.onChanged();
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setEmptyView(View emptyView) {
        flayEmpty.removeAllViews();
        flayEmpty.addView(emptyView);
    }

    public void setEmptyView(View emptyView, LayoutParams params) {
        flayEmpty.removeAllViews();
        flayEmpty.addView(emptyView, params);
    }

    public void setFooterRefreshAble(boolean footerRefreshAble) {
        refreshLayout.setFooterRefreshAble(footerRefreshAble);
    }

    public void setRefreshFinished() {
        refreshLayout.setRefreshFinished();
    }

    public void setOnSwipeRefreshListener(WWSwipeRefreshLayout.WWSwipeRefreshLayoutListener onSwipeRefreshListener) {
        refreshLayout.setOnSwipeRefreshListener(onSwipeRefreshListener);
    }
}
