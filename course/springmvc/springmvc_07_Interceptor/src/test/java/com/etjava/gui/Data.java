package com.etjava.gui;


import java.awt.Font;

public interface Data {
    Font title = new Font("微软雅黑", Font.BOLD, 50); // 窗口标题
    Font score = new Font("微软雅黑", Font.BOLD, 28); // 分数
    Font tips = new Font("宋体", Font.PLAIN, 20); // 说明

    /* font1: 数字2, 4, 8
    font2: 数字16, 32, 64
    font3: 数字128, 256, 512
    font4: 数字1024, 2048, 4096, 8192
    * */
    Font font1 = new Font("宋体", Font.BOLD, 46);
    Font font2 = new Font("宋体", Font.BOLD, 40);
    Font font3 = new Font("宋体", Font.BOLD, 34);
    Font font4 = new Font("宋体", Font.BOLD, 28);

    int CHART_GAP = 10;
    int CHART_ARC = 20;
    int CHART_SIZE = 86;
}
