package com.etjava.gui;


import java.awt.Font;

public interface Data {
    Font title = new Font("΢���ź�", Font.BOLD, 50); // ���ڱ���
    Font score = new Font("΢���ź�", Font.BOLD, 28); // ����
    Font tips = new Font("����", Font.PLAIN, 20); // ˵��

    /* font1: ����2, 4, 8
    font2: ����16, 32, 64
    font3: ����128, 256, 512
    font4: ����1024, 2048, 4096, 8192
    * */
    Font font1 = new Font("����", Font.BOLD, 46);
    Font font2 = new Font("����", Font.BOLD, 40);
    Font font3 = new Font("����", Font.BOLD, 34);
    Font font4 = new Font("����", Font.BOLD, 28);

    int CHART_GAP = 10;
    int CHART_ARC = 20;
    int CHART_SIZE = 86;
}
