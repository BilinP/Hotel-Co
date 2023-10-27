package com.hotelco.application;

import com.hotelco.constants.Constants;
import com.hotelco.entities.ReservationSystem;
import com.hotelco.utilities.DailyTask;
import com.hotelco.utilities.FXMLPaths;
import com.hotelco.utilities.Verifier;
import com.hotelco.utilities.DailyTask;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	private double xOffset = 0;
    private double yOffset = 0;
	@Override
	public void start(Stage primaryStage) {
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource(FXMLPaths.LOGIN));
			Scene scene = new Scene(root, 700, 500);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setTitle("HotelCo");
			primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/com/hotelco/images/hotelco.png")));
			primaryStage.setResizable(false);
			root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            root.setOnMouseDragged(event -> {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            });



			primaryStage.setScene(scene);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		DailyTask.runDailyTasks();
		launch(args);
	}
	
}