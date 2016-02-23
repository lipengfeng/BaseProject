package com.sour.sks.baseproject.adapter.abslistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class ABaseAdapter<T> extends BaseAdapter {
    private static final int _ID = 0x7f010000;
    private Context context;
    protected LayoutInflater layoutInflater;
    protected List<T> listData;
    protected int resId;

    @Deprecated
    public ABaseAdapter(Context context, List<T> listData, int resId) {
        super();
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.listData = listData;
        this.resId = resId;
    }


    public ABaseAdapter(Context context, int resId) {
        super();
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.listData = new ArrayList<T>();
        this.resId = resId;
    }

    public void clearList() {
        listData.clear();
        this.notifyDataSetChanged();
    }

    public List<T> getList() {
        return listData;
    }

    public void appendList(List<T> list) {
        this.listData.addAll(list);
        notifyDataSetChanged();
    }

    public void appendListTop(List<T> list) {
        this.listData.addAll(0, list);
        notifyDataSetChanged();
    }


    public void addList(List<T> list) {
        if (list == null) {
            list = new ArrayList<T>();
        }
        this.listData.clear();
        this.listData.addAll(list);
        notifyDataSetChanged();
    }

    public void addItem(T bean) {
        if (this.listData == null) {
            listData = new ArrayList<>();
        }
        this.listData.add(bean);
        notifyDataSetChanged();
    }

    public void addFristItem(T bean) {
        this.listData.add(0, bean);
        notifyDataSetChanged();
    }

    public void delItem(T bean) {
        this.listData.remove(bean);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public T getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return _ID + position;
    }

    /**
     * 获取列表中的 条目对应的 layout_resid
     *
     * @param position
     * @return
     */
    protected int getItemViewId(int position) {
        return resId;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IViewHolder<T> viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(getItemViewId(position), null);
            viewHolder = getViewHolder(position);
            viewHolder.createView(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (IViewHolder<T>) convertView.getTag();
        }
        viewHolder.buildData(position, getItem(position));
        return convertView;
    }

    protected abstract IViewHolder<T> getViewHolder(int position);

    public Context getContext() {
        return context;
    }

}
