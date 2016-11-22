package com.yunshangkeji.zhihuiyingxiao.b_part_guide.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.yunshangkeji.zhihuiyingxiao.R;

/**
 * Created by yellow on 2016/11/22.
 * 购物车页面
 */

public class ShoppingCarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_activity_shopping_car);
    }
}
