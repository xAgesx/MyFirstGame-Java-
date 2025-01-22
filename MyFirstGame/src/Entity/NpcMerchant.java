package Entity;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Object.Coin;
import Object.Potion;
import Object.SuperObject;

	public class NpcMerchant extends Entity {
			
		public NpcMerchant(GamePanel g) {
			super(g);
			hitBox = new Rectangle(0,0,g.TileSize-16,g.TileSize-16);
			Direction = "";
			Speed = 1 ;
			GetEntityImg("/npc/old_man_villager") ;
			setDialogue();
			
			inventorySize = 9 ;
			inventory = new SuperObject[inventorySize];
			g.UI.merchant = this ;
			setInventoryItems();
		}
		public void setInventoryItems() {
			inventory[0] = new Coin();
			inventory[1] = new Potion(g);
		}
		public void GetEntityImg(String imagePath) {
			
			try {
				
				up1 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_down_1.png"));
				down1 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_down_1.png"));
				right1 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_down_1.png"));
				left1 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_down_1.png"));
				
				up2 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_down_1.png"));
				down2 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_down_1.png"));
				right2 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_down_1.png"));
				left2 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_down_1.png"));
				
			}catch(IOException e) {
				e.printStackTrace();
				}
		}
		public void setDialogue(){
			
			Dialogue[0] = "Welcome Adventurer !\nConfused aren't ya , well you have been "
					+ "\n summoned by the heavens to save this world ! \n an Isekai story you see ...";
		}
		public void talk() {
			setDialogue();
			super.talk();
			g.UI.commandNum = 0;
			g.gameState = 7 ;
	
		}
}
