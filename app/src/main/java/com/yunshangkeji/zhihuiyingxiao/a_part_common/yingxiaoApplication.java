package com.yunshangkeji.zhihuiyingxiao.a_part_common;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by yellow on 2016/11/16.
 * Application
 */

public class yingxiaoApplication extends Application {

    /**
     * 手机屏幕宽高
     */
    public static int screen_Width;

    public static int screen_Height;

    /**
     * Volley Http请求队列
     */
    public static RequestQueue requestQueue;

    /**
     * 用户数据库
     */
    public static SQLiteDatabase user_db;

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化屏幕的宽高
        WindowManager windowManager =  (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        screen_Height = outMetrics.heightPixels;
        screen_Width = outMetrics.widthPixels;

        //初始化Volley Http请求队列
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //初始化用户数据库
        UserDB_SQLiteOpenHelper userDB_sqLiteOpenHelper = new UserDB_SQLiteOpenHelper(getApplicationContext(), "user_db", null, 1);
        user_db = userDB_sqLiteOpenHelper.getWritableDatabase();
    }
}
