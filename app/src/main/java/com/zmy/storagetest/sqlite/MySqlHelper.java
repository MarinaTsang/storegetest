package com.zmy.storagetest.sqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by zengmanyan on 2018/7/12.
 * 链接数据库辅助类
 */

public class MySqlHelper extends SQLiteOpenHelper {

    private Context mContext;

    public MySqlHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    public MySqlHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库
        db.execSQL("create table News(id integer primary key autoincrement ,topic text,content text ,price real,time integer)");

        db.execSQL("create table Category(id integer primary key autoincrement ,category text,num integer)");
        Toast.makeText(mContext,"create success",Toast.LENGTH_LONG).show();

    }

    /**
     * 升级数据库,构造方法中 版本号比之前的大即可执行onUpdate 方法。
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //如果数据库存在，先删除数据库，再创建，否则数据库存在时是不执行onCreate方法的
        db.execSQL("drop table if exists News");
        db.execSQL("drop table if exists Category");
        onCreate(db);
    }
}
