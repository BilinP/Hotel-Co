package com.hotelco.application;


import com.hotelco.connections.DatabaseConnection;
import com.hotelco.connections.DbSandbox;
import com.hotelco.entities.ReservationSystem;
import com.hotelco.utilities.FXMLPaths;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		ReservationSystem reservationSystem = new ReservationSystem();
		try {
			Parent root = FXMLLoader.load(getClass().getResource(FXMLPaths.LOGIN));
			Scene scene = new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setTitle("HotelCo");
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}