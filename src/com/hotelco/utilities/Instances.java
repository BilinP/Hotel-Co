package com.hotelco.utilities;

import com.hotelco.controllers.DashboardController;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Instances {
    private static Scene scene;

    private static Stage stage;

    private static DashboardController dbc;

    public static void setScene(Scene scene) {
        Instances.scene = scene;
    }

    public static void setStage(Stage stage) {
        Instances.stage = stage;
    }

    public static void setDashboardController(DashboardController dbc) {
        Instances.dbc = dbc;
    }

    public static Scene getScene() {
        return scene;
    }

    public static Stage getStage() {
        return stage;
    }

    public static DashboardController getDashboardController() {
        return dbc;
    }

}


