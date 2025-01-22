package Main;

import java.awt.Rectangle;

public class Event {
	
	GamePanel g ;
	Rectangle eventRect ;
	int defaultEventRectX ,defaultEventRectY ;
	int Timer = 0;
	public int teleportAvailable = 1 ;
	public Event(GamePanel g) {
		
		this.g = g ;
		eventRect = new Rectangle(23,23,2,2);
		defaultEventRectX = 23 ;
		defaultEventRectY = 23 ;
		
	}
	
	public void checkEvent() {
		
		Timer++; 
		
		
		if(Timer>100) {
			if(intersection(12,16)) {
				
				damagePlayer();
				Timer = 0 ;
			}
			
		}
		
		
			
	}
	
	public void teleport(String currentWorld , String destinationWorld) {
		
		if(teleportAvailable == 1) {
			teleportAvailable = 0 ;
			
			if(g.currentMap.equals(currentWorld)) {
				g.npcs = g.npcsMap1 ;
				g.monsters = g.monstersMap1 ;
				g.obj = g.objMap1 ;
				g.TileM.LoadMap(destinationWorld);
			
			}
			else if(g.currentMap.equals(destinationWorld)) {
				g.npcs = g.npcsMap0 ;
				g.monsters = g.monstersMap0 ;
				g.obj = g.objMap0 ;
				g.TileM.LoadMap(currentWorld);
			
			}
			g.player.Direction = "down";
			
		}
	}
	 public boolean intersection(int eventCol , int eventRow) {
		 
		 	boolean intersection = false ;
		 	g.player.hitBox.x = g.player.worldX + g.player.hitBox.x;
			g.player.hitBox.y = g.player.worldY + g.player.hitBox.y;
			
			eventRect.x += eventCol*g.TileSize ;
			eventRect.y += eventRow*g.TileSize ;
			
			if(g.player.hitBox.intersects(eventRect)) {
				intersection = true ;
			}
			 
			g.player.hitBox.x = g.player.defaultHitBoxX ;
			g.player.hitBox.y = g.player.defaultHitBoxY ;
			eventRect.x = defaultEventRectX ;
			eventRect.y = defaultEventRectY ;
			
			return intersection;
	}
	 
	 
	 public void damagePlayer() {
			
			g.player.HP -= 1 ;
			g.gameState = 4 ;
			g.UI.currentDialogue[0] ="Oh nooooo ... ! A LAVA TRAP !!!";
		
	}
}
