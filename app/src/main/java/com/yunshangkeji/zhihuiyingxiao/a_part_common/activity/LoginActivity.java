package com.yunshangkeji.zhihuiyingxiao.a_part_common.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.yunshangkeji.zhihuiyingxiao.R;
import com.yunshangkeji.zhihuiyingxiao.b_part_guide.activity.MerchandiseListActivity;
import com.yunshangkeji.zhihuiyingxiao.util.DBUtil;
import com.yunshangkeji.zhihuiyingxiao.util.HttpUtil;
import com.yunshangkeji.zhihuiyingxiao.util.OtherUtil;
import com.yunshangkeji.zhihuiyingxiao.util.yingxiaoHttpResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

    private PopupWindow loading_page;

    private View rootView;

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
                loading_page.showAtLocation(rootView, Gravity.CENTER, 0, 0);
                login_server();
                break;
            }
            default:
                break;
        }
    }

    //控件初始化
    private void viewInit(){
        loading_page = OtherUtil.getLoadingPage(LoginActivity.this);
        rootView = LayoutInflater.from(LoginActivity.this).inflate(R.layout.layout_activity_login, null);
        editText_account = (EditText)findViewById(R.id.edittext_login_account);
        editText_password = (EditText)findViewById(R.id.edittext_login_password);
        textView_loginBtn = (TextView)findViewById(R.id.texttview_login_loginBtn);
        textView_loginBtn.setOnClickListener(this);
    }

    //服务器登录
    private void login_server(){
        try {
            JSONObject jsonObject_params = new JSONObject();
            jsonObject_params.put("Account", editText_account.getText().toString());
            jsonObject_params.put("Character", DBUtil.ACCOUNT_IDENTITY_GUIDE);
            jsonObject_params.put("Password", editText_password.getText().toString());
            Map<String, String> params = new HashMap<>();
            params.put("obj", jsonObject_params.toString());
            HttpUtil.sendVolleyStringRequest_Post(LoginActivity.this, HttpUtil.login_url, params, false, "yellow_login",
                    new yingxiaoHttpResponseListener() {
                        @Override
                        public void onSuccessResponse(JSONObject jsonObject_response) {
                            try {
                                //登录成功
                                JSONObject jsonObject_data = jsonObject_response.getJSONObject("data");
                                String token = jsonObject_data.getString("Access_token");//获取token值
                                //判断此用户是否存在于本地数据库
                                //如果有此用户，更新此用户密码，token
                                if (DBUtil.hasThisLocalUserByAccount(editText_account.getText().toString())){
                                    DBUtil.localLogin(editText_account.getText().toString(), token, editText_password.getText().toString());

                                //如果没有此用户，添加此用户到本地数据库，再在本地数据库登录此账号
                                }else {
                                    DBUtil.addLocalUser(editText_account.getText().toString(), editText_password.getText().toString(), token, DBUtil.ACCOUNT_IDENTITY_GUIDE);
                                    DBUtil.localLogin(editText_account.getText().toString(), token, editText_password.getText().toString());
                                }
                                //跳转界面
                                Intent intent = new Intent(LoginActivity.this, MerchandiseListActivity.class);
                                startActivity(intent);
                                loading_page.dismiss();
                                LoginActivity.this.finish();
                            } catch (JSONException e) {
                                Toast.makeText(LoginActivity.this, "返回参数解析错误，请反馈！",Toast.LENGTH_SHORT).show();
                                loading_page.dismiss();
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onConnectingError() {
                            loading_page.dismiss();
                        }

                        @Override
                        public void onDisconnectError() {
                            loading_page.dismiss();
                        }
                    });
        } catch (JSONException e) {
            Toast.makeText(LoginActivity.this, "账号密码读取错误，请反馈！",Toast.LENGTH_SHORT).show();
            loading_page.dismiss();
            e.printStackTrace();
        }
    }
}
