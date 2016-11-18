package com.yunshangkeji.zhihuiyingxiao.a_part_common.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.yunshangkeji.zhihuiyingxiao.R;

/**
 * Created by yellow on 2016/11/18.
 * 选择身份页面
 */

public class ChoosedIdentityActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_activity_choosed_identity);
    }
}
