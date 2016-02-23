package com.sour.sks.baseproject.adapter.recyclerview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ww on 2016/1/26.
 */
public abstract class ARecyclerAdapter<T> extends ARecyclerBaseAdapter<T, IRecyclerViewHolder> {

    public ARecyclerAdapter(Context mContext, int resId) {
        super(mContext, resId);
    }

    public ARecyclerAdapter(List<T> mData, int resId, Context mContext) {
        super(mData, resId, mContext);
    }

    @SuppressWarnings("unchecked")
    @Override
    public IRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        IRecyclerViewHolder holder = getHolderInstance(mLayoutInflater.inflate(getResId(viewType), parent, false), viewType);
        if (mItemLongClickListener != null) {
            holder.setOnItemLongClickListener(mItemLongClickListener);
        }
        if (mItemClickListener != null) {
            holder.setOnItemClickListener(mItemClickListener);
        }
        return holder;
    }

    protected abstract IRecyclerViewHolder getHolderInstance(View view, int viewType);

}
