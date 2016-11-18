package com.yunshangkeji.zhihuiyingxiao.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.yunshangkeji.zhihuiyingxiao.R;

/**
 * Created by yellow on 2016/11/16.
 * 工具类
 */

public class OtherUtil {

    /**
     * dp转换为px,以及px转换为dp
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取加载页(PopupWindow)
     */
    public static PopupWindow getLoadingPage(Context context){
        View loadingPage_contentview = LayoutInflater.from(context).inflate(R.layout.layout_loading, null);
        PopupWindow popupWindow = new PopupWindow(loadingPage_contentview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        return popupWindow;
    }

}
