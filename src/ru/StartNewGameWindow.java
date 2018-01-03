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
public class StartNewGameWindow extends JFrame {

    private final GameWindowCZ gameWindow;
    // 23 объявлем констатнты для окна настроек
    private static final int WIN_HEIGHT = 230;
    private static final int WIN_WIDTH = 250;
    private static final int MIN_WIN_LENTH = 3;
    private static final int MAX_WIN_LENTH = 10;
    private static final int MIN_FIELD_SIZE = 3;
    private static final int MAX_FIELD_SIZE = 10;

    // 31
    private final String STR_WIN_LEN = "Win Length: ";

    // 34
    private static final String STR_FIELD_SIZE = "Field Size: ";

    // 32 создаем слайдер
    private JSlider slFieldSize;
    private JSlider slWinLength;

    // 28 radio btn  /// 30 - 2 делаем, что кнопка выбрана
    private JRadioButton jrbHumVsAi = new JRadioButton("Human vs Ai", true);
    private JRadioButton jrbHumVsHum = new JRadioButton("Human vs Human");
    //30 создаем группу кнопок для радио кнопок
    private ButtonGroup gameMode = new ButtonGroup();

    // 15. Создаем второе окно где будет игра
    // 16

    public int getStartNewGameWindowX() {
        return this.getX();

    }
    int getStartNewGameWindowY() {
        return this.getY();
    }
    StartNewGameWindow(GameWindowCZ gameWindow) {
        this.gameWindow = gameWindow;

        // 24 устанавливаем размеры
        setSize(WIN_WIDTH, WIN_HEIGHT);

        // 25 класс Rectangle нам нужно узнать где центр
        Rectangle gameWindowsBounds = gameWindow.getBounds();
        // здесь мы получаем координаты левого верхнего угла маленького окна
        int posX = (int) (gameWindowsBounds.getCenterX() - WIN_WIDTH/2);
        int posY = (int) (gameWindowsBounds.getCenterY() - WIN_HEIGHT/2);
        setLocation(posX, posY);

        // 26 устанавливаем заголовок и добавляем 10 строк и 1 столбец
        setTitle("New game parametrs");
        setLayout(new GridLayout(10,1));

        // 27
        addGameControlsMode();

        // 31 лайбл для выйграшной длины
        addGameControlsFieldWinLenght();
        // 34  кнопка старта игры
        JButton btnStartGame = new JButton("Start a game");
        btnStartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnStartGameClick();
            }
        });
        add(btnStartGame);
    }

    // 35 реакиця на кнопку старта
    void btnStartGameClick() {
        int gameMode;
        if(jrbHumVsAi.isSelected())
            gameMode = 0;
        else if(jrbHumVsHum.isSelected())
            gameMode = 1;
        else
            throw new RuntimeException("No buttons selected");

        int fieldSize = slFieldSize.getValue();
        int winLength = slWinLength.getValue();
        gameWindow.startNewGame(gameMode, fieldSize, fieldSize, winLength);
        setVisible(false);
    }

    // 27
    void addGameControlsMode() {
        add(new JLabel("Choose gaming mode"));

        // 30 - 1 добавляем кнопки в группу
        gameMode.add(jrbHumVsAi);
        gameMode.add(jrbHumVsHum);

        // 29 add radio btn
        add(jrbHumVsAi);
        add(jrbHumVsHum);
    }


    // 31 метод выйграшной длины
    void addGameControlsFieldWinLenght() {
        // 34 делаем зависимость от первого слайдера
        add(new JLabel("Choose field size:"));
        JLabel lblFieldSize = new JLabel(STR_FIELD_SIZE + MIN_FIELD_SIZE);
        add(lblFieldSize);
        slFieldSize = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, MIN_FIELD_SIZE);
        slFieldSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int currentFieldSize = slFieldSize.getValue();
                lblFieldSize.setText(STR_FIELD_SIZE + currentFieldSize);
                slWinLength.setMaximum(currentFieldSize);
            }
        });
        add(slFieldSize);

        add(new JLabel("Chosse winnig length: "));
        JLabel lbWinLen = new JLabel(STR_WIN_LEN + MIN_WIN_LENTH);
        add(lbWinLen);

        // 32
        slWinLength = new JSlider(MIN_WIN_LENTH, MAX_WIN_LENTH, MIN_WIN_LENTH);
        // 33 добавляем слушателя
        slWinLength.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                lbWinLen.setText(STR_WIN_LEN + slWinLength.getValue());
            }
        });
        add(slWinLength);
    }

}
