package com.siddhantjain.muskular.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.siddhantjain.muskular.R;

/**
 * Created by akash.jatangi on 9/20/15.
 */
public class DataStore {
    public static SharedPreferences userProfileStore = null;
    private static Context mContext;

    private DataStore() {} //Prevent Instantiation

    public static SharedPreferences getUserProfileStore(Context mContext){
        if (userProfileStore ==null){
            userProfileStore = mContext.getSharedPreferences(mContext.getString(R.string.preference_file_key_user_profile),Context.MODE_PRIVATE);
        }
        return userProfileStore;
    }
    public static void init(Context mContextArg){
        if (userProfileStore ==null){
            userProfileStore = mContextArg.getSharedPreferences(mContextArg.getString(R.string.preference_file_key_user_profile),Context.MODE_PRIVATE);
            mContext = mContextArg;
        }
    }
    public static String getUserId(){
        return userProfileStore.getString(mContext.getString(R.string.user_id),null);
    }
    public static void setUserId(String user_id){
        SharedPreferences.Editor SPEditor = userProfileStore.edit();
        SPEditor.putString(mContext.getString(R.string.user_id),user_id);
        SPEditor.commit();
    }
}
