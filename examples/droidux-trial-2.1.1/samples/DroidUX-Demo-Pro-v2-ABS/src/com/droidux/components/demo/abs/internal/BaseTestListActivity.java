package com.droidux.components.demo.abs.internal;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.droidux.components.demo.abs.R;
import com.droidux.widget.action.QuickTooltip;


public abstract class BaseTestListActivity extends __SherlockDuxListActivity {
    private QuickTooltip mTooltip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(ActivityHelper.getActivityTitle(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.menu_help, menu);
        // help
        final int descriptionRes = getDescriptionRes();
        if (descriptionRes != 0) {
            MenuItem helpMenu = menu.findItem(R.id.menu_help);
            ImageView help = (ImageView)helpMenu.getActionView().findViewById(R.id.image);
            help.setImageResource(R.drawable.ic_action_help);
            help.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTooltip = ActivityHelper.setupDescriptionTooltip(BaseTestListActivity.this, mTooltip, descriptionRes);
                    mTooltip.show(v);
                }
            });
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTooltip != null) {
            mTooltip.dismiss();
            mTooltip=null;
        }
    }

    protected int getDescriptionRes() {
        return 0;
    }
}
