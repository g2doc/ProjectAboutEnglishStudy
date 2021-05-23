package com.example.test.sqlite;
/*
用户信息类
 */

public class User{
    // 表名
    public static final String TABLE = "NiuNiu";

    //表的 各域名
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";

    // 属性
    public int user_id;
    public String user_name;





    private int id;
    private String username;
    private String pass;

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
}