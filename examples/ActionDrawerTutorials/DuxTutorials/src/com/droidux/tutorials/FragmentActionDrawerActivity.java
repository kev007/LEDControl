package com.droidux.tutorials;

import android.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.droidux.tutorials.fragments.FeatureHubFragment;
import com.droidux.widget.action.ActionDrawer;

// This is the last part of our three-parts tutorial series on the navigation drawer pattern using the ActionDrawer widget.
// For the previous parts of the tutorial, please visit our website at http://www.droidux.com
//
// In this tutorial we'll show how to use fragment within the navigation drawer.
public class FragmentActionDrawerActivity extends BaseSingleActionDrawerActivity implements FeatureHubFragment.OnFeatureSelectedListener{


    private FeatureHubFragment.FeatureItem mSelectedFeature;

    @Override
    public View onCreateDrawerView() {

        // Static fragment
        // We'll inflate the fragment from xml
//        return getLayoutInflater().inflate(R.layout.drawer_fragment, null);

        // Dynamic fragment
        // We'll create a container to host the fragment
        FrameLayout container = new FrameLayout(this);
        container.setId(R.id.drawer_fragment_container);
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // Create the fragment
        Fragment fragment = new FeatureHubFragment();

        // Add the fragment to the container
        getFragmentManager().beginTransaction().add(R.id.drawer_fragment_container, fragment).commit();

        return container;
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
        // replace the content fragment
        getFragmentManager().beginTransaction().replace(R.id.fragment_content, content).commit();
    }

    @Override
    public void onDrawerStateChange(int oldState, int newState) {
        super.onDrawerStateChange(oldState, newState);

        // We want to set the title to the selected feature
        switch (newState) {
            case ActionDrawer.STATE_DRAWER_CLOSED:
                if (mSelectedFeature != null) {
                    getActionBar().setTitle(mSelectedFeature.name);
                }
                break;
        }
    }
}
