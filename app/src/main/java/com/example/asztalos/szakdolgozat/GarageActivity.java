package com.example.asztalos.szakdolgozat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class GarageActivity extends AppCompatActivity implements AsyncResponse {
    ListView list;
    CustomList adapter;
    List<String> databaseRows;
    List<String> databaseRowsId;

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage);

        //+++Material
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(GarageActivity.this,NewCar.class);
                startActivity(i);

            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked

            }
        });
        //---MATERIAL
        list = (ListView)findViewById(R.id.list);
        databaseRows =new ArrayList<>();
        databaseRowsId = new ArrayList<>();
        RequestBody post_data = new FormBody.Builder()
                .add("action","query")
                .add("sql", "select * from user_cars ")
                .add("token",MainActivity.remember_token)
                .add("userID",MainActivity.userID)
                .build();
        Async async = new Async((AsyncResponse) this,true,"http://progreference.com/szakdolgozat",post_data,this);
        async.execute();
    }

    public void init(){
        //DB
//        dbHelper = new DatabaseHandler(this);
        adapter = new CustomList(this, databaseRows);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                if(databaseRows.size()>0){
                    String selectedItem= databaseRows.get(+position);
                    Intent inti = new Intent(GarageActivity.this,SingleCar.class);
                    inti.putExtra("user_cars_id",String.valueOf(position+1));
                    inti.putExtra("model",selectedItem.split("\\|")[0]+" "+selectedItem.split("\\|")[1]);
                    inti.putExtra("licence",selectedItem.split("\\|")[2]);
                    startActivity(inti);
                }
            }
        });
    }
    public void addToGarage(View v){
//        Intent i = new Intent(GarageActivity.this,);
//        startActivity(i);
    }
    @Override
    public void proccessFinish(String output) throws JSONException {
//        JSONObject obj = new JSONObject(output);
        Log.w("LOG",output);
        JSONArray jsonArray = new JSONArray(output);
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for(int i = 0;i<jsonArray.length();i++){
            jsonObjectList.add(jsonArray.getJSONObject(i));
            databaseRows.add(jsonObjectList.get(i).getString("model_name")+"|"+
                            jsonObjectList.get(i).getString("model_type")+"|"+
                            jsonObjectList.get(i).getString("licence_plate"));
            databaseRowsId.add(jsonObjectList.get(i).getString("id"));
        }
        init();
    }
}
