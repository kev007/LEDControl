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

package com.droidux.components.demo.abs.p2r;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.interfaces.OnRefreshListener;
import com.droidux.widget.pull2refresh.VerticalEdgeDetector;
import com.droidux.widget.pull2refresh.VerticalPullToRefreshLayout;

public class PullToRefreshScrollViewTest extends BaseTestActivity {
	VerticalPullToRefreshLayout mPullRefreshListView;
	ScrollView mScrollView;
	TextView mTextView;
	private int sParCount=1;
	@Override
	protected int getLayoutId() {
		return R.layout.activity_p2rscrolllistviewtest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_p2r_scroll_desc;
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mScrollView = (ScrollView) findViewById(R.id.scrollview);
		mTextView = (TextView) findViewById(R.id.text);
		updateText();
		mPullRefreshListView = (VerticalPullToRefreshLayout) findViewById(R.id.p2r_layout);
		mPullRefreshListView.setOnPullDownRefreshListener(new OnRefreshListener.Standard() {
			@Override
			public void refreshInBackground() {
				// here will normally be a long running task
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
				}
			}

			@Override
			public void onAfterRefresh() {
				updateText();
			}

		});
		mPullRefreshListView.setOnPullUpRefreshListener(new OnRefreshListener.Standard() {
			@Override
			public void refreshInBackground() {
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
				}
			}

			@Override
			public void onAfterRefresh() {
				updateText();
			}
		});
		// use custom edge detector to make the empty view pullable.
		mPullRefreshListView.setEdgeDetector(new VerticalEdgeDetector() {

			@Override
			public boolean isTopEdgeVisible(View view) {
				return mScrollView.getScrollY()<=0;
			}

			@Override
			public boolean isBottomEdgeVisible(View view) {
				return (mScrollView.getScrollY() + mScrollView.getHeight()) >= mTextView.getBottom();
			}
		});
	}

	private void updateText() {
		mTextView.append(String.format("Paragraph #%s.\n",sParCount++));
		mTextView.append(LIPSUM);

	}

	private static final String LIPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
			"Vivamus tincidunt, sapien non blandit scelerisque, sem.\n\n";

}
