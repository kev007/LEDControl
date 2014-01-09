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
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.droidux.cache.UrlImageCache;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestListActivity;
import com.droidux.widget.adapters.UrlImageAdapter;
import com.droidux.widget.list.DuxListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DuxListViewTest extends BaseTestListActivity {
	static final int ID_MENU_CLEAR_CACHE = Menu.FIRST+1;
	static final Random sRnd = new Random();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(createListAdapter());

	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_list_web_desc;
    }

    ListAdapter createListAdapter() {
		return new WebImageAdapter(this);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuItem item = menu.add(Menu.NONE, ID_MENU_CLEAR_CACHE, Menu.NONE, "Clear Cache");
        item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		return true;

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case ID_MENU_CLEAR_CACHE:
				clearImageCache();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void clearImageCache() {
		// clear downloaded images from the cache
		UrlImageCache cache = ((DuxListView)getListView()).getImageCache();
		if(cache != null) {
			cache.clear();
		}
	}
	static class WebImageAdapter extends BaseAdapter implements UrlImageAdapter {
		static final int IMAGE_COUNT = 50;
		final Context context;
		final List<Integer> list = new ArrayList<Integer>();

		public WebImageAdapter(Context context) {
			this.context = context;
			init();
		}
		void init() {
			int count = 3*IMAGE_COUNT;
			for (int i = 0; i < count; i++) {
				list.add(i%IMAGE_COUNT);
			}
		}
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.list_image_item, null);
				holder = new ViewHolder();
				holder.switcher = (ViewSwitcher) convertView.findViewById(R.id.switcher);
				setupSwitcherAnimation(holder.switcher);
				holder.image = (ImageView)convertView.findViewById(R.id.image);
				holder.title = (TextView)convertView.findViewById(R.id.title);
				holder.subtitle = (TextView)convertView.findViewById(R.id.subtitle);
				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();
			holder.image.setImageResource(R.drawable.placeholder_128);
			holder.subtitle.setText("Position# " + position);
			return convertView;
		}
		void setupSwitcherAnimation(ViewSwitcher switcher) {
			Animation fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
			fadeIn.setDuration(200);
			Animation fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);
			fadeOut.setDuration(200);
			switcher.setAnimateFirstView(false);
			switcher.setInAnimation(fadeIn);
			switcher.setOutAnimation(fadeOut);
		}
		@Override
		public void downloadUrlImages(int position, View itemView, Request request) {
			int imageId = (list.get(position) % IMAGE_COUNT)+1;
			ViewHolder holder = (ViewHolder) itemView.getTag();
			holder.title.setText("Image# " + imageId);
			//NOTE: try with your own images here.
			request.download("http://www.droidux.com/images/droidux/apidemos_v2/thumbs-128/" + imageId + ".png");
		}

		@Override
		public void onImageFail(int position, View itemView, String url, int refId, Exception exception) {
			ViewHolder holder = (ViewHolder) itemView.getTag();
			holder.image.setImageResource(R.drawable.placeholder_128);
			holder.switcher.setDisplayedChild(1);
		}

		@Override
		public void onImageReady(int position, View itemView, String url, int refId, Bitmap bitmap) {
			ViewHolder holder = (ViewHolder) itemView.getTag();
			holder.image.setImageBitmap(bitmap);
			holder.switcher.setDisplayedChild(1);
		}
		@Override
		public void onWaitingForImage(int position, View itemView, String url, int refId) {
			ViewHolder holder = (ViewHolder) itemView.getTag();
			holder.switcher.setDisplayedChild(0);
		}

		void addToFirst(int imageId) {
			list.add(0, imageId % IMAGE_COUNT);
		}
		void addToLast(int imageId) {
			list.add(imageId % IMAGE_COUNT);
		}
		void clear() {
			list.clear();
			notifyDataSetChanged();
		}


	}

	static class ViewHolder {
		ImageView image;
		TextView title;
		TextView subtitle;
		ViewSwitcher switcher;

	}
}
