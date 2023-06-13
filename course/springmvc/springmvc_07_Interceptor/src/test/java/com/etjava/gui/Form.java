package com.etjava.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyListener;

public class Form implements Data{
    public static JLabel lscore;

    /** ���촰�� */
    public Form() {
        /*
        setSize(x, y): ���ڴ�С x * y
        setVisible(true): true���ڿɼ�
        setLocationRelativeTo((Component)null)���ô��������ָ�������λ�ã�null��ʾ��������Ļ����
        ����������Ͻǹرգ����ֹرշ�ʽ��
        DO_NOTHING_ON_CLOSE����ִ���κβ�����
        HIDE_ON_CLOSE��ֻ���ؽ��棬setVisible(false)��
        DISPOSE_ON_CLOSE,���ز��ͷŴ��壬dispose()�������һ�����ڱ��ͷź������Ҳ��֮���н�����
        EXIT_ON_CLOSE,ֱ�ӹر�Ӧ�ó���System.exit(0)��һ��main������Ӧһ��������
         */
        /* �������� */
        JFrame frame = new JFrame("Game 2048");
        frame.setSize(400, 530); // width * height
        frame.setResizable(false); // ���ڴ�С���ɵ���
        frame.setVisible(true); // true���ڿɼ�
        frame.setLocationRelativeTo(null); // ���ھ���
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE); // ���ڵĹر�
        frame.setLayout(null); // �����û������ϵ���Ļ����ĸ�ʽ���֣�Ĭ��Ϊ��ʽ����

        /* ��ӱ�ǩ */
        // ��ӱ���: 2048
        JLabel ltitle = new JLabel("2048", JLabel.CENTER);
        frame.add(ltitle);
        ltitle.setFont(Data.title); // ��������/��С/λ��
        ltitle.setForeground(Color.BLACK);
        ltitle.setBounds(50, 0, 150, 60);

        // ��ӷ���: �÷�: 0
        JLabel lscorename = new JLabel("�� ��", JLabel.CENTER);
        frame.add(lscorename);
        lscorename.setFont(Data.score); // "�÷�"����/��С/λ��
        lscorename.setForeground(Color.WHITE);
        lscorename.setOpaque(true); // ���ÿؼ��Ƿ�͸��.true: �ؼ���͸��; false: �ؼ�͸��.
        lscorename.setBackground(Color.GRAY);
        lscorename.setBounds(250, 0, 120, 30);

        lscore = new JLabel("0", JLabel.CENTER);
        frame.add(lscore);
        lscore.setFont(Data.score); // "�÷�"����/��С/λ��
        lscore.setForeground(Color.WHITE);
        lscore.setOpaque(true); // ���ÿؼ��Ƿ�͸��.true: �ؼ���͸��; false: �ؼ�͸��.
        lscore.setBackground(Color.GRAY);
        lscore.setBounds(250, 30, 120, 30);

        // ��Ϸ˵��:
        JLabel ltips = new JLabel("����: �� �� �� ��, ��esc�����¿�ʼ  ", JLabel.CENTER);
        frame.add(ltips);
        ltips.setFont(Data.tips); // "˵��"����/��С/λ��
        ltips.setForeground(Color.DARK_GRAY);
        ltips.setBounds(0, 60, 400, 40);

        // ��Ϸ���:
        JPanel panel = new Game2048();
        frame.add(panel);
        panel.setBounds(0, 100, 400, 400); // ����������
        panel.setBackground(Color.GRAY);
        panel.setFocusable(true); //setFocusable��������Ƿ�ɱ�ѡ��
        // FlowLayout(��ʽ����): ������ռ�����Ⱥ�˳�������õĶ��뷽ʽ�����������У�һ����������һ�п�ʼ��������
        panel.setLayout(new FlowLayout());
        // ���̼���
        frame.addKeyListener((KeyListener) panel);
    }

    public static void main(String[] args) {
        new Form();
    }
}