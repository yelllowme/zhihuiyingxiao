package com.yunshangkeji.zhihuiyingxiao.util;

import org.json.JSONArray;
/**
 * Created by yellow on 2016/11/16.
 * 网络请求监听回调
 */

public interface yingxiaoHttpResponseListener {

    //和服务器通信成功，并且获取状态为成功
    public void onSuccessResponse(JSONArray jsonArray);

    //和服务器通信成功，但是获取状态为不成功
    public void onConnectingError();

    //和服务器建立连接错误
    public void onDisconnectError();

}
