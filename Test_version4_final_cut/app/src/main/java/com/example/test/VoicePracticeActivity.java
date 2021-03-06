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
 *  changed bt zhang hao in version 4.0_final
 *  API Level 28
 *  @author zhang
 *  date: 2021-05-27
 */
public class VoicePracticeActivity extends AppCompatActivity implements View.OnClickListener {
    public  String      appid="6055830d";
    private static final String TAG = "niu";
    private Button       voice_bt_voiceStart;
    private Button       voice_bt_voiceNext;
    private Button       voice_bt_voiceJudge;
    private Button       voice_bt_LanguageText;
    private Button       voice_bt_findVoice;
    private Button       voice_bt_reducePitch;
    private Button       voice_bt_increasePitch;
    private Button       voice_bt_reduceSpeed;
    private Button       voice_bt_increaseSpeed;
    private Button       voice_bt_chooseTodd;
    private Button       voice_bt_chooseDevon;
    private EditText     voice_edit_findVoice;
    private TextView     voice_text_showAll;
    private String       ChooseLanguage;
    private RadioButton  English, Chinese, French;
    private RadioGroup   LanguageGroup;
    private TextToSpeech mSpeech   = null;
    private boolean      ifEnglish = true;
    private int          PersonIndex       = 0;
    private int          ComputerIndex     = 0;
    private int          ifPressChoosePart = 0;  //0 ?????????????????? ???1 ?????? Todd 2?????? Devon
    private boolean      ifSpeakNext       = false;
    private AlertDialogUse alertDialogUse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voicepractice);
        initUI();
        initLanguageList();
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
            tempStr = "??????";
        }else if(chooseButtonId == Chinese.getId() ){
            tempStr = "??????";
        }
        else if(chooseButtonId == French.getId()){
            tempStr = "??????";
        }else { }
        return tempStr;
    }


    void initUI(){
        voice_bt_voiceStart     =(Button)findViewById(R.id.voice_bt_voiceStart);
        voice_bt_voiceNext      =(Button)findViewById(R.id.voice_bt_voiceNext);
        voice_bt_voiceJudge     =(Button)findViewById(R.id.voice_bt_voiceJudge);
        voice_bt_findVoice      =(Button)findViewById(R.id.voice_bt_findVoice);
        voice_bt_LanguageText   =(Button)findViewById(R.id.voice_bt_textLanguage);
        voice_bt_reducePitch    =(Button)findViewById(R.id.voice_bt_reducePitch);
        voice_bt_increasePitch  =(Button)findViewById(R.id.voice_bt_increasePitch);
        voice_bt_reduceSpeed    =(Button)findViewById(R.id.voice_bt_reduceSpeed);
        voice_bt_increaseSpeed  =(Button)findViewById(R.id.voice_bt_increaseSpeed);
        LanguageGroup           =(RadioGroup)findViewById(R.id.voice_radioGroup);
        voice_text_showAll      =(TextView)findViewById(R.id.voice_text_showAll);
        voice_bt_chooseTodd     =(Button)findViewById(R.id.voice_bt_chooseTodd);
        voice_bt_chooseDevon    =(Button)findViewById(R.id.voice_bt_chooseDevon);
        voice_edit_findVoice    =(EditText)findViewById(R.id.voice_edit_findVoice);
        English=(RadioButton)findViewById(R.id.voice_radioBt_English);
        Chinese=(RadioButton)findViewById(R.id.voice_radioBt_Chinese);
        French =(RadioButton)findViewById(R.id.voice_radioBt_French);

        voice_bt_voiceStart.setOnClickListener(this);
        voice_bt_voiceNext.setOnClickListener(this);
        voice_bt_voiceJudge.setOnClickListener(this);
        voice_bt_findVoice.setOnClickListener(this);
        voice_bt_LanguageText.setOnClickListener(this);
        voice_bt_reducePitch.setOnClickListener(this);
        voice_bt_increasePitch.setOnClickListener(this);
        voice_bt_reduceSpeed.setOnClickListener(this);
        voice_bt_increaseSpeed.setOnClickListener(this);

        // ?????? textView ????????????
        voice_text_showAll.setMovementMethod(ScrollingMovementMethod.getInstance());
        voice_bt_chooseTodd.setOnClickListener(this);
        voice_bt_chooseDevon.setOnClickListener(this);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "="+appid );
        mSpeech        =new TextToSpeech(VoicePracticeActivity.this, new TTSListener());
        alertDialogUse =new AlertDialogUse(VoicePracticeActivity.this);
    }

    private class TTSListener implements TextToSpeech.OnInitListener{
        @Override
        public void onInit(int status) {
            if( status == TextToSpeech.SUCCESS ){
                Toast.makeText(getApplicationContext(),"?????????????????????",Toast.LENGTH_SHORT);
                Log.i(TAG,"?????????????????????");
            }else {
                Toast.makeText(getApplicationContext(),"?????????????????????",Toast.LENGTH_SHORT);
                Log.i(TAG,"?????????????????????  = "+status );
            }
        }
    }


    /**
     *  ?????? ????????????
     */
    public void playVoice(int ifedit , String toSpeak){
        ChooseLanguage = getLanguage(LanguageGroup);
        int supported = mSpeech.setLanguage(ShareData.LanguageList.get(ChooseLanguage));
        Log.i(TAG,"mSpeech.setLanguage:"+ShareData.LanguageList.get(ChooseLanguage));

        Log.i(TAG,"supportdID =" + supported);
        mSpeech.setSpeechRate(ShareData.voice_speed);
        mSpeech.setPitch(ShareData.voice_pitch);
        Log.i(TAG,"????????????:"+ChooseLanguage+ "--" + ShareData.LanguageList.get(ChooseLanguage));
        if ((supported != TextToSpeech.LANG_AVAILABLE) && (supported != TextToSpeech.LANG_COUNTRY_AVAILABLE)) {
            Log.i(TAG,"??????????????????:" + ChooseLanguage);
        }else {
            Log.i(TAG,"???????????? ?????? !!:"+ ChooseLanguage);
        }

        if( 1 == ifedit )
        {
            String tempStr = toSpeak;
            Log.i(TAG,"????????? edit ????????? ???:" + toSpeak.length() );
            mSpeech.speak(tempStr, TextToSpeech.QUEUE_FLUSH, null);
            Log.i(TAG,"????????????:" + tempStr);
            Log.i(TAG,"????????????:" + ShareData.voice_speed + "// ????????????1.5");
            Log.i(TAG,"????????????:"+ShareData.voice_pitch + "// ????????????2.0");
            Log.i(TAG,"??????--------------------");
        }
        if( 0 == ifedit ){
            mSpeech.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
        }


    }


    private void initLanguageList(){
        ShareData.LanguageList.put("??????", Locale.ENGLISH); //Locale.ENGLISH ????????????
        Log.i(TAG,"Locale.ENGLISH =" + Locale.ENGLISH);
        ShareData.LanguageList.put("??????",Locale.CHINA);
        ShareData.LanguageList.put("??????",Locale.FRENCH);
    }

    /**
     * ???????????? start ???????????????????????? ??? Todd ?????? Devon, ?????????????????? Todd ??????????????????
     * if ???????????????????????? start, ?????? Dialog ??????????????????
     */
    public void playStart(){
        //voice_text_showAll.setText(EnglishArticle.getAllText()[0]);
        //voice_text_showAll.setText( mEnglishArticle.getAllText()[0] );  ???????????????, ??????????????????????????????, ????????????????????????
        /* set English and then change isEnglish flag to false */
        changeText();
        if( 0 == ifPressChoosePart ){
            alertDialogUse.normalDialog("??????????????????????????????Todd / Devon");
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
     * 1???public??????????????????????????????????????????????????? ?????????????????????????????????
     * 2???private???????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * ?????? EnglishArticle ?????? String[] ????????????, ????????? ??? public ?????? ??????
     */

    /**
     * playJudge ????????????????????????
     * ??????????????? "????????????" button ??? ?????????????????????????????????????????? ????????? edit ??? ??? ???????????????
     */
    public void getJudge(){
        initSpeech();
        playVoice(0,voice_edit_findVoice.getText().toString());
    }

    /**
     * ?????????????????????
     */
    public void initSpeech(){
        RecognizerDialog mDialog = new RecognizerDialog(this,null);
        mDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean islast) {
                if( !islast ){
                    String result = parseVoice(recognizerResult.getResultString() );
                    voice_edit_findVoice.setText(result);
                    voice_edit_findVoice.requestFocus();
                    voice_edit_findVoice.setSelection( result.length() );
                }
            }
            @Override
            public void onError(SpeechError speechError) {

            }
        });
        mDialog.show();
    }


    /**
     * ?????? ?????? json
     * @param resultString
     * @return
     */
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

    /**
     * ??????????????????
     */
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



    //?????? 1.0f  ?????? 2.0f
    public void reducePitch(){
        ShareData.voice_pitch = ShareData.voice_pitch - 0.1f;
        Toast.makeText(getApplicationContext(),"????????????="+ShareData.voice_pitch,Toast.LENGTH_SHORT);
    }

    public void increasePitch(){
        ShareData.voice_pitch = ShareData.voice_pitch + 0.1f;
        Toast.makeText(getApplicationContext(),"????????????="+ShareData.voice_pitch,Toast.LENGTH_SHORT);
    }

    //?????? 0.5f ?????? 1.5f
    public void reduceSpeed(){
        ShareData.voice_speed = ShareData.voice_speed - 0.1f;
        Toast.makeText(getApplicationContext(),"????????????="+ShareData.voice_speed,Toast.LENGTH_SHORT);
    }

    public void increaseSpeed(){
        ShareData.voice_speed = ShareData.voice_speed + 0.1f;
        Toast.makeText(getApplicationContext(),"????????????="+ShareData.voice_pitch,Toast.LENGTH_SHORT);
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

