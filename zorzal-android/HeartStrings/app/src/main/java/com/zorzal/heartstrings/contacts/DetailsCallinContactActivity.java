package com.zorzal.heartstrings.contacts;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.zorzal.heartstrings.R;


public class DetailsCallinContactActivity extends ActionBarActivity {

    TextView mNickDisplay;
    TextView mUserDisplay;
    TextView mNameDisplay;
    TextView mMailDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_callin_contact);

        mNickDisplay = (TextView)findViewById(R.id.display_nick);
        mUserDisplay = (TextView)findViewById(R.id.display_username);
        mNameDisplay = (TextView)findViewById(R.id.display_name);
        mMailDisplay = (TextView)findViewById(R.id.display_mail);

        Intent intent = getIntent();
        if(intent.hasExtra("USERNAME")) {
            mUserDisplay.setText(intent.getStringExtra("USERNAME"));
        }
        if(intent.hasExtra("MAIL")) {
            mMailDisplay.setText(intent.getStringExtra("MAIL"));
        }
        if(intent.hasExtra("NICK")) {
            mNickDisplay.setText(intent.getStringExtra("NICK"));
        }
        if(intent.hasExtra("NAME")) {
            mNameDisplay.setText(intent.getStringExtra("NAME"));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details_callin_contact, menu);
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
}
