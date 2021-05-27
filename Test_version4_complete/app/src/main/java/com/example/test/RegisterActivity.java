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
    同时应该包含数据库的 操作, 因为要注册 register
 */

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.Database.DBUser;
import com.example.test.Database.MyDB;
import com.example.test.MyLib.AlertDialogUse;

import java.util.ArrayList;


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
    private Button bt_show;

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

    private TextView  textshow;
    private boolean ifRegisterOk;
    private static final String TAG = "AddActivity";

    private AlertDialogUse alertDialogUse;
    //private MyDBHelper myDBHelper;


    public  DBUser     dbUser=null;
    private MyDB mMyDBHelper = null;
    private SQLiteDatabase database = null;
    ArrayList<DBUser> userArrayList = null;




    void InitView() {
        alertDialogUse = new AlertDialogUse( RegisterActivity.this );
        userArrayList = new ArrayList<DBUser>();
        //showProgressDialog();
        //showListDialog();
        //showNormalDialog();
        ifRegisterOk = false;// 默认把注册成功标志为 置为 false
        ed_pass              = (EditText) findViewById(R.id.edit_addpass);
        ed_pass2             = (EditText) findViewById(R.id.edit_addpassrepeat);
        ed_username          = (EditText) findViewById(R.id.edit_addname);
        ed_passQuestion      = (EditText) findViewById(R.id.edit_addpassques);
        ed_passAnswer        = (EditText) findViewById(R.id.edit_addpassans);
        //textshow              = (TextView) findViewById(R.id.textshow);


        bt_clickregister     = (Button)   findViewById(R.id.bt_clickregister);


        //bt_show = (Button) findViewById(R.id.bt_show);

//        bt_show.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectDatabase();
//            }
//        });

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
                //judgeIfRegister();
                if( judgeIfRegister() == true )
                {
                    alertDialogUse.normalDialog("注册成功,请返回登录");
                    //UserService service = new UserTools(RegisterActivity.this);
                    //boolean flag = service.addUser(new Object[]{username, pass});   //这是一个 数组
                    dbUser = new DBUser();
                    dbUser.setUsername(ed_username.getText().toString());
                    dbUser.setPassword(ed_pass.getText().toString());
                    dbUser.setPasswordQuestion(ed_passQuestion.getText().toString());
                    dbUser.setPasswordAnswer(ed_passAnswer.getText().toString());
                    insertDatabase(dbUser);
                    //runToLogin();
                }
            }
        });
    }

    // judge 判断
    boolean  judgeIfRegister(){
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
        if(username.length() > 6 && pass.length()>0 && pass2.length()>0 && pass2.equals(pass)){
            return true;
        }
        return false;
        //showDialog("请完善注册信息");
    }

    void runToLogin(){
        Intent intent11 = new Intent(RegisterActivity.this, MainActivity.class);
        intent11.putExtra("intent", "add ok");
        startActivity(intent11);
    }

    //
    private void  createDB() {
        // 1 先创建或打开数据库
        mMyDBHelper = new MyDB(this);
        /*
         * 调用getWritabelDatabase方法或者
         * getReadableDatabase方法时，如果数据库文
         * 件中不存在（注意一个数据库中可以存在多个表格），
         * 那么会回调MyHelper类的onCreate方法新建一个数据库文
         * 件并且在这个数据库文件中新建一
         * 个book表格
         */
        mMyDBHelper.getWritableDatabase();
    }


    /* 向数据库插入新数据 */
    private void insertDatabase(DBUser dbUser ){
        if( null == mMyDBHelper ){
            mMyDBHelper = new MyDB(this);
        }
        database = mMyDBHelper.getWritableDatabase();
        // 安卓把 SQL 语句给封装了起来, 通过 ContentValues 类的对象来保存数据库的数据
        //Object[] bindArgs = new Object[0];   //Object[]  bindArgs 是对象数组
        // 为什么  可以任何类型数据都可以存入  Object[] ?? 都可以向上转型???
        String sql = "insert into "+ mMyDBHelper.tablenameList.get(0)+"(username," +
                "password,passwordQuestion,passwordAnswer,studyLevel) values(?,?,?,?,?)";
        Object[] bindArgs = {dbUser.getUsername(),dbUser.getPassword(),
                dbUser.getPasswordQuestion(),dbUser.getPasswordAnswer(), null };
        database.execSQL(sql, bindArgs );
        database.close();
    }


    // 查询数据
    private void selectDatabase(){
        if( null == mMyDBHelper ){
            mMyDBHelper = new MyDB(this);
        }
        database = mMyDBHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from userinfo",null);
        if( cursor!=null && cursor.getCount() > 0 ){
            while( cursor.moveToNext()){
                // columnindex 代表的是 列的索引
                DBUser dbUser = new DBUser();
                dbUser.setUsername(cursor.getString(1));
                dbUser.setPassword(cursor.getString(2));
                dbUser.setPasswordQuestion(cursor.getString(3));
                dbUser.setPasswordAnswer(cursor.getString(4));
                //dbUser.setLevel(cursor.getString(5));
                userArrayList.add(dbUser);
//                String username = cursor.getString(1);
//                String password = cursor.getString(2);
//                String passwordQues = cursor.getString(3);
//                String passwordAnsw = cursor.getString(4);
//                String studyLevel = cursor.getString(5);
            }
        }
        ShowUser();
        // 数据库用完 一定要记得关闭
        database.close();
    }

    // ArrayList 的遍历方法 4 种
    void ShowUser(){
        String str="";
        for ( DBUser dbUser : userArrayList){
            System.out.println("name: "+dbUser.getUsername()+" "+
                    "pass: "+dbUser.getPassword()+" "+
                    "passQu: "+dbUser.getPasswordQuestion()+" "+
                    "passAns: "+dbUser.getPasswordAnswer()+" "+
                    "studyLev: "+dbUser.getstudyLevel());
            str += "name: "+dbUser.getUsername()+" "+
                    "pass: "+dbUser.getPassword()+" "+
                    "passQu: "+dbUser.getPasswordQuestion()+" "+
                    "passAns: "+dbUser.getPasswordAnswer()+" "+
                    "studyLev: "+dbUser.getstudyLevel()  ;
            textshow.setText(str);
        }
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

