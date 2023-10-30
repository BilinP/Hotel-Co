package com.hotelco.controllers;
import java.io.IOException;

import com.hotelco.utilities.FXMLPaths;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * The ThankYouController class is the associated controller class of the 'ThankYouGUI' view. 
 * It handles connection between the GUI and internal data.
 * 
 * @author      Bilin Pattasseril
 * @version    
 */
public class DashboardController extends BaseController {

    @FXML
    private BorderPane borderPane;
    @FXML 
    private AnchorPane anchor;
    
    @FXML
    private void initialize() {
        //add non JavaFX related code here
        Platform.runLater(() -> {
            //add JavaFX related code here
        });
    }

    

    /**
     * This method is called by pressing the 'home' button.
     * It changes the right anchorpane to the menu scene
     * @param event The 'mouse released' event that is triggered by pressing the 'home' button.
     */
    @FXML
    private void switchToMenu(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLPaths.NEWMENU));

        try {
            AnchorPane newContent = loader.load();
            anchor.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }


