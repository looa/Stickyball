package org.looa.stickyballview.utils;

import android.graphics.PointF;

/**
 * 几何图形工具
 */
public class GeometryUtil {

    /**
     * As meaning of method name. 获得两点之间的距离
     *
     * @param p0
     * @param p1
     * @return
     */
    public static float getDistanceBetween2Points(PointF p0, PointF p1) {
        float distance = (float) Math.sqrt(Math.pow(p0.y - p1.y, 2) + Math.pow(p0.x - p1.x, 2));
        return distance;
    }

    /**
     * Get middle point between p1 and p2. 获得两点连线的中点
     *
     * @param p1
     * @param p2
     * @return
     */
    public static PointF getMiddlePoint(PointF p1, PointF p2) {
        return new PointF((p1.x + p2.x) / 2.0f, (p1.y + p2.y) / 2.0f);
    }

    /**
     * Get point between p1 and p2 by percent. 根据百分比获取两点之间的某个点坐标
     *
     * @param p1
     * @param p2
     * @param percent
     * @return
     */
    public static PointF getPointByPercent(PointF p1, PointF p2, float percent) {
        return new PointF(evaluateValue(percent, p1.x, p2.x), evaluateValue(percent, p1.y, p2.y));
    }

    /**
     * 根据分度值，计算从start到end中，fraction位置的值。fraction范围为0 -> 1
     *
     * @param fraction
     * @param start
     * @param end
     * @return
     */
    public static float evaluateValue(float fraction, Number start, Number end) {
        return start.floatValue() + (end.floatValue() - start.floatValue()) * fraction;
    }

    /**
     * TODO 这个方法还是有很多问题的，只在横向移动的时候比较完美
     * Get the point of intersection between circle and line. 获取
     * 通过指定圆心，斜率为lineK的直线与圆的交点。
     *
     * @param pMiddle The circle center point.
     * @param radius  The circle radius.
     * @param lineK   The slope of line which cross the pMiddle.
     * @return
     */
    public static PointF[] getIntersectionPoints(PointF pMiddle, float radius, Double lineK, boolean isSource) {
        PointF[] points = new PointF[2];

        float radian, xOffset, yOffset;

        radian = (float) Math.atan(lineK);
        xOffset = (float) (Math.cos(radian) * radius / 2f);
        yOffset = (float) (Math.sin(radian) * radius / 2f);

        PointF p;
        if (isSource) p = new PointF(pMiddle.x + xOffset, pMiddle.y + yOffset);
        else p = new PointF(pMiddle.x - xOffset, pMiddle.y + yOffset);

        float dx = (float) (radius / 2f * Math.sqrt(3) * Math.sin(radian));
        float dy = (float) (radius / 2f * Math.sqrt(3) * Math.cos(radian));

        if (isSource) {
            points[0] = new PointF(p.x + dx, p.y + dy);
            points[1] = new PointF(p.x + dx, p.y - dy);
        } else {
            points[0] = new PointF(p.x - dx, p.y + dy);
            points[1] = new PointF(p.x - dx, p.y - dy);
        }

        return points;
    }

    public static PointF[] getIntersectionPoints(PointF o1, PointF o2, float r1, float r2, double k) {
        PointF[] points = new PointF[4];
        PointF p1 = new PointF();
        PointF p2 = new PointF();
        PointF p3 = new PointF();
        PointF p4 = new PointF();
        float alpha, beta, theta;
        float d;
        theta = (float) Math.atan(k);
        d = (float) (Math.sqrt(Math.pow(o1.x - o2.x, 2) + Math.pow(o1.y - o2.y, 2)) - r1 - r2);
        alpha = (float) Math.asin(r1 / (r1 + d / 2));
        beta = (float) Math.asin(r2 / (r2 + d / 2));
        float dX1, dY1, dX2, dY2;
        dX1 = (float) (r1 * Math.cos(Math.PI / 2 - alpha - theta));
        dY1 = (float) (r1 * Math.sin(Math.PI / 2 - alpha - theta));
        p1.set(o1.x - dX1, o1.y - dY1);
        dX2 = (float) (r2 * Math.cos(beta + theta));
        dY2 = (float) (r2 * Math.sin(beta + theta));
        p2.set(o2.x + dX2, o2.y - dY2);
        float[] xy = {};
        return points;
    }
}