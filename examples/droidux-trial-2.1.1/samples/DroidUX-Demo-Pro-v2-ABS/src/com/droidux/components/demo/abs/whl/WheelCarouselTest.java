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
package com.droidux.components.demo.abs.whl;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.droidux.anim.FloorBounceAnimation;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.interfaces.WheelInterfaces.Listeners.OnWheelItemClickListener;
import com.droidux.interfaces.WheelInterfaces.Listeners.OnWheelScrollListener;
import com.droidux.interfaces.WheelInterfaces.Views.WheelViewInterface;
import com.droidux.widget.wheel.WheelCarousel;

public class WheelCarouselTest extends BaseTestActivity {
	private Animation mAnim;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_wheelcarouseltests;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_whl_carousel_desc;
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		WheelCarousel carousel =  (WheelCarousel) findViewById(R.id.carousel);

		mAnim = new FloorBounceAnimation(0.3f, Animation.RELATIVE_TO_SELF);
		mAnim.setDuration(getResources().getInteger(R.integer.config_shortAnimTime));

		carousel.setAdapter(new ImageAdapter(this));
		carousel.setCallbackOnUnselectedItemClick(false);
		carousel.setOnWheelItemClickListener(new OnWheelItemClickListener() {
			@Override
			public void onItemClick(WheelViewInterface parent, View view, int position, long id) {
				Toast.makeText(WheelCarouselTest.this, "Clicked position: " + position, Toast.LENGTH_SHORT).show();
				view.startAnimation(mAnim);
			}
		});
		carousel.setOnWheelScrollListener(new OnWheelScrollListener() {

			@Override
			public void onScrolling(WheelViewInterface wheel, float distance) {				// TODO Auto-generated method stub

			}

			@Override
			public void onScrollStarted(WheelViewInterface wheel) {
			}

			@Override
			public void onScrollFinished(WheelViewInterface wheel) {
				View view = wheel.getSelectedView();
				if(view != null) {
					view.startAnimation(mAnim);
				}
			}
		});
		carousel.setInterpolator(new AnticipateOvershootInterpolator());
	}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);    //To change body of overridden methods use File | Settings | File Templates.
    }

    class ImageAdapter extends BaseAdapter {
		private final Context mContext;

		private final int[] mImageIds = {
				R.drawable.ic_social_behance,
				R.drawable.ic_social_vimeo,
				R.drawable.ic_social_digg,
				R.drawable.ic_social_youtube,
				R.drawable.ic_social_linkedin,
				R.drawable.ic_social_twitter,
				R.drawable.ic_social_facebook,
				R.drawable.ic_social_rss
		};
		public ImageAdapter(Context context) {
			mContext = context;
		}
		public int getCount() {
			return mImageIds.length;
		}
		public Object getItem(int position) {
			return position;
		}
		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView v;

			if(convertView==null) {
				v = new ImageView(mContext);
				v.setScaleType(ScaleType.CENTER_INSIDE);
			} else {
				v = (ImageView)convertView;
			}
			v.setImageResource(mImageIds[position]);
			return v;

		}
	}
}
