package com.etjava.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyListener;

public class Form implements Data{
    public static JLabel lscore;

    /** 构造窗体 */
    public Form() {
        /*
        setSize(x, y): 窗口大小 x * y
        setVisible(true): true窗口可见
        setLocationRelativeTo((Component)null)设置窗口相对于指定组件的位置，null表示窗口在屏幕中央
        点击窗口右上角关闭，四种关闭方式：
        DO_NOTHING_ON_CLOSE，不执行任何操作。
        HIDE_ON_CLOSE，只隐藏界面，setVisible(false)。
        DISPOSE_ON_CLOSE,隐藏并释放窗体，dispose()，当最后一个窗口被释放后，则程序也随之运行结束。
        EXIT_ON_CLOSE,直接关闭应用程序，System.exit(0)。一个main函数对应一整个程序。
         */
        /* 窗口设置 */
        JFrame frame = new JFrame("Game 2048");
        frame.setSize(400, 530); // width * height
        frame.setResizable(false); // 窗口大小不可调整
        frame.setVisible(true); // true窗口可见
        frame.setLocationRelativeTo(null); // 窗口居中
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE); // 窗口的关闭
        frame.setLayout(null); // 设置用户界面上的屏幕组件的格式布局，默认为流式布局

        /* 添加标签 */
        // 添加标题: 2048
        JLabel ltitle = new JLabel("2048", JLabel.CENTER);
        frame.add(ltitle);
        ltitle.setFont(Data.title); // 标题字体/大小/位置
        ltitle.setForeground(Color.BLACK);
        ltitle.setBounds(50, 0, 150, 60);

        // 添加分数: 得分: 0
        JLabel lscorename = new JLabel("得 分", JLabel.CENTER);
        frame.add(lscorename);
        lscorename.setFont(Data.score); // "得分"字体/大小/位置
        lscorename.setForeground(Color.WHITE);
        lscorename.setOpaque(true); // 设置控件是否透明.true: 控件不透明; false: 控件透明.
        lscorename.setBackground(Color.GRAY);
        lscorename.setBounds(250, 0, 120, 30);

        lscore = new JLabel("0", JLabel.CENTER);
        frame.add(lscore);
        lscore.setFont(Data.score); // "得分"字体/大小/位置
        lscore.setForeground(Color.WHITE);
        lscore.setOpaque(true); // 设置控件是否透明.true: 控件不透明; false: 控件透明.
        lscore.setBackground(Color.GRAY);
        lscore.setBounds(250, 30, 120, 30);

        // 游戏说明:
        JLabel ltips = new JLabel("操作: ↑ ↓ ← →, 按esc键重新开始  ", JLabel.CENTER);
        frame.add(ltips);
        ltips.setFont(Data.tips); // "说明"字体/大小/位置
        ltips.setForeground(Color.DARK_GRAY);
        ltips.setBounds(0, 60, 400, 40);

        // 游戏面板:
        JPanel panel = new Game2048();
        frame.add(panel);
        panel.setBounds(0, 100, 400, 400); // 面板绘制区域
        panel.setBackground(Color.GRAY);
        panel.setFocusable(true); //setFocusable设置组件是否可被选中
        // FlowLayout(流式布局): 组件按照加入的先后顺序按照设置的对齐方式从左向右排列，一行排满到下一行开始继续排列
        panel.setLayout(new FlowLayout());
        // 键盘监听
        frame.addKeyListener((KeyListener) panel);
    }

    public static void main(String[] args) {
        new Form();
    }
}