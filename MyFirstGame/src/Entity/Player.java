package Entity;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.Keyhandler;
import Object.BlueJelly;
import Object.Chest;
import Object.Coin;
import Object.Heart;
import Object.Key;
import Object.Potion;
import Object.SuperObject;
import Object.Sword;

public class Player extends Entity{
	GamePanel G ;
	Keyhandler KeyH;
	
	
	// relative player coordinates (Camera view)
	
	public final int RelativeX  ;
	public final int RelativeY  ;
	
	public int monsterIndex ;
	public int objIndex ;
	public int npcIndex ;
	
	public int dialogueMode = 0 ;
	public int lastNpcIndex ;
	
	//STATS
	public int level ;
	public int strength ;
	public int defense ;
	
	public int nextLevelExp;
	public int Coins ;
	
	
	
	
	

	
	public Player(GamePanel G , Keyhandler KeyH) {
		
		super(G);
		this.G = G;
		this.KeyH = KeyH;
		
		RelativeX = G.ScreenWidth/2 - G.TileSize/2 ;
		RelativeY = G.ScreenHeight/2  - G.TileSize/2;
		
		hitBox = new Rectangle();
		hitBox.x = 8;
		hitBox.y = 8;
		hitBox.width = 32 ;
		hitBox.height = 32 ;
		defaultHitBoxX = hitBox.x;
		defaultHitBoxY = hitBox.y;
		
		attackArea.width = 36 ;
		attackArea.height = 36 ;
		
		invincibilityCounter = 0 ;
		invincible = false;
		
		inventorySize = 27 ;
		inventory = new SuperObject[inventorySize];
	
		
		
		SetDefaults();
		GetEntityImg("/player/oldman");
		getPlayerAttackImage("/player/oldman");
		setInventoryItems();
	}
	public void setInventoryItems() {
		inventory[0] = null;
		inventory[1] = new Chest();
		inventory[2] = new Sword();
		inventory[3] = new Coin();
		inventory[4] = new Chest();
		inventory[5] = new Coin();
		inventory[6] = new Coin();
		inventory[7] = new Coin();
		inventory[8] = new Coin();
		inventory[9] = new Coin();
		inventory[10] = new Coin();
		inventory[11] = new Coin();
		inventory[12] = new Coin();
		inventory[13] = new Coin();
		inventory[14] = new Potion(G);
		inventory[15] = new Potion(G);
		inventory[16] = new Key();
		inventory[17] = new Coin();
		inventory[18] = new Coin();
		inventory[19] = new Coin();
		inventory[20] = new Key();
		inventory[21] = new Coin();
		inventory[22] = new Coin();
		inventory[23] = new Coin();
		inventory[24] = new Coin();
		inventory[25] = new Coin();
		inventory[26] = new Coin();

	
	}
	public int emptyInventorySlot() {
		for (int i = 0 ; i < inventorySize ;i++) {
			if(inventory[i] == null ) {
				return i;
			}
		}
		return -1;
	}
	public void SetDefaults() {
		
		// Absolute player coordinates on the worldMap
		worldX = 192;
		worldY = 816;
		
		Direction = "up1";
		
		//setDefault STATS
		MaxHP = 10 ;
		HP = 10 ;
		
		maxMana = 4 ;
		mana = maxMana ;
		manaRegen =0 ;
		
		baseDamage = 2 ;
		strength = 1 ;
		defense = 1 ;
		
		Speed = 4;
		level = 1 ;
		exp = 0 ;
		nextLevelExp = 2 ;
		
		Coins = 1450 ;
		
		projectile = new Fireball(g);
		
		
	}
	

	
	int mvtFps = 0;
	
	public void Update() {
		
		
		if(attacking == true ) {
			
			attacking();
		}
		//NATURAL MANA REGEN 
		manaRegen ++;
		
		if(mana < maxMana && manaRegen == 200) {
			mana++;
			
		}
		if(manaRegen == 200) {
			manaRegen = 0;
		}
		//LAUNCH PROJECTILE
		if(g.KeyH.shotKeyPressed == true && projectile.alive == false && projectile.shotCD == 80 && projectile.haveResource(this)) {
			
			//DEFAULTS FOR PROJECTILE 
			projectile.set(worldX,worldY, Direction ,true,this);
			
			//ADD PROJECTILE TO projectiles []
			g.projectiles[0] = projectile ;
			projectile.shotCD =0;
			
			//subtract MANACOST
			projectile.subtractMana(this); 
			
		}
		
		if(projectile.shotCD < 80) {
			projectile.shotCD ++;
		}
	 
		if(KeyH.UpPressed == true) {
			
			Direction ="up";
		}
		else if(KeyH.DownPressed == true) {
			
			Direction ="down";
		}
		else if(KeyH.RightPressed == true) {
			
			Direction ="right";
		}
		else if(KeyH.LeftPressed == true) {
			
			Direction ="left";
		}
		
		//DEAL WITH COLLISION 
			collisionOn = false ;
			G.collisionCheck.TileCheck(this);
			
			objIndex = G.collisionCheck.ObjectCheckCollision(this,true);
			
			npcIndex = g.collisionCheck.P2ECollision(this, g.npcs);
		
			monsterIndex = g.collisionCheck.P2ECollision(this, g.monsters);
			
			//check event
			g.event.checkEvent();
			PickUpObj(objIndex);
			
			
			//TELEPORT TO A DIFFERENT MAP
			if( objIndex != -1 && g.obj[objIndex] != null && g.obj[objIndex].name.equals("DoubleDoor") ) {
				
				g.event.teleport("worldMap","TutorialDungeonMap");
			}else {
				g.event.teleportAvailable = 1 ;
			}
			
			
			
			//STOP NPC ON PLAYER 2 ENTITY COLLISION
			if(npcIndex != -1 ) {
				
				attacking = false;
				interactNPC(npcIndex);
				
				//TALK WITH NPC ON KEY PRESS
				if(KeyH.InteractPressed == 1) {
					dialogueMode = 1 ;
					g.gameState = 4 ;
					g.npcs[npcIndex].talk();
					lastNpcIndex = npcIndex ;
					KeyH.InventoryPressed = 0 ;
				}
			}else if(dialogueMode == 1){
				
				g.gameState = 4 ;
				g.npcs[lastNpcIndex].talk();
			}
			
			if(invincible == true) {
				invincibilityCounter ++ ;
				
				if(invincibilityCounter >= 50) {
					
					invincibilityCounter = 0;	
					invincible = false;	 
				}
			}
			
			//TAKE DAMAGE ON MONSTER CONTACT (P2E)
			if((monsterIndex != -1 && invincible == false ) ) {	
	
				collisionOn = true ;
				touchMonster(monsterIndex);
				invincible = true ;
				
			}
		
		
		if (collisionOn == false && (g.KeyH.UpPressed || g.KeyH.DownPressed || g.KeyH.RightPressed || g.KeyH.LeftPressed)) {
			
			// HANDLE WALKING ANIMATION
			mvtFps ++;
			
			if(mvtFps > 20 ) {
				mvtFps = 0 ;
			}
			
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
		
		if(HP <= 0) {
			g.gameState = 5 ;
			g.UI.commandNum = 0 ;
		}
		
	}
	
	public void deathReset() {
		worldX = 192;
		worldY = 816;
		
		Direction = "up1";
		
		HP = MaxHP ;
		mana = maxMana ;
		g.aSetter.npcSetter();
		g.aSetter.setObject();
		g.aSetter.setMonster();
		clearInventory();
	}
	public void clearInventory() {
		for (int i=0 ; i<inventory.length ; i++) {
			inventory[i] = null;
		}
	}
	public void selectItem() {
		
		int itemIndex = g.UI.getItemIndex();
		if(g.player.inventory[itemIndex] != null) {
			
			g.player.inventory[itemIndex].use();
			
			itemIndex = -1 ;
		
		}
	}
	public void checkLevelUp() {
		
		if(exp >= nextLevelExp) {
			level++;
			exp = 0;
			nextLevelExp *= 2;
			HP = MaxHP;
			baseDamage ++ ;
			g.UI.currentDialogue[2] = "LEVEL UP !";
			
			
			
		}
	}
	public void attacking() {
		
		//HANDLE ATTACK ANIMATION
		mvtFps1 ++ ;
		if(mvtFps1 <= 10) {
			
			mvtCounter = 1 ;
		}
			
		else if(mvtFps1 < 20 && mvtFps1 > 5) {
			mvtCounter = 2 ;
			
			//CHECK ATTACK'S LANDING
			int currentWorldX = worldX ;
			int currentWorldY = worldY ;
			int hitBoxWidth = hitBox.width ;
			int hitBoxHeight = hitBox.height ;
			
			switch(Direction) {
			
			case "up" : worldY -= hitBox.height ;break ;
			case "down" : worldY += hitBox.height ;break ;	
			case "right" : worldX += hitBox.width ;break ;
			case "left" : worldX -= hitBox.width ;break ;	
			}
			
			hitBox.width = attackArea.width ;
			hitBox.height = attackArea.height ;
			
			int monsterIndex = g.collisionCheck.P2ECollision(this, g.monsters);
			
			damageMonster(monsterIndex,baseDamage);
			 worldX = currentWorldX ;
			 worldY = currentWorldY ;
			 hitBox.width = hitBoxWidth ;
			 hitBox.height = hitBoxHeight ;
		}
		else {
			mvtCounter = 1;
			attacking = false ;
			mvtFps1 = 0 ;
		}
			
	}		
	public void damageMonster(int i , int baseDamage) {
		
		if(i != -1) {
			
			int damage = baseDamage*strength - g.monsters[i].defense ;
			if(g.monsters[i].invincible == false) {

				g.monsters[i].HP -= (damage < 0) ? 0 : damage ;
				g.monsters[i].invincible = true ;
				
				if(g.monsters[i].HP <= 0 ) {
					exp += g.monsters[i].exp ;
					g.UI.currentDialogue[1] = "+ " + g.monsters[i].exp + " Exp"; 
					checkLevelUp();
				
				}
				
			}
			
		}
	}
	public void getPlayerAttackImage(String imagePath) {
			
		
		try {
	
			attackUp1 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_attackup_1.png"));
			attackDown1 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_attackdown_1.png"));
			attackRight1 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_attackright_1.png"));
			attackLeft1 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_attackleft_1.png"));
			
			attackUp2 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_attackup_2.png"));
			attackDown2 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_attackdown_2.png"));
			attackRight2 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_attackright_2.png"));
			attackLeft2 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_attackleft_2.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
			}
	}
	
	public void interactNPC(int i) {
		
		collisionOn = true ;
		g.npcs[i].Direction = "" ;
		
	}
	public void touchMonster(int i) {
		int damage = g.monsters[i].baseDamage * g.monsters[i].strength - defense ;
		HP -= (damage < 0) ? 0 : damage ;

	}
	public void PickUpObj(int i) {		
		
		int emptySlot = emptyInventorySlot();
		if(i != -1 && g.obj[i].obtainable ) {
			
			if(emptySlot != -1) {
				inventory[emptySlot] = g.obj[i];
				g.obj[i] = null ;
			}else {
				g.UI.currentDialogue[2] = "Inventory Is Full !";
			}
			
		}
			
	}
	public void Draw(Graphics2D g1) {
		
		//g1.setColor(Color.white);
		//g1.fillRect(x, y, G.TileSize, G.TileSize);
		
		BufferedImage image = null;
		
		switch (Direction) {
		
		case "up": if(attacking == false ) { image = (mvtFps < 10 ) ? up1 : up2  ;}
					else { image = (mvtCounter == 1 ) ? attackUp1 : attackUp2  ;}
		
		break;
		
		case "down": if(attacking == false ) { image = (mvtFps < 10 ) ? down1 : down2  ;}
						else { image = (mvtCounter == 1 ) ? attackDown1 : attackDown2  ;}
		break;
		
		case "right": if(attacking == false ) { image = (mvtFps < 10 ) ? right1 : right2  ;}
					else { image = (mvtCounter == 1 ) ? attackRight1 : attackRight2  ;}
		break;
		
		case "left": if(attacking == false ) { image = (mvtFps < 10 ) ? left1 : left2  ;}
						else { image = (mvtCounter == 1 ) ? attackLeft1 : attackLeft2  ;}
		break;
		
		default : image = down1;
		
		}
		
		if(invincible == true ) {
			g1.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));
		}
		
		g1.drawImage(image,RelativeX,RelativeY,G.TileSize,G.TileSize,null);
		g1.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
		
		
		//DEBUGGING
		//DRAW attackArea 
		int tempScreenX = RelativeX + hitBox.x;
		int tempScreenY = RelativeY + hitBox.y;		
		switch(Direction) {
		case "up": tempScreenY = RelativeY - attackArea.height; break;
		case "down": tempScreenY = RelativeY + g.TileSize; break; 
		case "left": tempScreenX = RelativeX - attackArea.width; break;
		case "right": tempScreenX = RelativeX + g.TileSize; break;
		}				
		g1.setColor(Color.red);
        g1.setStroke(new BasicStroke(1));
		g1.drawRect(tempScreenX, tempScreenY, attackArea.width, attackArea.height);
		
	}
}
