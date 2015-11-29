package com.siddhantjain.muskular.user;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.siddhantjain.muskular.R;
import com.siddhantjain.muskular.SeekArc;
import com.siddhantjain.muskular.utils.DataStore;

/**
 * Created by siddhaja on 11/3/2015.
 */
public class Weight extends Fragment implements View.OnClickListener {
    TextView weightTextValue;
    private SeekArc mSeekArc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weight, container, false);
        final SharedPreferences sharedPref = DataStore.getUserProfileStore(getActivity());
        final SharedPreferences.Editor editor = sharedPref.edit();
        ((TextView) rootView.findViewById(R.id.tvNextStepAge)).setOnClickListener(this);
        weightTextValue = (TextView) rootView.findViewById(R.id.tvWeightValueSelected);
        mSeekArc = (SeekArc) rootView.findViewById(R.id.saWeightSelector);
        mSeekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {
            int minimumValue = 30;
            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {
            }
            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {
            }

            @Override
            public void onProgressChanged(SeekArc seekArc, int progress,
                                          boolean fromUser) {

                weightTextValue.setText(String.valueOf(progress+minimumValue));
            }
        });




        return rootView;
    }
    @Override
    public void onClick(View v) {
        SharedPreferences sharedPref = DataStore.getUserProfileStore(getActivity());
        SharedPreferences.Editor editor = sharedPref.edit();
        switch (v.getId()) {
            case R.id.tvNextStepAge:
                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.questionsPager);
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                break;
        }
    }
}
