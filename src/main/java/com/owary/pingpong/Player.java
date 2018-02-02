/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.owary.pingpong;

import com.jogamp.opengl.GL2;

/**
 * @author OwaryLtd
 */
public class Player {

    public float height;

    public float currentXMax;
    public float currentXMin;
    public float currentYMax;
    public float currentYMin;

    public double yUpBound;
    public double yDownBound;

    public float p1trX;
    public float p1trY;

    public int[] keyCodes;

    public int score;

    public Player() {
        height = 80f;
        currentXMax = 0.0f;
        currentXMin = 0.0f;
        currentYMax = height / 2;
        currentYMin = -height / 2;
        yUpBound = 0;
        yDownBound = 0;
        p1trX = 0.0f;
        p1trY = 0.0f;
        keyCodes = new int[2];
        score = 0;
    }

    public void addPlayer(GL2 gl, int side, float xEDGE) {
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glTranslatef(this.p1trX, this.p1trY, 0);
        gl.glBegin(GL2.GL_QUADS);
        this.drawPlayer(30f, side, xEDGE, gl);
        gl.glPopMatrix();
        gl.glEnd();
    }

    public void drawPlayer(float side, int direction, float xEDGE, GL2 gl) {
        float bound = direction * xEDGE;
        float side_ = -1 * direction * side;
        float foo = direction * height / 2;

        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glVertex2f(bound + side_, foo);
        System.out.println("Bound : " + bound);
        gl.glVertex2f(bound, foo);
        gl.glVertex2f(bound, -foo);
        gl.glVertex2f(bound + side_, -foo);
        gl.glEnd();
        
        /*
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, 0);
        gl.glBegin(GL2.GL_LINES);
        gl.glColor3f(1.0f, 0.0f, 1.0f);
        gl.glVertex2d(direction*500, this.yUpBound);
        gl.glVertex2d(0, this.yUpBound);

        gl.glVertex2d(direction*500, this.yDownBound);
        gl.glVertex2d(0, this.yDownBound);
        gl.glPopMatrix();
        gl.glEnd();
        */

//        System.out.println("yUP : " + this.yUpBound + ", yDown : " + this.yDownBound);

        gl.glFlush();

    }

    public synchronized void moveThePlayer(int keyCode, float yEDGE) {
        int btn1 = this.keyCodes[1];
        int btn2 = this.keyCodes[0];

        double y = p1trY;
        float halfHeight = height / 2;
        float speed = 10f;

        if (keyCode == btn1) {
            if (y <= yEDGE - halfHeight) {
                p1trY += speed;
//                this.currentYMax += speed;
//                this.currentYMin += speed;
            }
        } else if (keyCode == btn2) {
            if (y >= -yEDGE + halfHeight) {
                p1trY -= speed;
//                this.currentYMax -= speed;
//                this.currentYMin -= speed;
            }
        } else {

        }
    }
}
