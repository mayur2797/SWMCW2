package com.neet.DiamondHunter.MapViewer;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ResetDialogController {

	@FXML
	private Button nobutton;

	@FXML
	public void nobutton() {
		 Scene scene = nobutton.getScene();
	     Stage currentscene = (Stage)scene.getWindow();
	     currentscene.hide();
	}
	
	 @FXML
	 	private Button yesbutton;

	@FXML
	 	public void yesbutton() {
	 	   Scene scene = yesbutton.getScene();
	 	   Stage currentscene = (Stage)scene.getWindow();
	 	   currentscene.hide();	
	 	   //add code to reset positions here
	}  
}
