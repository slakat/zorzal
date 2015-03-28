package com.zorzal.heartstrings.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zorzal.heartstrings.MainActivity;
import com.zorzal.heartstrings.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;


public class LoginActivity extends Activity{

    private static final String PROPERTY_REG_ID = "registration_id";
    // Declare Variables
    Button loginbutton;
    String passwordtxt;
    EditText password;
    String emailtxt;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contacts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void login(final View view) {

        passwordtxt = password.getText().toString();
        emailtxt = email.getText().toString();

        if (emailtxt.equals("") && passwordtxt.equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please complete the sign up form",
                    Toast.LENGTH_LONG).show();

        }
        else
            new PushNotification().execute();
    }

    class PushNotification extends AsyncTask<String, Void, Void> {

        private Exception exception;

        protected Void doInBackground(String ...query) {
            try {


                final SharedPreferences prefs = getSharedPreferences(MainActivity.class.getSimpleName(),
                        Context.MODE_PRIVATE);


                //ServerRequest sr = new ServerRequest();
                //JSONObject json = sr.getQueryJSON("http://zorzal.herokuapp.com/api/login?email=slakat@gmail.com&password=cinemania");
                //String jsonstr = json.getString("code");
                //Log.i("Zorzal - response", json.toString());
                //Log.i("Zorzal - code",jsonstr);

                URL url= new URL("http://zorzal.herokuapp.com/api/login?email="+emailtxt);
                Log.i("Zorzal - url", url.toString());
                InputStream content = null;
                HttpResponse response=null;
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    response = httpclient.execute(new HttpGet(url.toString()));
                } catch (Exception e) {
                    Log.i("[GET REQUEST]", "Network exception", e);
                }


                String json_string = EntityUtils.toString(response.getEntity());
                int statusCode = response.getStatusLine().getStatusCode();
                Log.i("STATUS", String.valueOf(statusCode));
                JSONObject json = new JSONObject(json_string);
                JSONObject user = new JSONObject(json.getString("user"));

                if(json != null){
                    try{
                        Log.i("Zorzal - response",json.toString());
                        String jsonstr = json.getString("code");
                        if (jsonstr.contains("200")){
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt("user_logged", 1);
                            editor.putString("current_user_password",passwordtxt);
                            editor.putString("current_user_username", user.getString("username").toString());
                            editor.putString("current_user_name", user.getString("name").toString());
                            editor.putString("current_user_email", emailtxt);
                            editor.commit();
                            Log.i("Zorzal - entre",prefs.getString("current_user_email", ""));
                            Intent intent = new Intent(
                                    LoginActivity.this,
                                    Welcome.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),
                                    "there was an error: ",
                                    Toast.LENGTH_LONG).show();
                        }

                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            } catch (Exception e) {
                this.exception = e;
            }
            return null;
        }


    }

            /**
             * A placeholder fragment containing a simple view.
             */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
            return rootView;
        }
    }

}
