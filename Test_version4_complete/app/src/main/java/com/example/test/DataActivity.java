package com.example.test;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.Database.DBUser;
import com.example.test.Database.MyDBHelper;
import com.example.test.Database.OperateDB;
import com.example.test.sqlite.UserService;
import com.example.test.sqlite.UserTools;

import java.util.List;
import java.util.Map;

/*
    当前数据库接口还需要修改
 */
public class DataActivity extends AppCompatActivity {
    private static final String TAG = "DataActivity";


    //
    Button bt_add, bt_show, bt_find, bt_delete, bt_clear ;
    TextView text_show, text_mess;
    EditText edit_username, edit_pass;

    String username, pass;
    int flagCanAdd = 0;

    //==============================================================================================//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        bindView();

        // 为了显示 长文本 所作的修改
        TextView contentTV = (TextView) findViewById(R.id.text_show);
        contentTV.setMovementMethod(ScrollingMovementMethod.getInstance());
    }


    public void bindView(){
        bt_add      = (Button) findViewById(R.id.button_add);
        bt_show     = (Button) findViewById(R.id.button_show);
        bt_find     = (Button) findViewById(R.id.button_find);
        bt_delete   = (Button) findViewById(R.id.button_delete);
        bt_clear    = (Button) findViewById(R.id.button_clear);
        text_show       = ( TextView) findViewById(R.id.text_show);
        text_mess       = ( TextView) findViewById(R.id.text_mess);
        edit_username   = ( EditText) findViewById(R.id.edit_username);
        edit_pass       = ( EditText) findViewById(R.id.edit_pass );

        // 创建 数据库 对象
        MyDBHelper mdbHelper = new MyDBHelper(DataActivity.this );
        //调用 getWritableDatabase()或者 getReadableDatabase()其中一个方法将数据库建立
        mdbHelper.getWritableDatabase();

        /*
            @ bt_add
         */
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = edit_username.getText().toString();
                pass     = edit_pass.getText().toString();

            // 父类 引用 指向 子类对象,
            // 向上转型：父类引用类型指向了子类的实例对象，此时无法使用子类里的成员变量以及方法。
            /*

            UserService service = new UserTools(DataActivity.this) ;
            if( username.length() > 6 ){
                if( pass.length() > 0 ) {
                    boolean flag = service.addUser(new Object[] { username , pass });   //这是一个 数组
                    Log.i(TAG,"===>" + flag );
                }else{
                    text_mess.setText(" 密码不能为空 ");
                }

            }else{
                text_mess.setText("请输入用户名长度 > 6 ");
            }

             */
                OperateDB mop = OperateDB.getOperateDB();
                DBUser muser = new DBUser();
                if( username.length() >6 ){
                    if( pass.length() != 0 ){
                        muser.setUsername(username);
                        muser.setUsername(pass);
                        mop.addDBUser(muser);

                    }else{
                        text_mess.setText(" 密码不能为空 ");
                    }

                }else{
                    text_mess.setText("请输入用户名长度 > 6 ");
                }
                // clear editText
                edit_pass.setText("");
                edit_username.setText("");
                //service.queryTest( null );
            }
        });
//==============================================================================================//

        /*
            @ bt_show
        */
        bt_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                username = edit_username.getText().toString();
                pass    = edit_pass.getText().toString();
                text_show.setText( username + pass );

                 */
                //查询多条记录，这里我们不需要传递参数，所以可以参数可以置为null
                UserService service = new UserTools(DataActivity.this);
                List<Map<String, String>> list = service.listUserMaps(null);
                Log.i(TAG,"----查询所有记录===>"+list.toString() );
                text_show.setText(list.toString() );
            }
        });


        /*
            @ bt_find
         */
        /*
        bt_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_show.setText(" find OK\r\n");

                // 测试 rawQuery()
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery("select * from t_users where id = ?",
                        new String[]{"18"});

                String username = cursor.getString( (cursor.getColumnIndex("username"))  ) ;
                System.out.println("--------------"+ username );

            }
        });

         */


        /*
            @ bt_delete
            删除 用户信息
         */
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserService service = new UserTools(DataActivity.this );
                int id = Integer.valueOf(edit_username.getText().toString() ).intValue(); //用不用 .intValue
                boolean flag = service.deleteUser(new Object[] {id} );


               Log.i(TAG,"Delete====> id = " + id + flag );
            }
        });


                /*
            @ bt_clear
         */
        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_show.setText(" clear OK\r\n");

            }
        });

    }


}