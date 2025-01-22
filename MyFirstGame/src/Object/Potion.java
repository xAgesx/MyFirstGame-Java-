package Object;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class Potion extends SuperObject{
	
		GamePanel g ;
	public Potion(GamePanel g) {
		this.g = g ;
		name = "Potion";
		price = 10 ;
		description = "["+name+"]\nThis will heal your injuries by \n1 Heart ";
		hitBox = new Rectangle(0,0,48,48);
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/Potion.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public void use() {
		int itemIndex = g.UI.getItemIndex(); 
		g.player.inventory[itemIndex] = null ;
		g.player.HP = (g.player.HP < g.player.MaxHP-1)? g.player.HP + 2 : g.player.MaxHP ;
		
	}
}
