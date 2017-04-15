package com.example.asztalos.szakdolgozat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class NewCar extends AppCompatActivity implements AsyncResponse{

    public static final String DATA_URL = "http://progreference.com/szakdolgozat";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_VC = "vc";
    public static final String JSON_ARRAY = "result";
    private OkHttpClient client;
    Spinner sp_uzemanyag;
    EditText et_modellname,et_modelltype,et_license,et_vin,et_motornumber,et_enginecap,et_km,et_avgfuel;
    Button btn_add;

    List<JSONObject> parts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car);
        initialize();



        btn_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String modellname,modelltype,license,vin,motornumber;
                int enginecap,km,uzemanyag;
                Double avgfuel;
                modellname = et_modellname.getText().toString();
                modelltype = et_modelltype.getText().toString();
                license = et_license.getText().toString();
                vin = et_vin.getText().toString();
                motornumber = et_motornumber.getText().toString();
                enginecap = Integer.parseInt(et_enginecap.getText().toString());
                km = Integer.parseInt(et_km.getText().toString());
                avgfuel = Double.parseDouble(et_avgfuel.getText().toString());
                uzemanyag = sp_uzemanyag.getSelectedItemPosition();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String strDate = sdf.format(c.getTime());
                int uid = Integer.parseInt(MainActivity.userID);
                carAdd(uid,modellname,modelltype,license,vin,motornumber,enginecap,uzemanyag,km,avgfuel,strDate);
            }
        });




    }


    public void initialize(){
        parts = new ArrayList<>();
        et_modellname = (EditText) findViewById(R.id.et_modell_name);
        et_modelltype = (EditText) findViewById(R.id.et_modell_type);
        et_license = (EditText) findViewById(R.id.et_licence_plate);
        et_vin = (EditText) findViewById(R.id.et_vin);
        et_motornumber = (EditText) findViewById(R.id.et_motor_number);
        et_enginecap = (EditText) findViewById(R.id.et_eng_cap);
        et_km = (EditText) findViewById(R.id.et_km);
        et_avgfuel = (EditText) findViewById(R.id.et_avg_fuel);

        sp_uzemanyag = (Spinner) findViewById(R.id.sp_fueltype);

        btn_add = (Button) findViewById(R.id.btn_add);


    }


    @Override
    public void proccessFinish(String output) throws JSONException {

    }


    public void carAdd(int userid, String model_name, String model_type, String license_plate, String vin, String motornumber, int enginecap, int fueltyp, int km, double avgfuel, String created_at){
        //---MATERIAL

        RequestBody post_data = new FormBody.Builder()
                .add("action","query")
                .add("sql", "insert into user_cars (user_id,model_name,model_type,licence_plate,created_at) values ('"+userid+"','"+model_name+"','"+model_type+"','"+license_plate+"','"+created_at+"')")
                .add("token",MainActivity.remember_token)
                .add("userID",MainActivity.userID)
                .build();
        Async async = new Async((AsyncResponse) this,true,"http://progreference.com/szakdolgozat",post_data,this);
        async.execute();

        RequestBody post_data1 = new FormBody.Builder()
                .add("action","query")
                .add("sql", "insert into user_car_properities (user_cars_id,VIN,motor_number,engine_capacity,fuel_type_id,kilometers,avarage_fuel) values ((select id from user_cars where licence_plate = '"+license_plate+"' ),'"+vin+"','"+motornumber+"','"+enginecap+"','"+fueltyp+"','"+km+"','"+avgfuel+"')")
                .add("token",MainActivity.remember_token)
                .add("userID",MainActivity.userID)
                .build();
        Async async1 = new Async((AsyncResponse) this,true,"http://progreference.com/szakdolgozat",post_data1,this);
        async1.execute();

        Toast.makeText(this,"Adatok felvíve",Toast.LENGTH_SHORT).show();


    }
}
