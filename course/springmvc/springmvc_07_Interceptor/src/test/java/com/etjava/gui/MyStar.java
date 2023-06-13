package com.etjava.gui;


import java.awt.Color;
import java.awt.Component;
import javax.swing.JFrame;

public class MyStar {

    public static void main(String[] args) {
        JFrame frame = new JFrame("ÂúÌìÐÇ");
        MyStarPanel panel = new MyStarPanel();
        frame.add(panel);
        Thread t = new Thread(panel);
        t.start();
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setLocationRelativeTo((Component)null);
        frame.setDefaultCloseOperation(3);
        frame.setBackground(Color.BLACK);
    }
}