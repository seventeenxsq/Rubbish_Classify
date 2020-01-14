package com.example.two_dimentioncodedemo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.two_dimentioncodedemo.MainActivity;
import com.example.two_dimentioncodedemo.R;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends Activity {

    private TextView textView1,textView2;
    private Timer mTimer;//延迟时间类
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
        int flag=WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        setContentView(R.layout.welcome);
        initView();

        new Handler().postDelayed(new Runnable(){

            public void run() {
                textView1.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.VISIBLE);
                //execute the task
            }

        }, 700);
        init();
    }

    private  void  init() {//这个方法帮我们去初始化一些类
        mTimer = new Timer();//时间管理对象
        mTimer.schedule(new TimerTask() {//时间任务
            @Override
            public void run() {
                toMain();
            }
        },  1500);//第二参数数时延迟时间，，3秒

    }
    /**
     * 跳转到mainactivity
     */
    private  void toMain(){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void initView(){
        textView1=findViewById(R.id.tv_line1);
        textView1.setVisibility(View.INVISIBLE);
        textView2=findViewById(R.id.tv_line2);
        textView2.setVisibility(View.INVISIBLE);
    }
}
