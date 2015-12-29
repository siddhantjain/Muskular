 package com.siddhantjain.muskular;

/**
 * Created by siddhaja on 12/26/2015.
 */
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class TwitterAsyncTask extends AsyncTask<Object, Void, ArrayList<TwitterTweet>> {
    AppCompatActivity callerActivity;
    ListView lvTweets;
    ProgressBar progressBar;
    final static String TWITTER_API_KEY = "pWWu6U5uJ255dbrOBrMo3AGMY";
    final static String TWITTER_API_SECRET = "HcmOaKNxisG3pYyS8A5aS8uKKObfQT6ltP9qtYFpeUgZ79xvVv";

    @Override
    protected ArrayList<TwitterTweet> doInBackground(Object... params) {
        ArrayList<TwitterTweet> twitterTweets = null;
        lvTweets = (ListView) params[1];
        progressBar = (ProgressBar) params[3];
        callerActivity = (AppCompatActivity) params[2];
        if (params.length > 0) {
            TwitterAPI twitterAPI = new TwitterAPI(TWITTER_API_KEY,TWITTER_API_SECRET);
            twitterTweets = twitterAPI.getTwitterTweets(params[0].toString());
        }
        return twitterTweets;
    }

    @Override
    protected void onPostExecute(ArrayList<TwitterTweet> twitterTweets) {
      //  ArrayAdapter<TwitterTweet> adapter =
        //        new ArrayAdapter<TwitterTweet>(callerActivity, R.layout.tweet_item, twitterTweets);
        progressBar.setVisibility(View.GONE);
        TwitterArrayAdapter adapter = new TwitterArrayAdapter(callerActivity,twitterTweets);
        lvTweets.setAdapter(adapter);
        //ListView lv = callerActivity.getListView();
        //lv.setDividerHeight(0);
        //lv.setDivider(this.getResources().getDrawable(android.R.color.transparent));
        //lv.setBackgroundColor(callerActivity.getResources().getColor(R.color.cs_blue));
    }
}
