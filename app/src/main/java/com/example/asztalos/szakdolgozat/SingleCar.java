package com.example.asztalos.szakdolgozat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class SingleCar extends AppCompatActivity implements AsyncResponse, View.OnClickListener{
    ListView detailList;

    TextView model;
    TextView licence;
    TextView VIN;
    TextView motor;
    TextView engine;
    TextView kilometers;

    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_car);

        model = (TextView)findViewById(R.id.model);
        licence = (TextView)findViewById(R.id.licence);
        VIN = (TextView)findViewById(R.id.vin);
        motor = (TextView)findViewById(R.id.motor_num);
        engine = (TextView)findViewById(R.id.engine);
        kilometers = (TextView)findViewById(R.id.kilometers);

        Intent i = getIntent();
        extras = getIntent().getExtras();
        model.setText(extras.getString("model",""));
        licence.setText(extras.getString("licence",""));

        RequestBody post_data = new FormBody.Builder()
                .add("action","query")
                .add("sql", "select * from user_car_properities where user_cars_id = '"+extras.getString("user_cars_id")+"'")
                .add("token",MainActivity.remember_token)
                .add("userID",MainActivity.userID)
                .build();
        Async async = new Async((AsyncResponse) this,true,"http://progreference.com/szakdolgozat",post_data,this);
        async.execute();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.service_button:
                Intent i = new Intent(SingleCar.this,ServiceActivity.class);
                i.putExtra("user_cars_id",extras.getString("user_cars_id"));
                startActivity(i);
                finish();
                break;
        }
    }

    @Override
    public void proccessFinish(String output) throws JSONException {
        JSONArray jsonArray = new JSONArray(output);
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for(int i = 0;i<jsonArray.length();i++){
            jsonObjectList.add(jsonArray.getJSONObject(i));
            VIN.setText(VIN.getText()+" "+jsonObjectList.get(i).getString("VIN"));
            motor.setText(motor.getText()+" "+jsonObjectList.get(i).getString("motor_number"));
            engine.setText(engine.getText()+" "+jsonObjectList.get(i).getString("engine_capacity")+" ccm");
            kilometers.setText(kilometers.getText()+" "+jsonObjectList.get(i).getString("kilometers")+" Km");
        }
    }
}
