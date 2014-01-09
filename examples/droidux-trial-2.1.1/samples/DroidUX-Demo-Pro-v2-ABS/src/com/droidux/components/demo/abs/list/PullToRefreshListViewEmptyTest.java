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

package com.droidux.components.demo.abs.list;

import android.os.Bundle;
import android.view.View;

import com.droidux.components.demo.abs.R;
import com.droidux.widget.pull2refresh.VerticalEdgeDetector;

public class PullToRefreshListViewEmptyTest extends PullToRefreshListViewTest {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mAdapter.clear();
	}

    @Override
    int getLayoutId() {
        return R.layout.activity_pulltorefreshlistviewemptytest;
    }

    @Override
    void initPull2Refresh() {
        super.initPull2Refresh();
        // use custom edge detector to make the empty view pullable.
        mPullRefreshListView.setEdgeDetector(new VerticalEdgeDetector() {

            @Override
            public boolean isTopEdgeVisible(View view) {
                if(getListView().getVisibility()==View.GONE) {
                    return VerticalEdgeDetector.detectTopEdge(getListView().getEmptyView());
                } else {
                    return VerticalEdgeDetector.detectTopEdge(getListView());
                }
            }

            @Override
            public boolean isBottomEdgeVisible(View view) {
                if(getListView().getVisibility()==View.GONE) {
                    return VerticalEdgeDetector.detectBottomEdge(getListView().getEmptyView());
                } else {
                    return VerticalEdgeDetector.detectBottomEdge(getListView());
                }
            }
        });    }

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_list_p2r_empty_desc;
    }
}
