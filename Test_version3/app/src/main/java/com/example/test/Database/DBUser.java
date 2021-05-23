package com.example.test.Database;
/*
        用以描述 数据库所操作的对象信息,
        方便 建表 和 增删 改 查
        以后修改 表字段内容直接 修改   DBUser 的成员变量
 */

import android.content.Context;

/*
    设计：
    java 数组过于简陋， 使用 之前必须指定长度
        不可以 动态扩容
        没有 迭代器， 没有方便的 增删改查 接口
    想要将 sql 数据库的 字段 column 存放到容器中
    采用 java 的容器类
    List 列表类

    
 */

import java.util.ArrayList;


public class DBUser {
    //==================================== members =========================================//
    // column  列，， 即 sql 的表字段
    private String username;
    private String password;
    private String passwordQuestion;
    private String passwordAnswer;
    private String studyLevel;            //表示 User 的水平

    // 定义 ArrayList<String >  去表示 sql 数据库的 column ，即 表字段
    ArrayList<String> columnList;


    public DBUser(){
        //mColumn.level           =this.level;
        // 初始化 columnList
        columnList = new ArrayList<String>();
        columnList.add("username");
        columnList.add("password");
        columnList.add("passwordQuestion");
        columnList.add("passwordAnswer");
        columnList.add("studyLevel");
//        for (String i : columnList){
//            System.out.println(i);
//        }
//        System.out.println( columnList.get(1) );
    }


    // 用 public 方法 修改 private 值，， 为了安全
    //==================================== set =========================================//
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setPasswordQuestion(String passwordQuestion) {
        this.passwordQuestion = passwordQuestion;
    }
    public void setPasswordAnswer(String passwordAnswer) {
        this.passwordAnswer = passwordAnswer;
    }
    public void setLevel(String level) {
        this.studyLevel = level;
    }

    //==================================== get =========================================//
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getPasswordQuestion() {
        return passwordQuestion;
    }
    public String getPasswordAnswer() {
        return passwordAnswer;
    }
    public String getstudyLevel() {
        return studyLevel;
    }
}
