package com.zorzal.heartstrings.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.zorzal.heartstrings.MainActivity;
import com.zorzal.heartstrings.R;
import com.zorzal.heartstrings.contacts.ContactsActivity;

/**
 * Created by katherine on 3/28/15.
 */
public class Welcome extends Activity{
    // Declare Variable
    Button logout,contacts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// Get the view from singleitemview.xml
        setContentView(R.layout.welcome);

// Retrieve current user from Parse.com
        final SharedPreferences prefs = getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
        String current_user = prefs.getString("current_user_username", "");


// Locate TextView in welcome.xml
        TextView txtuser = (TextView) findViewById(R.id.txtuser);

// Set the currentUser String into TextView
        txtuser.setText("You are logged in as " + current_user);

// Locate Button in welcome.xml
        logout = (Button) findViewById(R.id.logout);

// Logout Button Click Listener
        logout.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
// Logout current user
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("user_logged", 0);
                editor.putString("current_user_password", "");
                editor.putString("current_user_username", "");
                editor.putString("current_user_name", "");
                editor.putString("current_user_email", "");
                editor.commit();

                Intent intent = new Intent(
                        Welcome.this,
                        MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        // Locate Button in welcome.xml
        contacts = (Button) findViewById(R.id.contacts);

// Logout Button Click Listener
        contacts.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                Intent intent = new Intent(Welcome.this, ContactsActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}


