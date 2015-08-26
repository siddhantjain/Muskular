package com.siddhantjain.muskular;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * Created by siddhaja on 8/26/2015.
 */
public class what_we_expect_fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_what_we_expect, container, false);

        return rootView;
    }
    public void user_profile_input(View view){
        Intent intent = new Intent(what_we_expect_fragment.this.getActivity(),profile_getter.class);
        startActivity(intent);
    }
}

