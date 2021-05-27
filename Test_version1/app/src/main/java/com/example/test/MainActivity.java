package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.test.Database.DBUser;
import com.example.test.Database.MyDB;
import com.example.test.Database.MyDBHelper;
import com.example.test.sqlite.DBHelper;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    //@ 成员变量 member variate
    private Button bt_toData ;
    private Button bt_toRegister;
    private Button bt_login;

    private EditText    user;
    private EditText    passwd;
    private ImageView   leftArm;
    private ImageView   rightArm;
    private ImageView   leftHand;
    private ImageView   rightHand;



    //private MyDBHelper myDBHelper;
    //private MyDBHelper myDBHelper;
    private MyDB myDBHelper;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    // @初始化控件和功能
    public void initView(){
        ArrayList<String> columnList = new ArrayList<String>();
        columnList.add("username");
        columnList.add("password");
        columnList.add("passwordQuestion");
        columnList.add("passwordAnswer");
        columnList.add("studyLevel");
        for (String i : columnList){
            System.out.println(i);
        }
        System.out.println( columnList.get(1) ); // 0是 username 1 是 password


        //myDBHelper = new MyDBHelper(MainActivity.this);
        //myDBHelper = new MyDBHelper(MainActivity.this);

        myDBHelper = new MyDB(MainActivity.this);
        myDBHelper.getWritableDatabase();   //得到可写数据库



        // bind < 绑定控件和 variates >
        bt_toData       = (Button) findViewById(R.id.bt_todata);
        bt_login        = (Button) findViewById(R.id.bt_login);
        bt_toRegister   = (Button) findViewById(R.id.bt_toRegister);

        //user    = findViewById(R.id.ed_user);
        //passwd  = findViewById(R.id.ed_passwd );

        //@ 监听内容改变 --> 控制按钮的点击状态
        //user.addTextChangedListener( (TextWatcher) this);
        //passwd.addTextChangedListener((TextWatcher) this);

/*
        //@ 监听 editText 的焦点变化, 控制是否需要捂住眼睛
        //@ 实现 ed_passwd 焦点变化的处理函数
        passwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if ( hasFocus == true ){
                    // 捂住眼睛
                    close();
                }else{
                    // 打开
                    open();
                }
            }
        });

*/
        bt_toData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, DataActivity.class );
                intent.putExtra("content","OK!");
                startActivity( intent );
                // 最简单 的跳转, 第一种, 无返回值

            }
        });

        bt_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("content","succ");
                startActivity( intent );
            }
        });


        bt_toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                intent.putExtra("content","toRegisterOK");
                startActivity( intent );
            }
        });




    }



}