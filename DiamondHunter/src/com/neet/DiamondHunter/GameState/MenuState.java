// The main menu GameState.

package com.neet.DiamondHunter.GameState;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.neet.DiamondHunter.Main.GamePanel;
import com.neet.DiamondHunter.Main.Main;
import com.neet.DiamondHunter.Manager.Content;
import com.neet.DiamondHunter.Manager.GameStateManager;
import com.neet.DiamondHunter.Manager.JukeBox;
import com.neet.DiamondHunter.Manager.Keys;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.WindowEvent;
import javafx.application.Application;

public class MenuState extends GameState {
	
	private BufferedImage bg;
	private BufferedImage diamond;
	private BufferedImage snow;
		//private BufferedImage error;
	private int currentOption = 0;
	private String[] options = {
		"START",
		"EDIT MAP",
		"QUIT"
	};
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		bg = Content.MENUBG[0][0];
		diamond = Content.DIAMOND[0][0];
		snow = Content.SNOW[0][0];
	//error = Content.error[0][0];
		JukeBox.load("/SFX/collect.wav", "collect");
		JukeBox.load("/SFX/menuoption.wav", "menuoption");
	}
	
	public void update() {
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		
		g.drawImage(bg, 0, 0, null);
		/*g.drawImage(diamond, 0, 20, null);*/
		//g.drawImage(error, 113, 20, null);
		//g.drawImage(tree, 1, 76, null);
		//g.drawImage(tree, 86, 76, null);
		Content.drawString(g, options[0], 44, 80);
		Content.drawString(g, options[1], 34, 100);
		Content.drawString(g, options[2], 48, 120);
		
		/*if(currentOption == 0) g.drawImage(diamond, 28, 46, null);
		else if(currentOption == 1) g.drawImage(diamond, 20, 66, null);
		else if(currentOption == 2) g.drawImage(diamond, 32, 86, null);	*/

if(currentOption == 0) g.drawImage(snow, 22, 74, null);
		else if(currentOption == 1) g.drawImage(snow, 12, 92, null);
		else if(currentOption == 2) g.drawImage(snow, 26, 114, null);	
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.DOWN) && currentOption < options.length - 1) {
			JukeBox.play("menuoption");
			currentOption++;
		}
		if(Keys.isPressed(Keys.UP) && currentOption > 0) {
			JukeBox.play("menuoption");
			currentOption--;
		}
		if(Keys.isPressed(Keys.ENTER)) {
			JukeBox.play("collect");
			selectOption();
		}
	}
	
	private void selectOption() {
		if(currentOption == 0) {
			gsm.setState(GameStateManager.PLAY);
		}
		if(currentOption == 1) {
			String[] args = null;
			Application.launch(Main.class, args);
		}
		if(currentOption == 2) {
			System.exit(0);
		}
	}
	
}
