package com.example.test;
/*
    description: this Activity is for registering new users
                 and add user successfully  run to MainActivity
 */
// adb 调试起不来 https://blog.csdn.net/lee_shaoyang/article/details/108474113
// https://blog.csdn.net/qq_42293487/article/details/83864078
// https://blog.csdn.net/duanjie924/article/details/80181603
// 草原来是 手机助手占用了 我的 adb


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private Button bt_tofunction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Init();




    }

    void Init(){
        bt_tofunction = (Button) findViewById(R.id.bt_loginok);

        bt_tofunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, EnglishActivity.class);
                intent.putExtra("content","succ");
                startActivity( intent );



            }
        });

    }





    /*
        判断 登陆信息是否正确
     */

    boolean judgeifLogin(){
        return true;

    }








}