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

// ��Ϸ�����Ҫ�Լ��̽��м����������Ҫʵ�ֽӿ� KeyListener �е� keyPressed ����
public class Game2048 extends JPanel implements Data, KeyListener {
    /* �������� */
    private static int scores = 0;
    private Chart[][] charts = new Chart[4][4]; // ��Ϸ���
    private boolean isadd = true;  // �Ƿ���������

    /** ���췽�� */
    public Game2048() {
        initGame();  // ��ʼ��
    }

    /** ����ĳ����ʱ���ô˷��� */
    @Override
    public void keyPressed(KeyEvent e) {
        /* getKeyCode():������ÿһ����ť���ж�Ӧ��(Code),��������֪�û�����ʲô�������ص�ǰ��ť����ֵ
           getKeyChar():������ǱȽϸ߲���¼������ص���ÿǷ�û����̺�õ����ַ����������뷨�¾��Ǻ��֣�
           getKeyText():��������¼��еļ��������ַ�������getKeyText(e.getKeyCode())�ͷ����������µļ���*/

        switch (e.getKeyCode()){
            /* VK_ESCAPE: Esc
               VK_UP: ��
               VK_DOWN: ��
               VK_LEFT: ��
               VK_RIGHT: ��
            */
            // ���¿�ʼ��Ϸ
            case KeyEvent.VK_ESCAPE:
                System.out.println("esc");
                initGame(); // ���¿�ʼ��Ϸ����Ϸ��ʼ��
                break;
            // ��
            case KeyEvent.VK_UP:
//                System.out.println("up");
                MoveUp();  // �����ƶ�
                creatChart();  // �����������
//                judgeGameOver(); // �ж���Ϸ�Ƿ����
                break;
            // ��
            case KeyEvent.VK_DOWN:
//                System.out.println("down");
                MoveDown();  // �����ƶ�
                creatChart();  // �����������
//                judgeGameOver(); // �ж���Ϸ�Ƿ����
                break;
            // ��
            case KeyEvent.VK_LEFT:
//                System.out.println("left");
                MoveLeft();  // �����ƶ�
                creatChart();  // �����������
//                judgeGameOver(); // �ж���Ϸ�Ƿ����
                break;
            // ��
            case KeyEvent.VK_RIGHT:
//                System.out.println("right");
                MoveRight();  // �����ƶ�
                creatChart();  // �����������
//                judgeGameOver(); // �ж���Ϸ�Ƿ����
                break;
            // others
            default:
                break;
        }
        // paint()������������ͼ�Σ�repaint()�����������»���ͼ��
        repaint();
    }

    /** ��Ϸ��ʼ�� */
    private void initGame(){
        scores = 0;
        System.out.println("initGame");
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                charts[row][col] = new Chart();  // from Chart.java
            }
        }
        // �������������
        for (int i = 0; i < 2; i++){
            isadd = true;
            creatChart();  // �������һ������
        }
    }

    /** �����һ��λ������һ���� */
    private void creatChart(){
        List<Chart> list = getEmptyCharts();  // list Ϊ�հ׷���

        if (!list.isEmpty() && isadd) {  // �ڿհ׷�����������һ������
            Random random = new Random();
            int index = random.nextInt(list.size());
            Chart chart = list.get(index);
            // 2, 4���ָ���3:1
            /* random.nextInt(int n) �ǲ��� [0, n) ������� */
            /* random.nextInt(4): ������� 0, 1, 2, 3; */
            chart.value = (random.nextInt(4) % 3 == 0) ? 2 : 4;
            System.out.println(chart.value);
            isadd = false;
        }
    }

    /** ��ȡ�հ׷��� */
    private List<Chart> getEmptyCharts(){
        List<Chart> chartList = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (charts[i][j].value == 0){
                    chartList.add(charts[i][j]);  //���Ԫ�ص� ArrayList ����ʹ�� add() ����
                }
            }
        }
        return chartList;
    }

    /** �����ƶ� */
    private boolean MoveUp() {
//        System.out.println("MoveUp");
        /* �����ƶ���ֻ�迼�ǵڶ��е�������
           ����Ϊ�������:
           1����ǰ�����ϱ��޿ո񣬼��ϱ�ֵ��Ϊ 0
              a. ��ǰ�������ϱ�������ȣ��ϲ�
              b. ��ǰ�������ϱ����ֲ���ȣ�continue
           2����ǰ�����ϱ��пո񣬼��ϱ�ֵΪ 0�� ���� */
        for (int j = 0; j < 4; j++) {
            for (int i = 1, index = 0; i < 4; i++) {
                if (charts[i][j].value > 0) {
                    if (charts[i][j].value == charts[index][j].value) {
                        // ��ǰ���� == �ϱ�����
                    /* ����: ��ǰ���� + �ϱ�����
                       ��ֵ: �ϱ����� = �ϱ����� + ��ǰ����, ��ǰ���� = 0 */
                        scores += charts[i][j].value + charts[index][j].value;
                        charts[index][j].value = charts[i][j].value + charts[index][j].value;
                        charts[i][j].value = 0;
                        index += 1;
                        isadd = true;
                    }
                    // ��ǰ�������ϱ����ֲ���ȣ�continue ����ʡ�Բ�д
                    else if (charts[index][j].value == 0) {
                        // ��ǰ�����ϱ���0
                    /* ����: ����
                       ��ֵ: �ϱ����� = ��ǰ����, ��ǰ���� = 0 */
                        charts[index][j].value = charts[i][j].value;
                        charts[i][j].value = 0;
                        isadd = true;
                    }
                    else if (charts[++index][j].value == 0) {
                        // index �൱����ָ�룬j �൱�ڿ�ָ��
                        // Ҳ����˵��ָ�����ָ���м���ܴ���һ�����ϵĿո񣬻���index��j��δ����
                        // �ϱ����� = 0
                    /* ����: ����
                       ��ֵ: �ϱ����� = ��ǰ����, ��ǰ���� = 0 */
                        charts[index][j].value = charts[i][j].value;
                        charts[i][j].value = 0;
                        isadd = true;
                    }
                }
            }
        }
        return isadd;
    }

    /** �����ƶ� */
    private boolean MoveDown(){
        System.out.println("MoveDown");
        /* �����ƶ���ֻ�迼�ǵ�һ�е�������
           ����Ϊ�������:
           1����ǰ�����±��޿ո񣬼��±�ֵ��Ϊ 0
              a. ��ǰ�������±�������ȣ��ϲ�
              b. ��ǰ�������±����ֲ���ȣ�continue
           2����ǰ�����±��пո񣬼��±�ֵΪ 0�� ���� */
        for (int j = 0; j < 4; j++) {
            for (int i = 2, index = 3; i >= 0; i--) {
                if (charts[i][j].value > 0) {
                    if (charts[i][j].value == charts[index][j].value) {
                        // ��ǰ���� == �±�����
                        /* ����: ��ǰ���� + �±�����
                           ��ֵ: �±����� = �±����� + ��ǰ����, ��ǰ���� = 0 */
                        scores += charts[i][j].value + charts[index][j].value;
                        charts[index][j].value = charts[i][j].value + charts[index][j].value;
                        charts[i][j].value = 0;
                        index -= 1;
                        isadd = true;
                    }
                    // ��ǰ�������±����ֲ���ȣ�continue ����ʡ�Բ�д
                    else if (charts[index][j].value == 0) {
                        // ��ǰ�����±���0
                        /* ����: ����
                           ��ֵ: �±����� = ��ǰ����, ��ǰ���� = 0 */
                        charts[index][j].value = charts[i][j].value;
                        charts[i][j].value = 0;
                        isadd = true;
                    }
                    else if (charts[--index][j].value == 0) {
                        // index �൱����ָ�룬j �൱�ڿ�ָ��
                        // Ҳ����˵��ָ�����ָ���м���ܴ���һ�����ϵĿո񣬻���index��j��δ����
                        // �±����� = 0
                        /* ����: ����
                           ��ֵ: �±����� = ��ǰ����, ��ǰ���� = 0 */
                        charts[index][j].value = charts[i][j].value;
                        charts[i][j].value = 0;
                        isadd = true;
                    }
                }
            }
        }
        return isadd;
    }

    /** �����ƶ� */
    private boolean MoveLeft(){
        //        System.out.println("MoveLeft");
        /* �����ƶ���ֻ�迼�ǵڶ��е�������
           ����Ϊ�������:
           1����ǰ��������޿ո񣬼����ֵ��Ϊ 0
              a. ��ǰ���������������ȣ��ϲ�
              b. ��ǰ������������ֲ���ȣ�continue
           2����ǰ��������пո񣬼����ֵΪ 0�� ���� */
        for (int i = 0; i < 4; i++) {
            for (int j = 1, index = 0; j < 4; j++) {
                if (charts[i][j].value > 0) {
                    if (charts[i][j].value == charts[i][index].value) {
                        // ��ǰ���� == �������
                        /* ����: ��ǰ���� + �������
                           ��ֵ: ������� = ������� + ��ǰ����, ��ǰ���� = 0 */
                        scores += charts[i][j].value + charts[i][index].value;
                        charts[i][index].value = charts[i][index].value + charts[i][j].value;
                        charts[i][j].value = 0;
                        index += 1;
                        isadd = true;
                    }
                    // ��ǰ������������ֲ���ȣ�continue ����ʡ�Բ�д
                    else if (charts[i][index].value == 0) {
                        // ��ǰ���������0
                        /* ����: ����
                           ��ֵ: ������� = ��ǰ����, ��ǰ���� = 0 */
                        charts[i][index].value = charts[i][j].value;
                        charts[i][j].value = 0;
                        isadd = true;
                    }
                    else if (charts[i][++index].value == 0) {
                        // index �൱����ָ�룬j �൱�ڿ�ָ��
                        // Ҳ����˵��ָ�����ָ���м���ܴ���һ�����ϵĿո񣬻���index��j��δ����
                        // ������� = 0
                        /* ����: ����
                           ��ֵ: ������� = ��ǰ����, ��ǰ���� = 0 */
                        charts[i][index].value = charts[i][j].value;
                        charts[i][j].value = 0;
                        isadd = true;
                    }
                }
            }
        }

        return isadd;
    }

    /** �����ƶ� */
    private boolean MoveRight(){
        //        System.out.println("MoveRight");
      /* �����ƶ���ֻ�迼�ǵ�һ�е�������
         ����Ϊ�������:
         1����ǰ�����ұ��޿ո񣬼��ұ�ֵ��Ϊ 0
            a. ��ǰ�������ұ�������ȣ��ϲ�
            b. ��ǰ�������ұ����ֲ���ȣ�continue
         2����ǰ�����ұ��пո񣬼��ұ�ֵΪ 0�� ���� */
        for (int i = 0; i < 4; i++) {
            for (int j = 2, index = 3; j >= 0; j--) {
                if (charts[i][j].value > 0) {
                    if (charts[i][j].value == charts[i][index].value) {
                        // ��ǰ���� == �ұ�����
                        /* ����: ��ǰ���� + �ұ�����
                           ��ֵ: �ұ����� = �ұ����� + ��ǰ����, ��ǰ���� = 0 */
                        scores += charts[i][j].value + charts[i][index].value;
                        charts[i][index].value = charts[i][j].value + charts[i][index].value;
                        charts[i][j].value = 0;
                        index -= 1;
                        isadd = true;
                    }
                    // ��ǰ������������ֲ���ȣ�continue ����ʡ�Բ�д
                    else if (charts[i][index].value == 0) {
                        // ��ǰ�����ұ���0
                        /* ����: ����
                           ��ֵ: �ұ����� = ��ǰ����, ��ǰ���� = 0 */
                        charts[i][index].value = charts[i][j].value;
                        charts[i][j].value = 0;
                        isadd = true;
                    }
                    else if (charts[i][--index].value == 0) {
                        // index �൱����ָ�룬j �൱�ڿ�ָ��
                        // Ҳ����˵��ָ�����ָ���м���ܴ���һ�����ϵĿո񣬻���index��j��δ����
                        // �ұ����� = 0
                        /* ����: ����
                           ��ֵ: �ұ����� = ��ǰ����, ��ǰ���� = 0 */
                        charts[i][index].value = charts[i][j].value;
                        charts[i][j].value = 0;
                        isadd = true;
                    }
                }
            }
        }
        return isadd;
    }

    /** �ж���Ϸ�Ƿ���� */
    private boolean judgeGameOver(){
        // ��lscore��ǩ��������Ϊ scores + ""
        Form.lscore.setText(scores + "");

        // ���հ׿ո�Ϊ��ʱ������Ϸδ����
        if (!getEmptyCharts().isEmpty()){
            return false;
        }

        // ���հ׷���Ϊ��ʱ���ж��Ƿ���ڿɺϲ��ķ���
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (charts[i][j].value == charts[i][j + 1].value
                        || charts[i][j].value == charts[i + 1][j].value){
                    return false;
                }
            }
        }
        // �������������������������Ϸ����
        return true;
    }

    /** �ж���Ϸ�Ƿ�ɹ� */
    private boolean judgeGameSuccess() {
        // ����Ƿ���2048
        for (int i = 0; i< 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (charts[i][j].value == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    /** ���ʺ��� */
    @Override
    public void paint(Graphics g){
        super.paint(g);
        System.out.println("paint");
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                drawChart(g, i, j);
            }
        }

        // �����Ϸ����
        if (judgeGameOver()){
            g.setColor(new Color(64, 64, 64, 150));
            g.fillRect(0, 0, getWidth(), getHeight());  // ������
            g.setColor(Color.WHITE);  // ������ɫΪ��ɫ
            g.setFont(title);
            FontMetrics fm = getFontMetrics(title);
            String value = "Game Over!";  // ����: Game Over!
            g.drawString(value,
                    (getWidth() - fm.stringWidth(value)) / 2,
                    getHeight() / 2);
        }  // λ��

        // �����Ϸ�ɹ�
        if (judgeGameSuccess()) {
            g.setColor(new Color(64, 64, 64, 150));
            g.fillRect(0, 0, getWidth(), getHeight());  // ������
            g.setColor(Color.RED);  // ������ɫΪ��ɫ
            g.setFont(title);
            FontMetrics fm = getFontMetrics(title);
            String value = "Successful!";  // ����: Successful!
            g.drawString(value,
                    (getWidth() - fm.stringWidth(value)) / 2,
                    getHeight() / 2);
            // λ��
        }
    }

    /** ���Ʒ��� */
    private void drawChart(Graphics g, int i, int j){
        /* Java������Graphics���ṩ���Ƹ��ֻ����ļ���ͼ�εĻ�����,
           ��չGraphics���ṩһ��Graphics2D��,
           ��ӵ�ø�ǿ��Ķ�άͼ�δ�������,�ṩ������ת������ɫ�����Լ����ֲ��ֵȸ���ȷ�Ŀ��ơ�*/
        Graphics2D g2d = (Graphics2D) g;
        /* setRenderingHint() �����Ĳ�����һ�����Լ���Ӧ�ļ�ֵ��
           KEY_ANTIALIASING: �������ʾ��������ļ�����״���ַ����Ƿ񽫳�������״�ı�Ե���پ������
           ����ʾ�����ֵ��:
           -- VALUE_ANTIALIAS_ON��ʹ�ÿ����ģʽ��ɳ���
           -- VALUE_ANTIALIAS_OFF���ڲ�ʹ�ÿ����ģʽ���������ɳ���
           -- VALUE_ANTIALIAS_DEFAULT��ʹ����ʵ��ѡ���Ĭ�Ͽ����ģʽ��ɳ���
           KEY_STROKE_CONTROL: �ʻ��淶��������ʾ����STROKE_CONTROL ��ʾ�����Ƴ���ʵ���Ƿ�Ӧ�û�������ڸ���Ŀ�Ķ��޸������������ļ�����״��
           ����ʾ�����ֵ��
           -- VALUE_STROKE_NORMALIZE��������״Ӧ���淶��������߾����Ի�ֱ�߼�����������ۡ�
           -- VALUE_STROKE_PURE��������״Ӧ�ñ��ֲ��䲢ʹ�������ؾ�ȷ�ȳ���
           -- VALUE_STROKE_DEFAULT�����ݸ���ʵ�ֵ�Ȩ�⣬�����޸ļ�����״����ԭ���ļ�����״��
           */
        // �������
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        // ������״�淶��
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_NORMALIZE);

        Chart chart = charts[i][j];
        g2d.setColor(chart.getBackground());  // ��񱳾���ɫ
        /* ����Բ��
           -- x: �����ε� x ����
           -- y: �����ε� y ����
           -- width: �����εĿ��
           -- height: �����εĸ߶�
           -- arcwidth: 4�����ȵ�ˮƽֱ��
           -- archeight: 4�����ȵĴ�ֱֱ�� */
        g2d.fillRoundRect(CHART_GAP + (CHART_GAP + CHART_SIZE) * j,
                CHART_GAP + (CHART_GAP + CHART_SIZE) * i,
                CHART_SIZE, CHART_SIZE, CHART_ARC, CHART_ARC);
        g2d.setColor(chart.getForeground());  // ���ǰ����ɫ
        g2d.setFont(chart.getChartFont());   // ��������

        // �����趨
        /* FontMetrics ����������
           GetAscent(): Ascent��ʾ����ӻ��ߵ����˵ľ���
           getDescent(): Descent��ʾ����ӻ��ߵ��½��ַ��׶˵ľ���
           getLeading(): Leading ��ʾ������֮��ľ���
           getheight(): ����߶�  Ascent + Descent + Leading
           StringWidth(String): �ַ������ */
        FontMetrics fm = getFontMetrics(chart.getChartFont());
        String value = String.valueOf(chart.value);  // int��ת��ΪString�ַ���
        g2d.drawString(value,
                CHART_GAP + (CHART_GAP + CHART_SIZE) * j +
                        (CHART_SIZE - fm.stringWidth(value)) / 2,
                CHART_GAP + (CHART_GAP + CHART_SIZE) * i +
                        (CHART_SIZE - fm.getAscent() - fm.getDescent()) / 2
                        + fm.getAscent());

    }

    /** �ͷ�ĳ����ʱ���ô˷��� */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /** ����ĳ����ʱ���ô˷��� */
    @Override
    public void keyTyped(KeyEvent e) {

    }
}
