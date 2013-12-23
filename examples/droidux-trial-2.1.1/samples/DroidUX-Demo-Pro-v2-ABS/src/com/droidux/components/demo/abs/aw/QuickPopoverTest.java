/*******************************************************************************
 * Copyright (C) 2011 Ximpl
 * All Rights Reserved. This program and the accompanying materials
 * are owned by Ximpl or its suppliers.  The program is protected by international copyright laws and treaty provisions.  Any violation will be prosecuted under applicable laws.
 * NOTICE: The following is Source Code and is subject to all
 * restrictions on such code as contained in the End User License Agreement accompanying this product.
 ******************************************************************************/
package com.droidux.components.demo.abs.aw;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.interfaces.ActionInterfaces.Window.PopupWindowInterface;
import com.droidux.widget.ColorButton;
import com.droidux.widget.action.QuickPopover;
import com.droidux.widget.action.QuickTooltip;

public class QuickPopoverTest extends BaseTestActivity {
    private static final int TEXT_COLOR_LIGHT = 0xFF939393;
    private static final int TEXT_COLOR_DARK= 0xFF393939;
	private PopupWindowInterface mQpo;
	private TextView mText;
	private ImageView mImage;
	private View mCompound;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_quickpopovertest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_qact_popover_desc;
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = LayoutInflater.from(this);
		// text content
		mText = (TextView) inflater.inflate(R.layout.popover_text, null);

		// image content
		mImage= (ImageView) inflater.inflate(R.layout.popover_image, null);

		// other content
		mCompound = inflater.inflate(R.layout.popover_compound, null) ;

		// QuickPopover with default callout
		final QuickPopover qpo1 = new QuickPopover(this);
		// QuickPopover with custom callout
		final QuickPopover qpo2 = new QuickPopover(this);
		qpo2.setCalloutDrawables(R.drawable.callout_base,
			R.drawable.callout_leftarrow, R.drawable.callout_toparrow,
			R.drawable.callout_rightarrow, R.drawable.callout_bottomarrow,
			new Rect(14,14,14,14));


		// show quickpopovers
		// left-top
		Button btn = (Button) findViewById(R.id.btnLeftTop);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				qpo1.setContentView(mImage);
				qpo1.setAnimationStyle(QuickTooltip.ANIM_GROW_FROM_LEFT);
                qpo1.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());
				showPopover(qpo1, v, false);
			}
		});
		// top
		btn = (Button) findViewById(R.id.btnTop);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
                mText.setTextColor(TEXT_COLOR_DARK);
				qpo2.setContentView(mText);
				qpo2.setAnimationStyle(QuickTooltip.ANIM_GROW_FROM_CENTER);
                qpo2.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());

				showPopover(qpo2, v, false);
			}
		});
		// right-top
		btn = (Button) findViewById(R.id.btnRightTop);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
               ((TextView)mCompound.findViewById(R.id.text1)).setTextColor(TEXT_COLOR_LIGHT);
				qpo1.setContentView(mCompound);
                qpo1.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());
				qpo1.setAnimationStyle(QuickTooltip.ANIM_GROW_FROM_RIGHT);
				showPopover(qpo1, v, false);
			}
		});
		// left
		btn = (Button) findViewById(R.id.btnLeft);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
                ((TextView)mCompound.findViewById(R.id.text1)).setTextColor(TEXT_COLOR_DARK);
				qpo2.setContentView(mCompound);
				qpo2.setAnimationStyle(QuickTooltip.ANIM_FADE);
                qpo2.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());

				showPopover(qpo2, v, true);
			}
		});
		// right
		btn = (Button) findViewById(R.id.btnRight);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				qpo2.setContentView(mImage);
				qpo2.setAnimationStyle(QuickTooltip.ANIM_FADE);
                qpo2.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());
				showPopover(qpo2, v, true);
			}
		});

		// left-bottom
		btn = (Button) findViewById(R.id.btnLeftBottom);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
                ((TextView)mCompound.findViewById(R.id.text1)).setTextColor(TEXT_COLOR_LIGHT);
				qpo1.setContentView(mCompound);
                qpo1.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());
				qpo1.setAnimationStyle(QuickTooltip.ANIM_GROW_FROM_LEFT);
				showPopover(qpo1, v, false);
			}
		});
		// bottom
		btn = (Button) findViewById(R.id.btnBottom);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
                ((TextView)mCompound.findViewById(R.id.text1)).setTextColor(TEXT_COLOR_DARK);
				qpo2.setContentView(mCompound);
				qpo2.setAnimationStyle(QuickTooltip.ANIM_FADE);
                qpo2.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());

				showPopover(qpo2, v, false);
			}
		});
		// right-bottom
		btn = (Button) findViewById(R.id.btnRightBottom);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
                mText.setTextColor(TEXT_COLOR_LIGHT);
				qpo1.setContentView(mText);
                qpo1.setCalloutTintColor(((ColorButton) v).getTintColors().getDefaultColor());
				qpo1.setAnimationStyle(QuickTooltip.ANIM_GROW_FROM_RIGHT);
				showPopover(qpo1, v, false);
			}
		});

	}

	private void showPopover(QuickPopover qpo, View anchor, boolean horizArrow) {
		mQpo = qpo;
		qpo.show(anchor, horizArrow);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mQpo != null) {
			mQpo.dismiss();
			mQpo=null;
		}
	}

}
