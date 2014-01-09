/*******************************************************************************
 * Copyright (C) 2011 Ximpl
 * All Rights Reserved. This program and the accompanying materials
 * are owned by Ximpl or its suppliers.  The program is protected by international copyright laws and treaty provisions.  Any violation will be prosecuted under applicable laws.
 * NOTICE: The following is Source Code and is subject to all
 * restrictions on such code as contained in the End User License Agreement accompanying this product.
 ******************************************************************************/
package com.droidux.components.demo.abs.aw;

import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.components.demo.abs.utils.Utilities;
import com.droidux.widget.action.ActionDrawer;
import com.droidux.widget.list.DuxAutoScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ActionDrawerListMenuTest extends BaseTestActivity implements ActionDrawer.Callback {
    private static final String STATE_ACTION_DRAWER = "com.droidux.components.demo.state_action_drawer";
    private static final String STATE_DRAWER_ITEM = "com.droidux.components.demo.state_drawer_item";
    ActionDrawer mDrawer;
    private DrawerItem mActiveDrawerItem=null;
    private List<DrawerItem> mItems;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actiondrawertest);

        Object nonConfigInstance=getLastNonConfigurationInstance();
        mItems = nonConfigInstance!=null ? (List<DrawerItem>)nonConfigInstance : createItems(20);

        mDrawer = new ActionDrawer.Builder(this, this)
                .setArrowBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.drawer_arrow_left))
                .setShadowDrawable(R.drawable.drawer_shadow_left)
                .build();
        mDrawer.setDrawerBackground(Utilities.resolveThemeAttribute(this, R.attr.drawerBackground));

        if (savedInstanceState != null) {
            int position = savedInstanceState.getInt(STATE_DRAWER_ITEM, -1);
            mActiveDrawerItem = position>=0 ? mItems.get(position):null;
        }

        showActiveDrawerItem();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_actiondrawertest;
    }

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_actn_drawer_listmenu_desc;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.toggleDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (mDrawer != null && mDrawer.isDrawerVisible()) {
            mDrawer.closeDrawer();
            return;
        }
        super.onBackPressed();
    }

    private void showActiveDrawerItem() {
        TextView tv = (TextView) findViewById(R.id.text1);
        if (mActiveDrawerItem != null) {
            tv.setVisibility(View.VISIBLE);
            tv.setText("Active Drawer Item: " + mActiveDrawerItem.getTitle());
        } else {
            tv.setVisibility(View.GONE);
        }

    }

    @Override
    public View onCreateDrawerView() {
        DuxAutoScrollListView listDrawer = new DuxAutoScrollListView(this);
        listDrawer.setCacheColorHint(0);
        listDrawer.setAdapter(new ListDrawerAdapter(mItems));
        listDrawer.setDivider(new ColorDrawable(0x80737373));
        listDrawer.setDividerHeight(1);
        listDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mActiveDrawerItem = mItems.get(position);
                showActiveDrawerItem();
                mDrawer.setArrowAnchor(view);
                mDrawer.closeDrawer();
            }
        });
        listDrawer.setOnScrollChangedListener(new DuxAutoScrollListView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                mDrawer.getContainer().invalidate();
            }
        });
        return listDrawer;
    }

    private List<DrawerItem> createItems(int count) {
        List<DrawerItem> items = new ArrayList<DrawerItem>();
        int[] icons = new int[] {R.drawable.ic_rating_favorite, R.drawable.ic_collections_cloud,
                R.drawable.ic_collections_go_to_today, R.drawable.ic_content_picture};
        int currentSection=0, currentItem=0;
        DrawerItem di;
        int itemCount=0;
        for (int i = 0; i < count; i++) {
            int type = (int) (Math.random()*System.currentTimeMillis() % 2);
            if (type == 0 && itemCount>2 && i!=count-1) {
                di = new Section("Section " + ++currentSection);
                itemCount=0;
            } else {
                di = new Item("Item " + ++currentItem, icons[currentItem % icons.length]);
                itemCount++;
            }
            items.add(di);
        }
        return items;
    }

    @Override
    public boolean onPrepareDrawerView(View view) {
        if (mActiveDrawerItem != null) {
            DuxAutoScrollListView listDrawer = (DuxAutoScrollListView)view;
            int position = mItems.indexOf(mActiveDrawerItem);
            listDrawer.requestPositionToScreen(position, false);
            View active = listDrawer.getChildAt(position - listDrawer.getFirstVisiblePosition());
            if (active != null) {
                mDrawer.setArrowAnchor(active);
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_DRAWER_ITEM, mItems.indexOf(mActiveDrawerItem));
        outState.putParcelable(STATE_ACTION_DRAWER, mDrawer.onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mDrawer.onRestoreInstanceState(savedInstanceState.getParcelable(STATE_ACTION_DRAWER));
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        return mItems;
    }

    private interface DrawerItem {
        String getTitle();
        int getIcon();
    }
    private static class Section implements DrawerItem{
        String title;

        Section(String title) {
            this.title = title;
        }

        @Override
        public String getTitle() {
            return this.title;
        }

        @Override
        public int getIcon() {
            return 0;
        }
    }
    private static class Item implements DrawerItem {
        String title;
        int icon;

        Item(String title, int icon) {
            this.title=title;
            this.icon = icon;
        }

        @Override
        public String getTitle() {
            return this.title;
        }

        @Override
        public int getIcon() {
            return this.icon;
        }
    }
    private class ListDrawerAdapter extends BaseAdapter {
        private final List<DrawerItem> items;
        public ListDrawerAdapter(List<DrawerItem> items) {
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return getItem(position) instanceof Section ? 0:1;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public boolean isEnabled(int position) {
            return getItem(position) instanceof Item;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv= (TextView) convertView;
            DrawerItem di = (DrawerItem) getItem(position);
            if (tv == null) {
                if (di instanceof Section) {
                    tv = (TextView) getLayoutInflater().inflate(R.layout.drawer_section, parent, false);
                } else {
                    tv = (TextView) getLayoutInflater().inflate(R.layout.drawer_item, parent, false);
                }
            } else {
                // if we're reusing the current activeDrawerItem's view for another position,
                // remove the arrow anchor.
                DrawerItem tag = (DrawerItem) tv.getTag();
                if (tag == mActiveDrawerItem) {
                    mDrawer.setArrowAnchor(null);
                }
            }

            tv.setTag(di);
            tv.setText(di.getTitle());
            tv.setCompoundDrawablesWithIntrinsicBounds(di.getIcon(),0,0,0);

            if (mActiveDrawerItem == di) {
                mDrawer.setArrowAnchor(tv);
            }

            return tv;

        }
    }
}