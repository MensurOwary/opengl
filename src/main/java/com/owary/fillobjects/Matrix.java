/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.owary.fillobjects;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author OwaryLtd
 */
public class Matrix {

    private Point[][] pArr;
    private List<Set<Point>> list;

    public Matrix(int[][] arr) {
        list = new LinkedList<>();
        pArr = new Point[10][10];
        for (int y = 0; y < arr.length; y++) {
            for (int x = 0; x < arr.length; x++) {
                pArr[y][x] = new Point(x, y, arr[y][x]);
            }
        }
    }

    public Point[][] getpArr() {
        return pArr;
    }

    public List<Set<Point>> getList() {
        return list;
    }

    public Point right(Point p) {
        return pArr[p.getY()][p.getX() + 1];
    }

    public Point bottom(Point p) {
        return pArr[p.getY() + 1][p.getX()];
    }

    public Point up(Point p) {
        return pArr[p.getY() - 1][p.getX()];
    }

    public Point left(Point p) {
        return pArr[p.getY()][p.getX() - 1];
    }

    public void search() {
        for (int y = 0; y < pArr.length; y++) {
            for (int x = 0; x < pArr.length; x++) {
                Point p = pArr[y][x];
                if (p.isMarked()) {
                    Set<Point> s = new HashSet<>();
                    fillTheObject(p, s);
                    list.add(s);
                }
            }
        }
    }

    public void fillTheObject(Point p, Set s) {
        if (p.isMarked()) {
            p.fill();
            // add object to the set here
            s.add(p);
            if (p.getX() != 9) {
                fillTheObject(this.right(p), s);
            }
            if (p.getY() != 9) {
                fillTheObject(this.bottom(p), s);
            }
            if (p.getX() != 0) {
                fillTheObject(this.left(p), s);
            }
            if (p.getY() != 0) {
                fillTheObject(this.up(p), s);
            }
        }
    }

}
