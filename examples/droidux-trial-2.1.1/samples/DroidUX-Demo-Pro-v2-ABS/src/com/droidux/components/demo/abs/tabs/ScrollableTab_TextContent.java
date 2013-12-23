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

package com.droidux.components.demo.abs.tabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;


/**
 * A list view example where the
 * data for the list comes from an array of strings.
 */
public class ScrollableTab_TextContent extends Activity {
	private final static String EXTRA_NUMBER = "extra.number";
	private TextView mContent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createTextView(), new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        int number = getIntent().getIntExtra(EXTRA_NUMBER, 0);
        number %= 3;

        String str = mStrings[number];
        mContent.setText(str);
    }

    private View createTextView() {
    	TextView content = mContent = new TextView(this);
    	content.setGravity(Gravity.CENTER);
    	content.setTextSize(12);
    	content.setTextColor(Color.BLACK);
    	content.setTypeface(Typeface.DEFAULT_BOLD);
    	return content;
	}

	static Intent getTextIntent(Context context, int number) {
    	Intent intent = new Intent(context, ScrollableTab_TextContent.class);
    	intent.putExtra(EXTRA_NUMBER, number);
    	return intent;
    }
    private String[] mStrings = {
    		"Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
    		"Aliquam accumsan condimentum nisl, at hendrerit dolor sagittis ut. " +
    		"Donec convallis adipiscing consequat. ",
    		"Sed gravida arcu pulvinar metus pellentesque porta. " +
    		"Proin nisi leo, pulvinar quis mattis eget, suscipit quis urna. " +
    		"Ut volutpat lobortis aliquet.",
    		"Nullam posuere mauris a nibh vehicula nec interdum dui blandit. " +
    		"In risus eros, molestie sit amet aliquam consectetur, laoreet quis dui."
    };
}
