/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.owary.fillobjects;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * @author OwaryLtd
 */
public class Template implements GLEventListener {

    public Template() {

    }

    public static void main(String[] args) {

        //getting the capabilities object of GL2 profile
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        Template l = new Template();
        glcanvas.addGLEventListener(l);
        glcanvas.setSize(600, 600);

        //creating frame
        final JFrame frame = new JFrame("Fill Algorithm");
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
        gl.glScalef(0.20f, 0.20f, 0);

        gl.glTranslated(-5, 5, 0);
        gl.glBegin(GL2.GL_QUADS);

        double stX = 0;
        double stY = 0;
        double side = 1;

        Random random = new Random();

        int[][] arr = {{1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 1, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0}
        };


        Matrix matrix = new Matrix(arr);
        matrix.search();

        List<Set<Point>> list = matrix.getList();

        System.out.println(list);

        list.forEach((set) -> {
            gl.glColor3f(random.nextFloat(), random.nextFloat(), random.nextFloat());
            set.forEach((p) -> {
                drawSquare(p.getX(), -p.getY(), side, random, gl);
            });
        });

        gl.glPopMatrix();
        gl.glFlush();
        gl.glEnd();

    }

    public void drawSquare(double stX, double stY, double side, Random random, GL2 gl) {
        gl.glVertex3d(stX, stY, 0);
        gl.glVertex3d(stX + side, stY, 0);
        gl.glVertex3d(stX + side, stY - side, 0);
        gl.glVertex3d(stX, stY - side, 0);
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
