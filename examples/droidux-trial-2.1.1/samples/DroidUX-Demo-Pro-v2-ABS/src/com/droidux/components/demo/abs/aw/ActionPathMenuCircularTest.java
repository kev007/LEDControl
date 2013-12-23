/*******************************************************************************
 * Copyright (C) 2011 Ximpl
 * All Rights Reserved. This program and the accompanying materials
 * are owned by Ximpl or its suppliers.  The program is protected by international copyright laws and treaty provisions.  Any violation will be prosecuted under applicable laws.
 * NOTICE: The following is Source Code and is subject to all
 * restrictions on such code as contained in the End User License Agreement accompanying this product.
 ******************************************************************************/
package com.droidux.components.demo.abs.aw;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.interfaces.ActionInterfaces.Item.ActionItem;
import com.droidux.widget.action.ActionPathMenu;

/**
 *
 */
public class ActionPathMenuCircularTest extends BaseTestActivity implements ActionItem.OnActionListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionPathMenu pathMenu = (ActionPathMenu) findViewById(R.id.pathMenu);
        final float density = getResources().getDisplayMetrics().density;
        pathMenu.beginCircularLayout(R.layout.actionpath_control, R.id.controlIndicator)
                .addAction(new ActionItem("Cloud").setIcon(R.drawable.actionpath_ic_cloud).setOnActionListener(this))
                .addAction(new ActionItem("Email").setIcon(R.drawable.actionpath_ic_email).setOnActionListener(this))
                .addAction(new ActionItem("Favorite").setIcon(R.drawable.actionpath_ic_favorite).setOnActionListener(this))
                .addAction(new ActionItem("Picture").setIcon(R.drawable.actionpath_ic_picture).setOnActionListener(this))
                .addAction(new ActionItem("Calendar").setIcon(R.drawable.actionpath_ic_today).setOnActionListener(this))
                .addAction(new ActionItem("Users").setIcon(R.drawable.actionpath_ic_users).setOnActionListener(this))
                .endLayout();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_actionpathmenutest;
    }

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_actnpth_circular_desc;
    }

    @Override
    public void onAction(View view, ActionItem actionItem) {
        Toast.makeText(this, "Action pressed: " + actionItem.getTitle(), Toast.LENGTH_SHORT).show();
    }
}