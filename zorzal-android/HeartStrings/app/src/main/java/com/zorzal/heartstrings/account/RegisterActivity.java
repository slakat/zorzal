package com.zorzal.heartstrings.account;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zorzal.heartstrings.account.ServerRequest;

import com.zorzal.heartstrings.R;
import com.zorzal.heartstrings.contacts.ContactsActivity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by katherine on 3/26/15.
 */
public class RegisterActivity extends Activity {
    private static final String TAG = "ZORZAL";
    EditText email,password;
    Button register;
    String emailtxt,passwordtxt,email_res_txt,code_txt,npass_txt;
    List<NameValuePair> params;
    SharedPreferences pref;
    Dialog reset;
    ServerRequest sr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.register);

        TextView loginScreen = (TextView) findViewById(R.id.btnRegister);

        sr = new ServerRequest();
        email = (EditText)findViewById(R.id.reg_email);
        password = (EditText)findViewById(R.id.reg_password);
        register = (Button)findViewById(R.id.btnRegister);
        pref = getSharedPreferences("AppPref", MODE_PRIVATE);

        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                emailtxt = email.getText().toString();
                passwordtxt = password.getText().toString();
                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("email", "slakat@gmail.com"));
                params.add(new BasicNameValuePair("password", "12345678"));
                params.add(new BasicNameValuePair("name", "Katherine"));
                params.add(new BasicNameValuePair("username", "slakat"));

                DefaultHttpClient httpclient = new DefaultHttpClient();
                HttpPost httppostreq = new HttpPost("http://localhost:3000/api/push");
                try {
                    HttpResponse httpresponse = httpclient.execute(httppostreq);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Log.i(TAG,"antes");
                ServerRequest sr = new ServerRequest();
                Log.i(TAG,"medio");
                JSONObject json = sr.getJSON("http://localhost:3000/api/push",params);
                Log.i(TAG,"despues");
                if(json != null){
                    try{
                        String jsonstr = json.getString("response");
                        Log.i(TAG,jsonstr);
                        Log.i(TAG,"hola");

                        Intent profactivity = new Intent(RegisterActivity.this,ContactsActivity.class);
                        startActivity(profactivity);
                        finish();

                        Toast.makeText(getApplication(), jsonstr, Toast.LENGTH_LONG).show();
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // Closing registration screen
                // Switching to Login Screen/closing register screen
                finish();
            }
        });
    }

}
