package com.wingjay.jayandroid.xiami;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.RotateDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.wingjay.jayandroid.R;
import com.wingjay.jayandroid.util.DisplayUtil;
import com.wingjay.jayandroid.util.ThreadUtil;

/**
 * PlayerProgressBar 播放器进度条
 *
 * ●__________________________________________  X轴
 * |                |              |       |
 * |                |  4:13/5:25   |       |
 * |                |______________|       |
 * |                   ___________         |
 * |-------===========| 4:13/5:25 |        |
 * |                   -----------         |
 * |_______________________________________|
 * |
 * Y轴
 *
 * @author wingjay
 * @date 2017/09/06
 */
public class PlayerProgressBar extends View {
    private int currentTime;
    private int totalTime;

    private static final int DEFAULT_HEIGHT = 70;
    private static final int FLOATING_BAR_HEIGHT = 30;
    private final int FLOATING_BAR_PADDING = DisplayUtil.dip2px(getContext(), 10);
    private static final int CONTROL_BAR_HEIGHT = 20;

    private int width; // whole view
    private final int SCREEN_WIDTH = DisplayUtil.getScreenWidth(getContext());
    private final int height = DisplayUtil.dip2px(getContext(), DEFAULT_HEIGHT); // 整个view高度
    private final int bottomAreaHeight = DisplayUtil.dip2px(getContext(), DEFAULT_HEIGHT - FLOATING_BAR_HEIGHT); //下半部分的高度

    // track line
    private int maxTrackLineWidth;
    private int currentTrackLineWidth;
    private RectF trackRectF = new RectF();

    // controlBar
    private Rect controlArea = new Rect();
    private final int CONTROL_BAR_PADDING = DisplayUtil.dip2px(getContext(), 5);
    private final int controlBarHeight = DisplayUtil.dip2px(getContext(), CONTROL_BAR_HEIGHT);
    private int controlBarWidth; // based on text length
    private int floatingBarWidth;

    // loading
    private boolean loading; // song is loading
    private Rect loadingRect = new Rect();
    private RotateDrawable rotateDrawable =
        (RotateDrawable) ContextCompat.getDrawable(getContext(), R.drawable.loading_circle);
    private ValueAnimator loadingAnimator;
    private int LOADING_IMAGE_SIZE = DisplayUtil.dip2px(getContext(), 10);
    private int LOADING_IMAGE_PADDING = DisplayUtil.dip2px(getContext(), 1);
    private final int floatingBarHeight = DisplayUtil.dip2px(getContext(), FLOATING_BAR_HEIGHT);
    private final int floatingBarExtraWidth = DisplayUtil.dip2px(getContext(), 40); // 滑动时的渐变尾巴更长一点

    private Paint trackLinePaint;
    private Paint controlPaint;
    private Paint timePaint;
    private Paint floatingBarPaint;
    private Paint floatingBarTimePaint;

    // drag
    private int lastX;
    private boolean isDragging = false;
    private DragDirection dragDirection = DragDirection.None;
    private OnPlayerDragListener onPlayerDragListener;

    // floatingBar
    private @ColorInt int floatingBarColor = ContextCompat.getColor(getContext(), R.color.alpha_xiamired);
    private @ColorInt int trackColor = ContextCompat.getColor(getContext(), R.color.xiamired);
    private LinearGradient leftDraggingTailGradient; // <:[====
    private LinearGradient rightDraggingTailGradient; // ===]:>
    private RectF leftCircleRectF = new RectF();
    private RectF rightCircleRectF = new RectF();
    private Path draggingBarPath = new Path();
    private enum DragDirection {
        Left, // <:[====
        Right, // ===]:>
        TAP, // display normal
        None, // hide floatingBar
    }

    public PlayerProgressBar(Context context) {
        this(context, null);
    }

    public PlayerProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        trackLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        trackLinePaint.setStrokeWidth(5);

        controlPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        controlPaint.setColor(Color.BLACK);

        timePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        timePaint.setColor(Color.WHITE);
        timePaint.setTextAlign(Align.CENTER);
        timePaint.setTextSize(DisplayUtil.dip2px(getContext(), 10));

        // floating bar
        floatingBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        floatingBarPaint.setColor(floatingBarColor);

        floatingBarTimePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        floatingBarTimePaint.setColor(Color.WHITE);
        floatingBarTimePaint.setTextSize(DisplayUtil.dip2px(getContext(), 14));
        floatingBarTimePaint.setTextAlign(Align.CENTER);

        loadingAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        loadingAnimator.setRepeatCount(ValueAnimator.INFINITE);
        loadingAnimator.setDuration(700);
        loadingAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rotateDrawable.setLevel((int)(10000 * animation.getAnimatedFraction()));
                invalidate(loadingRect);
            }
        });
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
        int originCanvas = canvas.save();
        // 1. draw trackLine
        canvas.translate(0, floatingBarHeight);
        drawBackgroundTrack(canvas);
        // 2. draw controlBar
        canvas.translate(currentTrackLineWidth, (bottomAreaHeight - controlBarHeight) / 2);
        drawControlBar(canvas);
        canvas.restoreToCount(originCanvas);
        // 3. draw floatingBar
        originCanvas = canvas.save();
        canvas.translate(getFloatingBarLeftX(), 0);
        drawFloatingBar(canvas);
        canvas.restoreToCount(originCanvas);
    }

    /**
     * 绘制悬浮bar，原点在左上角
     *
     * ● -----------------> X轴
     *  (               )
     * (  4:13 / 5:57    )
     *  (_______________)
     * |
     * Y轴
     */
    private void drawFloatingBar(Canvas canvas) {
        switch (dragDirection) {
            case Left:
                if (leftDraggingTailGradient == null) {
                    leftDraggingTailGradient = new LinearGradient(0, floatingBarHeight/2,
                        floatingBarWidth + floatingBarExtraWidth, floatingBarHeight/2,
                        new int[]{floatingBarColor, Color.TRANSPARENT}, null, TileMode.CLAMP);
                }
                floatingBarPaint.setShader(leftDraggingTailGradient);
                draggingBarPath.reset();
                leftCircleRectF.set(0, 0, (float)floatingBarHeight, (float)floatingBarHeight);
                draggingBarPath.addArc(leftCircleRectF, 90, 180);
                draggingBarPath.addRect(floatingBarHeight/2, 0, floatingBarWidth + floatingBarExtraWidth,
                    floatingBarHeight, Direction.CCW);
                canvas.drawPath(draggingBarPath, floatingBarPaint);
                floatingBarPaint.setShader(null);

                canvas.translate((floatingBarWidth + floatingBarExtraWidth)/2, floatingBarHeight/2);
                break;
            case Right:
                if (rightDraggingTailGradient == null) {
                    rightDraggingTailGradient = new LinearGradient(0, floatingBarHeight/2,
                        floatingBarWidth + floatingBarExtraWidth, floatingBarHeight/2,
                        new int[]{Color.TRANSPARENT, floatingBarColor}, null, TileMode.CLAMP);
                }
                floatingBarPaint.setShader(rightDraggingTailGradient);
                draggingBarPath.reset();
                draggingBarPath.addRect(0, 0, floatingBarWidth + floatingBarExtraWidth - floatingBarHeight/2,
                    floatingBarHeight, Direction.CCW);
                rightCircleRectF.set(floatingBarWidth + floatingBarExtraWidth - floatingBarHeight, 0,
                    floatingBarWidth + floatingBarExtraWidth, floatingBarHeight);
                draggingBarPath.addArc(rightCircleRectF, 270, 180);
                canvas.drawPath(draggingBarPath, floatingBarPaint);
                floatingBarPaint.setShader(null);

                canvas.translate((floatingBarWidth + floatingBarExtraWidth)/2, floatingBarHeight/2);
                break;
            case TAP:
                RectF rectF = new RectF(0, 0, floatingBarWidth, floatingBarHeight);
                canvas.drawRoundRect(rectF, floatingBarHeight/2, floatingBarHeight/2, floatingBarPaint);
                canvas.translate(floatingBarWidth/2, floatingBarHeight/2);
                break;
            case None:
                return;
        }

        canvas.drawText(controlBarText(), 0, controlBarText().length(), floatingBarTimePaint);
    }

    /**
     * FloatingBar左边界：文字与controlBar文字居中
     */
    private int getFloatingBarLeftX() {
        int controlBarCenterX = currentTrackLineWidth + (controlBarWidth / 2);
        int floatingBarLeftX = controlBarCenterX - (floatingBarWidth / 2);
        if (floatingBarLeftX < 0) {
            return 0;
        } else if (dragDirection == DragDirection.Right
            && floatingBarLeftX > width - floatingBarWidth - floatingBarExtraWidth) {
            return width - floatingBarWidth - floatingBarExtraWidth;
        } else if (dragDirection != DragDirection.Right && floatingBarLeftX > width - floatingBarWidth) {
            return width - floatingBarWidth;
        } else {
            return floatingBarLeftX;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int nowX = getIntX(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!inTouchableArea(event)) {
                    return false;
                }
                isDragging = true;
                dragDirection = DragDirection.TAP;
                lastX = getIntX(event);
                updateCurrentTime(lastX, nowX);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDragging) {
                    if (nowX - lastX > 0) {
                        dragDirection = DragDirection.Right;
                    } else if (nowX - lastX < 0) {
                        dragDirection = DragDirection.Left;
                    } else {
                        if (dragDirection == DragDirection.None) {
                            dragDirection = DragDirection.None;
                        }
                    }
                    updateCurrentTime(lastX, nowX);
                    if (onPlayerDragListener != null) {
                        onPlayerDragListener.onDrag((int) (100 * currentPercent()), false);
                    }
                    lastX = nowX;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isDragging = false;
                dragDirection = DragDirection.None;
                updateCurrentTime(lastX, nowX);
                if (onPlayerDragListener != null) {
                    onPlayerDragListener.onDrag((int) (100 * currentPercent()), true);
                }
                break;
        }
        return true;
    }

    private int getIntX(MotionEvent event) {
        return Math.round(event.getX());
    }

    private void updateCurrentTime(int lastX, int nowX) {
        double changedPercentage = (double) (nowX - lastX) / maxTrackLineWidth;
        setCurrentTime(currentTime + (int) (changedPercentage * totalTime));
    }

    private boolean inTouchableArea(MotionEvent event) {
        return controlArea.contains((int) event.getX(), (int) event.getY());
    }

    /**
     * 计算 controlBar TrackLine floatingBar 宽度，用于后续绘制
     */
    private void preCalculateWidthInfo() {
        controlBarWidth = (int) timePaint.measureText(controlBarText()) + 2 * CONTROL_BAR_PADDING;
        if (loading) {
            controlBarWidth += 2 * (LOADING_IMAGE_SIZE + LOADING_IMAGE_PADDING);
        }

        maxTrackLineWidth = width - controlBarWidth;
        currentTrackLineWidth = (int) (maxTrackLineWidth * currentPercent());

        floatingBarWidth = (int) floatingBarTimePaint.measureText(controlBarText()) + 2 * FLOATING_BAR_PADDING;

        controlArea.left = currentTrackLineWidth;
        controlArea.top = floatingBarHeight;
        controlArea.right = currentTrackLineWidth + controlBarWidth;
        controlArea.bottom = height;
    }

    private void drawControlBar(Canvas canvas) {
        // canvas is already translated to right place.
        RectF rectF = new RectF(0, 0, controlBarWidth, controlBarHeight);
        canvas.drawRoundRect(rectF, controlBarHeight/2, controlBarHeight/2, controlPaint);

        if (loading) {
            loadingRect.set(CONTROL_BAR_PADDING + LOADING_IMAGE_PADDING,
                (controlBarHeight - LOADING_IMAGE_SIZE) / 2,
                CONTROL_BAR_PADDING + LOADING_IMAGE_PADDING + LOADING_IMAGE_SIZE,
                (controlBarHeight + LOADING_IMAGE_SIZE) / 2);
            rotateDrawable.setBounds(loadingRect);
            rotateDrawable.draw(canvas);
            canvas.translate((controlBarWidth + LOADING_IMAGE_SIZE + 2*LOADING_IMAGE_PADDING ) / 2, controlBarHeight / 2);
            canvas.drawText(controlBarText(), 0, controlBarText().length(), timePaint);
        } else {
            canvas.translate(controlBarWidth/2, controlBarHeight/2);
            canvas.drawText(controlBarText(), 0, controlBarText().length(), timePaint);
        }
    }

    /**
     * 设置时间信息。总时间和当前时间，单位：秒
     *
     * @param currentTimeSec 当前秒数
     * @param totalTimeSec 总时间秒数，最小为0
     */
    public void setTime(int currentTimeSec, @IntRange(from = 0) int totalTimeSec) {
        if (isDragging()) {
            return;
        }

        this.totalTime = totalTimeSec;
        setCurrentTime(currentTimeSec);
    }

    /**
     * 设置加载中状态，会出现loading元素
     */
    public void setLoading(boolean loading) {
        if (this.loading == loading) {
            return;
        }
        this.loading = loading;
        if (loading) {
            loadingAnimator.start();
        } else {
            loadingAnimator.end();
        }
        reDraw();
    }

    /**
     * 是否在拖动中
     */
    public boolean isDragging() {
        return isDragging;
    }

    public void setOnPlayerDragListener(@Nullable OnPlayerDragListener onPlayerDragListener) {
        this.onPlayerDragListener = onPlayerDragListener;
    }

    private void setCurrentTime(int currentTimeSec) {
        if (currentTimeSec < 0) {
            this.currentTime = 0;
        } else if (currentTimeSec > totalTime) {
            this.currentTime = totalTime;
        } else {
            this.currentTime = currentTimeSec;
        }

        reDraw();
    }

    private void reDraw() {
        if (ThreadUtil.isUIThread()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    private float currentPercent() {
        return currentTime / (float) totalTime;
    }

    private String controlBarText() {
        return String.format("%s / %s", formatToPlayerBarTime(currentTime*1000), formatToPlayerBarTime(totalTime * 1000));
    }

    private void drawBackgroundTrack(Canvas canvas) {
        trackRectF.set(0, bottomAreaHeight/2, currentTrackLineWidth, bottomAreaHeight/2);
        trackLinePaint.setShader(new LinearGradient(trackRectF.left, trackRectF.top, trackRectF.right, trackRectF.bottom,
            new int[]{Color.TRANSPARENT, trackColor},
            null, TileMode.CLAMP));
        canvas.drawLine(trackRectF.left, trackRectF.top, trackRectF.right, trackRectF.bottom, trackLinePaint);
    }

    /**
     * 转化成 "1:20/5:00" / "10:20/15:00"
     */
    private String formatToPlayerBarTime(long mills) {
        int secs = (int) (mills / 1000);
        int leftSecs = secs % 60;
        int mins = secs / 60;

        return String.format("%s:%s", mins + "", leftSecs < 10 ? ("0" + leftSecs) : leftSecs + "");
    }

    public interface OnPlayerDragListener {
        void onDrag(@IntRange(from = 0, to = 100) int progress, boolean isDragEnd);
    }
}
