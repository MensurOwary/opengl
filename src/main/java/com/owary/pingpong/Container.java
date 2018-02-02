/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.owary.pingpong;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author OwaryLtd
 */
public class Container extends JPanel implements GLEventListener {

    private static final int REFRESH_FPS = 60;    // Display refresh frames per second
    private static float xEDGE = 500;
    private static float yEDGE = 240;
    private static float posXBound, posYBound, negXBound, negYBound;
    final FPSAnimator animator;  // Used to drive display()
    public Ball ball = new Ball();
    public Player p1 = new Player();
    public Player p2 = new Player();
    public TextRenderer renderer;
    private GLU glu;             // For the GL Utility

    /* End */
    public Container() {
        p2.keyCodes[0] = 38;
        p2.keyCodes[1] = 40;
        p1.keyCodes[0] = 87;
        p1.keyCodes[1] = 83;
        GLCanvas canvas = new GLCanvas();
        this.setLayout(new BorderLayout());
        this.add(canvas, BorderLayout.CENTER);
        canvas.addGLEventListener(this);

        animator = new FPSAnimator(canvas, REFRESH_FPS, true);

        // initializing the boundaries
        posXBound = xEDGE - ball.side / 2;
        posYBound = yEDGE - ball.side / 2;
        negXBound = -xEDGE + ball.side / 2;
        negYBound = -yEDGE + ball.side / 2;
    }

    public static void main(String[] args) {
        final int WINDOW_WIDTH = 640;  // Width of the drawable
        final int WINDOW_HEIGHT = 480; // Height of the drawable
        final String WINDOW_TITLE = "3D Shapes";

        JFrame frame = new JFrame();
        final Container joglMain = new Container();
        frame.setContentPane(joglMain);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Use a dedicate thread to run the stop() to ensure that the
                // animator stops before program exits.
                new Thread() {
                    @Override
                    public void run() {
                        joglMain.animator.stop(); // stop the animator loop
                        System.exit(0);
                    }
                }.start();
            }
        });
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setTitle(WINDOW_TITLE);
        frame.setVisible(true);
        joglMain.animator.start(); // start the animation loop
    }

    @Override
    public void display(GLAutoDrawable drawable) {

        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL2.GL_MODELVIEW);

        ball.addBall(gl, posXBound, negXBound, posYBound, negYBound, p1, p2);

        p1.addPlayer(gl, -1, xEDGE);
        p2.addPlayer(gl, 1, xEDGE);

        addLine(gl);
        displayScore(p1.score, p2.score, renderer);
        gl.glFlush();


    }

    public synchronized void moveThePlayer(int keyCode) {
        p1.moveThePlayer(keyCode, yEDGE);
        p2.moveThePlayer(keyCode, yEDGE);
    }

    public void displayScore(int p1Score, int p2Score, TextRenderer renderer) {
        String score = String.format("%03d : %03d", p1Score, p2Score);
        renderer.beginRendering(Math.round(2 * xEDGE), Math.round(2 * yEDGE));
        // optionally set the color
        renderer.setColor(1.0f, 0.2f, 0.2f, 0.8f);
        renderer.draw(score, 421, 440);
        // ... more draw commands, color changes, etc.
        renderer.endRendering();
    }

    public void addLine(GL2 gl) {
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex3d(0, yEDGE, 0);
        gl.glVertex3d(0, -yEDGE, 0);
        gl.glPopMatrix();
        gl.glEnd();
    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
        GL2 gl = glad.getGL().getGL2();

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-500, 500, 240, -240, -1, 1);
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
