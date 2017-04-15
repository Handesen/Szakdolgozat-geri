package com.example.asztalos.szakdolgozat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class RegisterActivity extends AppCompatActivity implements AsyncResponse {

    EditText username;
    EditText password;
    EditText email;
    EditText firstname;
    EditText lastname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText)findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        firstname = (EditText) findViewById(R.id.keresztnev);
        lastname = (EditText) findViewById(R.id.vezeteknev);
    }
    public void register(View v) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        AeSimpleSHA1 sha = new AeSimpleSHA1();
        String passSHA = sha.SHA1(password.getText().toString());
        RequestBody post_data = new FormBody.Builder()
                .add("action","register")
                .add("username", username.getText().toString())
                .add("password",passSHA)
                .add("email",email.getText().toString())
                .add("firstname",firstname.getText().toString())
                .add("lastname",lastname.getText().toString())
                .build();
        Async async = new Async((AsyncResponse) this,true,"http://progreference.com/szakdolgozat",post_data,this);
        async.execute();
    }
    @Override
    public void proccessFinish(String output) throws JSONException {
        Log.w("OUTPUT",output);
        Toast.makeText(this,output,Toast.LENGTH_SHORT).show();
//        Intent inti = new Intent(RegisterActivity.this,MainActivity.class);
//        startActivity(inti);
        finish();

    }
}
