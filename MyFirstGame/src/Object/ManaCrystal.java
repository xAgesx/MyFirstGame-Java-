package Object;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class ManaCrystal extends SuperObject{
	
	GamePanel g;
	public BufferedImage image1;
	
	public ManaCrystal(GamePanel g) {
		this.g = g;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/FullCrystal.png")) ;
			image1 = ImageIO.read(getClass().getResourceAsStream("/objects/BlankCrystal.png")) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
