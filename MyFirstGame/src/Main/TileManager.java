package Main;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import Tiles.Tile;

public class TileManager {

	GamePanel g ;
	Tile[] tile;
	int TileMapNum[][];
	
	TileManager( GamePanel g ){
		
		this.g = g ;
		tile  = new Tile[50];		
		getTileImage();
		TileMapNum = new int [g.maxWorldCol][g.maxWorldRow];
		LoadMap(g.currentMap);
	
	}
	
	public void LoadMap(String mapName) {
		
		try {
			
			InputStream readMap = getClass().getResourceAsStream("/maps/"+mapName+".txt");
			BufferedReader read = new BufferedReader(new InputStreamReader(readMap));	
			g.currentMap = mapName ;
			
			int col = 0 ;
			int row = 0;
			while(col < g.maxWorldCol && row < g.maxWorldRow) {
				
				String Line = read.readLine();
				
				while(col<g.maxWorldCol) {
				
					String numbers[] = Line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					
					TileMapNum[col][row] = num ;
					col++;
				}
				if(col== g.maxWorldCol) {
					col = 0 ; 
					row ++ ;
				}	
			}
			read.close();
		}catch(Exception e) {
			
		}
	}
	
	public void getTileImage() {
		try {
			
			tile[0] = new Tile();
			tile[0].Image = ImageIO.read(getClass().getResourceAsStream("/Tiles/earth.png"));
			
			tile[1] = new Tile();
			tile[1].Image = ImageIO.read(getClass().getResourceAsStream("/Tiles/grass.png"));
			
			tile[2] = new Tile();
			tile[2].Image = ImageIO.read(getClass().getResourceAsStream("/Tiles/sand.png"));
		
			tile[3] = new Tile();
			tile[3].Image = ImageIO.read(getClass().getResourceAsStream("/Tiles/tree.png"));
			tile[3].collision = true ;
			
			tile[4] = new Tile();
			tile[4].Image = ImageIO.read(getClass().getResourceAsStream("/Tiles/water.png"));
			
			tile[5] = new Tile();
			tile[5].Image = ImageIO.read(getClass().getResourceAsStream("/Tiles/water_border_down.png"));
			
			tile[6] = new Tile();
			tile[6].Image = ImageIO.read(getClass().getResourceAsStream("/Tiles/wall_test.png"));
			tile[6].collision = true ;
			
			tile[7] = new Tile();
			tile[7].Image = ImageIO.read(getClass().getResourceAsStream("/Tiles/dungeonFloor1.png"));
			tile[7].collision = false ;
			
		}
		
		catch(IOException e) {
			e.printStackTrace();
		 
		}
		
	}
	
	public void Draw(Graphics2D g1) {
		
		int worldCol = 0 ;
		int worldRow = 0 ;
		
		
		while(worldCol < g.maxWorldCol && worldRow < g.maxWorldRow) {
			
			int worldX = worldCol * g.TileSize ;
			int worldY = worldRow * g.TileSize ;
			int RelativeX = worldX - g.player.worldX + g.player.RelativeX ;
			int RelativeY = worldY - g.player.worldY + g.player.RelativeY ;
			if(worldX > g.player.worldX - g.player.RelativeX - g.TileSize && worldX < g.player.worldX + g.player.RelativeX + g.TileSize 
					&& worldY > g.player.worldY - g.player.RelativeY - g.TileSize && worldY < g.player.worldY + g.player.RelativeY + g.TileSize ) {
				
				g1.drawImage(tile[TileMapNum[worldCol][worldRow]].Image, RelativeX, RelativeY, g.TileSize, g.TileSize, null);
			
			}
			
			worldCol++;
			
			
			if(worldCol == g.maxWorldCol) {
				worldCol = 0 ;
				
				worldRow ++;
			
			}
		}
		
		
	}
	
}
