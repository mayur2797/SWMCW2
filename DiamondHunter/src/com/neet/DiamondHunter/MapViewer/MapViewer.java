package com.neet.DiamondHunter.MapViewer;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

public class MapViewer implements Initializable {

	public MapViewer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GraphicsContext g = canvas.getGraphicsContext2D();
		loadTiles("/Tilesets/testtileset.gif");
		loadItems("/Sprites/items.gif");
		loadMap("/Maps/testmap.map");

		// Draw Initial Map and Item Position
		draw(g);
		g.drawImage(itemss[0], save_boatY, save_boatX);
		g.drawImage(itemss[1], save_axeY, save_axeX);
	}

	// Methods to extract images from resources
	// also to load tiles and map, and draw images in respective position according
	// to map.map
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

	// Variables Declaration
	// private int axeX=26,axeY=37,boatX=12,boatY=4;
	public static int save_axeX = 416, save_axeY = 592, save_boatX = 192, save_boatY = 64;
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
	private TextField axex;

	@FXML
	private Button save;

	@FXML
	private Button back;

	@FXML
	private TextField axey;

	@FXML
	private TextField boatx;

	@FXML
	private TextField boaty;

	@FXML
	private Button boat;

	@FXML
	private Button axe;

}
