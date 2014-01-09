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

package com.droidux.components.demo.abs.lyt;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;

import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.components.demo.abs.utils.Utilities;
import com.droidux.widget.editor.NoteView;

public class RoundedCornerScrollTest extends BaseTestActivity {
	@Override
	protected int getLayoutId() {
		return R.layout.activity_roundedcornerscrolltest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_lyt_rounded_scroll_desc;
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String lipsum = Utilities.getStringAsset(this, "lipsum_html.txt");
		Spanned html = Html.fromHtml(lipsum);

		NoteView nv = (NoteView)findViewById(R.id.nv);
		nv.setText(html);
		if(getResources().getBoolean(R.bool.isTablet)) {
			nv.append(html);
			nv.append(html);
			nv.append(html);
			nv.append(html);
			nv.append(html);
			nv.append(html);
			nv.append(html);
		}

	}

}
