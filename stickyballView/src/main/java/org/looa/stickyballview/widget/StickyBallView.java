package org.looa.stickyballview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import org.looa.stickyballview.utils.GeometryUtil;

/**
 * Created by ranxiangwei on 2017/1/19.
 */

public class StickyBallView extends View {

    private Paint paintBall;

    private PointF pointSource, pointTarget;//原点、目标点
    private PointF pointSourceCache, pointTargetCache;//保存原点、目标点数据
    private PointF[] pointSources, pointTargets;//贝塞尔介质各点
    private PointF pointControl, pointControl2;

    private Path movePath;

    private final static int DEFAULT_COLOR = Color.parseColor("#acacac");
    private int color = DEFAULT_COLOR;

    private float mCircleCenter = 150f;
    private float mRadius = 10f;//直径

    private float mMoveCircleCenter = 150f;
    private float mMoveRadius = 10f;//直径

    private OnTranslationListener listener;

    public StickyBallView(Context context) {
        this(context, null);
    }

    public StickyBallView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyBallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setColor(int color) {
        this.color = color;
    }

    private void init() {
        pointSource = new PointF(mCircleCenter, mCircleCenter);
        pointTarget = new PointF(mMoveCircleCenter, mMoveCircleCenter);
        pointSourceCache = new PointF(mCircleCenter, mCircleCenter);
        pointTargetCache = new PointF(mMoveCircleCenter, mMoveCircleCenter);

        paintBall = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBall.setColor(color);
        paintBall.setStrokeCap(Paint.Cap.ROUND);
        paintBall.setStyle(Paint.Style.FILL);

        resetPath(pointSource, pointTarget);
    }

    private void resetPath(PointF pointSource, PointF pointTarget) {
        float offX = pointSource.x - pointTarget.x;
        float offY = pointSource.y - pointTarget.y;
        double k = offX != 0 ? offY / offX : 0;

        pointSources = GeometryUtil.getIntersectionPoints(pointSource, mRadius, k, true);
        pointTargets = GeometryUtil.getIntersectionPoints(pointTarget, mMoveRadius, k, false);

        pointControl = GeometryUtil.getMiddlePoint(pointSource, pointTarget);
        pointControl2 = GeometryUtil.getMiddlePoint(pointSource, pointTarget);

        //中间变得更细
        pointControl.set(pointControl.x, pointControl.y - 1);
        pointControl2.set(pointControl2.x, pointControl2.y + 1);

        if (movePath == null) movePath = new Path();
        movePath.reset();
        movePath.moveTo(pointSources[0].x, pointSources[0].y);
        movePath.quadTo(pointControl.x, pointControl.y, pointTargets[0].x, pointTargets[0].y);
        movePath.lineTo(pointTargets[1].x, pointTargets[1].y);
        movePath.quadTo(pointControl2.x, pointControl2.y, pointSources[1].x, pointSources[1].y);
        movePath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(pointSource.x, pointSource.y, mRadius, paintBall);
        canvas.drawCircle(pointTarget.x, pointTarget.y, mMoveRadius, paintBall);
        canvas.drawPath(movePath, paintBall);
    }

    public void setSourceRadius(float mRadius) {
        this.mRadius = mRadius;
        resetPath(pointSource, pointTarget);
        invalidate();
    }

    public void setTargetRadius(float mMoveRadius) {
        this.mMoveRadius = mMoveRadius;
        resetPath(pointSource, pointTarget);
        invalidate();
    }

    public void setSourceXY(float[] sourceXY) {
        if (sourceXY == null || sourceXY.length != 2) return;
        pointSource.set(sourceXY[0], sourceXY[1]);
        pointSourceCache.set(sourceXY[0], sourceXY[1]);
        invalidate();
    }

    public void setTargetXY(float[] targetXY) {
        if (targetXY == null || targetXY.length != 2) return;
        pointTarget.set(targetXY[0], targetXY[1]);
        pointTargetCache.set(targetXY[0], targetXY[1]);
        invalidate();
    }

    public void updateSourceCache() {
        pointSourceCache.set(pointSource.x, pointSource.y);
    }

    public void updateTargetCache() {
        pointTargetCache.set(pointTarget.x, pointTarget.y);
    }

    public void setSourceTranslationX(float dX) {
        pointSource.set(pointSourceCache.x + dX, pointSource.y);
        if (listener != null) listener.onSourceTranslation(dX, 0);
        resetPath(pointSource, pointTarget);
        invalidate();
    }

    public void setSourceTranslationY(float dY) {
        pointSource.set(pointSource.x, pointSourceCache.y + dY);
        if (listener != null) listener.onSourceTranslation(0, dY);
        resetPath(pointSource, pointTarget);
        invalidate();
    }

    public void setTargetTranslationX(float dX) {
        pointTarget.set(pointTargetCache.x + dX, pointTarget.y);
        if (listener != null) listener.onTargetTranslation(dX, 0);
        resetPath(pointSource, pointTarget);
        invalidate();
    }

    public void setTargetTranslationY(float dY) {
        pointTarget.set(pointTarget.x, pointTargetCache.y + dY);
        if (listener != null) listener.onTargetTranslation(0, dY);
        resetPath(pointSource, pointTarget);
        invalidate();
    }

    public interface OnTranslationListener {
        void onSourceTranslation(float dX, float dY);

        void onTargetTranslation(float dX, float dY);
    }

    public void setOnTranslationListener(OnTranslationListener listener) {
        this.listener = listener;
    }
}
