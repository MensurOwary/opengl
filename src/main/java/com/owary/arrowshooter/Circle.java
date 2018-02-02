/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.owary.arrowshooter;

import com.jogamp.opengl.GL2;

/**
 *
 * @author OwaryLtd
 */
public class Circle {

    private double radius;
    private Point center;
    private int num_segments;

    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
        this.num_segments = 10000;
    }

    public void drawCircle(GL2 gl) {
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glColor3d(1, 0, 0);
        for (int ii = 0; ii < num_segments; ii++) {
            float theta = 2.0f * 3.1415926f * ii / num_segments;
            float x = (float) (radius * Math.cos(theta));
            float y = (float) (radius * Math.sin(theta));
            gl.glVertex2d(x + center.getX(), y + center.getY());
        }
        gl.glPopMatrix();
        gl.glEnd();
    }

    public boolean isOnCircle(Point p) {
        double term1 = Math.pow(p.getX() - center.getX(), 2);
        double term2 = Math.pow(p.getY() - center.getY(), 2);
        double sum = term1+term2;
        return sum<=Math.pow(radius,2);
    }

    public boolean doesLineCrossCircle(Point lead, Point start) {
        // form the line's equation
        // slope
        double slope = (lead.getY()-start.getY())/(lead.getX()-start.getX());
        // y-intercept
        double yIntercept = lead.getY() - slope*lead.getX();
        // function
        double line = slope;
        // plug it to the circle's equation
        // check the equality
        return false;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

}
