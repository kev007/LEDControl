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

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.droidux.anim.FloorBounceAnimation;
import com.droidux.cache.UrlImageCache;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestFragmentActivity;
import com.droidux.interfaces.GalleryFlowInterfaces.GalleryFlowViewInterface;
import com.droidux.widget.adapters.UrlImageAdapter;

import java.util.Arrays;

abstract class GalleryFlowViewFragmentTestsBase extends BaseTestFragmentActivity implements AnimationListener {
    private static final int ID_MENU_CLEAR_CACHE = Menu.FIRST + 1;

    Gallery mGalleryFlow;


    DemoImage[] mImages;
    private static UrlImageCache sCache = new UrlImageCache.Default();

    @Override
    protected int getLayoutId()   {
        return R.layout.frame_container;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_container, createFragment(), null);
        fragmentTransaction.commit();
    }
    private Fragment createFragment() {
        return new GalleryFlowFragment();
    }

    private Animation createMediumAnimation(boolean setListener) {
        Animation anim = new FloorBounceAnimation(0.30f, Animation.RELATIVE_TO_SELF);
        anim.setDuration(getResources().getInteger(R.integer.config_mediumAnimTime));
        if (setListener) {
            anim.setAnimationListener(this);
        }
        return anim;

    }

    @Override
    protected void onResume() {
        super.onResume();
        ((GalleryFlowViewInterface) mGalleryFlow).setImageCache(sCache);
    }


    SpinnerAdapter createAdapter() {
        return new ImageAdapter(this, mImages);
    }

    void setGalleryFlowListeners() {
        mGalleryFlow.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                // ignore when the clicked item is not the same as the selected item
                if (position != parent.getSelectedItemPosition()) {
                    return;
                }
                view.startAnimation(createMediumAnimation(true));
            }
        });


    }

    @Override
    public void onAnimationEnd(Animation animation) {
        ((GalleryFlowViewInterface) mGalleryFlow).setScrollingEnabled(true);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onAnimationStart(Animation animation) {
        ((GalleryFlowViewInterface) mGalleryFlow).setScrollingEnabled(false);
    }

    private DemoImage[] createItems() {
        DemoImage[] items = new DemoImage[15];

        //#1
        items[0] = new DemoImage("Chessboard", "chessboard");
        //#2
        items[1] = new DemoImage("Earth", "earth");
        //#3
        items[2] = new DemoImage("Grapes", "grapes");
        //#4
        items[3] = new DemoImage("Lake", "lake");
        //#5
        items[4] = new DemoImage("Ford Mustang", "mustang");
        //#6
        items[5] = new DemoImage("Nebula", "nebula");
        //#7
        items[6] = new DemoImage("New York", "newyork");
        //#8
        items[7] = new DemoImage("Orange", "orange");
        //#9
        items[8] = new DemoImage("Red Kite", "redkite");
        //#10
        items[9] = new DemoImage("Rose", "rose");
        //#11
        items[10] = new DemoImage("Flying Seagull", "seagull");
        //#12
        items[11] = new DemoImage("Space Shuttle", "shuttle");
        //#13
        items[12] = new DemoImage("Smarties", "smarties");
        //#14
        items[13] = new DemoImage("Sun", "sun");
        //#15
        items[14] = new DemoImage("Tulips", "tulips");
        Arrays.sort(items);
        return items;
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
        switch (item.getItemId()) {
            case ID_MENU_CLEAR_CACHE:
                clearImageCache();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clearImageCache() {
        // clear downloaded images from the cache
        UrlImageCache cache = ((GalleryFlowViewInterface) mGalleryFlow).getImageCache();
        if (cache != null) {
            cache.clear();
        }
    }

    static class ImageAdapter extends BaseAdapter implements UrlImageAdapter {
        final Context mContext;
        final DemoImage[] mItems;

        public ImageAdapter(Context context, DemoImage[] items) {
            mContext = context;
            mItems = items;
        }

        public int getCount() {
            return mItems.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            View content;
            final ViewHolder holder;
            if (convertView == null) {
                content = LayoutInflater.from(mContext).inflate(R.layout.galleryflowtest_content, parent);

                TextView tv = (TextView) content.findViewById(R.id.title);
                ImageView iv = (ImageView) content.findViewById(R.id.image);

                holder = new ViewHolder();
                holder.title = tv;
                holder.image = iv;
                content.setTag(holder);

            } else {
                content = convertView;
                holder = (ViewHolder) convertView.getTag();
            }
            holder.title.setText(mItems[position].getTitle());
            holder.image.setImageResource(R.drawable.placeholder_port);
            return content;
        }

        @Override
        public void downloadUrlImages(int position, View itemView, Request request) {
            request.download(mItems[position].getImageUrl());
        }

        @Override
        public void onImageFail(int position, View itemView, String url, int refId, Exception exception) {

        }

        @Override
        public void onImageReady(int position, View itemView, String url, int refId, Bitmap bitmap) {
            ViewHolder holder = (ViewHolder) itemView.getTag();
            holder.image.setImageBitmap(bitmap);
        }

        @Override
        public void onWaitingForImage(int position, View itemView, String url, int refId) {

        }
    }

    static class DemoImage implements Comparable<DemoImage> {
        private String title;
        private String image;

        DemoImage(String title, String image) {
            this.title = title;
            this.image = image;
        }

        String getTitle() {
            return title;
        }

        String getImageUrl() {
            return String.format("http://www.droidux.com/images/droidux/apidemos_v2/port/%s.jpg", image);
        }

        @Override
        public int compareTo(DemoImage another) {
            return this.title.compareTo(another.title);
        }
    }

    static class ViewHolder {
        TextView title;
        ImageView image;
    }

    protected abstract int getFragmentLayoutId();

    class GalleryFlowFragment extends SherlockFragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            ViewGroup viewGroup = (ViewGroup) inflater.inflate(getFragmentLayoutId(), container, false);

            mGalleryFlow = (Gallery) viewGroup.findViewById(R.id.galleryflow);
            // set the clipChildren to false for animation without clipping
            mGalleryFlow.setClipChildren(false);
            final Gallery galleryFlow = mGalleryFlow;
            mImages = createItems();
            SpinnerAdapter adapter = createAdapter();
            galleryFlow.setAdapter(adapter);
            galleryFlow.setSelection(0, true);
            galleryFlow.setCallbackDuringFling(false);
            setGalleryFlowListeners();

            return viewGroup;

        }
    }
}
