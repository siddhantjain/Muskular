package com.siddhantjain.muskular;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.siddhantjain.muskular.user.UserProfiler;


/**
 * Created by siddhaja on 10/29/2015.
 */
public class UserProfilerConnector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profiler_connector);
    }

    public void user_profiling_starter(View view){
        Intent intent = new Intent(UserProfilerConnector.this,UserProfiler.class);
        startActivity(intent);
    }
}
