package Entity;

import Main.GamePanel;

public class Projectile extends Entity{

		Entity user ;
		int manaCost;
		
	public Projectile(GamePanel g) {
		super(g);
		this.g=g;
		alive = false ;
	}
	public void set(int worldX , int worldY , String Direction ,boolean alive , Entity user) {
		this.worldX = worldX ;
		this.worldY = worldY ;
		this.Direction = Direction ;
		this.alive = alive ;
		this.user = user ;
		
	}
	public void update() {
		
		//HIT COLLISION FOR PROJECTILES
		if(user == g.player) {
			int monsterIndex = g.collisionCheck.P2ECollision(this, g.monsters);
			if(monsterIndex != -1) {
				g.player.damageMonster(monsterIndex,baseDamage);
				alive = false ;
			}
		}
		//PROJECTIVE LIFESPAN
		HP-- ;
		if(HP<= 0) {
			alive = false;
			HP = MaxHP ;
		}
		//PROJECTILE MVT
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
		//PROJECTILE ANIMATION
		mvtFps ++ ;
		if(mvtFps <= 10) {
			
			mvtCounter = 1 ;
		}
			
		else if(mvtFps <20) {
			mvtCounter = 2 ;
			
		}else {
			mvtFps = 0;
		}
		
	}
	public boolean haveResource(Entity user) {
		return false;
	}
	public void subtractMana(Entity user) {
	}

}
