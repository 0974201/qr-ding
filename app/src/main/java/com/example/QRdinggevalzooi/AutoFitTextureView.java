package com.example.QRdinggevalzooi;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;

public class AutoFitTextureView extends TextureView {

    private int mRatioWidth = 0;
    private int mRatioHeight = 0;

    public AutoFitTextureView(Context context) {

        super(context, null);
    }

    public AutoFitTextureView(Context context, AttributeSet att){

        this(context, att, 0);
    }

    public AutoFitTextureView(Context context, AttributeSet att, int style){

        super(context, att, style);
    }

    public void setAspectRatio(int width, int height){

        if(width < 0 || height < 0){

            throw new IllegalArgumentException("derp");
        }

        mRatioHeight = height;
        mRatioWidth = width;
        requestLayout();
    }

    protected void onMeasure(int widthMeas, int heightMeas){

        super.onMeasure(widthMeas, heightMeas);
        int width = MeasureSpec.getSize(widthMeas);
        int height = MeasureSpec.getSize(heightMeas);

        if(0 == mRatioWidth || 0 == mRatioHeight){

            setMeasuredDimension(width, height);
        } else {

            if(width < height * mRatioWidth / mRatioHeight){
                setMeasuredDimension(width, width * mRatioHeight / mRatioWidth);
            } else{
                setMeasuredDimension(height * mRatioWidth / mRatioHeight, height);
            }
        }
    }
}
