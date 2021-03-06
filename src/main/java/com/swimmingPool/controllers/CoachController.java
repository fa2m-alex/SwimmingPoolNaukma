package com.swimmingPool.controllers;

import com.swimmingPool.App;
import com.swimmingPool.dao.impl.CoachDaoImpl;
import com.swimmingPool.dao.interfaces.CoachDao;
import com.swimmingPool.models.Coach;
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
 * Created by Alex on 22.02.2016.
 */
public class CoachController {
    @FXML
    private TableView<Coach> coachTable;
    @FXML
    private TableColumn<Coach, String> nameColumn;
    @FXML
    private TableColumn<Coach, String> surnameColumn;

    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label birthdayLabel;



    // Reference to the main application.
    private App mainApp;

    private List<Coach> coaches = null;
    ObservableList<Coach> personData = null;

    private CoachDao coachDao;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public CoachController() {
        coachDao = new CoachDaoImpl();
        coaches = coachDao.getAll();

        personData = FXCollections.observableArrayList();
        for (Coach coach : coaches) {
            personData.add(coach);
        }
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.

        nameColumn.setCellValueFactory(new PropertyValueFactory<Coach, String>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Coach, String>("surname"));

        // Clear person details.
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        coachTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    private void showPersonDetails(Coach coach) {
        if (coach != null) {
            // Fill the labels with info from the person object.
            nameLabel.setText(coach.getName());
            surnameLabel.setText(coach.getSurname());
            birthdayLabel.setText(coach.getBirthday().toString());
        } else {
            // Person is null, remove all the text.
            nameLabel.setText("");
            surnameLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        coachTable.setItems(personData);
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void deleteCoach() {
        int selectedIndex = coachTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            coachDao.delete(coachTable.getSelectionModel().getSelectedItem());
            coachTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Не обрано");
            alert.setHeaderText("Не обрано жодного тренера");
            alert.setContentText("Будь ласка оберіть тренера");

            alert.showAndWait();
        }
    }


    @FXML
    private void newCoach() {
        Coach temp = new Coach();
        boolean okClicked = showCoachEditDialog(temp);
        if (okClicked) {
            personData.add(temp);
            coachDao.insert(temp);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Тренера було додано");
            alert.setHeaderText("Тренера було додано");
            alert.showAndWait();
        }
    }



    @FXML
    private void editCoach() {
        Coach selectedCoach = coachTable.getSelectionModel().getSelectedItem();
        if (selectedCoach != null) {
            boolean okClicked = showCoachEditDialog(selectedCoach);
            if (okClicked) {
                coachDao.update(selectedCoach);
                showPersonDetails(selectedCoach);
                coachTable.refresh();
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Не обрано");
            alert.setHeaderText("Не обрано жодного тренера");
            alert.setContentText("Будь ласка оберіть тренера");

            alert.showAndWait();
        }
    }

    public boolean showCoachEditDialog(Coach coach) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/view/CoachEditForm.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редагування тренера");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            CoachEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCoach(coach);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
