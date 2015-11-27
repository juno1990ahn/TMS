package com.pandaandthekid.tms.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class PackScrollView extends HorizontalScrollView implements View.OnTouchListener {

    private static final int SWIPE_PAGE_ON_FACTOR = 10;
    private static final int PACK_WIDTH_DP = 200;


    private int activePack;
    private float prevScrollX;
    private boolean mStart;
    private int packWidth;

    public PackScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.activePack = 0;

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;

        packWidth = (int) Math.ceil(PACK_WIDTH_DP * logicalDensity);

        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getRawX();

        boolean handled = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                prevScrollX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mStart) {
                    prevScrollX = x;
                    mStart = false;
                }

                break;
            case MotionEvent.ACTION_UP:
                mStart = true;
                int minFactor = packWidth / SWIPE_PAGE_ON_FACTOR;

                if ((prevScrollX - (float) x) > minFactor) {
                    if (activePack < getMaxItemCount() - 1) {
                        activePack = activePack + 1;
                    }
                }
                else if (((float) x - prevScrollX) > minFactor) {
                    if (activePack > 0) {
                        activePack = activePack - 1;
                    }
                }

                scrollToActiveItem();

                handled = true;
                break;
        }

        return handled;
    }

    private int getMaxItemCount() {
        return getLinearLayout().getChildCount();
    }

    private LinearLayout getLinearLayout() {
        return (LinearLayout) getChildAt(0);
    }

    /**
     * Scrolls the list view to the currently active child.
     */
    public void scrollToActiveItem() {
        int maxItemCount = getMaxItemCount();
        if (maxItemCount == 0) {
            return;
        }

        int targetItem = Math.min(maxItemCount - 1, activePack);
        targetItem = Math.max(0, targetItem);

        activePack = targetItem;

        // Scroll so that the target child is centered
        View targetView = getLinearLayout().getChildAt(targetItem);

        int targetLeft = targetView.getLeft();
        int childWidth = targetView.getRight() - targetLeft;

        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int targetScroll = targetLeft - ((width - childWidth) / 2);

        super.smoothScrollTo(targetScroll, 0);
    }

    /**
     * Sets the current item and centers it.
     * @param currentItem The new current item.
     */
    public void setCurrentItemAndCenter(int currentItem) {
        this.activePack = currentItem;
        scrollToActiveItem();
    }

    public int getActivePack() {
        return activePack;
    }

    public void setActivePack(int activePack) {
        this.activePack = activePack;
    }
}
