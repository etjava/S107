package com.etjava.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
 
public class MyStarPanel extends JPanel implements Runnable {
    int[] xx = new int[100];
    int[] yy = new int[100];
    BufferedImage image;
    int[] fonts = new int[100];
 
    public MyStarPanel() {
        for(int i = 0; i < 100; ++i) {
            this.xx[i] = (int)(Math.random() * 800);
            this.yy[i] = (int)(Math.random() * 600);
        }
 
        try {
           // this.image = ImageIO.read(MyStarPanel.class.getResource("D:\\shu3.jpg"));
            this.image = ImageIO.read(new File("D:/shu3.jpg"));
        } catch (IOException var2) {
            var2.printStackTrace();
        }
 
    }
 
    public void paint(Graphics g) {
        g.drawImage(this.image, 0, 0, 800, 600, (ImageObserver)null);
        g.setColor(Color.WHITE);

 
        for(int i = 0; i < 100; ++i) {
            Font ft = new Font("Î¢ÈíÑÅºÚ", 1, this.fonts[i]);
            g.setFont(ft);
            g.drawString("*", this.xx[i], this.yy[i]);
        }
 
    }
 
    public void run() {
        while(true) {
            for(int i = 0; i < 100; ++i) {
                this.yy[i]++;
                if (this.yy[i] > 600) {
                    this.yy[i] = 0;
                }
 
                if (this.yy[i] > 0 && this.yy[i] < 150) {
                    this.fonts[i] = 18;
                } else if (this.yy[i] > 150 && this.yy[i] < 500) {
                    this.fonts[i] = 22;
                } else {
                    this.fonts[i] = 32;
                }
            }
 
            try {
                Thread.sleep(100);
            } catch (InterruptedException var2) {
                var2.printStackTrace();
            }
 
            this.repaint();
        }
    }
}