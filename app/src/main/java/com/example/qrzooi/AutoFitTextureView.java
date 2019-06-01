package com.example.qrzooi;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;


public class AutoFitTextureView extends TextureView {

    private int rWidth = 0;
    private int rHeight = 0;


    public AutoFitTextureView(Context context) {
        this(context, null);
    }

    public AutoFitTextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoFitTextureView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAspectRatio(int w, int h){
        //zet de aspect ratio voor de view.

        if(w < 0 || h < 0){
            throw new IllegalArgumentException("Size cannot be negative.");
        }

        rWidth = w;
        rHeight = h;
        requestLayout();
    }

    protected void onMeasure(int wMeasure, int hMeasure){
        super.onMeasure(wMeasure, hMeasure);

        int w = MeasureSpec.getSize(wMeasure);
        int h = MeasureSpec.getSize(hMeasure);

        if(0 == rWidth || 0 == rHeight){
            setMeasuredDimension(w, h);
        } else {
            //rekent hoogte/breedte uit en matcht dat met de aspect ratio
            if(w < h * rWidth / rHeight){
                setMeasuredDimension(w, w * rHeight / rWidth);
            } else {
                setMeasuredDimension(h * rWidth / rHeight, h);
            }
        }
    }
}
