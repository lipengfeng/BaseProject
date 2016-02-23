package com.sour.sks.baseproject.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import com.sour.sks.baseproject.R;

import ww.com.core.ScreenUtil;

public class DialogUtils {

    public static void showCommonNotice(Context context, String msg, String ok, String no,
                                        OnClickListener onClickListener) {
        showCommonNotice(context, "", msg, ok, no, onClickListener);
    }

    public static void showCommonNotice(Context context, String title, String msg, String ok, String no,
                                        OnClickListener onClickListener) {
        try {
            if (!TextUtils.isEmpty(msg)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setMessage(msg)
                        .setCancelable(true)
                        .setTitle(title)
                        .setNegativeButton(no, null)
                        .setPositiveButton(ok, onClickListener);
                show(builder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showNotice(Context context, String title, String msg,
                                  OnClickListener onClickListener) {
        try {
            if (!TextUtils.isEmpty(msg)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle(title)
                        .setMessage(msg)
                        .setCancelable(true)
                        .setPositiveButton(context.getString(R.string.ok), onClickListener);
                show(builder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showNotice(Context context, String title, String msg) {
        showNotice(context, title, msg, null);
    }

    public static void showNotice(Context context, String msg, String positive,
                                  OnClickListener pClickListener, String negative,
                                  OnClickListener nClickListener) {
        showNotice(context, msg, positive, pClickListener, negative, nClickListener, null);
    }

    public static void showNotice(Context context, String msg, String positive,
                                  OnClickListener pClickListener, String negative,
                                  OnClickListener nClickListener, DialogInterface.OnCancelListener cancelListener) {
        showNotice(context, null, msg, positive, pClickListener, negative, nClickListener, null, null, cancelListener);
    }

    public static void showNotice(Context context, String title, String msg, String positive,
                                  OnClickListener pClickListener, String negative,
                                  OnClickListener nClickListener, String neutral,
                                  OnClickListener neClickListener,
                                  DialogInterface.OnCancelListener cancelListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(positive, pClickListener);
        builder.setNeutralButton(neutral, neClickListener);
        builder.setNegativeButton(negative, nClickListener);

        if (cancelListener != null) {
            builder.setOnCancelListener(cancelListener);
        }
        show(builder);
    }

    /**
     * @param context
     * @param itemClick wich 0 拍照  1 本地选择
     */
    public static void showPhotoSelect(Context context, OnClickListener itemClick) {
        ArrayAdapter<String> itemAdater = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, context.getResources().getStringArray(R.array.photo_select)) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.parseColor("#53a9ff"));
                int padding = ScreenUtil.getScalePxValue(72);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenUtil.getScalePxValue(42));
                textView.setPadding(padding, 0, padding, 0);
                return textView;
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("图片选择");
        builder.setAdapter(itemAdater, itemClick);
//        builder.show();
        show(builder);
    }
//
//    public static void showWebList(Context context, List<WebInfo> infos, OnClickListener itemClick) {
//        ArrayAdapter<WebInfo> itemAdater = new ArrayAdapter<WebInfo>(context, android.R.layout.simple_list_item_1, infos) {
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                TextView textView = (TextView) super.getView(position, convertView, parent);
//                textView.setTextColor(Color.parseColor("#53a9ff"));
//                int padding = ScreenUtil.getScalePxValue(72);
//                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenUtil.getScalePxValue(42));
//                textView.setPadding(padding, 0, padding, 0);
//                textView.setCompoundDrawablePadding(padding);
//                textView.setCompoundDrawables(getItem(position).ico, null, null, null);
//                return textView;
//            }
//        };
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("浏览器选择");
//        builder.setAdapter(itemAdater, itemClick);
//        show(builder);
//    }

    public static void show(AlertDialog.Builder builder) {
        AlertDialog dialog = builder.show();
        Button btnPositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        int btnSize = ScreenUtil.getScalePxValue(42);
        int textSize = ScreenUtil.getScalePxValue(46);

        if (btnPositive != null) {
            btnPositive.setTextColor(Color.parseColor("#53a9ff"));
            btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnSize);
        }

        Button btnNegative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (btnNegative != null) {
            btnNegative.setTextColor(Color.parseColor("#ff4283"));
            btnNegative.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnSize);
        }

        TextView textView = (TextView) dialog
                .findViewById(android.R.id.message);
        if (textView != null) {
            textView.setTextColor(Color.parseColor("#747474"));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
    }

}
