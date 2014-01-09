package com.droidux.tutorials.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.droidux.tutorials.R;
import com.droidux.widget.list.DuxAutoScrollListView;

public class FeatureContextDrawerFragment extends Fragment {

    public static final String ARGS_NAME = "args.name";
    public static final String ARGS_ITEMS = "args.items";
    private DuxAutoScrollListView mDrawerList;
    private String[] mActions;
    private OnActionSelectedListener mListener;

    public interface OnActionSelectedListener {
        void onActionSelected(String action);
    }

    public FeatureContextDrawerFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name = getArguments().getString(ARGS_NAME);
        int length = getArguments().getInt(ARGS_ITEMS);
        mActions = new String[length];
        for (int i = 0; i < length; i++) {
            mActions[i] = String.format("%s Action %d", name, i + 1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDrawerList = (DuxAutoScrollListView) inflater.inflate(R.layout.list_drawer, null);
        mDrawerList.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.drawer_list_item, mActions));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (mListener != null) {
                    mListener.onActionSelected(mActions[position]);
                }
            }
        });
        mDrawerList.setBackgroundColor(0xffe98354);
        mDrawerList.setDivider(null);
        mDrawerList.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
        return mDrawerList;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnActionSelectedListener) {
            mListener = (OnActionSelectedListener)activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
