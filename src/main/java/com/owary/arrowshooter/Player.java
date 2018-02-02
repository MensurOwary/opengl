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
public class Player {

    private double length;
    private Point around;
    private Point leadingPoint;

    private Point aroundPreserved;
    private Point leadingPointPreserved;

    public Player(double length) {
        this.length = length;
        this.around = new Point(500, 0);
        this.leadingPoint = new Point(around.getX() - length, 0);
        this.aroundPreserved = around;
        this.leadingPointPreserved = leadingPoint;
    }

    public void drawPlayer(GL2 gl) {
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glBegin(GL2.GL_LINES);
        gl.glPointSize(0.65f);
        gl.glColor3d(0, 1, 0);

        gl.glVertex2d(leadingPoint.getX(), leadingPoint.getY());
        gl.glVertex2d(around.getX(), around.getY());

        gl.glPopMatrix();
        gl.glFlush();
        gl.glEnd();
    }

    public void move(double speedX){
        // form the line's equation
        // slope
        double slope = (leadingPoint.getY()-around.getY())/(leadingPoint.getX()-around.getX());
        // y-intercept
        double yIntercept = leadingPoint.getY() - slope*leadingPoint.getX();
        // function
        double speedYLeading = slope*(leadingPointPreserved.getX()+speedX)+yIntercept;
        double speedYAround = slope*(aroundPreserved.getX()+speedX)+yIntercept;
        
        
        around.setX(aroundPreserved.getX() + speedX);
        around.setY(speedYAround);

        leadingPoint.setX(leadingPointPreserved.getX() + speedX);
        leadingPoint.setY(speedYLeading);
    }
    
    public void reset(){
        around = aroundPreserved;
        leadingPoint = leadingPointPreserved;
    }

    public Point rotateAroundLine(double deg) {

        // Translate to origin
        Point p = new Point(leadingPoint.getX(), leadingPoint.getY());

        p.setX(p.getX() - around.getX());
        p.setY(p.getY() - around.getY());

        // Do the rotation
        double x = p.getX() * Math.cos(Math.toRadians(deg)) - p.getY() * Math.sin(Math.toRadians(deg));
        double y = p.getX() * Math.sin(Math.toRadians(deg)) + p.getY() * Math.cos(Math.toRadians(deg));

        // Translate back to the around
        
        x += around.getX();
        y += around.getY();
        
        // Construct the point
        Point p2 = new Point(x, y);
        getLeadingPoint().setX(x);
        getLeadingPoint().setY(y);
//        setLeadingPoint(p2);
        return p2;
    }

    public void setLeadingPoint(Point leadingPoint) {
        this.leadingPoint = leadingPoint;
    }

    public Point getLeadingPoint() {
        return leadingPoint;
    }

}
