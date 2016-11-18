package com.yunshangkeji.zhihuiyingxiao.a_part_common.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yunshangkeji.zhihuiyingxiao.R;
import com.yunshangkeji.zhihuiyingxiao.a_part_common.model.AppVersionModel;
import com.yunshangkeji.zhihuiyingxiao.a_part_common.yingxiaoApplication;
import com.yunshangkeji.zhihuiyingxiao.util.DBUtil;
import com.yunshangkeji.zhihuiyingxiao.util.HttpUtil;
import com.yunshangkeji.zhihuiyingxiao.util.yingxiaoHttpResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yellow on 2016/11/16.
 * APP加载页（欢迎页）
 * WelcomePageActivity执行步骤：
 * 检测版本->有版本更新->跳转到更新界面
 *         ->没有版本更新->判定本地是否有登录账号——》有本地已登录账号——》login—（成功）—》WelcomePageActivity登陆后步骤
 *                                                                          —（失败）—》跳转到登录界面
 *                                               ——》无本地已登录账号——》跳转到登录界面
 * WelcomePageActivity登陆后步骤：
 * 根据login成功后获取到账号身份列表操作—（账号只有一个身份）—》默认选取这个身份，根据这个身份操作（跳转到相应界面），将这个身份记录到数据库
 *                                      —（账号有多个身份）—》选择身份界面
 */

public class WelcomePageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_activity_welcome_page);

        viewInit();
        checkVersion();
    }

    //初始化控件
    private void viewInit(){

    }

    //版本检测之后的步骤(不需要版本更新)
    private void stepAfterCheckVersion_notUpdate(){
        //判定用户登录情况
        //如果本地有已登录用户，服务器登录
        if(DBUtil.isLogin()){
            login_server();

        //如果本地没有已登录用户，跳转到登录界面
        }else {
            Intent intent = new Intent(WelcomePageActivity.this, LoginActivity.class);
            startActivity(intent);
            WelcomePageActivity.this.finish();
        }
    }

    //版本检测（获取版本信息）
    private void checkVersion(){
        Map<String, String> params = new HashMap<>();
        params.put("systems", String.valueOf(HttpUtil.APP_SYSTEM_ANDROID));
        HttpUtil.sendVolleyStringRequest_Get(WelcomePageActivity.this, HttpUtil.getVersionInfo_url, params, false, "yellow_getVersionInfo",
                new yingxiaoHttpResponseListener() {
                    @Override
                    public void onSuccessResponse(JSONArray jsonArray) {
                        try {
                            JSONObject jsonObject_data = jsonArray.getJSONObject(0);
                            AppVersionModel.appVersionModel.setYearVersion(jsonObject_data.getInt("year")); //设置年版本号
                            AppVersionModel.appVersionModel.setMonthVersion(jsonObject_data.getInt("Month"));//设置月版本号
                            AppVersionModel.appVersionModel.setWeekVersion(jsonObject_data.getInt("Day"));//设置周版本号
                            AppVersionModel.appVersionModel.setIsNecessary(jsonObject_data.getInt("Forcibly"));//设置是否是必须更新
                            AppVersionModel.appVersionModel.setDownloadUrl(jsonObject_data.getString("DownloadUrl"));//设置APP新版本下载地址
                            AppVersionModel.appVersionModel.setVersionInfo(jsonObject_data.getString("Remark"));//设置APP版本信息
                            //判定版本更新情况
                            //如果不需要版本更新
                            if(AppVersionModel.appVersionModel.getYearVersion() == HttpUtil.APP_VERSION_ONE &&
                                    AppVersionModel.appVersionModel.getMonthVersion() == HttpUtil.APP_VERSION_TWO &&
                                    AppVersionModel.appVersionModel.getWeekVersion() == HttpUtil.APP_VERSION_THREE){
                                stepAfterCheckVersion_notUpdate();
                            //如果需要版本更新，跳转到更新界面
                            }else {
                                Intent intent = new Intent(WelcomePageActivity.this, AppUpdateActivity.class);
                                startActivity(intent);
                                WelcomePageActivity.this.finish();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(WelcomePageActivity.this, "解析数据出错，请反馈！", Toast.LENGTH_SHORT).show();
                            stepAfterCheckVersion_notUpdate();
                        }
                    }
                    @Override
                    public void onConnectingError() {
                        stepAfterCheckVersion_notUpdate();
                    }
                    @Override
                    public void onDisconnectError() {
                        stepAfterCheckVersion_notUpdate();
                    }
                });
    }

    //服务器登录
    private void login_server(){
        //登录成功，根据用户角色跳转到主界面
        //本地登录一次，更新token,role

        //登录失败，提示自动登录失败，跳转到登录界面
        //本地退出，所有账号logout
    }
}
