package com.example.asztalos.szakdolgozat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class ServiceActivity extends AppCompatActivity implements AsyncResponse{
    List<String> parts;
    ListView lista;
    ArrayAdapter<String> adapter;

    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        lista = (ListView)findViewById(R.id.serviceList);
        parts = new ArrayList<>();
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                parts);
        lista.setAdapter(adapter);
        extras = getIntent().getExtras();
//        AeSimpleSHA1 sha = new AeSimpleSHA1();
//        String passSHA = sha.SHA1(password.getText().toString());
        RequestBody post_data = new FormBody.Builder()
                .add("action","query")
                .add("sql","select * from user_car_service JOIN service_parts ON service_parts.id =service_part_id where user_cars_id = '"+extras.getString("user_cars_id")+"'")
                .add("token", MainActivity.remember_token)
                .add("userID",MainActivity.userID)
                .build();
        Async async = new Async((AsyncResponse) this,true,"http://progreference.com/szakdolgozat",post_data,this);
        async.execute();
    }
    public void addService(View v){
        Intent inti = new Intent(ServiceActivity.this,AddServiceActivity.class);
        startActivity(inti);
    }
    @Override
    public void proccessFinish(String output) throws JSONException {
        JSONArray jsonArray = new JSONArray(output);
        List<JSONObject> jsonObjectList = new ArrayList<>();
        jsonObjectList.add(jsonArray.getJSONObject(0));
        //Get Parts
        if(jsonObjectList.get(0).getString("part_name")!=null){
            for(int i = 0;i<jsonArray.length();i++){
                parts.add(jsonArray.getJSONObject(i).getString("part_name")+" - "+jsonArray.getJSONObject(i).getString("part_prince")+" Ft - "+
                        jsonArray.getJSONObject(i).getString("actual_kilometers")+" Km");
            }
            adapter.notifyDataSetChanged();
        }
    }
}
