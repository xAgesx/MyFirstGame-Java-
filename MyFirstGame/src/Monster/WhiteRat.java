package Monster;

import java.awt.Rectangle;
import java.util.Random;

import Entity.Entity;
import Main.GamePanel;

public class WhiteRat extends Entity{
	
	
	
	public WhiteRat(GamePanel g) {
		
		super(g);
		Speed = 2 ;
		MaxHP = 20 ;
		HP = MaxHP;
		hitBox = new Rectangle(0,0,g.TileSize,g.TileSize);
		Direction = "left";
		GetEntityImg("/monsters/BlueSlime") ;;
		entityType = 2;
		baseDamage = 1 ;
	}
	
	
public void setAction(){
		
		Random random = new Random() ;
		int i = random.nextInt(3);
		mvtCounter ++ ;
		if(mvtCounter == 200 ) {
			
			switch(i) {
			
			case 0: 
					Direction = "left";
			break;
			case 1: 
					Direction = "right";
			break;
			case 2: 
				Direction = "up";
			break;
			case 3: 
				Direction = "down";	
			break;
			
			}
			mvtCounter = 0 ;
		}
		
	}

	



}
