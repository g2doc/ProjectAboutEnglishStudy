package com.example.test.Database;

import com.example.test.Database.DBUser;

import java.util.ArrayList;

/*
    用于  和 后台数据库的 交互
 */



public class OperateDB {
    private static OperateDB          mOperateDB;         // 用于 全局 使用
    private static ArrayList<DBUser>  mUserList;


    //======================================= construction ====================================//
    private OperateDB(){
        mUserList = new ArrayList<DBUser>( );    //初始化   mUserList

    }


    //======================================= get OperateDB  =================================//
    public static OperateDB getOperateDB(){
        if ( mOperateDB == null )
        {
            mOperateDB = new OperateDB();
        }
        return mOperateDB;
    }
    //======================================= get ArrayList  =================================//

    public static ArrayList<DBUser>  getUserList(){
        return mUserList;
    }

    public void deleteDBUser(DBUser dbUser ){

    }


    public void addDBUser(DBUser dbuser){
        mUserList.add(dbuser);
    }


    public DBUser getDBUser( String username ){
        for( DBUser dbuser : mUserList ){  //遍历 ArrayList
            if( dbuser.getUsername().equals(username) ){
                return dbuser;
            }
        }
        return null;   //没有 找到 对应的 dbuser ， 即 用户不存在
    }
}
