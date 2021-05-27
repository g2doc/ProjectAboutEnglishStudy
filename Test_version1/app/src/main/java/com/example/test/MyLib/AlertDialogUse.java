package com.example.test.MyLib;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.test.R;
import com.example.test.RegisterActivity;

import java.lang.InterruptedException;
import java.util.ArrayList;
import java.util.PrimitiveIterator;




/*
    自定义 AlertDialog 的使用类
    拥有好的兼容性，可供其他项目使用

 */

public class AlertDialogUse{
    private Context useContext;
    public AlertDialogUse(Context param ){
        this.useContext = param;
    }

    /*
        进度条 Dialog
        description:      等待一段加载时间之后消失
        params:
            useContext: 使用该 waitWhileDialog 的 Activity 的 context
            progressSpeed  是进度条的加载速度
            由新开辟线程里的  Thread.sleep( 1000 );  决定， 单位是 ms
     */
    public void waitWhileDialog(int progressMax, String title, int progressSleepTime )
    {
        final ProgressDialog progressDialog = new ProgressDialog( useContext );
        progressDialog.setProgress(0);  //设置初始进度
        progressDialog.setIcon(R.drawable.ic_launcher_background );
        progressDialog.setTitle(title );
        progressDialog.setProgressStyle( ProgressDialog.STYLE_HORIZONTAL );  //进度条样式
        progressDialog.setMax( progressMax );       //设置 进度最大值


        // 样式设置完成之后， show Dialog
        progressDialog.show();


        /*
            执行 耗时的逻辑， 并更新 waitWhileDialog 的进度
            method: 另外开辟线程 执行逻辑
         */

        new Thread(new Runnable() {
            @Override
            public void run()  {
                int p =0;
                while( p<progressMax )
                {
                    // 调用 Thread 的静态方法 sleep
                    try {
                        Thread.sleep( progressSleepTime );  // ms
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    p++;
                    progressDialog.setProgress(p );

                }

                //进度条 达到 设置的最大 就消失
                progressDialog.cancel();  //取消  cancle
            }
        }).start();
    }



    public void normalDialog(String Message ){
        /*
            setIcon     设置对话框图标
            setTitle    设置对话框标题
            setMessage   消息提示
            setXXXX 方法返回 Dialog 对象
         */
        //  import android.app.Dialog;  //
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder( useContext );

        normalDialog.setIcon(R.drawable.bb);
        normalDialog.setTitle("Register Error");
        normalDialog.setMessage( Message );

        // 生成按钮 button
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  to do
            }
        });
        // show() 方法是 builder 继承的  父类 Dialog 的 show()
        normalDialog.show();
    }
}
