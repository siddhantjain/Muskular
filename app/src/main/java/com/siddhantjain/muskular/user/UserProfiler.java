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
                    return new Name();
                case 1:
                    return new Gender();
                case 2:
                    return new Height();
                case 3:
                    return new Weight();
                case 4:
                    return new Age();
                case 5:
                    return new Goal();
                default:
                    return new Name();
            }
        }

        @Override
        public int getCount() {
            return 6;
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

    private boolean allUserProfilerInfoExists(String gender, String goal, String height, String weight, String yob) {
//        if (gender.equalsIgnoreCase("not found") ||  goal.equalsIgnoreCase("not found") || weight.equalsIgnoreCase("not found") || height.equalsIgnoreCase("not found") || yob.equalsIgnoreCase("not found"))
        if(gender == null || goal == null || height == null || weight == null || yob == null)
            return false;
        return true;
    }


    public void planGridActivityConnector(View view){
        Log.v("USER ID DATASTORE",DataStore.getUserId());
//        SharedPreferences sharedPref = DataStore.getUserProfileStore(getApplicationContext());
//        String gender = sharedPref.getString(getString(R.string.user_gender), "not found");
//        String user_id = sharedPref.getString(getString(R.string.user_id), "not found");
//        String goal = sharedPref.getString(getString(R.string.user_goal), "not found");
//        String height = sharedPref.getString(getString(R.string.user_height), "not found");
//        String weight = sharedPref.getString(getString(R.string.user_weight), "not found");
//        String level = sharedPref.getString(getString(R.string.user_level), "not found");
//        String yob = sharedPref.getString(getString(R.string.user_yob), "not found");

        String gender = DataStore.getUserGender();
        String user_id = DataStore.getUserId();
        String goal = DataStore.getUserGoal();
        String height = DataStore.getUserHeight();
        String weight = DataStore.getUserWeight();
        //String level = sharedPref.getString(getString(R.string.user_level), "not found");
        String yob = DataStore.getUserYob();


//        Intent intent_temp = new Intent(this,PlanGrid.class);
//        startActivity(intent_temp);

        if (allUserProfilerInfoExists(gender, goal, height, weight, yob))
        {

            //Tweak below code to write all values properly

            UserProfilerRequest userProfilerRequest = new UserProfilerRequest();
            userProfilerRequest.setGender(gender);
            userProfilerRequest.setYob(String.valueOf(yob));
            userProfilerRequest.setGoal(goal);
            userProfilerRequest.setHeight(height);
            userProfilerRequest.setWeight(weight);
//            userProfilerRequest.setLevel(level);
            MuskAPI APIGuy = APIClient.getAPIClient();
            APIGuy.profileUser(user_id, userProfilerRequest, new APICallback<UserProfilerResponse, UserProfilerResponseData>(this) {
                @Override
                public void onSuccess(UserProfilerResponseData data) {
                    if(data.getUserId().equalsIgnoreCase(DataStore.getUserId())){
                        DataStore.setUwyaId(data.getUwyaId());
                        DataStore.setUwywId(data.getUwywId());
                        DataStore.setEligiblePrograms(data.getEligiblePrograms());
                        Intent intent = new Intent(getApplicationContext(), PlanGrid.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Integrity Error", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(String errorMessage) {
                    Log.v("USER PROFILER RESPONSE", errorMessage);
                    Toast.makeText(getApplicationContext(), errorMessage,Toast.LENGTH_LONG).show();
                    //TextView bad_credentials = (TextView) findViewById(R.id.tvBadCredentialsMessage);
                    // bad_credentials.setVisibility(View.VISIBLE);
                }
            });
        }
        else {
            Log.v("USER PROFILER","ALL INFO NOT EXISTS");
        }
    }


}
