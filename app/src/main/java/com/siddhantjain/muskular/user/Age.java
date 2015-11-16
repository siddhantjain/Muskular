package com.siddhantjain.muskular.user;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.siddhantjain.muskular.R;
import com.siddhantjain.muskular.utils.DataStore;

import java.util.Calendar;

/**
 * Created by siddhaja on 11/4/2015.
 */
public class Age extends Fragment implements View.OnClickListener {
    private TextView yearOfBirth;
    private int selectedYearOfBirth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_age, container, false);
        yearOfBirth = (TextView) rootView.findViewById(R.id.tvYearOfBirth);
        yearOfBirth.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {
                show(rootView);
            }
        });
        ((TextView) rootView.findViewById(R.id.tvNextStepGoal)).setOnClickListener(this);
        return rootView;
    }

    public void show(View v)
    {
        selectedYearOfBirth = 1993;
        final Dialog d = new Dialog(v.getContext());
        d.setTitle("Select your year of birth");
        d.setContentView(R.layout.dialog_year_picker);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.npYearPicker);
        final Button btnSubmit = (Button) d.findViewById(R.id.btnSubmitBirthYear);
        np.setMaxValue(1997);
        np.setMinValue(1900);
        np.setValue(1993);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                selectedYearOfBirth = newVal;
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearOfBirth.setText(String.valueOf(selectedYearOfBirth));
                d.dismiss();
            }
        });

        d.show();


    }
    @Override
    public void onClick(View v) {
        SharedPreferences sharedPref = DataStore.getUserProfileStore(getActivity());
        SharedPreferences.Editor editor = sharedPref.edit();
        switch (v.getId()) {
            case R.id.tvNextStepGoal:
                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.questionsPager);
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                break;
        }
    }
}
