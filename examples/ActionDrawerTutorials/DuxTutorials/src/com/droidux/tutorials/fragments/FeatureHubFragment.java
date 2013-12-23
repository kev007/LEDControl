package com.droidux.tutorials.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.droidux.tutorials.R;
import com.droidux.widget.list.DuxAutoScrollListView;

import java.util.ArrayList;
import java.util.List;

public class FeatureHubFragment extends Fragment {
    private static final String STATE_SELECTED_POS = "com.droidux.tutorials.features.state_selected_pos";
    private DuxAutoScrollListView mDrawerList;
    private OnFeatureSelectedListener mListener;
    private List<FeatureItem> mFeatures;
    private int mSelectedPosition;

    public interface OnFeatureSelectedListener {
        void onFeatureSelected(FeatureItem feature);
    }


    public FeatureHubFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFeatures = createFeatureList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDrawerList = (DuxAutoScrollListView) inflater.inflate(R.layout.list_drawer, null);
        mDrawerList.setAdapter(new FeatureListDrawerAdapter(getActivity(), R.layout.drawer_list_item, mFeatures));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectFeature(position);
            }
        });

        if (savedInstanceState != null) {
            mSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POS);
        }
        return mDrawerList;
    }

    private void selectFeature(int position) {
        mDrawerList.setItemChecked(position, true);
        mSelectedPosition = position;

        if (mListener != null) {
            mListener.onFeatureSelected(mFeatures.get(position));
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        selectFeature(mSelectedPosition);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity instanceof OnFeatureSelectedListener) {
            mListener = (OnFeatureSelectedListener)activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POS, mSelectedPosition);
    }

    private static List<FeatureItem> createFeatureList() {
        List<FeatureItem> items = new ArrayList<FeatureItem>();

        items.add(new FeatureItem("Now", R.drawable.ic_now, R.drawable.ic_now_content, R.drawable.ic_now_context, 8));
        items.add(new FeatureItem("Hot", R.drawable.ic_hot, R.drawable.ic_hot_content, R.drawable.ic_hot_context, 7));
        items.add(new FeatureItem("Trending", R.drawable.ic_trending, R.drawable.ic_trending_content, R.drawable.ic_trending_context, 5));
        items.add(new FeatureItem("People", R.drawable.ic_people, R.drawable.ic_people_content, R.drawable.ic_people_context, 10));
        items.add(new FeatureItem("Location", R.drawable.ic_location, R.drawable.ic_location_content, R.drawable.ic_location_context, 8));
        items.add(new FeatureItem("Chat", R.drawable.ic_chat, R.drawable.ic_chat_content, R.drawable.ic_chat_context, 9));
        items.add(new FeatureItem("Books", R.drawable.ic_books, R.drawable.ic_books_content, R.drawable.ic_books_context, 6));
        items.add(new FeatureItem("Travel", R.drawable.ic_travel, R.drawable.ic_travel_content, R.drawable.ic_travel_context, 7));
        items.add(new FeatureItem("Shop", R.drawable.ic_shop, R.drawable.ic_shop_content, R.drawable.ic_shop_context, 8));
        items.add(new FeatureItem("Settings", R.drawable.ic_setting, R.drawable.ic_setting_content, R.drawable.ic_setting_context, 5));

        return items;

    }

    public static class FeatureItem {
        public String name;
        public int contextIconId;
        public int contentIconId;
        public int drawerIconId;
        private int actionsCount;

        FeatureItem(String name, int drawerIconId, int contentIconId, int contextIconId, int actionsCount) {
            this.name = name;
            this.contextIconId = contextIconId;
            this.contentIconId = contentIconId;
            this.drawerIconId = drawerIconId;
            this.actionsCount = actionsCount;
        }

        public Fragment getContentFragment() {
            FeatureContentFragment fragment = new FeatureContentFragment();
            Bundle args= new Bundle();
            args.putString(FeatureContentFragment.ARGS_NAME, name);
            args.putInt(FeatureContentFragment.ARGS_ICONID, contentIconId);
            fragment.setArguments(args);
            return fragment;
        }
        public Fragment getContextDrawerFragment() {
            FeatureContextDrawerFragment fragment = new FeatureContextDrawerFragment();
            Bundle args = new Bundle();
            args.putString(FeatureContentFragment.ARGS_NAME, name);
            args.putInt(FeatureContextDrawerFragment.ARGS_ITEMS, actionsCount);
            fragment.setArguments(args);
            return fragment;
        }
    }

    private class FeatureListDrawerAdapter extends ArrayAdapter<FeatureItem> {
        private final int layoutResId;

        public FeatureListDrawerAdapter(Context context, int layoutResId, List<FeatureItem> featureList) {
            super(context, layoutResId, featureList);
            this.layoutResId = layoutResId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv;
            if (convertView == null) {
                tv = (TextView) LayoutInflater.from(getContext()).inflate(layoutResId, null);
            } else {
                tv = (TextView)convertView;
            }
            FeatureItem item = getItem(position);
            tv.setText(item.name);
            tv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(item.drawerIconId), null, null, null);
            return tv;
        }
    }
}