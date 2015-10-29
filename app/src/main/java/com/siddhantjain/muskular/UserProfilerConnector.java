package com.siddhantjain.muskular;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.siddhantjain.muskular.user.UserProfiler;


/**
 * Created by siddhaja on 10/29/2015.
 */
public class UserProfilerConnector extends AppCompatActivity {
    private TextView ButtonText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profiler_connector);
        ButtonText = (TextView) findViewById(R.id.tvUserProfilingStarter);
        Typeface Helvetica = Typeface.createFromAsset(getAssets(), "fonts/GeosansLight.ttf");
        ButtonText.setTypeface(Helvetica);
    }

    public void user_profiling_starter(View view){
        Intent intent = new Intent(UserProfilerConnector.this,UserProfiler.class);
        startActivity(intent);
    }
}
