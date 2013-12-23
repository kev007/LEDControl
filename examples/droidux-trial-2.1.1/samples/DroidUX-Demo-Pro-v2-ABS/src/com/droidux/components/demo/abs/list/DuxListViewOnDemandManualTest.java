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
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListAdapter;
import com.droidux.components.demo.abs.R;

public class DuxListViewOnDemandManualTest extends DuxListViewTest {
	DuxListViewOnDemandAutoTest.EndlessImageAdapter mAdapter;

	@Override
	ListAdapter createListAdapter() {
		mAdapter = new EndlessManualLoadingAdapter(this);
		// init items
		for(int i=0;i<5;i++) {
			mAdapter.addToLast(i);
		}
		return mAdapter;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_list_ondemand_manual_desc;
    }

    static class EndlessManualLoadingAdapter extends DuxListViewOnDemandAutoTest.EndlessImageAdapter {
		int[] ids;
		boolean manualLoading=false;
		EndlessManualLoadingAdapter(Context context) {
			super(context);
		}

		@Override
		public boolean shouldAutoload(int lastPosition) {
			return false;
		}

		@Override
		public View getLoadingView(int loastPosition, View convertView, boolean autoload) {
			LoadingViewHolder holder = null;
			if(convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.loadingview_endless, null);
				holder= new LoadingViewHolder();
				convertView.setTag(holder);
				holder.loading = convertView.findViewById(R.id.loading);
				holder.manual = convertView.findViewById(R.id.manual);
				final LoadingViewHolder lvholder = holder;
				holder.manual.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						lvholder.loading.setVisibility(View.VISIBLE);
						lvholder.manual.setVisibility(View.INVISIBLE);
						manualLoading=true;
						(new ManualRefreshTask(EndlessManualLoadingAdapter.this)).execute();
					}
				});
			} else {
				holder = (LoadingViewHolder)convertView.getTag();
			}
			if(holder != null) {
				boolean isLoading = autoload || manualLoading;
				holder.loading.setVisibility(isLoading?View.VISIBLE:View.INVISIBLE);
				holder.manual.setVisibility(isLoading?View.INVISIBLE:View.VISIBLE);
			}
			return convertView;
		}

	}

	private static class ManualRefreshTask extends AsyncTask<Void, Void, Void > {
		EndlessManualLoadingAdapter endless;
		ManualRefreshTask(EndlessManualLoadingAdapter adapter) {
			endless = adapter;
		}
		@Override
		protected Void doInBackground(Void... params) {
			endless.refreshInBackground();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			endless.onAfterRefresh();
			endless.manualLoading=false;
			super.onPostExecute(result);
		}
	}
}
