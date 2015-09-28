package com.siddhantjain.muskular;

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

/**
 * Created by siddhaja on 8/26/2015.
 */
public class Gender extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gender, container, false);
        ((Button) rootView.findViewById(R.id.btnMale)).setOnClickListener(this);
        ((Button) rootView.findViewById(R.id.btnFemale)).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        switch (v.getId()) {
            case R.id.btnMale:
                ((ImageView)getView().findViewById(R.id.imgMale)).setColorFilter(Color.argb(50,0,0,0));
                ((ImageView)getView().findViewById(R.id.imgFemale)).setColorFilter(Color.argb(0, 0, 0, 0));
                editor.putString(getString(R.string.user_gender), "Male");
                editor.commit();
                break;
            case R.id.btnFemale:
                ((ImageView)getView().findViewById(R.id.imgFemale)).setColorFilter(Color.argb(50, 0, 0, 0));
                ((ImageView)getView().findViewById(R.id.imgMale)).setColorFilter(Color.argb(0, 0, 0, 0));
                editor.putString(getString(R.string.user_gender), "Female");
                editor.commit();
                break;
        }
    }
}
