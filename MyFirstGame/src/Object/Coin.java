package Object;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Coin extends SuperObject{
	
	public Coin() {
		
		name = "Coin";
		description = "["+name+"]\nThis is the currency of this world ";
		hitBox = new Rectangle(0,0,48,48);
		price = 10 ;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/GoldCoin.png"));
		}catch(IOException e ) {
			e.printStackTrace();
		}
	}
}
