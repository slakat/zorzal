package com.zorzal.heartstrings.contacts;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zorzal.heartstrings.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.net.URL;


public class DetailsCalloutContactActivity extends ActionBarActivity {
    private static final String PROPERTY_REG_ID = "registration_id";


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

                URL url= new URL("http://zorzal.herokuapp.com/api/push?email="+ mMailDisplay.getText());
                Log.i("Zorzal - url", url.toString());
                InputStream content = null;
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpResponse response = httpclient.execute(new HttpGet(url.toString()));
                    content = response.getEntity().getContent();
                } catch (Exception e) {
                    Log.i("[GET REQUEST]", "Network exception", e);
                }



                Log.i("Zorzal - luck","despues");
                if(content != null){
                    try{


                        finish();
                        Toast.makeText(getApplication(), "error", Toast.LENGTH_LONG).show();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                /*SAXParserFactory factory =SAXParserFactory.newInstance();
                SAXParser parser=factory.newSAXParser();
                XMLReader xmlreader=parser.getXMLReader();
                RssHandler theRSSHandler=new RssHandler();
                xmlreader.setContentHandler(theRSSHandler);
                */
                //InputSource is=
                //new InputSource(url.openStream());
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
