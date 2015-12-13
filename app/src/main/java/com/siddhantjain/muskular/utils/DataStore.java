package com.siddhantjain.muskular.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siddhantjain.muskular.R;
import com.siddhantjain.muskular.models.ProgramMetadata;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by akash.jatangi on 9/20/15.
 */
public class DataStore {
    public static SharedPreferences userProfileStore = null;
    private static Context mContext;
    static Type listProgramMetadataType = new TypeToken<List<ProgramMetadata>>(){}.getType();

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
    public static String getUwyaId(){
        return userProfileStore.getString(mContext.getString(R.string.uwya_id),null);
    }
    public static void setUwyaId(String uwya_id){
        SharedPreferences.Editor SPEditor = userProfileStore.edit();
        SPEditor.putString(mContext.getString(R.string.uwya_id),uwya_id);
        SPEditor.commit();
    }
    public static String getUwywId(){
        return userProfileStore.getString(mContext.getString(R.string.uwyw_id),null);
    }
    public static void setUwywId(String uwyw_id){
        SharedPreferences.Editor SPEditor = userProfileStore.edit();
        SPEditor.putString(mContext.getString(R.string.uwyw_id),uwyw_id);
        SPEditor.commit();
    }
    public static List<ProgramMetadata> getEligiblePrograms(){
        Gson gson = new Gson();
        String elp = userProfileStore.getString(mContext.getString(R.string.eligible_programs),"");
        return gson.fromJson(elp, listProgramMetadataType);
    }
    public static void setEligiblePrograms(List<ProgramMetadata> eligiblePrograms){
        Gson gson = new Gson();
        String elp = gson.toJson(eligiblePrograms,listProgramMetadataType);
        SharedPreferences.Editor SPEditor = userProfileStore.edit();
        SPEditor.putString(mContext.getString(R.string.eligible_programs),elp);
        SPEditor.commit();
    }
    public static String getUserGoal(){
        return userProfileStore.getString(mContext.getString(R.string.user_goal), null);
    }
    public static void setUserGoal(String user_id){
        SharedPreferences.Editor SPEditor = userProfileStore.edit();
        SPEditor.putString(mContext.getString(R.string.user_goal),user_id);
        SPEditor.commit();
    }
    public static String getUserGender(){
        return userProfileStore.getString(mContext.getString(R.string.user_gender),null);
    }
    public static void setUserGender(String user_id){
        SharedPreferences.Editor SPEditor = userProfileStore.edit();
        SPEditor.putString(mContext.getString(R.string.user_gender),user_id);
        SPEditor.commit();
    }
    public static String getUserHeight(){
        return userProfileStore.getString(mContext.getString(R.string.user_height),null);
    }
    public static void setUserHeight(String user_id){
        SharedPreferences.Editor SPEditor = userProfileStore.edit();
        SPEditor.putString(mContext.getString(R.string.user_height),user_id);
        SPEditor.commit();
    }
    public static String getUserWeight(){
        return userProfileStore.getString(mContext.getString(R.string.user_weight),null);
    }
    public static void setUserWeight(String user_id){
        SharedPreferences.Editor SPEditor = userProfileStore.edit();
        SPEditor.putString(mContext.getString(R.string.user_weight),user_id);
        SPEditor.commit();
    }
    public static String getUserYob(){
        return userProfileStore.getString(mContext.getString(R.string.user_yob),null);
    }
    public static void setUserYob(String user_id){
        SharedPreferences.Editor SPEditor = userProfileStore.edit();
        SPEditor.putString(mContext.getString(R.string.user_yob),user_id);
        SPEditor.commit();
    }
}
