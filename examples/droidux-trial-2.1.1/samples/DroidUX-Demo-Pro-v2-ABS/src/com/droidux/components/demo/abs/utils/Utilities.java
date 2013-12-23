/*******************************************************************************
 * Copyright (C) 2011 Ximpl
 * All Rights Reserved. This program and the accompanying materials
 * are owned by Ximpl or its suppliers.  The program is protected by international copyright laws and treaty provisions.  Any violation will be prosecuted under applicable laws.
 * NOTICE: The following is Source Code and is subject to all
 * restrictions on such code as contained in the End User License Agreement accompanying this product.
 ******************************************************************************/
package com.droidux.components.demo.abs.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.TypedValue;

import java.io.IOException;
import java.io.InputStream;

public class Utilities {
	public static String getStringAsset(final Context context, String assetName) {
		String assetsText = null;
        try {
            InputStream is = context.getAssets().open(assetName);

            // We guarantee that the available method returns the total
            // size of the asset...  of course, this does mean that a single
            // asset can't be more than 2 gigs.
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            assetsText = new String(buffer);
        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }
		return assetsText;
	}

	public static String colorToHex(int color) {
		return ("#" + Integer.toHexString(color).toUpperCase());
	}
	public static int calcMixColor(int color1, int color2, double ratio) {
		int r = (int)(Color.red(color1)*(1-ratio) + Color.red(color2)*ratio);
		int g = (int)(Color.green(color1)*(1-ratio) + Color.green(color2)*ratio);
		int b = (int)(Color.blue(color1)*(1-ratio) + Color.blue(color2)*ratio);
		int a = (int)(Color.alpha(color1)*(1-ratio) + Color.alpha(color2)*ratio);
		return Color.argb(a, r, g, b);

	}
    public static double restrict(double amount, double low, double high) {
        return amount < low ? low : (amount > high ? high : amount);
    }

    public static int resolveThemeAttribute(Context context, int attr) {
        Resources.Theme theme = context.getTheme();
        int resolved = 0;
        TypedValue tv = new TypedValue();
        if (theme.resolveAttribute(attr, tv, true)) {
            resolved = tv.resourceId;
        }
        return resolved;

    }

    public static int constrain(int amount, int low, int high) {
        return amount < low ? low : (amount > high ? high : amount);
    }
    public static float constrain(float amount, float low, float high) {
        return amount < low ? low : (amount > high ? high : amount);
    }


}
