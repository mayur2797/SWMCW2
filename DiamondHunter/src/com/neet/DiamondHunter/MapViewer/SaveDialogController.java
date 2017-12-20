package com.neet.DiamondHunter.MapViewer;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class SaveDialogController {
	@FXML
	private Button okbutton;

	@FXML
	public void okbutton() {
		 Scene scene = okbutton.getScene();
	     Stage currentscene = (Stage)scene.getWindow();
	     currentscene.hide();
	}
	     
	
}
