package com.hotelco.application;

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

public class IdleTimer {
    
        private static PauseTransition idleTimer;

        private IdleTimer() {}

        public static void initialize(Scene scene, Stage stage) {
            if (idleTimer == null) {
                idleTimer = new PauseTransition(Duration.seconds(5));
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

        private static void resetOnIdle(Stage stage) {
            try {
                FXMLLoader loader = new FXMLLoader(IdleTimer.class.getResource(FXMLPaths.LOGIN));
                Parent root = loader.load();    
                Scene scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
                IdleTimer.initialize(scene, stage);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}