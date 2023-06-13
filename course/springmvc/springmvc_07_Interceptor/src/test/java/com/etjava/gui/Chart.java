package com.etjava.gui;

import java.awt.Color;
import java.awt.Font;

public class Chart implements Data{
    /**
     * ������
     * @param value: ����ϵ�����ֵ
     */
    public int value;

    public Chart(){
        clear();
    }

    /** ������ */
    public void clear(){
        value = 0;
    }

    /** ��Ӧ���ֵ�������ɫ */
    public Color getForeground(){
        /* ��Ӧ���ֵ�������ɫ
        0: 0xcdc1b4, ͬ������ɫ��ͬ����˲�����ʾ
        2, 4: BLACK, ��ɫ
        ����: WHITE, ��ɫ */
        /*return switch(value){// 14.0�﷨
            case 0 -> new Color(0xcdc1b4);
            case 2, 4 -> Color.BLACK;
            default -> Color.WHITE;
        };*/
        switch(value){
            case 0: return new Color(0xcdc1b4);
            case 2: return Color.BLACK;
            case 4: return Color.BLACK;
            default: return Color.WHITE;
        }
    }

    /** ��Ӧ���ֵı�����ɫ */
    public Color getBackground(){
        /* ��Ӧ���ֵı�����ɫ */
        /*return switch (value){
            case 0 -> new Color(0xcdc1b4);
            case 2 -> new Color(0xeee4da);
            case 4 -> new Color(0xede0c8);
            case 8 -> new Color(0xf2b179);
            case 16 -> new Color(0xf59563);
            case 32 -> new Color(0xf67c5f);
            case 64 -> new Color(0xf65e3b);
            case 128 -> new Color(0xedcf72);
            case 256 -> new Color(0xedcc61);
            case 512 -> new Color(0xedc850);
            case 1024 -> new Color(0xedc53f);
            case 2048 -> new Color(0xedc22e);
            default -> new Color(0x248c51);
        };*/
        switch (value){
            case 0: return new Color(0xcdc1b4);
            case 2: return new Color(0xeee4da);
            case 4: return new Color(0xede0c8);
            case 8: return new Color(0xf2b179);
            case 16: return new Color(0xf59563);
            case 32: return new Color(0xf67c5f);
            case 64: return new Color(0xf65e3b);
            case 128: return new Color(0xedcf72);
            case 256: return new Color(0xedcc61);
            case 512: return new Color(0xedc850);
            case 1024: return new Color(0xedc53f);
            case 2048: return new Color(0xedc22e);
            default: return new Color(0x248c51);
        }
    }

    /** ��Ӧ���ֵ������С */
    public Font getChartFont(){
       /* return switch (value){
            case 0, 2, 4, 8 -> font1;
            case 16, 32, 64 -> font2;
            case 128, 256, 512 -> font3;
            default -> font4;
        };*/
        switch (value){
            case 0: return font1;
            case 2: return font1;
            case 4: return font1;
            case 8: return font1;
            case 16: return font2;
            case 32: return font2;
            case 64: return font2;
            case 128: return font3;
            case 256: return font3;
            case 512: return font3;
            default: return font4;
        }
    }
}
