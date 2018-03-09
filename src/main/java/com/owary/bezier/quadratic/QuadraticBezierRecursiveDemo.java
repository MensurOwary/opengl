/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.owary.bezier.quadratic;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.owary.bezier.domain.Point;

import javax.swing.*;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * @author OwaryLtd
 */
public class QuadraticBezierRecursiveDemo implements GLEventListener {

    public static Point p1 = new Point(200, 340);
    public static Point p2 = new Point(0, 150);
    public static Point p3 = new Point(250, 0);

    public QuadraticBezierRecursiveDemo() {

    }

    public static void main(String[] args) {

        //getting the capabilities object of GL2 profile
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        QuadraticBezierRecursiveDemo l = new QuadraticBezierRecursiveDemo();
        glcanvas.addGLEventListener(l);
        glcanvas.setSize(600, 600);

        //creating frame
        final JFrame frame = new JFrame("Quadratic Bezier Recursively");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //adding canvas to frame
        frame.getContentPane().add(glcanvas);

        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);

        final FPSAnimator animator = new FPSAnimator(glcanvas, 300, true);
//        animator.start();
    }//end of main

    @Override
    public void display(GLAutoDrawable drawable) {

        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glPointSize(.9f);
        gl.glScalef(0.0025f, 0.0025f, 0);

//        gl.glTranslated(-5, 5, 0);
//        gl.glBegin(GL2.GL_POINTS);
        gl.glBegin(GL2.GL_LINES);

//        drawAQuadraticBezier(p1, p2,p3,0.0001,gl);
        drawAQuadraticBezierRecursive(p1, p2, p3, 0.0001, gl);

        gl.glPopMatrix();
        gl.glFlush();
        gl.glEnd();

    }

    public void drawAQuadraticBezierRecursive(Point a, Point b, Point c, double precision, GL2 gl) {
        if (QuadraticBezier.isFlatEnough(a, b, c, precision)) {
            gl.glColor3d(1, 0, 0);
            gl.glVertex2d(a.getX(), a.getY());
            gl.glVertex2d(c.getX(), c.getY());
        } else {
            Point firstMid = QuadraticBezier.midPoint(a, b);
            Point secondMid = QuadraticBezier.midPoint(b, c);
            Point thirdMid = QuadraticBezier.midPoint(firstMid, secondMid);

            drawAQuadraticBezierRecursive(a, firstMid, thirdMid, precision, gl);
            drawAQuadraticBezierRecursive(thirdMid, secondMid, c, precision, gl);
        }
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        //method body
    }

    @Override
    public void init(GLAutoDrawable arg0) {
        // method body
    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {

    }

}
