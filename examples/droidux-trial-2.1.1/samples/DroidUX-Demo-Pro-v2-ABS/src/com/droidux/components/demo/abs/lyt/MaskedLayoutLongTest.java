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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.components.demo.abs.internal.ComposePathDrawable;
import com.droidux.widget.UrlImageView;
import com.droidux.widget.layouts.MaskedLayout;

import java.util.Arrays;

public class MaskedLayoutLongTest extends BaseTestActivity {
    int mCount=10;
	@Override
	protected int getLayoutId() {
		return R.layout.activity_maskedlayoutlongtest;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MaskedLayout ml = (MaskedLayout) findViewById(R.id.lytMasked);
		ml.setMaskDrawable(new ComposePathDrawable());


		LinearLayout container = (LinearLayout) findViewById(R.id.container);
		DemoImage[] items = createItems();
        if (items.length < mCount) {
            mCount = items.length;
        }
		for (int i = 0; i < mCount; i++) {
			addContent(i, items[i], container);
		}

		container.requestLayout();

	}

    private DemoImage[] createItems() {
        DemoImage[] items = new DemoImage[15];

        //#1
        items[0] = new DemoImage("Chessboard", "chessboard");
        //#2
        items[1] = new DemoImage("Earth", "earth");
        //#3
        items[2] = new DemoImage("Grapes", "grapes");
        //#4
        items[3] = new DemoImage("Lake", "lake");
        //#5
        items[4] = new DemoImage("Ford Mustang", "mustang");
        //#6
        items[5] = new DemoImage("Nebula", "nebula");
        //#7
        items[6] = new DemoImage("New York", "newyork");
        //#8
        items[7] = new DemoImage("Orange", "orange");
        //#9
        items[8] = new DemoImage("Red Kite", "redkite");
        //#10
        items[9] = new DemoImage("Rose", "rose");
        //#11
        items[10] = new DemoImage("Flying Seagull", "seagull");
        //#12
        items[11] = new DemoImage("Space Shuttle", "shuttle");
        //#13
        items[12] = new DemoImage("Smarties", "smarties");
        //#14
        items[13] = new DemoImage("Sun", "sun");
        //#15
        items[14] = new DemoImage("Tulips", "tulips");
        Arrays.sort(items);
        return items;
    }
	private void addContent(int index, DemoImage image, LinearLayout parent) {
		View view = LayoutInflater.from(this).inflate(R.layout.simple_item, null);
        parent.addView(view);
		UrlImageView uiv = (UrlImageView) view.findViewById(R.id.image);
		TextView title = (TextView) view.findViewById(R.id.title);
		uiv.setImageUrl(image.getImageUrl(), R.drawable.placeholder_port, R.drawable.placeholder_port_err);
		title.setText(image.title);
		int bg = getResources().getColor((index%2 == 0)?R.color.holo_purple_1 : R.color.holo_purple_2);
		view.setBackgroundColor(bg);
	}

    static class DemoImage implements Comparable<DemoImage> {
        private String title;
        private String image;

        DemoImage(String title, String image) {
            this.title = title;
            this.image = image;
        }

        String getImageUrl() {
            return String.format("http://www.droidux.com/images/droidux/apidemos_v2/port/%s.jpg", image);
        }

        @Override
        public int compareTo(DemoImage another) {
            return this.title.compareTo(another.title);
        }
    }

}

