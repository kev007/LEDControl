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
package com.droidux.components.demo.abs.aw;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.interfaces.ActionInterfaces.Window.PopupWindowInterface;
import com.droidux.widget.ColorButton;
import com.droidux.widget.action.QuickTooltip;

public class QuickTooltipTest extends BaseTestActivity {

	private PopupWindowInterface mQtt;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_quicktooltiptest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_qact_tooltip_desc;
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// init quicktooltip
		final QuickTooltip qtt = new QuickTooltip(this);
		// store for future reference
		mQtt = qtt;

		final Drawable image = getResources().getDrawable(R.drawable.ic_launcher);

		// show quickactionlist
		// left-top
		Button btn = (Button) findViewById(R.id.btnLeftTop);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				qtt.setTips(image, "Tooltip with image", DESCRIPTION);
				qtt.setAnimationStyle(QuickTooltip.ANIM_GROW_FROM_LEFT);
                qtt.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());
				qtt.show(v);
			}
		});
		// top
		btn = (Button) findViewById(R.id.btnTop);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				qtt.setTips("Tooltips without image", DESCRIPTION);
				qtt.setAnimationStyle(QuickTooltip.ANIM_GROW_FROM_CENTER);
                qtt.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());
				qtt.show(v);
			}
		});
		// right-top
		btn = (Button) findViewById(R.id.btnRightTop);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				qtt.setTips(image, "This tooltip has image.", DESCRIPTION);
				qtt.setAnimationStyle(QuickTooltip.ANIM_AUTO);
                qtt.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());
				qtt.show(v);
			}
		});
		//left-bottom
		btn = (Button) findViewById(R.id.btnLeftBottom);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				qtt.setTips(R.drawable.ic_launcher, R.string.text_tooltip_withimage, R.string.text_loremipsum);
				qtt.setAnimationStyle(QuickTooltip.ANIM_AUTO);
                qtt.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());
				qtt.show(v);
			}
		});
		//bottom
		btn = (Button) findViewById(R.id.btnBottom);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				qtt.setTips(R.string.text_tooltip_noimage, R.string.text_loremipsum);
				qtt.setAnimationStyle(QuickTooltip.ANIM_GROW_FROM_CENTER);
                qtt.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());
				qtt.show(v);
			}
		});
		//right-bottom
		btn = (Button) findViewById(R.id.btnRightBottom);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				qtt.setTips(R.drawable.ic_launcher, R.string.text_tooltip_withimage, R.string.text_loremipsum);
				qtt.setAnimationStyle(QuickTooltip.ANIM_GROW_FROM_RIGHT);
                qtt.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());
				qtt.show(v);
			}
		});
		//left
		btn = (Button) findViewById(R.id.btnLeft);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				qtt.setTips(image, "This tooltip has image", DESCRIPTION);
				qtt.setAnimationStyle(QuickTooltip.ANIM_FADE);
                qtt.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());
				qtt.show(v, true);
			}
		});
		//right
		btn = (Button) findViewById(R.id.btnRight);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				qtt.setTips(R.string.text_tooltip_noimage, R.string.text_loremipsum);
				qtt.setAnimationStyle(QuickTooltip.ANIM_FADE);
                qtt.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());
				qtt.show(v, true);
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mQtt != null) {
			mQtt.dismiss();
			mQtt=null;
		}
	}

	private static final String DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
			"Integer feugiat mollis nibh vitae tincidunt. Nulla vel nibh dui. " +
			"Nulla a nunc sed nulla porta porttitor vitae et augue. Aliquam erat volutpat.";

}
