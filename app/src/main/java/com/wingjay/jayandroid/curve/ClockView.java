package com.wingjay.jayandroid.curve;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.Calendar;

/**
 * Draw a Clock.
 */
public class ClockView extends View {

    private final static int UPDATE_TIME = 1;
    private int width, height;
    private int defaultWidth = 300, defaultHeight = 300;
    private int centerX, centerY; // based on the view

    private Paint circlePaint;
    private Paint innerCirclePaint;
    private Paint longPointerPaint;
    private Paint midPointerPaint;
    private Paint shortPointerPaint;
    private Paint textPaint;
    private Paint hourLinePaint;
    private Paint minuteLinePaint;
    private Paint secondLinePaint;

    private int longPointerLength = 30;
    private int midPointerLength = 20;
    private int shortPointerLength = 15;

    private int hour = 0, minute = 0, second = 0;

    private int paddingLeft = 5, paddingTop = 5, paddingRight = 5, paddingBottom = 5;
    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(5);

        innerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        longPointerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        longPointerPaint.setStyle(Paint.Style.FILL);
        longPointerPaint.setStrokeWidth(5);

        midPointerPaint = new Paint(longPointerPaint);
        midPointerPaint.setStrokeWidth(3);

        shortPointerPaint = new Paint(longPointerPaint);
        shortPointerPaint.setStrokeWidth(1);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        hourLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        hourLinePaint.setColor(Color.BLACK);

        minuteLinePaint = new Paint(hourLinePaint);
        minuteLinePaint.setColor(Color.GREEN);

        secondLinePaint = new Paint(hourLinePaint);
        secondLinePaint.setColor(Color.RED);

        new TimeThread().start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST) {
            width = defaultWidth;
        }
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            height = defaultHeight;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setCenterXY();

        // 1. Draw circle
        canvas.drawCircle(centerX, centerY, getRadius(), circlePaint);
        canvas.save();

        // 2. Pointer
        for (int i=0; i<60; i++) {
            if (i % 15 == 0) {
                // Hour pointer
                canvas.drawLine(centerX, centerY - getRadius(),
                        centerX, centerY - getRadius() + longPointerLength, longPointerPaint);

                int value = (i == 0) ? 12 : (i / 5);
                float textWidth = textPaint.measureText(String.valueOf(value));
                textPaint.setTextSize(28);
                // move and rotate canvas to keep text vertically
                canvas.translate(centerX, centerY - getRadius() + longPointerLength);
                canvas.rotate(-(i * 6));
                float textX = 0, textY = 0;
                switch (i) {
                    case 0:
                        // top
                        textX = -(textWidth / 2);
                        textY = 25;
                        break;
                    case 15:
                        // right
                        textX = -25;
                        textY = textWidth / 2;
                        break;
                    case 30:
                        // bottom
                        textX = -(textWidth / 2);
                        textY = -10;
                        break;
                    case 45:
                        //left
                        textX = 10;
                        textY = textWidth / 2;
                        break;
                }
                canvas.drawText(String.valueOf(value), textX, textY, textPaint);
                // restore the canvas
                canvas.rotate(i * 6);
                canvas.translate(-centerX, -(centerY - getRadius() + longPointerLength));
            } else if (i % 5 == 0) {
                // Minute pointer
                canvas.drawLine(centerX, centerY - getRadius(),
                        centerX, centerY - getRadius() + midPointerLength, midPointerPaint);
            } else {
                // Second pointer
                canvas.drawLine(centerX, centerY - getRadius(),
                        centerX, centerY - getRadius() + shortPointerLength, shortPointerPaint);
            }
            canvas.save();
            canvas.rotate(6, centerX, centerY);
        }

        // 3. two Lines
        canvas.translate(centerX, centerY);
        canvas.rotate(-90);
        // calculate degrees for two lines
        float hourDegree = (float)((hour * 3600 + minute * 60 + second) * 360 / (12 * 60 * 60)) ;
        float minuteDegree = (float)((minute * 60) * 360 / (60 * 60));
        int secondDegree = second * 6;
        // hour
        canvas.rotate(hourDegree);
        canvas.drawRect(-6, -6, getRadius() * 6 / 10, 6, hourLinePaint);
        canvas.rotate(-hourDegree);
        // minute
        canvas.rotate(minuteDegree);
        canvas.drawRect(-4, -4, getRadius() * 7 / 10, 4, minuteLinePaint);
        canvas.rotate(-minuteDegree);
        // second
        canvas.rotate(secondDegree);
        canvas.drawRect(-1, -1, getRadius(), 1, secondLinePaint);
        // circle
        canvas.drawCircle(0, 0, 10, innerCirclePaint);
        canvas.restore();
    }

    private int getRadius() {
        return Math.min(width, height) / 2;
    }

    private void setCenterXY() {
        centerX = width / 2;
        centerY = height / 2;
    }

    private class TimeThread extends Thread {
        @Override
        public void run() {
            Calendar calendar;
            do {
                calendar = Calendar.getInstance();
                int milliSecond = calendar.get(Calendar.MILLISECOND);
                if (milliSecond == 0) {
                    Message msg = new Message();
                    msg.what = UPDATE_TIME;
                    msg.obj = calendar;
                    timeHandler.sendMessage(msg);
                }
            } while (true);
        }
    }

    private final Handler timeHandler = new TimeHandler(this);

    private static class TimeHandler extends Handler {

        private final WeakReference<ClockView> clockViewWeakReference;

        public TimeHandler(ClockView clockView) {
            this.clockViewWeakReference = new WeakReference<>(clockView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPDATE_TIME) {
                ClockView clockView = clockViewWeakReference.get();
                if (clockView != null) {
                    clockView.updateTime((Calendar) msg.obj);
                }
            }
        }
    }

    public void updateTime() {
        updateTime(Calendar.getInstance());
    }

    public void updateTime(Calendar calendar) {
        updateTime(calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));
    }

    public void updateTime(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        invalidate();
    }

}
