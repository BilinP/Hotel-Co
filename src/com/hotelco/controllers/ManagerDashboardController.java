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
 * The Dashboard class is the associated controller class of the 'AdminDashboard' view. 
 * It handles connection between the GUI and internal data. 
 * 
 * @author      Bilin Pattasseril
 * 
 */
public class ManagerDashboardController extends BaseController {
    

    /**
     * Button named 'Book Room' that calls 'switchToRoomSearch()' when pressed.
     */
    @FXML
    private Button revenueButton;

    /**
     * Button named 'cart' that calls 'switchToMenu()' when pressed.
     */
    @FXML
    private Button vacancyButton;

    /**
     * Button named 'home' that calls 'switchToCart()' when pressed.
     */
    @FXML
    private Button reservationsButton;

    /**
     * Button named 'profile' that calls 'switchToProfile()' when pressed.
     */
    @FXML
    private Button exportButton;

    /**
     *  A AnchorPane which is the right of the dashboard.
     */
    @FXML
    public  AnchorPane rightAnchor;


     /**
     * Array of Button  that stores all the menu button.
     */
    private final Button[] menubuttons = new Button[4];

    /**
     * String which stores the current anchor scene.
     */
    private String currentPath;


    /**
     * Called immediately upon controller creation.
     * It changes the anchor to the home and initalize each menu button
     * 
     */
    @FXML
    private void initialize() {
        switchAnchor(FXMLPaths.HOME);
         menubuttons[0] = revenueButton;
         menubuttons[1] = vacancyButton;
         menubuttons[2] = reservationsButton;
         menubuttons[3] = exportButton;
         buttonSelection(revenueButton);
        Platform.runLater(() -> {
      
        });
    }

    

    
    /** 
     * @param event
     */
    @FXML
    void switchToReservactions(MouseEvent event) {
        switchAnchor(FXMLPaths.ALL_RESERVATION);
        buttonSelection(reservationsButton);
    }

   /**
     * This method is called by pressing the 'home' button.
     * It changes the right anchorpane to the home scene
     * @param event The 'mouse released' event that is triggered by pressing the 'home' button.
     */
    @FXML
    private void switchToRevenue(MouseEvent event) {
       switchAnchor(FXMLPaths.HOME);
       buttonSelection(revenueButton);
    }

    
    /** 
     * Method is isused by pressing the 
     * @param event
     */
    @FXML
    void switchToLoginScene(MouseEvent event) {
        ReservationSystem.logout();
        switchScene(FXMLPaths.LOGIN);
    }

    /**
     * This method is called by pressing the 'Reservation History' button.
     * It changes the right anchorpane to the ReservationHistoryGUI scene
     * @param event The 'mouse released' event that is triggered by pressing the 'Reservation History' button.
     */
    @FXML
    void switchToRoomVacancy(MouseEvent event) {
        switchAnchor(FXMLPaths.RHGUI);
        buttonSelection(vacancyButton);
    }

   /**
     * This method is called by pressing the 'Book Room' button.
     * It changes the right anchorpane to the RoomChoiceGUI scene
     * @param event The 'mouse released' event that is triggered by pressing the 'Book ROom' button.
     */
    @FXML
    void switchToExport(MouseEvent event) {
        switchAnchor(FXMLPaths.ROOMS);
        buttonSelection(exportButton);
    }

    /**
     * This method is used to change the style of the button to emphasis the 
     * * current selection and the non current selection.
     * @param selectedButton The  button that is selected
     */
    private void buttonSelection(Button selectedButton){
        for(Button button: menubuttons){
            if(button!= null && button.equals(selectedButton)){
                button.setStyle("-fx-text-fill: #3c4149;-fx-background-color:#f9bd1a;-fx-border-color: transparent;");
            }else if (button!=null){
                button.setStyle("-fx-text-fill: #f9bd1a;-fx-background-color:#3c4149;-fx-border-color: transparent;");
            }
        }

    }

    /**
     * This method will switch the anchor the user is currently viewing to the provided FXML file located in 'fxmlLocation'.
     * @param path The file path of the FXML file to switch to.
     */
     public BaseController switchAnchor(String path){
        if(!path.equals(currentPath)){
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            try {
                AnchorPane newContent = loader.load();
                rightAnchor.getChildren().setAll(newContent);
                return loader.getController();
            } catch (IOException e) {
                e.printStackTrace();
            }
            currentPath = path;
        }
        return null;
    }
}


