/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.owary.fillobjects;

/**
 * @author OwaryLtd
 */
public class Point {

    private int x;
    private int y;
    private int magnitude;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, int magnitude) {
        this.x = x;
        this.y = y;
        this.magnitude = magnitude;
    }

    public boolean isMarked() {
        return this.magnitude == 1;
    }

    public void fill() {
        this.magnitude = 2;
    }

    public Point right() {
        return new Point(x + 1, y);
    }

    public Point bottom() {
        return new Point(x, y + 1);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(int magnitude) {
        this.magnitude = magnitude;
    }

    @Override
    public String toString() {
        return "{" + "x=" + x + ", y=" + y + ", m=" + magnitude + '}';
    }


}
