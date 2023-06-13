package com.etjava.gui.m;

import java.awt.image.BufferedImage;

public class Mario implements  Runnable{
    //���ڴ洢������
    private int x;
    //���ڴ洢������
    private int y;
    //�洢��ʾ����µ�ǰ��״̬
    private String status;
    //������ʾ��ǰ״̬��Ӧ��ͼ��
    private BufferedImage show =null;
    //����һ��BackGround�������ڻ�ȡ�ϰ�����Ϣ
    private BackGround backGround=new BackGround();
    //����ʵ������µĶ���
    private Thread thread=null;
    //����µ��ƶ��ٶ�
    private int xSpeed;
    //����µ���Ծ�ٶ�
    private int ySpeed;
    //ȡ������µ��˶�ͼ��
    private int index;
    //��ʾ����µ�����ʱ��
    private int upTime=0;



    //��ʾ����
    private int  score=0;

    //�ж�������Ƿ�����
    private boolean isDeath=false;
    //�ж�������Ƿ��ߵ��˳Ǳ����ſ�
    private boolean isOK;
    public Mario(){
    }
    public void death()
    {
        isDeath=true;
    }
    public Mario(int x,int y){
        this.x=x;
        this.y=y;
        show=StaticValue.stand_R;
        this.status="stand--right";
        thread=new Thread(this);
        thread.start();
    }
    //����������ƶ�
    public void leftMove(){
        xSpeed=-5;
        //�ж������1�Ƿ�����������
        if(backGround.isReach())
            xSpeed=0;
        //�ж�������Ƿ��ڿ���
        if(status.indexOf("jump")!=-1){
            status="jump--left";
        }else
        {
            status="move--left";
        }
    }

    //����������ƶ�
    public void rightMove(){
        xSpeed=5;
        //�ж������1�Ƿ�����������
        if(backGround.isReach())
            xSpeed=0;
        //�ж�������Ƿ��ڿ���
        if(status.indexOf("jump")!=-1){
            status="jump--right";
        }else
        {
            status="move--right";
        }
    }

    //���������ֹͣ
    public void leftStop(){
        xSpeed=0;
        //�ж�������Ƿ��ڿ���
        if(status.indexOf("jump")!=-1){
            status="jump--left";
        }else
        {
            status="stop--left";
        }
    }

    //���������ֹͣ
    public void rightStop(){
        xSpeed=0;
        //�ж�������Ƿ��ڿ���
        if(status.indexOf("jump")!=-1){
            status="jump--right";
        }else
        {
            status="stop--right";
        }
    }

    //�������Ծ
  public void jump(){

        if(status.indexOf("jump")==-1){
            if(status.indexOf("left")!=-1)
            {
                status="jump--left";
            }else
                status="jump--right";
            ySpeed=-10;
            upTime=7;
            //�ж������1�Ƿ�����������
            if(backGround.isReach())
                ySpeed=0;
        }
    }
    //���������
    public void fall(){
        if(status.indexOf("left")!=-1){
            status="jump--left";
        }else{
            status="jump--right";}
        ySpeed=10;
    }
    @Override
    public void run() {
    while(true){
        //�ж��Ƿ����ϰ�����
        boolean onObstacle=false;
        //�ж��Ƿ����������
        boolean canRight=true;
        //�ж��Ƿ����������
        boolean canleft=true;
        //�ж�������Ƿ񵽴����λ��
        if(backGround.isFlag()&&this.x>=500)
        {
         this.backGround.setReach(true);

         //�ж������Ƿ��������
            if(this.backGround.isBase()){
                status="move--right";
                if(x<690){
                    x+=5;
                }else
                {
                   isOK=true;
                }
            }else{
                if(y<395){
                    xSpeed=0;
                this.y+=5;
                status="jump--right";
                }
                if(y>395){
                    this.y=395;
                    status="stop--right";
                }
            }
        }else {
            //������ǰ���������е��ϰ���
            for (int i = 0; i < backGround.getObstacleList().size(); i++) {
                Obstacle ob = backGround.getObstacleList().get(i);
                //�ж�������Ƿ�λ���ϰ�����
                if (ob.getY() == this.y + 25 && (ob.getX() > this.x - 30) && (ob.getX() < this.x + 25)) {
                    onObstacle = true;
                }
                //�ж��Ƿ�����������ש��
                if (ob.getY() >= this.y - 30 && (ob.getY() <= this.y - 20) && (ob.getX() > this.x - 30) && (ob.getX() < this.x + 25)) {
                    if (ob.getType() == 0) {
                        backGround.getObstacleList().remove(ob);
                        upTime = 0;
                        score+=1;
                    }
                }
                //�ж��Ƿ����������
                if (ob.getX() == this.x + 25 && (ob.getY() > this.y - 30) && (ob.getY() < this.y + 25)) {
                    canRight = false;
                }
                //�ж��Ƿ����������
                if (ob.getX() == this.x - 30 && (ob.getY() > this.y - 30) && (ob.getY() < this.y + 25))
                    canleft = false;

            }
            //�ж�������Ƿ����������������߲���Ģ������
            for(int i=0;i<backGround.getEnemyList().size();i++)
            {
                Enemy e=backGround.getEnemyList().get(i);
                if(e.getY()==this.y+20&&(e.getX()-25<this.x)&&(e.getX()+35>=this.x))
                {
                    if(e.getType()==1)
                    {
                        e.death();
                        upTime=3;
                        ySpeed=-10;
                        score+=2;
                    }
                    else if(e.getType()==2)
                    {
                        //�������
                        death();
                    }
                }
                if(e.getX()+35>this.x&&e.getX()-25<this.x&&e.getY()+35>this.y&&e.getY()-20<this.y)
                {
                    //�������
                        death();
                }
            }
            //�����������Ծ�Ĳ���
            if (onObstacle && upTime == 0) {
                if (status.indexOf("left") != -1) {
                    if (xSpeed != 0) {
                        status = "move--left";
                    } else
                        status = "stop--left";
                } else {
                    if (xSpeed != 0) {
                        status = "move--right";
                    } else
                        status = "stop--right";
                }
            } else {
                if (upTime != 0) upTime--;
                else fall();
                y += ySpeed;
            }
        }
        if((canleft&&xSpeed<0)||(canRight&&xSpeed>0)) {
            x += xSpeed;
            //�ж�������Ƿ��˶�������Ļ�������
            if (x < 0)
                x = 0;
        }
            //�жϵ�ǰ�Ƿ����ƶ�״̬
            if(status.contains("move")){
                index=index==0?1:0;
            }
            //�ж��Ƿ������ƶ�
            if("move--left".equals(status)){
                show=StaticValue.run_L.get(index);
            }
            //�ж��Ƿ������ƶ�
            if("move--right".equals(status)){
                show=StaticValue.run_R.get(index);
            }
            //�ж��Ƿ�����ֹͣ
            if("stop--left".equals(status)){
                show=StaticValue.stand_L;
            }
            //�ж��Ƿ�����ֹͣ
            if("stop--right".equals(status)){
                show=StaticValue.stand_R;
            }
            //�ж��Ƿ�������Ծ
            if("jump--left".equals(status))
            {
                show=StaticValue.jump_L;
            }
            //�ж��Ƿ�������Ծ
            if("jump--right".equals(status))
            {
                show=StaticValue.jump_R;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
     }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public int getScore() {
        return score;
    }

    public void setShow(BufferedImage show) {
        this.show = show;
    }

    public BufferedImage getShow() {
        return show;
    }
    public void setBackGround(BackGround backGround) {
        this.backGround = backGround;
    }
    public boolean isOK() {
        return isOK;
    }
    public boolean isDeath() {
        return isDeath;
    }
}

