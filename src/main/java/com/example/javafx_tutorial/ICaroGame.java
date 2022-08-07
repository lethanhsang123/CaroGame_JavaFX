package com.example.javafx_tutorial;

import javafx.scene.control.Button;

public interface ICaroGame {
    public void click(Square square);

    public boolean checkWinCondition(Square square);

    public void playAgain();

    public void unDo();

    public void newGame();

    public void startGame();

}
