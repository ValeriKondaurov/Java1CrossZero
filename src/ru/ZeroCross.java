package ru;

import java.util.Random;
import java.util.Scanner;

public class ZeroCross {


    //1. параметры игрового поля
    private int SIZE_Y=0; //размер поля по вертикале
    private int SIZE_X=0; //расчет поля по горизонтале
    private int SIZE_WIN=0; //кол-во заполненных подряж полей для победы
    private char[][] fieldg;
    private int indexAi; //координа хода компьютера
    // игровые элемент
    static final char player_DOT= 'X';
    static final char Ai_DOT= 'O';
    static final char EMPTY_DOT= '.';

    // обявляется классов ввода и случайного числа для игры
    static Scanner scr = new Scanner(System.in);
    static Random rnd = new Random();

    public  void setFieldgX(int SIZE_X) {
        this.SIZE_X =SIZE_X;
    }
    public void setFieldgY(int SIZE_Y) {
        this.SIZE_Y =SIZE_Y;
    }
    public void setwinLenght(int winLenght) {
        this.SIZE_WIN =winLenght;
    }

    public void setFieldgSize(int SIZE_Y, int SIZE_X, int winLenght){

        setFieldgX(SIZE_X);
        setFieldgY(SIZE_Y);
        setwinLenght(winLenght);
        fieldg =  new char [this.SIZE_Y][this.SIZE_X];
        emtpyField ();
        printField();
    }

    public  void playerMove(int SIZE_Y, int SIZE_X){
        fieldg =  new char [SIZE_Y][SIZE_X];
    }

    //поле в начале игры
    private  void emtpyField () {
       for (int i=0; i<SIZE_Y; i++) {
            for (int j=0; j<SIZE_X; j++) {
                fieldg [i][j] = EMPTY_DOT;
            }
        }
    }

    //печать поля на экран
    private  void printField ()     {
        printFieldLine ();
        for (int i=0; i<SIZE_Y; i++) {
            System.out.print("|");
            for (int j=0; j<SIZE_X; j++) {
                System.out.print(fieldg[i][j]+"|");
            }
            System.out.println("");
        }
        printFieldLine ();
    }
    //чертим линию для поля
    private  void printFieldLine () {
        for (int i =0; i<fieldg.length*2+1; i++) {
            System.out.print("-");
        }
        System.out.println("");
    }
    //запись хода игрока на поле
     void dotField (int y, int x, char dot) {
        fieldg [y][x] = dot;
        printField();
    }
    //Ход человева
    private  void playerMove () {
        int x, y;
        do {
            System.out.println("Введите координаты вашего хода в диапозоне от 1 до " + SIZE_Y);
            System.out.print ("Координат по строке ");
            y = scr.nextInt()-1;
            System.out.print ("Координат по столбцу ");
            x = scr.nextInt()-1;

        } while (!checkMove(y,x));
        dotField(y, x, player_DOT);
    }
    //Ход компьютера
    int AiMove () {
        int x, y;
        //блокировка ходов человека
        for (int v = 0; v<SIZE_Y; v++) {
            for (int h = 0; h < SIZE_X; h++) {
                //анализ наличие поля для проверки
                if (h+SIZE_WIN<=SIZE_X) {                           //по горизонтале
                    if (checkLineHorisont(v, h, player_DOT) == SIZE_WIN - 1) {
                        if (MoveAiLineHorisont(v, h, Ai_DOT)) return indexAi;
                    }

                    if (v - SIZE_WIN > -2) {                            //вверх по диагонале
                        if (checkDiaUp(v, h, player_DOT) == SIZE_WIN - 1) {
                            if (MoveAiDiaUp(v, h, Ai_DOT)) return indexAi;
                        }
                    }
                    if (v + SIZE_WIN <= SIZE_Y) {                       //вниз по диагонале
                        if (checkDiaDown(v, h, player_DOT) == SIZE_WIN - 1) {
                            if (MoveAiDiaDown(v, h, Ai_DOT)) return indexAi;
                        }
                    }
                }
                if (v+SIZE_WIN<=SIZE_Y) {                       //по вертикале
                    if (checkLineVertical(v,h,player_DOT) ==SIZE_WIN-1) {
                         if (MoveAiLineVertical(v,h,Ai_DOT)) return indexAi;
                    }
                }
            }
        }
        //игра на победу
        for (int v = 0; v<SIZE_Y; v++) {
            for (int h = 0; h < SIZE_X; h++) {
                //анализ наличие поля для проверки
                if (h+SIZE_WIN<=SIZE_X) {                           //по горизонтале
                    if (checkLineHorisont(v,h,Ai_DOT) == SIZE_WIN-1) {
                        if (MoveAiLineHorisont(v,h,Ai_DOT)) return indexAi;
                    }

                    if (v-SIZE_WIN>-2) {                            //вверх по диагонале
                        if (checkDiaUp(v, h, Ai_DOT) == SIZE_WIN-1) {
                            if (MoveAiDiaUp(v,h,Ai_DOT)) return  indexAi;
                        }
                    }
                    if (v+SIZE_WIN<=SIZE_Y) {                       //вниз по диагонале
                        if (checkDiaDown(v, h, Ai_DOT) == SIZE_WIN-1) {
                            if (MoveAiDiaDown(v,h,Ai_DOT)) return indexAi;
                        }
                    }

                }
                if (v+SIZE_WIN<=SIZE_Y) {                       //по вертикале
                    if (checkLineVertical(v,h,Ai_DOT) ==SIZE_WIN-1) {
                        if (MoveAiLineVertical(v,h,Ai_DOT)) return indexAi;
                    }
                }
            }
        }

        //случайный ход
        do {
            y = rnd.nextInt(SIZE_Y);
            x = rnd.nextInt(SIZE_X);
        } while (!checkMove(y,x));
        dotField(y, x, Ai_DOT);
        return y *SIZE_Y +x;
    }
    //запись хода компьютера
    private void RecordMoveAi(int y, int x, char dot) {
        fieldg[y][x] = dot;
        indexAi=y*SIZE_Y+x;
        printField();

    }

    //ход компьютера по горизонтале
    private boolean MoveAiLineHorisont(int v, int h, char dot) {
        for (int j = h; j < SIZE_WIN; j++) {
            if ((fieldg[v][j] == EMPTY_DOT)) {
                RecordMoveAi(v,j, dot);
                return true;
            }
        }
        System.out.println("Ошибка по горизонтали");
        return false;
    }
    //ход компьютера по вертикале
    private  boolean MoveAiLineVertical(int v, int h, char dot) {
        for (int i = v; i<SIZE_WIN; i++) {
            if ((fieldg[i][h] == EMPTY_DOT)) {
                RecordMoveAi(i,h, dot);
                return true;
            }
        }
        System.out.println("Ошибка по вертикали");
        return false;
    }
    //проверка заполнения всей линии по диагонале вверх

    private  boolean MoveAiDiaUp(int v, int h, char dot) {
        for (int i = 0, j = 0; j < SIZE_WIN; i--, j++) {
            if ((fieldg[v+i][h+j] == EMPTY_DOT)) {
                RecordMoveAi(v+i,h+j, dot);

                return true;
            }
        }
        System.out.println("Ошибка по диагонали вверх");
        return false;
    }
    //проверка заполнения всей линии по диагонале вниз

    private  boolean MoveAiDiaDown(int v, int h, char dot) {

        for (int i = 0; i < SIZE_WIN; i++) {
            if ((fieldg[i+v][i+h] == EMPTY_DOT)) {
                RecordMoveAi(v+i,h+i, dot);
                return true;
            }
        }
        System.out.println("Ошибка по диагонали вниз");
        return false;
    }
    //проверка заполнения выбранного для хода игроком
    boolean checkMove(int y, int x) {
        if (x<0 || x >=SIZE_X || y<0 || y>=SIZE_Y) return false;
        else if (!(fieldg[y][x]==EMPTY_DOT)) return false;

        return true;
    }
    //проверка на ничью (все  ячейки поля заполнены ходами)
      boolean fullField() {
        for (int i=0; i<SIZE_Y; i++) {
            for (int j=0; j<SIZE_X; j++) {
                if (fieldg[i][j] == EMPTY_DOT) return false;
            }
        }
        return true;
    }

    //проверка победы
      boolean checkWin(char dot) {
        for (int v = 0; v<SIZE_Y; v++){
            for (int h= 0; h<SIZE_X; h++) {
                //анализ наличие поля для проверки
                if (h + SIZE_WIN <= SIZE_X) {                           //по горизонтале
                    if (checkLineHorisont(v, h, dot) >= SIZE_WIN) return true;

                    if (v - SIZE_WIN > -2) {                            //вверх по диагонале
                        if (checkDiaUp(v, h, dot) >= SIZE_WIN) return true;
                    }
                    if (v + SIZE_WIN <= SIZE_Y) {                       //вниз по диагонале
                        if (checkDiaDown(v, h, dot) >= SIZE_WIN) return true;
                    }
                }
                if (v + SIZE_WIN <= SIZE_Y) {                       //по вертикале
                    if (checkLineVertical(v, h, dot) >= SIZE_WIN) return true;
                }
            }
        }
        return false;
    }

    //проверка заполнения всей линии по диагонале вверх

    private  int checkDiaUp(int v, int h, char dot) {
        int count=0;
        for (int i = 0, j = 0; j < SIZE_WIN; i--, j++) {
            if ((fieldg[v+i][h+j] == dot)) count++;
        }
        return count;
    }
    //проверка заполнения всей линии по диагонале вниз

    private  int checkDiaDown(int v, int h, char dot) {
        int count=0;
        for (int i = 0; i < SIZE_WIN; i++) {
            if ((fieldg[i+v][i+h] == dot)) count++;
        }
        return count;
    }
    //провека заполнения всей линии горизонтале
    private  int checkLineHorisont(int v, int h, char dot) {
        int count=0;
        for (int j = h; j < h+SIZE_WIN; j++) {
            if ((fieldg[v][j] == dot)) count++;
        }
        return count;
    }
    //проверка заполнения всей линии по вертикале
    private  int checkLineVertical(int v, int h, char dot) {
        int count=0;
        for (int i = v; i<v+SIZE_WIN; i++) {
            if ((fieldg[i][h] == dot)) count++;
        }
        return count;
    }

    public  void ZeroCross() {

    }
}

