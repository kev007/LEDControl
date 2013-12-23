package com.droidux.tutorials;

import android.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.droidux.tutorials.fragments.FeatureContextDrawerFragment;
import com.droidux.tutorials.fragments.FeatureHubFragment;
import com.droidux.widget.action.ActionDrawer;

// In this activity we'll use dual drawers and fragments to implement a contextual drawer.
public class ContextActionDrawerActivity extends BaseDualActionDrawerActivity
        implements FeatureHubFragment.OnFeatureSelectedListener, FeatureContextDrawerFragment.OnActionSelectedListener
{

    private FeatureHubFragment.FeatureItem mSelectedFeature;

    @Override
    void buildRightDrawer() {
        // Let's create the right drawer to host our context actions
        // We'll use dynamic fragment for the menu
        mRightDrawer = new ActionDrawer.Builder(this, new ActionDrawer.Callback() {

            @Override
            public View onCreateDrawerView() {
                // Here we create a fragment container
                FrameLayout container = new FrameLayout(ContextActionDrawerActivity.this);
                container.setId(R.id.drawer_fragment_container);
                container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                return container;
            }

            @Override
            public boolean onPrepareDrawerView(View view) {
                return false;
            }
        })
                .setDrawerPosition(ActionDrawer.DrawerPosition.RIGHT)
                .setShadowDrawable(R.drawable.drawer_shadow)
                .build();
    }

    @Override
    public View onCreateDrawerView() {
        // For the left drawer, we'll use static fragment to present a features menu
        return getLayoutInflater().inflate(R.layout.drawer_fragment, null);
    }


    @Override
    public void onFeatureSelected(FeatureHubFragment.FeatureItem feature) {
        mSelectedFeature = feature;
        showFeatureContent();
        mDrawer.closeDrawer();
    }

    private void showFeatureContent() {
        if (mSelectedFeature == null) {
            return;
        }
        // set the title to the selected feature
        getActionBar().setTitle(mSelectedFeature.name);

        // get the feature's content fragment
        Fragment content = mSelectedFeature.getContentFragment();
        // get the feature's context drawer
        Fragment contextDrawer = mSelectedFeature.getContextDrawerFragment();

        // add the fragments to their container
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_content, content)
                .replace(R.id.drawer_fragment_container, contextDrawer)
                .commit();
        invalidateOptionsMenu();
    }

    @Override
    public void onActionSelected(String action) {
        // We'll just show a toast when an action is selected.
        Toast.makeText(this, action + " is clicked.", Toast.LENGTH_SHORT).show();
        mRightDrawer.closeDrawer();
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Here we want to change the right drawer's icon for the selected feature
        MenuItem rightDrawer = menu.findItem(R.id.action_rightDrawer);
        rightDrawer.setIcon(mSelectedFeature != null ? mSelectedFeature.contextIconId : R.drawable.action_drawer);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onDrawerStateChange(int oldState, int newState) {
        super.onDrawerStateChange(oldState, newState);

        // We want to set the title to the selected feature when the drawer is closed
        switch (newState) {
            case ActionDrawer.STATE_DRAWER_CLOSED:
                if (mSelectedFeature != null) {
                    getActionBar().setTitle(mSelectedFeature.name);
                }
                break;
        }
    }

}
