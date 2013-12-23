/*******************************************************************************
 * Copyright (C) 2011 Ximpl
 * All Rights Reserved. This program and the accompanying materials
 * are owned by Ximpl or its suppliers.  The program is protected by international copyright laws and treaty provisions.  Any violation will be prosecuted under applicable laws.
 * NOTICE: The following is Source Code and is subject to all
 * restrictions on such code as contained in the End User License Agreement accompanying this product.
 ******************************************************************************/
package com.droidux.components.demo.abs.aw;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.view.MenuItem;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.ActivityHelper;
import com.droidux.components.demo.abs.internal.BaseTestListActivity;
import com.droidux.widget.action.ActionDrawer;
import com.droidux.widget.list.DuxListView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ActionDrawerListActivityTest extends BaseTestListActivity implements ActionDrawer.Callback {
    private static final String STATE_ACTION_DRAWER = "com.droidux.components.demo.state_action_drawer";

    private ActionDrawer mDrawer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If the action drawer's mode is set to CONTENT_OVERLAY, we need to make sure
        // that the setContentView() is called first.  In ListActivity, calling the getListView() will make sure of that.
        ListView lv = getListView();
        lv.setCacheColorHint(0);

        mDrawer = new ActionDrawer.Builder(this, this)
                .setDrawerMode(ActionDrawer.DrawerMode.CONTENT_OVERLAY)
                .build();

        List<String> items = new ArrayList<String>();
        for (int i = 0; i <= 20; i++) {
            items.add("Item " + i);
        }

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
    }

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_actn_drawer_listactivity_desc;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.toggleDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateDrawerView() {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tv.setText("Drawer");
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    @Override
    public void onBackPressed() {
        if (mDrawer != null && mDrawer.isDrawerVisible()) {
            mDrawer.closeDrawer();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onPrepareDrawerView(View view) {
        view.setBackgroundColor(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
        return true;
    }

    @Override
    protected void onListItemClick(DuxListView l, View v, int position, long id) {
        String str = (String) getListAdapter().getItem(position);
        Toast.makeText(this, "Clicked: " + str, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_ACTION_DRAWER, mDrawer.onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mDrawer.onRestoreInstanceState(savedInstanceState.getParcelable(STATE_ACTION_DRAWER));
    }

}