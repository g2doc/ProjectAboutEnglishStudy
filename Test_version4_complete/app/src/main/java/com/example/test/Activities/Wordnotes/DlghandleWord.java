package com.example.test.Activities.Wordnotes;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.R;


public class DlghandleWord {

    public interface OnFinishListener{
        public void onFinish(Cursor cursor);
    }
    private OnFinishListener listener;
    private DicDb dictDb;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private Context context;
    private View view;
    private WordRec word;
    public static final int ADDWORD = 0;
    public static final int FINDWORD = 1;
    public static final int DELWORD = 2;
    public static final int CHANGEWORD = 3;

    public DlghandleWord(Context context, DicDb dictDb, int DlgKind,WordRec word, OnFinishListener listener){
        this.context = context;
        this.dictDb = dictDb;
        this.listener = listener;
        builder = new AlertDialog.Builder(context);
        this.word = word;
        buildDlg(DlgKind);
    }

    private void buildDlg(int DlgKind){
        switch(DlgKind){
            case ADDWORD:
                buildAddDlg();
                break;
            case FINDWORD:
                buildFindDlg();
                break;
            case DELWORD:
                buildDelDlg();
                break;
            case CHANGEWORD:
                buildChangeDlg();
                break;
            default:
                break;
        }
    }

    private void buildAddDlg(){
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.add_word_form, null, false);
        alertDialog = builder
                .setIcon(R.mipmap.dict)
                .setTitle("增加单词")
                .setView(view)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addNewWord();
                    }
                })
                .create();
    }
    private void buildFindDlg(){
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.find_word_form, null, false);
        alertDialog = builder
                .setIcon(R.mipmap.dict)
                .setTitle("查找单词")
                .setView(view)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        findWord();
                    }
                })
                .create();
    }
    private void buildDelDlg(){
        alertDialog = builder
                .setIcon(R.mipmap.dict)
                .setTitle("删除单词")
                .setMessage("是否确定要删除单词"+this.word.getWord())
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delWord();
                    }
                })
                .create();
    }
    private void buildChangeDlg(){
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.change_word_form, null, false);
        alertDialog = builder
                .setIcon(R.mipmap.dict)
                .setTitle("修改单词")
                .setView(view)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changeWord();
                    }
                })
                .create();
        EditText ed1 = view.findViewById(R.id.word_spell_2);
        EditText ed2 = view.findViewById(R.id.word_explanation_2);
        EditText ed3 = view.findViewById(R.id.word_level_2);
        ed1.setText(word.getWord());
        ed2.setText(word.getExplanation());
        ed3.setText(String.valueOf(word.getLevel()));
    }

    public void showDialog(){
        alertDialog.show();
    }

    private void addNewWord(){
        EditText word_spell = (EditText)view.findViewById(R.id.word_spell);
        String word = word_spell.getText().toString();

        EditText word_explanation = (EditText)view.findViewById(R.id.word_explanation);
        String explanation = word_explanation.getText().toString();

        EditText word_level = (EditText)view.findViewById(R.id.word_level);
        if(isNumeric((word_level.getText().toString()))==false){
            Toast.makeText(context, "级别只能输入0-6"+"你的输入为"+word_level.getText().toString()+")",Toast.LENGTH_SHORT).show();
            return;
        }
        int level = Integer.parseInt(word_level.getText().toString());

        CheckBox checkBox = (CheckBox) view.findViewById(R.id.word_cover);
        boolean cover = checkBox.isChecked()?true:false;
        if(word.length()==0){
            Toast.makeText(context, "单词或级别不能为空", Toast.LENGTH_SHORT).show();
        }
        else{
            Cursor cursor = dictDb.query(null, null, "word=?", new String[]{word}, null);
            if(cursor.getCount()==0){
                ContentValues cv = new ContentValues();
                cv.put("word", word);
                cv.put("explanation", explanation);
                cv.put("level", level);
                dictDb.insert(null, cv);
                Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
            }
            else{
                if(cover==true){
                    ContentValues cv = new ContentValues();
                    cv.put("word", word);
                    cv.put("explanation", explanation);
                    cv.put("level", level);
                    dictDb.update(null,cv,"_id=?", new String[]{String.valueOf(this.word.getId())});
                    Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "添加失败，单词库已有该单词", Toast.LENGTH_SHORT).show();
                }
            }
        }
        Cursor newCursor = dictDb.query(null, null, null, null, "word");
        listener.onFinish(newCursor);
    }
    private void findWord(){
        EditText word_input = (EditText)view.findViewById(R.id.word_spell_3);
        String word_seg = word_input.getText().toString();
        Cursor cursor = dictDb.query(null, null, "word like ?", new String[]{"%"+word_seg+"%"}, "word");
        listener.onFinish(cursor);
//        Toast.makeText(context, word_seg, Toast.LENGTH_LONG).show();

    }

    private void delWord(){
        int id = word.getId();
        int ret = dictDb.delete(null, "_id=?", new String[]{String.valueOf(id)});
        Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
        Cursor newCursor = dictDb.query(null, null, null, null, "word");
        listener.onFinish(newCursor);
        ////删除操作
    }
    private void changeWord(){
        EditText ed1 = view.findViewById(R.id.word_spell_2);
        EditText ed2 = view.findViewById(R.id.word_explanation_2);
        EditText ed3 = view.findViewById(R.id.word_level_2);
        String word_spell = ed1.getText().toString();
        String explanation = ed2.getText().toString();
        int level = Integer.parseInt(ed3.getText().toString());
        ContentValues cv = new ContentValues();
        cv.put("word", word_spell);
        cv.put("explanation", explanation);
        cv.put("level", level);
        dictDb.update(null,cv,"_id=?",new String[]{String.valueOf(word.getId())});
        Cursor newCursor = dictDb.query(null, null, null, null, "word");
        listener.onFinish(newCursor);
    }

    public final static boolean isNumeric(String s) {
        if (s != null && !"".equals(s.trim()))
            return s.matches("^[0-6]$");
        else
            return false;
    }

}
