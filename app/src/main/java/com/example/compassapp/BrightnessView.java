package com.example.compassapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.widgets.Rectangle;

public class BrightnessView extends View {

    private Paint paint=new Paint() ;
    private float brightness ;

    private Rect rect ;

    public BrightnessView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        int centreY=(int) getPivotY() ;
        int rectStartX = 0+getWidth()/100 ;
        int rectStartY = centreY + getHeight()/20 ;
        rect=new Rect(rectStartX,rectStartY,getWidth()-2*getWidth()/100,2*(getHeight()/20)) ;
    }

    public BrightnessView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.BLACK);
        //get the y value of the middle point of the view
        paint.setTextSize(30);
        canvas.drawText(Float.toString(brightness),60,70,paint);
        paint.reset();
        paint.setColor(Color.BLACK);
        canvas.drawRect(rect,paint);
        canvas.drawLine(150,150,0,0,paint);
        //set the atrributes for the rectangle

    }

    public void setBrightness(float brightness){
        this.brightness=brightness ;
    }
}
