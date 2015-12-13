package com.siddhantjain.muskular.user;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.siddhantjain.muskular.R;
import com.siddhantjain.muskular.VerticalSeekBar;
import com.siddhantjain.muskular.utils.DataStore;

/**
 * Created by siddhaja on 11/3/2015.
 */
public class Height extends Fragment implements View.OnClickListener {
    TextView heightTextValue;
    int finalHeight;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_height, container, false);
        final SharedPreferences sharedPref = DataStore.getUserProfileStore(getActivity());
        final SharedPreferences.Editor editor = sharedPref.edit();
        ((TextView) rootView.findViewById(R.id.tvNextStepWeight)).setOnClickListener(this);
        VerticalSeekBar heightControl = (VerticalSeekBar) rootView.findViewById(R.id.vsbHeightSelector);
        heightTextValue = (TextView) rootView.findViewById(R.id.tvHeightValueSelected);
        heightControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;
            int minimumValue = 150;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress+minimumValue;
                heightTextValue.setText(String.valueOf(progressChanged));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                heightTextValue.setText(String.valueOf(progressChanged));
//                editor.putString(getString(R.string.user_height), String.valueOf(progressChanged));
//                editor.commit();
                finalHeight = progressChanged;

            }
        });
        return rootView;
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sharedPref = DataStore.getUserProfileStore(getActivity());
        SharedPreferences.Editor editor = sharedPref.edit();
        switch (v.getId()) {
            case R.id.tvNextStepWeight:
                DataStore.setUserHeight(String.valueOf(finalHeight));
                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.questionsPager);
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                break;
        }
    }
}
