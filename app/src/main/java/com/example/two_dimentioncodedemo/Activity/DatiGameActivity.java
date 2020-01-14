package com.example.two_dimentioncodedemo.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.two_dimentioncodedemo.HelperClass.MyDatabaseHelper;
import com.example.two_dimentioncodedemo.R;
import com.example.two_dimentioncodedemo.StatusBarUtils.StatusBarUtil;

public class DatiGameActivity extends MyActivity implements View.OnClickListener {

    private TextView tihao,qname;
    private CheckBox answer1,answer2,answer3,answer4;
    private ImageView iv;
    private Button ly;
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
     int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dati_game);
        //数据库对象获取权限
        dbHelper=new MyDatabaseHelper(this,"Dati.db",null,1);
        db=dbHelper.getWritableDatabase();
        init();
        addallDatas();
        //全局的光标
        cursor = db.query("Dati", null, null, null, null, null, null, null);
        //展示数据 光标移到第一层
        cursor.moveToFirst();
        showResult();
    }


    private void init(){
        iv=findViewById(R.id.datibackground);
        ly=findViewById(R.id.xiayiti);
        iv.setAlpha(0.3f);//设置透明度数值from 0 to 1
        ly.setOnClickListener(this);
        qname=findViewById(R.id.title);
        tihao=findViewById(R.id.titleid);
        answer1=findViewById(R.id.choose1);
        answer2=findViewById(R.id.choose2);
        answer3=findViewById(R.id.choose3);
        answer4=findViewById(R.id.choose4);
    }

    private void addallDatas(){
        addDatas("第 一 题",
                "过敏药物属于（）? 需要特殊安全处理",
                "有害垃圾",
                "可回收垃圾",
                "餐厨垃圾",
                "干垃圾"
        );
        addDatas("第 二 题",
                "番茄酱属于以下哪种垃圾？",
                "有害垃圾",
                "可回收垃圾",
                "餐厨垃圾",
                "干垃圾"
        );
        addDatas("第 三 题",
                "过期的感冒药属于以下哪种垃圾？",
                "有害垃圾",
                "可回收垃圾",
                "餐厨垃圾",
                "干垃圾"
        );
        addDatas("第 四 题",
                "以下哪些属于有害垃圾？（多选）",
                "有害垃圾",
                "可回收垃圾",
                "餐厨垃圾",
                "干垃圾"
        );
        addDatas("第 五 题",
                "以下哪种垃圾可以当做肥料滋养庄家？",
                "有害垃圾",
                "可回收垃圾",
                "餐厨垃圾",
                "干垃圾"
        );
    }

    @Override
    public void onClick(View v) {
        Countsocre(3);
        switch (v.getId()){
            case R.id.xiayiti:
                if(cursor.moveToNext()){
                   // cursor.moveToNext();
                    initCheckbox();
                    showResult();
                    Log.e("showresult","has changed") ;
                }else{
                    Intent intent=new Intent(this,ResultActivity.class);
                    intent.putExtra("extra_data",score);
                    startActivity(intent);
                    finish();
                }
        }
    }
    public void addDatas(String titlenum,String question,String answer1,String answer2,String answer3,String answer4){
        //获取读写权限 是用SQLite数据库对象来操作
        ContentValues values=new ContentValues();
        //组装数据
        values.put("question",question);
        values.put("titlenum",titlenum);
        values.put("answer1",answer1);
        values.put("answer2",answer2);
        values.put("answer3",answer3);
        values.put("answer4",answer4);
        db.insert("Dati",null,values);
        Log.e("addData","succeed");
        values.clear();
    }

    public void showResult() {
      //  Cursor cursor = db.query("Dati", null, null, null, null, null, null, null);
         {
            String ques = cursor.getString(cursor.getColumnIndex("question"));
            qname.setText(ques);
            String titlenum=cursor.getString(cursor.getColumnIndex("titlenum"));
            tihao.setText(titlenum);
            String answer_1=cursor.getString(cursor.getColumnIndex("answer1"));
            answer1.setText(answer_1);
            String answer_2=cursor.getString(cursor.getColumnIndex("answer2"));
            answer2.setText(answer_2);
            String answer_3=cursor.getString(cursor.getColumnIndex("answer3"));
            answer3.setText(answer_3);
            String answer_4=cursor.getString(cursor.getColumnIndex("answer4"));
            answer4.setText(answer_4);
             Log.e("showResult", "succeed");
        }
    }

    @Override
    protected void onResume() {
        db.execSQL("delete from Dati");
        super.onResume();
    }
    private void Countsocre(int theTruenum){
        switch(theTruenum){
            case 1:
                if (answer1.isChecked()){
                    score++;
                    break;
                }
            case 2:
                if (answer2.isChecked()){
                    score++;
                    break;
                }
            case 3:
                if (answer3.isChecked()){
                    score++;
                    break;
                }
            case 4:
                if (answer4.isChecked()){
                    score++;
                    break;
                }
        }

    }

    private void initCheckbox(){
        answer1.setChecked(false);
        answer2.setChecked(false);
        answer3.setChecked(false);
        answer4.setChecked(false);
    }


}
