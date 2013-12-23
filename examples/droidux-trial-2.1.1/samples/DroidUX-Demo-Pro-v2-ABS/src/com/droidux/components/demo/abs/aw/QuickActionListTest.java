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
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.interfaces.ActionInterfaces.Item.ActionItem;
import com.droidux.interfaces.ActionInterfaces.Item.ActionItem.OnActionListener;
import com.droidux.interfaces.ActionInterfaces.Window.PopupWindowInterface;
import com.droidux.widget.ColorButton;
import com.droidux.widget.action.QuickActionList;

public class QuickActionListTest extends BaseTestActivity implements OnActionListener {

	private PopupWindowInterface mQal;

	private ActionItem mChart;
	private ActionItem mAppointment;
	private ActionItem mContact;
	private ActionItem mSearch;
	private ActionItem mChat;
	private ActionItem mShop;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_quickactionlisttest;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initActionItems();

		// init quickactionlist
		final QuickActionList qal = new QuickActionList(this);
		// store for future reference
		mQal = qal;
		// setup layout
		qal.beginLayout()
			.addAction(mChart)
			.addAction(mAppointment)
			.addAction(mContact)
			.addAction(mShop)
			.addAction(mChat)
			.addAction(mSearch)
		.endLayout();

		// show quickactionlist
		// left-top
		ColorButton btn = (ColorButton) findViewById(R.id.btnLeftTop);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				qal.setTitle("LeftTop Menu");
				qal.setAnimationStyle(QuickActionList.ANIM_GROW_FROM_LEFT);
                qal.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());
				qal.show(v);
			}
		});
		// top
		btn = (ColorButton) findViewById(R.id.btnTop);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				qal.setTitle("Top Menu");
				qal.setAnimationStyle(QuickActionList.ANIM_GROW_FROM_CENTER);
                qal.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());
				qal.show(v);
			}
		});
		// right-top
		btn = (ColorButton) findViewById(R.id.btnRightTop);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				qal.setTitle(null);
				qal.setAnimationStyle(QuickActionList.ANIM_AUTO);
                qal.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());
				qal.show(v);
			}
		});
		//left-bottom
		btn = (ColorButton) findViewById(R.id.btnLeftBottom);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				qal.setTitle(null);
				qal.setAnimationStyle(QuickActionList.ANIM_AUTO);
                qal.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());
				qal.show(v);
			}
		});
		//bottom
		btn = (ColorButton) findViewById(R.id.btnBottom);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				qal.setTitle("Bottom Menu");
				qal.setAnimationStyle(QuickActionList.ANIM_GROW_FROM_CENTER);
                qal.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());
				qal.show(v);
			}
		});
		//right-bottom
		btn = (ColorButton) findViewById(R.id.btnRightBottom);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				qal.setTitle("RightBottom Menu");
				qal.setAnimationStyle(QuickActionList.ANIM_GROW_FROM_RIGHT);
                qal.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());
				qal.show(v);
			}
		});
		//left
		btn = (ColorButton) findViewById(R.id.btnLeft);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				qal.setTitle("Left Menu");
				qal.setAnimationStyle(QuickActionList.ANIM_FADE);
                qal.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());
				qal.show(v, true);
			}
		});
		//right
		btn = (ColorButton) findViewById(R.id.btnRight);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				qal.setTitle(null);
				qal.setAnimationStyle(QuickActionList.ANIM_FADE);
                qal.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());
                qal.show(v, true);
			}
		});
	}

	private void initActionItems() {
		mChart = new ActionItem()
		    .setTitle("Chart")
		    .setIcon(getResources().getDrawable(R.drawable.ic_qact_chart))
		    .setOnActionListener(this);


		mAppointment = new ActionItem()
		    .setTitle("Appointment")
		    .setIcon(getResources().getDrawable(R.drawable.ic_qact_calendar))
		    .setOnActionListener(this);

		mContact = new ActionItem()
		    .setTitle("Contact")
		    .setIcon(getResources().getDrawable(R.drawable.ic_qact_contact))
		    .setOnActionListener(this);

		mSearch = new ActionItem()
		    .setTitle("Search")
		    .setIcon(getResources().getDrawable(R.drawable.ic_qact_search))
		    .setOnActionListener(this);

		mChat = new ActionItem("Chat",getResources().getDrawable(R.drawable.ic_qact_chat))
		    .setOnActionListener(this);

		mShop = new ActionItem("Shopping List", getResources().getDrawable(R.drawable.ic_qact_shop)).setOnActionListener(this);

	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_qact_list_desc;
    }

    @Override
	public void onAction(View v, ActionItem action) {
		Toast.makeText(QuickActionListTest.this, String.format("%s selected", action.getTitle()), Toast.LENGTH_SHORT).show();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mQal != null) {
			mQal.dismiss();
			mQal=null;
		}
	}


}
