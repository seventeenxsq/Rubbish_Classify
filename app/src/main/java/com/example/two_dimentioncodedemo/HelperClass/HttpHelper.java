package com.example.two_dimentioncodedemo.HelperClass;

import com.example.two_dimentioncodedemo.Bean.Garbage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpHelper {

 public static void sendOkHttpRequest(final String address, final okhttp3.Callback callback) {
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
            .url(address)
            .build();
    client.newCall(request).enqueue(callback);
}
    //用JSONObject解析json数据
     public  static void parseJSONWithJSONObject(String jsonData) {
            try {//解析数组
                JSONArray jsonArray = new JSONArray(jsonData);
                for (int i = 0; i <3; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                   // String id = jsonObject.getString("id");
                    String name = jsonObject.getString("itemName");
                    String categoey = jsonObject.getString("itemCategory");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    public static String parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        String message=null;
        List<Garbage> garbageList = gson.fromJson(jsonData, new TypeToken<List<Garbage>>() {}.getType());
        for (Garbage garbage : garbageList) {
          message=garbage.getMsg()+"\n";
        }
        return message;
    }


}
