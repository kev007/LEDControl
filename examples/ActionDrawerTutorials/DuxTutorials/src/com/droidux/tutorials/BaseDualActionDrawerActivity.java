package com.droidux.tutorials;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;

import com.droidux.tutorials.ui.ActionDrawerToggle;
import com.droidux.widget.action.ActionDrawer;

class BaseDualActionDrawerActivity extends BaseSingleActionDrawerActivity {
    private static final String STATE_ACTION_RIGHTDRAWER = "com.droidux.tutorials.actions.state_action_rightdrawer";
    ActionDrawer mRightDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRightDrawer.setOnDrawerStateChangeListener(new ActionDrawer.OnDrawerStateChangeListener() {

            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                mDrawer.setDrawerEnabled(newState == ActionDrawer.STATE_DRAWER_CLOSED);
            }
        });

    }

    @Override
    void buildRightDrawer() {
        // Let's create our RIGHT drawer
        mRightDrawer = new ActionDrawer.Builder(this, this)
                .setDrawerPosition(ActionDrawer.DrawerPosition.RIGHT)
                .build();

    }

    @Override
    public void onDrawerStateChange(int oldState, int newState) {
        super.onDrawerStateChange(oldState, newState);
        // We don't want the other drawer to be activated when one drawer is open.
        mRightDrawer.setDrawerEnabled(newState == ActionDrawer.STATE_DRAWER_CLOSED);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_drawer_right, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerVisible = mDrawer.isDrawerVisible();
        menu.findItem(R.id.action_rightDrawer).setVisible(!drawerVisible);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_rightDrawer:
            mRightDrawer.toggleDrawer();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(mRightDrawer != null && mRightDrawer.isDrawerVisible()) {
            mRightDrawer.closeDrawer();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // We're going to save the drawer's state on configuration change
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_ACTION_RIGHTDRAWER, mRightDrawer.onSaveInstanceState());
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Here we'll restore the drawer state after configuration change
        super.onRestoreInstanceState(savedInstanceState);
        Parcelable state = savedInstanceState.getParcelable(STATE_ACTION_RIGHTDRAWER);
        mRightDrawer.onRestoreInstanceState(state);
    }

}
