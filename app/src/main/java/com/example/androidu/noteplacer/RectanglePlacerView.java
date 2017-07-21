package com.example.androidu.noteplacer;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class RectanglePlacerView extends View implements View.OnTouchListener{

    private Paint mPaint;

    private int mNumRows = 10;
    private int mNumColumns = 10;

    private int mScreenWidth = 1;
    private int mScreenHeight = 1;

    private int mSquareWidth = 1;
    private int mSquareHeight = 1;
    private float mPadding = 0.1f;

    private int mRectX1 = -1;
    private int mRectY1 = -1;
    private int mRectX2 = -1;
    private int mRectY2 = -1;
    private int mRectColor = Color.BLUE;
    private RectangleDragListener mListener;


    ////////////////////////////////////////////
    //
    //  Interfaces
    //
    ////////////////////////////////////////////

    public interface RectangleDragListener {
        public void onRectangleDrag(int rectX1, int rectY1, int rectX2, int rectY2);
    }


    ////////////////////////////////////////////
    //
    //  Constructors
    //
    ////////////////////////////////////////////

    public RectanglePlacerView(Context context){
        super(context);
        mPaint = new Paint();
        this.setOnTouchListener(this);
    }

    public RectanglePlacerView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        mPaint = new Paint();
        this.setOnTouchListener(this);
    }



    ////////////////////////////////////////////
    //
    //  Methods that the user can call
    //
    ////////////////////////////////////////////

    public void setNumRows(int rows){
        mNumRows = rows;
    }

    public void setNumColumns(int columns){
        mNumColumns = columns;
    }

    public void setDragRectColor(int color){
        // color is AlphaRGB with format 0xAARRGGBB
        mRectColor = color;
    }

    public void setNoteDragListener(RectangleDragListener listener){
        mListener = listener;
    }


    ////////////////////////////////////////////
    //
    //  Drawing and Touch Handler Methods
    //
    ////////////////////////////////////////////

    @Override
    protected void onDraw(Canvas canvas) {
        mScreenWidth = this.getWidth();
        mScreenHeight = this.getHeight();

        mSquareWidth = mScreenWidth / mNumColumns;
        mSquareHeight = mScreenHeight / mNumRows;

        canvas.drawRGB(255, 200, 200);

        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(4);
        for(int i = 1; i < mNumColumns; i++){
            canvas.drawLine(i * mSquareWidth, 0, i * mSquareWidth, mScreenHeight, mPaint);
        }
        for(int i = 1; i < mNumRows; i++){
            canvas.drawLine(0, i * mSquareHeight, mScreenWidth, i * mSquareHeight, mPaint);
        }

        mPaint.setColor(mRectColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(
                mRectX1 * mSquareWidth + mSquareWidth * mPadding,
                mRectY1 * mSquareHeight + mSquareHeight * mPadding,
                (mRectX2 + 1) * mSquareWidth - mSquareWidth * mPadding,
                (mRectY2 + 1) * mSquareHeight - mSquareHeight * mPadding,
                mPaint);

        invalidate();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            mRectX1 = (int)event.getX() / mSquareWidth;
            mRectY1 = (int)event.getY() / mSquareHeight;
            mRectX2 = mRectX1;
            mRectY2 = mRectY1;
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            if(mListener != null)
                mListener.onRectangleDrag(mRectX1, mRectY1, mRectX2, mRectY2);
        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE){

            int rectX = (int)event.getX() / mSquareWidth;
            int rectY = (int)event.getY() / mSquareHeight;

            if(rectX >= mRectX1){
                mRectX2 = rectX;
            }

            if(rectY >= mRectY1){
                mRectY2 = rectY;
            }

        }

        return true;
    }

}
