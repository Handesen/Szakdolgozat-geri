package com.example.asztalos.szakdolgozat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class AddServiceActivity extends AppCompatActivity implements AsyncResponse {
    Spinner sp_parts;
    CustomList adapter;
    List<String> databaseRows;
    List<String> databaseRowsId;
    EditText et_szhelye,et_aktkm,et_csalkar;
    Button btn_add,btn_partsadd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        initialize();
        //---MATERIAL
        sp_parts = (Spinner) findViewById(R.id.spinner_parts);
        databaseRows =new ArrayList<>();
        databaseRowsId = new ArrayList<>();
        RequestBody post_data = new FormBody.Builder()
                .add("action","query")
                .add("sql", "select * from service_parts ")
                .add("token",MainActivity.remember_token)
                .add("userID",MainActivity.userID)
                .build();
        Async async = new Async((AsyncResponse) this,true,"http://progreference.com/szakdolgozat",post_data,this);
        async.execute();

        btn_partsadd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent inti = new Intent(AddServiceActivity.this,PartsAddActivity.class);
                startActivity(inti);

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String szervizhely;
                int aktkm,cseralkar,cseralktip;
                cseralktip = sp_parts.getSelectedItemPosition();
                aktkm = Integer.parseInt(et_aktkm.getText().toString());
                cseralkar = Integer.parseInt(et_csalkar.getText().toString());
                szervizhely = et_szhelye.getText().toString();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String strDate = sdf.format(c.getTime());
                int uid = Integer.parseInt(MainActivity.userID);
                serviceAdd(3,cseralktip,szervizhely,aktkm,cseralkar,strDate);
            }
        });
    }

    public void serviceAdd(int car_id,int serice_part_id, String location, int actual_km,int part_prince, String created_at){

        RequestBody post_data = new FormBody.Builder()
                .add("action","query")
                .add("sql", "insert into user_car_service (user_cars_id,service_part_id,location,actual_kilometers,part_prince,created_at) values ('"+car_id+"','"+serice_part_id+"','"+location+"','"+actual_km+"','"+part_prince+"','"+created_at+"')")
                .add("token",MainActivity.remember_token)
                .add("userID",MainActivity.userID)
                .build();
        Async async = new Async((AsyncResponse) this,true,"http://progreference.com/szakdolgozat",post_data,this);
        async.execute();

        Toast.makeText(this,"Adatok felvíve",Toast.LENGTH_SHORT).show();

        Intent inti = new Intent(AddServiceActivity.this,test_Activity.class);
        startActivity(inti);

    }

    public void initialize(){
        et_szhelye =(EditText) findViewById(R.id.et_location);
        et_aktkm = (EditText) findViewById(R.id.et_sz_km);
        et_csalkar = (EditText) findViewById(R.id.et_sz_ar);
        btn_add = (Button) findViewById(R.id.btn_sz_add);
        btn_partsadd = (Button) findViewById(R.id.btn_partsadd);
    }
    @Override
    public void proccessFinish(String output) throws JSONException {
        Log.w("LOG",output);
        JSONArray jsonArray = new JSONArray(output);
        databaseRows.add("Kérlek válassz");
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for(int i = 0;i<jsonArray.length();i++){
            jsonObjectList.add(jsonArray.getJSONObject(i));
            databaseRows.add(jsonObjectList.get(i).getString("part_name"));
            databaseRowsId.add(jsonObjectList.get(i).getString("id"));
        }
        adapter = new CustomList(this, databaseRows);
        Log.v("LOG",databaseRows.toString());


      //  String[] array = databaseRows.toArray(new String[0]);
        ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, databaseRows);
        sp_parts.setAdapter(NoCoreAdapter);


    }
}
