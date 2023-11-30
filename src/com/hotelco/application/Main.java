package com.hotelco.application;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.hotelco.controllers.Instances;
import com.hotelco.controllers.LoginController;
import com.hotelco.utilities.DailyTask;
import com.hotelco.utilities.FXMLPaths;
import com.hotelco.utilities.FrequentTask;
import com.hotelco.utilities.IdleTimer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 * Initializes the JavaFX application, setting up the primary stage, 
 * loading the login scene, and performing various tasks upon application start.
 * @author     Bilin Pattasseril, Grigor Azakian

 */
public class Main extends Application {
	
	/**
	* This method performs the necessary initialization steps, such as scheduling
 	*  daily and frequent tasks, loading the login scene, and configuring the primary stage.
 	*/
	@Override
	public void start(Stage primaryStage) {
		
		try {
			DailyTask.scheduleDailyTasks();
			FrequentTask.scheduleFrequentTasks();
			/*
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPaths.LOGIN));
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
			Instances.setScene(scene);
			Instances.setStage(primaryStage);
			IdleTimer.initialize();
			LoginController lc = (LoginController) fxmlLoader.getController();
			lc.initializeIdleTimer();
			*/
			Instances.setStage(primaryStage);
			Instances.initializeScenes();
			primaryStage.centerOnScreen();
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setTitle("HotelCo");
			primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/com/hotelco/images/hotelco.png")));
			primaryStage.setResizable(false);
			Instances.switchScene(FXMLPaths.LOGIN);
			//primaryStage.setScene();
			//primaryStage.show();
			//primaryStage.setFullScreen(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
 	* This method serves as the starting point for the application's execution.
 	*/
	 public static void main(String[] args) throws AddressException, MessagingException, IOException {
		launch(args);
	}
}