// The entry point of the game.
// This class loads up a JFrame window and
// puts a GamePanel into it.

package com.neet.DiamondHunter.Main;

import javax.swing.JFrame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Game extends Application {

	public Stage windowmap;

	public static void main(String[] args) {

		JFrame window = new JFrame("Diamond Hunter");

		window.add(new GamePanel());

		window.setResizable(false);
		window.pack();

		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		windowmap = primaryStage;
		System.out.println("MapViewer");
		AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("../MapViewer/MapViewer.fxml"));
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
	
	public void run() {
		String args = null;
		launch(args);
	}
	
	public void setvisible() {
		windowmap.show();
	}

}
