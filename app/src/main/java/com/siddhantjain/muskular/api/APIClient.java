package com.siddhantjain.muskular.api;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.siddhantjain.muskular.R;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by akash.jatangi on 9/19/15.
 */
public class APIClient {
    public static MuskAPI muskAPI = null;
    public static RestAdapter restAdapter;


    public static void initialise(){
        String baseURL = "http://192.168.0.104:8000/mmapp";
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Log.v("Checking Base URL -", baseURL);

        restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(baseURL)
                .setConverter(new GsonConverter(gson))
                .build();
//        restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(String.valueOf(R.string.base_url)).build();
        muskAPI = restAdapter.create(MuskAPI.class);
    }

    public static MuskAPI getAPIClient(){
        if(muskAPI==null){
            initialise();
        }
        return muskAPI;
    }

}
