package com.droidux.tutorials.ui;

import android.R;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.droidux.widget.action.ActionDrawer;

public class ActionDrawerToggle implements ActionDrawer.OnDrawingListener {

    private static final float MAXIMUM_UP_SLIDING_OFFSET = 0.3f;
    private final Activity mContext;
    private TranslateDrawable mDrawerIcon;
    private final ActionDrawer mDrawer;

    public ActionDrawerToggle(Activity context, ActionDrawer drawer,  int drawerIcon) {
        mContext = context;
        mDrawer = drawer;
        setHomeAsUpIndicator(drawerIcon);
    }
    private void setHomeAsUpIndicator(int drawerIcon) {
        mDrawerIcon = null;
        final View homeView = mContext.findViewById(R.id.home);
        if (homeView != null) {
            final ViewGroup parent = (ViewGroup) homeView.getParent();
            final int childCount = parent.getChildCount();
            if (childCount != 2) {
                return;
            }

            final View firstChild = parent.getChildAt(0);
            final View secondChild = parent.getChildAt(1);
            final View upView = firstChild.getId()==android.R.id.home?secondChild:firstChild;
            if (upView instanceof ImageView) {
                ImageView upIndicatorView = (ImageView) upView;
                mDrawerIcon = new TranslateDrawable(mContext.getResources().getDrawable(drawerIcon));
                upIndicatorView.setImageDrawable(mDrawerIcon);
            }
        }
    }

    @Override
    public boolean onDrawerDrawing(Canvas canvas, float openRatio) {
        boolean drawing = mDrawerIcon != null;
        if (drawing) {
            mDrawerIcon.setOffset(MAXIMUM_UP_SLIDING_OFFSET *openRatio);
        }
        return drawing;
    }

    public void syncState() {
        mDrawerIcon.setOffset(mDrawer.isDrawerVisible()?1f:0f);
    }

    @Override
    public boolean onContentDrawing(Canvas canvas, float openRatio) {
        return false;
    }

    @Override
    public boolean onArrowDrawing(Canvas canvas, float openRatio, Rect arrowRect, Paint paint) {
        return false;
    }

    private static class TranslateDrawable extends Drawable implements Drawable.Callback {
        private Drawable wrapped;
        private float offset;

        private final Rect wrappedRect = new Rect();

        public TranslateDrawable(Drawable wrapped) {
            this.wrapped = wrapped;
        }

        public void setOffset(float offset) {
            this.offset = offset;
            invalidateSelf();
        }
        @Override
        public void invalidateDrawable(Drawable drawable) {
            if (drawable == wrapped) {
                invalidateSelf();
            }

        }

        @Override
        public void scheduleDrawable(Drawable drawable, Runnable runnable, long when) {
            if (drawable == wrapped) {
                scheduleSelf(runnable, when);
            }
        }

        @Override
        public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
            if (drawable == wrapped) {
                unscheduleSelf(runnable);
            }
        }

        @Override
        public void draw(Canvas canvas) {
            wrapped.copyBounds(wrappedRect);
            canvas.save();
            canvas.translate(wrappedRect.width()*-offset, 0);
            wrapped.draw(canvas);
            canvas.restore();
        }

        @Override
        public void setChangingConfigurations(int configs) {
            wrapped.setChangingConfigurations(configs);
        }

        @Override
        public int getChangingConfigurations() {
            return wrapped.getChangingConfigurations();
        }

        @Override
        public void setDither(boolean dither) {
            wrapped.setDither(dither);
        }

        @Override
        public void setFilterBitmap(boolean filter) {
            wrapped.setFilterBitmap(filter);
        }

        @Override
        public void setAlpha(int alpha) {
            wrapped.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            wrapped.setColorFilter(cf);
        }

        @Override
        public void setColorFilter(int color, PorterDuff.Mode mode) {
            wrapped.setColorFilter(color, mode);
        }

        @Override
        public void clearColorFilter() {
            wrapped.clearColorFilter();
        }

        @Override
        public boolean isStateful() {
            return wrapped.isStateful();
        }

        @Override
        public boolean setState(int[] stateSet) {
            return wrapped.setState(stateSet);
        }

        @Override
        public int[] getState() {
            return wrapped.getState();
        }

        @Override
        public Drawable getCurrent() {
            return wrapped.getCurrent();
        }

        @Override
        public boolean setVisible(boolean visible, boolean restart) {
            return super.setVisible(visible, restart);
        }

        @Override
        public int getOpacity() {
            return wrapped.getOpacity();
        }

        @Override
        public Region getTransparentRegion() {
            return wrapped.getTransparentRegion();
        }

        @Override
        protected boolean onStateChange(int[] state) {
            wrapped.setState(state);
            return super.onStateChange(state);
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            wrapped.setBounds(bounds);
        }

        @Override
        public int getIntrinsicWidth() {
            return wrapped.getIntrinsicWidth();
        }

        @Override
        public int getIntrinsicHeight() {
            return wrapped.getIntrinsicHeight();
        }

        @Override
        public int getMinimumWidth() {
            return wrapped.getMinimumWidth();
        }

        @Override
        public int getMinimumHeight() {
            return wrapped.getMinimumHeight();
        }

        @Override
        public boolean getPadding(Rect padding) {
            return wrapped.getPadding(padding);
        }

        @Override
        public ConstantState getConstantState() {
            return super.getConstantState();
        }    }
}
