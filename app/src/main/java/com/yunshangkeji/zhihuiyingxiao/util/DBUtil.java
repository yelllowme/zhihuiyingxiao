package com.yunshangkeji.zhihuiyingxiao.util;

import android.content.ContentValues;
import android.database.Cursor;

import com.yunshangkeji.zhihuiyingxiao.a_part_common.model.LocalUserModel;
import com.yunshangkeji.zhihuiyingxiao.a_part_common.yingxiaoApplication;

/**
 * Created by yellow on 2016/11/16.
 * 本地数据库工具
 */

public class DBUtil {

    /**
     * 本地数据库登录状态
     * 1：已登录
     * 2：未登录
     */
    public static final int LOCAL_LOGIN_STATE_HAS_LOGIN = 1;

    public static final int LOCAL_LOGIN_STATE_NOT_LOGIN = 2;

    /**
     *账号身份编码
     * 10111：导购
     */
    public static final String ACCOUNT_IDENTITY_GUIDE = "10111";

    //本地登录
    public static void localLogin(String account, String token, String password){
        String sql = "select * from user_info where account=" + account;
        Cursor cursor = yingxiaoApplication.user_db.rawQuery(sql, null);
        if (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            ContentValues contentValues = new ContentValues();
            contentValues.put("access_token", token);
            contentValues.put("password", password);
            contentValues.put("islogin", LOCAL_LOGIN_STATE_HAS_LOGIN);
            yingxiaoApplication.user_db.update("user_info", contentValues, "id=?", new String[]{String.valueOf(id)});
        }
        cursor.close();
    }

    //是否有本地登录用户
    public static boolean isLocalLogin(){
        String sql = "select * from user_info where islogin=" + LOCAL_LOGIN_STATE_HAS_LOGIN;
        Cursor cursor = yingxiaoApplication.user_db.rawQuery(sql, null);
        if (cursor.moveToNext()){
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    //添加一个本地用户
    public static void addLocalUser(String account, String password, String access_token, String role){
        ContentValues contentValues = new ContentValues();
        contentValues.put("account",account);
        contentValues.put("password", password);
        contentValues.put("access_token", access_token);
        contentValues.put("islogin", LOCAL_LOGIN_STATE_NOT_LOGIN);
        contentValues.put("role",role);
        yingxiaoApplication.user_db.insert("user_info", null, contentValues);
    }

    //获取本地登录用户
    public static LocalUserModel getLoginLocalUser(){
        String sql = "select * from user_info where islogin=" + LOCAL_LOGIN_STATE_HAS_LOGIN;
        Cursor cursor = yingxiaoApplication.user_db.rawQuery(sql, null);
        if (cursor.moveToNext()){
            LocalUserModel loginUser = new LocalUserModel();
            loginUser.setAccount(cursor.getString(cursor.getColumnIndex("account")));
            loginUser.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            loginUser.setRole(cursor.getString(cursor.getColumnIndex("role")));
            loginUser.setToken(cursor.getString(cursor.getColumnIndex("access_token")));
            cursor.close();
            return loginUser;
        }
        return null;
    }

    //本地注销
    public static void localLogout(){
        String sql = "select * from user_info where islogin=" + LOCAL_LOGIN_STATE_HAS_LOGIN;
        Cursor cursor = yingxiaoApplication.user_db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            ContentValues contentValues = new ContentValues();
            contentValues.put("islogin", LOCAL_LOGIN_STATE_NOT_LOGIN);
            yingxiaoApplication.user_db.update("user_info", contentValues, "id=?", new String[]{String.valueOf(id)});
        }
        cursor.close();
    }

    //根据账号判断本地是否有此用户
    public static boolean hasThisLocalUserByAccount(String account){
        String sql = "select * from user_info where account=" + account;
        Cursor cursor = yingxiaoApplication.user_db.rawQuery(sql, null);
        if (cursor.moveToNext()){
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

}
