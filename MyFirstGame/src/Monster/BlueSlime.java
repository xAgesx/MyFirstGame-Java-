package Monster;

import java.awt.Rectangle;
import java.util.Random;

import Entity.Entity;
import Main.GamePanel;
import Object.BlueJelly;

public class BlueSlime extends Entity{


	public BlueSlime(GamePanel g) {
		
		super(g);
		
		
		hitBox = new Rectangle(0,0,g.TileSize,g.TileSize);
		Direction = "left";
		GetEntityImg("/monsters/BlueSlime") ;
		entityType = 2;
		
		MaxHP = 5 ;
		HP = MaxHP;
		strength = 3 ;
		baseDamage = 1 ;
		Speed = 2 ;
		exp = 1;
		
	}
	
public void checkDrop() {
	int i = new Random().nextInt(100)+1 ;
	if(i<20) {
		this.dropItem(new BlueJelly());
	}
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
