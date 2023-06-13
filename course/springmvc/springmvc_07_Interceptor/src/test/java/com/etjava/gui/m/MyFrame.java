package com.etjava.gui.m;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/*
一个类包括属性和方法两种，我们首先定义MyFrame的属性。MyFrame的作用就是绘制最终的页面，所以在这个类里面要定义页面的大小、位置等等，主要目的就是将我们定义的图片绘制到窗口中去。

在游戏中我们有三个关卡，前两个关卡的背景图和第三个关卡的背景图是不一样的，所以设置了一个数组来保存我们所有的图片信息，因为在绘制的时候总要有一个场景吧，所以设置了nowbg对象，这里的background对象就是为了保存每一个场景中的所有要放置的图片，当我们在绘制的时候直接将nowbg里保存的图片画出即可。

游戏的绘制怎么能少得了主人公马里奥呢？所以我们这个时候定义一个Mario对象，并为它设置线程对象。

属性都定义好了以后我们就开始定义构造方法，绘制好游戏窗口，为定义的变量赋值，使用重定义的repaint方法，将图片显示在屏幕上。

重写paint方法的时候，先把图片放在缓冲区，等全部缓冲好以后我们再把它们一起绘制出来。

根据马里奥的坐标判断是否已经到达了场景的终点来进行场景的切换，根据bg里的障碍物坐标进行障碍物坐标的绘制。

对于主人公来说，我们需要控制的有他的位置即x、y坐标和当前的状态，由于要走路和跳跃，我们还要判断一下马里奥是否在空中，代码中的注释也写得很详细了，就是构造一些方法，通过方法去判断状态，重点是要提前判断好各点的坐标和位置关系从而设置限定条件，进行判断。
 */
public class MyFrame extends JFrame implements KeyListener ,Runnable{
    //存储所有背景
    private List<BackGround> allBg = new ArrayList<>();
    //存储当前背景
    private BackGround nowBg = new BackGround();
    //双存储
    private Image offScreenImage=null;
    //马里奥对象
    private Mario mario=new Mario();
    //线程对象，用于实现马里奥的运动
    private Thread thread =new Thread(this);
public MyFrame(){
    //窗口大小
    this.setSize(800,600);
    //窗口居中显示
    this.setLocationRelativeTo(null);
    //设置窗口可见性
    this.setVisible(true);
    //设置点击窗口上的关闭键，结束程序
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //设置窗口大小不可变
    this.setResizable(false);
    //向窗口对象添加键盘监听器
    this.addKeyListener(this);
    //设置窗口名称
    this.setTitle("马里奥");
    StaticValue.init();
    //初始化马里奥
    mario=new Mario(10,355);
    //创建所有场景
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
        Graphics graphics = offScreenImage.getGraphics();//画布
        graphics.fillRect(0,0,800,600);
        //把图片绘制到缓冲区上
        graphics.drawImage(nowBg.getBgImage(), 0,0,this);
        //绘制敌人
        for(Enemy ob:nowBg.getEnemyList()){
            graphics.drawImage(ob.getShow(),ob.getX(),ob.getY(),this);
        }
        //绘制障碍物
        for(Obstacle ob: nowBg.getObstacleList())
            graphics.drawImage(ob.getShow(),ob.getX(),ob.getY(),this);
        //绘制城堡
        graphics.drawImage(nowBg.getTower(),620,270,this);
        //绘制旗杆
        graphics.drawImage(nowBg.getGan(), 500,220,this);
        //绘制马里奥
        graphics.drawImage(mario.getShow(),mario.getX(),mario.getY(),this);
        //绘制分数
        Color c=graphics.getColor();
        graphics.setColor(Color.black);
        graphics.setFont(new Font("黑体",Font.BOLD,25));
        graphics.drawString("当前的分数为"+mario.getScore(),300,100);
        graphics.setColor(c);
        //把缓冲区的图片绘制到窗口中
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
        //向右移动
        if(e.getKeyCode()==39){
            mario.rightMove();
        }
        //向左移动
        if(e.getKeyCode()==37)
            mario.leftMove();
        //跳跃
       if(e.getKeyCode()==38){
            mario.jump();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
         //向左停止
        if(e.getKeyCode()==37)
        {
            mario.leftStop();
        }
         //向右停止
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
                //判断马里奥是否死亡
                if(mario.isDeath())
                {
                    JOptionPane.showMessageDialog(this,"马里奥死亡");
                    System.exit(0);
                }
                //判断游戏是否结束
                if(mario.isOK()){
                    JOptionPane.showMessageDialog(this,"恭喜过关");
                    System.exit(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
