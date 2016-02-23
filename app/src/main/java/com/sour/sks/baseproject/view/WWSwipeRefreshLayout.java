package com.sour.sks.baseproject.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;

import com.ww.refreshlibrary.swiprefresh.WWScrollView;
import com.ww.refreshlibrary.swiprefresh.WWScrollViewListener;

public class WWSwipeRefreshLayout extends SwipeRefreshLayout implements
        OnScrollListener, WWScrollViewListener, OnRefreshListener {

    private AbsListView mAdapterView;
    private GridView mGridView;
    private WWScrollView mScrollView;
    private RecyclerView mRecyclerView;
    private boolean isRefreshing;
    protected boolean footerRefreshAble;
    private Handler handler;
    private WWSwipeRefreshLayoutListener refreshLayoutListener;

    public WWSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setColorSchemeColors(Color.parseColor("#62A8DB"), Color.RED);
        footerRefreshAble = false;
        handler = new Handler();
        setOnRefreshListener(this);
    }

    public WWSwipeRefreshLayout(Context context) {
        super(context);
        setColorSchemeColors(Color.parseColor("#62A8DB"));
        footerRefreshAble = true;
        handler = new Handler();
        setOnRefreshListener(this);
    }

    public void setFooterRefreshAble(boolean able) {
        footerRefreshAble = able;
    }

    public void setAbsListView(AbsListView listView) {
        mAdapterView = listView;
        mAdapterView.setOnScrollListener(this);
    }

    public void setGridView(GridView gridView) {
        mGridView = gridView;
        mGridView.setOnScrollListener(this);
    }

    public void setScrollView(WWScrollView scrollView) {
        mScrollView = scrollView;
        mScrollView.setWWScrollViewListener(this);
    }

    public void setRecyclerView(final RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
        final RecyclerView.LayoutManager mLayoutManager = mRecyclerView.getLayoutManager();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!footerRefreshAble || isRefreshing && refreshLayoutListener != null) {
                    return;
                }
                int lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                // dy>0 表示向下滑动
                if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                    onFooterRefreshing();
                }
            }
        });
    }

    public void setOnSwipeRefreshListener(WWSwipeRefreshLayoutListener listener) {
        refreshLayoutListener = listener;
    }

    public void setRefreshFinished() {
        isRefreshing = false;
        setRefreshing(false);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (!footerRefreshAble || isRefreshing) {
            return;
        }
        if (mAdapterView != null
                && scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            View lastChild = mAdapterView.getChildAt(mAdapterView
                    .getChildCount() - 1);
            if (lastChild != null) {
                if (lastChild.getBottom() <= getHeight()
                        && mAdapterView.getLastVisiblePosition() == mAdapterView
                        .getCount() - 1 ) {
                    onFooterRefreshing();
                }
            }
        }
        if (mGridView != null
                && scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            View lastChild = mGridView
                    .getChildAt(mGridView.getChildCount() - 1);
            if (lastChild != null) {
                if (lastChild.getBottom() <= getHeight()
                        && mGridView.getLastVisiblePosition() == mGridView
                        .getCount() - 1 ) {
                    onFooterRefreshing();
                }
            }
        }
    }

    protected void onFooterRefreshing() {
        isRefreshing = true;
        if (refreshLayoutListener != null) {
            handler.postDelayed(new Runnable() {
                public void run() {
                    refreshLayoutListener.onFooterRefreshing();
                }
            }, 300);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
    }

    @Override
    public void onRefresh() {
        isRefreshing = true;
        if (refreshLayoutListener != null) {
            refreshLayoutListener.onHeaderRefreshing();
        }
    }

    @Override
    public void onScrollToFooter() {
        if (!footerRefreshAble || isRefreshing && refreshLayoutListener != null) {
            return;
        }
        isRefreshing = true;
        handler.postDelayed(new Runnable() {
            public void run() {
                refreshLayoutListener.onFooterRefreshing();
            }
        }, 300);
    }

    public interface WWSwipeRefreshLayoutListener {

        void onHeaderRefreshing();

        void onFooterRefreshing();
    }

}
