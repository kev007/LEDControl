package com.droidux.tutorials;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.droidux.widget.action.ActionDrawer;

abstract class BaseActionDrawerActivity extends Activity implements ActionDrawer.Callback {

    private static final String STATE_ACTION_DRAWER = "com.droidux.tutorials.actions.state_action_drawer";
    ActionDrawer mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        buildDrawers();


    }

    void buildDrawers() {
        buildLeftDrawer();
        buildRightDrawer();
    }

    abstract void buildRightDrawer();

    abstract void buildLeftDrawer();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public View onCreateDrawerView() {
        // Here you create your drawer layout. We'll use a simple layout for this tutorial.
        View drawerLayout = LayoutInflater.from(this).inflate(R.layout.drawer_basic, null);
        return drawerLayout;
    }

    @Override
    public boolean onPrepareDrawerView(View arg0) {
        // We're not gong to do anything here for now.
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // We need to toggle the drawer when user click on the "home" button
        switch(item.getItemId()) {
            case android.R.id.home:
                mDrawer.toggleDrawer();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // When the drawer is open we want to close it when the user press the "back" button
        if(mDrawer != null && mDrawer.isDrawerVisible()) {
            mDrawer.closeDrawer();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // We're going to save the drawer's state on configuration change
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_ACTION_DRAWER, mDrawer.onSaveInstanceState());
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Here we'll restore the drawer state after configuration change
        super.onRestoreInstanceState(savedInstanceState);
        Parcelable state = savedInstanceState.getParcelable(STATE_ACTION_DRAWER);
        mDrawer.onRestoreInstanceState(state);
    }
}
