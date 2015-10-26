package com.siddhantjain.muskular.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.siddhantjain.muskular.PlanGrid;
import com.siddhantjain.muskular.R;
import com.siddhantjain.muskular.api.APICallback;
import com.siddhantjain.muskular.api.APIClient;
import com.siddhantjain.muskular.api.MuskAPI;
import com.siddhantjain.muskular.models.UserProfilerRequest;
import com.siddhantjain.muskular.models.UserProfilerResponse;
import com.siddhantjain.muskular.models.UserProfilerResponseData;
import com.siddhantjain.muskular.utils.DataStore;

public class UserProfiler extends FragmentActivity {

    QuestionsPagerAdapter mQuestionsPagerAdapter;
    ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profiler);

        Context context;
        context = this;
//        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);

        mQuestionsPagerAdapter =  new QuestionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.questionsPager);
        mViewPager.setAdapter(mQuestionsPagerAdapter);
    }

    public class QuestionsPagerAdapter extends FragmentPagerAdapter {
        public QuestionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch(i){
                case 0:
                    return new Gender();
                case 1:
                    return new Goal();
                case 2:
                    return new Proficiency();
                case 3:
                    return new Biostats();
                default:
                    return new Gender();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Q: "+(position + 1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profiler, menu);
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

    private boolean allUserProfilerInfoExists(String gender, String goal, String height, String weight, String level, String yob) {
        if (gender.equalsIgnoreCase("not found") ||  goal.equalsIgnoreCase("not found") || weight.equalsIgnoreCase("not found") || height.equalsIgnoreCase("not found") || level.equalsIgnoreCase("not found") || yob.equalsIgnoreCase("not found"))
            return false;
        return true;
    }

    public void planGridActivityConnector(View view){
        SharedPreferences sharedPref = DataStore.getUserProfileStore(getApplicationContext());
        String gender = sharedPref.getString(getString(R.string.user_gender), "not found");
        String user_id = sharedPref.getString(getString(R.string.user_id), "not found");
        String goal = sharedPref.getString(getString(R.string.user_goal), "not found");
        String height = sharedPref.getString(getString(R.string.user_height), "not found");
        String weight = sharedPref.getString(getString(R.string.user_weight), "not found");
        String level = sharedPref.getString(getString(R.string.user_level), "not found");
        String yob = sharedPref.getString(getString(R.string.user_yob), "not found");

        if (allUserProfilerInfoExists(gender, goal, height, weight, level, yob))
        {
            System.out.println(gender);
            UserProfilerRequest userProfilerRequest = new UserProfilerRequest();
            userProfilerRequest.setGender(gender);
            userProfilerRequest.setYob(String.valueOf(yob));
            userProfilerRequest.setGoal(goal);
            userProfilerRequest.setHeight(height);
            userProfilerRequest.setWeight(weight);
            userProfilerRequest.setLevel(level);
            userProfilerRequest.setUserId(user_id);
            MuskAPI APIGuy = APIClient.getAPIClient();
            APIGuy.profileUser(user_id, userProfilerRequest, new APICallback<UserProfilerResponse, UserProfilerResponseData>(this) {
                @Override
                public void onSuccess(UserProfilerResponseData data) {
                    SharedPreferences sharedPreferences = DataStore.getUserProfileStore(getApplicationContext());
                    SharedPreferences.Editor SPEditor = sharedPreferences.edit();
                    SPEditor.putString(String.valueOf(R.string.uwya_id),data.getUwyaId());
                    SPEditor.putString(String.valueOf(R.string.uwyw_id),data.getUwywId());
                    SPEditor.apply();
                }

                @Override
                public void onFailure(String errorMessage) {
                    Log.v("USER PROFILER RESPONSE", errorMessage);
                    Toast.makeText(getApplicationContext(), errorMessage,
                            Toast.LENGTH_LONG).show();
                    //TextView bad_credentials = (TextView) findViewById(R.id.tvBadCredentialsMessage);
                   // bad_credentials.setVisibility(View.VISIBLE);
                }
            });
            Intent intent = new Intent(this,PlanGrid.class);
            startActivity(intent);
        }
    }


}
