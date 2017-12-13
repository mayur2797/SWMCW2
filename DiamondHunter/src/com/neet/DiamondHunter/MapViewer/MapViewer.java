package com.neet.DiamondHunter.MapViewer;

import javafx.event.EventHandler;
import java.awt.event.*;
import java.awt.event.KeyEvent;

import javafx.event.Event;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import javafx.scene.input.*;
import javafx.scene.input.KeyCode;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

import com.neet.DiamondHunter.Main.Game;
import com.neet.DiamondHunter.Manager.Keys;

public class MapViewer implements Initializable{

	Stage primaryStage= new Stage();
	
	/*
	 * public MapViewer() { // TODO Auto-generated constructor stub }
	 */
	
	public static int save_axeX, save_axeY, save_boatX, save_boatY = 0;
	
	//private int axeX = 37, axeY = 26, boatX = 4, boatY = 12;
	private int axeX, axeY, boatX, boatY = 0;

	public void initialize(URL location, ResourceBundle resources) {
		////////////////////////////////////////////////////////////////////////////////////////////////////

		/*Alert saved = new Alert(Alert.AlertType.INFORMATION);
		saved.setTitle("Axe and Boat");
		saved.setHeaderText("Changes have been saved.");*/


		readfromfile();
		
		axeX = save_axeX / 16;
		axeY = save_axeY / 16;
		boatX = save_boatX / 16;
		boatY = save_boatY / 16;
		
		GraphicsContext g = canvas.getGraphicsContext2D();
		loadTiles("/Tilesets/testtileset.gif");
		loadItems("/Sprites/items.gif");
		loadMap("/Maps/testmap.map");

		// Draw Initial Map and Item Position
		draw(g);
		g.drawImage(itemss[0], save_boatX, save_boatY);
		g.drawImage(itemss[1], save_axeX, save_axeY);
		// Set TextField to default/initial value
		

		y_Axe.setText(Integer.toString(save_axeY / 16));
		x_Axe.setText(Integer.toString(save_axeX / 16));
		y_Boat.setText(Integer.toString(save_boatY / 16));
		x_Boat.setText(Integer.toString(save_boatX / 16));
		


		////////////////////////////////////////////////////////////////////////////////////////////////////
		// Objects on GUI

		boat.setLayoutX((save_boatX + 383));
		boat.setLayoutY(save_boatY + 64);
		boat.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(javafx.scene.input.MouseEvent event) {
				select = 1;
			}
		});
		
		
		axe.setLayoutX((save_axeX + 383));
		axe.setLayoutY(save_axeY + 64);
		axe.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
		
			public void handle(javafx.scene.input.MouseEvent event) {
				select = 0;
			}
		});
	

		canvas.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {

			public void handle(javafx.scene.input.MouseEvent e) {
				GraphicsContext gg = canvas.getGraphicsContext2D();
				if (select == 0) {
					draw(gg);
					if (first_boat) {
						gg.drawImage(itemss[0], save_boatX, save_boatY);
					}
					if (!first_boat) {
						gg.drawImage(itemss[0], boatX * 16, boatY * 16);
					}
					axeX = (int) e.getX() / 16;
					axeY = (int) e.getY() / 16;
				    x_Axe.setText(Integer.toString(axeX));
					y_Axe.setText(Integer.toString(axeY));
					first_axe = false;
					gg.drawImage(itemss[1], axeX * 16, axeY * 16);
					axe.setLayoutX((axeX) * 16 + 383);
					axe.setLayoutY((axeY) * 16 + 64);
					
				} else {
					draw(gg);
					if (first_axe) {
						gg.drawImage(itemss[1], save_axeX, save_axeY);
					}
					if (!first_axe) {
						gg.drawImage(itemss[1], axeX * 16, axeY * 16);
					}
					boatX = (int) e.getX() / 16;
					boatY = (int) e.getY() / 16;
					x_Boat.setText(Integer.toString(boatX));
					y_Boat.setText(Integer.toString(boatY));
					first_boat = false;
					gg.drawImage(itemss[0], boatX * 16, boatY * 16);
					
					boat.setLayoutX((boatX) * 16 + 383);
					boat.setLayoutY((boatY) * 16 + 64);
				}
				
			}
		});

		
		x_Axe.textProperty().addListener((observable, oldValue, newValue) -> {
			GraphicsContext gg = canvas.getGraphicsContext2D();
			try{
				draw(gg);
		   // System.out.println("TextField Text Changed (newValue: " + newValue + ")");
		    
		    axeX=Integer.parseInt(x_Axe.getText());
			gg.drawImage(itemss[1], axeX * 16, axeY * 16);
			gg.drawImage(itemss[0], boatX * 16, boatY * 16);
			//gg.drawImage(itemss[0], save_boatX, save_boatY);
			axe.setLayoutX((axeX) * 16 + 383);
			axe.setLayoutY((axeY) * 16 + 64);
			}
			
			catch (NullPointerException e){
				
			}
			
			catch(NumberFormatException e){
				
			};
		});
		
		y_Axe.textProperty().addListener((observable, oldValue, newValue) -> {
			GraphicsContext gg = canvas.getGraphicsContext2D();
			try{
				draw(gg);
		//    System.out.println("TextField Text Changed (newValue: " + newValue + ")");
		    axeY=Integer.parseInt(y_Axe.getText());
		    gg.drawImage(itemss[1], axeX * 16, axeY * 16);
		    gg.drawImage(itemss[0], boatX * 16, boatY * 16);
			axe.setLayoutX((axeX) * 16 + 383);
			axe.setLayoutY((axeY) * 16 + 64);
	
			}
			
			catch (NullPointerException e){
				
			}
			
			catch(NumberFormatException e){
				
			};
		});
		
		
		x_Boat.textProperty().addListener((observable, oldValue, newValue) -> {
			GraphicsContext gg = canvas.getGraphicsContext2D();
			try{
				draw(gg);
		//    System.out.println("TextField Text Changed (newValue: " + newValue + ")");
			boatX=Integer.parseInt(x_Boat.getText());
		    gg.drawImage(itemss[0], boatX * 16, boatY * 16);
		    gg.drawImage(itemss[1], axeX * 16, axeY * 16);
			boat.setLayoutX((boatX) * 16 + 383);
			boat.setLayoutY((boatY) * 16 + 64);
	
			}
			
			catch (NullPointerException e){
				
			}
			
			catch(NumberFormatException e){
				
			};
		});
		
		y_Boat.textProperty().addListener((observable, oldValue, newValue) -> {
			GraphicsContext gg = canvas.getGraphicsContext2D();
			try{
				draw(gg);
		//    System.out.println("TextField Text Changed (newValue: " + newValue + ")");
			boatY=Integer.parseInt(y_Boat.getText());
		    gg.drawImage(itemss[0], boatX * 16, boatY * 16);
		    gg.drawImage(itemss[1], axeX * 16, axeY * 16);
			boat.setLayoutX((boatX) * 16 + 383);
			boat.setLayoutY((boatY) * 16 + 64);
	
			}
			
			catch (NullPointerException e){
				
			}
			
			catch(NumberFormatException e){
				
			};
		});

//clicking on save button
	savebutton.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
		public void handle(javafx.scene.input.MouseEvent event) {
			
			
			//Insert code for check valid position here
			if (save_axeY == (axeY*16) && save_axeX == (axeX * 16) && save_boatY == (boatY * 16) && save_boatX == (boatX *16)) {
				//code to alert that no changes made
				System.out.println("No changes made");
			} else if (map[axeY][axeX] == 20 || map[axeY][axeX] == 21 || map[boatY][boatX] == 20 || map[boatY][boatX] == 21 || map[boatY][boatX] == 22) {
				//code to display no-entry sign
			}
			else {
				
				Parent root;
				try {
					root = FXMLLoader.load(getClass().getResource("savedialog.fxml"));
					 	primaryStage.setTitle("Saved changes");        
				        Scene scene = new Scene(root);
				        primaryStage.setScene(scene);
				        primaryStage.setResizable(false);
				        primaryStage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//close editor when OK is clicked
				 Scene scene = savebutton.getScene();
			     Stage currentscene = (Stage)scene.getWindow();
			     currentscene.hide();
				
				save_axeY = axeY * 16;
				save_axeX = axeX * 16;
				save_boatY = boatY * 16;
				save_boatX = boatX * 16;
				

				savetofile();
		       
		        
				System.out.println("Changes saved");
			}
			
			
			
			// if(first_boat==false){
			/*try {
				if (first_axe == true && first_boat == true) {
					checkInvalidPos(axeY, axeX, boatY, boatX);
				}
				if (first_axe == false && first_boat == false) {
					checkInvalidPos(axeX, axeY, boatX, boatY);
				}
				if (first_axe == true && first_boat == false) {
					checkInvalidPos(axeY, axeX, boatX, boatY);
				}
				if (first_axe == false && first_boat == true) {
					checkInvalidPos(axeX, axeY, boatY, boatX);
				}

				if (first_axe == false) {
					save_axeX = axeY * 16;
					save_axeY = axeX * 16;
				}
				if (first_boat == false) {
					save_boatX = boatY * 16;
					save_boatY = boatX * 16;
				}
				saved.setContentText("Position of Axe (x,y)  : " + save_axeY / 16 + " " + save_axeX / 16
						+ "\nPosition of Boat (x,y) : " + save_boatY / 16 + " " + save_boatX / 16
						+ "\n\n * please note that there is possibility that you might not be able to complete the game due to improper positioning.");
				saved.showAndWait();
			} catch (MyException e) {
				Alerts.display("Alert", e.message);
			}*/

		}
		

		     

	});
}
	/*
	 * @FXML public void back() throws Exception { Scene scene = back.getScene();
	 * Stage currentscene = (Stage) scene.getWindow(); currentscene.hide(); }
	 */

	//////////////////////////////////////////////////
	// Methods to extract images from resources
	// also to load tiles and map, and draw images in respective position according
	////////////////////////////////////////////////// to map.map
	public void loadItems(String s) {
		Image setTile = new Image(s);
		itemss = new Image[2];
		for (int col = 0; col < 2; col++) {
			itemss[col] = new WritableImage(setTile.getPixelReader(), col * tileSize, 16,

					tileSize, tileSize);
		}
	}

	public void loadTiles(String s) {

		try {

			Image setTile = new Image(s);
			numTilesAcross = (int) setTile.getWidth() / tileSize;
			tiles = new Image[2][numTilesAcross];

			for (int col = 0; col < numTilesAcross; col++) {
				tiles[0][col] = new WritableImage(setTile.getPixelReader(), col * tileSize, 0, tileSize, tileSize);
				tiles[1][col] = new WritableImage(setTile.getPixelReader(), col * tileSize, tileSize, tileSize,
						tileSize);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void loadMap(String s) {

		try {

			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];

			String delims = "\\s+";
			for (int row = 0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for (int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void draw(GraphicsContext g) {

		for (int row = 0; row < 40; row++) {

			if (row >= numRows)
				break;

			for (int col = 0; col < 40; col++) {

				if (col >= numCols)
					break;
				if (map[row][col] == 0)
					continue;

				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;

				g.drawImage(tiles[r][c], col * tileSize, row * tileSize);

			}

		}

	}
	
	public void savetofile() {
		try {
			PrintWriter out = new PrintWriter(new FileWriter(fileName));
			out.println(save_axeX);
			out.println(save_axeY);
			out.println(save_boatX);
			out.println(save_boatY);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readfromfile() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			save_axeX = Integer.parseInt(in.readLine());
			save_axeY = Integer.parseInt(in.readLine());
			save_boatX = Integer.parseInt(in.readLine());
			save_boatY = Integer.parseInt(in.readLine());
			in.close();
		} catch (FileNotFoundException e2) {
			save_axeY = 416;
			save_axeX = 592;
			save_boatY = 192;
			save_boatX = 64;
			e2.printStackTrace();
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	//clicking on reset button
			@FXML
			public void resetbutton() {
				Parent root;
				try {
					root = FXMLLoader.load(getClass().getResource("resetdialog.fxml"));
					 	primaryStage.setTitle("Reset Positions");        
				        Scene scene = new Scene(root);
				        primaryStage.setScene(scene);
				        primaryStage.setResizable(false);
				        primaryStage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
	//clicking on back button
			@FXML
			public void backbutton() {
				Parent root;
				try {
					root = FXMLLoader.load(getClass().getResource("backdialog.fxml"));
					 	primaryStage.setTitle("Exit Editor");        
				        Scene scene = new Scene(root);
				        primaryStage.setScene(scene);
				        primaryStage.setResizable(false);
				        primaryStage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	
	//////////////////////////////////////////////////
			
	// Variables Declaration

	
	String fileName = "coordinates.txt";


	int select = 0;
	boolean first_boat = true, first_axe = true;

	private int[][] map;
	private int tileSize = 16;
	private int numRows;
	private int numCols;

	private Image[][] tiles;
	private Image[] itemss;
	public Image image;
	private int numTilesAcross;
	
	

	///////////////////////////////////////////////////////
	@FXML
	public Canvas canvas;

	@FXML
	private TextField x_Axe;

	@FXML
	private Button savebutton;

	@FXML
	public Button backbutton;

	@FXML
	private Button resetbutton;

	@FXML
	private TextField y_Axe;

	@FXML
	private TextField x_Boat;

	@FXML
	private TextField y_Boat;

	@FXML
	private Button boat;

	@FXML
	private Button axe;

}
