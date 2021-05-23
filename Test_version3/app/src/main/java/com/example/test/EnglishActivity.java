package com.example.test;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.ArrayList;

/*

    the main function widget
 */


public class EnglishActivity extends AppCompatActivity {
    public  String      appid="6055830d";
    public  String      voiceTTS="6055830d";  //语音合成 appid
    private Button      bt_voice;
    private TextView    text_result;

    private Button bt_expla;

    private EditText    et_tofind;
    private Button      bt_TTS;
    private TextView showall;
    private TextView personshow;
    private boolean flagIfread=false;
    private Button bt_tofindVoice;
    private boolean flagIsEng=true;

    private Button bt_speaknext;
    private Button bt_ok;

    int count_computer = 0;
    int ifspeak = 0;
    int count_per=0;

    public String[] person = new String[]{"Okay,I'll do it",
            "If kids is what I takes to be with you, then kids it is.",
            "If I have to, I'll do all again. I'll do the 4 o'clock feeding thing. I'll go to the P.T.A. meetings. I'll coach the soccer team",
            " Yeah, if I have to. Monica, I don't wanna lose you, so if I have to do it all over again, then I will",
            "But you're not",
            "God. I love you",
            "I guess we just keep dancing",

    };
    public String[] computer = new String[]{"You'll do what?","Oh my God!",
            "Really?",
            "You're the most wonderful man. And if you hadn't said“if I have to” like seventeen times, then I'd be saying “Okay, let's do it",
            "Oh my God, I can't believe what I'm getting ready to say. I wanna have a baby, but I don't wanna have one with someone who doesn't really wanna have one",
            "I know you do. Me, too. So what now?"
    };

    private String[] list = new String[]{
            "A:Okay,I'll do it\n " +
                    "B:You'll do what?\n" +
                    "A: If kids is what I takes to be with you, then kids it is.\n" +
                    "B:Oh my God!\n " +
                    "A: If I have to, I'll do all again. I'll do the 4 o'clock feeding thing. I'll go to the P.T.A. meetings. I'll coach the soccer team.\n" +
                    "B:Really?\n A" +
                    ": Yeah, if I have to. Monica, I don't wanna lose you, so if I have to do it all over again, then I will.\n" +
                    "B:You're the most wonderful man. And if you hadn't said“if I have to” like seventeen times, then I'd be saying “Okay, let's do it\n" +
                    "A:But you're not.\n " +
                    "B: Oh my God, I can't believe what I'm getting ready to say. I wanna have a baby, but I don't wanna have one with someone who doesn't really wanna have one\n" +
                    "A:God. I love you.\n" +
                    "B: I know you do. Me, too. So what now?\n " +
                    "A: I guess we just keep dancing.",

            "A:好吧，我会这样做的。\n " +
                    "B:你会怎么做？\n " +
                    "A:如果要我和你生孩子，才能和你在一起，那就生吧\n " +
                    "B:天啊" +
                    "A:如果我应该做，我会重新开始，我会每天4点钟起来喂奶，我会参加家长和教师联谊会，我会为孩子的足球队当教练\n" +
                    "B:真的吗？\n A:当前，如果这是我应该做的，莫妮卡，我不想失去你，所以如果要我把这个过程重复一起，我也愿意\n" +
                    "B:你是最棒的，如果不是你说了无数次的 如果我应该要，那我会说:好的，我们就这样决定吧 \n " +
                    "A:但是你没有这么说\n" +
                    "B:天啊，我都不敢相信，我准备要说下面的这些话。我想要个宝宝，但我不想和一个并非心甘情愿要孩子的人一起生\n" +
                    "A:天啊，我真爱你\n" +
                    "B:我知道你爱我。我也爱你。现在干嘛?\n" +
                    "A:我觉得我们继续跳舞吧--"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english);


        // 此处在 activity 初始化的时候就 初始化 迅飞的工具
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "="+appid );
        bindView();

        showall = (TextView)findViewById(R.id.showall);
        showall.setMovementMethod(new ScrollingMovementMethod());

        personshow = (TextView)findViewById(R.id.personshow);
        et_tofind = (EditText)findViewById(R.id.et_tofind);

        bt_tofindVoice = (Button)findViewById(R.id.bt_tofindvoice);
        bt_tofindVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TTSUtility.getInstance(getApplicationContext()).speaking(et_tofind.getText().toString() );
            }
        });

        bt_expla = (Button)findViewById(R.id.explain);
        bt_expla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {    // true means need to show english
                if( flagIsEng == false){
                    showall.setText(list[1]);

                    flagIsEng= true;


                }else {
                    showall.setText(list[0]);
                    flagIsEng = false;
                }
            }
        });

        bt_speaknext = (Button)findViewById(R.id.speaknext);
        bt_speaknext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 这里点击播放下一句
                if( count_computer <= computer.length && ifspeak == 1 ){
                    TTSUtility.getInstance(getApplicationContext()).speaking( computer[count_computer] );
                    ifspeak = 0;
                    if( count_computer ==0){
                        count_computer++;
                    }

                }
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                //initSpeech();

            }
        });


        bt_ok = (Button)findViewById(R.id.bt_ok);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ifspeak = 1;
                if(count_computer != 0){
                count_computer++;}
            }
        });


        // textview 可以滚动的网址 https://blog.csdn.net/judyge/article/details/46359967
    }



    void judgeIfok(){
        if( personshow.getText().toString() == person[count_per]){
            count_per++;
            ifspeak = 1;
        }

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
                    //et_show.setText(result);
                    personshow.setText(result);

                    //获取  焦点
                    //et_show.requestFocus();
                    // 将光标 定位到 文字最后， 以便 修改
                    //et_show.setSelection( result.length() );
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

        bt_TTS       =(findViewById(R.id.bt_TTS));

        bt_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSpeech();   // initSpeech 是语音识别接口

            }
        });


        // 播放的 button
        bt_TTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                new Thread(){
//                    @Override
//                    public void run() {
//                        System.out.println("//////////////   Thread START ");
//                        int i=0;
//                        while (true){
//                            if (flagIfread && i<= computer.length){
//                                TTSUtility.getInstance(getApplicationContext()).speaking(computer[i]);
//                                i++;
//                                flagIfread = false;
//                                System.out.println("alive:" + i);
//                            }
//                        }
//                    }
//                }.start();
//                // 发音的线程
//                new Thread(){
//                    @Override
//                    public void run() {
//                        int i = 0;
//                        while (true){
//                            if( flagIfread = false && i<= person.length ){
//                                initSpeech();
//                                if(personshow.toString() == person[i]){
//                                    i++;
//                                    flagIfread = true;
//                                }
//                            }
//                        }
//
//                    }
//                }.start();

                count_computer=0;
                count_per=0;
                flagIfread=false;
                flagIsEng=true;


                int count = 0;
                int ifspeak = 0;
                int count_per=0;

                //TTSUtility.getInstance(getApplicationContext()).speaking(  et_show.getText().toString()  );
                String[] auto = new String[]{"请跟着下面的对话进行跟读，你的人物是A ,如 Okay, I will do it"};
                TTSUtility.getInstance(getApplicationContext()).speaking(auto[0] );

                showall.setText(list[0]);
                flagIsEng = false;
                // 然后开启线程

            }
        });

    }
    // 封装 语音合成工具类
    //在上面的截图中有一个TTSUtility类，没错，我们把语音合成疯转在一个工具类中。
    //同时将其打造成单例模式。这样在我们整个应用程序中，只有一个工具类，就不用每次需要合成是都new一个对象


    /*
        搞一个 线程，一直跑着， 调用监听
     */


}

