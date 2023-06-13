package com.etjava.gui.m;

import java.awt.image.BufferedImage;

public class Obstacle implements Runnable{
    //��ʾ��ǰ����
    private int x;
    private int y;
    //��ʾ��¼�ϰ�������
    private int type;
    //������ʾͼ��
    private BufferedImage show = null;
    //���嵱ǰ�ĳ�������
    private  BackGround bg = null;
    //���嵱ǰ�ĳ�������
    private Thread thread=new Thread(this);
    public Obstacle(int x,int y,int type,BackGround bg)
    {
        this.x=x;
        this.y=y;
        this.type=type;
        this.bg=bg;
        this.show=StaticValue.obstacle.get(type);
        if(type==8)
        {
            thread.start();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }

    public BufferedImage getShow() {
        return show;
    }

    @Override
    public void run() {
    while(true) {
        if(this.bg.isReach())
        {
            if(this.y<374)
                this.y+=5;
            else
                this.bg.setBase(true);

        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    }
}

