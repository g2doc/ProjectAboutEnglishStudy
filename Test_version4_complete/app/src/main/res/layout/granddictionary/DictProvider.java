package com.example.jing.granddictionary;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DictProvider extends ContentProvider{
    private DBOpenHandler dbOpenHandler;
    private SQLiteDatabase db;

    @Override
    public boolean onCreate()  {
        dbOpenHandler = new DBOpenHandler(getContext(), "dbWord.db3", null, 1);
        return (dbOpenHandler==null?false:true);
    }

    @Override
    public String getType(Uri uri){    // 返回本ContentProvider所提供数据的MIME类型
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String where,String[] whereArgs, String sortOrder) {
        db = dbOpenHandler.getReadableDatabase();
        Cursor cursor = db.query("dict", projection, where, whereArgs, null, null, sortOrder, null);
        return cursor;
    }
    @Override
    public Uri insert(Uri uri, ContentValues cv){
        System.out.println("===insert iscalled==="+cv.get("name")+sh(uri)); return uri;
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs){
        System.out.println("===delete is called==="+sh(uri)); return 1;
    }
    @Override
    public int update(Uri uri, ContentValues cv, String where,String[] whereArgs){
        System.out.println("===update is called==="+sh(uri));
        getContext().getContentResolver().notifyChange(uri, null);
        return 2;
    }

    private String sh(Uri uri){
        return "\r\n-----"+uri+"-----\r\n";
    }
}