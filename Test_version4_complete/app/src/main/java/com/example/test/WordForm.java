package com.example.test;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 *  WordForm  对应本地的单词数据库
 *
 *
 */

public class WordForm extends AppCompatActivity {
    private TextView wordNum;
    private List<Word> mWord;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_form);

        initWord();

        RecyclerView wordFormRecycler = (RecyclerView) findViewById(R.id.word_local_recycler);
        wordNum = findViewById(R.id.word_num);
        wordNum.setText("共有 "+mWord.size()+" 个单词");

        wordFormRecycler.setLayoutManager(new LinearLayoutManager(this));
        WordFormAdapter adapter = new WordFormAdapter(mWord);
        wordFormRecycler.setAdapter(adapter);

    }

    private List<Word> initWord(){
        mWord = DataSupport.findAll(Word.class);
        return mWord;
    }


    public void backMain(View view) {
        finish();
    }
}