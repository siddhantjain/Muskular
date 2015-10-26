package com.siddhantjain.muskular;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.siddhantjain.muskular.utils.DataStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomeScreen extends Activity{
    AnimationDrawable welcomeAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = DataStore.getUserProfileStore(getApplicationContext());
        String userId = sharedPreferences.getString("user_id", null);
        if(userId != null){
            Intent intent = new Intent(this,Dashboard.class);
            startActivity(intent);
            WelcomeScreen.this.finish();
        }
        setContentView(R.layout.activity_welcome_screen);

        ImageView welcomeBgImage = (ImageView) findViewById(R.id.ivBackground);
        welcomeBgImage.setBackgroundResource(R.drawable.splash_animation);
        welcomeAnimation = (AnimationDrawable) welcomeBgImage.getBackground();
        welcomeAnimation.setEnterFadeDuration(1000);
        welcomeAnimation.setExitFadeDuration(1000);
        welcomeAnimation.start();
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
        Intent intent = new Intent(this,UserSignUp.class);
        startActivity(intent);
    }
    public void user_login(View view){
        Intent intent = new Intent(this,UserLogin.class);
        startActivity(intent);
    }
}
