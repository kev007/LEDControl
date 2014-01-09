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

import android.content.Context;
import android.os.Bundle;

import com.droidux.components.demo.abs.R;
import com.droidux.interfaces.OnRefreshListener;
import com.droidux.widget.pull2refresh.VerticalPullToRefreshLayout;

public class PullToRefreshListViewTest extends DuxListViewTest {
	VerticalPullToRefreshLayout mPullRefreshListView;
	PullToRefreshAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initPull2Refresh();

		mAdapter = new PullToRefreshAdapter(this);
		// init items
		for(int i=0;i<10;i++) {
			mAdapter.addToLast(i);
		}

		setListAdapter(mAdapter);
	}

    int getLayoutId() {
       return R.layout.activity_pulltorefreshlistviewtest;
    }

    void initPull2Refresh() {
        mPullRefreshListView = (VerticalPullToRefreshLayout) findViewById(R.id.p2r_layout);
        mPullRefreshListView.setOnPullDownRefreshListener(new OnRefreshListener.Standard() {
            int[] ids;
            @Override
            public void refreshInBackground() {
                // here will normally be a long running task
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }
                int size = sRnd.nextInt(5);
                ids = new int[size==0?1:size];
                for (int i = 0; i < ids.length; i++) {
                    ids[i] = sRnd.nextInt(WebImageAdapter.IMAGE_COUNT);
                }
            }

            @Override
            public void onAfterRefresh() {
                for (int i = 0; i < ids.length; i++) {
                    mAdapter.addToFirst(ids[i]);
                }
                mAdapter.notifyDataSetChanged();
            }

        });
        mPullRefreshListView.setOnPullUpRefreshListener(new OnRefreshListener.Standard() {
            int[] ids;
            @Override
            public void refreshInBackground() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                }
                int size = sRnd.nextInt(5);
                ids = new int[size==0?1:size];
                for (int i = 0; i < ids.length; i++) {
                    ids[i] = sRnd.nextInt(WebImageAdapter.IMAGE_COUNT);
                }
            }

            @Override
            public void onAfterRefresh() {
                for (int i = 0; i < ids.length; i++) {
                    mAdapter.addToLast(ids[i]);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_list_p2r_desc;
    }

    static class PullToRefreshAdapter extends WebImageAdapter {

		PullToRefreshAdapter(Context context) {
			super(context);
		}

		@Override
		void init() {}

	}
}
