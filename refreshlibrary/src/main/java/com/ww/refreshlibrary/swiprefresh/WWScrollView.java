package com.ww.refreshlibrary.swiprefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class WWScrollView extends ScrollView {

	private WWScrollViewListener scrollViewListener;

	public void setWWScrollViewListener(WWScrollViewListener listener) {
		scrollViewListener = listener;
	}

	public WWScrollView(Context context) {
		super(context);
	}

	public WWScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WWScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		if (scrollViewListener == null)
			return;
		int scrollY = getScrollY();
		int height = getHeight();
		int scrollViewMeasuredHeight = getChildAt(0).getMeasuredHeight();
		if ((scrollY + height) == scrollViewMeasuredHeight) {
			scrollViewListener.onScrollToFooter();
		}
		super.onScrollChanged(l, t, oldl, oldt);
	}

}

