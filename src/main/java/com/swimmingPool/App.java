package com.swimmingPool;

import com.swimmingPool.controllers.RootLayoutController;
import com.swimmingPool.controllers.SwimmerController;
import com.swimmingPool.controllers.SwimmerEditController;
import com.swimmingPool.dao.impl.SwimmerDaoImpl;
import com.swimmingPool.dao.interfaces.SwimmerDao;
import com.swimmingPool.models.Swimmer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Created by Alex on 17.02.2016.
 */
public class App extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;


    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public App(){

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Swimming Pool");

        initRootLayout();

    }


    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            controller.showSwimmerForm();

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
