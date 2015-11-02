package com.siddhantjain.muskular.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.siddhantjain.muskular.R;
import com.siddhantjain.muskular.utils.DataStore;

/**
 * Created by siddhaja on 8/26/2015.
 */
public class Gender extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gender, container, false);
        ((ImageView) rootView.findViewById(R.id.imgMale)).setOnClickListener(this);
        ((ImageView) rootView.findViewById(R.id.imgFemale)).setOnClickListener(this);
        ((TextView) rootView.findViewById(R.id.tvNextStepProficiency)).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sharedPref = DataStore.getUserProfileStore(getActivity());
        SharedPreferences.Editor editor = sharedPref.edit();
        switch (v.getId()) {
            case R.id.imgMale:
                ((ImageView)getView().findViewById(R.id.imgMale)).setImageDrawable(getResources().getDrawable(
                        R.drawable.user_male_selected, getActivity().getTheme()));
                //((ImageView)getView().findViewById(R.id.imgMale)).setColorFilter(0xffff00ff, PorterDuff.Mode.MULTIPLY);
                ((ImageView)getView().findViewById(R.id.imgFemale)).setImageDrawable(getResources().getDrawable(
                        R.drawable.user_female, getActivity().getTheme()));
                editor.putString(getString(R.string.user_gender), "Male");
                editor.commit();
                break;
            case R.id.imgFemale:
                ((ImageView)getView().findViewById(R.id.imgFemale)).setImageDrawable(getResources().getDrawable(
                        R.drawable.user_female_selected, getActivity().getTheme()));
                //((ImageView)getView().findViewById(R.id.imgMale)).setColorFilter(0xffff00ff, PorterDuff.Mode.MULTIPLY);
                ((ImageView)getView().findViewById(R.id.imgMale)).setImageDrawable(getResources().getDrawable(
                        R.drawable.user_male, getActivity().getTheme()));
                //((ImageView)getView().findViewById(R.id.imgFemale)).setColorFilter( 0xffff0000, PorterDuff.Mode.ADD);
                //((ImageView)getView().findViewById(R.id.imgMale)).setColorFilter( 0xff000000, PorterDuff.Mode.DARKEN);
                editor.putString(getString(R.string.user_gender), "Female");
                editor.commit();
                break;
            case R.id.tvNextStepProficiency:
                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.questionsPager);
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                break;
        }
    }
}
