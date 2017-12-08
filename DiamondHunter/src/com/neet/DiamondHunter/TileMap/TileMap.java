// The tile map class contains a loaded tile set\
// and a 2d array of the map.
// Each index in the map corresponds to a specific tile.

package com.neet.DiamondHunter.TileMap;

import java.awt.Graphics2D;
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

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import javax.imageio.ImageIO;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

import com.neet.DiamondHunter.Main.GamePanel;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class TileMap {
	
	// position
	private int x;
	private int y;
	private int xdest;
	private int ydest;
	private int speed;
	private boolean moving;
	
	// bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	// map
	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	// tileset
	private BufferedImage tileset;
	private int numTilesAcross;
	private Tile[][] tiles;
	
	// drawing
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	public TileMap(int tileSize) {
		this.tileSize = tileSize;
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		System.out.println("number of rows to draw "+numRowsToDraw);
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;
		System.out.println("number of colums  to draw "+numColsToDraw);
		speed = 4;
	}
	
public void loadTiles(String s) {
		
		try {

			tileset = ImageIO.read(
				getClass().getResourceAsStream(s)
			);
			System.out.println("the size of the tiles are "+tileSize);
			numTilesAcross = tileset.getWidth() / tileSize;
			System.out.println("number of tiles to be drawn across ,tiles accross'= "+numTilesAcross);
			tiles = new Tile[2][numTilesAcross];
			System.out.println("tiles "+Arrays.toString(tiles));
			Image setTile = new Image(s);
			BufferedImage subimage;
		
			for(int col = 0; col < numTilesAcross; col++) {
				subimage = tileset.getSubimage(
							col * tileSize,
							0,
							tileSize,
							tileSize
						);
				tiles[0][col] = new Tile(subimage, Tile.NORMAL);
				subimage = tileset.getSubimage(
							col * tileSize,
							tileSize,
							tileSize,
							tileSize
						);
				tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
		}
			
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void loadMap(String s) {
		
		try {
			
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(
						new InputStreamReader(in)
					);
			
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			System.out.println(numCols+"  "+numRows);
			map = new int[numRows][numCols];
			width = numCols * tileSize;
			System.out.println("width is "+width);
			height = numRows * tileSize;
			
			xmin = GamePanel.WIDTH - width;
			xmin = -width;
			xmax = 0;
			ymin = GamePanel.HEIGHT - height;
			ymin = -height;
			ymax = 0;
			
			String delims = "\\s+";
			System.out.println("token");
			for(int row = 0; row < numRows; row++) {
				String line = br.readLine();			
				String[] tokens = line.split(delims);
				System.out.println(Arrays.toString(tokens));
				for(int col = 0; col < numCols; col++) {	
					map[row][col] =Integer.parseInt(tokens[col]);
				}
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public int getTileSize() { return tileSize; }
	public int getx() { return x; }
	public int gety() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getNumRows() { return numRows; }
	public int getNumCols() { return numCols; }
	public int getType(int row, int col) {
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
	}
	public int getIndex(int row, int col) {
		return map[row][col];
	}
	
	public boolean isMoving() { return moving; }
	
	public void setTile(int row, int col, int index) {
		map[row][col] = index;
	}
	public void replace(int i1, int i2) {
		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numCols; col++) {
				if(map[row][col] == i1) map[row][col] = i2;
			}
		}
	}
	
	public void setPosition(int x, int y) {
		xdest = x;
		ydest = y;
	}
	public void setPositionImmediately(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void fixBounds() {
		if(x < xmin) x = xmin;
		if(y < ymin) y = ymin;
		if(x > xmax) x = xmax;
		if(y > ymax) y = ymax;
	}
	
	public void update() {
		if(x < xdest) {
			x += speed;
			if(x > xdest) {
				x = xdest;
			}
		}
		if(x > xdest) {
			x -= speed;
			if(x < xdest) {
				x = xdest;
			}
		}
		if(y < ydest) {
			y += speed;
			if(y > ydest) {
				y = ydest;
			}
		}
		if(y > ydest) {
			y -= speed;
			if(y < ydest) {
				y = ydest;
			}
		}
		
		fixBounds();
		
		colOffset = -this.x / tileSize;
		rowOffset = -this.y / tileSize;
		
		if(x != xdest || y != ydest) moving = true;
		else moving = false;
		
	}
	
	public void draw(Graphics2D g) {
		
		for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
		
			if(row >= numRows) break;
			
			for(int col = colOffset; col < colOffset + numColsToDraw; col++) {
				
				if(col >= numCols) break;
				if(map[row][col] == 0) continue;
				
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				
				g.drawImage(
					tiles[r][c].getImage(),
					x + col * tileSize,
					y + row * tileSize,
					null
				);
				
			}
			
		}
		
	}
	
}

