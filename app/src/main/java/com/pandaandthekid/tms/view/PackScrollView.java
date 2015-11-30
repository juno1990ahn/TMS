package com.pandaandthekid.tms.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class PackScrollView extends HorizontalScrollView {

    private static final int SWIPE_PAGE_ON_FACTOR = 10;
    private static final int PACK_WIDTH_DP = 200;


    private int activePack;
    private int packWidth;

    private GestureDetector gestureDetector;

    public PackScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.activePack = 0;

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;

        gestureDetector = new GestureDetector(context, new PackScrollGesture());

        packWidth = (int) Math.ceil(PACK_WIDTH_DP * logicalDensity);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return gestureDetector.onTouchEvent(ev);
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

    class PackScrollGesture extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            try {
                if (e1.getX() - e2.getX() > packWidth / SWIPE_PAGE_ON_FACTOR) {
                    activePack++;
                } else if (e2.getX() - e1.getX() > packWidth / SWIPE_PAGE_ON_FACTOR) {
                    activePack--;
                }

                scrollToActiveItem();

                return true;
            } catch (Exception e) {
                Log.e("Fling", "There was an error processing the fling event: " + e.getMessage());
            }

            return false;
        }
    }
}
