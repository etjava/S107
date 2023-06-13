package com.etjava.gui;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.FontMetrics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// 游戏面板需要对键盘进行监听，因此需要实现接口 KeyListener 中的 keyPressed 方法
public class Game2048 extends JPanel implements Data, KeyListener {
    /* 变量定义 */
    private static int scores = 0;
    private Chart[][] charts = new Chart[4][4]; // 游戏面板
    private boolean isadd = true;  // 是否新增数字

    /** 构造方法 */
    public Game2048() {
        initGame();  // 初始化
    }

    /** 按下某个键时调用此方法 */
    @Override
    public void keyPressed(KeyEvent e) {
        /* getKeyCode():键盘上每一个按钮都有对应码(Code),可用来查知用户按了什么键，返回当前按钮的数值
           getKeyChar():处理的是比较高层的事件，返回的是每欠敲击键盘后得到的字符（中文输入法下就是汉字）
           getKeyText():返回与此事件中的键关联的字符。比如getKeyText(e.getKeyCode())就返回你所按下的键盘*/

        switch (e.getKeyCode()){
            /* VK_ESCAPE: Esc
               VK_UP: ↑
               VK_DOWN: ↓
               VK_LEFT: ←
               VK_RIGHT: →
            */
            // 重新开始游戏
            case KeyEvent.VK_ESCAPE:
                System.out.println("esc");
                initGame(); // 重新开始游戏，游戏初始化
                break;
            // ↑
            case KeyEvent.VK_UP:
//                System.out.println("up");
                MoveUp();  // 向左移动
                creatChart();  // 随机生成数字
//                judgeGameOver(); // 判断游戏是否结束
                break;
            // ↓
            case KeyEvent.VK_DOWN:
//                System.out.println("down");
                MoveDown();  // 向右移动
                creatChart();  // 随机生成数字
//                judgeGameOver(); // 判断游戏是否结束
                break;
            // ←
            case KeyEvent.VK_LEFT:
//                System.out.println("left");
                MoveLeft();  // 向左移动
                creatChart();  // 随机生成数字
//                judgeGameOver(); // 判断游戏是否结束
                break;
            // →
            case KeyEvent.VK_RIGHT:
//                System.out.println("right");
                MoveRight();  // 向右移动
                creatChart();  // 随机生成数字
//                judgeGameOver(); // 判断游戏是否结束
                break;
            // others
            default:
                break;
        }
        // paint()方法用来绘制图形，repaint()方法用来重新绘制图像
        repaint();
    }

    /** 游戏初始化 */
    private void initGame(){
        scores = 0;
        System.out.println("initGame");
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                charts[row][col] = new Chart();  // from Chart.java
            }
        }
        // 随机生成两个数
        for (int i = 0; i < 2; i++){
            isadd = true;
            creatChart();  // 随机生成一个数字
        }
    }

    /** 随机在一个位置生成一个数 */
    private void creatChart(){
        List<Chart> list = getEmptyCharts();  // list 为空白方格

        if (!list.isEmpty() && isadd) {  // 在空白方格出随机生成一个数字
            Random random = new Random();
            int index = random.nextInt(list.size());
            Chart chart = list.get(index);
            // 2, 4出现概率3:1
            /* random.nextInt(int n) 是参数 [0, n) 的随机数 */
            /* random.nextInt(4): 随机生成 0, 1, 2, 3; */
            chart.value = (random.nextInt(4) % 3 == 0) ? 2 : 4;
            System.out.println(chart.value);
            isadd = false;
        }
    }

    /** 获取空白方格 */
    private List<Chart> getEmptyCharts(){
        List<Chart> chartList = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (charts[i][j].value == 0){
                    chartList.add(charts[i][j]);  //添加元素到 ArrayList 可以使用 add() 方法
                }
            }
        }
        return chartList;
    }

    /** 向上移动 */
    private boolean MoveUp() {
//        System.out.println("MoveUp");
        /* 向上移动，只需考虑第二行到第四行
           共分为两种情况:
           1、当前数字上边无空格，即上边值不为 0
              a. 当前数字与上边数字相等，合并
              b. 当前数字与上边数字不相等，continue
           2、当前数字上边有空格，即上边值为 0， 上移 */
        for (int j = 0; j < 4; j++) {
            for (int i = 1, index = 0; i < 4; i++) {
                if (charts[i][j].value > 0) {
                    if (charts[i][j].value == charts[index][j].value) {
                        // 当前数字 == 上边数字
                    /* 分数: 当前数字 + 上边数字
                       数值: 上边数字 = 上边数字 + 当前数字, 当前数字 = 0 */
                        scores += charts[i][j].value + charts[index][j].value;
                        charts[index][j].value = charts[i][j].value + charts[index][j].value;
                        charts[i][j].value = 0;
                        index += 1;
                        isadd = true;
                    }
                    // 当前数字与上边数字不相等，continue 可以省略不写
                    else if (charts[index][j].value == 0) {
                        // 当前数字上边有0
                    /* 分数: 不变
                       数值: 上边数字 = 当前数字, 当前数字 = 0 */
                        charts[index][j].value = charts[i][j].value;
                        charts[i][j].value = 0;
                        isadd = true;
                    }
                    else if (charts[++index][j].value == 0) {
                        // index 相当于慢指针，j 相当于快指针
                        // 也就是说快指针和慢指针中间可能存在一个以上的空格，或者index和j并未相邻
                        // 上边数字 = 0
                    /* 分数: 不变
                       数值: 上边数字 = 当前数字, 当前数字 = 0 */
                        charts[index][j].value = charts[i][j].value;
                        charts[i][j].value = 0;
                        isadd = true;
                    }
                }
            }
        }
        return isadd;
    }

    /** 向下移动 */
    private boolean MoveDown(){
        System.out.println("MoveDown");
        /* 向下移动，只需考虑第一列到第三列
           共分为两种情况:
           1、当前数字下边无空格，即下边值不为 0
              a. 当前数字与下边数字相等，合并
              b. 当前数字与下边数字不相等，continue
           2、当前数字下边有空格，即下边值为 0， 下移 */
        for (int j = 0; j < 4; j++) {
            for (int i = 2, index = 3; i >= 0; i--) {
                if (charts[i][j].value > 0) {
                    if (charts[i][j].value == charts[index][j].value) {
                        // 当前数字 == 下边数字
                        /* 分数: 当前数字 + 下边数字
                           数值: 下边数字 = 下边数字 + 当前数字, 当前数字 = 0 */
                        scores += charts[i][j].value + charts[index][j].value;
                        charts[index][j].value = charts[i][j].value + charts[index][j].value;
                        charts[i][j].value = 0;
                        index -= 1;
                        isadd = true;
                    }
                    // 当前数字与下边数字不相等，continue 可以省略不写
                    else if (charts[index][j].value == 0) {
                        // 当前数字下边有0
                        /* 分数: 不变
                           数值: 下边数字 = 当前数字, 当前数字 = 0 */
                        charts[index][j].value = charts[i][j].value;
                        charts[i][j].value = 0;
                        isadd = true;
                    }
                    else if (charts[--index][j].value == 0) {
                        // index 相当于慢指针，j 相当于快指针
                        // 也就是说快指针和慢指针中间可能存在一个以上的空格，或者index和j并未相邻
                        // 下边数字 = 0
                        /* 分数: 不变
                           数值: 下边数字 = 当前数字, 当前数字 = 0 */
                        charts[index][j].value = charts[i][j].value;
                        charts[i][j].value = 0;
                        isadd = true;
                    }
                }
            }
        }
        return isadd;
    }

    /** 向左移动 */
    private boolean MoveLeft(){
        //        System.out.println("MoveLeft");
        /* 向左移动，只需考虑第二列到第四列
           共分为两种情况:
           1、当前数字左边无空格，即左边值不为 0
              a. 当前数字与左边数字相等，合并
              b. 当前数字与左边数字不相等，continue
           2、当前数字左边有空格，即左边值为 0， 左移 */
        for (int i = 0; i < 4; i++) {
            for (int j = 1, index = 0; j < 4; j++) {
                if (charts[i][j].value > 0) {
                    if (charts[i][j].value == charts[i][index].value) {
                        // 当前数字 == 左边数字
                        /* 分数: 当前数字 + 左边数字
                           数值: 左边数字 = 左边数字 + 当前数字, 当前数字 = 0 */
                        scores += charts[i][j].value + charts[i][index].value;
                        charts[i][index].value = charts[i][index].value + charts[i][j].value;
                        charts[i][j].value = 0;
                        index += 1;
                        isadd = true;
                    }
                    // 当前数字与左边数字不相等，continue 可以省略不写
                    else if (charts[i][index].value == 0) {
                        // 当前数字左边有0
                        /* 分数: 不变
                           数值: 左边数字 = 当前数字, 当前数字 = 0 */
                        charts[i][index].value = charts[i][j].value;
                        charts[i][j].value = 0;
                        isadd = true;
                    }
                    else if (charts[i][++index].value == 0) {
                        // index 相当于慢指针，j 相当于快指针
                        // 也就是说快指针和慢指针中间可能存在一个以上的空格，或者index和j并未相邻
                        // 左边数字 = 0
                        /* 分数: 不变
                           数值: 左边数字 = 当前数字, 当前数字 = 0 */
                        charts[i][index].value = charts[i][j].value;
                        charts[i][j].value = 0;
                        isadd = true;
                    }
                }
            }
        }

        return isadd;
    }

    /** 向右移动 */
    private boolean MoveRight(){
        //        System.out.println("MoveRight");
      /* 向右移动，只需考虑第一列到第三列
         共分为两种情况:
         1、当前数字右边无空格，即右边值不为 0
            a. 当前数字与右边数字相等，合并
            b. 当前数字与右边数字不相等，continue
         2、当前数字右边有空格，即右边值为 0， 右移 */
        for (int i = 0; i < 4; i++) {
            for (int j = 2, index = 3; j >= 0; j--) {
                if (charts[i][j].value > 0) {
                    if (charts[i][j].value == charts[i][index].value) {
                        // 当前数字 == 右边数字
                        /* 分数: 当前数字 + 右边数字
                           数值: 右边数字 = 右边数字 + 当前数字, 当前数字 = 0 */
                        scores += charts[i][j].value + charts[i][index].value;
                        charts[i][index].value = charts[i][j].value + charts[i][index].value;
                        charts[i][j].value = 0;
                        index -= 1;
                        isadd = true;
                    }
                    // 当前数字与左边数字不相等，continue 可以省略不写
                    else if (charts[i][index].value == 0) {
                        // 当前数字右边有0
                        /* 分数: 不变
                           数值: 右边数字 = 当前数字, 当前数字 = 0 */
                        charts[i][index].value = charts[i][j].value;
                        charts[i][j].value = 0;
                        isadd = true;
                    }
                    else if (charts[i][--index].value == 0) {
                        // index 相当于慢指针，j 相当于快指针
                        // 也就是说快指针和慢指针中间可能存在一个以上的空格，或者index和j并未相邻
                        // 右边数字 = 0
                        /* 分数: 不变
                           数值: 右边数字 = 当前数字, 当前数字 = 0 */
                        charts[i][index].value = charts[i][j].value;
                        charts[i][j].value = 0;
                        isadd = true;
                    }
                }
            }
        }
        return isadd;
    }

    /** 判断游戏是否结束 */
    private boolean judgeGameOver(){
        // 将lscore标签内容设置为 scores + ""
        Form.lscore.setText(scores + "");

        // 当空白空格不为空时，即游戏未结束
        if (!getEmptyCharts().isEmpty()){
            return false;
        }

        // 当空白方格为空时，判断是否存在可合并的方格
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (charts[i][j].value == charts[i][j + 1].value
                        || charts[i][j].value == charts[i + 1][j].value){
                    return false;
                }
            }
        }
        // 若不满足以上两种情况，则游戏结束
        return true;
    }

    /** 判断游戏是否成功 */
    private boolean judgeGameSuccess() {
        // 检查是否有2048
        for (int i = 0; i< 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (charts[i][j].value == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    /** 画笔函数 */
    @Override
    public void paint(Graphics g){
        super.paint(g);
        System.out.println("paint");
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                drawChart(g, i, j);
            }
        }

        // 如果游戏结束
        if (judgeGameOver()){
            g.setColor(new Color(64, 64, 64, 150));
            g.fillRect(0, 0, getWidth(), getHeight());  // 画矩形
            g.setColor(Color.WHITE);  // 画笔颜色为白色
            g.setFont(title);
            FontMetrics fm = getFontMetrics(title);
            String value = "Game Over!";  // 内容: Game Over!
            g.drawString(value,
                    (getWidth() - fm.stringWidth(value)) / 2,
                    getHeight() / 2);
        }  // 位置

        // 如果游戏成功
        if (judgeGameSuccess()) {
            g.setColor(new Color(64, 64, 64, 150));
            g.fillRect(0, 0, getWidth(), getHeight());  // 画矩形
            g.setColor(Color.RED);  // 画笔颜色为红色
            g.setFont(title);
            FontMetrics fm = getFontMetrics(title);
            String value = "Successful!";  // 内容: Successful!
            g.drawString(value,
                    (getWidth() - fm.stringWidth(value)) / 2,
                    getHeight() / 2);
            // 位置
        }
    }

    /** 绘制方格 */
    private void drawChart(Graphics g, int i, int j){
        /* Java语言在Graphics类提供绘制各种基本的几何图形的基础上,
           扩展Graphics类提供一个Graphics2D类,
           它拥用更强大的二维图形处理能力,提供、坐标转换、颜色管理以及文字布局等更精确的控制。*/
        Graphics2D g2d = (Graphics2D) g;
        /* setRenderingHint() 方法的参数是一个键以及对应的键值。
           KEY_ANTIALIASING: 抗锯齿提示键。对象的几何形状呈现方法是否将尝试沿形状的边缘减少锯齿现象
           此提示允许的值有:
           -- VALUE_ANTIALIAS_ON：使用抗锯齿模式完成呈现
           -- VALUE_ANTIALIAS_OFF：在不使用抗锯齿模式的情况下完成呈现
           -- VALUE_ANTIALIAS_DEFAULT：使用由实现选择的默认抗锯齿模式完成呈现
           KEY_STROKE_CONTROL: 笔划规范化控制提示键。STROKE_CONTROL 提示键控制呈现实现是否应该或允许出于各种目的而修改所呈现轮廓的几何形状。
           此提示允许的值有
           -- VALUE_STROKE_NORMALIZE：几何形状应当规范化，以提高均匀性或直线间隔和整体美观。
           -- VALUE_STROKE_PURE：几何形状应该保持不变并使用子像素精确度呈现
           -- VALUE_STROKE_DEFAULT：根据给定实现的权衡，可以修改几何形状或保留原来的几何形状。
           */
        // 消除锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        // 几何形状规范化
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_NORMALIZE);

        Chart chart = charts[i][j];
        g2d.setColor(chart.getBackground());  // 表格背景颜色
        /* 绘制圆角
           -- x: 填充矩形的 x 坐标
           -- y: 填充矩形的 y 坐标
           -- width: 填充矩形的宽度
           -- height: 填充矩形的高度
           -- arcwidth: 4个弧度的水平直径
           -- archeight: 4个弧度的垂直直径 */
        g2d.fillRoundRect(CHART_GAP + (CHART_GAP + CHART_SIZE) * j,
                CHART_GAP + (CHART_GAP + CHART_SIZE) * i,
                CHART_SIZE, CHART_SIZE, CHART_ARC, CHART_ARC);
        g2d.setColor(chart.getForeground());  // 表格前景颜色
        g2d.setFont(chart.getChartFont());   // 设置字体

        // 文字设定
        /* FontMetrics 字体属性类
           GetAscent(): Ascent表示字体从基线到顶端的距离
           getDescent(): Descent表示字体从基线到下降字符底端的距离
           getLeading(): Leading 表示本文行之间的距离
           getheight(): 字体高度  Ascent + Descent + Leading
           StringWidth(String): 字符串宽度 */
        FontMetrics fm = getFontMetrics(chart.getChartFont());
        String value = String.valueOf(chart.value);  // int型转换为String字符串
        g2d.drawString(value,
                CHART_GAP + (CHART_GAP + CHART_SIZE) * j +
                        (CHART_SIZE - fm.stringWidth(value)) / 2,
                CHART_GAP + (CHART_GAP + CHART_SIZE) * i +
                        (CHART_SIZE - fm.getAscent() - fm.getDescent()) / 2
                        + fm.getAscent());

    }

    /** 释放某个键时调用此方法 */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /** 键入某个键时调用此方法 */
    @Override
    public void keyTyped(KeyEvent e) {

    }
}
