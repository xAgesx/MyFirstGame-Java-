package Object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Main.GamePanel;

public class SuperObject {
	
	public BufferedImage image ;
	public String name ;
	public boolean collision = false;
	public int worldX,worldY;
	public Rectangle hitBox;
	public int DefaultHitBoxX = 0;
	public int DefaultHitBoxY = 0;
	public int baseDamage = 0 ;
	public String description = "";
	public int currentItemIndex ;
	public boolean obtainable = true ;
	public int price = -1;

	public void Draw(GamePanel g , Graphics2D g1 ) {
		
		int RelativeX = worldX - g.player.worldX + g.player.RelativeX ;
		int RelativeY = worldY - g.player.worldY + g.player.RelativeY ;
		if(worldX > g.player.worldX - g.player.RelativeX - g.TileSize && worldX < g.player.worldX + g.player.RelativeX + g.TileSize 
				&& worldY > g.player.worldY - g.player.RelativeY - g.TileSize && worldY < g.player.worldY + g.player.RelativeY + g.TileSize ) {
			
			g1.drawImage(image, RelativeX, RelativeY, g.TileSize, g.TileSize, null);
		
		}
	}
	public void use() {
		
	}
	public void dropItem() {
		
	}
}
