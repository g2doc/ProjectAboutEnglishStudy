package com.example.test.Activities.Wordnotes;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wordnotes extends AppCompatActivity {

    // members
    private DicDb dictDb;
    private Uri uri;
    private ListView lv;
    private TextView tv;
    ArrayList<WordRec> words;
    boolean showExp = false;
    ProgressDialog progressDialog;
    Integer progressStatus = 0;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==0x123){
                progressDialog.setProgress(progressStatus);
            }
            else if(msg.what==0x110){
                Toast.makeText(Wordnotes.this, "下载完成", Toast.LENGTH_SHORT).show();
                listAllWords();
                final int[] ids = new int[]{R.id.test_view_1, R.id.test_view_2, R.id.test_view_3, R.id.test_view_4, R.id.test_view_5, R.id.test_view_6,
                        R.id.test_view_7, R.id.test_view_8, R.id.test_view_9, R.id.test_view_10, R.id.test_view_11, R.id.test_view_12,
                        R.id.test_view_13, R.id.test_view_14, R.id.test_view_15,R.id.test_view_16, R.id.test_view_17, R.id.test_view_18,R.id.test_view_19, R.id.test_view_20,
                        R.id.test_view_21,R.id.test_view_22, R.id.test_view_23, R.id.test_view_24,
                        R.id.test_view_25, R.id.test_view_26, R.id.test_view_27,R.id.test_view_28};
                for(int i = 0; i < ids.length; i++){
                    TextView tv = findViewById(ids[i]);
                    tv.setBackgroundColor(Color.argb(255, 210, 105, 30));
                }
            }
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordnotes);
        uri = Uri.parse("content://com.example.jing");

        ActionBar actionBar = getSupportActionBar();
//        getSupportActionBar().show();
        actionBar.setDisplayHomeAsUpEnabled(false);
//        // 空指针报错，因为 找不到标题栏
//
        getSupportActionBar().setTitle("易错单词收录");
        getSupportActionBar().setSubtitle("郑州大学");

        //actionBar.setTitle("易错单词收录");
        //actionBar.setSubtitle("郑州大学");

        tv = (TextView)findViewById(R.id.display_explanation);
        lv = findViewById(R.id.listView);

        Context appContext = this.getApplicationContext();
        dictDb = new DicDb(appContext);

        listFirstLetter();
        listAllWords();
    }


    //上面的26个字母
    private void listFirstLetter(){
        String letters = " ABCDEFGHIJKLMNOPQRSTUVWXYZ ";
        LinearLayout ll = (LinearLayout)findViewById(R.id.word_gallery);
        LinearLayout.LayoutParams textviewLP = new LinearLayout.LayoutParams(40, LinearLayout.LayoutParams.WRAP_CONTENT);
        textviewLP.setMargins(10, 0, 10,0);
        final int[] ids = new int[]{R.id.test_view_1, R.id.test_view_2, R.id.test_view_3, R.id.test_view_4, R.id.test_view_5, R.id.test_view_6, R.id.test_view_7, R.id.test_view_8, R.id.test_view_9, R.id.test_view_10, R.id.test_view_11, R.id.test_view_12,
                R.id.test_view_13, R.id.test_view_14, R.id.test_view_15,R.id.test_view_16, R.id.test_view_17, R.id.test_view_18,R.id.test_view_19, R.id.test_view_20, R.id.test_view_21,R.id.test_view_22, R.id.test_view_23, R.id.test_view_24,
                R.id.test_view_25, R.id.test_view_26, R.id.test_view_27,R.id.test_view_28};
        for(int i = 0; i < letters.length(); i++){
            TextView letterTextView = new TextView(Wordnotes.this);
            letterTextView.setText(String.valueOf(letters.charAt(i)));
            letterTextView.setGravity(Gravity.CENTER);
            letterTextView.setTextColor(Color.argb(255, 255, 255, 255));
            letterTextView.setBackgroundColor(Color.argb(255,210,105,30));
            letterTextView.setClickable(true);
            letterTextView.setId(ids[i]);
            if(i == 0 || i == 27){
                letterTextView.setText("");
            }
            letterTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i = 0; i < ids.length; i++){
                        TextView tv = findViewById(ids[i]);
                        tv.setBackgroundColor(Color.argb(255, 210, 105, 30));
                    }
                    v.setBackgroundColor(Color.argb(255, 50, 255, 50));
                    listSpecWords(((TextView)v).getText().toString());
                }
            });
            ll.addView(letterTextView, textviewLP);
        }
    }

    //列出某字母开头的单词
    private void listSpecWords(String firstLetter){
        Cursor cursor = dictDb.query(null, null, "word like ? or word like ?", new String[]{firstLetter+"%", firstLetter.toLowerCase()+"%"}, "word");
        listWords(cursor);
    }

    //列出所有单词
    private void listAllWords(){
        Cursor cursor = dictDb.query(uri, null, null, null, "word");
        listWords(cursor);
    }

    private void listWords(Cursor cursor){
        if(cursor!=null){
            words = new ArrayList<WordRec>();
            while(cursor.moveToNext()){
                WordRec word = new WordRec();
                word.setWord(cursor.getString(cursor.getColumnIndex("word")));
                word.setExplanation(cursor.getString(cursor.getColumnIndex("explanation")));
                word.setLevel(cursor.getInt(cursor.getColumnIndex("level")));
                word.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                words.add(word);
            }
        }
        if(showExp==false){
            ArrayList<String> data = new ArrayList<>();
            for(int i = 0; i < words.size(); i++){
                data.add(words.get(i).getWord());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
            lv.setAdapter(adapter);
        }
        else{
            ArrayList<Map<String, Object>> data = new ArrayList<>();
            for(int i = 0; i < words.size(); i++){
                Map<String, Object> map = new HashMap<>();
                map.put("word", words.get(i).getWord());
                map.put("explanation", words.get(i).getExplanation());
                data.add(map);
            }
            SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.list_item, new String[]{"word", "explanation"}, new int[]{R.id.list_item_word, R.id.list_item_exp});
            lv.setAdapter(adapter);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            lv.setLayoutParams(lp);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                ViewGroup.LayoutParams params = lv.getLayoutParams();
                params.height = 1300;
                lv.setLayoutParams(params);
                tv.setVisibility(View.VISIBLE);
                tv.setText(words.get(position).getWord()+"\n"+words.get(position).getExplanation());
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
                PopupMenu popup = new PopupMenu(Wordnotes.this, view);
                popup.getMenuInflater().inflate(R.menu.menu_popup, popup.getMenu());
                final WordRec tmpWord = words.get(position);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.del:
                                delWord(tmpWord);
                                break;
                            case R.id.xiugai:
                                changeWord(tmpWord);
                                Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                                break;
                            default:break;
                        }
                        return true;
                    }
                });
                popup.show();
                return true;
            }
        });
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.support, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action1:
                findWord();
                break;
            case R.id.action2:
                addWord();
                break;
            case R.id.menu1:
                downloadWord();
                break;
            case R.id.menu2:
                showExp = !showExp;
                item.setChecked(showExp);
                listWords(null);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    private void findWord(){
        DlghandleWord findWordDlg = new DlghandleWord(this, dictDb, DlghandleWord.FINDWORD, null,new DlghandleWord.OnFinishListener(){
            @Override
            public void onFinish(Cursor cursor){
                listWords(cursor);
            }
        });
        findWordDlg.showDialog();
    }
    private void addWord(){
        DlghandleWord findWordDlg = new DlghandleWord(this, dictDb, DlghandleWord.ADDWORD, null,new DlghandleWord.OnFinishListener(){
            @Override
            public void onFinish(Cursor cursor){
                listWords(cursor);
            }
        });
        findWordDlg.showDialog();
    }
    private void delWord(WordRec word){
        DlghandleWord findWordDlg = new DlghandleWord(this, dictDb, DlghandleWord.DELWORD, word,new DlghandleWord.OnFinishListener(){
            @Override
            public void onFinish(Cursor cursor){
                listWords(cursor);
            }
        });
        findWordDlg.showDialog();
    }
    private void changeWord(WordRec word){
        DlghandleWord findWordDlg = new DlghandleWord(this, dictDb, DlghandleWord.CHANGEWORD, word,new DlghandleWord.OnFinishListener(){
            @Override
            public void onFinish(Cursor cursor){
                listWords(cursor);
            }
        });
        findWordDlg.showDialog();
    }
    private void downloadWord(){
        progressStatus = 0;
        progressDialog = new ProgressDialog(Wordnotes.this);
        progressDialog.setMax(100);
        progressDialog.setTitle("下载词典");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        new Thread(){
            @Override
            public void run(){
                try{
                    progressStatus = 20;
                    handler.sendEmptyMessage(0x123);
                    Thread.sleep(1000);
                    progressStatus = 50;
                    handler.sendEmptyMessage(0x123);
                    Thread.sleep(1000);
                    progressStatus = 80;
                    handler.sendEmptyMessage(0x123);
//                   DownloadDict downloadDict = new DownloadDict(getApplicationContext(), dictDb);
//                    downloadDict.start("http://172.18.187.9:8080/dict/");
                    progressStatus = 90;
                    handler.sendEmptyMessage(0x123);
                    Thread.sleep(1000);
                    progressDialog.dismiss();
                    handler.sendEmptyMessage(0x110);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }.start();
    }




}