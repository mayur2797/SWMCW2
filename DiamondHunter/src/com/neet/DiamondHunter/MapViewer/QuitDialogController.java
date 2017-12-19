package com.neet.DiamondHunter.MapViewer;


import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class QuitDialogController {
	@FXML
	private Button nobuttonback;

	@FXML
	public void nobuttonback() {
		 Scene scene = nobuttonback.getScene();
	     Stage currentscene = (Stage)scene.getWindow();
	     currentscene.hide();
	}
	
	 @FXML
	 private Button yesbuttonback;

	 @FXML
	 	public void yesbuttonback() {
	 	System.exit(0);
	 }
	 	  
	 }

	
	 

