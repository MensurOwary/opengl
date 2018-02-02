/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.owary.arrowshooter;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.TextRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author OwaryLtd
 */
public class Container extends JPanel implements GLEventListener {

    private static final int REFRESH_FPS = 60;  // Display refresh frames per second
    // Viewport Settings
    public static int xEdge = 500;
    public static int yEdge = 240;
    public static double rotateIncrement = 0;   // for rotation
    public static double moveX = 0;             // for moving x coord
    public static double speed = 15;             // speed of x
    public static double translateCircleCenter = 0;
    public static int circleMoveToggler = 1;
    public static boolean isReleased = false;
    public static boolean didCollide = false;
    public static FPSAnimator animator;         // Used to drive display()
    public TextRenderer renderer;
    private GLU glu;                            // For the GL Utility


    /* End */
    public Container() {
        GLCanvas canvas = new GLCanvas();
        this.setLayout(new BorderLayout());
        this.add(canvas, BorderLayout.CENTER);
        canvas.addGLEventListener(this);

        animator = new FPSAnimator(canvas, REFRESH_FPS, true);

    }

    @Override
    public void display(GLAutoDrawable drawable) {

        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL2.GL_MODELVIEW);

        // drawing and moving the target
        Point center = new Point(-350, 0 + translateCircleCenter);
        Circle circle = new Circle(center, 50);
        circle.drawCircle(gl);

        /* circle movement start */
        if (center.getY() + circle.getRadius() >= 240) {
            circleMoveToggler = -1;
        } else if (center.getY() - circle.getRadius() <= -240) {
            circleMoveToggler = 1;
        }
        translateCircleCenter += circleMoveToggler * 1.5;
        /* circle movement end */


        // adding the arrow
        Player player = new Player(100);

        // rotation
        player.rotateAroundLine(0 + rotateIncrement);

        // move the arrow
        player.move(moveX);
        if (isReleased) {
            moveX -= speed;
        }

        // collision detection
        if (circle.isOnCircle(player.getLeadingPoint())) {
            displayScore(renderer);
            isReleased = false;
            didCollide = true;
            circleMoveToggler = 0;
        }

        // drawing the player
        player.drawPlayer(gl);

        if (!isInBorder(player.getLeadingPoint())) {
            reset();
        }

        gl.glFlush();

    }

    public boolean isInBorder(Point p) {
        boolean result = false;
        if (p.getX() <= xEdge && p.getX() >= -xEdge && p.getY() <= yEdge && p.getY() >= -yEdge) {
            result = true;
        }
        return result;
    }

    public void reset() {
        rotateIncrement = 0;
        moveX = 0;
        isReleased = false;
        didCollide = false;
        circleMoveToggler = 1;
    }

    public synchronized void moveThePlayer(int keyCode) {
        System.out.println(keyCode);
        switch (keyCode) {
            case KeyEvent.VK_DOWN:
                if (!isReleased && !didCollide) {
                    rotateIncrement += 0.5;
                }
                break;
            case KeyEvent.VK_UP:
                if (!isReleased && !didCollide) {
                    rotateIncrement -= 0.5;
                }
                break;
            case KeyEvent.VK_SPACE:
                if (!didCollide) {
                    isReleased = true;
                }
                break;
            case KeyEvent.VK_ENTER:
                if (!isReleased) {
                    reset();
                }
                break;
        }
    }

    public void displayScore(TextRenderer renderer) {
        String msg1 = "             You Win!";
        String msg2 = "Press Enter to Restart!";
        renderer.beginRendering(Math.round(2 * xEdge), Math.round(2 * yEdge));
        // set the color
        renderer.setColor(1.0f, 0.2f, 0.2f, 0.8f);
        renderer.draw(msg1, 320, 240);
        renderer.draw(msg2, 320, 200);
        renderer.endRendering();
    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
        GL2 gl = glad.getGL().getGL2();

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-500, 500, -240, 240, -1, 1);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        // Get the OpenGL graphics context
        GL2 gl = drawable.getGL().getGL2();
        // GL Utilities
        glu = new GLU();
        // Enable smooth shading, which blends colors nicely, and smoothes out lighting.
        gl.glShadeModel(GL2.GL_SMOOTH);
        // Set background color in RGBA. Alpha: 0 (transparent) 1 (opaque) 
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        // Setup the depth buffer and enable the depth testing
        gl.glClearDepth(1.0f);          // clear z-buffer to the farthest
        gl.glEnable(GL.GL_DEPTH_TEST);  // enables depth testing
        gl.glDepthFunc(GL.GL_LEQUAL);   // the type of depth test to do
        // Do the best perspective correction
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

        renderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 36));
    }

    @Override
    public void dispose(GLAutoDrawable glad) {
    }
}
