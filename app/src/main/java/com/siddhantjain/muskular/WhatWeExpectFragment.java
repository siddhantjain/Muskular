package com.siddhantjain.muskular;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siddhantjain.muskular.user.UserProfiler;


/**
 * Created by siddhaja on 8/26/2015.
 */
public class WhatWeExpectFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_what_we_expect, container, false);

        return rootView;
    }
    public void user_profile_input(View view){
        Intent intent = new Intent(WhatWeExpectFragment.this.getActivity(),UserProfiler.class);
        startActivity(intent);
    }
}

