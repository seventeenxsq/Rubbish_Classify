package com.example.two_dimentioncodedemo.Activity;


import android.animation.ObjectAnimator;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.two_dimentioncodedemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment {

    private ImageView iv1,iv2,iv3,iv4;
    private Button btndati;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main3, container, false);
        initview(view);
        Animation();
        return view;

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
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(iv4, "translationY", 0f, -40f,0f);
        animator4.setDuration(3000);
        animator4.setRepeatCount(-1);//设置一直重复
        animator4.start();
    }

    private void initview(View view){
        iv1=view.findViewById(R.id.brownround3);
        iv2=view.findViewById(R.id.blueround3);
        iv3=view.findViewById(R.id.redround3);
        iv4=view.findViewById(R.id.greenround3);
        btndati=view.findViewById(R.id.btn_dati);
        btndati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Service.VIBRATOR_SERVICE);
                vibrator.vibrate(40);
                Intent intent=new Intent(getActivity(),DatiGameActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

    }

}
