package Object;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sword extends SuperObject{
	
	public Sword () {
		name = "Sword";
		hitBox = new Rectangle(0,0,48,48);
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/Sword1.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}