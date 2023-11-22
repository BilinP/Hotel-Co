package com.hotelco.application;

import com.hotelco.constants.Constants;
import com.hotelco.controllers.LoginController;
import com.hotelco.entities.ReservationSystem;
import com.hotelco.utilities.FXMLPaths;

import javafx.animation.PauseTransition;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * The IdleTimer class sets up a singleton PauseTransition that logs a user out of the system
 * if the user has been idle for a certain period of time.
 * 
 * @author      Grigor Azakian
 * @version %I%, %G%
 */
public class IdleTimer {
    
        /**
         * PauseTransition that logs a user out of the system after its timer ends.
         */
        private static PauseTransition idleTimer;

        /**
         * Private constructor to ensure IdleTimer will remain a singleton.
         */
        private IdleTimer() {}

        /**
         * Sets up idleTimer if it hasn't already been initialized and attaches an EventHandler to the current scene.
         * idleTimer will call resetOnIdle() upon finishing.
         * The EventHandler will reset the timer upon any Event being detected.
         * @param scene
         * @param stage
         */
        public static void initialize(Scene scene, Stage stage) {
            if (idleTimer == null) {
                idleTimer = new PauseTransition(Duration.seconds(Constants.IDLE_TIMEOUT));
            }

            idleTimer.setOnFinished(e -> {
				if (ReservationSystem.getCurrentUser() != null) {
                    ReservationSystem.logout();
					resetOnIdle(stage);
					idleTimer.playFromStart();
				}
			});

            scene.addEventHandler(Event.ANY, e -> {
                idleTimer.stop();
                idleTimer.playFromStart();                
            });
        }

        /**
         * Switches the scene to 'LoginGUI'. Called by idleTimer upon its timer finishing.
         * @param stage Current instance of the stage.
         */
        private static void resetOnIdle(Stage stage) {
            try {
                FXMLLoader loader = new FXMLLoader(IdleTimer.class.getResource(FXMLPaths.LOGIN));
                Parent root = loader.load();    
                Scene scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
                LoginController lc = (LoginController) loader.getController();
                lc.initializeIdleTimer(stage, scene);
                IdleTimer.initialize(scene, stage);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}