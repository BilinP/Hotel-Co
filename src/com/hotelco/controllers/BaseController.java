package com.hotelco.controllers;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;

/**
 * The BaseController is a base class that all controllers inherit from. 
 * It contains common functionality all controllers must use.
 * 
 * @author      Grigor Azakian
 * @author      Bilin Pattasseril
 * @version     %I%, %G%
 */
public class BaseController  {
    /**
     * This method will switch the scene the user is currently viewing to the provided FXML file located in 'fxmlLocation'.
     * @param fxmlLocation The file path of the FXML file to switch to.
     * @param event The event that triggered the function that called switchScene().
     * @return Returns the controller associated with 'fxmlLocation'. If the method fails to switch the scene, returns null.
     */
    protected BaseController switchScene(String fxmlLocation, Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlLocation));
            Parent root = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            //stage.setFullScreen(true);
            return loader.getController();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}