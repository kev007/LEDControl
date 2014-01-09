/*******************************************************************************
 * Copyright (C) 2011 Ximpl
 * All Rights Reserved. This program and the accompanying materials
 * are owned by Ximpl or its suppliers.  The program is protected by international copyright laws and treaty provisions.  Any violation will be prosecuted under applicable laws.
 * NOTICE: The following is Source Code and is subject to all
 * restrictions on such code as contained in the End User License Agreement accompanying this product.
 ******************************************************************************/
package com.droidux.components.demo.abs;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.droidux.components.demo.abs.internal.ActivityHelper;
import com.droidux.components.demo.abs.internal.DroidUXDemosBrowser;
import com.droidux.widget.action.QuickTooltip;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DroidUXDemos extends SherlockListActivity {
	static final String EXTRA_PATH = "com.droidux.components.demo.Path";
    private static final String INTENT_CATEGORY = "com.droidux.intent.category.API_DEMOS";
    private QuickTooltip mInfoTooltip;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String path = intent.getStringExtra(EXTRA_PATH);

        if (path == null) {
            path = "";
        }

        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setTitle(ActivityHelper.getActivityTitle(this, path));

        setListAdapter(new SimpleAdapter(this, getData(path),
               android.R.layout.simple_list_item_1, new String[] { "title" },
                new int[] { android.R.id.text1 }));
        getListView().setTextFilterEnabled(true);
        getListView().setCacheColorHint(0);
        getListView().setBackgroundDrawable(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.menu_info, menu);
        ImageView info = (ImageView) menu.findItem(R.id.menu_info).getActionView().findViewById(R.id.image);
        info.setImageResource(R.drawable.ic_action_about);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInfoTooltip = ActivityHelper.setupInfoTooltip(DroidUXDemos.this, mInfoTooltip);
                mInfoTooltip.show(v);
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return ActivityHelper.goHome(this);
        }
        return super.onOptionsItemSelected(item);
    }

    protected List getData(String prefix) {
        List<Map> myData = new ArrayList<Map>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(INTENT_CATEGORY);
        mainIntent.setPackage(getPackageName());

        PackageManager pm = getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

        if (null == list)
            return myData;

        String[] prefixPath;

        if (prefix.equals("")) {
            prefixPath = null;
        } else {
            prefixPath = prefix.split("/");
        }

        int len = list.size();

        Map<String, Boolean> entries = new HashMap<String, Boolean>();

        for (int i = 0; i < len; i++) {
            ResolveInfo info = list.get(i);
            CharSequence labelSeq = info.loadLabel(pm);
            String label = labelSeq != null
                    ? labelSeq.toString()
                    : info.activityInfo.name;

            if (prefix.length() == 0 || label.startsWith(prefix)) {

                String[] labelPath = label.split("/");

                String nextLabel = prefixPath == null ? labelPath[0] : labelPath[prefixPath.length];

                if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
                    addItem(myData, nextLabel, activityIntent(
                            info.activityInfo.applicationInfo.packageName,
                            info.activityInfo.name));
                } else {
                    if (entries.get(nextLabel) == null) {
                        addItem(myData, nextLabel, browseIntent(prefix.equals("") ? nextLabel : prefix + "/" + nextLabel));
                        entries.put(nextLabel, true);
                    }
                }
            }
        }

        Collections.sort(myData, sDisplayNameComparator);

        return myData;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mInfoTooltip != null) {
            mInfoTooltip.dismiss();
            mInfoTooltip = null;
        }
    }

    private final static Comparator<Map> sDisplayNameComparator = new Comparator<Map>() {
        private final Collator   collator = Collator.getInstance();

        public int compare(Map map1, Map map2) {
            return collator.compare(map1.get("title"), map2.get("title"));
        }
    };

    private Intent activityIntent(String pkg, String componentName) {
        Intent result = new Intent();
        result.setClassName(pkg, componentName);
        return result;
    }

    private Intent browseIntent(String path) {
        Intent result = new Intent();
        if(TextUtils.isEmpty(path)) {
            result.setClass(this, DroidUXDemos.class);
        } else {
            result.setClass(this, DroidUXDemosBrowser.class);
            result.putExtra(EXTRA_PATH, path);
        }
        return result;
    }

    private void addItem(List<Map> data, String name, Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("intent", intent);
        data.add(temp);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map map = (Map) l.getItemAtPosition(position);

        Intent intent = (Intent) map.get("intent");
        startActivity(intent);
    }
}
