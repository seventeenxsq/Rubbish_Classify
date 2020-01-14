package com.example.two_dimentioncodedemo.DateUtils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DateUtils {

    public  static String getJsonFromAssets(Context context,String fileName) {
        /**
         * 1、StringBuilder 存放读取出的数据
         * 2、利用AssetManger资源管理器，Open方法 打开指定的资源文件  返回Inputstream(输入流对象)是字节流
         * 3、利用InputStreamReader（字节到字符的桥接器）把输入流的字节转化成字符，   BufferedReader(读取字符的缓冲区)
         * 4、这样我们就可以利用BufferedReader的readline的方法寻循环读取字符文件
         * 	循环利用 BufferedReader 的Readline读取每一行数据
         * 	并且把读取出来的数据存放到StringBulider里面。
         * 5、返回读取出来的所有数据。
         */

        //StingBuilder  存放读取出来的数据
        StringBuilder stringBuilder = new StringBuilder();
        //AssetManger资源管理器
        AssetManager assetManager = context.getAssets();
        //open方法打开指定的资源文件，返回InputStream输入流
        try {
            InputStream inputStream = assetManager.open(fileName);
            //InputStreamRreader（字节到字符）,把我的字节流放入
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            //循环利用readline获取每一行的数据
            String line;
            while((line=bufferedReader.readLine())!=null){
                //将读取出来的数据存放到stringBuilder中
                stringBuilder.append(line);
            };
        }catch (IOException e){
         e.printStackTrace();
        }

        //stringBuilder是字符流，我们还要转换成字符串
        return  stringBuilder.toString();
    }
}
