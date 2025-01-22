package Main;



import Entity.Entity;

public class CollisionCheck {
	
	GamePanel g;
	
	public CollisionCheck(GamePanel g) {
		
		this.g = g ; 
		
	}
	
	public void E2PCollision(Entity entity) {
		
		entity.hitBox.x = entity.worldX + entity.hitBox.x;
		entity.hitBox.y = entity.worldY + entity.hitBox.y;
		
		g.player.hitBox.x += g.player.worldX ;
		g.player.hitBox.y += g.player.worldY ;
		
		
		if(entity.hitBox.intersects(g.player.hitBox)  ) {
			entity.contactPlayer = true ;
			if(entity.entityType != 2) {	
				entity.collisionOn = true;
				
				
			}
			
		}
		switch (entity.Direction) {
		
		case "up" :
			 entity.hitBox.y -= entity.Speed;
			
				break;
				
			case "down" :  entity.hitBox.y += entity.Speed;
			
				break;
				
			case "left" :  entity.hitBox.x -= entity.Speed;
			
				break;
			case "right" : entity.hitBox.x += entity.Speed;
			
				break;
				
			}
	
		entity.hitBox.x = entity.defaultHitBoxX ;
		entity.hitBox.y = entity.defaultHitBoxX ;
		g.player.hitBox.x = g.player.defaultHitBoxX;
		g.player.hitBox.y = g.player.defaultHitBoxY;
		
		}	
		
	
	public int P2ECollision(Entity entity,Entity[] target) {
		int index = -1;
		
		if(entity.worldX > g.player.worldX - g.player.RelativeX - g.TileSize && entity.worldX < g.player.worldX + g.player.RelativeX + g.TileSize 
				&& entity.worldY > g.player.worldY - g.player.RelativeY - g.TileSize && entity.worldY < g.player.worldY + g.player.RelativeY + g.TileSize ) {
			
			for ( int i = 0 ; i < g.entityMaxNumber ; i++) {
				
				if(target[i] != null) {
					
				entity.hitBox.x = entity.worldX + entity.hitBox.x;
				entity.hitBox.y = entity.worldY + entity.hitBox.y;
				
				target[i].hitBox.x += target[i].worldX ;
				target[i].hitBox.y += target[i].worldY ;
				
				
				switch (entity.Direction) {
				
				case "up" :
					 entity.hitBox.y -= entity.Speed;
						
						if(entity.hitBox.intersects(target[i].hitBox)  ) {
							index = i ; 	
						}
						break;
						
					case "down" :  entity.hitBox.y += entity.Speed;
					if(entity.hitBox.intersects(target[i].hitBox)  ) {
						index = i ;
					}
						break;
			
					case "left" :  entity.hitBox.x -= entity.Speed;
					
					if(entity.hitBox.intersects(target[i].hitBox)  ) {
						index = i ;
					}
						break;
					case "right" : entity.hitBox.x += entity.Speed;
					if(entity.hitBox.intersects(target[i].hitBox)  ) {
						index = i ;
					}
						break;
		
					}
				
			
				entity.hitBox.x = entity.defaultHitBoxX ;
				entity.hitBox.y = entity.defaultHitBoxX ;
				target[i].hitBox.x = target[i].defaultHitBoxX;
				target[i].hitBox.y = target[i].defaultHitBoxY;
				
				}	
			}
		
			

	}
		return index;
	}
	
	public int ObjectCheckCollision(Entity entity,Boolean isPlayer) {
		int index = -1;
		
		if(entity.worldX > g.player.worldX - g.player.RelativeX - g.TileSize && entity.worldX < g.player.worldX + g.player.RelativeX + g.TileSize 
				&& entity.worldY > g.player.worldY - g.player.RelativeY - g.TileSize && entity.worldY < g.player.worldY + g.player.RelativeY + g.TileSize ) {
			
			for ( int i = 0 ; i < 16 ; i++) {
				
				if(g.obj[i] != null) {
					
				entity.hitBox.x = entity.worldX + entity.hitBox.x;
				entity.hitBox.y = entity.worldY + entity.hitBox.y;
				
				g.obj[i].hitBox.x += g.obj[i].worldX ;
				g.obj[i].hitBox.y += g.obj[i].worldY ;
				
				
				switch (entity.Direction) {
				
				case "up" :
					 entity.hitBox.y -= entity.Speed;
						
						if(entity.hitBox.intersects(g.obj[i].hitBox)  ) {
							
							index = i ; 
							
							if(g.obj[i].collision == true ) {
								
								entity.collisionOn = true ;
							}
								
							
						}
					
					
						break;
						
					case "down" :  entity.hitBox.y += entity.Speed;
					if(entity.hitBox.intersects(g.obj[i].hitBox)  ) {
						
						index = i ;
						
						if(g.obj[i].collision == true ) 
							entity.collisionOn = true ;
						
					}
						break;
						
					case "left" :  entity.hitBox.x -= entity.Speed;
					if(entity.hitBox.intersects(g.obj[i].hitBox)  ) {
						
						index = i ;
						
						if(g.obj[i].collision == true ) 
							entity.collisionOn = true ;
						
					}
						break;
					case "right" : entity.hitBox.x += entity.Speed;
					if(entity.hitBox.intersects(g.obj[i].hitBox)  ) {
						
						index = i ;
						
						if(g.obj[i].collision == true ) 
							entity.collisionOn = true ;
						
					}
						break;
						
					}
				
			
				entity.hitBox.x = entity.defaultHitBoxX ;
				entity.hitBox.y = entity.defaultHitBoxX ;
				g.obj[i].hitBox.x = g.obj[i].DefaultHitBoxX;
				g.obj[i].hitBox.y = g.obj[i].DefaultHitBoxY;
				
				}	
			}
		
			

	}
		return index;
	}
	
	public void TileCheck(Entity entity ) {
		
		int hitBoxLeftX = entity.worldX + entity.hitBox.x ;
		int hitBoxRightX = entity.worldX + entity.hitBox.x + entity.hitBox.width ;
		int hitBoxTopY = entity.worldY + entity.hitBox.y;
		int hitBoxBottomY = entity.worldY + entity.hitBox.y + entity.hitBox.height ;
		
		int hitBoxLeftCol = hitBoxLeftX / g.TileSize;
		int hitBoxRightCol = hitBoxRightX / g.TileSize;
		int hitBoxTopRow = hitBoxTopY / g.TileSize;
		int hitBoxBottomRow = hitBoxBottomY / g.TileSize;
		
		int Tile1,Tile2;
		
switch (entity.Direction) {
		
		case "up" : hitBoxTopRow = (hitBoxTopY - entity.Speed) / g.TileSize ;
					Tile1 = g.TileM.TileMapNum[hitBoxLeftCol][hitBoxTopRow];
					Tile2 = g.TileM.TileMapNum[hitBoxRightCol][hitBoxTopRow];
					if(g.TileM.tile[Tile1].collision == true || g.TileM.tile[Tile2].collision == true) {
						entity.collisionOn = true ;
					}
			break;
			
		case "down" :  hitBoxBottomRow = (hitBoxBottomY + entity.Speed) / g.TileSize ;
						Tile1 = g.TileM.TileMapNum[hitBoxLeftCol][hitBoxBottomRow];
						Tile2 = g.TileM.TileMapNum[hitBoxRightCol][hitBoxBottomRow];
						if(g.TileM.tile[Tile1].collision == true || g.TileM.tile[Tile2].collision == true) {
							entity.collisionOn = true ;
		}
			break;
			
		case "left" :  hitBoxLeftCol = (hitBoxLeftX - entity.Speed) / g.TileSize ;
						Tile1 = g.TileM.TileMapNum[hitBoxLeftCol][hitBoxTopRow];
						Tile2 = g.TileM.TileMapNum[hitBoxLeftCol][hitBoxBottomRow];
						if(g.TileM.tile[Tile1].collision == true || g.TileM.tile[Tile2].collision == true) {
							entity.collisionOn = true ;
		}
			break;
		case "right" :  hitBoxRightCol = (hitBoxRightX + entity.Speed) / g.TileSize ;
						Tile1 = g.TileM.TileMapNum[hitBoxRightCol][hitBoxTopRow];
						Tile2 = g.TileM.TileMapNum[hitBoxRightCol][hitBoxBottomRow];
						if(g.TileM.tile[Tile1].collision == true || g.TileM.tile[Tile2].collision == true) {
							entity.collisionOn = true ;
		}
			break;
			
		
		}
	}
	
}
