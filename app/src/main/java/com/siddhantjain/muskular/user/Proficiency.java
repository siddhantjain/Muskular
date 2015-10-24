package com.siddhantjain.muskular.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.siddhantjain.muskular.R;
import com.siddhantjain.muskular.utils.DataStore;

/**
 * Created by siddhaja on 8/26/2015.
 */
public class Proficiency extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_proficiency, container, false);
        (rootView.findViewById(R.id.btnBeginner)).setOnClickListener(this);
        (rootView.findViewById(R.id.btnIntermediate)).setOnClickListener(this);
        (rootView.findViewById(R.id.btnAdvanced)).setOnClickListener(this);

        return rootView;
    }
    public void onClick(View v) {
        SharedPreferences sharedPref = DataStore.getUserProfileStore(getActivity());
        SharedPreferences.Editor editor = sharedPref.edit();

        switch (v.getId()) {
            case R.id.btnBeginner:
                ((ImageView)getView().findViewById(R.id.imgBeginner)).setColorFilter(Color.argb(50,0,0,0));
                ((ImageView)getView().findViewById(R.id.imgIntermediate)).setColorFilter(Color.argb(0, 0, 0, 0));
                ((ImageView)getView().findViewById(R.id.imgAdvanced)).setColorFilter(Color.argb(0, 0, 0, 0));
                editor.putString(getString(R.string.user_level), "Beginner");
                editor.commit();
                break;
            case R.id.btnIntermediate:
                ((ImageView)getView().findViewById(R.id.imgBeginner)).setColorFilter(Color.argb(0,0,0,0));
                ((ImageView)getView().findViewById(R.id.imgIntermediate)).setColorFilter(Color.argb(50, 0, 0, 0));
                ((ImageView)getView().findViewById(R.id.imgAdvanced)).setColorFilter(Color.argb(0, 0, 0, 0));
                editor.putString(getString(R.string.user_level), "Intermediate");
                editor.commit();
                break;
            case R.id.btnAdvanced:
                ((ImageView)getView().findViewById(R.id.imgBeginner)).setColorFilter(Color.argb(0,0,0,0));
                ((ImageView)getView().findViewById(R.id.imgIntermediate)).setColorFilter(Color.argb(0, 0, 0, 0));
                ((ImageView)getView().findViewById(R.id.imgAdvanced)).setColorFilter(Color.argb(50, 0, 0, 0));
                editor.putString(getString(R.string.user_level), "Advanced");
                editor.commit();
                break;
        }
    }
}
