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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SignupActivity extends Activity{

    private static final String PROPERTY_REG_ID = "registration_id";
    // Declare Variables
    Button loginbutton;
    Button signup;
    String usernametxt;
    String passwordtxt;
    EditText password;
    EditText username;
    String nametxt;
    String emailtxt;
    EditText email;
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
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

    public void signingUp(final View view) {

        usernametxt = username.getText().toString();
        passwordtxt = password.getText().toString();
        nametxt = name.getText().toString();
        emailtxt = email.getText().toString();

        if (usernametxt.equals("") && passwordtxt.equals("")&& nametxt.equals("") && emailtxt.equals("")) {
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

                ServerRequest sr = new ServerRequest();


                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("user[email]", emailtxt));
                params.add(new BasicNameValuePair("user[password]", passwordtxt));
                params.add(new BasicNameValuePair("user[name]", nametxt));
                params.add(new BasicNameValuePair("user[username]", usernametxt));
                final SharedPreferences prefs = getSharedPreferences(MainActivity.class.getSimpleName(),
                        Context.MODE_PRIVATE);
                String restoredText = prefs.getString(PROPERTY_REG_ID, "");

                if (restoredText != null) {
                    params.add(new BasicNameValuePair("reg_id", restoredText));

                }

                JSONObject json = sr.getJSON("http://zorzal.herokuapp.com/api/users",params);
                if(json != null){
                    try{
                        String jsonstr = json.getString("code");
                        Log.i("Zorzal - response",json.toString());
                        Log.i("Zorzal - code",jsonstr);
                        if (jsonstr.contains("200") ){
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt("user_logged", 1);
                            editor.putString("current_user_password", passwordtxt);
                            editor.putString("current_user_username", usernametxt);
                            editor.putString("current_user_name", nametxt);
                            editor.putString("current_user_email", emailtxt);
                            editor.commit();
                            Log.i("Zorzal - entre",prefs.getString("current_user_email", ""));
                            Intent intent = new Intent(
                                    SignupActivity.this,
                                    Welcome.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                            Toast.makeText(getApplicationContext(),
                                    "there was an error: "+jsonstr,
                                    Toast.LENGTH_LONG).show();


                    }catch (JSONException e) {
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
