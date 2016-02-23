package com.sour.sks.baseproject.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.sour.sks.baseproject.adapter.HeaderAndFooterRecyclerViewAdapter;


/**
 * Created by lenovo on 2016/1/29.
 */
public class XRecyclerView extends RecyclerView {
    private HeaderAndFooterRecyclerViewAdapter hfAdapter;

    public XRecyclerView(Context context) {
        super(context);
    }

    public XRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        hfAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        super.setAdapter(hfAdapter);
    }

    public void addHeadView(View v) {
        if (hfAdapter != null) {
            hfAdapter.addHeaderView(v);
        }
    }

    public void removeHeadView(View v) {
        if (hfAdapter != null) {
            hfAdapter.removeHeaderView(v);
        }
    }

    public void addFooterView(View v) {
        if (hfAdapter != null) {
            hfAdapter.addFooterView(v);
        }
    }

    public void removeFooterView(View v) {
        if (hfAdapter != null) {
            hfAdapter.removeFooterView(v);
        }
    }
}
