package com.swimmingPool.controllers;

import com.swimmingPool.models.Swimmer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alex on 20.02.2016.
 */
public class SwimmerEditController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField birthdayField;
    @FXML
    private TextField growthField;
    @FXML
    private TextField coachIdField;


    private Stage dialogStage;
    private Swimmer swimmer;
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


    public void setSwimmer(Swimmer swimmer) {
        this.swimmer = swimmer;

        nameField.setText(swimmer.getName());
        surnameField.setText(swimmer.getSurname());
        growthField.setText(String.valueOf(swimmer.getGrowth()));
        coachIdField.setText(Integer.toString(swimmer.getCoach_id()));

        if(swimmer.getBirthday() != null)
            birthdayField.setText(swimmer.getBirthday().toString());

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

            swimmer.setName(nameField.getText());
            swimmer.setSurname(surnameField.getText());
            //swimmer.setBirthday(new Date(birthdayField.getText()));
            swimmer.setGrowth(Integer.parseInt(growthField.getText()));
            swimmer.setCoach_id(Integer.parseInt(coachIdField.getText()));

            String source=birthdayField.getText();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date d = new java.sql.Date(format.parse(source).getTime());
            swimmer.setBirthday(d);

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
            errorMessage += "No valid name!\n";
        }
        if (surnameField.getText() == null || surnameField.getText().length() == 0) {
            errorMessage += "No valid surname!\n";
        }

        if (growthField.getText() == null || growthField.getText().length() == 0) {
            errorMessage += "No valid growth!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(growthField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid growth (must be an integer)!\n";
            }
        }

        if (coachIdField.getText() == null || coachIdField.getText().length() == 0) {
            errorMessage += "No valid coach id!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(coachIdField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid coach id (must be an integer)!\n";
            }
        }


        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}