package com.droidux.tutorials;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.droidux.tutorials.fragments.SimpleContentFragment;
import com.droidux.tutorials.utils.MathUtils;
import com.droidux.widget.action.ActionDrawer;
import com.droidux.widget.list.DuxAutoScrollListView;

// We'll extend from the previous tutorial.
// In this tutorial we'll show you how to use a list menu in the left drawer.
// [Note: Google has recently introduced a new design guideline for the navigation drawer. Towards the end
// of this tutorial, we'll show you how to customize the ActionDrawer to follow the new guideline]
public class ListActionDrawerActivity extends BaseSingleActionDrawerActivity implements ViewTreeObserver.OnGlobalLayoutListener {

    private static final String SELECTED_DRAWER_ITEM = "com.droidux.tutorials.selected_drawer_item";
    private String[] mColorMenu;
    private DuxAutoScrollListView mDrawerList;
    private int mSelectedDrawerItem;
    private final Rect mContentRect = new Rect();
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTitle = getTitle();
        mDrawer.getContainer().getViewTreeObserver().addOnGlobalLayoutListener(this);

        // Add a drawing listener to the drawer to apply effects while the drawer is drawn.
        mDrawer.setOnDrawingListener(new ActionDrawer.OnDrawingListener() {
            @Override
            public boolean onDrawerDrawing(Canvas canvas, float v) {
                // Here we can add effects to the drawer while it's sliding, e.g. scaling/zooming the drawer, etc,
                // by applying canvas transformation.
                // We can also use this listener to apply effects to other part of the screen while the drawer
                // is sliding, e.g. to the action bar to animate the home icon, etc.
                return false;
            }

            @Override
            public boolean onContentDrawing(Canvas canvas, float v) {
                // Here we can add effects to the content view while the drawer is sliding.
                return false;
            }

            @Override
            public boolean onArrowDrawing(Canvas canvas, float v, Rect rect, Paint paint) {
                // Here we'll hide the arrow when it pass beyond the content's rect.
                boolean transformed = false;
                float arrowTop = rect.top;
                float arrowBottom = rect.bottom;
                float contentTop = mContentRect.top;
                float contentBottom = mContentRect.bottom;

                if (arrowTop < contentTop) {
                    float ratio = MathUtils.constrain(1f - ((contentTop - arrowTop) / rect.height()), 0f, 1f);
                    canvas.scale(ratio, ratio, rect.right, arrowBottom);
                    paint.setAlpha((int) (ratio * 255));
                    transformed = true;
                } else if (arrowBottom > contentBottom) {
                    float ratio = MathUtils.constrain(1f - ((arrowBottom - contentBottom) / rect.height()), 0f, 1f);
                    canvas.scale(ratio, ratio, rect.right,arrowTop);
                    paint.setAlpha((int) (ratio * 255));
                    transformed=true;
                }
                return transformed;
            }


        });

        mDrawer.setOnDrawerStateChangeListener(new ActionDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                switch (newState) {
                    case ActionDrawer.STATE_DRAWER_CLOSED:
                        getActionBar().setTitle(mTitle);
                        invalidateOptionsMenu();
                        break;
                    case ActionDrawer.STATE_DRAWER_OPEN:
                        getActionBar().setTitle(R.string.text_drawerisopen);
                        invalidateOptionsMenu();
                        break;
                }
            }
        });

        // Show the initial menu selection
        mSelectedDrawerItem = (savedInstanceState == null) ? 3 : savedInstanceState.getInt(SELECTED_DRAWER_ITEM, 3);
        selectDrawerItem(mSelectedDrawerItem);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerVisible = mDrawer.isDrawerVisible();
        menu.findItem(R.id.action_search).setVisible(!drawerVisible);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public View onCreateDrawerView() {
        // Here we create our list menu.

        // Get our menu
        mColorMenu = getResources().getStringArray(R.array.menu_colors);

        // We'll use the DuxAutoScrollListView here, but you can also use any standard or custom ListView widget.
        mDrawerList = (DuxAutoScrollListView) getLayoutInflater().inflate(R.layout.list_drawer, null);
        mDrawerList.setAdapter(new ListDrawerAdapter(this, R.layout.drawer_list_item, mColorMenu));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectDrawerItem(position);
                // Point the arrow to the selected item for decoration
                mDrawer.setArrowAnchor(view);
                mDrawer.closeDrawer();
            }
        });

        mDrawerList.setOnScrollChangedListener(new DuxAutoScrollListView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                // Invalidate the drawer container to redraw the arro while the list is scrolled.
                mDrawer.getContainer().invalidate();
            }
        });

        // return our list menu
        return mDrawerList;

    }

    @Override
    public boolean onPrepareDrawerView(View arg0) {
        // We want to make sure the selected item is displayed on the screen whenever the drawer is opened.
        mDrawerList.requestPositionToScreen(mSelectedDrawerItem, false);

        // Point an arrow to the selected drawer item for decoration
        View selected = mDrawerList.getChildAt(mSelectedDrawerItem - mDrawerList.getFirstVisiblePosition());
        if (selected != null) {
            mDrawer.setArrowAnchor(selected);
        }

        return true;
    }

    private void selectDrawerItem(int position) {
        // We'll show the content for the selected item using fragment
        Fragment fragment = new SimpleContentFragment();
        Bundle args = new Bundle();
        args.putString(SimpleContentFragment.ARG_COLOR_HEX, mColorMenu[position]);
        fragment.setArguments(args);

        // replace the content fragment
        getFragmentManager().beginTransaction().replace(R.id.fragment_content, fragment).commit();

        mDrawerList.setItemChecked(position, true);

        // save the selected item position
        mSelectedDrawerItem = position;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_DRAWER_ITEM, mSelectedDrawerItem);
    }

    @Override
    public void onGlobalLayout() {
        // We want to hide the arrow if it pass beyond the content view, e.g. on the action bar area or
        // below the bottom of the screen.
        // Let's calculate the content area
        View content = mDrawer.getContainer().findViewById(android.R.id.content);
        if (content != null) {
            // get the content's visible rect
            content.getGlobalVisibleRect(mContentRect);
        }

        getActionBar().setTitle(mDrawer.isDrawerVisible()?getString(R.string.text_drawerisopen):mTitle);

        mDrawer.getContainer().getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    private class ListDrawerAdapter extends ArrayAdapter<String> {
        public ListDrawerAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv;
            if (convertView == null) {
                tv = (TextView) super.getView(position, convertView, parent);
                // create icon
                ShapeDrawable icon = new ShapeDrawable(new OvalShape());
                icon.setIntrinsicHeight(48);
                icon.setIntrinsicWidth(48);
                icon.setBounds(0, 0, 48, 48);
                tv.setCompoundDrawables(icon, null, null, null);

            } else {
                tv = (TextView)convertView;

                // If we are reusing the selected item's view for another position,
                // don't point the arrow to this view
                Object tag = tv.getTag();
                if (tag != null) {
                    mDrawer.setArrowAnchor(null);
                    tv.setTag(null);
                }
            }

            // if the position is currently selected, point arrow to it
            if (position == mSelectedDrawerItem) {
                mDrawer.setArrowAnchor(tv);
                // tag the selected view
                tv.setTag(new Object());
            }

            ShapeDrawable icon = (ShapeDrawable) tv.getCompoundDrawables()[0];
            icon.getPaint().setColor(Color.parseColor(getItem(position)));
            tv.setText("Color " + position);

            return tv;
        }
    }
}
