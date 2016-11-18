package com.yunshangkeji.zhihuiyingxiao.b_part_guide.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.yunshangkeji.zhihuiyingxiao.R;

/**
 * Created by yellow on 2016/11/18.
 * 商品列表界面
 */

public class MerchandiseListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_activity_merchandise_list);
    }
}
