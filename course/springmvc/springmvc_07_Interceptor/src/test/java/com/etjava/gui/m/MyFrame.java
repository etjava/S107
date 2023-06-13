package com.etjava.gui.m;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/*
һ����������Ժͷ������֣��������ȶ���MyFrame�����ԡ�MyFrame�����þ��ǻ������յ�ҳ�棬���������������Ҫ����ҳ��Ĵ�С��λ�õȵȣ���ҪĿ�ľ��ǽ����Ƕ����ͼƬ���Ƶ�������ȥ��

����Ϸ�������������ؿ���ǰ�����ؿ��ı���ͼ�͵������ؿ��ı���ͼ�ǲ�һ���ģ�����������һ�������������������е�ͼƬ��Ϣ����Ϊ�ڻ��Ƶ�ʱ����Ҫ��һ�������ɣ�����������nowbg���������background�������Ϊ�˱���ÿһ�������е�����Ҫ���õ�ͼƬ���������ڻ��Ƶ�ʱ��ֱ�ӽ�nowbg�ﱣ���ͼƬ�������ɡ�

��Ϸ�Ļ�����ô���ٵ������˹�������أ������������ʱ����һ��Mario���󣬲�Ϊ�������̶߳���

���Զ���������Ժ����ǾͿ�ʼ���幹�췽�������ƺ���Ϸ���ڣ�Ϊ����ı�����ֵ��ʹ���ض����repaint��������ͼƬ��ʾ����Ļ�ϡ�

��дpaint������ʱ���Ȱ�ͼƬ���ڻ���������ȫ��������Ժ������ٰ�����һ����Ƴ�����

��������µ������ж��Ƿ��Ѿ������˳������յ������г������л�������bg����ϰ�����������ϰ�������Ļ��ơ�

�������˹���˵��������Ҫ���Ƶ�������λ�ü�x��y����͵�ǰ��״̬������Ҫ��·����Ծ�����ǻ�Ҫ�ж�һ��������Ƿ��ڿ��У������е�ע��Ҳд�ú���ϸ�ˣ����ǹ���һЩ������ͨ������ȥ�ж�״̬���ص���Ҫ��ǰ�жϺø���������λ�ù�ϵ�Ӷ������޶������������жϡ�
 */
public class MyFrame extends JFrame implements KeyListener ,Runnable{
    //�洢���б���
    private List<BackGround> allBg = new ArrayList<>();
    //�洢��ǰ����
    private BackGround nowBg = new BackGround();
    //˫�洢
    private Image offScreenImage=null;
    //����¶���
    private Mario mario=new Mario();
    //�̶߳�������ʵ������µ��˶�
    private Thread thread =new Thread(this);
public MyFrame(){
    //���ڴ�С
    this.setSize(800,600);
    //���ھ�����ʾ
    this.setLocationRelativeTo(null);
    //���ô��ڿɼ���
    this.setVisible(true);
    //���õ�������ϵĹرռ�����������
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //���ô��ڴ�С���ɱ�
    this.setResizable(false);
    //�򴰿ڶ�����Ӽ��̼�����
    this.addKeyListener(this);
    //���ô�������
    this.setTitle("�����");
    StaticValue.init();
    //��ʼ�������
    mario=new Mario(10,355);
    //�������г���
    for (int i = 1; i <=3 ; i++) {
        allBg.add(new BackGround(i,i==3?true:false));
    }
    nowBg=allBg.get(0);
    mario.setBackGround(nowBg);
    repaint();
    thread.start();
}

    @Override
    public void paint(Graphics g) {
        if(offScreenImage==null)
        {
            offScreenImage=createImage(800,600);
        }
        Graphics graphics = offScreenImage.getGraphics();//����
        graphics.fillRect(0,0,800,600);
        //��ͼƬ���Ƶ���������
        graphics.drawImage(nowBg.getBgImage(), 0,0,this);
        //���Ƶ���
        for(Enemy ob:nowBg.getEnemyList()){
            graphics.drawImage(ob.getShow(),ob.getX(),ob.getY(),this);
        }
        //�����ϰ���
        for(Obstacle ob: nowBg.getObstacleList())
            graphics.drawImage(ob.getShow(),ob.getX(),ob.getY(),this);
        //���ƳǱ�
        graphics.drawImage(nowBg.getTower(),620,270,this);
        //�������
        graphics.drawImage(nowBg.getGan(), 500,220,this);
        //���������
        graphics.drawImage(mario.getShow(),mario.getX(),mario.getY(),this);
        //���Ʒ���
        Color c=graphics.getColor();
        graphics.setColor(Color.black);
        graphics.setFont(new Font("����",Font.BOLD,25));
        graphics.drawString("��ǰ�ķ���Ϊ"+mario.getScore(),300,100);
        graphics.setColor(c);
        //�ѻ�������ͼƬ���Ƶ�������
        g.drawImage(offScreenImage,0,0,this);
    }

    public static void main(String[] args) {
        MyFrame myFrame=new MyFrame();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //�����ƶ�
        if(e.getKeyCode()==39){
            mario.rightMove();
        }
        //�����ƶ�
        if(e.getKeyCode()==37)
            mario.leftMove();
        //��Ծ
       if(e.getKeyCode()==38){
            mario.jump();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
         //����ֹͣ
        if(e.getKeyCode()==37)
        {
            mario.leftStop();
        }
         //����ֹͣ
        if(e.getKeyCode()==39)
        {
            mario.rightStop();
        }
    }

    @Override
    public void run() {
        while(true){
            repaint();
            try {
                Thread.sleep(50);
                if(mario.getX()>=750){
                    nowBg=allBg.get(nowBg.getSort());
                    mario.setBackGround(nowBg);
                    mario.setX(10);
                    mario.setY(355);
                }
                //�ж�������Ƿ�����
                if(mario.isDeath())
                {
                    JOptionPane.showMessageDialog(this,"���������");
                    System.exit(0);
                }
                //�ж���Ϸ�Ƿ����
                if(mario.isOK()){
                    JOptionPane.showMessageDialog(this,"��ϲ����");
                    System.exit(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
