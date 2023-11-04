package com.hotelco.application;

import com.hotelco.utilities.DailyTask;
import com.hotelco.utilities.FXMLPaths;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource(FXMLPaths.LOGIN));
			Scene scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(),Screen.getPrimary().getBounds().getHeight() );
			primaryStage.centerOnScreen();
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setTitle("HotelCo");
			primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/com/hotelco/images/hotelco.png")));
			primaryStage.setResizable(false);
			

			primaryStage.setScene(scene);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		DailyTask.scheduleDailyTasks();
		launch(args);
	}
	
}