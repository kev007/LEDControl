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
package com.droidux.components.demo.abs.edtr;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.widget.editor.MultiAutoCompleteNoteEditor;

public class MultiAutoCompleteNoteTest extends BaseTestActivity {
	@Override
	protected int getLayoutId() {
		return R.layout.activity_multiautocompletenotetest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_edtr_multiautocomplete_desc;
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, AutoCompleteNoteTest.COUNTRIES);
        MultiAutoCompleteNoteEditor textView = (MultiAutoCompleteNoteEditor) findViewById(R.id.edit);
        textView.setAdapter(adapter);
        textView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
	}
}
