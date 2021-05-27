package com.example.test;
/*
    fountionActivity 是实现需求的主要界面
    1-1 语音识别
    // android项目引入 jar 包 https://jingyan.baidu.com/article/acf728fd54f192f8e510a3c1.html
 */

import android.os.Bundle;
import android.provider.UserDictionary;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.ls.LSException;

import java.util.List;


//  Dialog 工具类
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;





// 用于保存 语音识别 returnResult 的 class
class DictationResult{
    private String sn;  //sn number : 第几句
    private String ls;
    private String bg;
    private String ed;

    private List<Words> ws;


    // 定义成员类
    public static class Words{
        private String bg;
        private List<Cw> cw;

        public static class Cw{
            private String w;
            private String sc;

            public String getW(){
                return w;
            }

            public void  setW( String w){
                this.w = w;
            }

            public String getSc(){
                return sc;
            }

            public void  setSc( String sc){
                this.sc = sc;
            }
        }

        public String getBg(){
            return bg;
        }

        public void setBg(String bg){
            this.bg=bg;
        }

        public List<Cw> getCw(){
            return cw;
        }

        public void setCw( List<Cw> cw){
            this.cw = cw;

        }



        // 学习 java List 遍历的 function
        @NonNull
        @Override
        public String toString() {
            String result = "";
            for ( Cw cwTemp : cw){
                result += cwTemp.toString();
            }
            return result;

        }
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getLs() {
        return ls;
    }

    public void setLs(String ls) {
        this.ls = ls;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getEd() {
        return ed;
    }

    public void setEd(String ed) {
        this.ed = ed;
    }

    public List<Words> getWs() {
        return ws;
    }

    public void setWs(List<Words> ws) {
        this.ws = ws;
    }

    @Override
    public String toString() {
        String result = "";
        for (Words wsTmp : ws) {
            result += wsTmp.toString();
        }
        return result;
    }


}


public class FountionActivity extends AppCompatActivity {
    private Button bt_speak;
    private EditText ed_text;
    private RecognizerDialog iatDialog;
    private String appID = "=6055830d";   //科大讯飞: 应用名=英语学习助手-毕设

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fountion);
        // Context context = this.getApplicationContext();  纯测试
        SpeechUtility.createUtility(this,SpeechConstant.APPID+"="+appID);
        Init();

    }

    // 定义不容修改的 mInitListener
    public static final String TAG = "FountionActivity";

    private InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(FountionActivity.this,
                        "初始化失败,错误码:" + code, Toast.LENGTH_SHORT).show();
            }
        }
    };

    void Initspeak() {
        //SpeechUtility.createUtility(FountionActivity.this, SpeechConstant.APPID + appID);
        //初始化有交互动画的语音识别器
        iatDialog = new RecognizerDialog(FountionActivity.this, mInitListener);

        //@ 设置监听, 实现听写结果的回调              匿名类传参,   要回顾复习
        iatDialog.setListener(new RecognizerDialogListener() {

            String resultJson = "[";  //放置在外边  做类的变量则报错，会造成json格式不对（？）



            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                System.out.println("------------------onResult----------------");

                System.out.println("s111111111");


                if (!isLast) {
                    resultJson += recognizerResult.getResultString() + ",";

                } else {
                    resultJson += recognizerResult.getResultString() + "]";
                }

                if (isLast) {
                    // 解析语音识别 返回后的 json 格式的结果
                    Gson gson = new Gson();
                    // DictationResult 是自建的一个 用来接收 转换语音听写结果的 class
                    List<DictationResult> resultList = gson.fromJson(resultJson,
                            new TypeToken<List<DictationResult>>() {
                            }.getType());
                    String result = "";
                    for (int i = 0; i < resultList.size() - 1; i++) {
                        result += resultList.get(i).toString();
                    }
                    ed_text.setText(result);

                    // 获取焦点
                    ed_text.requestFocus();

                    // 将光标 定位到 数字最后,  以便 修改
                    ed_text.setSelection(result.length());
                }


            }

            @Override
            public void onError(SpeechError speechError) {
                // 自动 生成的方法 存根
                speechError.getPlainDescription(true);

            }
        });


        // 开始 听写
        iatDialog.show();

    }


    void Init() {
        bt_speak = (Button) findViewById(R.id.bt_speak);
        ed_text = (EditText) findViewById(R.id.ed_text);

        // 开多线程
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Initspeak();
                System.out.println(1111);

            }
        }).start();
        */
        //Initspeak();


        // 开始讲话的 button
        bt_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理语音识别的逻辑
                //Initspeak();
            }
        });
    }


    // TODO 开始说话
    private void btnVoice(){
        // 学习相关控件的使用
        RecognizerDialog dialog = new RecognizerDialog(this, null);
        dialog.setParameter(SpeechConstant.LANGUAGE,"zh_cn");
        dialog.setParameter(SpeechConstant.ACCENT,"mandarin");

        // 设置监听
        dialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                printResult( recognizerResult );
            }

            @Override
            public void onError(SpeechError speechError) {


            }
        });
        dialog.show();
        Toast.makeText(this,"请开始说话",Toast.LENGTH_SHORT).show();
    }

    // 回调结果
    private void printResult( RecognizerResult  results ){
        String text  = parselatResult(results.getResultString());
        // 自动填写地址
        ed_text.append( text );
    }

    public static String parselatResult( String json){
        StringBuffer ret = new StringBuffer();
        try{
            JSONTokener tokener = new JSONTokener( json);
            JSONObject jsonObject = new JSONObject(tokener);
            JSONArray words = jsonObject.getJSONArray("ws");
            for( int i=0; i< words.length(); i++ ){
                // 转写 结果词, 默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret.toString();
    }
}


