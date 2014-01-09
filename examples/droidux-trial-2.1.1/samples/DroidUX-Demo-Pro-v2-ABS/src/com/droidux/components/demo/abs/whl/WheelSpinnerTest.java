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
package com.droidux.components.demo.abs.whl;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.interfaces.ColorPickerInterfaces.Listeners.OnColorDialogListener;
import com.droidux.interfaces.WheelInterfaces.Adapters.BaseWheelAdapter;
import com.droidux.interfaces.WheelInterfaces.Adapters.WheelAdapterInterface;
import com.droidux.interfaces.WheelInterfaces.Listeners.OnWheelItemSelectedListener;
import com.droidux.interfaces.WheelInterfaces.Views.WheelSpinnerInterface;
import com.droidux.interfaces.WheelInterfaces.Views.WheelViewInterface;
import com.droidux.widget.ColorButton;
import com.droidux.widget.color.ColorPickerAlertDialog;
import com.droidux.widget.wheel.WheelSpinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class WheelSpinnerTest extends BaseTestActivity {
    private TextView mMessage;
    private final Random mRnd = new Random();
    private Country[] mCountries;
	private ColorPickerAlertDialog mColorPicker;
	private ColorButton mFaceColorButton;
	private ColorButton mFrameColorButton;
	private WheelSpinner mWheel;

    @Override
    protected int getLayoutId() {
    	return R.layout.activity_wheelspinnertests;
    }

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_whl_spinner_desc;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMessage = (TextView)findViewById(R.id.message);

        mFaceColorButton = (ColorButton) findViewById(R.id.faceColorButton);
        mFrameColorButton = (ColorButton) findViewById(R.id.frameColorButton);
        final WheelSpinner wheel1 = mWheel =  (WheelSpinner) findViewById(R.id.wheel1);
        mCountries = createCountries();

        // labels
        ImageView iv = (ImageView) wheel1.getLeftStaticView();
        iv.setImageResource(R.drawable.ic_whl_lbl_droid);
        iv.setPadding(3, 0, 5, 0);
        TextView tv = (TextView) wheel1.getRightStaticView();
        tv.setText("Country");
        wheel1.setCallbackDuringFling(true);
        wheel1.setOnWheelItemSelectedListener(new OnWheelItemSelectedListener() {
			@Override
			public void onItemSelected(WheelViewInterface wheel, View selected, int position, long id) {
				mMessage.setText(mCountries[position].name);
			}
		});
        wheel1.setInterpolator(new AnticipateOvershootInterpolator());
        wheel1.setAdapter(new CountryAdapter(this, mCountries));
        wheel1.setSelection(mRnd.nextInt(wheel1.getAdapter().getCount()));
        final CheckBox chk = (CheckBox) findViewById(R.id.chkCyclical);
        ((CountryAdapter)wheel1.getAdapter()).setCyclical(chk.isChecked());
        chk.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				CountryAdapter adapter = (CountryAdapter) wheel1.getAdapter();
				adapter.setCyclical(isChecked);
				adapter.notifyDataSetChanged();
			}
		});

    }

    public void doRandomSpin(View sender) {
    	WheelSpinnerInterface wheel = (WheelSpinnerInterface) findViewById(R.id.wheel1);
    	WheelAdapterInterface adapter = wheel.getAdapter();
    	int count = adapter.getCount();
    	boolean isCyclic = adapter.isCyclical();
    	int position = mRnd.nextInt(100);
    	if(!isCyclic) {
    		position %= count;
    	}
    	int oldPos = wheel.getSelection();
    	position = ((oldPos-position)==0)?(position+1)%count :position;
    	wheel.setSelection(position, 2000);
    }
    private Country[] createCountries() {
    	ArrayList<Country> countries = new ArrayList<Country>();
    	countries.add(new Country("Argentina", R.drawable.flag_argentina));
    	countries.add(new Country("Australia", R.drawable.flag_australia));
    	countries.add(new Country("Canada", R.drawable.flag_canada));
    	countries.add(new Country("U.S.A.", R.drawable.flag_usa));
    	countries.add(new Country("Hong Kong", R.drawable.flag_hong_kong));
    	countries.add(new Country("Israel", R.drawable.flag_israel));
    	countries.add(new Country("Indonesia", R.drawable.flag_indonesia));
    	countries.add(new Country("Japan", R.drawable.flag_japan));
    	countries.add(new Country("Netherlands", R.drawable.flag_netherlands));
    	countries.add(new Country("South Africa", R.drawable.flag_south_africa));
    	countries.add(new Country("South Korea", R.drawable.flag_south_korea));
    	countries.add(new Country("Russia", R.drawable.flag_russia));
    	countries.add(new Country("China", R.drawable.flag_china));
    	countries.add(new Country("Brazil", R.drawable.flag_brazil));
    	countries.add(new Country("Germany", R.drawable.flag_germany));
    	countries.add(new Country("India", R.drawable.flag_india));
    	countries.add(new Country("France", R.drawable.flag_france));
    	countries.add(new Country("Italy", R.drawable.flag_italy));
    	countries.add(new Country("U.K.", R.drawable.flag_great_britain));

    	Collections.sort(countries);
    	Country[] acountries = new Country[countries.size()];
    	countries.toArray(acountries);
    	return acountries;

    }
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mColorPicker != null) {
			mColorPicker.dismiss();
			mColorPicker = null;
		}
	}

	public void changeColors(View sender) {
		if(!(sender instanceof ColorButton)) {
			return;
		}
		final ColorButton button = (ColorButton) sender;
		int initialColor = (button==mFaceColorButton)?mWheel.getFaceColor():mWheel.getFrameColor();
		ColorPickerAlertDialog dlg = mColorPicker = new ColorPickerAlertDialog(this, initialColor,
			new OnColorDialogListener() {

				@Override
				public void onColorSelected(DialogInterface dialog, int color) {
					button.setTintColor(color);
					if(button == mFaceColorButton) {
						mWheel.setFaceColor(color);
					} else if (button == mFrameColorButton) {
						mWheel.setFrameColor(color);
					}
				}

				@Override
				public void onColorChanged(DialogInterface dialog, int color) {
					// nothing

				}
			});
		dlg.showAlphaPanel(false);
		dlg.show();
	}

    private static class CountryAdapter extends BaseWheelAdapter {
    	private final Context mContext;
    	private final Country[] mCountries;
    	private boolean mIsCyclical;

    	public CountryAdapter(Context context, Country[] countries) {
    		mContext = context;
    		mCountries = countries;
    	}
		@Override
		public int getCount() {

			return mCountries.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView v;
			if(convertView == null) {
				v = new TextView(mContext);
			} else {
				v = (TextView)convertView;
			}
			v.setText(mCountries[position].name);
			v.setTextSize(18);
			v.setTextColor(0xd2000001);
			v.setTypeface(Typeface.DEFAULT_BOLD);
			v.setPadding(5, 2, 0, 2);
			v.setCompoundDrawablePadding(5);
			Drawable flag = mContext.getResources().getDrawable(mCountries[position].flag);
			v.setCompoundDrawablesWithIntrinsicBounds(flag, null, null, null);
			return v;
		}
		@Override
		public boolean isCyclical() {
			return mIsCyclical;
		}
		public void setCyclical(boolean cyclical) {
			mIsCyclical= cyclical;
		}

    }
    private static class Country implements Comparable<Country> {
    	String name;
    	int flag;
    	Country(String name, int flag) {
    		this.name=name;
    		this.flag = flag;
    	}
		@Override
		public int compareTo(Country another) {
			return this.name.compareTo(another.name);
		}
    }
}
