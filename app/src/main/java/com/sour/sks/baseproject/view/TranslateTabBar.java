package com.sour.sks.baseproject.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.sour.sks.baseproject.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import ww.com.core.ScreenUtil;


public class TranslateTabBar extends RelativeLayout implements OnClickListener,
        AnimationListener {

    private Context mContext;
    private int btnCount = 4;
    private int buttonWidth = 0;
    private int buttonHeight = LayoutParams.MATCH_PARENT;
    private int translateLineWidth = 170;
    private int translateLineHeight = 10;
    private int dividLineWidth = 0;
    private int dividLineHeight = 64;
    private int currentTranslateLineIndex = 0;

    private int splitLineColor = R.color.main_layout_bg_color;
    private int translateLineColor = R.color.titlebar_bg_color4;
    private int bottomLineColor = R.color.titlebar_bg_color4;

    private int buttonTextColor = R.drawable.translate_tab_button_selector;

    private LinearLayout buttonLayout;
    private Button[] tabButton;
    private String[] stringArr;
    private View translateLineView;

    private OnTabChangeListener onChangelistener;

    public TranslateTabBar(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public TranslateTabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mContext = context;
        initView();
        getResourceForXml(attrs);
        initLine();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        setCurrentIndex(v.getId());
    }

    private void initView() {
        buttonLayout = new LinearLayout(mContext);
        buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
        LayoutParams p = new LayoutParams(ScreenUtil.BASE_SCREEN_WIDTH, 105);
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        this.addView(buttonLayout, p);

        this.setGravity(Gravity.CENTER_VERTICAL);
    }

    private void initLine() {
        LayoutParams p2 = new LayoutParams(translateLineWidth,
                translateLineHeight);
        p2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        p2.leftMargin = getTranslateLineOffsetX(0);
        View tempView = createTranslateLine();
        this.addView(tempView, p2);

        LayoutParams p3 = new LayoutParams(LayoutParams.MATCH_PARENT, 2);
        p3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        // this.addView(createBottomLine(), p3);

        buttonWidth = ScreenUtil.getScalePxValue(buttonWidth);
        translateLineWidth = ScreenUtil.getScalePxValue(translateLineWidth);

        setCurrentIndex(0);
    }

    @SuppressLint("Recycle")
    private void getResourceForXml(AttributeSet attrs) {
        if (attrs == null)
            return;
        TypedArray array = mContext.obtainStyledAttributes(attrs,
                R.styleable.TranslateTabBar, 0, 0);

        int textResId = array.getResourceId(R.styleable.TranslateTabBar_text,
                -1);
        float lwidth = array.getDimension(
                R.styleable.TranslateTabBar_lineWidth, 170f);

        int tempButtonTextColor = array.getResourceId(
                R.styleable.TranslateTabBar_textColor, -1);
        int tempSpliteLineColor = array.getResourceId(
                R.styleable.TranslateTabBar_spliteLineColor, -1);
        int tempTranslateLineColor = array.getResourceId(
                R.styleable.TranslateTabBar_translateLineColor, -1);
        int tempBottomLineColor = array.getResourceId(
                R.styleable.TranslateTabBar_bottomLineColor, -1);

        int tempButtonCount = array.getInteger(
                R.styleable.TranslateTabBar_buttonCount, 4);

        btnCount = tempButtonCount;

        buttonTextColor = (tempButtonTextColor == -1) ? buttonTextColor
                : tempButtonTextColor;
        splitLineColor = (tempSpliteLineColor == -1) ? splitLineColor
                : tempSpliteLineColor;
        translateLineColor = (tempTranslateLineColor == -1) ? translateLineColor
                : tempTranslateLineColor;
        bottomLineColor = (tempBottomLineColor == -1) ? bottomLineColor
                : tempBottomLineColor;
        translateLineWidth = (int) lwidth;
        stringArr = getResources().getStringArray(textResId);
        setText(stringArr);

    }

    private void setText(String[] str) {
        buttonLayout.removeAllViews();
        btnCount = str.length;
        tabButton = new Button[btnCount];
        buttonWidth = btnCount <= 1 ? ScreenUtil.BASE_SCREEN_WIDTH
                : (ScreenUtil.BASE_SCREEN_WIDTH - dividLineWidth
                * (btnCount - 1))
                / btnCount;
        for (int i = 0; i < btnCount; i++) {
            LayoutParams params = new LayoutParams(buttonWidth, buttonHeight);
            buttonLayout.addView(createButton(str[i], i), params);
            if (btnCount > 1 && i < (btnCount - 1)) {
                LayoutParams params2 = new LayoutParams(dividLineWidth,
                        dividLineHeight);
                buttonLayout.addView(createLine(), params2);
            }
        }
    }

    private Button createButton(String str, int index) {
        Button v = (Button) View.inflate(mContext,
                R.layout.view_translate_tab_button, null);
        v.setOnClickListener(this);
        v.setId(index);
        v.setText(String.format(str, "0"));
        try {
            v.setTextColor(ColorStateList.createFromXml(getResources(),
                    getResources().getXml(buttonTextColor)));
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        tabButton[index] = v;
        return v;
    }

    private View createLine() {
        View v = new View(mContext);
        v.setBackgroundResource(splitLineColor);

        return v;
    }

    private View createTranslateLine() {
        View v = new View(mContext);
        v.setBackgroundResource(translateLineColor);
        translateLineView = v;
        return v;
    }

    private View createBottomLine() {
        View v = new View(mContext);
        v.setBackgroundResource(bottomLineColor);
        return v;
    }

    /**
     * @param index 0 - ...
     * @return
     */
    private int getTranslateLineOffsetX(int index) {

        int offset = (buttonWidth - translateLineWidth) / 2 + buttonWidth
                * index;
        return offset;
    }

    private TranslateAnimation translateLine(int index) {
        int fromX = getTranslateLineOffsetX(currentTranslateLineIndex)
                - getTranslateLineOffsetX(0);
        int toX = getTranslateLineOffsetX(index) - getTranslateLineOffsetX(0);
        TranslateAnimation anim = new TranslateAnimation(fromX, toX, 0, 0);
        anim.setDuration(250);
        anim.setFillAfter(true);
        anim.setAnimationListener(this);

        currentTranslateLineIndex = index;

        return anim;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub
        for (Button btn : tabButton) {
            btn.setClickable(false);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // TODO Auto-generated method stub
        for (Button btn : tabButton) {
            btn.setClickable(true);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub

    }

    public void setText(int index, String str) {
        tabButton[index].setText(String.format(stringArr[index], str));
    }

    public void setOnTabChangeListener(OnTabChangeListener _li) {
        onChangelistener = _li;
    }

    public int getCount() {
        return btnCount;
    }

    public void setCurrentIndex(int index) {
        if (index != currentTranslateLineIndex && onChangelistener != null) {
            onChangelistener.onChange(index);
        }
        translateLineView.startAnimation(translateLine(index));
        for (Button btn : tabButton) {
            btn.setSelected(false);
        }
        tabButton[index].setSelected(true);
        tabButton[index].setClickable(false);
    }

    public interface OnTabChangeListener {
        public void onChange(int index);
    }
}
