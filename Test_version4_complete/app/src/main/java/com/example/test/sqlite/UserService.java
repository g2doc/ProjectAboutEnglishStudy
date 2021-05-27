/*
    @ 用 execSQL( String sql, Object[] bindArgs) 方法定义 增删改 操作  DML
        db.execSQL( sql, new Object[]{user.getUsername(), user.getPass() }  );

        !!!!!!!  定义 增删改查 接口,  面向接口 编程

        学习 java List 和 Map 的用法!!!
 */
package com.example.test.sqlite;

import java.util.List;
import java.util.Map;

public interface  UserService{
    public boolean addUser( Object[] params );

    public boolean deleteUser( Object[] params);

    public boolean updateUser( Object[] params );

    public boolean getIdByName( Object[] params );

    public User findUserById( Integer id );

    public void queryTest(Object params);

    // 使用 Map< String, String > 做一个封装,
    // 比如 查询数据库的时候  返回的单条记录
    public Map<String, String> viewUser(String[]  selectArgs );

    // 使用 List<Map<Stirng, String> > 做一个 封装
    // 比如查询 数据库的时候  返回的多条记录
    public List< Map<String, String> > listUserMaps( String[] selectArgs);



}