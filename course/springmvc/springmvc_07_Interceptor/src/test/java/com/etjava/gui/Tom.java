package com.etjava.gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Component;


public class Tom extends JPanel implements Runnable, MouseListener {
    BufferedImage back;
    BufferedImage eat;
    BufferedImage cymbal;
    BufferedImage drink;
    BufferedImage fart;
    BufferedImage pie;
    BufferedImage scratch;
    String[] paths = new String[100];
    int count = 0;
    int index = 0;
    boolean flag = false;

    public Tom() {

        try {
            this.back = ImageIO.read(Tom.class.getResource("Animations/Eat/eat_00.jpg"));
            this.eat = ImageIO.read(Tom.class.getResource("Buttons/eat.png"));
            this.cymbal = ImageIO.read(Tom.class.getResource("Buttons/cymbal.png"));
            this.drink = ImageIO.read(Tom.class.getResource("Buttons/drink.png"));
            this.fart = ImageIO.read(Tom.class.getResource("Buttons/fart.png"));
            this.pie = ImageIO.read(Tom.class.getResource("Buttons/pie.png"));
            this.scratch = ImageIO.read(Tom.class.getResource("Buttons/scratch.png"));
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(this.back, 0, 0, 350, 600, (ImageObserver)null);
        g.drawImage(this.eat, 20, 300, 50, 50, (ImageObserver)null);
        g.drawImage(this.cymbal, 20, 370, 50, 50, (ImageObserver)null);
        g.drawImage(this.drink, 20, 440, 50, 50, (ImageObserver)null);
        g.drawImage(this.fart, 265, 300, 50, 50, (ImageObserver)null);
        g.drawImage(this.pie, 265, 370, 50, 50, (ImageObserver)null);
        g.drawImage(this.scratch, 265, 440, 50, 50, (ImageObserver)null);
    }

    public void run() {
        while(true) {
            if (this.flag) {
                ++this.index;
                if (this.index >= this.count) {
                    this.index = 0;
                    this.flag = false;
                }

                try {
                    this.back = ImageIO.read(Tom.class.getResource(this.paths[this.index]));
                } catch (IOException var3) {
                    var3.printStackTrace();
                }
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException var2) {
                var2.printStackTrace();
            }

            this.repaint();
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        System.out.println(mouseX+"--"+mouseY);
        if (mouseX > 20 && mouseX < 70 && mouseY > 300 && mouseY < 350) {
            this.count = 40;
            this.updateImage("eat");
            this.flag = true;
        }

        if (mouseX > 20 && mouseX < 70 && mouseY > 370 && mouseY < 420) {
            this.count = 13;
            this.updateImage("cymbal");
            this.flag = true;
        }

        if (mouseX > 20 && mouseX < 70 && mouseY > 440 && mouseY < 490) {
            this.count = 81;
            this.updateImage("drink");
            this.flag = true;
        }

        if (mouseX > 265 && mouseX < 315 && mouseY > 300 && mouseY < 350) {
            this.count = 28;
            this.updateImage("fart");
            this.flag = true;
        }

        if (mouseX > 265 && mouseX < 315 && mouseY > 370 && mouseY < 420) {
            this.count = 24;
            this.updateImage("pie");
            this.flag = true;
        }

        if (mouseX > 265 && mouseX < 315 && mouseY > 440 && mouseY < 490) {
            this.count = 56;
            this.updateImage("scratch");
            this.flag = true;
        }

        if (mouseX > 100 && mouseX < 250 && mouseY > 100 && mouseY < 250) {
            this.count = 81;
            this.updateImage("knockout");
            this.flag = true;
        }

        if (mouseX > 120 && mouseX < 230 && mouseY > 400 && mouseY < 500) {
            this.count = 34;
            this.updateImage("stomach");
            this.flag = true;
        }

        if (mouseX > 235 && mouseX < 285 && mouseY > 450 && mouseY < 540) {
            this.count = 26;
            this.updateImage("angry");
            this.flag = true;
        }

        if (mouseX > 120 && mouseX < 170 && mouseY > 520 && mouseY < 560) {
            this.count = 30;
            this.updateImage("footRight");
            this.flag = true;
        }

        if (mouseX > 175 && mouseX < 225 && mouseY > 520 && mouseY < 560) {
            this.count = 30;
            this.updateImage("footLeft");
            this.flag = true;
        }

        this.repaint();
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void updateImage(String str) {
        for(int i = 0; i < this.count; ++i) {
            if (i < 10) {
                this.paths[i] = "Animations/" + str + "/" + str + "_0" + i + ".jpg";
            } else {
                this.paths[i] = "Animations/" + str + "/" + str + "_" + i + ".jpg";
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tom猫");
        Tom panel = new Tom();
        frame.add(panel);
        Thread t = new Thread(panel);
        t.start();
        panel.addMouseListener(panel);
        frame.setDefaultCloseOperation(3);
        frame.setSize(350, 600);
        frame.setLocationRelativeTo((Component)null);
        frame.setVisible(true);
    }
}
