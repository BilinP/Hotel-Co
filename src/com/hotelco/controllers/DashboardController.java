package com.hotelco.controllers;
import java.io.IOException;

import com.hotelco.entities.ReservationSystem;
import com.hotelco.utilities.FXMLPaths;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


/**
 * The ThankYouController class is the associated controller class of the 'ThankYouGUI' view. 
 * It handles connection between the GUI and internal data.
 * 
 * @author      Bilin Pattasseril
 * @version    
 */
public class DashboardController extends BaseController {
    

    
    @FXML
    private Button bookRoomButton;

    @FXML
    private Button cartButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button profileButton;

    @FXML
    private AnchorPane rightAnchor;

    @FXML
    private Button viewBookingButton;

    private final Button[] menubuttons = new Button[5];
    private String currentPath;
    @FXML
    private void initialize() {
        //add non JavaFX related code here
         switchAnchor(FXMLPaths.HOME);
         menubuttons[0] = bookRoomButton;
         menubuttons[1] = cartButton;
         menubuttons[2] = homeButton;
         menubuttons[3] = profileButton;
         menubuttons[4] = viewBookingButton;
         buttonSelection(homeButton);
        Platform.runLater(() -> {
            //add JavaFX related code here
      
        });
    }

    

    @FXML
    void switchToCart(MouseEvent event) {
        buttonSelection(cartButton);
    }

   /**
     * This method is called by pressing the 'home' button.
     * It changes the right anchorpane to the menu scene
     * @param event The 'mouse released' event that is triggered by pressing the 'home' button.
     */
    @FXML
    private void switchToMenu(MouseEvent event) {
       switchAnchor(FXMLPaths.HOME);
       buttonSelection(homeButton);
    }

    @FXML
    void switchToLoginScene(MouseEvent event) {
        ReservationSystem.logout();
        switchScene(FXMLPaths.LOGIN, event);
    }

    @FXML
    void switchToProfile(MouseEvent event) {
        buttonSelection(profileButton);
        switchAnchor(FXMLPaths.PROFILE);
    }

    @FXML
    void switchToReservationHistory(MouseEvent event) {
        switchAnchor(FXMLPaths.RHGUI);
        buttonSelection(viewBookingButton);
    }

    @FXML
    void switchToRoomSearch(MouseEvent event) {
        switchAnchor(FXMLPaths.ROOMS);
        buttonSelection(bookRoomButton);
    }

    private void buttonSelection(Button selectedButton){
        for(Button button: menubuttons){
            if(button!= null && button.equals(selectedButton)){
                button.setStyle("-fx-text-fill: #3c4149;-fx-background-color:#f9bd1a;-fx-border-color: transparent;");
            }else if (button!=null){
                button.setStyle("-fx-text-fill: #f9bd1a;-fx-background-color:#3c4149;-fx-border-color: transparent;");
            }
        }

    }

    
    public void switchAnchor(String path){
        if(!path.equals(currentPath)){
         FXMLLoader loader = new FXMLLoader(getClass().getResource(path));

        try {
            AnchorPane newContent = loader.load();
            rightAnchor.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentPath=path;
        }
    }

    }


