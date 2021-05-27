/*
    @ 数据库的 访问工具类
        实现  定义好的 增删改查 接口
        UserTools 里  私有成员  dbHelper

 */

package com.example.test.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.test.sqlite.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// UserTools 是对 UserService 接口的实现
public class UserTools implements  UserService {
    // 获得  helper 对象来 操作数据库
    private DBHelper dbHelper = null;

    public UserTools(Context context ){
        this.dbHelper = new DBHelper(context );

    }

    // query test
    @Override
    public void queryTest(Object params) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("t_users",null,null,null,
                null,null,null);
        if ( cursor.moveToFirst() ){
            do{
                // 遍历 cursor 对象, 取出数据并打印
                String username = cursor.getString(cursor.getColumnIndex("username"));
                System.out.println("/////" + cursor.getColumnIndex("username"));

                String pass = cursor.getString(cursor.getColumnIndex("pass"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                Log.d("MainActivity", "Username: " + username +"  pass: " +pass +"  id: " +id);
            }while ( cursor.moveToNext());
        }
        cursor.close();
    }

    /*
            @ 通过 id 查找到 对应的 User 用户信息
         */
    @Override
    public User findUserById(Integer id ) {
        User userTemp = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // 用 游标 Cursor 接收 从 数据库检索到的 数据
        Cursor cursor = db.rawQuery("select * from t_users where id = ?",
                new String[] {id.toString()});
        try{
           if (cursor.moveToFirst() ){ // 先移动到 first 第一行, 依次取出数据
                userTemp = new User();

           }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if ( db != null){
                db.close();
            }
        }

        return userTemp;
    }



    // add User
    @Override
    public boolean addUser(Object[] params) {
        boolean flag = false;
        SQLiteDatabase db = null;
        //String sql = "insert into t_users (username, pass) values(?,?)";
        //db.execSQL( sql, new Object[]{user.getUsername(), user.getPass() }  );   写法参考
        try {
            // ?  标识 占位符, 所以要需要传入所有的占位符的值,传入值有这个方法中的参数传递
            String sql = "insert into t_users(username,pass) values(?,?)";
            db = dbHelper.getWritableDatabase();   //实现对 数据库的 写的操作
            db.execSQL(sql, params );
            flag = true;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if( db != null ){
                db.close();
            }
        }
        return flag;
    }

    @Override
    public boolean updateUser(Object[] params) {
        boolean flag = false;
        SQLiteDatabase db = null;
        try {
            String sql = " update t_users set username = ?,pass = ? where id = ?";
            db.execSQL(sql, params );
            flag = true;

        }catch (Exception e){
            e.printStackTrace();

        }
        finally {
            if( db != null){
                db.close();
            }

        }
        return flag;
    }


    // delete User
    @Override
    public boolean deleteUser(Object[] params) {
        boolean flag = false;
        SQLiteDatabase db = null;
        try{
            String sql = "delete from t_users where id = ?";
            db = dbHelper.getWritableDatabase();
            db.execSQL(sql, params );

            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db != null){
                db.close();
            }
        }
        return flag;
    }

    @Override
    public Map<String, String> viewUser(String[] selectArgs) {
        return null;
    }

    @Override
    public boolean getIdByName(Object[] params) {
        SQLiteDatabase db = null;
        db = dbHelper.getReadableDatabase();
        try{
            Cursor cursor;
            String sql = "select id from t_users where username = ?";
            db.execSQL(sql, params );


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if( db != null){
                db.close();
            }
        }
        return false;
    }


    // show all
    @Override
    public List<Map<String, String>> listUserMaps(String[] selectArgs) {
        List<Map<String, String>> list = new ArrayList<Map<String,String>>();
        SQLiteDatabase db = null;
        try {
            String sql = "select * from t_users "; //这个是查询表中所有的内容，所以就不需要传入的这个参数值了
            db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, selectArgs);
            int colums = cursor.getColumnCount();

            while(cursor.moveToNext()) {
                Map<String, String> map = new HashMap<String, String>();
                for(int i = 0; i < colums; i++) {
                    String cols_name = cursor.getColumnName(i);
                    String cols_value = cursor.getString(cursor.getColumnIndex(cols_name));
                    if(cols_name == null) {
                        cols_value = "";
                    }
                    map.put(cols_name, cols_value);
                }
                list.add(map);
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if( db != null){
                db.close();
            }
        }
        return list;
    }


    }

