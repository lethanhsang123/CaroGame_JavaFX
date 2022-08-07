package com.example.javafx_tutorial;

import javafx.scene.control.Button;

public class Square  {

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getColNumber() {
        return colNumber;
    }

    public void setColNumber(int colNumber) {
        this.colNumber = colNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    private Button button;
    private boolean status;
    private int colNumber;
    private int rowNumber;


    public Square() {
    }

    public Square(Button button, boolean status, int colNumber, int rowNumber) {
        this.button = button;
        this.status = status;
        this.colNumber = colNumber;
        this.rowNumber = rowNumber;
    }



}
