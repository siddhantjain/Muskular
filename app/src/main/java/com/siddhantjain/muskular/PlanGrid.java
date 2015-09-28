package com.siddhantjain.muskular;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class PlanGrid extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*temporary code to illustrate use of stored variables*/
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String user_gender = sharedPref.getString(getString(R.string.user_gender), "not found");
        String user_goal = sharedPref.getString(getString(R.string.user_gender),"not found");
        String user_level = sharedPref.getString(getString(R.string.user_gender),"not found");
        System.out.println("Gender: " + user_gender);
        int duration = Toast.LENGTH_SHORT;

        Toast toast1 = Toast.makeText(this,user_gender,duration);
        Toast toast2= Toast.makeText(this,user_goal,duration);
        Toast toast3 = Toast.makeText(this,user_level,duration);

        toast1.show();
        toast2.show();
        toast3.show();
        /* Insert code to get plans from database by sending user preferences*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_grid);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plan_grid, menu);
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
    public void update_profile(View view){
        Intent intent = new Intent(this,Dashboard.class);
        startActivity(intent);
    }
}
