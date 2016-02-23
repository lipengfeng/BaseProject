package com.sour.sks.baseproject.titlebar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;


import com.sour.sks.baseproject.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import ww.com.core.ScreenUtil;

public class TitleBar implements ITileBarInterface {

    protected View titleView;

    @Nullable
    @Bind(R.id.btn_title_left)
    protected Button btnTitleLeft;
    @Nullable
    @Bind(R.id.btn_title_right)
    protected Button btnTitleRight;
    @Nullable
    @Bind(R.id.text_title)
    protected TextView textTitle;
    @Nullable
    @Bind(R.id.text_title_right)
    protected TextView textTitleRight;
    @Nullable
    @Bind(R.id.text_title_left)
    protected TextView textTitleLeft;

    private Resources mResources;

    private TitleBar(Context context) {
        mResources = context.getResources();
    }

    public static TitleBar getInstance(Context context) {
        return new TitleBar(context);
    }

    public TitleBar init(View titleView) {
        this.titleView = titleView;
        ButterKnife.bind(this, this.titleView);
        return this;
    }

    public Button getTitleButtonLeft(int _id) {
        if (titleView != null & btnTitleLeft != null) {
            if (_id > 0) {
                Drawable left = ScreenUtil.scaleBoundsDrawable(mResources
                        .getDrawable(_id));
                ((Button) btnTitleLeft).setCompoundDrawables(left, null, null,
                        null);
            }
            btnTitleLeft.setVisibility(View.VISIBLE);

        }
        return btnTitleLeft;
    }

    public Button getTitleButtonRight(int _id) {
        if (titleView != null & btnTitleRight != null) {
            if (_id > 0) {
                Drawable right = ScreenUtil.scaleBoundsDrawable(mResources
                        .getDrawable(_id));
                ((Button) btnTitleRight).setCompoundDrawables(null, null, right,
                        null);
            }
            btnTitleRight.setVisibility(View.VISIBLE);

        }
        return btnTitleRight;
    }

    public TextView setTitle(String _str) {
        if (titleView != null && textTitle != null) {
            textTitle.setText(_str);
        }
        return textTitle;
    }

    public TextView getTitleTextRight(String _str) {
        if (titleView != null && textTitleRight != null) {
            textTitleRight.setText(_str);
            textTitleRight.setVisibility(View.VISIBLE);
        }
        return textTitleRight;
    }

    public TextView getTitleTextLeft(String _str) {
        if (titleView != null && textTitleLeft != null) {
            textTitleLeft.setText(_str);
            textTitleLeft.setVisibility(View.VISIBLE);
        }
        return textTitleLeft;
    }

    public void setTitle(View _view, LayoutParams _params) {
        if (titleView != null) {
            ((RelativeLayout) titleView).addView(_view, _params);
        }
    }

}
