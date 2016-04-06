package com.swimmingPool.controllers;

import com.swimmingPool.App;
import com.swimmingPool.dao.impl.CoachDaoImpl;
import com.swimmingPool.dao.impl.SwimmerDaoImpl;
import com.swimmingPool.dao.interfaces.CoachDao;
import com.swimmingPool.dao.interfaces.SwimmerDao;
import com.swimmingPool.models.Coach;
import com.swimmingPool.models.Swimmer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

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
    private TableColumn<Swimmer, Integer> ratingColumn;

    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Label growthLabel;
    @FXML
    private Label coachLabel;


    // Reference to the main application.
    private App mainApp;

    private List<Swimmer> swimmers = null;
    ObservableList<Swimmer> personData = null;

    private List<Coach> coaches = null;

    private SwimmerDao swimmerDao;
    private CoachDao coachDao;


    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public SwimmerController() {
        swimmerDao = new SwimmerDaoImpl();
        swimmers = swimmerDao.getAll();

        coachDao = new CoachDaoImpl();
        coaches = coachDao.getAll();

        personData = FXCollections.observableArrayList();
        for (Swimmer swimmer : swimmers) {
            personData.add(swimmer);
        }
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
        ratingColumn.setCellValueFactory(new PropertyValueFactory<Swimmer, Integer>("rating_mark"));

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

            if(swimmer.getCoach_id() != 0)
                coachLabel.setText(getCoachByIdFromAll(swimmer.getCoach_id()).toString());
            else
                coachLabel.setText("");
        } else {
            // Person is null, remove all the text.
            nameLabel.setText("");
            surnameLabel.setText("");
            growthLabel.setText("");
            birthdayLabel.setText("");
            coachLabel.setText("");
        }
    }

    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        swimmerTable.setItems(personData);
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void deleteSwimmer() {
        int selectedIndex = swimmerTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            swimmerDao.delete(swimmerTable.getSelectionModel().getSelectedItem());
            swimmerTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }


    @FXML
    private void newSwimmer() {
        Swimmer temp = new Swimmer();
        boolean okClicked = showSwimmerEditDialog(temp);
        if (okClicked) {
            personData.add(temp);
            swimmerDao.insert(temp);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Swimmer has been added");
            alert.setHeaderText("Swimmer has been added");
            alert.showAndWait();
        }
    }

    public boolean showSwimmerEditDialog(Swimmer swimmer) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/view/SwimmerEditForm.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Swimmer");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            SwimmerEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSwimmer(swimmer);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    @FXML
    private void editSwimmer() {
        Swimmer selectedSwimmer = swimmerTable.getSelectionModel().getSelectedItem();
        if (selectedSwimmer != null) {
            boolean okClicked = showSwimmerEditDialog(selectedSwimmer);
            if (okClicked) {
                swimmerDao.update(selectedSwimmer);
                showPersonDetails(selectedSwimmer);
                swimmerTable.refresh();
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
    }

    private Coach getCoachByIdFromAll(int id){
        for (Coach coach:coaches) {
            if(id == coach.getId())
                return coach;
        }
        return null;
    }
}
