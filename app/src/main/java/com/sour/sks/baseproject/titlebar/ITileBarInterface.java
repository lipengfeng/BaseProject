package com.sour.sks.baseproject.titlebar;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public interface ITileBarInterface {

    /**
     * 设置标题
     */
    void setTitle(View _view, LayoutParams _params);

    TextView setTitle(String _str);

    Button getTitleButtonRight(int _id);

    Button getTitleButtonLeft(int _id);

    TextView getTitleTextRight(String _str);

    TextView getTitleTextLeft(String _str);
}
