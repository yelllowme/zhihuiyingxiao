package com.yunshangkeji.zhihuiyingxiao.a_part_common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yellow on 2016/11/18.
 * 用户数据库OpenHelper
 */

public class UserDB_SQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_USER_INFO = "create table user_info ("
            + "id integer primary key autoincrement,"
            + "account text,"
            + "password text,"
            + "access_token text,"
            + "islogin integer,"
            + "role integer)";

    public UserDB_SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
