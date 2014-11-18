package com.zorzal.heartstrings.contacts;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zorzal.heartstrings.R;

import org.xml.sax.InputSource;

import java.net.URL;


public class DetailsCalloutContactActivity extends ActionBarActivity {

    TextView mNickDisplay;
    TextView mUserDisplay;
    TextView mNameDisplay;
    TextView mMailDisplay;
    TextView mKeywordDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_callout_contact);

        mNickDisplay = (TextView)findViewById(R.id.display_nick);
        mUserDisplay = (TextView)findViewById(R.id.display_username);
        mNameDisplay = (TextView)findViewById(R.id.display_name);
        mMailDisplay = (TextView)findViewById(R.id.display_mail);
        mKeywordDisplay = (TextView)findViewById(R.id.display_keyword);

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
        if(intent.hasExtra("KEYWORD")) {
            mKeywordDisplay.setText(intent.getStringExtra("KEYWORD"));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.callout, menu);
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

    // Stop the alarm
    public void onRing(final View view) {
        String query = "?token=1&user=" + mUserDisplay.getText();
        new PushNotification().execute(query);
    }

    class PushNotification extends AsyncTask<String, Void, Void> {

        private Exception exception;

        protected Void doInBackground(String ...query) {
            try {
                URL url= new URL("http://zorzal.herokuapp.com/api/push/" + query[0]);
                /*SAXParserFactory factory =SAXParserFactory.newInstance();
                SAXParser parser=factory.newSAXParser();
                XMLReader xmlreader=parser.getXMLReader();
                RssHandler theRSSHandler=new RssHandler();
                xmlreader.setContentHandler(theRSSHandler);
                */
                //InputSource is=
                new InputSource(url.openStream());
                //xmlreader.parse(is);
                //return theRSSHandler.getFeed();
            } catch (Exception e) {
                this.exception = e;
            }
            return null;
        }
/*
        protected void onPostExecute(RSSFeed feed) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }
*/
    }
}
