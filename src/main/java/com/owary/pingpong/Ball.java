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
public class Ball {

    public float incrX, incrY;
    public float centerX;
    public float centerY;
    public float side;
    public float paceX;
    public float paceY;
    public float scaleFactor;
    private int x = 1;
    private int y = 1;

    public Ball() {
        incrX = 0.0f;
        incrY = 0.0f;
        centerX = 0.5f;
        centerY = 0.0f;
        side = 25;//0.1f;
        paceX = 3.5f;//0.038f;
        paceY = 4;//0.027f;
        scaleFactor = 1;
    }

    public void addBall(GL2 gl, float posXBound, float negXBound, float posYBound, float negYBound, Player p1, Player p2) {
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glTranslatef(this.incrX, this.incrY, 0);
        gl.glBegin(GL2.GL_QUADS);
        this.drawSquare(gl);
        this.moveToggle();
        this.toggle(posXBound, negXBound, posYBound, negYBound, p1, p2);
        gl.glPopMatrix();
        gl.glEnd();
    }

    public void drawSquare(GL2 gl) {
        double hSide = side / 2;
        double xVertexRight = centerX + hSide;
        double xVertexLeft = centerX - hSide;
        double yVertexUp = centerY + hSide;
        double yVertexDown = centerY - hSide;

        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glVertex3d(xVertexRight, yVertexDown, 0);
        gl.glVertex3d(xVertexLeft, yVertexDown, 0);
        gl.glVertex3d(xVertexLeft, yVertexUp, 0);
        gl.glVertex3d(xVertexRight, yVertexUp, 0);
    }

    public void toggle(float posXBound, float negXBound, float posYBound, float negYBound, Player p1, Player p2) {
        float xCoord = incrX + centerX;
        float yCoord = incrY + centerY;
        p1.yUpBound = p1.height / 2 + p1.p1trY + side;
        p1.yDownBound = -p1.height / 2 + p1.p1trY - side;
        p2.yUpBound = p2.height / 2 + p2.p1trY + side;
        p2.yDownBound = -p2.height / 2 + p2.p1trY - side;

        if (xCoord >= posXBound || xCoord <= negXBound) {
            if (x == -1) {
                p2.score++;
            } else {
                p1.score++;
            }
            this.incrX = 0;
            this.incrY = 0;
//            x = -1 * x;
        }
        if (yCoord >= posYBound || yCoord <= negYBound) {
            y = -1 * y;
        }

        if (xCoord <= negXBound + 30f) {
            if (yCoord >= p1.yDownBound && yCoord <= p1.yUpBound) {
                x = -1 * x;
                System.out.println("p1 : repelled");
            }
        }

        if (xCoord >= posXBound - 30f) {
            if (yCoord >= p2.yDownBound && yCoord <= p2.yUpBound) {
                x = -1 * x;
                System.out.println("p2 : repelled");
            }
        }
//        if (xCoord >= posXBound) {
//            x = -1 * x;
//            incrX = 0;
//            incrY = 0;
//            System.out.println("1");
//        }
//        if (xCoord <= negXBound) {
//            x = -1 * x;
//            incrX = 0;
//            incrY = 0;
//            System.out.println("2");
//        }
//        if ((yCoord >= posYBound || yCoord <= negYBound)) {
//            y = -1 * y;
//        }

    }

    public void moveToggle() {
        if (x == 1) {
            incrX += paceX;
        } else {
            incrX -= paceX;
        }
        if (y == 1) {
            incrY += paceY;
        } else {
            incrY -= paceY;
        }

    }
}
