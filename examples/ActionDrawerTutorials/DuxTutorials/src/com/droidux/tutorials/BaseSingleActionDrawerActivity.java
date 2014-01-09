package com.droidux.tutorials;

import android.os.Bundle;
import android.view.Menu;

import com.droidux.tutorials.ui.ActionDrawerToggle;
import com.droidux.widget.action.ActionDrawer;
import com.droidux.widget.action.ActionDrawer.Callback;

class BaseSingleActionDrawerActivity extends BaseActionDrawerActivity implements Callback, ActionDrawer.OnDrawerStateChangeListener{
    private CharSequence mTitle;
    private ActionDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTitle = getTitle();

        mDrawer.setOnDrawerStateChangeListener(this);

        mDrawerToggle = new ActionDrawerToggle(this, mDrawer, R.drawable.ic_drawer_home);
        mDrawer.setOnDrawingListener(mDrawerToggle);

    }

    @Override
    void buildRightDrawer() {

    }

    @Override
    void buildLeftDrawer() {
        // Let's create our LEFT drawer
        // new design guideline
        mDrawer = new ActionDrawer.Builder(this, this)
                // drawer below action bar
                .setDrawerMode(ActionDrawer.DrawerMode.CONTENT_OVERLAY)
                        // drawer on top of content
                .setDrawerOnTop(true)
                .setShadowDrawable(R.drawable.drawer_shadow)
                .build();
        // don't apply fade effect when drawer open
        mDrawer.setDrawerFadeEnabled(false);
        // slide the drawer view without parallax effect
        mDrawer.setParallaxFactor(1f);
        // dim the content when the drawer is open
        mDrawer.setDisableContentWhenDrawerVisible(true, true);
        // don't slide the content while the drawer is opening
        mDrawer.setContentSlideEnabled(false);
    }

    @Override
    public void onDrawerStateChange(int oldState, int newState) {
        switch (newState) {
            case ActionDrawer.STATE_DRAWER_CLOSED:
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
                break;
            case ActionDrawer.STATE_DRAWER_OPEN:
                getActionBar().setTitle(R.string.text_selectFeature);
                invalidateOptionsMenu();
                break;
        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerVisible = mDrawer.isDrawerVisible();
        menu.findItem(R.id.action_search).setVisible(!drawerVisible);
        return super.onPrepareOptionsMenu(menu);
    }

}
