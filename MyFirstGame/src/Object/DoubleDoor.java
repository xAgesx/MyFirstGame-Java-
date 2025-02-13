package Object;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DoubleDoor extends SuperObject{

	public DoubleDoor() {
		name = "DoubleDoor";
		hitBox = new Rectangle(0,0,48,48);
		obtainable = false ;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/DoubleDoor.png"));
		}catch(IOException e ) {
			e.printStackTrace();
		}
	}
}
