package com.example.test;
/*
    description: this Activity is for registering new users
                succ run to MainActivity
                err  show   dialog

 */
// adb 调试起不来 https://blog.csdn.net/lee_shaoyang/article/details/108474113
// https://blog.csdn.net/qq_42293487/article/details/83864078
// https://blog.csdn.net/duanjie924/article/details/80181603
// 草原来是 手机助手占用了 我的 adb

/*
    用到了 dialog 对话框 用来 做 用户注册失败的消息提示
    空间和函数命名---> 见名知意

 */

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.test.Database.DBUser;
import com.example.test.sqlite.UserService;
import com.example.test.sqlite.UserTools;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.MyLib.AlertDialogUse;


public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        InitView();
    }




    //======================================  members ============================================//
    private Button bt_add;
    private Button bt_clickregister;

    private String pass;
    private String pass2;
    private String username;
    private String passQuestion;
    private String passAnswer;

    private int addFlag;

    private EditText ed_pass;
    private EditText ed_pass2;
    private EditText ed_username;
    private EditText ed_passQuestion;
    private EditText ed_passAnswer;

    private TextView  textadd;
    private boolean ifRegisterOk;
    private static final String TAG = "AddActivity";

    private AlertDialogUse alertDialogUse;


    //private MyDBHelper myDBHelper;
    public  DBUser     dbUser;



    void InitView() {

        alertDialogUse = new AlertDialogUse( RegisterActivity.this );
        dbUser = new DBUser();
        // < 初始化 DB >
        System.out.println("3333333333333333333333333333333333333");
        /*
        myDBHelper = new MyDBHelper(RegisterActivity.this,
                null,
                1
                );
        myDBHelper.getWritableDatabase();   //得到可写数据库

*/


        //showProgressDialog();
        //showListDialog();
        //showNormalDialog();
        ifRegisterOk = false;           // 默认把注册成功标志为 置为 false
        ed_pass              = (EditText) findViewById(R.id.edit_addpass);
        ed_pass2             = (EditText) findViewById(R.id.edit_addpassrepeat);
        ed_username          = (EditText) findViewById(R.id.edit_addname);
        ed_passQuestion      = (EditText) findViewById(R.id.edit_addpassques);
        ed_passAnswer        = (EditText) findViewById(R.id.edit_addpassans);
        textadd              = (TextView) findViewById(R.id.textadd);

        bt_clickregister     = (Button)   findViewById(R.id.bt_clickregister);



        bt_clickregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                    when the bt_bt_clickregister   is pressed,
                    if success:
                        run to Login Activity
                    if error:
                        show the error dialog
                 */

                // test  waitwhileDialog
                //alertDialogUse.waitWhileDialog(100,"测试进度条",100);


                pass            = ed_pass.getText().toString();
                pass2           = ed_pass2.getText().toString();
                username        = ed_username.getText().toString();
                passQuestion    = ed_passQuestion.getText().toString();
                passAnswer      = ed_passAnswer.getText().toString();

                // 父类 引用 指向 子类对象,
                // 向上转型：父类引用类型指向了子类的实例对象，此时无法使用子类里的成员变量以及方法。
                judgeIfRegister();
                if( ifRegisterOk == true )
                {
                    UserService service = new UserTools(RegisterActivity.this);

                    boolean flag = service.addUser(new Object[]{username, pass});   //这是一个 数组
                    if ( flag == true ){
                        runToLogin();
                    }
                    service.queryTest(null);

                }


                // clear editText
                //edit_pass.setText("");
                //edit_username.setText("");

                //showDialog();
                //ifRegisterOk = true;
                //runToMain();
            }
        });
    }

    // judge 判断
    void judgeIfRegister(){
        String mess;
        if ( username.length() > 6 && pass.length() >0 && pass2.length()>0 && pass2.equals(pass) )
        {
            ifRegisterOk = true;
        }
        else {
            if (username.length() > 6) {  // 就肯定是另外 3 个的问题
                if (pass.length() > 0) {  //说明只能是 pass2 =0 或者 pass2 !=  pass
                    mess = "密码确认失败";
                    alertDialogUse.normalDialog(mess);
                } else {
                    mess = "密码不能为空";
                    alertDialogUse.normalDialog(mess);
                }
            } else {
                mess = "请输入用户名长度>6";
                alertDialogUse.normalDialog(mess);
            }
        }
        //showDialog("请完善注册信息");
    }





    void runToLogin(){
        Intent intent11 = new Intent(RegisterActivity.this, LoginActivity.class);
        intent11.putExtra("intent", "add ok");
        startActivity(intent11);
    }



     void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(RegisterActivity.this);
        normalDialog.setIcon(R.drawable.background);
        normalDialog.setTitle("我是一个普通Dialog");
        normalDialog.setMessage("你要点击哪一个按钮呢?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });

        normalDialog.show();    // 显示
    }



    /**
     * 列表Dialog
     */
    private void showListDialog(){
        final String[] items = {"我是1","我是2","我是3"};
        AlertDialog.Builder listDialog = new AlertDialog.Builder(RegisterActivity.this);
        listDialog.setIcon(R.drawable.head);//图标
        listDialog.setTitle("我就是个列表Dialog");

        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(RegisterActivity.this,"点击了"+items[which],Toast.LENGTH_SHORT).show();
            }
        });
        listDialog.show();
    }

    private void showProgressDialog()  {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("我是个等待的Dialog");
        progressDialog.setMessage("等待中");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);     //使得屏幕不可点击下载时间完成后主动调用函数 关闭该Dialog
        progressDialog.show();
        new Thread(MyService);
        progressDialog.dismiss();
    }

    private Runnable MyService = new Runnable() {
        @Override
        public void run() {
            // 访问网络或者 数据库等费时的工作

        }
    };




}

