package com.example.test;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/*
    功能: 用于记录每天查询单词 并 添加到本地词库
    词库构建
    tableName : words
    id integer primary key 主键 id || varchar(50) word-zh  || varchar(100) word-en || varchar(100) other


 */

public class WordRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_record);
    }


    void Init(){

    }
}