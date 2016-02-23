package com.sour.sks.baseproject.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ww on 2016/1/26.
 */
public abstract class ARecyclerBaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<T> mData;
    protected int resId;
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    protected ItemClickListener mItemClickListener;
    protected ItemLongClickListener mItemLongClickListener;

    public ARecyclerBaseAdapter(Context mContext, int resId) {
        this(new ArrayList<T>(), resId, mContext);
    }

    public ARecyclerBaseAdapter(List<T> mData, int resId, Context mContext) {
        this.mData = mData;
        this.resId = resId;
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public ItemClickListener getOnItemClickListener() {
        return mItemClickListener;
    }

    public void setOnItemClickListener(ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public ItemLongClickListener getOnItemLongClickListener() {
        return mItemLongClickListener;
    }

    public void setOnItemLongClickListener(ItemLongClickListener mItemLongClickListener) {
        this.mItemLongClickListener = mItemLongClickListener;
    }

    public void addList(List<T> newData) {
        if (mData == null) {
            mData = new ArrayList<T>();
        }
        this.mData.clear();
        this.mData.addAll(newData);
        this.notifyDataSetChanged();
    }

    public void appendItems(List<T> appendData) {
        int startPosition = getItemCount();
        mData.addAll(appendData);
        this.notifyItemRangeInserted(startPosition, appendData.size());
    }

    public void addItem(T item) {
        addItem(item, getItemCount());
    }

    public void addFirstItem(T item) {
        addItem(item, 0);
    }

    public void addItem(T item, int position) {
        mData.add(position, item);
        this.notifyItemInserted(position);
    }

    public void delItem(int position) {
        mData.remove(position);
        this.notifyItemRemoved(position);
    }

    protected int getResId(int viewType) {
        return resId;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
