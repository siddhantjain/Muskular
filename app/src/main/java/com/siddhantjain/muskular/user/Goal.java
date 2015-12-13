package com.siddhantjain.muskular.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.siddhantjain.muskular.R;
import com.siddhantjain.muskular.utils.DataStore;

/**
 * Created by siddhaja on 8/26/2015.
 */
public class Goal extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_goal, container, false);
        ((Button) rootView.findViewById(R.id.btnLoseFat)).setOnClickListener(this);
        ((Button) rootView.findViewById(R.id.btnTransform)).setOnClickListener(this);
        ((Button) rootView.findViewById(R.id.btnGainMuscle)).setOnClickListener(this);
        ((ImageView) rootView.findViewById(R.id.imgLoseFat)).setOnClickListener(this);
        ((ImageView) rootView.findViewById(R.id.imgGainMuscle)).setOnClickListener(this);
        ((ImageView) rootView.findViewById(R.id.imgTransform)).setOnClickListener(this);

        return rootView;
    }
    public void onClick(View v) {
        SharedPreferences sharedPref = DataStore.getUserProfileStore(getActivity());
        SharedPreferences.Editor editor = sharedPref.edit();
        switch (v.getId()) {
            case R.id.imgTransform:
            case R.id.btnTransform:
                ((ImageView)getView().findViewById(R.id.imgTransform)).setColorFilter(Color.argb(0, 0, 0, 0));
                ((ImageView)getView().findViewById(R.id.imgLoseFat)).setColorFilter(Color.argb(255,255,255,255));
                ((ImageView)getView().findViewById(R.id.imgGainMuscle)).setColorFilter(Color.argb(255,255,255,255));
//                editor.putString(getString(R.string.user_goal), "Transform");
//                editor.commit();
                DataStore.setUserGoal(String.valueOf("Transform"));
                break;


            case R.id.imgLoseFat:
            case R.id.btnLoseFat:
                ((ImageView)getView().findViewById(R.id.imgTransform)).setColorFilter(Color.argb(255, 255,255,255));
                ((ImageView)getView().findViewById(R.id.imgLoseFat)).setColorFilter(Color.argb(0,0,0,0));
                ((ImageView)getView().findViewById(R.id.imgGainMuscle)).setColorFilter(Color.argb(255,255,255,255));
//                editor.putString(getString(R.string.user_goal), "Lose");
//                editor.commit();
                DataStore.setUserGoal(String.valueOf("Lose"));
                break;

            case R.id.imgGainMuscle:
            case R.id.btnGainMuscle:
                ((ImageView)getView().findViewById(R.id.imgGainMuscle)).setColorFilter(Color.argb(0,0,0,0));
                ((ImageView)getView().findViewById(R.id.imgLoseFat)).setColorFilter(Color.argb(255,255,255,255));
                ((ImageView)getView().findViewById(R.id.imgTransform)).setColorFilter(Color.argb(255,255,255,255));
//                editor.putString(getString(R.string.user_goal), "Gain");
//                editor.commit();
                DataStore.setUserGoal(String.valueOf("Transform"));
                break;

            case R.id.btnNextStepPlanSelector:
                ((UserProfiler)getActivity()).planGridActivityConnector(getView().findViewById(R.id.btnNextStepPlanSelector));
        }
    }


}
