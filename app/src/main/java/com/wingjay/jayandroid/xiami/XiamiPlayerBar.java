package com.wingjay.jayandroid.xiami;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.wingjay.jayandroid.util.DisplayUtil;

/**
 * XiamiPlayerBar
 *
 * ---------|4:13/5:50|
 *
 * @author wingjay
 * @date 2017/09/06
 */
public class XiamiPlayerBar extends View {

    private int currentTime = 357;
    private int totalTime = 357; // 357s
    private String totalTimeString;

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 50;
    private static final int CONTROL_BAR_HEIGHT_DP = 20;
    private final int CONTROL_BAR_PADDING = DisplayUtil.dp2px(getContext(), 8); //dp

    private int width; // whole view currentTrackLineWidth
    private final int SCREEN_WIDTH = DisplayUtil.getScreenWidth(getContext());
    private final int height = DisplayUtil.dp2px(getContext(), DEFAULT_HEIGHT);

    private int currentTrackLineWidth;
    private final int controlBarHeight = DisplayUtil.dp2px(getContext(), CONTROL_BAR_HEIGHT_DP);
    private int controlBarWidth; // based on text length

    private Paint trackLinePaint;
    private Paint controlPaint;
    private Paint timePaint;

    public XiamiPlayerBar(Context context) {
        this(context, null);
    }

    public XiamiPlayerBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XiamiPlayerBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundColor(0x55000000);
        totalTimeString = milliSecondToTime(totalTime * 1000);

        trackLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        controlPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        controlPaint.setColor(Color.BLACK);

        timePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        timePaint.setColor(Color.WHITE);
        timePaint.setTextAlign(Align.CENTER);
        timePaint.setTextSize(DisplayUtil.dp2px(getContext(), 10));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            width = SCREEN_WIDTH;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        preCalculateWidthInfo();
        drawBackgroundTrack(canvas);

        int trackCanvas = canvas.save();
        canvas.translate(currentTrackLineWidth, (height - controlBarHeight) / 2);
        drawControlBar(canvas);
        canvas.restoreToCount(trackCanvas);
    }

    private void preCalculateWidthInfo() {
        controlBarWidth = (int) timePaint.measureText(controlBarText()) + 2 * CONTROL_BAR_PADDING;
        int maxTrackLineWidth = width - controlBarWidth;
        currentTrackLineWidth = (int) (maxTrackLineWidth * currentPercent());
    }

    private void drawControlBar(Canvas canvas) {
        // canvas is already translated to right place.
        RectF rectF = new RectF(0, 0, controlBarWidth, controlBarHeight);
        canvas.drawRoundRect(rectF, controlBarHeight/2, controlBarHeight/2, controlPaint);
        canvas.translate(controlBarWidth/2, controlBarHeight/2);
        canvas.drawText(controlBarText(), 0, controlBarText().length(), timePaint);
    }

    @MainThread
    public void setCurrentTime(int currentTimeSec) {
        this.currentTime = currentTimeSec;
        invalidate();
    }

    private double currentPercent() {
        return currentTime / (double) totalTime;
    }

    private String controlBarText() {
        String currentString = milliSecondToTime(currentTime*1000);
        return currentString + " / " + totalTimeString;
    }

    private void drawBackgroundTrack(Canvas canvas) {
        LinearGradient trackLineDrawable = new LinearGradient(
            0, height/2,
            currentTrackLineWidth, height/2,
            new int[]{Color.TRANSPARENT, Color.RED},
            null, TileMode.CLAMP);
        trackLinePaint.setShader(trackLineDrawable);
        trackLinePaint.setStrokeWidth(5);
        canvas.drawLine(0, height/2,
            currentTrackLineWidth, height/2, trackLinePaint);
    }

    /**
     *  MilliSecond to xx:xx
     */
    public static String milliSecondToTime(long mills) {
        int secs = (int) (mills / 1000);
        int leftSecs = secs % 60;
        int mins = secs / 60;

        return String.format("%s:%s",
            mins < 10 ? ("0" + mins) : mins + "",
            leftSecs < 10 ? ("0" + leftSecs) : leftSecs + "");
    }
}
