package com.yunshangkeji.zhihuiyingxiao.a_part_common.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.yunshangkeji.zhihuiyingxiao.R;
import com.yunshangkeji.zhihuiyingxiao.util.DBUtil;

/**
 * Created by yellow on 2016/11/16.
 * 登录页面
 * 登录成功后操作步骤：
 * 判断此用户是否存在于本地数据库中—（存在）—》更新此账号密码，token——》根据登录成功后获取的身份列表操作
 *                                 —（不存在）—》本地数据库添加此账号，并在本地进行登录——》根据登录成功后获取的身份列表操作
 *
 * 根据登录成功后获取的身份列表操作——》将本地记录的身份和获取的身份列表比对—（记录身份存在于获取的身份列表中）—》以此身份进行后续跳转
 *                                                                           —（记录身份不存在与获取的身份列表中）—》根据身份列表长度操作—（身份列表只有一个身份）—》以身份列表仅有的这个身份跳转
 *                                                                                                                                         —（身份列表有多个身份）—》跳转到选择身份界面
 */

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText editText_account, editText_password;

    private TextView textView_loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_activity_login);

        viewInit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //登录按钮
            case R.id.texttview_login_loginBtn:{
                login();
                break;
            }
            default:
                break;
        }
    }

    //控件初始化
    private void viewInit(){
        editText_account = (EditText)findViewById(R.id.edittext_login_account);
        editText_password = (EditText)findViewById(R.id.edittext_login_password);
        textView_loginBtn = (TextView)findViewById(R.id.texttview_login_loginBtn);
        textView_loginBtn.setOnClickListener(this);
    }

    //服务器登录
    private void login(){
        //登录成功
        //判断此用户是否存在于本地数据库
        //如果有此用户，更新此用户密码，token,role
        if (DBUtil.hasThisUserByAccount()){
            DBUtil.login();
        //如果没有此用户，添加此用户到本地数据库，再在本地数据库登录此账号
        }else {
            DBUtil.addLocalUser();
            DBUtil.login();
        }
        //根据登录用户角色跳转到主界面

        //登录失败，Toast
    }
}
