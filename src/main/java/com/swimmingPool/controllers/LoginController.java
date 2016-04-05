package com.swimmingPool.controllers;

import com.swimmingPool.resources.CheckGuest;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Created by Alex on 04.04.2016.
 */
public class LoginController {

    private boolean isOkClicked = false;
    private Stage dialogStage;

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;


    public boolean isOkClicked() {
        return isOkClicked;
    }

    public LoginController(){

    }

    @FXML
    private void initialize(){

    }

    @FXML
    private void handleOk(){
        if(isInputValid()){
            isOkClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (!loginField.getText().equals(CheckGuest.login) && !passwordField.getText().equals(CheckGuest.password)) {
            errorMessage += "Невірні дані!\n";
        }
        /*if (passwordField.getText() == null || passwordField.getText().length() == 0) {
            errorMessage += "No valid password!\n";
        }*/

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Невалідні поля");
            alert.setHeaderText("Виправте будь ласка");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
