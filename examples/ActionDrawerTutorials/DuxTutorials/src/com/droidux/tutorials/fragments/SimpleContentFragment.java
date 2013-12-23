package com.droidux.tutorials.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.droidux.tutorials.R;

public class SimpleContentFragment extends Fragment {
    public static final String ARG_COLOR_HEX = "arg.color.hex";
    public SimpleContentFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView tv = (TextView) inflater.inflate(R.layout.textview_content, container, false);
        String colorHex = getArguments().getString(ARG_COLOR_HEX);
        ShapeDrawable icon = new ShapeDrawable(new OvalShape());
        icon.setIntrinsicHeight(256);
        icon.setIntrinsicWidth(256);
        icon.setBounds(0, 0, 256, 256);
        icon.getPaint().setColor(Color.parseColor(colorHex));
        tv.setCompoundDrawables(null, icon, null, null);
        tv.setText(colorHex.toUpperCase());

        return tv;
    }
}
