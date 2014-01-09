/*******************************************************************************
 * Copyright (C) 2011 Ximpl
 * All Rights Reserved. This program and the accompanying materials
 * are owned by Ximpl or its suppliers.  The program is protected by international copyright laws and treaty provisions.  Any violation will be prosecuted under applicable laws.
 * NOTICE: The following is Source Code and is subject to all
 * restrictions on such code as contained in the End User License Agreement accompanying this product.
 ******************************************************************************/
package com.droidux.components.demo.abs.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;

import com.droidux.components.demo.abs.DroidUXDemos;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.utils.Utilities;
import com.droidux.widget.action.QuickTooltip;

public class ActivityHelper {
	public static String getActivityTitle(final Activity context) {
		return getActivityTitle(context, getActivityLabel(context));
	}
	public static String getActivityTitle(final Activity context, final String path) {
        String[] labels = path.split("/");
        String title = labels[labels.length-1];
        if (TextUtils.isEmpty(title)) {
            title = getActivityLabel(context);
        }
        return title;
	}

    public static boolean goHome(final Activity context) {
        Intent intent = new Intent(context, DroidUXDemos.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        context.finish();
        return true;
    }

    private static String getActivityLabel(Activity context) {
		if(context == null ) {
			return null;
		}
		String label = null;
		try {
			PackageManager pm = context.getPackageManager();
			ActivityInfo info = context.getPackageManager().getActivityInfo(context.getComponentName(), 0);
            int labelRes = info.labelRes;
            if (labelRes != 0) {
                label = context.getResources().getString(labelRes);
            } else {
                CharSequence labelSeq = info.loadLabel(pm);
                label = labelSeq != null
                    ? labelSeq.toString()
                    : info.name;
            }
		} catch (NameNotFoundException e) {
		}
		return label;
	}

    public static QuickTooltip setupInfoTooltip(Context context, QuickTooltip tooltip) {
        if (tooltip == null) {
            tooltip = createDefaultTooltip(context);
            tooltip.setCalloutTintColor(context.getResources().getColor(R.color.tooltip_info));
        }
        Drawable image = context.getResources().getDrawable(R.drawable.ic_launcher);
        CharSequence title = context.getResources().getText(R.string.app_name);
        CharSequence description = Html.fromHtml(Utilities.getStringAsset(context, "droidux_info.html"));
        tooltip.setTips(image, title, description);
        tooltip.setAnimationStyle(QuickTooltip.ANIM_FADE);

        return tooltip;
    }

    public static QuickTooltip setupDescriptionTooltip(Activity context, QuickTooltip tooltip, int descriptionRes) {
        if (tooltip == null) {
            tooltip = createDefaultTooltip(context);
            tooltip.setCalloutTintColor(context.getResources().getColor(R.color.tooltip_desc));
        }
        CharSequence title = getActivityTitle(context);
        CharSequence description = context.getResources().getText(descriptionRes);
        tooltip.setTips(title, description);
        tooltip.setAnimationStyle(QuickTooltip.ANIM_FADE);

        return tooltip;
    }

    private static QuickTooltip createDefaultTooltip(Context context) {
        QuickTooltip tooltip = new QuickTooltip(context);
        tooltip.setCalloutDrawables(R.drawable.callout_base,R.drawable.callout_leftarrow,
                R.drawable.callout_toparrow, R.drawable.callout_rightarrow, R.drawable.callout_bottomarrow,
                new Rect(14,14,14,14));
        return tooltip;
    }
}
