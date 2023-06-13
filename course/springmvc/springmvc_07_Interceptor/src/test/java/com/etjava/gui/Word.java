package com.etjava.gui;

import java.awt.*;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Word extends JPanel implements Runnable, KeyListener{
    int[] xx = new int[10];
    int[] yy = new int[10];
    char[] words = new char[10];
    Color[] colors = new Color[10];
    int score = 0;
    int speed = 10;

    public Word() {
        for(int i = 0; i < 10; ++i) {
            this.xx[i] = (int)(Math.random() * 800);
            this.yy[i] = (int)(Math.random() * 600);
            this.colors[i] = this.randomColor();
            this.words[i] = (char)((int)(Math.random() * 26 + 65));
        }
    }

    public Color randomColor() {
        int R = (int)(Math.random() * 255);
        int G = (int)(Math.random() * 255);
        int B = (int)(Math.random() * 255);
        Color color = new Color(R, G, B);
        return color;
    }

    public void paint(Graphics g) {
        super.paint(g);
        Font ft = new Font("微软雅黑", 1, 28);
        g.setFont(ft);
        g.drawString("分数" + this.score, 50, 50);

        for(int i = 0; i < 10; ++i) {
            g.setColor(this.colors[i]);
            g.drawString(String.valueOf(this.words[i]), this.xx[i], this.yy[i]);
        }

    }

    public void run() {
        while(true) {
            for(int i = 0; i < 10; ++i) {
                this.yy[i]++;
                if (this.yy[i] > 600) {
                    this.yy[i] = 0;
                }
            }

            try {
                Thread.sleep((long)this.speed);
            } catch (InterruptedException var2) {
                var2.printStackTrace();
            }

            this.repaint();
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        for(int i = 0; i < 10; ++i) {
            if (e.getKeyCode() == this.words[i]) {
                this.xx[i] = (int)(Math.random() * 800.0D);
                this.yy[i] = 0;
                this.words[i] = (char)((int)(Math.random() * 26 + 65));
                ++this.score;
                break;
            }
        }

        if (this.score > 5 && this.score < 10) {
            this.speed = 5;
        } else if (this.score > 10) {
            this.speed = 1;
        }

        this.repaint();
    }

    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("眼疾手快");
        Word panel = new Word();
        frame.add(panel);
        Thread t = new Thread(panel);
        t.start();
        panel.addKeyListener(panel);
        panel.setFocusable(true);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo((Component)null);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }
}