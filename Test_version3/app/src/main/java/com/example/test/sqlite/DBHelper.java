package com.example.test.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;


import android.database.sqlite.SQLiteOpenHelper;

/*
    @param context
            上下文
    @param name
            数据库名
    @param factory
            可选的数据库  游标 工厂类, 当查询 query 被提交时,该对象会被调用来实例化 1 个游标,默认null

            用来更新或者创建  数据库 使用
 */


/**
 * 查看SQLiteOpenHelper api文档的，它的构造方法
 * public SQLiteOpenHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
 * 创建一个帮助类的对象来创建，打开，或者管理一个数据库，这个方法总是会快速的返回，
 * <span style="background-color: rgb(255, 255, 0);">这个数据库实际上是没有被创建或者打开的，直到getWritableDatabase() 或者 getReadableDatabase() 方法中的一个被调用时才会进行创建或者打开</span>
 * 参数：
 *   context ： 上下文对象，用来打开或者创建数据库
 *   name ： 数据库文件的名称，如果是创建内存中则为null ，
 *   factory ： 用来创建游标对象，默认的是为null
 *   version ： 数据库的版本号(以版本数字号1开始)，如果数据库比较旧，就会用 onUpgrade(SQLiteDatabase, int, int) 方法来更新数据库，
 *       why？？<span style="background-color: rgb(255, 255, 102);">如果数据库比较新，就使用 onDowngrade(SQLiteDatabase, int, int)  方法来 回退数据库</span>
 * 【注意】 : 我们声明完这个构造方法之后，包括初始化它的名称 和 版本之后，实际上它还是没有马上被创建起来的。
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final int version = 1;  // final 不可以再修改

    //更新数据库的版本号 时 ，此时会执行 onUpgrade()方法

    // 数据库名称
    private static final String name = "N.db";   // 还是要  带有 后缀的

    //必须要有构造函数
    /*
    public DBHelper(Context context, String name, CursorFactory factory,
                    int version) {
        super(context, name, factory, version);

     */
        // 一般 factory 选 null
        //super(context,DATABASE_NAME,null,DATABASE_VERSION );
    public DBHelper( Context context ){
        super( context, name, null,version );
    }



    @Override
    /*
    当数据库 创建的时候, 是第一次被执行, 完成该数据库的  表 的创建!!!!!!!!!!!!  表

    覆写 onCreate 方法, 当数据库 创建时 就用 SQL 命令创建一个表

    创建 1 个 user 表, id 主键 自动增长,  字符类型的 username 和 pass
     */
    public void onCreate( SQLiteDatabase  db){
        // 创建数据  表!
        String CREATE_TABLE_NIU = "create table " + User.TABLE + "" +
                "(" + User.KEY_ID + "INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + User.KEY_NAME + "TEXT " + ")" ;


        String sql="create table mmmmm( id integer primary key autoincrement," +
                "username varchar(200), pass varchar(200) )";

        // 输出 创建数据库的 日志信息
        Log.i("TAG","create Database ------------>"+"old");
        db.execSQL( sql );

    }



    /**
     * onUpgrade() 方法是在什么时候被执行呢？
     * 查看API文档中 onUpgrade()介绍
     *   当数据库需要升级时调用这个方法[在开发过程中涉及到数据库的设计存在缺陷的时候进行升级，不会损坏原来的数据]，这种实现方式会使用方法来减少表，或者增加表，或者做版本更新的需求。
     * 在这里就可以执行 SQLite Alter语句了，你可以使用 ALTER TABLE 来增加新的列插入到一张表中，你可以使用 ALTER TABLE 语句来重命名列或者移除列，或者重命名旧的表。
     * 你也可以创建新的表然后将旧表的内容填充到新表中。
     *   此方法会将事务之内的事件一起执行，如果有异常抛出，任何改变都会自动回滚操作。
     *   参数：
     *     db ： 数据库
     *     oldVersion ： 旧版本数据库
     *     newVersion ： 新版本数据库
     * 【注意】：这里的删除等操作必须要保证新的版本必须要比旧版本的版本号要大才行。[即 Version 2.0 > Version 1.0 ] 所以这边我们不需要对其进行操作。
     */

    // 更新 数据库 时执行该方法
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion){
        // 输出 更新数据库的 日志信息
        Log.i("TAG","update Database ------------->");


        // 如果旧表 存在 , 删除, 所以数据就会消失

        //db.execSQL("drop table if exists " + User.TABLE );
        String sql="alter table t_users add address varchar(100)";
        db.execSQL(sql );

        // 再次 创建表
        onCreate( db );


    }
}
