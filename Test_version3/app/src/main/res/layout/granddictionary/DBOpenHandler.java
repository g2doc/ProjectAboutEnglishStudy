package com.example.jing.granddictionary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by isszym on 2018/6/26.
 */

class DBOpenHandler extends SQLiteOpenHelper {
    int version;
    public DBOpenHandler(Context context, String name,
                         SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.version = version;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {// 当数据库创建时就用SQL命令创建一个表
        // 创建一个stu（学生）表：_id--主键（自动增长)，name(姓名),num（学号）和age（年龄）;
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
