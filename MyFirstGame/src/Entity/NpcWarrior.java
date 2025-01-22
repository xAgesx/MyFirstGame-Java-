package Entity;

import java.awt.Rectangle;
import java.util.Random;

import Main.GamePanel;

public class NpcWarrior extends Entity{
	
	
	public NpcWarrior(GamePanel G) {
		super(G);
		hitBox = new Rectangle(0,0,g.TileSize-16,g.TileSize-16);
		Direction = "";
		Speed = 1 ;
		GetEntityImg("/npc/Warrior") ;
		setDialogue();
		
	}
		
	//NPC MVT
	Random random = new Random() ;
	public void setAction(){
		
		
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
	public void setDialogue(){
		
		Dialogue[0] = "Hello there ... You are a new face arround\n here aren't ya ! And you look quite the \nmagician too !";
		Dialogue[1] = "Anyhow , I have a task for you ... you see \nlately the slimes in these areas have become \na bit pesky , they are entering human territory ";
		Dialogue[2] = "I would deal with them myself but emm ...\n I am a busy man you see ! ";
		Dialogue[3] = "Alright off you go ! ";
		
	}
public void update() {
		
		setAction();
		collisionOn = false ;
		g.collisionCheck.TileCheck(this);
		g.collisionCheck.ObjectCheckCollision(this, false);
		g.collisionCheck.E2PCollision(this);
		if (collisionOn == false ) {
			
			switch(Direction) {
			
			case "up": worldY -= Speed ;
				break ;
			case "down": worldY += Speed ;
				break ;
			case "right": worldX += Speed ;
				break ;
			case "left": worldX -= Speed ; 
				break ;
			
			}
		}
		mvtFps ++;
		
		if(mvtFps > 20 ) {
			mvtFps = 0 ;
		}
	}
	
			
}

