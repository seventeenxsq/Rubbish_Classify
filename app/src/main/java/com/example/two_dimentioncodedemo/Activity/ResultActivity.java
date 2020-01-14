package com.example.two_dimentioncodedemo.Activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.two_dimentioncodedemo.MainActivity;
import com.example.two_dimentioncodedemo.R;

public class ResultActivity extends MyActivity implements View.OnClickListener {

    ImageView imageView;
    TextView tv1,tv2,tv3;
    private Button btn;

   // public  static  final String SCORE = "SCORE";
    int jifen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        init();
        Animation();
        showresult();
    }

    private void init(){
        imageView=findViewById(R.id.iv_zan);
        imageView.setAlpha(0.5f);//设置透明度数值from 0 to 1
        tv1=findViewById(R.id.tv_1);
        tv2=findViewById(R.id.tv_2);
        tv3=findViewById(R.id.tv_3);
        btn=findViewById(R.id.back);
        btn.setOnClickListener(this);
    }

    private void Animation(){
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(imageView, "translationY", -1000f,0f);
        animator1.setDuration(1000);
        animator1.start();
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(tv1, "translationY", 700,0);
        animator2.setDuration(2000);
        animator2.start();
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(tv2, "translationY", 700f,0f);
        animator3.setDuration(2500);
        animator3.start();
        ObjectAnimator animator4= ObjectAnimator.ofFloat(tv3, "translationY", 700f,0f);
        animator4.setDuration(3000);
        animator4.start();
        ObjectAnimator animator5= ObjectAnimator.ofFloat(btn, "translationY", 700f,0f);
        animator5.setDuration(4000);
        animator5.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
        }
    }

    public void showresult(){
        Intent intent=getIntent();
        int Score=intent.getIntExtra("extra_data",0);
        tv1.setText("答 对 题 数：     "+Score+"/5");
        jifen=Score*10;
        tv2.setText("获得积分：          "+jifen);

    }


}
