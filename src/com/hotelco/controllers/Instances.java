package com.hotelco.controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Instances {
    private static Scene scene;

    private static Stage stage;

    private static DashboardController dbc;

    public static void setScene(Scene sc) {
        scene = sc;
    }

    public static void setStage(Stage st) {
        stage = st;
    }

    public static void setDashboardController(DashboardController d) {
        dbc = d;
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


