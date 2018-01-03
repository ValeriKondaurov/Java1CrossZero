package ru;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Gulshat on 08.10.2017.
 */

// настройки для нашей новой игры
public class EndGameWindow extends JFrame {
    private final GameWindowCZ gameWindow;
    // 23 объявлем констатнты для окна настроек
    private static final int WIN_HEIGHT = 130;
    private static final int WIN_WIDTH = 350;
    private static int typeEndGame;
    private static String textEndGame;
    private static JLabel text;

    int getEndGameWindowX() {
        return this.getX();
    }
    int getEndGameWindowY() {
        return this.getY();
    }

    public void setTypeEndGame (int typeEndgame) {
        this.typeEndGame = typeEndgame;
    }

    EndGameWindow(GameWindowCZ gameWindow) {
        this.gameWindow = gameWindow;

        // 24 устанавливаем размеры
        setSize(WIN_WIDTH, WIN_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 25 класс Rectangle нам нужно узнать где центр
        Rectangle gameWindowsBounds = gameWindow.getBounds();
        // здесь мы получаем координаты левого верхнего угла маленького окна
        int posX = (int) (gameWindowsBounds.getCenterX() - WIN_WIDTH/2);
        int posY = (int) (gameWindowsBounds.getCenterY() - WIN_HEIGHT/2);
        setLocation(posX, posY);

        // 26 устанавливаем заголовок и добавляем 10 строк и 1 столбец
        setTitle("End game");
        setLayout(new GridLayout(2,1));
        text = new JLabel("");
        text.setHorizontalAlignment(JLabel.CENTER);
        text.setFont(new Font("Verdana", Font.BOLD, 20));
        add(text);



        JButton btnOkEndGame = new JButton("Ok");
        btnOkEndGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                gameWindow.deleteGameField();
            }
        });
        add(btnOkEndGame);
    }
    void EndGameWindowUpdate (int typeEndgame, int nubmerPlayer) {//определения текста
        switch (typeEndgame) {
            case (-1) : textEndGame = ((nubmerPlayer == 0) ? "Вы выиграли" : "Выиграл Игрок №" + nubmerPlayer);
                break;
            case (-2) : textEndGame = "Выиграл компьютер";
                break;
            case (-3) : textEndGame = "Ничья";
                break;
        }
        text.setText(textEndGame);
        setVisible(true);
    }


}
