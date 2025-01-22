package Object;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Key extends SuperObject{
	
	public Key () {
		name = "Key";
		hitBox = new Rectangle(0,0,48,48);
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/Key.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
