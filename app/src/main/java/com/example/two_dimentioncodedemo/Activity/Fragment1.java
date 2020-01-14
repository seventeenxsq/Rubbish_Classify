package com.example.two_dimentioncodedemo.Activity;

import android.animation.ObjectAnimator;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.two_dimentioncodedemo.MainActivity;
import com.example.two_dimentioncodedemo.R;

import butterknife.BindView;

public class Fragment1 extends Fragment implements View.OnClickListener {

    private ImageView ivGreen,ivRed,ivBrown,ivXiangji;
    private Button btntakephoto;
    private Button btn1,btn2,btn3,btn4;
    private TextView city;

    @Override//将fragment与布局文件联系起来
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        //
        View view=inflater.inflate(R.layout.fragment_main1,container,false);
        btntakephoto=view.findViewById(R.id.btn_takephoto);
        initfindview(view);
        Animatation();
        return view;
    }

    private void Animatation(){
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(ivGreen, "translationY", 0f, -40f,0f);
        animator1.setDuration(3000);
        animator1.setRepeatCount(-1);//设置一直重复
        animator1.start();
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(ivBrown, "translationY", 25f, 70f,25f);
        animator2.setDuration(3000);
        animator2.setRepeatCount(-1);//设置一直重复
        animator2.start();
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(ivRed, "translationY", 10f, -30f,10f);
        animator3.setDuration(3000);
        animator3.setRepeatCount(-1);//设置一直重复
        animator3.start();
        ObjectAnimator animator5=ObjectAnimator.ofFloat(ivXiangji,"scaleX",1f,1.1f,1f);
        animator5.setDuration(1500);
        animator5.setRepeatCount(-1);//设置一直重复
        animator5.start();
        ObjectAnimator animator6=ObjectAnimator.ofFloat(ivXiangji,"scaleY",1f,1.1f,1f);
        animator6.setDuration(1500);
        animator6.setRepeatCount(-1);//设置一直重复
        animator6.start();
    }

    private void initfindview(View view){
        btntakephoto=view.findViewById(R.id.btn_takephoto);
        ivBrown=view.findViewById(R.id.brownround);
        ivGreen=view.findViewById(R.id.greenround);
        ivRed=view.findViewById(R.id.redround);
        btn1=view.findViewById(R.id.btn1);
        btn2=view.findViewById(R.id.btn2);
        btn3=view.findViewById(R.id.btn3);
        btn4=view.findViewById(R.id.btn4);
        city=view.findViewById(R.id.tv_city);
        city.setOnClickListener(this);
        btntakephoto.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        ivBrown.setOnClickListener(this);
        ivRed.setOnClickListener(this);

    }

    public void onClick(View v) {
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(30);
        switch (v.getId()){
            case R.id.btn_takephoto:
                startActivity(new Intent(getActivity(),ImageActivity.class));
                break;
            case R.id.btn1:
                Intent intent1 = new Intent(getActivity(), InfoActivity.class);
                intent1.putExtra(InfoActivity.CONTENT_URL,"http://baike.sm.cn/item/d1a955cf4368c08f6b2ddb16d055621e.html?from=smsc&uc_param_str=dnntnwvepffrgibijbpr");
                startActivity(intent1);
                break;
            case R.id.btn2:
                Intent intent2 = new Intent(getActivity(), InfoActivity.class);
                intent2.putExtra(InfoActivity.CONTENT_URL,"https://baike.baidu.com/item/%E5%B9%B2%E5%9E%83%E5%9C%BE/23589706?ivk_sa=1022817p");
                startActivity(intent2);
                break;
            case R.id.btn3:
                Intent intent3 = new Intent(getActivity(), InfoActivity.class);
                intent3.putExtra(InfoActivity.CONTENT_URL,"https://m.baidu.com/sf_bk/item/%E6%B9%BF%E5%9E%83%E5%9C%BE/23589703?fromtitle=%E6%9C%89%E6%9C%BA%E5%9E%83%E5%9C%BE&fromid=3467944&fr=aladdin&ms=1&rid=7207784841057021943\n");
                startActivity(intent3);
                break;
            case R.id.btn4:
                Intent intent4 = new Intent(getActivity(), InfoActivity.class);
                intent4.putExtra(InfoActivity.CONTENT_URL,"https://m.baidu.com/sf_bk/item/%E6%9C%89%E5%AE%B3%E5%9E%83%E5%9C%BE/5677220?fr=kg_general&ms=1&rid=7951982560968948558");
                startActivity(intent4);
                break;
            case R.id.tv_city:
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setTitle("选 择 城 市");
                //指定下拉列表的显示数据
                final String[] cities = {"上海","广州","北京","深圳","太原","芜湖","南京","青岛","大连","唐山"};
                //设置下拉列表选择项
                builder.setItems(cities, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        city.setText(cities[which]);
                    }
                });
                //最后我们要显示alterdialog
                builder.show();
                break;
            case R.id.brownround:
                Toast.makeText(v.getContext(),"你是什么垃圾？？？",Toast.LENGTH_SHORT).show();
                break;
            case R.id.redround:
                Toast.makeText(v.getContext(),"我是有害垃圾",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}



