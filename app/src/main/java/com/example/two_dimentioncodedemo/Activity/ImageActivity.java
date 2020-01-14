package com.example.two_dimentioncodedemo.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.two_dimentioncodedemo.Base64Utils.Base64Util;
import com.example.two_dimentioncodedemo.Bean.Garbage;
import com.example.two_dimentioncodedemo.Bean.GarbageBean;
import com.example.two_dimentioncodedemo.HelperClass.HttpHelper;
import com.example.two_dimentioncodedemo.HelperClass.HttpUtil;
import com.example.two_dimentioncodedemo.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.internal.tls.OkHostnameVerifier;

public class ImageActivity extends MyActivity implements SurfaceHolder.Callback, View.OnClickListener
        , Camera.PictureCallback{

    //相机获取画面的幕布
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private static final String TAG="CameraActivity";
    private final int CAMERA_FRONT=1;//前置摄像头
    private final int CAMERA_BEHIND=0;//后置摄像头
    private int CAMERA_NOW=CAMERA_BEHIND;//默认打开后置摄像头
    private Button btn,btn2;
    private Camera mCamera;
    private LinearLayout resultbar;
    String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/advanced_general";
    String accessToken = "24.cf9c813c54d451a593da365aea65643e.2592000.1573377803.282335-16986339";

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        //设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initViews();

    }

    private void initViews(){
        mSurfaceView=findViewById(R.id.surfaceView);
        mSurfaceHolder=mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);//添加回调接口，这一句很重要，一定要添加
        btn=findViewById(R.id.btn_confirm);
        btn.setOnClickListener(this);
        btn2=findViewById(R.id.btn_confirm2);
        btn2.setOnClickListener(this);
    }

    public Camera getCamera(){//初始化摄像头
        Camera camera;
        try {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            camera=Camera.open(CAMERA_NOW);//默认打开后置摄像头
//            Log.e("getCamera method inner", camera.toString());
            return camera;
        }catch (Exception e){
//            Log.i(TAG,"open camera failed");
            e.printStackTrace();
        }
        return null;
    }
    //开始预览
    public void startPreview(Camera camera,SurfaceHolder surfaceHolder){
        if(camera==null){
            mCamera=getCamera();
        }else{
            mCamera=camera;
        }
        if(surfaceHolder==null){
            surfaceHolder=mSurfaceView.getHolder();
        }
        try {
            mCamera.setDisplayOrientation(90);//安卓默认是横屏，旋转转为90度，转为竖屏
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.startPreview();
//            mCamera.autoFocus(new Camera.AutoFocusCallback() {
//                @Override
//                public void onAutoFocus(boolean success, Camera camera) {
//            mCamera.cancelAutoFocus();
//                }
//            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void releaseCamera(){
        mCamera.stopPreview();
        if(mCamera==null){
            return;
        }
        mCamera.release();
        mCamera=null;
        mSurfaceHolder=null;
    }

    @Override
    protected void onDestroy() {
        Log.e("ImageActivity","onDestroy");
        releaseCamera();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        Log.e("ImageActivity","onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.e("ImageActivity","onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e("ImageActivity","onPause");
        super.onPause();
    }

    @Override
    protected void onRestart() {
       Log.e("ImageActivity","onRestart");
       mCamera.startPreview();
        super.onRestart();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        startPreview(mCamera, mSurfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        startPreview(mCamera, mSurfaceHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    /////返回信息
    public void getCategory(String garbage) throws IOException {

        final String url="https://service.xiaoyuan.net.cn/garbage/index/search?kw=";
        //地 址 ：
        final String garbageinput=garbage;
        String garbage_encode = URLEncoder.encode(garbage, "UTF-8");
        String address=url+garbage_encode;
        //返回的综合数组
        final List result=new ArrayList();

        HttpHelper.sendOkHttpRequest(address, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.add(0);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //解析返回的数据
                String responseData=response.body().string();
                Gson gson = new Gson();
                //将json解析成javabean对象
                GarbageBean garbageList = gson.fromJson(responseData, GarbageBean.class);
                //id码
                result.add(garbageList.getData().get(0).getId());
                //name
                result.add(garbageList.getData().get(0).getName());
                //int型
                result.add(garbageList.getData().get(0).getType());
                //category
                result.add(garbageList.getData().get(0).getCategory());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder=new AlertDialog.Builder(ImageActivity.this);
                        builder.setCancelable(false);
                        if(!garbageinput.equals("图像模糊")){
                            //如果不是未识别的垃圾
                            if(!(result.get(0)).equals(0)){
                                //垃圾种类
                                builder.setTitle(result.get(3).toString());
                                //设置图标
                                builder.setIcon(setIcon((int)result.get(2)));
                                //builder.setMessage("垃圾为："+inquire+"\n"+"投放要求:\n"+lajiinformation((int)inquire_result.get(2)));
                                builder.setMessage("垃圾为："+garbageinput+"\n"+"投放要求:\n"+lajiinformation((int)result.get(2)));
                                builder.setPositiveButton("确定",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                            }
                                        });
                                builder.setNegativeButton("识别错误？点我更正", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        upload();
                                    }
                                });
                                builder.show();
                            }
                            else{
                                builder.setTitle("可回收垃圾");
                                //设置图标
                                builder.setIcon(setIcon(3));
                                builder.setMessage("垃圾为："+garbageinput+"\n"+"投放要求:\n"+lajiinformation(3)
                                );
                                builder.setPositiveButton("确定",null);
                                builder.setNegativeButton("识别错误？点我更正", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        upload();
                                    }
                                });
                                builder.show();
                            }
                }
                else
                {
                    builder.setMessage("亲，太糊了 \n我也不知道是什么垃圾QAQ！");
                    builder.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                     builder.show();
                }
                    }
                });
                }
//                if(!garbage.equals("图像模糊")){
//                    //如果不是未识别的垃圾
//                    if(!(result.get(0)).equals(0)){
//                        //垃圾种类
//                        builder.setTitle(result.get(3).toString());
//                        //设置图标
//                        builder.setIcon(setIcon((int)result.get(2)));
//                        //builder.setMessage("垃圾为："+inquire+"\n"+"投放要求:\n"+lajiinformation((int)inquire_result.get(2)));
//                        builder.setMessage("sdFDSD");
//                        builder.setPositiveButton("确定",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                    }
//                                });
//                        builder.setNegativeButton("识别错误？点我更正", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                upload();
//                            }
//                        });
//                        builder.show();
//                    }
//                    else{
//                        builder.setTitle("我从未见过这种东东垃圾>_<");
//                        builder.setMessage("此东东不是垃圾or垃圾未收录"
//                        );
//                        builder.setPositiveButton("确定",null);
//                        builder.setNegativeButton("识别错误？点我更正", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                upload();
//                            }
//                        });
//                        builder.show();
//                    }
//                }
//                else
//                {
//                    builder.setMessage("亲，太糊了 \n我也不知道是什么垃圾QAQ！");
//                    builder.setPositiveButton("确定",
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                }
//                            });
//                    // builder.show();
//                }
        });

        return;
      }
//

    @Override//重写拍照之后获得数据的回调
    public void onPictureTaken(byte[] bytes, Camera camera) {//bytes为图像字节
        //########实例化contentResolver相当于一个临时的内容提供器########
        //它将压缩的图片,,在此我们不需要将图片保存
        //ContentResolver resolver = getContentResolver();
        //Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        //Log.e("picture", "save is ok?");
        //MediaStore.Images.Media.insertImage(resolver, bitmap, "t", "des");//返回暂时存放图像的URL
        //拍照后重新开始预览
        camera.startPreview();
        //显示结果
        Showresult(bytes);
    }

    public Bitmap getPicFromBytes(byte[] bytes,
                                  BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
                        opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }

    @Override//按快门拍照
    public void onClick(View v) {
        Vibrator vibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(30);
        switch (v.getId()){
            case R.id.btn_confirm:
                mCamera.takePicture(null, null, this);
                break;
            case R.id.btn_confirm2:
                Intent intent=new Intent(this, ImageActivity2.class);
                startActivity(intent);
                Toast toast=Toast.makeText(this,"模型识别模式",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                finish();
                break;
        }
    }

    private void upload(){
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(ImageActivity.this);
        builder1.setTitle("上传垃圾信息");
//
        //用布局填充器填充
        LayoutInflater factory = LayoutInflater.from(ImageActivity.this);//提示框
        //用布局充斥填充器对象使成为一个view
        final View view = factory.inflate(R.layout.twoedittext, null);//这里必须是final的
        builder1.setCancelable(false);
        builder1.setView(view);
        builder1.setNegativeButton("取消", null);
        builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editText1=view.findViewById(R.id.tv_name);
                EditText editText2=view.findViewById(R.id.tv_kind);
                if(!((editText1.getText().toString().equals(""))||(editText2.getText().toString().equals("")))){
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(ImageActivity.this);
                    builder2.setTitle("上传成功");
                    builder2.setMessage("感谢您的矫正支持！\n核实成功后，将获得积分+10");
                    builder2.setPositiveButton("确定", null);
                    builder2.show();
                }
                else{
                    Toast.makeText(getBaseContext(),"请输入内容",Toast.LENGTH_LONG).show();
                }
            }
        });
        builder1.show();
    }

    //拍照后显示结果
    private void Showresult(byte[] bytes){
        try {
            String imgStr = Base64Util.encode(bytes);//用64编码给图像数组编码
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");//转换成UTF-8
            String param = "image=" + imgParam;
//            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
//            //String accessToken = "[调用鉴权接口获取的token]";  //密钥
            String result = HttpUtil.post(url, accessToken, param);  //编码后的参数

            Log.d("jiaqiang",result);

            // "keyword": "摄像机"
            Pattern keywordPattern = Pattern.compile("\"keyword\": \"(\\S+)\"");
            Matcher matcher = keywordPattern.matcher(result);
            if (matcher.find()){//##########inquire为产品的字符串
                String inquire = matcher.group(1);

                //返回种类信息
                getCategory(inquire);
//                builder.setMessage(result_info.get(3).toString());
//                builder.setCancelable(false);
//                builder.show();
//                if(!inquire.equals("图像模糊")){
//                    //如果不是未识别的垃圾
//                    if(!(inquire_result.get(0)).equals(0)){
//                        //垃圾种类
//                        builder.setTitle(inquire_result.get(3).toString());
//                        //设置图标
//                        builder.setIcon(setIcon((int)inquire_result.get(2)));
//                        //builder.setMessage("垃圾为："+inquire+"\n"+"投放要求:\n"+lajiinformation((int)inquire_result.get(2)));
//                        builder.setMessage("sdFDSD");
//                        builder.setPositiveButton("确定",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                    }
//                                });
//                        builder.setNegativeButton("识别错误？点我更正", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                upload();
//                            }
//                        });
//                        builder.show();
//                    }
//                    else{
//                        builder.setTitle("我从未见过这种东东垃圾>_<");
//                        builder.setMessage("此东东不是垃圾or垃圾未收录"
//                        );
//                        builder.setPositiveButton("确定",null);
//                        builder.setNegativeButton("识别错误？点我更正", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                upload();
//                            }
//                        });
//                        builder.show();
//                    }
//                }
//                else
//                {
//                    builder.setMessage("亲，太糊了 \n我也不知道是什么垃圾QAQ！");
//                    builder.setPositiveButton("确定",
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                }
//                            });
//                  // builder.show();
//                }
                Log.d("++++++++++++++",matcher.group(1));
            }else {
                Log.d("++++++++","Missing");
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    //垃圾信息
    private String lajiinformation(int i) {
        String result = new String();
        switch (i) {
            case 2: //湿垃圾
                result = "1、湿垃圾投放需破袋处理。盛放湿垃圾的容器，如塑料袋等，在投放时应予去除。\n2、湿垃圾应从产生时就与其他湿垃圾应从产生时就与其他品种垃圾分开收集，投放前尽量沥干水分。\n3、有包装物的湿垃圾应将包装物去除后分类投放，包装物应投放到对应的可回收物或干垃圾收集容器。";
                break;
            case 3://可回收垃圾
                result = "本身或材质可再利用的纸类、硬纸板、玻璃、塑料、金属、人造合成材料包装等，与这些材质有关的如：报纸、杂志、广告单及其它干净的纸类等皆可回收。";
                break;
            case 1: //干垃圾
                result = "干垃圾也就是其他垃圾，包括砖瓦陶瓷、渣土、卫生间废纸、瓷器碎片等难以回收的废弃物，\n采取卫生填埋可有效减少对地下水、地表水、土壤及空气的污染，在当今社会，还无有效化解其他垃圾的好方法，所以需尽量少产生。";
                break;
            case 4: //有害垃圾
                result = "1. 投放时请注意轻放 \n2. 易破损的请连带包装或包裹后轻放 \n3. 如易挥发，请密封后投放";
                break;
        }
        return result;
    }

    private int setIcon(int i) {
       int back=0;
        switch (i) {
            case 1://干
                back= R.mipmap.ganlaji;
            break;
            case 2://湿
                back= R.mipmap.shilaji;
            break;
            case 3://可回收垃圾
                back= R.mipmap.kehuishouwu;
            break;
            case 4://有害
                back= R.mipmap.youhailaji;
            break;
        }
        return back;
   }

}
