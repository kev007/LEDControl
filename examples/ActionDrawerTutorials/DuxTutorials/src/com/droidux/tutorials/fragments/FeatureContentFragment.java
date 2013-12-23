package com.droidux.tutorials.fragments;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.droidux.tutorials.R;

public class FeatureContentFragment extends Fragment {

    public static final String ARGS_NAME = "args.name";
    public static final String ARGS_ICONID = "args.iconid";

    public FeatureContentFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView tv = (TextView) inflater.inflate(R.layout.textview_content, container, false);
        Drawable icon = getResources().getDrawable(getArguments().getInt(ARGS_ICONID));
        tv.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);
        tv.setText(getArguments().getString(ARGS_NAME) + " Content") ;
        return tv;
    }
}
