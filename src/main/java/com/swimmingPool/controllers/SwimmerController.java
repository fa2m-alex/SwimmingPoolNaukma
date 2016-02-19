package com.swimmingPool.controllers;

import com.swimmingPool.App;
import com.swimmingPool.models.Swimmer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by Alex on 17.02.2016.
 */
public class SwimmerController {
    @FXML
    private TableView<Swimmer> swimmerTable;
    @FXML
    private TableColumn<Swimmer, String> nameColumn;
    @FXML
    private TableColumn<Swimmer, String> surnameColumn;

    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Label growthLabel;


    // Reference to the main application.
    private App mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public SwimmerController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.

        nameColumn.setCellValueFactory(new PropertyValueFactory<Swimmer, String>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Swimmer, String>("surname"));

        // Clear person details.
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        swimmerTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    private void showPersonDetails(Swimmer swimmer) {
        if (swimmer != null) {
            // Fill the labels with info from the person object.
            nameLabel.setText(swimmer.getName());
            surnameLabel.setText(swimmer.getSurname());
            growthLabel.setText(String.valueOf(swimmer.getGrowth()));
            birthdayLabel.setText(swimmer.getBirthday().toString());
        } else {
            // Person is null, remove all the text.
            nameLabel.setText("");
            surnameLabel.setText("");
            growthLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        ObservableList<Swimmer> personData = FXCollections.observableArrayList();
        for (Swimmer swimmer : mainApp.getSwimmers()) {
            personData.add(swimmer);
        }
        swimmerTable.setItems(personData);
    }

    /**
     * Called when the user clicks on the delete button.
     */
   /* @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }*/

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
   /* @FXML
    private void handleNewPerson() {
        PojoPerson tempPerson = new PojoPerson();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }*/

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    /*@FXML
    private void handleEditPerson() {
        PojoPerson selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }*/
}
