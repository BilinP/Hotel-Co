package com.hotelco.controllers;

import com.hotelco.utilities.FXMLPaths;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * The ScreenSaverController class is the associated controller class of the 'ScreenSaverGUI' view. 
 * It handles connection between the GUI and internal data.
 * 
 * @author      Grigor Azakian
 * @version     %I%, %G%
 */
public class ScreenSaverController extends BaseController {

    @FXML
    private AnchorPane anchor;

    /**
     * ImageView that is set to kick off the initial FadeTransition out.
     */
    @FXML
    private ImageView fo;

    /**
     * ImageView that is set to FadeTransition in first.
     */    
    @FXML
    private ImageView fi;

    /**
     * Triggered when fo or fi's opacity reaches a certain value.
     * Ensures the ChangeListeners do not keep triggering during one iteration of a FadeTransition.
     */    
    private boolean isFadingIn = false;

    /**
     * This method is called immediately upon controller creation.
     * It creates an array of images and sets up the fade in and out transitions of images.
     */
    @FXML
    private void initialize() {
        Image images[] = {
            new Image("/com/hotelco/images/boracay.jpg"),
            new Image("/com/hotelco/images/pool.jpg"),
            new Image("/com/hotelco/images/resort.jpg"),
            new Image("/com/hotelco/images/rocks.jpg"),
            new Image("/com/hotelco/images/zanzibar.jpg"),
        };
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                fo.fitHeightProperty().bind(anchor.heightProperty());
                fo.fitWidthProperty().bind(anchor.widthProperty());
                fi.fitHeightProperty().bind(anchor.heightProperty());
                fi.fitWidthProperty().bind(anchor.widthProperty());                
                setChangeListeners(images);
                playTransition();
            }
        });
    }

    /**
     * Sets up the initital FadeTransition to be played.
     */    
    private void playTransition() {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(3000), fo);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(8500));
        pauseTransition.setOnFinished(e -> {
            fadeOut.play();
        });
        pauseTransition.play();
    }

    /**
     * Sets up the ChangeListeners for fo and fi.
     * The ChangeListeners will listen to the opacity value of fo and fi
     * which will set the next image to be shown and trigger the next FadeTransition.
     * @param images
     */    
    private void setChangeListeners(Image images[]) {
        fo.opacityProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(oldValue.doubleValue() > newValue.doubleValue() && newValue.doubleValue() <= 0.65) {
                    if (!isFadingIn) {
                        isFadingIn = true;
                        Image nextImage = images[0];
                        System.arraycopy(images, 1, images, 0, images.length - 1);
                        images[images.length - 1] = nextImage;
                        fi.setImage(nextImage);
                        FadeTransition fadeIn = new FadeTransition(Duration.millis(3000), fi);
                        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(8.5));
                        fadeIn.setFromValue(0.0);
                        fadeIn.setToValue(1.0);
                        fadeIn.setOnFinished(e -> {
                            pauseTransition.play();
                        });
                        pauseTransition.setOnFinished(e -> {
                            isFadingIn = false;
                            FadeTransition fadeOut = new FadeTransition(Duration.millis(3000), fi);
                            fadeOut.setFromValue(1.0);
                            fadeOut.setToValue(0.0);
                            fadeOut.play();
                        });
                        fadeIn.play();
                    }
                }
            }
        });

        fi.opacityProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(oldValue.doubleValue() > newValue.doubleValue() && newValue.doubleValue() <= 0.65) {
                    if (!isFadingIn) {
                        isFadingIn = true;
                        Image nextImage = images[0];
                        System.arraycopy(images, 1, images, 0, images.length - 1);
                        images[images.length - 1] = nextImage;
                        fo.setImage(nextImage);
                        FadeTransition fadeIn = new FadeTransition(Duration.millis(3000), fo);
                        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(8.5));
                        fadeIn.setFromValue(0.0);
                        fadeIn.setToValue(1.0);
                        fadeIn.setOnFinished(e -> {
                            pauseTransition.play();
                        });
                        pauseTransition.setOnFinished(e -> {
                            isFadingIn = false;
                            FadeTransition fadeOut = new FadeTransition(Duration.millis(3000), fo);
                            fadeOut.setFromValue(1.0);
                            fadeOut.setToValue(0.0);
                            fadeOut.play();                            
                        });
                        fadeIn.play();
                    }
                }
            }
        });
    }

    /**
     * Switches the scene to 'LoginGUI' upon moving the mouse.
     * @param event The 'mouse moved' event that is triggered by moving the mouse.
     */
    @FXML
    void switchToLoginScene(MouseEvent event) {
        switchScene(FXMLPaths.LOGIN, event);
    }

}
