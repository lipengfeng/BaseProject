package com.sour.sks.baseproject.adapter.abslistview;

import android.view.View;

import butterknife.ButterKnife;
import ww.com.core.ScreenUtil;

public abstract class IViewHolder<T> {
    protected View contentView;

    /**
     * initView(这里用一句话描述这个方法的作用)
     *
     * @param v
     * @return void
     * @Title: initView  初始化布局
     * @Description:
     */
    public final void createView(View v) {
        ScreenUtil.scale(v);
        this.contentView = v;
        initView();
    }

    ;

    @SuppressWarnings("unchecked")
    public <T extends View> T findView(int resId) {
        return (T) contentView.findViewById(resId);
    }

    public void initView() {
        ButterKnife.bind(this, contentView);
    }

    /**
     * buildData(这里用一句话描述这个方法的作用)
     *
     * @param obj
     * @return void
     * @Title: buildData  绑定数据
     */
    public abstract void buildData(int position, T obj);

}
