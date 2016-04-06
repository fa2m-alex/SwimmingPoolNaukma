package com.swimmingPool.controllers;

import com.swimmingPool.dao.impl.SwimmerDaoImpl;
import com.swimmingPool.dao.interfaces.SwimmerDao;
import com.swimmingPool.models.Injury;
import com.swimmingPool.models.Swimmer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by west on 05.04.2016.
 */
public class InjuryEditController {
    @FXML
    private TextField titleField;
    @FXML
    private TextField dateField;

    @FXML
    private ChoiceBox<Swimmer> swimmerIdField;

    private Stage dialogStage;
    private Injury injury;
    private boolean okClicked = false;

    private List<Swimmer> swimmers = null;
    ObservableList<Swimmer> personData = null;
    private SwimmerDao swimmerDao;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        swimmerDao = new SwimmerDaoImpl();
        swimmers = swimmerDao.getAll();

        personData = FXCollections.observableArrayList();
        for (Swimmer swimmer : swimmers) {
            personData.add(swimmer);
        }

        swimmerIdField.setItems(personData);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public boolean isOkClicked() {
        return okClicked;
    }

    public void setInjury(Injury injury) {
        this.injury = injury;

        titleField.setText(injury.getTitle());

        if(injury.getDate() != null)
            dateField.setText(injury.getDate().toString());

        dateField.setPromptText("yyyy-mm-dd");

        swimmerIdField.getSelectionModel().select(getSwimmerFromPersonData(injury.getSwimmer_id()));
    }

    private Swimmer getSwimmerFromPersonData(int index){
        for (Swimmer swimmer:personData) {
            if(swimmer.getId() == index)
                return swimmer;
        }
        return null;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() throws ParseException {
        if (isInputValid()) {

            injury.setTitle(titleField.getText());

            if(swimmerIdField.getSelectionModel().getSelectedItem() != null)
                injury.setSwimmer_id(swimmerIdField.getSelectionModel().getSelectedItem().getId());
            else
                injury.setSwimmer_id(0);

            String source=dateField.getText();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date d = new java.sql.Date(format.parse(source).getTime());
            injury.setDate(d);

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
        /*String errorMessage = "";

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

        if (coachIdField.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "No valid coach id!\n";
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
        }*/
        return true;
    }

}
