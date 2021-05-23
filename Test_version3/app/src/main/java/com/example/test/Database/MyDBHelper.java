package com.example.test.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.test.Database.DBUser;


import android.database.sqlite.SQLiteOpenHelper;

/*
    android 项目中 数据库的位置  Device File Explorer /  data / data / com.xxxxxx.***  /database
 */

/*
    extends   SQLiteOpenHelper  必须 重写  onCreate  和  onUpgrade  方法
 */

// ======================================== MyDBHelper =================================================//
//---> 更新数据库的版本号 时 ，此时会执行 onUpgrade()方法
public class MyDBHelper extends SQLiteOpenHelper {
    private static final int version = 1;            // final 不可以再修改
    private static final String name = "User.db";   //数据库名称还是要  带有 .db 后缀的
    private static final String table_name = "test";

    //必须要有构造函数
    /*
    public DBHelper(Context context, String name, CursorFactory factory,
                    int version) {
        super(context, name, factory, version);

     */
    // 一般 factory 选 null
    //super(context,DATABASE_NAME,null,DATABASE_VERSION );
    public MyDBHelper(Context context ){
        super( context, name, null,version );
    }



    @Override
    /*
    当数据库 创建的时候, 是第一次被执行, 完成该数据库的  表 的创建!!!!!!!!!!!!  表

    覆写 onCreate 方法, 当数据库 创建时 就用 SQL 命令创建一个表

    创建 1 个 user 表, id 主键 自动增长,  字符类型的 username 和 pass
     */
    public void onCreate( SQLiteDatabase db){


        DBUser dbuser = new DBUser();

        String sql3="create table Mytable_1( id integer primary key autoincrement," +
                "username varchar(200), pass varchar(200) , money   int )";
/*
        String sql2 = "create table" + table_name + "(" + "id integer primary key autoincrement"
                + ","
                + dbuser.mColumn.username            + " varchar(10) "
                + ","
                + dbuser.mColumn.password            + " varchar(10) "
                + ","
                + dbuser.mColumn.passwordQuestion    + " varchar(20) "
                + ","
                + dbuser.mColumn.passwordAnswer      + " varchar(20) "
                + ","
                + dbuser.mColumn.level               + " varchar(20) " + " ) "
                ;

 */
        String sql = "create table  ssssss(id integer primary key autoincrement," + "username varchar(10) ) ";

        String sqlr="create table swsw( id integer primary key autoincrement," +
                "username varchar(200), password varchar(200)  )";

        // 输出 创建数据库的 日志信息
        Log.i("TAG","数据库   创建");
        db.execSQL( sql3 );

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
        Log.i("TAG","数据库 更新");


        // 如果旧表 存在 , 删除, 所以数据就会消失

        //db.execSQL("drop table if exists " + User.TABLE );
        //String sql="alter table aaaa add address varchar(100)";
        //db.execSQL(sql );

        // 再次 创建表
        //onCreate( db );


    }
}

