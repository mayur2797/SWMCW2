package com.neet.DiamondHunter.MapViewer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	public Stage windowmap;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		windowmap = primaryStage;
		System.out.println("MapViewer");
		AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("MapViewer.fxml"));
		Scene scene = new Scene(root);
		windowmap.setScene(scene);
		windowmap.show();

		windowmap.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent t) {
				Platform.exit();
				// System.exit(0);
				//windowmap.hide();
			}
		});
		windowmap.setResizable(false);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
