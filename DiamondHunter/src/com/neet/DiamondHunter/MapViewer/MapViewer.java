package com.neet.DiamondHunter.MapViewer;

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

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

public class MapViewer implements Initializable {

	GraphicsContext gg;
	Stage primaryStage = new Stage();


	public static int save_axeX, save_axeY, save_boatX, save_boatY = 0;

	private int axeX, axeY, boatX, boatY = 0;

	public void initialize(URL location, ResourceBundle resources) {
		////////////////////////////////////////////////////////////////////////////////////////////////////


		gg = canvas.getGraphicsContext2D();
		readfromfile();

		axeX = save_axeX / 16;
		axeY = save_axeY / 16;
		boatX = save_boatX / 16;
		boatY = save_boatY / 16;

		// GraphicsContext gg = canvas.getGraphicsContext2D();
		loadTiles("/Tilesets/testtileset.gif");
		loadItems("/Sprites/items.gif");
		loadMap("/Maps/testmap.map");
		loadError("/Sprites/error.png");

		// Draw Initial Map and Item Position
		draw(gg);
		gg.drawImage(itemss[0], save_boatX, save_boatY);
		gg.drawImage(itemss[1], save_axeX, save_axeY);
		// Set TextField to default/initial value

		y_Axe.setText(Integer.toString(save_axeY / 16));
		x_Axe.setText(Integer.toString(save_axeX / 16));
		y_Boat.setText(Integer.toString(save_boatY / 16));
		x_Boat.setText(Integer.toString(save_boatX / 16));

		////////////////////////////////////////////////////////////////////////////////////////////////////
		// Objects on GUI

		boat.setLayoutX((save_boatX + 384));
		boat.setLayoutY(save_boatY + 62);
		boat.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(javafx.scene.input.MouseEvent event) {
				select = 1;
			}
		});

		axe.setLayoutX((save_axeX + 384));
		axe.setLayoutY(save_axeY + 62);
		axe.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {

			public void handle(javafx.scene.input.MouseEvent event) {
				select = 0;
			}
		});

		canvas.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {

			public void handle(javafx.scene.input.MouseEvent e) {
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
					if (check_tiles(axeY, axeX)) {
						gg.drawImage(itemss[2], axeX * 16, axeY * 16);
						x_Axe.setText(Integer.toString(axeX));
						y_Axe.setText(Integer.toString(axeY));
						axe.setLayoutX((axeX) * 16 + 384);
						axe.setLayoutY((axeY) * 16 + 62);
					} else {
						x_Axe.setText(Integer.toString(axeX));
						y_Axe.setText(Integer.toString(axeY));
						first_axe = false;
						gg.drawImage(itemss[1], axeX * 16, axeY * 16);
						axe.setLayoutX((axeX) * 16 + 384);
						axe.setLayoutY((axeY) * 16 + 62);
					}

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
					if (check_tiles(boatY, boatX)) {
						gg.drawImage(itemss[2], (boatX) * 16, (boatY) * 16);
						x_Boat.setText(Integer.toString(boatX));
						y_Boat.setText(Integer.toString(boatY));
						boat.setLayoutX((boatX) * 16 + 384);
						boat.setLayoutY((boatY) * 16 + 62);
					} else {

						x_Boat.setText(Integer.toString(boatX));
						y_Boat.setText(Integer.toString(boatY));
						first_boat = false;
						gg.drawImage(itemss[0], boatX * 16, boatY * 16);

						boat.setLayoutX((boatX) * 16 + 384);
						boat.setLayoutY((boatY) * 16 + 62);
					}
				}

			}
		});

		x_Axe.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				draw(gg);

				axeX = Integer.parseInt(x_Axe.getText());
				if (check_tiles(axeY, axeX)) {
					gg.drawImage(itemss[0], (boatX) * 16, (boatY) * 16);
					gg.drawImage(itemss[2], axeX * 16, axeY * 16);

				} else {
					if (axeX >= 40) {
						gg.drawImage(itemss[2], 39 * 16, axeY * 16);
						gg.drawImage(itemss[0], boatX * 16, boatY * 16);
						axe.setLayoutX((39) * 16 + 384);
						axe.setLayoutY((axeY) * 16 + 62);
					} else {
						gg.drawImage(itemss[1], axeX * 16, axeY * 16);
						gg.drawImage(itemss[0], boatX * 16, boatY * 16);
						axe.setLayoutX((axeX) * 16 + 384);
						axe.setLayoutY((axeY) * 16 + 62);

					}

				}
			}

			catch (NullPointerException e) {

			}

			catch (NumberFormatException e) {
				gg.drawImage(itemss[2], 0 * 16, axeY * 16);
				gg.drawImage(itemss[0], boatX * 16, boatY * 16);
				axe.setLayoutX((0) * 16 + 384);
				axe.setLayoutY((axeY) * 16 + 62);

			}
			;
		});

		y_Axe.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				draw(gg);

				axeY = Integer.parseInt(y_Axe.getText());
				if (check_tiles(axeY, axeX)) {
					gg.drawImage(itemss[0], (boatX) * 16, (boatY) * 16);
					gg.drawImage(itemss[2], axeX * 16, axeY * 16);

				} else {
					if (axeY >= 40) {
						gg.drawImage(itemss[2], axeX * 16, 39 * 16);
						gg.drawImage(itemss[0], boatX * 16, boatY * 16);
						axe.setLayoutX((axeX) * 16 + 384);
						axe.setLayoutY((39) * 16 + 62);
					} else {

						gg.drawImage(itemss[1], axeX * 16, axeY * 16);
						gg.drawImage(itemss[0], boatX * 16, boatY * 16);
						axe.setLayoutX((axeX) * 16 + 384);
						axe.setLayoutY((axeY) * 16 + 62);

					}

				}
			}

			catch (NullPointerException e) {

			}

			catch (NumberFormatException e) {

				gg.drawImage(itemss[2], axeX * 16, 0 * 16);
				gg.drawImage(itemss[0], boatX * 16, boatY * 16);
				axe.setLayoutX((axeX) * 16 + 384);
				axe.setLayoutY((0) * 16 + 62);

			}
			;
		});

		// listening to the change in value of the text field of the x value of the boat
		x_Boat.textProperty().addListener((observable, oldValue, newValue) -> {
			draw(gg);
			try {

				boatX = Integer.parseInt(x_Boat.getText());
				if (check_tiles(boatY, boatX)) {
					gg.drawImage(itemss[2], (boatX) * 16, (boatY) * 16);
					gg.drawImage(itemss[1], axeX * 16, axeY * 16);

				}

				else {

					if (boatX >= 40) {
						gg.drawImage(itemss[2], 39 * 16, boatY * 16);
						gg.drawImage(itemss[1], axeX * 16, axeY * 16);
						boat.setLayoutX((39) * 16 + 384);
						boat.setLayoutY((boatY) * 16 + 62);

					} else {

						gg.drawImage(itemss[0], boatX * 16, boatY * 16);// draw the boat
						gg.drawImage(itemss[1], axeX * 16, axeY * 16);// draw the axe
						boat.setLayoutX((boatX) * 16 + 384);
						boat.setLayoutY((boatY) * 16 + 62);

					}
				}

			}

			catch (NullPointerException e) {

			}

			catch (NumberFormatException e) {
				gg.drawImage(itemss[2], 0 * 16, boatY * 16);// draw the boat
				gg.drawImage(itemss[1], axeX * 16, axeY * 16);// draw the axe
				boat.setLayoutX((0) * 16 + 384);
				boat.setLayoutY((boatY) * 16 + 62);

			}
			;
		});

		y_Boat.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				draw(gg);

				boatY = Integer.parseInt(y_Boat.getText());
				if (check_tiles(boatY, boatX)) {
					gg.drawImage(itemss[2], (boatX) * 16, (boatY) * 16);
					gg.drawImage(itemss[1], axeX * 16, axeY * 16);

				} else

				{

					if (boatY >= 40) {
						gg.drawImage(itemss[2], boatX * 16, 39 * 16);
						gg.drawImage(itemss[1], axeX * 16, axeY * 16);
						boat.setLayoutX((boatX) * 16 + 384);
						boat.setLayoutY((39) * 16 + 62);
					}

					else {

						gg.drawImage(itemss[0], boatX * 16, boatY * 16);
						gg.drawImage(itemss[1], axeX * 16, axeY * 16);
						boat.setLayoutX((boatX) * 16 + 384);
						boat.setLayoutY((boatY) * 16 + 62);

					}
				}

			}

			catch (NullPointerException e) {
				e.printStackTrace();

			} catch (NumberFormatException e) {
				gg.drawImage(itemss[2], boatX * 16, 0 * 16);
				gg.drawImage(itemss[1], axeX * 16, axeY * 16);
				boat.setLayoutX((boatX) * 16 + 384);
				boat.setLayoutY((0) * 16 + 62);
			}
			;
		});

		// clicking on save button
		savebutton.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(javafx.scene.input.MouseEvent event) {

				// Insert code for check valid position here
				if (save_axeY == (axeY * 16) && save_axeX == (axeX * 16) && save_boatY == (boatY * 16)
						&& save_boatX == (boatX * 16)) {
					// code to alert that no changes made
					Parent root;
					try {
						root = FXMLLoader.load(getClass().getResource("nochangesdialog.fxml"));
						primaryStage.setTitle("No changes made");
						Scene scene = new Scene(root);
						primaryStage.setScene(scene);
						primaryStage.setResizable(false);
						primaryStage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}

					
					System.out.println("No changes made");
				} else if (map[axeY][axeX] == 20 || map[axeY][axeX] == 21 || map[boatY][boatX] == 20
						|| map[boatY][boatX] == 21 || map[boatY][boatX] == 22) {
					// code to display no-entry sign
				} else {

					Parent root;
					try {
						root = FXMLLoader.load(getClass().getResource("savedialog.fxml"));
						primaryStage.setTitle("Saved changes");
						Scene scene = new Scene(root);
						primaryStage.setScene(scene);
						primaryStage.setResizable(false);
						primaryStage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}

					save_axeY = axeY * 16;
					save_axeX = axeX * 16;
					save_boatY = boatY * 16;
					save_boatX = boatX * 16;

					savetofile();

					System.out.println("Changes saved");
				}

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
		itemss = new Image[3];
		for (int col = 0; col < 2; col++) {
			itemss[col] = new WritableImage(setTile.getPixelReader(), col * tileSize, 16, tileSize, tileSize);
		}
	}

	public boolean check_tiles(int n, int m) {
		try {

			if ((map[n][m] == 20) || map[n][m] == 22 || map[n][m] == 21) {

				return true;
			} else {

				return false;
			}
		} catch (IndexOutOfBoundsException e) {

		}

		return false;
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

	public void loadError(String s) {

		try {

			Image image3 = new Image(s);
			itemss[2] = image3;
		}

		catch (Exception e) {

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

	// clicking on reset button

	@FXML
	public void resetbutton() {
		Parent root;
		try {
			FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("resetdialog.fxml"));
			// fxmlloader.setRoot(this);
			fxmlloader.setController(this);
			// root = FXMLLoader.load(getClass().getResource("resetdialog.fxml"));
			root = fxmlloader.load();
			primaryStage.setTitle("Reset Positions");
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private Button nobutton;

	@FXML
	public void nobutton() {
		Scene scene = nobutton.getScene();
		Stage currentscene = (Stage) scene.getWindow();
		currentscene.hide();
	}

	@FXML
	private Button yesbutton;

	@FXML
	public void yesbutton() {

		save_axeY = 416;
		save_axeX = 592;
		save_boatY = 192;
		save_boatX = 64;
		axeX = save_axeX / 16;
		axeY = save_axeY / 16;
		boatX = save_boatX / 16;
		boatY = save_boatY / 16;

		savetofile();

		draw(gg);
		gg.drawImage(itemss[1], axeX * 16, axeY * 16);
		gg.drawImage(itemss[0], boatX * 16, boatY * 16);
		axe.setLayoutX((axeX) * 16 + 384);
		axe.setLayoutY((axeY) * 16 + 62);
		boat.setLayoutX((boatX) * 16 + 384);
		boat.setLayoutY((boatY) * 16 + 62);
		x_Axe.setText(Integer.toString(axeX));
		y_Axe.setText(Integer.toString(axeY));
		x_Boat.setText(Integer.toString(boatX));
		y_Boat.setText(Integer.toString(boatY));

		Scene scene = yesbutton.getScene();
		Stage currentscene = (Stage) scene.getWindow();
		currentscene.hide();
		// add code to reset positions here
	}

	// clicking on playgame button
	@FXML
	private Button playbutton;

	@FXML
	public void playbutton() {
		 Platform.exit();
	}
	
	//clicking on quitbutton
	@FXML
	private Button quitbutton;

	@FXML
	public void quitbutton() {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("quitdialog.fxml"));
			primaryStage.setTitle("Quit Editor?");
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) {
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
