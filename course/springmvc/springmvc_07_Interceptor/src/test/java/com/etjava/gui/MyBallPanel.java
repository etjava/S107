package com.etjava.gui;
 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Component;
import javax.swing.JFrame;

 
public class MyBallPanel extends JPanel implements Runnable {
    Color color;    // 小球的颜色
    int speed = 10; // 速度
    int score = 0; // 得分
    int[] xx = new int[5]; //  小球出现的坐标
    int[] yy = new int[5]; // 小球出现的坐标
    Color[] colors = new Color[5]; // 给五个小球添加颜色
    int[] ff = new int[5]; // 初始化5个小球
 
    public MyBallPanel() {
        /*
            for循环：5个小球的一开始出现的位置和颜色
            画布的大小：800*600，小球的大小为30*30
            我们小球的起始位置xx：0 - 770      yy：0 - 570
            小球出了画布，不会完整显示
         */
        for(int i = 0; i < 5; ++i) {
            this.xx[i] = (int)(Math.random() * 770);
            this.yy[i] = (int)(Math.random() * 570);
            this.colors[i] = this.randomColor();
            this.ff[i] = (int)(Math.random() * 4);
        }
    }

    // 画笔
    /*
        小球的颜色为我们一开始设定的颜色，大小为30
        左上角的“分数”，字体为微软雅黑，大小50
     */
    public void paint(Graphics g) {
        super.paint(g);
        for(int i = 0; i < 5; ++i) {
            g.setColor(this.colors[i]);
            g.fillOval(this.xx[i], this.yy[i], 30, 30);
        }
        g.setFont(new Font("微软雅黑", 1, 28));
        g.drawString("分数：" + this.score, 50, 50);
    }
 
    public void run() {
        while(true) {
            /*
            for循环：循环5次，分别代表5个小球
            定义小球运动方向ff：
            ff：是一个长度为5的int型数组，分别存储5个小球的运动状态，即小球的运动方向。
            ff = 0, 1, 2, 3

            注：画布的坐标轴和我们数学上的不太一样，起始点O在左上角。因此ff = 0是右下方向，如下图所示。
             */
            for(int i = 0; i < 5; ++i) {
                /*
                    小球运动到边界时，需要更改小球运动状态，改变小球颜色，分数加1 ，速度减1
                    一共四个边界
                    下面我们以一个边界为例：下边界，即xx[i] > 770（画布大小为800*600）
                 */
                if (this.ff[i] == 0) {
                    this.xx[i]++;
                    this.yy[i]++;
                }
 
                if (this.ff[i] == 1) {
                    this.xx[i]++;
                    this.yy[i]--;
                }
 
                if (this.ff[i] == 2) {
                    this.xx[i]--;
                    this.yy[i]--;
                }
 
                if (this.ff[i] == 3) {
                    this.xx[i]--;
                    this.yy[i]++;
                }

                /*
                    小球撞击右边边界有两种情况：
                    一是由①撞击，那么此时小球的运动状态为ff = 0，此时我们需要改变小球的运动状态ff = 3
                    二是由②撞击，那么此时小球的运动状态为ff  = 1，此时我们需要改变小球的运动状态ff = 2
                 */
                if (this.yy[i] > 540) {
                    ++this.score;
                    --this.speed;
                    this.colors[i] = this.randomColor();
                    if (this.ff[i] == 0) {
                        this.ff[i] = 1;
                    } else {
                        this.ff[i] = 2;
                    }
                }
 
                if (this.xx[i] > 770) {
                    ++this.score;
                    --this.speed;
                    this.colors[i] = this.randomColor();
                    if (this.ff[i] == 1) {
                        this.ff[i] = 2;
                    } else {
                        this.ff[i] = 3;
                    }
                }
 
                if (this.yy[i] < 0) {
                    ++this.score;
                    --this.speed;
                    this.colors[i] = this.randomColor();
                    if (this.ff[i] == 2) {
                        this.ff[i] = 3;
                    } else {
                        this.ff[i] = 0;
                    }
                }
 
                if (this.xx[i] < 0) {
                    ++this.score;
                    --this.speed;
                    this.colors[i] = this.randomColor();
                    if (this.ff[i] == 3) {
                        this.ff[i] = 0;
                    } else {
                        this.ff[i] = 1;
                    }
                }

                // 当速度为1时，速度保持为1
                if (this.speed < 1) {
                    this.speed = 1;
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

    /*
        小球的颜色
        随机
     */
    public Color randomColor() {
        int R = (int)(Math.random() * 255);
        int G = (int)(Math.random() * 255);
        int B = (int)(Math.random() * 255);
        Color color = new Color(R, G, B);
        return color;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("球球");
        MyBallPanel panel = new MyBallPanel();
        frame.add(panel);
        Thread t = new Thread(panel);
        t.start();
        frame.setSize(800, 600);
        frame.setLocationRelativeTo((Component)null);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }
}