package com.example.test;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.EnglishVoiceTool.EnglishArticle;
import com.example.test.EnglishVoiceTool.ShareData;
import com.example.test.MyLib.AlertDialogUse;
import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.Locale;

/**
 *  The English Voice Widget
 *  changed bt zhang hao in version 4.0
 *  description: delete the XunFei SDK , choose Google native TextToSpeech to meet the requirement
 *  need API 28
 *  @author zhang
 *  date: 2021-05-26
 */
public class VoicePracticeActivity extends AppCompatActivity implements View.OnClickListener {
    public  String      appid="6055830d";
    private Button      voice_bt_voiceStart;
    private Button      voice_bt_voiceNext;
    private Button      voice_bt_voiceJudge;
    private Button      voice_bt_LanguageText;
    private Button      voice_bt_findVoice;
    private Button      voice_bt_reducePitch;
    private Button      voice_bt_increasePitch;
    private Button      voice_bt_reduceSpeed;
    private Button      voice_bt_increaseSpeed;
    private Button      voice_bt_chooseTodd;
    private Button      voice_bt_chooseDevon;

    private EditText    voice_edit_findVoice;
    private TextView    voice_text_showAll;

    private static final String TAG = "niu";
    private String       ChooseLanguage;
    private RadioButton  English, Chinese, French;
    private RadioGroup   LanguageGroup;

    private TextToSpeech mSpeech   = null;
    private boolean      ifEnglish = true;

    private EnglishArticle mEnglishArticle;
    private int     PersonIndex       = 0;
    private int     ComputerIndex     = 0;
    private int     ifPressChoosePart = 0;  //0 表示没有按下 ，1 代表 Todd 2代表 Devon
    private boolean ifSpeakNext       = false;
    private AlertDialogUse alertDialogUse;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voicepractice);
        initUI();
        // textview 可以滚动的网址 https://blog.csdn.net/judyge/article/details/46359967

        initLanguageList();
        // 得到 Article 单例
       // mEnglishArticle = EnglishArticle.getInstance();
    }


    /**
     * function: get the language that user choose
     * @param LanguageGroup
     * @return tempStr
     */
    private String getLanguage(RadioGroup LanguageGroup){
        int chooseButtonId = LanguageGroup.getCheckedRadioButtonId();
        String tempStr = "";
        if( chooseButtonId == English.getId() ){
            tempStr = "英语";
        }else if(chooseButtonId == Chinese.getId() ){
            tempStr = "汉语";
        }
        else if(chooseButtonId == French.getId()){
            tempStr = "法语";
        }else { }
        return tempStr;
    }


    void initUI(){
        voice_bt_voiceStart   =(Button)findViewById(R.id.voice_bt_voiceStart);
        voice_bt_voiceNext    =(Button)findViewById(R.id.voice_bt_voiceNext);
        voice_bt_voiceJudge   =(Button)findViewById(R.id.voice_bt_voiceJudge);
        voice_bt_findVoice    =(Button)findViewById(R.id.voice_bt_findVoice);
        voice_bt_LanguageText =(Button)findViewById(R.id.voice_bt_textLanguage);

        voice_bt_voiceStart.setOnClickListener(this);
        voice_bt_voiceNext.setOnClickListener(this);
        voice_bt_voiceJudge.setOnClickListener(this);
        voice_bt_findVoice.setOnClickListener(this);
        voice_bt_LanguageText.setOnClickListener(this);

        voice_edit_findVoice=(EditText)findViewById(R.id.voice_edit_findVoice);
        English= (RadioButton)findViewById(R.id.voice_radioBt_English);
        Chinese= (RadioButton)findViewById(R.id.voice_radioBt_Chinese);
        French = (RadioButton)findViewById(R.id.voice_radioBt_French);

        voice_bt_reducePitch    =(Button)findViewById(R.id.voice_bt_reducePitch);
        voice_bt_increasePitch  =(Button)findViewById(R.id.voice_bt_increasePitch);
        voice_bt_reduceSpeed    =(Button)findViewById(R.id.voice_bt_reduceSpeed);
        voice_bt_increaseSpeed  =(Button)findViewById(R.id.voice_bt_increaseSpeed);

        voice_bt_reducePitch.setOnClickListener(this);
        voice_bt_increasePitch.setOnClickListener(this);
        voice_bt_reduceSpeed.setOnClickListener(this);
        voice_bt_increaseSpeed.setOnClickListener(this);

        voice_text_showAll = (TextView)findViewById(R.id.voice_text_showAll);
        // 设置 textView 可以滑动
        voice_text_showAll.setMovementMethod(ScrollingMovementMethod.getInstance());
        LanguageGroup      = (RadioGroup)findViewById(R.id.voice_radioGroup);

        voice_bt_chooseTodd=(Button)findViewById(R.id.voice_bt_chooseTodd);
        voice_bt_chooseDevon=(Button)findViewById(R.id.voice_bt_chooseDevon);
        voice_bt_chooseTodd.setOnClickListener(this);
        voice_bt_chooseDevon.setOnClickListener(this);

        mSpeech = new TextToSpeech(VoicePracticeActivity.this, new TTSListener());
        alertDialogUse = new AlertDialogUse(VoicePracticeActivity.this);

        // 此处在 activity 初始化的时候就 初始化 迅飞的工具
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "="+appid );
    }

    private class TTSListener implements TextToSpeech.OnInitListener{
        @Override
        public void onInit(int status) {
            if( status == TextToSpeech.SUCCESS ){
                Toast.makeText(getApplicationContext(),"引擎初始化成功",Toast.LENGTH_SHORT);
                Log.i(TAG,"引擎初始化成功");
            }else {
                Toast.makeText(getApplicationContext(),"引擎初始化失败",Toast.LENGTH_SHORT);
                Log.i(TAG,"引擎初始化失败  = "+status );
                /**
                 *  if 抛出的 status = -2
                 *  public static final int LANG_NOT_SUPPORTED = -2;     语言不支持
                 */
            }
        }
    }


    /**
     *  实现 播报功能
     *
     */
    public void playVoice(int ifedit , String toSpeak){
        ChooseLanguage = getLanguage(LanguageGroup);
        int supported = mSpeech.setLanguage(ShareData.LanguageList.get(ChooseLanguage));
        Log.i(TAG,"mSpeech.setLanguage:"+ShareData.LanguageList.get(ChooseLanguage));

        Log.i(TAG,"supportdID =" + supported);  // -2
        mSpeech.setSpeechRate(ShareData.voice_speed);
        mSpeech.setPitch(ShareData.voice_pitch);
        Log.i(TAG,"选择语言:"+ChooseLanguage+ "--" + ShareData.LanguageList.get(ChooseLanguage));
        //mSpeech.setAudioAttributes(new AudioAttributes());
        //mSpeech.setVoice(new Voice(null,Locale.US, Voice.QUALITY_HIGH,Voice.LATENCY_NORMAL,false,null));

        //public static final int LANG_AVAILABLE = 0;
        //public static final int LANG_COUNTRY_AVAILABLE = 1;

        if ((supported != TextToSpeech.LANG_AVAILABLE) && (supported != TextToSpeech.LANG_COUNTRY_AVAILABLE)) {
            // 语言设置失败
            // 当前 return -2
            //    public static final int LANG_NOT_SUPPORTED = -2;  语言不支持
            Log.i(TAG,"语言设置失败:" + ChooseLanguage);
        }else {
            Log.i(TAG,"语音设置 成功 !!:"+ ChooseLanguage);
        }

        if( 1 == ifedit )
        {
            String tempStr = toSpeak;
            Log.i(TAG,"当前的 edit 的长度 为:" + toSpeak.length() );
            mSpeech.speak(tempStr, TextToSpeech.QUEUE_FLUSH, null);
            Log.i(TAG,"测试文本:" + tempStr);
            Log.i(TAG,"当前语速:" + ShareData.voice_speed + "// 最快语速1.5");
            Log.i(TAG,"当前音调:"+ShareData.voice_pitch + "// 最高音调2.0");
            Log.i(TAG,"执行--------------------");
        }

        if( 0 == ifedit ){
            mSpeech.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
        }


    }


    private void initLanguageList(){
        ShareData.LanguageList.put("英语", Locale.ENGLISH); //Locale.ENGLISH 设置语言
        Log.i(TAG,"Locale.ENGLISH =" + Locale.ENGLISH);
        ShareData.LanguageList.put("汉语",Locale.CHINA);
        ShareData.LanguageList.put("法语",Locale.FRENCH);
    }


    /**
     * 逻辑是： start 之前要先选择一次 是 Todd 还是 Devon, 不选的话默认 Todd 是用户的角色
     * if 没有选择就点击了 start, 弹出 Dialog 提醒用户选择
     */
    public void playStart(){
        //voice_text_showAll.setText(EnglishArticle.getAllText()[0]);
        //voice_text_showAll.setText( mEnglishArticle.getAllText()[0] );  没有初始化, 构建没问题，运行报错, 通过获取单例访问
        /* set English and then change isEnglish flag to false */
        changeText();
        if( 0 == ifPressChoosePart ){
            alertDialogUse.normalDialog("请选择您的对话角色：Todd / Devon");
        }else if( 1 == ifPressChoosePart){ // person is Todd
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        if( true == ifSpeakNext){

                        }


                    }
                }
            }).start();
        }else if( 2 == ifPressChoosePart ){  // person is Devon

        }
    }


    /**
     * 1、public：声明公共类，公共类其他类可以调用 （其它类中也可以调用）
     * 2、private：声明私有类，私有类自己的类可以使用（只能本类之中使用），其它类不可使用。
     * 因为 EnglishArticle 中的 String[] 是私有的, 所以要 用 public 方法 返回
     */

    /**
     * playJudge 是用作语音检测的
     * 逻辑：点击 "发音评测" button ， 调用内部的语音识别，识别内容 显示在 edit 上 ， 并进行播报
     */
    public void getJudge(){
        initSpeech();
        playVoice(0,voice_edit_findVoice.getText().toString());
    }


    /**
     * 问题: public boolean setParameter(String var1, String var2) {
     *         return super.setParameter(var1, var2);
     *     }
     */
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
                    voice_edit_findVoice.setText(result);

                    //获取  焦点
                    voice_edit_findVoice.requestFocus();
                    // 将光标 定位到 文字最后， 以便 修改
                    voice_edit_findVoice.setSelection( result.length() );
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






    public void playNext(){
        if( 1 == ifPressChoosePart){ //Todd
            String[] temp = EnglishArticle.getInstance().getPersonText();
            if( PersonIndex < temp.length ){
                playVoice(1,temp[PersonIndex]);
                PersonIndex++;
            }
        }else if( 2== ifPressChoosePart ){
            String[] temp = EnglishArticle.getInstance().getComputerText();
            if( ComputerIndex < temp.length){
                playVoice(1,temp[ComputerIndex]);
                ComputerIndex++;
            }
        }
    }


    public void changeText(){
        if( !ifEnglish ){
            voice_text_showAll.setText( EnglishArticle.getInstance().getExplain()[0]);
            ifEnglish = true;
        }else{
            voice_text_showAll.setText( EnglishArticle.getInstance().getAllText()[0]);
            ifEnglish = false;
        }
    }



    //当前 1.0f  最大 2.0f
    public void reducePitch(){
        ShareData.voice_pitch = ShareData.voice_pitch - 0.1f;
        Toast.makeText(getApplicationContext(),"当前音调="+ShareData.voice_pitch,Toast.LENGTH_SHORT);
    }

    public void increasePitch(){
        ShareData.voice_pitch = ShareData.voice_pitch + 0.1f;
        Toast.makeText(getApplicationContext(),"当前音调="+ShareData.voice_pitch,Toast.LENGTH_SHORT);
    }

    //当前 0.5f 最大 1.5f
    public void reduceSpeed(){
        ShareData.voice_speed = ShareData.voice_speed - 0.1f;
        Toast.makeText(getApplicationContext(),"当前语速="+ShareData.voice_speed,Toast.LENGTH_SHORT);
    }

    public void increaseSpeed(){
        ShareData.voice_speed = ShareData.voice_speed + 0.1f;
        Toast.makeText(getApplicationContext(),"当前音调="+ShareData.voice_pitch,Toast.LENGTH_SHORT);
    }





    /**
     * function: set the listener of Views
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.voice_bt_voiceStart:
                playStart();
                break;
            case R.id.voice_bt_voiceNext:
                playNext();
                break;
            case R.id.voice_bt_voiceJudge:
                getJudge();
                break;
            case R.id.voice_bt_findVoice:
                playVoice(1,voice_edit_findVoice.getText().toString());
                break;
            case R.id.voice_bt_textLanguage:
                changeText();
                break;
            case R.id.voice_bt_reducePitch:
                reducePitch();
                break;
            case R.id.voice_bt_increasePitch:
                increasePitch();
                break;
            case R.id.voice_bt_reduceSpeed:
                reduceSpeed();
                break;
            case R.id.voice_bt_increaseSpeed:
                increaseSpeed();
                break;
            case R.id.voice_bt_chooseTodd:
                ifPressChoosePart=1;
                break;
            case R.id.voice_bt_chooseDevon:
                ifPressChoosePart=2;
                break;
        }
    }
}

