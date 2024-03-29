// The main menu GameState.

package com.neet.DiamondHunter.GameState;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.neet.DiamondHunter.Main.Game;
import com.neet.DiamondHunter.Manager.Content;
import com.neet.DiamondHunter.Manager.GameStateManager;
import com.neet.DiamondHunter.Manager.JukeBox;
import com.neet.DiamondHunter.Manager.Keys;

import javafx.application.Application;

public class MenuState extends GameState {


	private BufferedImage bg;
	private BufferedImage snow;
	private int currentOption = 0;

	//adding the option to Edit Map in the menu
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
		snow = Content.SNOW[0][0];
		JukeBox.load("/SFX/collect.wav", "collect");
		JukeBox.load("/SFX/menuoption.wav", "menuoption");
	}

	public void update() {
		handleInput();
	}

	public void draw(Graphics2D g) {

		g.drawImage(bg, 0, 0, null);
		//drawing the options 
		Content.drawString(g, options[0], 44, 80);
		Content.drawString(g, options[1], 34, 100);
		Content.drawString(g, options[2], 48, 120);

		//drawing the Santa hat accordingly
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

	//the processes that will happen once the user selects one of the options available
	private void selectOption() {
		if(currentOption == 0) {
			gsm.setState(GameStateManager.PLAY);
		}
		if(currentOption == 1) {
			String[] args = null;			
			Application.launch(Game.class, args);
			gsm.setState(GameStateManager.PLAY);
		}
		if(currentOption == 2) {
			System.exit(0);
		}
	}

}
