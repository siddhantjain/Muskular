package com.siddhantjain.muskular.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.siddhantjain.muskular.R;

/**
 * Created by akash.jatangi on 9/20/15.
 */
public class DataStore {
    public static SharedPreferences userProfileStore = null;

    public static SharedPreferences getUserProfileStore(Context mContext){
        if (userProfileStore ==null){
            userProfileStore = mContext.getSharedPreferences(mContext.getString(R.string.preference_file_key_user_profile),Context.MODE_PRIVATE);
        }
        return userProfileStore;
    }
}
