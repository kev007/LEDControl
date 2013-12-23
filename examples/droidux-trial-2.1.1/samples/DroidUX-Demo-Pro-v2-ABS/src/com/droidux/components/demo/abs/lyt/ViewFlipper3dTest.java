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
package com.droidux.components.demo.abs.lyt;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestListActivity;
import com.droidux.widget.layouts.ViewFlipper3D;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ViewFlipper3dTest extends BaseTestListActivity {
    private static final int ID_MENU_FLIP = Menu.FIRST+1;
    private ViewFlipper3D mFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewflipper3dtest);
        mFlipper = (ViewFlipper3D) findViewById(R.id.flipper);
        mFlipper.setFlipDuration(500);

        List<String> items = new ArrayList<String>();
        for (int i = 0; i <= 20; i++) {
            items.add("Item " + i);
        }

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));

    }

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_lyt_flip3d_desc;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(Menu.NONE, ID_MENU_FLIP, Menu.NONE, "Flip View");
        item.setIcon(R.drawable.ic_action_viewflip);
        item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ID_MENU_FLIP: {
                if (mFlipper.getDisplayedChild() == 0) {
                    showNext();
                } else {
                    showPrevious();
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPrevious(){
        mFlipper.setFlipDirection(ViewFlipper3D.FLIP_DIRECTION_RIGHT_LEFT);
        mFlipper.showPrevious();
    }

    private void showNext() {
        mFlipper.setFlipDirection(ViewFlipper3D.FLIP_DIRECTION_LEFT_RIGHT);
        mFlipper.showNext();
    }
}