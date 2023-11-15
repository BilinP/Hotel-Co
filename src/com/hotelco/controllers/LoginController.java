package com.hotelco.controllers;

import com.hotelco.entities.ReservationSystem;
import com.hotelco.entities.User;
import com.hotelco.utilities.DatabaseUtil;
import com.hotelco.utilities.FXMLPaths;
import com.hotelco.utilities.Verifier;

import javafx.application.Platform;
import javafx.event.Event;
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

/**
 * The LoginController class is the associated controller class of the 'LoginGUI' view. 
 * It handles connection between the GUI and internal data.
 * 
 * @author      Grigor Azakian
 * @version     %I%, %G%
 */
public class LoginController extends BaseController {

    @FXML
    private ImageView image;

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
     * Rounds the corners of the beach ImageView upon initialization of the Controller.
     * @author Grigor Azakian
     */
    @FXML
    private void initialize() {     
        Platform.runLater(() -> {
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
        });
    }

    /**
     * This method is called when pressing the 'Login' button.
     * It handles input verification and user login. It will enter 'MenuGUI' upon successful login.
     * @param event The 'mouse released' event that is triggered by pressing the 'Login' button.
     * @author Grigor Azakian
     * @author Daniel Schwartz
     */
    @FXML
    private void login(Event event) {
        if (email.getText().isEmpty() || password.getText().isEmpty()) {
            notification.setText("Please enter username and password");
            notification.setFill(Color.RED);
            return;
        }
        String emailStr = email.getText();
        if(DatabaseUtil.doesEmailExist(emailStr)){
            if (Verifier.verifyPassword(emailStr, password.getText()))
            {
                ReservationSystem.setCurrentUser(new User(emailStr));
                switchScene(FXMLPaths.DASHBOARD, event);
            }
            else{
                notification.setText("Invalid Username/Password!");
                notification.setFill(Color.RED);
            }
        }
        else {
            notification.setText("Invalid Username/Password!");
            notification.setFill(Color.RED);
        }
    }

    /**
     * This method is called by pressing the 'Don't have an account? Create an account.' text.
     * It exits the 'LoginGUI' and enters the 'CreateAccountGUI'.
     * @param event The 'mouse released' event that is triggered by pressing the 'Don't have an account? Create an account.' text.
     */
    @FXML
    private void switchToCreateAccount(MouseEvent event) {
        switchScene(FXMLPaths.CREATE_ACCOUNT, event);
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
     * @param  The string to set the variable 'notification' to.
     * @parm color sets the string color for 'notification' to specifed color and if it is null it will not set a color. 
     * @author Bilin P
     */
    void setNotification(String s, Color color) {
        notification.setText(s);
        if(color!=null){
        notification.setFill(color);
        }
    }

}