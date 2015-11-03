package com.siddhantjain.muskular.user;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.siddhantjain.muskular.R;
import com.siddhantjain.muskular.utils.DataStore;

/**
 * Created by siddhaja on 10/30/2015.
 */
public class Name extends Fragment implements View.OnClickListener {
    EditText mUserScreenName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_name, container, false);
        ((TextView) rootView.findViewById(R.id.tvNextStepGender)).setOnClickListener(this);
        mUserScreenName = (EditText) rootView.findViewById(R.id.etName);
        return rootView;
    }
    @Override
    public void onClick(View v) {
        SharedPreferences sharedPref = DataStore.getUserProfileStore(getActivity());
        SharedPreferences.Editor editor = sharedPref.edit();
        //EditText etName = (EditText) v.findViewById(R.id.etName);
        switch (v.getId()) {
            case R.id.tvNextStepGender:
                editor.putString(getString(R.string.user_name), mUserScreenName.getText().toString());
                editor.commit();
                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.questionsPager);
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                break;
        }
    }

}
