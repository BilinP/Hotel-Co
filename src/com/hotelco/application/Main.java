package com.hotelco.application;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.hotelco.utilities.DailyTask;
import com.hotelco.utilities.FXMLPaths;
import com.hotelco.utilities.FrequentTask;

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
			DailyTask.scheduleDailyTasks();
			FrequentTask.scheduleFrequentTasks();
			Parent root = FXMLLoader.load(getClass().getResource(FXMLPaths.LOGIN));
			Scene scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
			primaryStage.centerOnScreen();
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setTitle("HotelCo");
			primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/com/hotelco/images/hotelco.png")));
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
			//primaryStage.setFullScreen(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	 public static void main(String[] args) throws AddressException, MessagingException, IOException {
		//String[] testargs = {"bilin.pattasseril.563@my.csun.edu", "for realsy", "we won a award!"};
		
		//SendMail.startSend(testargs[0], testargs[1], testargs[2]);
		launch(args);
	}
	
}