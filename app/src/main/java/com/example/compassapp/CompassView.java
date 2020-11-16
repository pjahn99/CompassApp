package com.example.compassapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CompassView extends View {
    //stored the current angle of the phone
    private float angle ;

    //needed for style information of the object, that has to be drawn
    private Paint paint=new Paint() ;

    public CompassView(Context context, AttributeSet attr) {
        super(context, attr);
    }


    public CompassView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //get the coordinates for the center in the View
        float centreX=getPivotX() ;
        float centreY=getPivotY() ;

        //define the Radius of the red point and the radius of the circle
        float pointRadius= getWidth()/20 ;
        float circleRadius=getWidth()/2 ;

        //calculate the end points for the compass needle
        float directionX = centreX - (float) Math.sin(angle/180 * Math.PI) * circleRadius ;
        float directionY = centreY - (float) Math.cos(angle/180 * Math.PI) * circleRadius ;


        //defining the look of the line and drawing it
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(getWidth()/130);
        canvas.drawLine(centreX,centreY,directionX,directionY,paint);

        //defining the look of the red dot and drawing it
        paint.reset();
        paint.setColor(Color.RED) ;
        paint.setStyle(Paint.Style.FILL) ;
        canvas.drawCircle(centreX,centreY,pointRadius,paint);


        //defining the look of the red circle and drawing it
        paint.reset();
        paint.setColor(Color.BLACK) ;
        paint.setStyle(Paint.Style.STROKE) ;
        paint.setStrokeWidth(getWidth()/130);
        canvas.drawCircle(centreX,centreY,circleRadius,paint);
    }


    public void setAzimuth(float azimuthValue){
        angle=azimuthValue ;
    }
}
