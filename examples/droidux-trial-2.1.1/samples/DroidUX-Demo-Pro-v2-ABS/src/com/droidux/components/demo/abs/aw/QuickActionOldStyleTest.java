/*******************************************************************************
 * Copyright (C) 2011 Ximpl
 * All Rights Reserved. This program and the accompanying materials
 * are owned by Ximpl or its suppliers.  The program is protected by international copyright laws and treaty provisions.  Any violation will be prosecuted under applicable laws.
 * NOTICE: The following is Source Code and is subject to all
 * restrictions on such code as contained in the End User License Agreement accompanying this product.
 ******************************************************************************/
package com.droidux.components.demo.abs.aw;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.interfaces.ActionInterfaces.Item.ActionItem;
import com.droidux.interfaces.ActionInterfaces.Item.ActionItem.OnActionListener;
import com.droidux.interfaces.ActionInterfaces.Window.ActionWindowInterface;
import com.droidux.widget.action.DropDownMenu;
import com.droidux.widget.action.QuickActionBar;
import com.droidux.widget.action.QuickActionGrid;

public class QuickActionOldStyleTest extends BaseTestActivity {
	private ActionWindowInterface mAW;

	private ActionItem mChart;
	private ActionItem mAppointment;
	private ActionItem mContact;
	private ActionItem mSearch;
	private ActionItem mChat;
	private ActionItem mShop;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_actionwindowtests;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mGD.setIsLongpressEnabled(true);

        setupActionItems();

		Button btnBar1 = (Button) this.findViewById(R.id.btnBar1);
		btnBar1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				QuickActionBar qa = new QuickActionBar(QuickActionOldStyleTest.this);
				qa.beginLayout()
					.addAction(mChart)
					.addAction(mAppointment)
					.endLayout();
				qa.setAnimationStyle(QuickActionBar.ANIM_AUTO);

				qa.show(v);
				mAW = qa;
			}
		});

		Button btnGrid1 = (Button) this.findViewById(R.id.btnGrid1);
		btnGrid1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				QuickActionGrid qa = new QuickActionGrid(QuickActionOldStyleTest.this);
				qa.beginLayout()
					.addAction(mChart)
					.addAction(mAppointment)
					.addAction(mContact)
					.addAction(mShop)
					.addAction(mChat)
					.addAction(mSearch)
					.endLayout();
				qa.setAnimationStyle(QuickActionGrid.ANIM_AUTO);
				qa.show(v);
				mAW = qa;
			}
		});
		Button btnMenu1 = (Button) this.findViewById(R.id.btnMenu1);
		btnMenu1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DropDownMenu dd = new DropDownMenu(QuickActionOldStyleTest.this);
				dd.beginLayout()
				.addAction(mChart)
				.addAction(mAppointment)
				.addAction(mContact)
				.endLayout();
				dd.setAnimationStyle(DropDownMenu.ANIM_AUTO);
				dd.setTitle("Menu Title");
				dd.show(v);
				mAW = dd;
			}
		});


		Button btnBar2 = (Button) this.findViewById(R.id.btnBar2);
		btnBar2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				QuickActionBar qa = new QuickActionBar(QuickActionOldStyleTest.this);

				qa.beginLayout()
					.addAction(mChart)
					.addAction(mAppointment)
					.addAction(mContact)
					.addAction(mShop)
					.addAction(mChat)
					.addAction(mSearch)
				.endLayout();

				qa.setAnimationStyle(QuickActionBar.ANIM_GROW_FROM_CENTER);
				qa.setTitle("Quick Actions");
				qa.show(v);
				mAW = qa;
			}
		});
		Button btnMenu2 = (Button) this.findViewById(R.id.btnMenu2);
		btnMenu2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DropDownMenu dd = new DropDownMenu(QuickActionOldStyleTest.this);
				dd.beginLayout()
				.addAction(mChart)
				.addAction(mAppointment)
				.addAction(mContact)
				.addAction(mShop)
				.addAction(mChat)
				.addAction(mSearch)
				.endLayout();

				dd.setAnimationStyle(DropDownMenu.ANIM_GROW_FROM_CENTER);
				dd.show(v);
				mAW = dd;
			}
		});
	}

    private void setupActionItems() {
        mChart = new ActionItem()
                .setTitle("Chart")
                .setIcon(getResources().getDrawable(R.drawable.ic_qact_chart))
                .setOnActionListener(mListener);


        mAppointment = new ActionItem()
                .setTitle("Appointment")
                .setIcon(getResources().getDrawable(R.drawable.ic_qact_calendar))
                .setOnActionListener(mListener);

        mContact = new ActionItem()
                .setTitle("Contact")
                .setIcon(getResources().getDrawable(R.drawable.ic_qact_contact))
                .setOnActionListener(mListener);

        mSearch = new ActionItem()
                .setTitle("Search")
                .setIcon(getResources().getDrawable(R.drawable.ic_qact_search))
                .setOnActionListener(mListener);

        mChat = new ActionItem("Chat",getResources().getDrawable(R.drawable.ic_qact_chat))
                .setOnActionListener(mListener);

        mShop = new ActionItem("Shopping List", getResources().getDrawable(R.drawable.ic_qact_shop)).setOnActionListener(mListener);
    }

    private OnActionListener mListener = new OnActionListener() {

		@Override
		public void onAction(View v, ActionItem action) {
			Toast.makeText(QuickActionOldStyleTest.this, String.format("%s selected", action.getTitle()), Toast.LENGTH_SHORT).show();
		}
	};

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_qact_oldstyle_desc;
    }

    @Override
	public boolean onTouchEvent(MotionEvent event) {
		return mGD.onTouchEvent(event);
	}
	@Override
	protected void onPause() {
		if(mAW != null) {
			// this is needed to avoid leaking the window (android.view.WindowsLeak problem)
			// on orientation changed
			mAW.dismiss();
		}
		super.onPause();
	}

	private final GestureDetector mGD = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {

		@Override
		public void onLongPress(MotionEvent ev) {
			QuickActionGrid qa = new QuickActionGrid(QuickActionOldStyleTest.this);
			qa.beginLayout()
			.addAction(mChart)
			.addAction(mAppointment)
			.addAction(mContact)
			.addAction(mShop)
			.addAction(mChat)
			.addAction(mSearch)
			.endLayout();

			qa.setAnimationStyle(QuickActionGrid.ANIM_DEFAULT);
			View anchor = findViewById(android.R.id.content).getRootView();
			qa.show(anchor, (int)ev.getRawX(), (int)ev.getRawY());
			mAW = qa;
		}

	});

}
