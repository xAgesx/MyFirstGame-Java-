package Object;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class Heart extends SuperObject{
	public BufferedImage image1,image2;

	GamePanel g;
	public Heart(GamePanel g) {
		this.g = g ;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/FullHeart.png"));
			image1 = ImageIO.read(getClass().getResourceAsStream("/objects/HalfHeart.png"));
			image2 = ImageIO.read(getClass().getResourceAsStream("/objects/BlankHeart.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
