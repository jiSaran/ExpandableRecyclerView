package com.example.saran.extendablecardviewexample;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;

/**
 * Created by Saran on 6/17/2017.
 */

public class MyCardView extends CardView {
    private int animationDuration;
    private boolean mFlag;
    public MyCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyCardView(Context context) {
        super(context);
    }

    public MyCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void expand(final boolean flag){
        mFlag = flag;
        if (flag){
            final int initialHeight = getHeight();
            measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            final int targetHeight = getMeasuredHeight();
            final int distance_to_expand = targetHeight - initialHeight;
            Animation animation = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    getLayoutParams().height = (int) (initialHeight +(distance_to_expand*interpolatedTime));
                    requestLayout();
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };
            animationDuration = distance_to_expand;
            animation.setDuration((long)distance_to_expand);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    updateFlag(mFlag);
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    updateFlag(mFlag);
                }
            });
            startAnimation(animation);
        }
    }

    private void updateFlag(boolean flag){
        if (flag){
            flag = false;
        } else {
            flag = true;
        }
    }

    public int getAnimationTime(){
        return animationDuration;
    }

    public void collapse(){
        final int initialHeight = getMeasuredHeight();
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int collapsingHeight = (int) (44 * scale + 0.5f);
        final int distance_to_collapse = initialHeight - collapsingHeight;
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                getLayoutParams().height = (int)(initialHeight-(distance_to_collapse*interpolatedTime));
                requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animationDuration = distance_to_collapse;
        animation.setDuration((long)distance_to_collapse);
        startAnimation(animation);

    }
}
