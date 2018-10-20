package main;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import java.util.Random;

public class Controller {

    @FXML
    private CheckBox isHumanFirst;

    @FXML
    private Button btnStart;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btn6;

    @FXML
    private Button btn9;

    @FXML
    private Button btn5;

    @FXML
    private Button btn4;

    @FXML
    private Button btn7;

    @FXML
    private Button btn8;

    @FXML
    private Label resultLabel;

    // Custom fields
    private Button[][] btnArr = new Button[3][3]; // for checking results

    private enum Player {AI, HUMAN}

    private Player currentPlayer;
    private String SELECTED_STYLE = ".selected-btn", USUAL_STYLE=".usual-btn";
    private String humanSymbol = "X", compSymbol = "O";
    private Random rand = new Random();

    public Controller(){}

    @FXML
    void initialize(){
        this.btnArr = new Button[][]{{btn1, btn2, btn3}, {btn4, btn5, btn6}, {btn7, btn8, btn9}};
        addHandlers();
    }


    private void addHandlers(){

        disableAllButtons(true); // before game starts
        for (Button[] btnRow : btnArr){
            for (Button btn : btnRow){
                btn.setOnAction(this::onGameBtnClicked);
            }
        }

        btnStart.setOnAction(this::startGame);



    }

    private void fakeComputerMakeStep(){
        int a = rand.nextInt(3), b = rand.nextInt(3);
        Button curBtn = btnArr[a][b];

        while (curBtn.isDisable()){
            a = rand.nextInt(3);
            b = rand.nextInt(3);
            curBtn = btnArr[a][b];
        }

        onGameBtnClicked(curBtn);
    }

    private void onGameBtnClicked(Button btn){
        btn.setDisable(true);
        btn.setStyle(SELECTED_STYLE);

        if (currentPlayer == Player.HUMAN){
            btn.setText(humanSymbol);
        } else {
            btn.setText(compSymbol);
        }

        afterBtnClicked();
    }

    private void onGameBtnClicked(Event event){
        Button btn = (Button) event.getTarget();
        btn.setDisable(true);
        btn.setStyle(SELECTED_STYLE);

        if (currentPlayer == Player.HUMAN){
            btn.setText(humanSymbol);
        } else {
            btn.setText(compSymbol);
        }

        afterBtnClicked();
    }

    private void afterBtnClicked(){
        if (!checkWin()){

            if (checkDraw()) {
                endGameByDraw();
            } else {
                changeTurn();

                if (currentPlayer == Player.AI){
                    fakeComputerMakeStep();
                }
            }
        } else {
            endGame();
        }
    }

    private void startGame(Event event){
        disableAllButtons(false);

        currentPlayer = isHumanFirst.isSelected() ? Player.HUMAN : Player.AI;
        btnStart.setDisable(true);
        isHumanFirst.setDisable(true);

        clearGameTable();

        if (currentPlayer == Player.AI){
            fakeComputerMakeStep();
        }
    }

    private void endGame(){
        showAsResult(String.format("%s won the game!", currentPlayer.toString()));
        restartGame();
    }

    private void endGameByDraw(){
        showAsResult("It's draw!");
        restartGame();
    }

    private void restartGame(){
        disableAllButtons(true);
        btnStart.setDisable(false);
        isHumanFirst.setDisable(false);
    }

    private void showAsResult(String text){
        resultLabel.setText(text);
    }
    
    private void clearGameTable(){
        for (Button[] btnRow : btnArr){
            for (Button btn : btnRow){
                btn.setStyle(USUAL_STYLE);
                btn.setText("");
            }
        }
        showAsResult("Tic Tac Toe game!");
    }

    private void disableAllButtons(boolean disable){
        for (Button[] btnRow : btnArr) {
            for (Button btn : btnRow) {
                btn.setDisable(disable);
            }
        }
    }

    private boolean checkWin() {
        String playerText = currentPlayer == Player.HUMAN ? humanSymbol : compSymbol;
        // только не бейте палками
        int counter = 0;

        for (int i = 0; i < btnArr.length; i++) {
            counter = 0;
            for (int j = 0; j < btnArr[i].length; j++) {
                if (btnArr[i][j].getText().equalsIgnoreCase(playerText)) {
                    counter++;
                }
            }
            if (counter == 3) {
                return true;
            }
        }


        for (int i = 0; i < btnArr.length; i++) {
            counter = 0;
            for (int j = 0; j < btnArr[i].length; j++) {
                if (btnArr[j][i].getText().equalsIgnoreCase(playerText)) {
                    counter++;
                }
            }

            if (counter == 3) {
                return true;
            }
        }

        // пожалуйста
        return btnArr[0][0].getText().equalsIgnoreCase(playerText)
                && btnArr[1][1].getText().equalsIgnoreCase(playerText)
                && btnArr[2][2].getText().equalsIgnoreCase(playerText)
                || btnArr[0][2].getText().equalsIgnoreCase(playerText)
                && btnArr[1][1].getText().equalsIgnoreCase(playerText)
                && btnArr[2][0].getText().equalsIgnoreCase(playerText);
    }


    private void changeTurn(){
        currentPlayer = currentPlayer == Player.HUMAN ? Player.AI : Player.HUMAN;
    }

    private boolean checkDraw(){
        int counter = 0;
        for (Button[] btns: btnArr){
            for (Button btn: btns) {
                if (btn.isDisable()){
                    counter++;
                }
            }
        }

        return counter == 9;
    }

}
