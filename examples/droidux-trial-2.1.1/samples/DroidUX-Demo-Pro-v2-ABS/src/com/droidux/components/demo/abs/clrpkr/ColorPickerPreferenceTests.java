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
package com.droidux.components.demo.abs.clrpkr;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;

import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestPreferenceActivity;
import com.droidux.components.demo.abs.utils.Utilities;

public class ColorPickerPreferenceTests extends BaseTestPreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.prefs_colorpickertests);
        findPreference(getString(R.string.color4_key)).setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary(Utilities.colorToHex((Integer) newValue));
                return true;
            }

        });
    }

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_clrpkr_preference_desc;
    }
}