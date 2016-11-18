package com.yunshangkeji.zhihuiyingxiao.a_part_common.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.yunshangkeji.zhihuiyingxiao.R;

/**
 * Created by yellow on 2016/11/17.
 * APP版本更新页面
 */

public class AppUpdateActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_activity_app_update);

        viewInit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            default:
                break;
        }
    }

    //初始化控件
    private void viewInit(){

    }
}
