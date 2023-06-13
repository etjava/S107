package com.etjava.gui.m;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BackGround {
    //��ǰ����
    private BufferedImage bgImage = null;
    //��¼��ǰ�ǵڼ�������
    private int sort;
    //�ж��Ƿ������һ������
    private boolean flag;
    //���ڴ�����е��ϰ���
    private  List<Obstacle> obstacleList = new ArrayList<>();
    //������еĵ���
    private List<Enemy> enemyList=new ArrayList<>();
    //��ʾ���
    private BufferedImage gan=null;

    //�ж�������Ƿ�����˵�λ��
    private boolean isReach=false;
    //�ж������Ƿ����
    private boolean isBase=false;
    //��ʾ�Ǳ�
    private BufferedImage tower = null;

    public BackGround() {

    }

    public BackGround(int sort, boolean flag) {
        this.sort = sort;
        this.flag = flag;
        if (flag) {
            bgImage = StaticValue.bg2;
        } else
            bgImage = StaticValue.bg;

        if (sort == 1) {
            //���Ƶ�һ�صĵ���
            for (int i = 0; i < 27; i++) {
                obstacleList.add(new Obstacle(i * 30, 420, 1, this));
            }
            for (int i = 0; i <= 120; i += 30) {
                for (int j = 0; j < 27; j++) {
                    obstacleList.add(new Obstacle(j * 30, 570 - i, 2, this));
                }
            }
            //����ש��A
            for (int i = 120; i <= 150; i += 30) {
                obstacleList.add(new Obstacle(i, 300, 7, this));
            }
            //����ש��B-F
            for (int i = 300; i <= 570; i += 30) {
                if (i == 360 || i == 390 || i == 480 || i == 510 || i == 540)
                    obstacleList.add(new Obstacle(i, 300, 7, this));
                else
                    obstacleList.add(new Obstacle(i, 300, 0, this));
            }
            //����ש��G
            for (int i = 420; i <= 450; i += 30) {
                obstacleList.add(new Obstacle(i, 240, 7, this));
            }
            //����ˮ��
            for (int i = 360; i <= 600; i += 25) {
                if (i == 360) {
                    obstacleList.add(new Obstacle(620, i, 3, this));
                    obstacleList.add(new Obstacle(645, i, 4, this));
                } else {
                    obstacleList.add(new Obstacle(620, i, 5, this));
                    obstacleList.add(new Obstacle(645, i, 6, this));
                }
            }
            //���Ƶ�һ�ص�Ģ������
            enemyList.add(new Enemy(580,385,true,1,this));
            //���Ƶ�һ�ص�ʳ�˻�����
            enemyList.add(new Enemy(635,420,true,2,328,418,this));

        }
        //�ж��Ƿ��ǵڶ���
        if(sort==2)
        {
            //���Ƶ���
            for (int i = 0; i < 27; i++) {
                obstacleList.add(new Obstacle(i * 30, 420, 1, this));
            }
            for (int i = 0; i <= 120; i += 30) {
                for (int j = 0; j < 27; j++) {
                    obstacleList.add(new Obstacle(j * 30, 570 - i, 2, this));
                }
            }
            //����ˮ��
            for (int i = 360; i <= 600; i += 25) {
                if (i == 360) {
                    obstacleList.add(new Obstacle(60, i, 3, this));
                    obstacleList.add(new Obstacle(85, i, 4, this));
                } else {
                    obstacleList.add(new Obstacle(60, i, 5, this));
                    obstacleList.add(new Obstacle(85, i, 6, this));
                }
            }
            for (int i = 330; i <= 600; i += 25) {
                if (i == 330) {
                    obstacleList.add(new Obstacle(620, i, 3, this));
                    obstacleList.add(new Obstacle(645, i, 4, this));
                } else {
                    obstacleList.add(new Obstacle(620, i, 5, this));
                    obstacleList.add(new Obstacle(645, i, 6, this));
                }
            }
            //����ש��C
            obstacleList.add(new Obstacle(300,330,0,this));
            //����ש��B��E��G
            for (int i = 270; i <=330 ; i+=30) {
               if(i==270||i==330)
                    obstacleList.add(new Obstacle(i,360,0,this));
               else
                   obstacleList.add(new Obstacle(i,360,7,this));
                }
            //����ש��A��D��F��H��I
            for (int i = 270; i <=360 ; i+=30) {
                if(i==240||i==360)
                {
                    obstacleList.add(new Obstacle(i,390,0,this));
            }else
                obstacleList.add(new Obstacle(i,390,7,this));
            }
            //����ש��1
            obstacleList.add(new Obstacle(240,300,0,this));
            //���ƿ�1-4ש��
            for (int i = 360; i <=540 ; i+=60) {
                obstacleList.add(new Obstacle(i,270,7,this));
            }
            //���Ƶڶ��ص�Ģ������
            enemyList.add(new Enemy(200,385,true,1,this));
            enemyList.add(new Enemy(500,385,true,1,this));
            //���Ƶڶ��ص�ʳ�˻�����
            enemyList.add(new Enemy(75,420,true,2,328,418,this));
            enemyList.add(new Enemy(635,420,true,2,328,418,this));
        }
        if(sort==3)
        {
            //���Ƶ���
            for (int i = 0; i < 27; i++) {
                obstacleList.add(new Obstacle(i * 30, 420, 1, this));
            }
            for (int i = 0; i <= 120; i += 30) {
                for (int j = 0; j < 27; j++) {
                    obstacleList.add(new Obstacle(j * 30, 570 - i, 2, this));
                }
            }
            //����A-Oש��
            int temp=290;
            for (int i = 390; i >=270 ; i-=30) {
                for (int j = temp; j <=410 ; j+=30) {
                    obstacleList.add(new Obstacle(j,i,7,this));
                }
                temp+=30;
            }
            //����P-Rש��
            temp=60;
            for (int i = 390; i >=360 ; i-=30) {
                for (int j = temp; j <=90 ; j+=30) {
                    obstacleList.add(new Obstacle(j,i,7,this));
                }
                temp+=30;
            }
            //�������
            gan = StaticValue.gan;
            //���ƳǱ�
            tower=StaticValue.tower;
            //������ӵ������
            obstacleList.add(new Obstacle(515,220,8,this));
            //Ģ������
            enemyList.add(new Enemy(150,385,true,1,this));
        }
    }
    public BufferedImage getBgImage() {
        return bgImage;
    }

    public int getSort() {
        return sort;
    }

    public boolean isFlag() {
        return flag;
    }

    public List<Obstacle> getObstacleList() {
        return obstacleList;
    }
    public BufferedImage getTower() {
        return tower;
    }
    public BufferedImage getGan() {
        return gan;
    }

    public boolean isBase() {
        return isBase;
    }

    public void setBase(boolean base) {
        isBase = base;
    }
    public boolean isReach() {
        return isReach;
    }

    public void setReach(boolean reach) {
        isReach = reach;
    }
    public List<Enemy> getEnemyList() {
        return enemyList;
    }

}



