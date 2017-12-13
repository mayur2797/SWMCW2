package com.neet.DiamondHunter.Main;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	private static JFrame window;
	public Stage windowmap;
	
	public void start(Stage primaryStage) {
		try {
			windowmap = primaryStage;
			System.out.println("Test!");
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("../MapViewer/MapViewer.fxml"));
			Scene scene = new Scene(root);
			windowmap.setScene(scene);
			windowmap.hide();
			
			//game
			window = new JFrame("Diamond Hunter");

			window.add(new GamePanel());

			window.setResizable(false);
			window.pack();

			window.setLocationRelativeTo(null);
			window.setVisible(true);
			window.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
			
			

			
			windowmap.setOnCloseRequest(new EventHandler<WindowEvent> (){
				public void handle(WindowEvent t){
					//Platform.exit();
					//System.exit(0);
					windowmap.hide();
				}
			});
			windowmap.setResizable(false);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static JFrame getWindow() {
		return window;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
