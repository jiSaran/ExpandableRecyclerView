package com.example.saran.extendablecardviewexample;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Saran on 6/17/2017.
 */

public class MyCardView extends CardView {

    public MyCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyCardView(Context context) {
        super(context);
    }

    public MyCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void expand(){
        final int initialHeight = getHeight();
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int targetHeight = (int) (232 * scale + 0.5f);
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
        animation.setDuration((long)distance_to_expand);
        startAnimation(animation);
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
        animation.setDuration((long)distance_to_collapse);
        startAnimation(animation);
    }
}
