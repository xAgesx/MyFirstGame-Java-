package Object;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BlueJelly extends SuperObject{
	
	public BlueJelly() {
		
		name = "Blue Jelly";
		description = "["+name+"]\nemm very ... Slimey . \n wonder what I can use it for";
		hitBox = new Rectangle(0,0,48,48);
		price = 3 ;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/BlueJelly.png"));
		}catch(IOException e ) {
			e.printStackTrace();
		}
	}
}
