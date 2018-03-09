package com.owary.bezier.quadratic;

import com.owary.bezier.domain.Point;

public class QuadraticBezier {

    // Formula Based
    public static Point bezier(Point p1, Point p2, Point p3, double t) {
        if (t > 1 || t < 0) return null;

        double x = (1 - t) * (1 - t) * p1.getX() + 2 * (1 - t) * t * p2.getX() + t * t * p3.getX();
        double y = (1 - t) * (1 - t) * p1.getY() + 2 * (1 - t) * t * p2.getY() + t * t * p3.getY();

        return new Point(x, y);
    }

    // Recursive Part
    public static Point midPoint(Point a, Point b) {
        double x = (a.getX() + b.getX()) / 2;
        double y = (a.getY() + b.getY()) / 2;
        return new Point(x, y);
    }

    public static boolean isFlatEnough(Point a, Point b, Point c, double epsilon) {
        double ab = Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2));
        double bc = Math.sqrt(Math.pow((c.getX() - b.getX()), 2) + Math.pow((c.getY() - b.getY()), 2));
        double ac = Math.sqrt(Math.pow((a.getX() - c.getX()), 2) + Math.pow((a.getY() - c.getY()), 2));

        double semiPerim = (ab + bc + ac) / 2;

        double altitude = (2 * Math.sqrt(semiPerim * (semiPerim - ab) * (semiPerim - bc) * (semiPerim - ac))) / ac;

        return altitude <= epsilon;
    }
}
