package com.yunshangkeji.zhihuiyingxiao.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yunshangkeji.zhihuiyingxiao.a_part_common.yingxiaoApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by yellow on 2016/11/16.
 * 网络请求工具类
 */

public class HttpUtil {

    /**
     * 用户接口调用OpenId
     */
    public static final String OPEN_ID = "kN2bB3rB1dU6nD2hT1cZ4jB2fE1gT6";

    /**
     * APP版本信息
     * ONE（年版本，从左往右第一个版本号）
     * TWO（月版本，从左往右第二个版本号）
     * THREE（周版本，从左往右第三个版本号）
     *
     * 2016/11/16 17：29 版本：v1.0.0（初始版本）
     */
    public static int APP_VERSION_ONE = 1;

    public static int APP_VERSION_TWO = 0;

    public static int APP_VERSION_THREE = 0;

    /**
     * APP版本代号
     * 0：ios
     * 1：Android
     */
    public static final int APP_SYSTEM_IOS = 0;

    public static final int APP_SYSTEM_ANDROID = 1;

    /**
     * 接口返回Flag标记值
     * 10000：正常拿到结果（msg为""）
     * 其他：不正常(msg不为""，有错误提示)
     */
    public static final int HTTP_RESPONSE_STATE_SUCCESS = 10000;

    /**
     * url
     */

    //接口协议：HTTP
    public static final String APP_API_PROTOCOL = "http://";

    //接口域名（临时内网地址）
    public static final String APP_API_DOMAIN = "192.168.0.31";

    //测试服接口域名
    // public static final String APP_API_DOMAIN = "t2sj.tgpapp.com";

    //正式服接口域名
    // public static final String APP_API_DOMAIN = "sj.tgpapp.com";

    //APP接口路径
    public static final String APP_API_PATH = "/appApi/Business/";

    /**
     * 接口方法名
     */
    public static String url_header = APP_API_PROTOCOL + APP_API_DOMAIN + APP_API_PATH;

    //获取版本信息
    public static String getVersionInfo_url = url_header + "GetVersionBusiness.ashx";

    //登录
    public static String login_url = url_header + "AppLogin.ashx";

    /**
     * Http POST请求通用方法
     * @param context 上下文
     * @param url 请求地址
     * @param params Volley请求参数
     * @param annotation 返回Log描述
     * @param listener 返回监听
     */
    public static void sendVolleyStringRequest_Post(final Context context, String url, final Map<String, String> params, final boolean needLogin, final String annotation,
                                                    final yingxiaoHttpResponseListener listener){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(annotation, s);
                try {
                    JSONArray jsonArray_response = new JSONArray(s);
                    JSONObject jsonObject_response = jsonArray_response.getJSONObject(0);
                    int flag = jsonObject_response.getInt("flag");
                    if (flag == HTTP_RESPONSE_STATE_SUCCESS){
                        listener.onSuccessResponse(jsonObject_response);
                    }else {
                        listener.onConnectingError();
                        String msg = jsonObject_response.getString("msg");
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "解析返回数据出错，请反馈！", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                listener.onDisconnectError();
                parseVolleyError(context, volleyError);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                params.put("OpenId", OPEN_ID);
                if (needLogin){
                    addLoginVolleyHttpParams_Post(params);
                }
                return params;
            }
        };
        yingxiaoApplication.requestQueue.add(stringRequest);
    }

    /**
     * 向Volley请求的参数中添加请求参数（POST）
     * @param params
     */
    public static void addLoginVolleyHttpParams_Post(Map<String, String> params){

    }

    /**
     * Http GET请求通用方法
     * @param context 上下文
     * @param url 请求地址
     * @param params Volley请求参数
     * @param annotation 返回Log描述
     * @param listener 返回监听
     */
    public static void sendVolleyStringRequest_Get(final Context context, String url, final Map<String, String> params, final boolean needLogin, final String annotation,
                                                   final yingxiaoHttpResponseListener listener){
        url += "?" + "OpenId=" + OPEN_ID;
        if (needLogin){
            addLoginVolleyHttpParams_Get(url);
        }
        for(String key:params.keySet()){
            url += "&" + key + "=" + params.get(key);
        }
        Log.d("yellow_temp", "get_url--->" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(annotation, s);
                try {
                    JSONArray jsonArray_response = new JSONArray(s);
                    JSONObject jsonObject_response = jsonArray_response.getJSONObject(0);
                    int flag = jsonObject_response.getInt("flag");
                    if (flag == HTTP_RESPONSE_STATE_SUCCESS){
                        listener.onSuccessResponse(jsonObject_response);
                    }else {
                        listener.onConnectingError();
                        String msg = jsonObject_response.getString("msg");
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "解析返回数据出错，请反馈！", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                listener.onDisconnectError();
                parseVolleyError(context, volleyError);
            }
        });
        yingxiaoApplication.requestQueue.add(stringRequest);
    }

    /**
     * 向Volley请求的参数中添加请求参数（Get）
     * @param url 请求url
     */
    public static void addLoginVolleyHttpParams_Get(String url){

    }

    /**
     * 解析VolleyError
     */
    public static void parseVolleyError(Context context, VolleyError volleyError){
        if (volleyError instanceof AuthFailureError) {
            Toast.makeText(context, "Http身份验证错误，请重试！", Toast.LENGTH_SHORT).show();
        }
        else if (volleyError instanceof NetworkError) {
            Toast.makeText(context, "连接服务器超时，请检查网络是否畅通！", Toast.LENGTH_SHORT).show();
        }
        else if (volleyError instanceof ParseError) {
            Toast.makeText(context, "解析Volley返回参数错误，请反馈！", Toast.LENGTH_SHORT).show();
        }
        else if (volleyError instanceof ServerError) {
            Toast.makeText(context, "服务器响应错误，请稍后重试", Toast.LENGTH_SHORT).show();
        }
        else if (volleyError instanceof TimeoutError) {
            Toast.makeText(context, "网络连接超时，请检查网络后重试！", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "未知错误，请反馈！", Toast.LENGTH_SHORT).show();
        }
    }
}
