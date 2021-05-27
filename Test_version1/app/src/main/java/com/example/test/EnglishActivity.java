package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.speech.tts.Voice;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.ArrayList;

import com.example.test.TTSUtility;

/*

    the main function widget
 */


public class EnglishActivity extends AppCompatActivity {
    public  String      appid="6055830d";
    public  String      voiceTTS="6055830d";  //语音合成 appid
    private Button      bt_voice;
    private TextView    text_result;
    private EditText    et_show;
    private Button      bt_TTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english);


        // 此处在 activity 初始化的时候就 初始化 迅飞的工具
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "="+appid );
        bindView();
    }

    /* 初始化  语音识别  */
    public void initSpeech(){
        //语音 配置对象初始化
        //SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(this,null);
        // 1 创建 RecognizerDialog 对象
        RecognizerDialog mDialog = new RecognizerDialog(this,null);

        // 2 设置听写参数  API 手册 Android  SpeechConstant 类
        //mIat.setParameter(SpeechConstant.DOMAIN,"iat");   // domain: 域名
        //mIat.setParameter(SpeechConstant.LANGUAGE,"zh_cn");
        //mIat.setParameter(SpeechConstant.ACCENT,"mandarin");

        // 3 设置 回调接口
        mDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean islast) {
                if( !islast ){
                    // 解析语言
                    String result = parseVoice(recognizerResult.getResultString() );
                    et_show.setText(result);

                    //获取  焦点
                    et_show.requestFocus();
                    // 将光标 定位到 文字最后， 以便 修改
                    et_show.setSelection( result.length() );
                }

            }

            @Override
            public void onError(SpeechError speechError) {

            }
        });

        // 显示 dialog  ，  接受语音输入
        mDialog.show();
    }

    /* 解析 语音 json */
    public static String parseVoice(String resultString ){
        Gson gson = new Gson();
        Voice voiceBean = gson.fromJson(resultString, Voice.class );

        StringBuffer sb = new StringBuffer();
        ArrayList<Voice.WSBean>  ws = voiceBean.ws;
        for( Voice.WSBean  Wsbean : ws ){
            String word = Wsbean.cw.get(0).w;
            sb.append(word);

        }
        return sb.toString();

    }

    // 封装 语音对象
    class Voice{
        public ArrayList<WSBean>  ws;

        public class WSBean{
            public ArrayList<CWBean>  cw;
        }

        public class CWBean{
            public String w;
        }
    }


    void bindView(){
        bt_voice     =(findViewById(R.id.bt_voice));
        text_result  =(findViewById(R.id.text_result));
        et_show      =(findViewById(R.id.et_show));
        bt_TTS       =(findViewById(R.id.bt_TTS));

        bt_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSpeech();
            }
        });

        bt_TTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TTSUtility.getInstance(getApplicationContext()).speaking(  et_show.getText().toString()  );
            }
        });

    }

    // 封装 语音合成工具类
    //在上面的截图中有一个TTSUtility类，没错，我们把语音合成疯转在一个工具类中。
    //同时将其打造成单例模式。这样在我们整个应用程序中，只有一个工具类，就不用每次需要合成是都new一个对象


}

