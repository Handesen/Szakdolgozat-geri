package com.example.asztalos.szakdolgozat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static android.R.attr.value;

public class MainActivity extends AppCompatActivity implements AsyncResponse,View.OnClickListener {

    public static final String DATA_URL = "http://progreference.com/szakdolgozat";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_VC = "vc";
    public static final String JSON_ARRAY = "result";
    private OkHttpClient client;

    EditText username;
    EditText password;

    public static String remember_token ="";
    public static String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new OkHttpClient();

        username =(EditText) findViewById(R.id.editText);
        password =(EditText) findViewById(R.id.editText2);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonLog:
                break;
            case R.id.buttonReg:
                break;
        }
    }
    public void login(View v) throws IOException, NoSuchAlgorithmException {
        AeSimpleSHA1 sha = new AeSimpleSHA1();
        String passSHA = sha.SHA1(password.getText().toString());
        RequestBody post_data = new FormBody.Builder()
                .add("action","login")
                .add("username", username.getText().toString())
                .add("password",passSHA)
                .build();
        Async async = new Async((AsyncResponse) this,true,"http://progreference.com/szakdolgozat",post_data,this);
        async.execute();
    }
    public void register(View v){
        Intent inti = new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(inti);
    }
    @Override
    public void proccessFinish(String output) throws JSONException {
        Log.w("OUTPUT",output);
//        Toast.makeText(this,output,Toast.LENGTH_SHORT).show();
//        JSONObject obj = new JSONObject(output);
        remember_token = output.split(Pattern.quote("||"))[0];
        userID=output.split(Pattern.quote("||"))[1];
        Intent inti = new Intent(getApplicationContext(),test_Activity.class);
        startActivity(inti);
        finish();
//        if(obj.has("success") && obj.getString("success").equals("true")){
//            finish();
//        }else{
////            error.writeError(this,output,false);
//        }

    }
}
