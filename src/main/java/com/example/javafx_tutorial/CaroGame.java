package com.example.javafx_tutorial;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

public class CaroGame extends Application implements ICaroGame {

    Stage window;
    Square box[][] = new Square[20][20];

    User user1 = new User(1, "User1", "X");
    User user2 = new User(2, "User2", "O");

    Stack<Square> list = new Stack<>();

    User currentUser = user1;

    Label lb = new Label();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        window = primaryStage;
        window.setTitle("Game Caro");

        Scene scene1 = startGame();

        window.setScene(scene1);
        window.show();

    }

    @Override
    public Scene startGame() {

        GridPane pane = new GridPane();

        for (int i = 0; i < box.length; i++) {
            for (int j = 0; j < box[0].length; j++) {
                Button button = new Button();
                button.setPrefSize(20, 20);
                button.setPadding(new Insets(5));

                Square square = new Square(button, true, j, i);

                button.setOnAction(e -> {
                    click(square);
                });
                box[i][j] = square;
                pane.addRow(i, square.getButton());
            }
        }

        Button btNewGame = new Button();
        btNewGame.setText("New Game");
        btNewGame.setOnAction(e -> {
            newGame();
        });
        Button btUndo = new Button();
        btUndo.setText("UnDo");
        btUndo.setOnAction(e -> {
            unDo();
        });
        Button btExit = new Button();
        btExit.setText("Exit");
        btExit.setOnAction(e -> {
            Platform.exit();
        });

        lb.setText(currentUser.getName() + " Turn " + currentUser.getValue());
        pane.addColumn(21, lb, btNewGame, btUndo, btExit);


        Scene scene1 = new Scene(pane, 600, 600);

        return scene1;
    }

    @Override
    public void click(Square square) {
        if (square.isStatus()) {
            list.push(square);
            square.getButton().setText(currentUser.getValue());
            square.setStatus(!square.isStatus());
            if (currentUser.getValue().equals("X")) {
                square.getButton().setStyle("-fx-text-fill: red ; -fx-font-weight: bold");
            } else {
                square.getButton().setStyle("-fx-text-fill: blue ; -fx-font-weight: bold");
            }

            if (checkWinCondition(square)) {
                playAgain();
                System.out.println("New Game");
            }else{
                System.out.println("NotWin");
            }

            currentUser = currentUser.getID() == user1.getID() ? user2 : user1;
            lb.setText(currentUser.getName() + " Turn " + currentUser.getValue());
        }
    }

    @Override
    public boolean checkWinCondition(Square square) {

        int i = square.getRowNumber();
        int j = square.getColNumber();

        int d = 0, k = i, h;
        // kiểm tra hàng
        while (box[k][j].getButton().getText() == box[i][j].getButton().getText()) {
            d++;
            k++;
        }
        k = i - 1;
        while (box[k][j].getButton().getText() == box[i][j].getButton().getText()) {
            d++;
            k--;
        }
        if (d > 4) return true;
        d = 0;
        h = j;

        // kiểm tra cột
        while (box[i][h].getButton().getText() == box[i][j].getButton().getText()) {
            d++;
            h++;
        }
        h = j - 1;
        while (box[i][h].getButton().getText() == box[i][j].getButton().getText()) {
            d++;
            h--;
        }
        if (d > 4) return true;

        // kiểm tra đường chéo 1
        h = i;
        k = j;
        d = 0;
        while (box[i][j].getButton().getText() == box[h][k].getButton().getText()) {
            d++;
            h++;
            k++;
        }
        h = i - 1;
        k = j - 1;
        while (box[i][j].getButton().getText() == box[h][k].getButton().getText()) {
            d++;
            h--;
            k--;
        }
        if (d > 4) return true;

        // kiểm tra đường chéo 2
        h = i;
        k = j;
        d = 0;
        while (box[i][j].getButton().getText() == box[h][k].getButton().getText()) {
            d++;
            h++;
            k--;
        }
        h = i - 1;
        k = j + 1;
        while (box[i][j].getButton().getText() == box[h][k].getButton().getText()) {
            d++;
            h--;
            k++;
        }
        if (d > 4) return true;
        // nếu không đương chéo nào thỏa mãn thì trả về false.
        return false;
    }

    @Override
    public void playAgain() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("CARO GAME");
        alert.setHeaderText("congratulation");
        alert.setContentText("YOU WIN");

        ButtonType btAgain = new ButtonType("Play Again", ButtonBar.ButtonData.YES);
        ButtonType btCancel = new ButtonType("Cancel", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().removeAll(ButtonType.OK);
        alert.getButtonTypes().addAll(btAgain, btCancel);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get().getButtonData() == ButtonBar.ButtonData.YES) {
            System.out.println("Play Again");
            newGame();
        } else if (result.get().getButtonData() == ButtonBar.ButtonData.NO) {
            System.out.println("Exit");
            Platform.exit();
        }
    }

    @Override
    public void unDo() {
        if (!list.empty()) {
            Square sq = list.pop();
            sq.setStatus(!sq.isStatus());

            Button bt = box[sq.getRowNumber()][sq.getColNumber()].getButton();
            bt.setText(null);

            currentUser = currentUser.getID() == user1.getID() ? user2 : user1;
            lb.setText(currentUser.getName() + " Turn " + currentUser.getValue());
        }
    }

    @Override
    public void newGame() {

        while (!list.isEmpty()){
            Square sq = list.pop();
            sq.setStatus(!sq.isStatus());

            Button bt = box[sq.getRowNumber()][sq.getColNumber()].getButton();
            bt.setText(null);
        }

        currentUser = currentUser == user1 ? user2 : user1;
        user1.setValue(user1.getValue().equals("X")  ? "O" : "X");
        user2.setValue(user2.getValue().equals("X")  ? "O" : "X");

        lb.setText(currentUser.getName() + " Turn " + currentUser.getValue());
    }


}
