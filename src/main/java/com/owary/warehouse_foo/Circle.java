/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.owary.warehouse_foo;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;

/**
 * @author OwaryLtd
 */
public class Circle implements GLEventListener {

    public static void main(String[] args) {

        //getting the capabilities object of GL2 profile
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        Circle l = new Circle();
        glcanvas.addGLEventListener(l);
        glcanvas.setSize(600, 600);

        //creating frame
        final JFrame frame = new JFrame("Bouncing");

        //adding canvas to frame
        frame.getContentPane().add(glcanvas);

        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);

        final FPSAnimator animator = new FPSAnimator(glcanvas, 300, true);
        animator.start();

    }//end of main

    @Override
    public void init(GLAutoDrawable glad) {

    }

    @Override
    public void dispose(GLAutoDrawable glad) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        drawCircle(gl);
    }

    public void drawCircle(GL2 gl) {
        gl.glBegin(GL2.GL_POINTS);
        double y = 0;
        double radius = 1;
        double a = radius * Math.cos(Math.PI / 4);
        System.out.println("a : " + a);
        for (double i = 0; i <= a; i += 0.01) {
            y = Math.sqrt(radius * radius - i * i);
            gl.glVertex2d(i, y);
            gl.glVertex2d(i, -y);
            gl.glVertex2d(-i, y);
            gl.glVertex2d(-i, -y);
            gl.glVertex2d(y, i);
            gl.glVertex2d(-y, -i);
            gl.glVertex2d(-y, i);
            gl.glVertex2d(y, -i);
        }
        gl.glEnd();
    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
    }
}
