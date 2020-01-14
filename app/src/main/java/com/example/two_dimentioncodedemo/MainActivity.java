package com.example.two_dimentioncodedemo;

import android.app.Service;
import android.os.Vibrator;
import android.support.v4.app.Fragment;;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.example.two_dimentioncodedemo.Activity.Fragment1;
import com.example.two_dimentioncodedemo.Activity.Fragment2;
import com.example.two_dimentioncodedemo.Activity.Fragment3;
import com.example.two_dimentioncodedemo.Activity.MyActivity;
import com.example.two_dimentioncodedemo.StatusBarUtils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends MyActivity implements View.OnClickListener {

    ImageButton button1,button2,button3;

    private List<Fragment> list;
    private ViewPager viewPager;
    private TabFragmentPagerAdapter adapter;   //将fragment与viewpager绑定


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.transparencyBar(this);
        StatusBarUtil.BlackFontStatusBar(this.getWindow());
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView(){
        button1=findViewById(R.id.btn_1);
        button2=findViewById(R.id.btn_2);
        button3=findViewById(R.id.btn_3);


        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        viewPager =  findViewById(R.id.viewpager);
        //绑定点击事件

        //把Fragment添加到List集合里面
        list = new ArrayList<>();
        list.add(new Fragment1());
        list.add(new Fragment2());
        list.add(new Fragment3());
        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyPagerChangeListener());
    }



    /**
     * 设置一个ViewPager的侦听事件，当左右滑动ViewPager时菜单栏被选中状态跟着改变
     *
     */
    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            resetImg(); //将图片全部默认为不选中
            int currentItem = viewPager.getCurrentItem();
            switch (currentItem) {
                case 0:
                    button1.setImageResource(R.mipmap.btn_1_green);
                    break;
                case 1:
                    button2.setImageResource(R.mipmap.btn_2_green);
                    //frontImage.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    button3.setImageResource(R.mipmap.icon2);
                    break;
                default:
                    break;
            }

            }

        }

    private void resetImg() {
        button1.setImageResource(R.mipmap.eyeicon);
        button2.setImageResource(R.mipmap.searchicon);
        button3.setImageResource(R.mipmap.icon3);
    }

    @Override
    public void onClick(View v) {
        Vibrator vibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(30);
        resetImg();
        switch (v.getId()) {
            case R.id.btn_1:
                viewPager.setCurrentItem(0);// 跳到第一个页面
                button1.setImageResource(R.mipmap.btn_1_green); // 图片变为选中
                break;
            case R.id.btn_2:
                viewPager.setCurrentItem(1);
                button2.setImageResource(R.mipmap.btn_2_green);
                break;
            case R.id.btn_3:
                viewPager.setCurrentItem(2);
                button3.setImageResource(R.mipmap.icon2);
                break;
            default:
                break;
        }
    }
}

