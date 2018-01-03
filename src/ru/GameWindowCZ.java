package ru;

import javafx.scene.control.TitledPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Gulshat on 08.10.2017.
 */
public class GameWindowCZ extends JFrame {

    // 2. обозначаем размеры
    private static final int WIN_HEIGHT = 500;
    private static final int WIN_WIDTH = 500;
    private static final int WIN_POS_X = 200;
    private static final int WIN_POS_Y = 200;

    // 16 создаем константы
    private static int count;
    private static StartNewGameWindow startNewGameWindow;
    private static EndGameWindow endGameWindow;
    private static JPanel field; //игровое поле
    private static JButton btnExit;
    private static JButton btnNewGame;
    private static JPanel bottomPanelSouth, bottomPanelCenter;
    private static JButton[] jbs;

    // 3. создаем конструктор

    void deleteGameField () {
       remove(bottomPanelCenter);
    }
    GameWindowCZ() {
        // 5. добавляем операцию close, при закрытии окна, программа завершается.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 6. задаем размеры
        setBounds(WIN_POS_X, WIN_POS_Y, WIN_WIDTH, WIN_HEIGHT);

        //12. Добавляем заголовок и запрещаем изменение размера окна
        setTitle("Tic Tac Toe");
        setResizable(false);


        // 9 Создаем панель, (только сначала рассказываем по север юг запад восток),
        // далее рассказываем про new GridLayout(1, 2) и что можно еще что-нибудь добавить
        bottomPanelSouth = new JPanel(new GridLayout(1, 2));
        bottomPanelCenter = new JPanel(new GridLayout(1, 1));
          // 7. создаем кнопку
        btnNewGame = new JButton("Start new game");
       // 7.1 добавляем, потом перепишем
       // add(btnNewGame);
        // 22 обрабатываем стар новая игра
        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 19 - 22 делаем наше окно настроек видимое // при крестике окно просто становить не видимым
                startNewGameWindow.setVisible(true);
            }
        });


        // 8. создаем кнопку
        btnExit = new JButton("Exit game");
        // 8.1 добавляем, потом перепишем
        add(btnExit);
        // 21 Вешаем слушателя на кнопку
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ноль для остановки программы
                System.exit(0);
            }
        });

        // 10 далее в панель кнопки
        bottomPanelSouth.add(btnNewGame);
        bottomPanelSouth.add(btnExit);

        // 17 добавляем панели для игрового поля
          add(bottomPanelCenter);

        // 11 добавляем в окно панель и распологаем ее на юге
        add(bottomPanelSouth, BorderLayout.SOUTH);

        // 18 передаем возможность определение координатов следующему окну
        startNewGameWindow = new StartNewGameWindow(this);
        endGameWindow = new EndGameWindow(this);
        // 19 делаем наше окно настроек видимое
       // startNewGameWindow.setVisible(true);

        // 4. создаем наше первое окно (УРА!)
        setVisible(true);
    }


    // 20 кнопка стартануть будет на окне настроек, этот метод нужно вызывать из 2го окна
    void startNewGame(int mode, int fieldSizeX, int fieldSizeY, int winLenght) {
        jbs = new JButton[fieldSizeX*fieldSizeY];//массив графических кнопок
        final Font font = new Font("Verdana", Font.PLAIN, (int) (100/(fieldSizeY-2)));//размер игрового символа
        ZeroCross zerocross = new ZeroCross();
        count=0;
        zerocross.setFieldgSize(fieldSizeY, fieldSizeX, winLenght);//передаем данныые об игровом поле
        remove(bottomPanelCenter);//удаление предыдущей версии игрового поля
        bottomPanelCenter = new JPanel(new GridLayout(fieldSizeY, fieldSizeX));
        TitleText(mode, count);
        for (int i = 0; i < jbs.length; i++) {//создание иговых секторов
            jbs[i] = new JButton(" ");
            jbs[i].setFont(font);
            bottomPanelCenter.add(jbs[i]);
            jbs[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {//обработка хода игрока
                    int y = buttonNum(e.getYOnScreen(), getY(), fieldSizeY, -20);//определение индекса по вертикали
                    int x = buttonNum(e.getXOnScreen(), getX(), fieldSizeY, 0); //определение индекса по горизонтали
                    int i = y *fieldSizeY +x; //индекс в массиве кнопок

                    TitleText(mode, count);
                    if (zerocross.checkMove(y,x)) {//Ход человека
                            count++;
                            zerocross.dotField(y,x,DOT(count));//запись хода
                            jbs[i].setText(Character.toString(DOT(count))); //проставление на поле
                            if (zerocross.checkWin(DOT(count))) {//проверку на победу
                                endGameWindow.EndGameWindowUpdate(-1,
                                        ((mode ==0) ? 0 : ((count%2==1)? 1 : 2)));

                            } else if (zerocross.fullField()) {
                                endGameWindow.EndGameWindowUpdate(-3, 0);
                            }
                             else if (mode == 0) {
                                count++;
                                jbs[zerocross.AiMove()].setText(Character.toString(DOT(count)));
                                if (zerocross.checkWin(DOT(count))) {
                                    endGameWindow.EndGameWindowUpdate(-2, 0);
                                } else if (zerocross.fullField()) {
                                    endGameWindow.EndGameWindowUpdate(-3, 0);
                                }
                            }
                    }
                    //System.out.println("Точка0  " + count);
                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
        add(bottomPanelCenter);
        setVisible(true);

    }
    int buttonNum(int z, int xy, int countY, int corr) {//определение строки/стоблца игрового поля
        return (int) ((z - xy) / ((WIN_HEIGHT + corr) / countY));
    }

    char DOT (int count) { // определени какой символ показывать на поле
        if (count%2 == 1) return 'X';
        else return 'O';
    }

    void TitleText(int mode, int count) { //текст в Титуле окна
        if (mode ==0) {
            setTitle("Tic Tac Toe                  Ваш ход ");
        } else  setTitle("Tic Tac Toe                  Ход игрока " + ((count%2==1)? 1 : 2));
    }

}
