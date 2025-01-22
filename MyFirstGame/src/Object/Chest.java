package Object;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Chest extends SuperObject{
	
	public Chest() {
		name = "Chest";
		hitBox = new Rectangle(0,0,48,48);
		obtainable = false ;
		collision = true ;
		
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/Chest2.png"));
		}catch(IOException e ) {
			e.printStackTrace();
		}
	}
}
