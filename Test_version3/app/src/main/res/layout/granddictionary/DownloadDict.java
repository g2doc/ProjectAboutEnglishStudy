package com.example.jing.granddictionary;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class DownloadDict {
    private Context mContext;
    private DictDb dictDb;
    Uri uri;
    public DownloadDict(Context mContext, DictDb dictDb){
        this.mContext = mContext;
        this.dictDb = dictDb;
        this.uri = Uri.parse("content://com.example.jing");
    }
    public void start(String apiUrl)throws Exception{
        //从服务器中获取数据
        String res = getJsonData(apiUrl);
        //解析Json格式
        List<WordRec> words = parserJSON(res);
        //写入到数据库中
        for(int i = 0; i < words.size(); i++){
            ContentValues cv = new ContentValues();
            cv.put("word", words.get(i).getWord());
            cv.put("explanation", words.get(i).getExplanation());
            cv.put("level", words.get(i).getLevel());
            dictDb.insert(uri, cv);
            Log.e("num",String.valueOf(i));
        }
    }

    public static List<WordRec> parserJSON(String str){
        JSONArray wordsArray=null;
        try {
            wordsArray = new JSONArray(str);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        List<WordRec> words = new ArrayList<>();
        for(int i = 0; i < wordsArray.length(); i++){
            WordRec word = new WordRec();
            JSONObject wordObj = wordsArray.optJSONObject(i);
            word.setId(i);
            word.setWord(wordObj.optString("word"));
            word.setExplanation(wordObj.optString("explanation"));
            word.setLevel(wordObj.optInt("level"));
            words.add(word);
        }

        return words;
    }


    static String getInputStreamText(InputStream is) throws Exception {
        InputStreamReader isr = new InputStreamReader(is, "utf8");
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb=new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    public String getJsonData(String apiUrl)throws Exception{
        URL url= new URL(apiUrl);
        URLConnection open = url.openConnection();
        InputStream inputStream = open.getInputStream();
        return getInputStreamText(inputStream);
    }
}
