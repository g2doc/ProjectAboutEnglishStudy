package com.example.test.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Activities.Wordnotes.Wordnotes;
import com.example.test.AppActivity;
import com.example.test.R;
import com.example.test.Word;
import com.example.test.WordFormAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 *  version 3.0 重构
 *  重新单独列出 wordfind 界面
 */

public class WordFindActi extends AppCompatActivity {
    private Button bt_wordfind;
    private Button bt_tolocalword;
    private EditText search_text;
    private Cursor cursor;
    public  RecyclerView search_recycler;
    private final String TAG = "find";  // log 调试
    private FloatingActionButton bt_floating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_find);
        Init();
    }


    void Init() {
        bt_floating = (FloatingActionButton) findViewById(R.id.btf_towordnotes);

        bt_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordFindActi.this, Wordnotes.class);
                startActivity(intent);
            }
        });


        bt_wordfind = (Button) findViewById(R.id.bt_toWordFind);
        bt_wordfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(" 单词查找 ");
                searchWord(v);
            }
        });

        // 读取数据库的 单词 ， 第一次读取会非常卡顿甚至黑屏， 开多线程
        Connector.getDatabase();
        Word word = DataSupport.findFirst(Word.class);
        Context context = getApplicationContext();
        if (word == null){
            // 最简易的 多线程实现
            new Thread(){
                @Override
                public void run(){

                    readTxt(context,"Result.txt");
                }
            }.start();


        }

        bt_tolocalword = (Button) findViewById(R.id.bt_toLocalWord);
        bt_tolocalword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordFindActi.this, WordLocalActi.class);
                startActivity(intent);
                finish();
            }
        });

        search_text = (EditText) findViewById(R.id.edit_tofind);
        search_recycler = (RecyclerView) findViewById(R.id.search_recycler);


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



    /*
            核心功能
     */
    public void searchWord(View view) {

        List<Word> results = new ArrayList<>();
        String key = search_text.getText().toString();
        String error = "Your input isn't compliant, please input English or Chinese";
        if (key == null) {
            return;
        } else if (!isEnglish(key) && !isChinese(key)) {
            Toast.makeText(view.getContext(), error, Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEnglish(key)) {
            cursor = DataSupport.findBySQL("select * from word " +
                    "where word like ?", key + "%");
        } else if (isChinese(key)) {
            cursor = DataSupport.findBySQL("select * from word where " +
                    "interpretation like ?", "%" + key + "%");
        }
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("word"));
                String translate = cursor.getString(cursor.getColumnIndex("interpretation"));
                results.add(new Word(name, translate));
            } while (cursor.moveToNext());
        }
        if (results.size() > 0) {
            search_recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
            WordFormAdapter adapter = new WordFormAdapter(results);
            search_recycler.setAdapter(adapter);
            Log.d(TAG, "执行查找成功");
            search_recycler.setVisibility(View.VISIBLE);
        } else {
            search_recycler.setVisibility(View.GONE);
            Toast.makeText(view.getContext(), "not found", Toast.LENGTH_SHORT).show();
        }

    }

    public static boolean isEnglish(String str) {
        return str.matches("^[a-zA-Z]*");
    }

    public static boolean isChinese(String str) {
        String regEx = "[\\u4e00-\\u9fa5]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if( keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(WordFindActi.this, AppActivity.class);
            startActivity(intent);
        }

        return super.onKeyDown(keyCode, event);
    }
}

