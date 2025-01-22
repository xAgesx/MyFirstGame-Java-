package Object;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class LavaTrap extends SuperObject{
	
	GamePanel g ;
	 
	
	public LavaTrap(GamePanel g) {
		this.g = g;
		baseDamage = 1 ;
		name = "LavaTrap";
		hitBox = new Rectangle(23,23,2,2);
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/LavaTrap.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
	}
	
	
	

	
}
