package com.example.test.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.AppActivity;
import com.example.test.R;
import com.example.test.Word;
import com.example.test.WordFormAdapter;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 *
 *  version 3.0 重构  by zhang
 */

public class WordLocalActi extends AppCompatActivity {

    private TextView wordNum;  // 存放单词数量
    private List<Word> mWord;   // 定义 List<Word> 存放 word

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_local);

        back = (ImageView)findViewById(R.id.image_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordLocalActi.this, AppActivity.class);
                startActivity(intent);
                finish();
            }
        });

        initWord();
        RecyclerView wordFormRecycler = (RecyclerView) findViewById(R.id.loal_recycler);
        wordNum = findViewById(R.id.word_num);
        wordNum.setText("共有 "+mWord.size()+" 个单词");

        //  关键 代码进行学习
        wordFormRecycler.setLayoutManager(new LinearLayoutManager(this));
        WordFormAdapter adapter = new WordFormAdapter(mWord);
        wordFormRecycler.setAdapter(adapter);
    }

    // 初始化 List<Word> 词库
    private List<Word>  initWord(){
        mWord = DataSupport.findAll(Word.class);   //  Word 进行 绑定
        return mWord;

    }

    public void backApp(){
        // 返回到 App 界面
    }


    // rewrite  onkeydown
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if( keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(WordLocalActi.this, WordFindActi.class);
            startActivity(intent);

        }

        return super.onKeyDown(keyCode, event);
    }
}

















