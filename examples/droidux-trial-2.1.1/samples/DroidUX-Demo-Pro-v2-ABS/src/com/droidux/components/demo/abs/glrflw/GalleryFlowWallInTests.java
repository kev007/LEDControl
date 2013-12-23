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
package com.droidux.components.demo.abs.glrflw;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.ImageView.ScaleType;
import com.droidux.components.demo.abs.R;
import com.droidux.interfaces.GalleryFlowInterfaces.Adapters.WallAdapterLooper;
import com.droidux.interfaces.GalleryFlowInterfaces.Wall.OnCellClickListener;
import com.droidux.widget.adapters.UrlImageAdapter;
import com.droidux.widget.gallery.GalleryFlowWall;

public class GalleryFlowWallInTests extends GalleryFlowViewTestsBase{
	private WallAdapterLooper mAdapterLooper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mGalleryFlow.setSelection(mAdapterLooper.getCenterPosition());
	}
	@Override
	protected int getLayoutId() {
		return R.layout.activity_gallerywallviewtest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_glrflow_wallin_desc;
    }

    @Override
	SpinnerAdapter createAdapter() {
		GalleryFlowWall galleryWall = (GalleryFlowWall)mGalleryFlow;
		mAdapterLooper = new WallAdapterLooper(new PhotoAdapter(), galleryWall.getWallHeight());
		return (mAdapterLooper);
	}

	@Override
	void setGalleryFlowListeners() {
		Gallery galleryFlow = mGalleryFlow;
		GalleryFlowWall galleryWall = (GalleryFlowWall) galleryFlow;
		galleryWall.setOnCellClickListener(new OnCellClickListener() {
			@Override
			public void onCellClick(AdapterView<?> parent, View view, int position, long id) {
				position = mAdapterLooper.getInnerPosition(position);
				Toast.makeText(GalleryFlowWallInTests.this, "Cell clicked position: " + position, Toast.LENGTH_SHORT).show();
			}
		});
	}

	private class PhotoAdapter extends BaseAdapter implements UrlImageAdapter {
		private final String[] mPhotos;

		PhotoAdapter() {
			mPhotos = new String[15];
			mPhotos[0]="chessboard";
			mPhotos[1]="earth";
			mPhotos[2]="grapes";
			mPhotos[3]="lake";
			mPhotos[4]="mustang";
			mPhotos[5]="nebula";
			mPhotos[6]="newyork";
			mPhotos[7]="orange";
			mPhotos[8]="redkite";
			mPhotos[9]="rose";
			mPhotos[10]="seagull";
			mPhotos[11]="shuttle";
			mPhotos[12]="smarties";
			mPhotos[13]="sun";
			mPhotos[14]="tulips";
		}
		@Override
		public int getCount() {
			return mPhotos.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView v;

			if(convertView==null) {
				v = new ImageView(GalleryFlowWallInTests.this);
				v.setPadding(2, 2, 2, 2);
				v.setBackgroundColor(0xffFCFAE1);
				v.setScaleType(ScaleType.CENTER);
			} else {
				v = (ImageView)convertView;
			}
			v.setImageResource(R.drawable.placeholder_land);
			return v;
		}
		@Override
		public void downloadUrlImages(int position, View itemView, Request request) {
			request.download(String.format("http://www.droidux.com/images/droidux/apidemos_v2/land/%s.jpg",mPhotos[position]));
		}
		@Override
		public void onImageFail(int position, View itemView, String url, int refId, Exception exception) {
			((ImageView)itemView).setImageResource(R.drawable.placeholder_land_err);

		}
		@Override
		public void onImageReady(int position, View itemView, String url, int refId, Bitmap bitmap) {
			((ImageView)itemView).setImageBitmap(bitmap);
		}
		@Override
		public void onWaitingForImage(int position, View itemView, String url, int refId) {

		}

	}
}
