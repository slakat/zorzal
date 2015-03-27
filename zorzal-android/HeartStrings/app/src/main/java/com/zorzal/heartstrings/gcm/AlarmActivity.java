package com.zorzal.heartstrings.gcm;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.zorzal.heartstrings.R;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;


public class AlarmActivity extends ActionBarActivity implements MediaPlayer.OnPreparedListener{
    MediaPlayer player = null;
    TextView mDisplay;

    public void onPrepared(MediaPlayer player){
        player.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        mDisplay = (TextView) findViewById(R.id.display);
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_ALARM);
        try{
            AssetFileDescriptor afd = getAssets().openFd("alarm.m4r");
            player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            player.setOnPreparedListener(this);
            player.setLooping(true);
            player.prepareAsync();
        }
        catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = getIntent();
        if(intent.hasExtra("SENDER"))
            mDisplay.setText(intent.getStringExtra("SENDER"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.alarm, menu);
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
    public void onStop(final View view) {
        if(player.isPlaying()){
            player.stop();
        }
        player.release();
        player = null;
    }
}