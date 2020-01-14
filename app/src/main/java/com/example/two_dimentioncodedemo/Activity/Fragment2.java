package com.example.two_dimentioncodedemo.Activity;


import android.animation.ObjectAnimator;
import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.two_dimentioncodedemo.Bean.Garbage;
import com.example.two_dimentioncodedemo.HelperClass.HttpHelper;
import com.example.two_dimentioncodedemo.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Response;

public class Fragment2 extends Fragment implements View.OnClickListener {

    private String address, name;
    private final String applyURL = "http://api.tianapi.com/txapi/lajifenlei/?&";
    private final String AppKey = "key=b6dfe03714edcfbb04e9228299d57216";
    private Button btn;
    private TextView tv_data;
    private EditText ed_input;
    private ImageView iv1,iv2,iv3,ivcategory,biaoyu;
    private   ImageView  ivfront;


    @Override//将fragment与布局文件联系起来
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main2,container,false);
        initview(view);
        Animation();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search();
            }
        });
        return  view;
    }

    private void initview(View view){
        btn = view.findViewById(R.id.btn_takephoto);
        tv_data =view. findViewById(R.id.tv_output);
        ed_input = view.findViewById(R.id.ed_info);
        ivcategory=view.findViewById(R.id.iv_CategoryPicture);
        iv1=view.findViewById(R.id.iv_blue);
        iv2=view.findViewById(R.id.iv_brown);
        iv3=view.findViewById(R.id.iv_green);
        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        ivfront=view.findViewById(R.id.iv_front);
        biaoyu=view.findViewById(R.id.iv_biaoyu);
    }

    private void Animation(){
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(iv1, "translationY", 0f, 40f,0f);
        animator1.setDuration(4000);
        animator1.setRepeatCount(-1);//设置一直重复
        animator1.start();
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(iv2, "translationY", 0f, -40f,0f);
        animator2.setDuration(4000);
        animator2.setRepeatCount(-1);//设置一直重复
        animator2.start();
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(iv3, "translationY", 0f, 40f,0f);
        animator3.setDuration(3000);
        animator3.setRepeatCount(-1);//设置一直重复
        animator3.start();
    }

    private void Search() {
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(30);
        ivfront.setVisibility(View.INVISIBLE);
        biaoyu.setVisibility(View.INVISIBLE);
        name = ed_input.getText().toString();
        if (name.equals("")) {
            showResponse("大锅请输入内容");
        } else {
            try {
                name = ed_input.getText().toString();
                name = URLEncoder.encode(name, "UTF-8");//将name改成中文编码UTF-8
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            //将详细地址拼接起来
            address = applyURL + AppKey + "&word=" + name;
            HttpHelper.sendOkHttpRequest(address, new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("response", "fail");
                    showResponse("网络故障QAQ");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //得到服务器返回的具体内容
                    String responseData = response.body().string();
                    Gson gson = new Gson();
                    //将json解析成javabean对象
                    Garbage garbage = gson.fromJson(responseData, Garbage.class);
                    if (garbage.getCode() == 250) {
                        showResponse("亲，找不到此垃圾QAQ");
                    } else {
                        int i=garbage.getNewslist().get(0).getType();
                        Transform(i);
                        String info=null;
                        info="名称： "+garbage.getNewslist().get(0).getName()+"\n"
                                +"种类："+Transform(garbage.getNewslist().get(0).getType())+"\n"
                                +"解释："+garbage.getNewslist().get(0).getExplain()+"\n"
                                +"内容包括："+garbage.getNewslist().get(0).getContain()+"\n"
                                +"小提示@(^_^)@: "+garbage.getNewslist().get(0).getTip();
                        showResponse(info,i);
                    }
                }
            });
        }


    }

    private String Transform(int i){
        String str=null;
        switch (i){
            case 0:
                str= "可回收物/垃圾";
                break;
            case 1:
                str= "有害垃圾";
                break;
            case 2:
                str= "湿垃圾/厨余垃圾";
                break;
            case 3:
                str= "干垃圾/不可回收垃圾";
                break;
        }
        return str;
    }

    private void showResponse(final String response,final int i) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (i){
                    case 0:
                        ivcategory.setImageResource(R.mipmap.b0);    //要求传递一个资源ID ;
                        break;
                    case 1:
                        ivcategory.setImageResource(R.mipmap.b1);
                        break;
                    case 2:
                        ivcategory.setImageResource(R.mipmap.b2);
                        break;
                    case 3:
                        ivcategory.setImageResource(R.mipmap.b3);
                        break;

                }
                //结果显示时将线程切换成UI主线程，将结果显示到界面上
                tv_data.setText(response);
            }
        });
    }

    private void showResponse(final String response){

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_data.setText(response);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_blue:
                Toast.makeText(v.getContext(), "猜猜我是什么垃圾", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_green:
                Toast.makeText(v.getContext(), "我是可回收垃圾", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_brown:
                Toast.makeText(v.getContext(), "我是传说中的干垃圾", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
