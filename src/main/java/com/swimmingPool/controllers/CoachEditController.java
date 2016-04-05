package com.swimmingPool.controllers;

import com.swimmingPool.models.Coach;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Alex on 22.02.2016.
 */
public class CoachEditController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField birthdayField;


    private Stage dialogStage;
    private Coach coach;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public void setCoach(Coach coach) {
        this.coach = coach;

        nameField.setText(coach.getName());
        surnameField.setText(coach.getSurname());

        if(coach.getBirthday() != null)
            birthdayField.setText(coach.getBirthday().toString());

        birthdayField.setPromptText("yyyy-mm-dd");
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() throws ParseException {
        if (isInputValid()) {

            coach.setName(nameField.getText());
            coach.setSurname(surnameField.getText());

            String source=birthdayField.getText();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date d = new java.sql.Date(format.parse(source).getTime());
            coach.setBirthday(d);

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }


    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "Невалідне ім'я!\n";
        }
        if (surnameField.getText() == null || surnameField.getText().length() == 0) {
            errorMessage += "Невалідне прізвище!\n";
        }



        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "Невалідна дата народження!\n";
        }

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
}
