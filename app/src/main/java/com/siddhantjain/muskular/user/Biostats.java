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
import android.widget.SeekBar;
import android.widget.TextView;

import com.siddhantjain.muskular.R;
import com.siddhantjain.muskular.utils.DataStore;

/**
 * Created by siddhaja on 8/26/2015.
 */


public class Biostats extends Fragment {

    private SeekBar heightControl = null;
    private SeekBar weightControl = null;
    private SeekBar ageControl = null;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_biostats, container, false);
        final SharedPreferences sharedPref = DataStore.getUserProfileStore(getActivity());
        final SharedPreferences.Editor editor = sharedPref.edit();
        readAndSetBioStats(rootView, editor);
        return rootView;
    }

    private void readAndSetBioStats(View rootView, final SharedPreferences.Editor editor) {
        heightControl = (SeekBar) rootView.findViewById(R.id.seek_height);
        weightControl = (SeekBar) rootView.findViewById(R.id.seek_weight);
        ageControl = (SeekBar) rootView.findViewById(R.id.seek_age);

        final TextView heightTextValue = (TextView) rootView.findViewById(R.id.heightValue);
        final TextView weightTextValue = (TextView) rootView.findViewById(R.id.weightValue);
        final TextView ageTextValue = (TextView) rootView.findViewById(R.id.ageValue);


        heightControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressChanged = progress;
                heightTextValue.setText(String.valueOf(progress));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                heightTextValue.setText(String.valueOf(progressChanged));
                editor.putString(getString(R.string.user_height), String.valueOf(progressChanged));
                editor.commit();
            }
        });

        weightControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressChanged = progress;
                weightTextValue.setText(String.valueOf(progress));

            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                weightTextValue.setText(String.valueOf(progressChanged));
                editor.putString(getString(R.string.user_weight), String.valueOf(progressChanged));
                editor.commit();
            }
        });

        ageControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressChanged = progress;
                ageTextValue.setText(String.valueOf(progress));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                ageTextValue.setText(String.valueOf(progressChanged));
                editor.putString(getString(R.string.user_yob), String.valueOf(progressChanged));
                editor.commit();
            }
        });
    }
}
