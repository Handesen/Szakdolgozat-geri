package com.example.asztalos.szakdolgozat;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class Async extends AsyncTask<String,Void,String> {
    public AsyncResponse resp = null;//call back interface
    public boolean isPost;
    public String url;
    public String _parameters;
    //public Map<String,String> dictionary;
    RequestBody postData;
    Map<String,String> dictionary;

    public Async(AsyncResponse asyncResponse,boolean isPost,String url,String parameters) {
        resp = asyncResponse;//Assigning call back interfacethrough constructor
        this.isPost = isPost;
        this.url = url;
        this._parameters = parameters;
    }
    public Async(AsyncResponse asyncResponse,boolean isPost,String url,RequestBody postData,Context context){
        resp = asyncResponse;//Assigning call back interfacethrough constructor
        this.isPost = isPost;
        this.url = url;
        this.postData = postData;
        ConnectionHelper.isNetworkAvailable(context);
    }



    @Override
    protected String doInBackground(String... params) {

        try{
            //OKHTTP object létrehozás
            OkHttpClient client = new OkHttpClient();
            String result = "";
            //POST
            if (isPost)
            {
                Request request_post = new Request.Builder()
                        .url(url)
                        .post(postData)
                        .build();
                Response response = client.newCall(request_post).execute();
                result = response.body().string();
            }else{
                Request request_get = new Request.Builder()
                        .url(url+"?"+ _parameters)
                        .get()
                        .build();
                Response response = client.newCall(request_get).execute();
                result = response.body().string();
            }

            return result;
        }catch(Exception e){
            return "";
        }
    }
    @Override
    protected void onPostExecute(String s){
        try {
            if(!s.equals("")) resp.proccessFinish(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}


