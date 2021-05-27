package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.Activities.WordFindActi;
import com.example.test.Activities.WordTestActi;
import com.example.test.Activities.Wordnotes.Wordnotes;

/**
 * function: the Main Function Widget to run to different Activities
 * @author zhang
 */

public class AppActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_toDict;
    private Button bt_toVoice;
    private Button bt_toListen;
    private Button bt_toWordRecord;

    private Button bt_toWordfind;

    private Button bt_toWordNote;
    private Button bt_toWordTest;
    private Button bt_toTransLate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        Init(); 
    }

    void Init(){
        bt_toWordfind       = (Button)findViewById(R.id.bt_toWordFind);
        bt_toWordfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppActivity.this, WordFindActi.class);
                startActivity(intent);
                finish();
            }
        });


        bt_toTransLate = (Button)findViewById(R.id.to_trans) ;
        bt_toTransLate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppActivity.this, WordTranslate.class);
                startActivity(intent);
            }
        });

        bt_toWordNote       = (Button)findViewById(R.id.bt_TowordNote);
        bt_toWordNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppActivity.this, Wordnotes.class);
                //intent.putExtra("content", "succ");
                startActivity(intent);
            }
        });


        bt_toWordTest = (Button)findViewById(R.id.to_wordTest);
        bt_toWordTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppActivity.this, WordTestActi.class);
                intent.putExtra("content", "succ");
                startActivity(intent);
            }
        });


        bt_toVoice          = (Button)findViewById(R.id.bt_toVoice);
        bt_toListen         = (Button)findViewById(R.id.bt_toListen);
        bt_toVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppActivity.this, VoicePracticeActivity.class);
                intent.putExtra("content", "succ");
                startActivity(intent);

            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}