package com.example.two_dimentioncodedemo.Activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Service;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.example.two_dimentioncodedemo.HelperClass.ImageUtils;
import com.example.two_dimentioncodedemo.R;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.zip.Inflater;

import static com.example.two_dimentioncodedemo.R.id.btn_confirm2;

public class ImageActivity2 extends MyActivity implements SurfaceHolder.Callback, View.OnClickListener
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

    //Load the tensorflow inference library
    static {
        System.loadLibrary("tensorflow_inference");
    }

    //与神经层有关
    private String INPUT_NAME = "conv2d_1_input";
    private String OUTPUT_NAME = "output_1";
    private TensorFlowInferenceInterface tf;

    //ARRAY TO HOLD THE PREDICTIONS AND FLOAT VALUES TO HOLD THE IMAGE DATA
    float[] PREDICTIONS = new float[15];    //返回预测的精度数组
    private float[] floatValues;               //输入数据
    private int[] INPUT_SIZE = {224,224,3};    //输入尺寸


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image2);
        //设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initViews();

        //initialize tensorflow with the AssetManager and the Model
        //PATH TO OUR MODEL FILE AND NAMES OF THE INPUT AND OUTPUT NODES
        String MODEL_PATH = "file:///android_asset/my_simpleNet.pb";
        //这一步加载模型
        tf = new TensorFlowInferenceInterface(getAssets(), MODEL_PATH);
    }

    private void initViews(){
        mSurfaceView=findViewById(R.id.surfaceView);
        mSurfaceHolder=mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);//添加回调接口，这一句很重要，一定要添加
        resultbar=findViewById(R.id.result);
        btn2=findViewById(btn_confirm2);
        btn2.setOnClickListener(this);
        btn=findViewById(R.id.btn_confirm);
        btn.setOnClickListener(this);
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
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    camera.cancelAutoFocus();//只有加上了这一句，才会自动对焦。
                }
            });
            mCamera.startPreview();
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

    @Override//重写拍照之后获得数据的回调//#######bytes为图像字节
    public void onPictureTaken(byte[] bytes, Camera camera) {
        //########实例化contentResolver相当于一个临时的内容提供器########
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//        InputStream imageStream = null;
//        try {
//            imageStream = getAssets().open("flower.jpg");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
        //predict(bitmap);  ////现在展示结果在predict函数中了
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        //resultInfo(builder,result);
        //builder.setIcon(R.mipmap.kehuishouwu);
        builder.setTitle("混合垃圾");
        builder.setMessage("垃圾名称：  "+"奶茶"+"\n"+"投放建议："+"\n"+"珍珠等食物为湿垃圾"+"\n"+"奶茶杯为干垃圾"+"\n"+"未喝完的奶茶请沥干水分");
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        //setCancelable设置点击以外的地方不会取消
        builder.setCancelable(false);
        builder.setNegativeButton("识别错误？点我更正",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //调用显示上传的函数
                        upload();
                    }
                });
        builder.show();
        camera.startPreview();
    }

    @Override//按快门拍照
    public void onClick(View v) {
        Vibrator vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(20);
        switch(v.getId()){
            case R.id.btn_confirm2:
                mCamera.takePicture(null, null, this);
                break;

            case R.id.btn_confirm:
                Intent intent=new Intent(this, ImageActivity.class);
                startActivity(intent);
                Toast toast=Toast.makeText(this,"百度识图模式",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                finish();
        }

    }
    @SuppressLint("StaticFieldLeak")


    //预测核心函数
    public void predict(final Bitmap bitmap){
        //Runs inference in background thread
        new AsyncTask<Integer,Integer,Integer>(){

            @Override
            protected Integer doInBackground(Integer ...params){

                //Resize the image into 224 x 224
                Bitmap resized_image = ImageUtils.processBitmap(bitmap,224);

                //Normalize the pixels
                floatValues = ImageUtils.normalizeBitmap(resized_image,224,127.5f,1.0f);

                //Pass input into the tensorflow 将输入传入tensorflow中
                tf.feed(INPUT_NAME,floatValues,1,224,224,3);

                //compute predictions
                tf.run(new String[]{OUTPUT_NAME});

                //copy the output into the PREDICTIONS array
                tf.fetch(OUTPUT_NAME,PREDICTIONS);

                //Obtained highest prediction
                final Object[] results = argmax(PREDICTIONS);
                //预测下标
                int class_index = (Integer) results[0];
                //正确率
                float confidence = (Float) results[1];

                //执行显示结果操作
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Showresult(results);
//                    }
//                });
                return 0;

            }
        }.execute(0);

    }

    //返回结果的Object[]{best,best_confidence}
    public Object[] argmax(float[] array){

        int best = -1;
        float best_confidence = 0.0f;

        for(int i = 0;i < array.length;i++){

            float value = array[i];

            if (value > best_confidence){

                best_confidence = value;
                best = i;
            }
        }

        //返回对象数组
        return new Object[]{best,best_confidence};
    }

    private void upload(){
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(ImageActivity2.this);
        builder1.setTitle("上传垃圾信息");
//
        //用布局填充器填充
        LayoutInflater factory = LayoutInflater.from(ImageActivity2.this);//提示框
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
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(ImageActivity2.this);
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

    private void Showresult(Object[] result){

    }

    //返回标题字符串
    private void resultInfo(AlertDialog.Builder builder,Object[] result){

        //我去switch
        switch ((Integer) result[0]){
            case 0:
//                builder.setIcon();
//                builder.setTitle();
//                builder.setMessage();
            break;
            case 1:
//                builder.setIcon();
//                builder.setTitle();
//                builder.setMessage();
                break;
            case 2:
//                builder.setIcon();
//                builder.setTitle();
//                builder.setMessage();
                break;
            case 3:
//                builder.setIcon();
//                builder.setTitle();
//                builder.setMessage();
                break;
            case 4:
//                builder.setIcon();
//                builder.setTitle();
//                builder.setMessage();
                break;
            case 5:
//                builder.setIcon();
//                builder.setTitle();
//                builder.setMessage();
                break;
            case 6:
//                builder.setIcon();
//                builder.setTitle();
//                builder.setMessage();
                break;
            case 7:
//                builder.setIcon();
//                builder.setTitle();
//                builder.setMessage();
                break;
            case 8:
//                builder.setIcon();
//                builder.setTitle();
//                builder.setMessage();
                break;
            case 9:
//                builder.setIcon();
//                builder.setTitle();
//                builder.setMessage();
                break;

        }
    }
}
