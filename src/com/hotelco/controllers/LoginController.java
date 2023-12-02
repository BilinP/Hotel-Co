package com.hotelco.controllers;

import com.hotelco.entities.Password;
import com.hotelco.entities.ReservationSystem;
import com.hotelco.entities.User;
import com.hotelco.utilities.DatabaseUtil;
import com.hotelco.utilities.FXMLPaths;
import com.hotelco.utilities.Instances;
import com.hotelco.utilities.Verifier;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * The LoginController class is the associated controller class of the 'LoginGUI' view. 
 * It handles connection between the GUI and internal data.
 * 
 * @author      Grigor Azakian
 */
public class LoginController extends BaseController {

    /**
    * ImageView that contains the whole image with rounded corners.
    */
    @FXML
    private ImageView image;

    /**
    * ImageView that contains the right half of the image.
    */    
    @FXML
    private ImageView imageRight;
    /**
     * Text that can be displayed to notify the user of any potential invalid inputs.
     */
    @FXML
    private Text notification;
    
    /**
     * TextField that contains the email of the user.
     */
    @FXML
    private TextField email;

    /**
     * TextField that contains the password of the user.
     */
    @FXML
    private PasswordField password;

    /**
     * PauseTransition that is set to switch to ScreenSaverGUI
     * after the user is idle for some amount of time
     */
    private PauseTransition idleTimer;

    /**
     * EventHandler used to determine if the user is making inputs.
     */
    private EventHandler<Event> handler;

    /**
     * Rounds the corners of the beach ImageView upon initialization of the Controller.
     * @author Grigor Azakian
     */
    @FXML
    private void initialize() {     
           Rectangle rectangle = new Rectangle(
                image.getFitWidth(),
                image.getFitHeight()
           );

            Rectangle clip = new Rectangle(
                image.getFitWidth() / 2,
                image.getFitHeight()
           );

            rectangle.setArcWidth(50);
            rectangle.setArcHeight(50);
            clip.setX(imageRight.getFitWidth() / 2);
            image.setClip(rectangle);
            imageRight.setClip(clip);   
             
        Platform.runLater(() -> {
            email.getScene().getRoot().requestFocus(); 
        });
    }

    public LoginController(TextField email, PasswordField password){
       this.email=email;
       this.password=password;
    }

    /**
     * This method is called when pressing the 'Login' button.
     * It handles input verification and user login. It will enter 'MenuGUI' upon successful login.
     * @param event The 'mouse released' event that is triggered by pressing the 'Login' button.
     * @author Grigor Azakian
     * @author Daniel Schwartz
     */
    @FXML
    public int login(Event event) {
        if (email.getText().isEmpty() 
        || password.getText().isEmpty()) {
            notification.setText("Please enter username and password");
            notification.setFill(Color.RED);
            return 0;
        }
        String emailStr = email.getText();
        if(DatabaseUtil.doesEmailExist(emailStr)){
            if (Verifier.verifyPassword(emailStr, password.getText())){
                idleTimer.stop();
                Instances.getScene().removeEventHandler(Event.ANY, handler);
                ReservationSystem.setCurrentUser(new User(emailStr, true));
                if(ReservationSystem.getCurrentUser().getIsManager()){
                    switchScene(FXMLPaths.MANAGER_DASHBOARD);
                    return 1;
                }else{
                    switchScene(FXMLPaths.DASHBOARD);  
                    return 2;
                }     
            }
            else{
                notification.setText("Invalid Username/Password!");
                notification.setFill(Color.RED);
                return 3;
            }
        }
        else {
            notification.setText("Invalid Username/Password!");
            notification.setFill(Color.RED);
            return 4;
        }
    }


    /**
     * This method is called by pressing the 'Don't have an account? Create an account.' text.
     * It exits the 'LoginGUI' and enters the 'CreateAccountGUI'.
     * @param event The 'mouse released' event that is triggered by pressing the 'Don't have an account? Create an account.' text.
     */
    @FXML
    private void switchToCreateAccount(MouseEvent event) {
        idleTimer.stop();
        Instances.getScene().removeEventHandler(Event.ANY, handler);
        switchScene(FXMLPaths.CREATE_ACCOUNT);
    }

    @FXML
    void switchToResetPassword(MouseEvent event) {
        ResetPasswordController rpc = (ResetPasswordController) switchScene(FXMLPaths.RESET_PASSWORD);
        rpc.initializeIdleTimer();
    }
    
    /**
     * This method is called by pressing any key on the keyboard.
     * It will call the 'login()' function if the key pressed is the 'Enter' key.
     * @param event The 'key pressed' event that is triggered by pressing any key on the keyboard.
     * @author Bilin P
     */
    @FXML
    private void enter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            login(event);
        }
    }

    /**
     * This method is a setter for the variable 'notification'.
     * @param  text The string to set the variable 'notification' to.
     * @param color sets the string color for 'notification' to specifed color and if it is null it will not set a color. 
     * @author Bilin P
     */
    void setNotification(String text, Color color) {
        notification.setText(text);
        if(color != null) {
            notification.setFill(color);
        }
    }

    /**
     * Must be called upon initializing LoginController.
     * Sets up idleTimer and the current scene's EventHandler.
     * idleTimer will call switchToScreenSaver() when its timer reaches 0.
     * The scene will track every event and reset the timer when an event is made.
     */
    public void initializeIdleTimer() {
        idleTimer = new PauseTransition(Duration.seconds(10));
        idleTimer.setOnFinished(e -> {
            switchToScreenSaver();
        });

        handler = e -> {
            idleTimer.stop();
            idleTimer.playFromStart();
        };

        Instances.getScene().addEventHandler(Event.ANY, handler);
        idleTimer.play();
    }

    /**
     * Called if idleTimer reaches the end of its timer.
     * Switches the scene to ScreenSaverGUI.
     */
    private void switchToScreenSaver() {
        idleTimer.stop();
        Instances.getScene().removeEventHandler(Event.ANY, handler);
        switchScene(FXMLPaths.SCREENSAVER);
    }

}