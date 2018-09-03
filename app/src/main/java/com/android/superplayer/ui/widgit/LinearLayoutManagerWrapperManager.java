package com.android.superplayer.ui.widgit;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * authr : edz on 2017/9/26  下午5:50
 * describe : recyclerView IndexOutOfBoundsException
 */


public class LinearLayoutManagerWrapperManager extends LinearLayoutManager {

    public LinearLayoutManagerWrapperManager(Context context) {
        super(context);
    }

    public LinearLayoutManagerWrapperManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public LinearLayoutManagerWrapperManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
