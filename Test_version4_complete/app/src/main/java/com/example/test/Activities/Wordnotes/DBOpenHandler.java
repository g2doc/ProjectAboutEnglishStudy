package com.example.test.Activities.Wordnotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public  class DBOpenHandler extends SQLiteOpenHelper {

    int version;  //数据库 版本

    public DBOpenHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.version = version;
    }

    /**
     *  数据库的工具类
     *  需要 继承自  SQLiteOpenHelper
     *  重写  onCreate \    onUpgrade   \  和 实现自己的 构造函数
     *
     * @param db
     */


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE dict(_id integer primary key autoincrement, word varchar(64) unique, explanation text, level int default 0, modified_time timestamp)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        if (oldVersion < 2){
            try {
//                db.execSQL("ALTER TABLE stu ADD COLUMN age int");
                Log.e("version", "version");
            }catch(Exception ex){

            }
        }

    }
}
