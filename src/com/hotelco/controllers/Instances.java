package com.hotelco.controllers;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.hotelco.utilities.FXMLPaths;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Instances {
    private static Scene scene;

    private static Stage stage;

    private static Map<String, Scene> scenes;
    private static Map<Scene, BaseController> controllers;
    //private Map<Scene, BaseController> controllers;

    private static BaseController curController;

    public static void initializeScenes() {
        scenes = new HashMap<>();
        controllers = new HashMap<>();
        Field[] paths = FXMLPaths.obtainAllPaths();
        for (int i = 0; i < paths.length; i++) {
        try {
            FXMLLoader loader = new FXMLLoader(Instances.class.getResource((String) paths[i].get(null)));
            Parent root = loader.load();
            Scene scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight()); 
            controllers.put(scene, loader.getController());
            scenes.put((String) paths[i].get(null), scene);           
        } catch (Exception e) {
            e.printStackTrace();
            try {
                System.out.println((String) paths[i].get(null));
            } catch (Exception d) {

            }
            
        }
        }
    }

    public static BaseController switchScene(String path) {
        stage.setScene(scenes.get(path));
        scene = scenes.get(path);
        FXMLLoader loader = new FXMLLoader(Instances.class.getResource(path));
        loader.setRoot(scenes.get(path).getRoot());
        controllers.get(scenes.get(path)).initialize();
        stage.show();
        if (curController != null)
            curController.cleanup();
        curController = controllers.get(scenes.get(path));
        return curController;
    }

    public static BaseController switchAnchor(String path, String currentPath, AnchorPane rightAnchor){
        if(!path.equals(currentPath)){
            AnchorPane newContent = (AnchorPane) Instances.selectScene(path).getRoot();
            rightAnchor.getChildren().setAll(newContent);
            currentPath = path;
            controllers.get(scenes.get(path)).initialize();
            return Instances.selectController(path);   
        }
        return null;
    }    


    public static void setScene(Scene scene) {
        Instances.scene = scene;
    }

    public static void setStage(Stage stage) {
        Instances.stage = stage;
    }

    public static Scene getScene() {
        return scene;
    }

    public static Stage getStage() {
        return stage;
    }

    public static DashboardController getDashboardController() {
        return (DashboardController) controllers.get(scenes.get(FXMLPaths.DASHBOARD));
    }

    static Scene selectScene(String path) {
        return scenes.get(path);
    }

    static BaseController selectController(String path) {
        return controllers.get(scenes.get(path));
    }

}


