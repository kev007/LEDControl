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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ViewSwitcher;
import com.droidux.components.demo.abs.R;
import com.droidux.widget.adapters.LoadOnDemandAdapter;

public class DuxListViewOnDemandAutoTest extends DuxListViewTest {
    EndlessImageAdapter mAdapter;

    @Override
    ListAdapter createListAdapter() {
        mAdapter = new EndlessImageAdapter(this);
        // init items
        for (int i = 0; i < 5; i++) {
            mAdapter.addToLast(i);
        }
        return mAdapter;
    }

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_list_ondemand_auto_desc;
    }

    static class EndlessImageAdapter extends WebImageAdapter implements LoadOnDemandAdapter {
        int[] ids;
        private static final int INCREASE = 5;

        EndlessImageAdapter(Context context) {
            super(context);
        }

        @Override
        void init() {
            list.clear();
        }


        @Override
        public void onBeforeRefresh() {

        }

        @Override
        public void refreshInBackground() {
            // here will normally be a long running task
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            ids = new int[INCREASE];
            for (int i = 0; i < ids.length; i++) {
                ids[i] = sRnd.nextInt(IMAGE_COUNT);
            }

        }

        @Override
        public void onAfterRefresh() {
            for (int i = 0; i < ids.length; i++) {
                addToLast(ids[i]);
            }
            notifyDataSetChanged();

        }

        @Override
        public boolean shouldAutoload(int lastPosition) {
            return true;
        }

        @Override
        public View getLoadingView(int lastPosition, View convertView, boolean autoLoad) {
            LoadingViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.loadingview_endless, null);
                holder = new LoadingViewHolder();
                convertView.setTag(holder);
                holder.loading = convertView.findViewById(R.id.loading);
                holder.manual = convertView.findViewById(R.id.manual);
            } else {
                holder = (LoadingViewHolder) convertView.getTag();
            }
            if (holder != null) {
                holder.loading.setVisibility(autoLoad ? View.VISIBLE : View.INVISIBLE);
                holder.manual.setVisibility(autoLoad ? View.INVISIBLE : View.VISIBLE);
            }
            return convertView;
        }

        @Override
        public boolean hasMore(int lastPosition) {
            return true; // forever
        }

        @Override
        void setupSwitcherAnimation(ViewSwitcher switcher) {
            // no animation
        }

        static class LoadingViewHolder {
            View loading;
            View manual;
        }
    }
}
