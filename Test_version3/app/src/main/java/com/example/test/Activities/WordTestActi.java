package com.example.test.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;
import com.example.test.WordTest;

public class WordTestActi extends AppCompatActivity {

    private TextView  start,testNum;
    private SeekBar   seekBar;
    private int       maxNum = 100;  //可以选择单词测试的 最大数量


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_test2);

        // debug
        System.out.println("======================   可以进入  ");
        start = (TextView) findViewById(R.id.bt_start_test);

        Init();



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






    void Init(){
        testNum        = (TextView)  findViewById(R.id.test_num);
        seekBar        = (SeekBar)   findViewById(R.id.seekbar);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (maxNum < 1){
                    Toast.makeText(v.getContext(),"单词总数不能为0",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(WordTestActi.this, WordTest.class);
                intent.putExtra("maxNum",maxNum);
                startActivity(intent);

            }
        });




    }
}