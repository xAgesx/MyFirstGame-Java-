package Entity;

import java.awt.Rectangle;

import Main.GamePanel;

public class Fireball extends Projectile{
	
	GamePanel g ;
	
	
	public Fireball(GamePanel g) {
		super(g);
		this.g = g ;
		Speed = 5 ;
		baseDamage = 1 ;
		MaxHP = 80;
		HP = MaxHP;
		manaCost = 1 ;
		
		hitBox = new Rectangle(8,8,32,32);
		
		GetEntityImg("/projectiles/fireball") ;
		
		
	}
	public boolean haveResource(Entity user) {
		if(user.mana >= manaCost) {
			return true;
		}else {
			return false ;
		}
	}
	public void subtractMana(Entity user) {
		user.mana -= manaCost ;
	}

}
