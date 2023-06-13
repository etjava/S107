package com.etjava.gui;
 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Component;
import javax.swing.JFrame;

 
public class MyBallPanel extends JPanel implements Runnable {
    Color color;    // С�����ɫ
    int speed = 10; // �ٶ�
    int score = 0; // �÷�
    int[] xx = new int[5]; //  С����ֵ�����
    int[] yy = new int[5]; // С����ֵ�����
    Color[] colors = new Color[5]; // �����С�������ɫ
    int[] ff = new int[5]; // ��ʼ��5��С��
 
    public MyBallPanel() {
        /*
            forѭ����5��С���һ��ʼ���ֵ�λ�ú���ɫ
            �����Ĵ�С��800*600��С��Ĵ�СΪ30*30
            ����С�����ʼλ��xx��0 - 770      yy��0 - 570
            С����˻���������������ʾ
         */
        for(int i = 0; i < 5; ++i) {
            this.xx[i] = (int)(Math.random() * 770);
            this.yy[i] = (int)(Math.random() * 570);
            this.colors[i] = this.randomColor();
            this.ff[i] = (int)(Math.random() * 4);
        }
    }

    // ����
    /*
        С�����ɫΪ����һ��ʼ�趨����ɫ����СΪ30
        ���Ͻǵġ�������������Ϊ΢���źڣ���С50
     */
    public void paint(Graphics g) {
        super.paint(g);
        for(int i = 0; i < 5; ++i) {
            g.setColor(this.colors[i]);
            g.fillOval(this.xx[i], this.yy[i], 30, 30);
        }
        g.setFont(new Font("΢���ź�", 1, 28));
        g.drawString("������" + this.score, 50, 50);
    }
 
    public void run() {
        while(true) {
            /*
            forѭ����ѭ��5�Σ��ֱ����5��С��
            ����С���˶�����ff��
            ff����һ������Ϊ5��int�����飬�ֱ�洢5��С����˶�״̬����С����˶�����
            ff = 0, 1, 2, 3

            ע���������������������ѧ�ϵĲ�̫һ������ʼ��O�����Ͻǡ����ff = 0�����·�������ͼ��ʾ��
             */
            for(int i = 0; i < 5; ++i) {
                /*
                    С���˶����߽�ʱ����Ҫ����С���˶�״̬���ı�С����ɫ��������1 ���ٶȼ�1
                    һ���ĸ��߽�
                    ����������һ���߽�Ϊ�����±߽磬��xx[i] > 770��������СΪ800*600��
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
                    С��ײ���ұ߽߱������������
                    һ���ɢ�ײ������ô��ʱС����˶�״̬Ϊff = 0����ʱ������Ҫ�ı�С����˶�״̬ff = 3
                    �����ɢ�ײ������ô��ʱС����˶�״̬Ϊff  = 1����ʱ������Ҫ�ı�С����˶�״̬ff = 2
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

                // ���ٶ�Ϊ1ʱ���ٶȱ���Ϊ1
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
        С�����ɫ
        ���
     */
    public Color randomColor() {
        int R = (int)(Math.random() * 255);
        int G = (int)(Math.random() * 255);
        int B = (int)(Math.random() * 255);
        Color color = new Color(R, G, B);
        return color;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("����");
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