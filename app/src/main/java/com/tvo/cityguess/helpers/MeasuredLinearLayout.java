package com.tvo.cityguess.helpers;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by victor on 12/21/16.
 */

public class MeasuredLinearLayout extends LinearLayout {

    public MeasuredLinearLayout(Context context) {
        super(context);
    }

    public MeasuredLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasuredLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.d( "HEIGHT FROM ONMEASURE", getMeasuredHeight() + "" );

    }
}
