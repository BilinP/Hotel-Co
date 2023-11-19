package com.hotelco.application;

import com.hotelco.controllers.BaseController;
import com.hotelco.entities.ReservationSystem;

import javafx.animation.PauseTransition;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class IdleTimer {
    
        private static PauseTransition idleTimer;

        public static void initialize(Scene scene, Stage stage, BaseController bc) {
            if (idleTimer == null) {
                idleTimer = new PauseTransition(Duration.seconds(5));
            }

            idleTimer.setOnFinished(e -> {
				if (ReservationSystem.getCurrentUser() != null) {
                    ReservationSystem.logout();
					bc.resetOnIdle(stage);
					idleTimer.playFromStart();
				}
			});

            scene.addEventHandler(Event.ANY, e -> {
                resetTimer();
            });
        }

        private static void resetTimer() {
            idleTimer.stop();
            idleTimer.playFromStart();
        }
}