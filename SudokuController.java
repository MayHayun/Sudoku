package question1.suduku;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class SudokuController {

    @FXML
    private Button clear;

    @FXML
    private GridPane grid;

    @FXML
    private GridPane grid2;

    @FXML
    private Button set;
    //size of one size of the square
    private final int SIZE = 9;

    //text fields array to store all text fields in table
    private TextField tf[][];

    //to declare if we are in game or in settings
    private boolean inGame = false;

    public void initialize() {
        tf = new TextField[SIZE][SIZE];
        handleTextFields();
    }

    //fill square with text fields
    private void handleTextFields() {
        double length = grid.getPrefWidth();
        for (int i = 0; i < SIZE; i++) { //row
            for (int j = 0; j < SIZE; j++) { //col
                tf[i][j] = new TextField(null);
                tf[i][j].setPrefSize(length / SIZE, length / SIZE); //set square size
                tf[i][j].setAlignment(Pos.CENTER);
                grid.add(tf[i][j], i, j);
                tf[i][j].setStyle("-fx-border-color: rgb(0, 0, 0); fx-border-width: 2px ;"); //set color of text black and line the squares
                if (((j < 3 || j > 5) && (i < 3 || i > 5)) || ((i > 2 && i < 6) && (j > 2 && j < 6))) {
                    String s = tf[i][j].getStyle();
                    tf[i][j].setStyle(s + " -fx-background-color: rgb(211, 211, 211);"); //change backgrownd of innew square
                }
                int finalI = i;
                int finalJ = j;
                tf[i][j].setFont(Font.font(20));
                tf[i][j].setOnKeyPressed(e -> {
                        if(e.getCode() == KeyCode.ENTER){
                            checkValidness(finalI, finalJ);
                    } // check if enter pressed if the input in that square is valid
                });
            }
        }
    }

    //a method to check if enter pressed if the input in that square is valid
    private void checkValidness(int row, int col){
        if(!checkNum(tf[row][col].getText())) {
            Alert a = new Alert(Alert.AlertType.ERROR, "this input is not a valid digit");
            a.showAndWait();
            tf[row][col].clear();
        }
        checkInnerSquare(row, col);
        for(int i = 0;  i < SIZE; i++){
            if(((i != row) && (tf[row][col].getText().equals(tf[i][col].getText()))) || ((i != col) &&(tf[row][col].getText().equals(tf[row][i].getText())))) {
                if(!inGame) {
                    Alert a = new Alert(Alert.AlertType.ERROR, "this number number is conflicting with the game rules");
                    a.showAndWait();
                    tf[row][col].clear();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "this move is incorrect, please fix it!");
                    a.showAndWait();
                    tf[row][col].clear();
                }
            }
        }
    }
    //a method to check if enter pressed if the input in that inner square is valid
    private void checkInnerSquare(int row, int col) {
        int rowIn, colIn;
        rowIn = checkRow(row);
        colIn = checkCol(col);
        for(int i = rowIn; i < rowIn + 3; i++){
            for(int j = colIn; j < colIn + 3; j++){
                if(!((row == i) && (j == col)) && (tf[row][col].getText().equals(tf[i][j].getText()))){
                    if(!inGame) {
                        Alert a = new Alert(Alert.AlertType.ERROR, "this number number is conflicting with the game rules");
                        a.showAndWait();
                        tf[row][col].clear();
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR, "this move is incorrect, please fix it!");
                        a.showAndWait();
                    }
                }
            }
        }
    }
    //methods to check which inner square is relevant
    private int checkCol(int col){
        if(col < 3)
            return 0;
        else if(col >=3 && col < 6)
            return 3;
        else
            return 6;
    }
    private int checkRow(int row){
        if(row < 3)
            return 0;
        else if(row >=3 && row < 6)
            return 3;
        else
            return 6;
    }

    //a method to check if a given string is a digit
    private boolean checkNum(String s){
        if(s == null)
            return false;
        else {
            s = s.trim();
            if(s.length() > 1)
                return false;
            try {
                int d = Integer.parseInt(s);
            } catch (NumberFormatException nfe) {
                return false;
            }
        }
        return true;
    }

    @FXML
    void clearPressed(ActionEvent event) {
        initialize();
        set.setDisable(false);
        inGame = false;
    }

    @FXML
    void setPressed(ActionEvent event) {
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(tf[i][j].getText() != null){
                    String s = tf[i][j].getStyle();
                    tf[i][j].setStyle(s + "-fx-text-fill: blue;"); //change color
                    tf[i][j].setFont(Font.font(tf[i][j].getText(), FontWeight.BOLD, FontPosture.REGULAR, 30)); //change font
                    tf[i][j].setEditable(false);
                }
            }
        }
        inGame = true; //declare a game starting
        set.setDisable(true);
    }
}





