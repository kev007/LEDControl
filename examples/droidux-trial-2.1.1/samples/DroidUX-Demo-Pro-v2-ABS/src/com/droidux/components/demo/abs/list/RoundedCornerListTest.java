/*
 * Copyright (C) 2011-2012 Ximpl
 * All Rights Reserved. This program and the accompanying materials
 * are owned by Ximpl or its suppliers.  The program is protected by
 * international copyright laws and treaty provisions.
 * Any violation will be prosecuted under applicable laws.
 * NOTICE: The following is Source Code and is subject to all
 * restrictions on such code as contained in the End User License Agreement
 * accompanying this product.
 */
package com.droidux.components.demo.abs.list;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import com.droidux.components.demo.abs.R;

public class RoundedCornerListTest extends DuxListViewTest {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roundedcornerlisttest);
        setListAdapter(new WebImageAdapter(this) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                int bgColor = getResources().getColor(position % 2 == 0 ? R.color.holo_purple_1 : R.color.holo_purple_2);
                view.setBackgroundDrawable(new ItemBackgroundDrawable(bgColor));
                return view;
            }
        });
        getListView().setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Toast.makeText(RoundedCornerListTest.this, "Click position: " + position, Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_list_rounded_desc;
    }

    private static class ItemBackgroundDrawable extends StateListDrawable {
        public ItemBackgroundDrawable(int backgroundColor) {
            Drawable background = new ColorDrawable(backgroundColor);
            Drawable selected = new ColorDrawable(Color.TRANSPARENT);
            addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled,
                    android.R.attr.state_window_focused}, selected);
            addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled,
                    android.R.attr.state_window_focused, android.R.attr.state_selected}, selected);
            addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_window_focused, android.R.attr.state_selected}, selected);
            addState(new int[]{}, background);
        }
    }
}
