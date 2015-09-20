package com.siddhantjain.muskular.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by akash.jatangi on 9/20/15.
 */
public class DataStore {
    public static SharedPreferences storedData = null;

    public static SharedPreferences getSharedPref(Context mContext){
        if (storedData==null){
            storedData = mContext.getSharedPreferences(Constants.getSharedPrefName(),Context.MODE_PRIVATE);
        }
        return storedData;
    }
}
