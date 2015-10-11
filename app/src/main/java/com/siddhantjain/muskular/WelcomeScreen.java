package com.siddhantjain.muskular;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.siddhantjain.muskular.utils.DataStore;

import java.io.IOException;

public class WelcomeScreen extends Activity implements SurfaceHolder.Callback {
    private MediaPlayer mp = null;
    SurfaceView mSurfaceView=null;
    SurfaceHolder videoHolder = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = DataStore.getSharedPref(getApplicationContext());
        String userId = sharedPreferences.getString("user_id",null);
        if(userId != null){
            Intent intent = new Intent(this,Dashboard.class);
            startActivity(intent);
            WelcomeScreen.this.finish();
        }
        setContentView(R.layout.activity_welcome_screen);
        mp= new MediaPlayer();
        mSurfaceView = (SurfaceView) findViewById(R.id.surface);
        videoHolder = mSurfaceView.getHolder();
        videoHolder.addCallback(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void user_sign_up(View view){
        mp.stop();
        mp.reset();
        Intent intent = new Intent(this,UserSignUp.class);
        startActivity(intent);
    }
    public void user_login(View view){
        mp.stop();
        mp.reset();
        Intent intent = new Intent(this,UserLogin.class);
        startActivity(intent);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        SharedPreferences sharedPreferences = DataStore.getSharedPref(getApplicationContext());
        String userId = sharedPreferences.getString("user_id",null);
        if(userId != null){
            Intent intent = new Intent(this,Dashboard.class);
            startActivity(intent);
            WelcomeScreen.this.finish();
        }
        mp.setDisplay(videoHolder);
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/"
                + R.raw.welcomevideo);
        Context context = getApplicationContext();
        try {
            mp.setDataSource(context, video);
            mp.prepare();
        }catch(IOException ie){
            throw new RuntimeException(ie);
        }
        //Start video
        mp.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
