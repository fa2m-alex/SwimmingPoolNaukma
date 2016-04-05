package com.swimmingPool.controllers;

import com.swimmingPool.App;
import com.swimmingPool.resources.CheckGuest;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Alex on 22.02.2016.
 */
public class RootLayoutController {

    @FXML
    private Label label;
    @FXML
    private Button loginButton;

    // Reference to the main application
    private App mainApp;



    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void showSwimmerForm() {
        try {
            label.setText("Плавці");

            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            if(CheckGuest.isGuest)
                loader.setLocation(App.class.getResource("/view/SwimmerFormGuest.fxml"));
            else
                loader.setLocation(App.class.getResource("/view/SwimmerForm.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            mainApp.getRootLayout().setCenter(personOverview);

            // Give the controller access to the main app.
            SwimmerController controller = loader.getController();
            controller.setMainApp(mainApp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showCoachForm() {
        try {
            label.setText("Тренери");

            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            if(CheckGuest.isGuest)
                loader.setLocation(App.class.getResource("/view/CoachFormGuest.fxml"));
            else
                loader.setLocation(App.class.getResource("/view/CoachForm.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            mainApp.getRootLayout().setCenter(personOverview);

            // Give the controller access to the main app.
            CoachController controller = loader.getController();
            controller.setMainApp(mainApp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showCompetitionForm() {
        try {
            label.setText("Змагання");

            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/view/CompetitionForm.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            mainApp.getRootLayout().setCenter(personOverview);

            // Give the controller access to the main app.
            CompetitionController controller = loader.getController();
            controller.setMainApp(mainApp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loginAdmin(){
        if(CheckGuest.isGuest){
            if(isLogIn()){
                CheckGuest.isGuest = false;
                showSwimmerForm();
                loginButton.setText("Log out");
            }

        }
        else{
            CheckGuest.isGuest = true;
            showSwimmerForm();
            loginButton.setText("Log in");
        }
    }

    private boolean isLogIn(){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/view/LoginView.fxml"));
            GridPane page = (GridPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Log In");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            LoginController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
