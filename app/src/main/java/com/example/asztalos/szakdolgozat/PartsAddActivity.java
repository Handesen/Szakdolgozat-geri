package com.example.asztalos.szakdolgozat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class PartsAddActivity extends AppCompatActivity implements AsyncResponse {

    EditText et_partsname;
    Button btn_partsadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parts_add);
        initialize();

        btn_partsadd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String partname;
                partname = et_partsname.getText().toString();
                partsAdd(partname);

            }
        });
    }

    public void partsAdd(String partname){
        RequestBody post_data = new FormBody.Builder()
                .add("action","query")
                .add("sql", "insert into service_parts (part_name) values ('"+partname+"')")
                .add("token",MainActivity.remember_token)
                .add("userID",MainActivity.userID)
                .build();
        Async async = new Async((AsyncResponse) this,true,"http://progreference.com/szakdolgozat",post_data,this);
        async.execute();
    }
    public void initialize(){
        et_partsname = (EditText) findViewById(R.id.et_partsname);
        btn_partsadd = (Button) findViewById(R.id.btn_partsadd);
    }

    @Override
    public void proccessFinish(String output) throws JSONException {
        Intent inti = new Intent(PartsAddActivity.this,AddServiceActivity.class);
        startActivity(inti);
    }
}
