package com.example.test.Activities.Wordnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DicDb {
    private DBOpenHandler dbOpenHandler;
    public DicDb(Context context){
        this.dbOpenHandler = new DBOpenHandler(context, "dbWord.db3", null, 1);
    }

    public Uri insert(Uri uri, ContentValues cv){
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        db.insert("dict", null, cv);
        db.close();
        return uri;
    }

    public int update(Uri uri, ContentValues cv, String where, String[] whereArgs){
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        int ret = db.update("dict", cv, where, whereArgs);
        db.close();
        return ret;
    }

    public int delete(Uri uri, String where, String[] whereArgs){
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        int ret = db.delete("dict", where, whereArgs);
        db.close();
        return ret;
    }

    public Cursor query(Uri uri, String[] projection, String where, String[] whereArgs, String sortOrder){
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        Cursor cursor = db.query("dict", projection, where, whereArgs, null, null, sortOrder, null);
//        db.close();
        return cursor;
    }
    //////
    //还需要定义操作函数
}