package com.siddhantjain.muskular.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.siddhantjain.muskular.R;

/**
 * Created by siddhaja on 10/30/2015.
 */
public class Name extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_name, container, false);
        return rootView;
    }
}
