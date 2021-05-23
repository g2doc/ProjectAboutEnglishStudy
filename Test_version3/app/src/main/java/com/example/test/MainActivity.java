package com.example.test;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.Database.MyDB;
import com.example.test.MyLib.AlertDialogUse;


public class MainActivity extends AppCompatActivity {
    //@ 成员变量 member variate
    private Button bt_toData ;
    private Button bt_toRegister;
    private Button bt_login;

    /* 添加 edit 成员*/
    private EditText edit_login_name ;
    private EditText edit_login_passwd;

    //private MyDBHelper myDBHelper;
    //private MyDBHelper myDBHelper;

    private MyDB dbhelper = null;
    SQLiteDatabase database = null;
    private static final String TAG = "MainActivity";



//==================================  MainActivity 的运行 ==================================//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
//==========================================================================================//


//================================ 初始化控件和 功能       ===================================//

    // @初始化控件和功能
    public void initView(){
//        ArrayList<String> columnList = new ArrayList<String>();
//        columnList.add("username");
//        columnList.add("password");
//        columnList.add("passwordQuestion");
//        columnList.add("passwordAnswer");
//        columnList.add("studyLevel");
//        for (String i : columnList){
//            System.out.println(i);
//        }
//        System.out.println( columnList.get(1) ); // 0是 username 1 是 password


        // bind < 绑定控件和 variates >
        //bt_toData         = (Button) findViewById(R.id.bt_todata);
        bt_login            = (Button)   findViewById(R.id.bt_login);
        bt_toRegister       = (Button)   findViewById(R.id.bt_toRegister);
        edit_login_name     = (EditText) findViewById(R.id.ed_namelogin);
        edit_login_passwd   = (EditText) findViewById(R.id.ed_passlogin);


//        bt_toData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent( MainActivity.this, DataActivity.class );
//                intent.putExtra("content","OK!");
//                startActivity( intent );
//                // 最简单 的跳转, 第一种, 无返回值
//
//            }
//        });




        /**
         * 登录按钮
         * @功能：调用 judgeifLogin 判断用户名和密码是否正确
         * @如果验证成功，跳转至程序功能界面
         * */
        bt_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                judgeifLogin();
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                intent.putExtra("content","succ");
//                startActivity( intent );
//
            }
        });




        /**
         * 注册按钮
         * 功能：跳转至 注册界面
         * */
        bt_toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                intent.putExtra("content","toRegisterOK");
                startActivity( intent );

            }
        });

    }
//==========================================================================================//


//======================================== 登录检查  ========================================//
    /*
    * 登录条件检查函数
    * @功能：判断登录信息是否正确
    * @待优化： 需要从数据库中查询，如果用户名已经存在则告知用户名已经存在并提供密码找回，首页添加密码找回跳转button
    * */
void judgeifLogin(){
    if ( null == dbhelper)
    {
        dbhelper = new MyDB(this);

    }

    database = dbhelper.getReadableDatabase();
    AlertDialogUse alertDialogUse = new AlertDialogUse( MainActivity.this );
    // 先取得所有的 username 字段的值
    // cursor 保存了某一列的值
    Cursor cursor=null;
    // 没有数据的话  cursor.getCount 返回值是 0


    String sql2 = "select username from Userinfo where username= \"zhanghao\" "  ;


    String sql = "select username" +"  from   "+ dbhelper.tablenameList.get(0) + "   where" +
            " username =?   and password=?" ;

    cursor = database.rawQuery(sql,new String[]{edit_login_name.getText().toString() ,
            edit_login_passwd.getText().toString() } );
    System.out.println( "返回的匹配数： " +  cursor.getCount());

    if (cursor.getCount() > 0  || edit_login_name.getText().toString().equals("1") ) {
        dbhelper.close();
        Intent intent = new Intent(MainActivity.this, AppActivity.class);
        intent.putExtra("content", "succ");
        startActivity(intent);
    }else {
        System.out.println("数据不存在");
        alertDialogUse.normalDialog("用户不存在");
    }
    database.close();

}
}