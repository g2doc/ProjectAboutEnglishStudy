package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/*
    单词查询的主界面,
    author: Zhanghao
    data:   2021:04:16
 */

public class StartDictActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView word_form,word_search;
    private TextView  start,translateStart,testNum;
    private SeekBar   seekBar;
    private int       maxNum = 100;  //可以选择单词测试的 最大数量



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        隐藏标题
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*隐藏任务栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        super.onCreate(savedInstanceState);            //传入的 savedInstanceState 为空
        setContentView(R.layout.activity_start_dict);
        Init();

    }

    // component: 组成部分,组件,元件(n)    组成的,构成的(adj)
    //========================================================================================//

    void Init(){
        bindComponent();  //绑定 View

        translateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartDictActivity.this,WordTranslate.class);
                startActivity(intent);
            }
        });

        // 就是这个 下载单词好像

        Connector.getDatabase();
        Word word = DataSupport.findFirst(Word.class);
        if (word == null){
            readTxt(this,"Result.txt");
        }


        // 跳转到 单词页面
        word_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartDictActivity.this,WordForm.class);
                startActivity(intent);
            }
        });


        word_search.setOnClickListener(this);

//        word_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(StartDictActivity.this,DictActivity.class);
//                startActivity(intent);
//            }
//        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (maxNum < 1){
                    Toast.makeText(v.getContext(),"单词总数不能为0",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(StartDictActivity.this,WordTest.class);
                intent.putExtra("maxNum",maxNum);
                startActivity(intent);
            }
        });



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                testNum.setText("测试单词总数:"+progress);
                maxNum = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }




    private  void bindComponent(){
        word_form      = (ImageView) findViewById(R.id.word_form);
        word_search    = (ImageView) findViewById(R.id.word_search);
        start          = (TextView)  findViewById(R.id.bt_start_test);
        translateStart = (TextView)  findViewById(R.id.word_translate);
        seekBar        = (SeekBar)   findViewById(R.id.seekbar);
        testNum        = (TextView)  findViewById(R.id.test_num);
    }



    private void readTxt(Context context, String name) {
        BufferedReader reader = null;
        String temp = null;

        try {
            InputStream is = context.getAssets().open(name);
            reader = new BufferedReader(new InputStreamReader(is));
            while ((temp = reader.readLine()) != null) {
                Word word = new Word();
                String[] words = temp.split("=>");
                word.setWord(words[0]);
                word.setInterpretation(words[1]);
                word.save();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_floating:
                Intent intent = new Intent(StartDictActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.word_search:
                Intent intentToDict = new Intent(StartDictActivity.this,DictActivity.class);
                startActivity(intentToDict);

                break;
            default:
                break;

        }

    }
}

