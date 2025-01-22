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
import Object.BlueJelly;
import Object.SuperObject;

public class Entity {
	
	public GamePanel g;
	public int defaultHitBoxX = 0 , defaultHitBoxY = 0 ;
	public int worldX , worldY ;
	public int Speed ;
	
	public BufferedImage up1,down1,right1,left1,up2,down2,right2,left2; 
	public BufferedImage attackUp1,attackDown1,attackRight1,attackLeft1,attackUp2,attackDown2,attackRight2,attackLeft2;
	public String Direction;
	public BufferedImage dyingImage1 , dyingImage2 ;
	
	public Rectangle hitBox ;
	public Rectangle attackArea = new Rectangle(0,0,0,0);
	public boolean collisionOn = false;
	
	public int mvtFps = 0 ;
	public int mvtFps1 = 0;
	public int mvtCounter ;
	
	public String Dialogue[] = new String[50];
	public int dialogueIndex = 0 ;
	
	public int entityType ; // 0 : player , 1 : npc , 2 : monster
	
	public boolean contactPlayer = false ;
	public boolean attacking ;
	public int invincibilityCounter = 0 ;
	public boolean invincible = false;
	int deathCounter = 0 ;
	
	//HP
	public boolean  HPBarOn = true ;
	public int MaxHP ;
	public int HP;
	
	//MANA
	public int maxMana ;
	public int mana  ;
	public int manaRegen ;
	
	public boolean alive = true ;
	
	public int baseDamage ;
	public int strength = 1;
	public int defense ;
	public int exp ;
	
	//PROJECTILE
	int shotCD = 0;
	
	//Inventory
	public int inventorySize  ;
	public SuperObject inventory[];
	
	public Projectile projectile ;
	
	public Entity(GamePanel g) {
		this.g = g ;
		
		
		//Dying animation
		try {
			dyingImage1 = ImageIO.read(getClass().getResourceAsStream("/effects/dyingImage1.png"));
			dyingImage2 = ImageIO.read(getClass().getResourceAsStream("/effects/dyingImage2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setInventoryItems() {
		
	}
	public void checkDrop() {
		
	}
	public void setAction() {
		
	}
	public void dropItem(SuperObject Item) {
		for(int i = 0 ; i<g.obj.length ; i++) {
			if(g.obj[i]== null) {
				g.obj[i] = Item ;
				g.obj[i].worldX = worldX ;
				g.obj[i].worldY = worldY ;
				break;
			}
		}
	}
	public void talk() {
		
		
		if(Dialogue[dialogueIndex] == null) {
			g.gameState = 1 ;
			dialogueIndex = 0;
			g.player.dialogueMode = 0;
		}
		else {
			g.UI.currentDialogue[0] = Dialogue[dialogueIndex];
			dialogueIndex++ ;
		}
		
		
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
		
		//DAMAGE PLAYER ON PLAYER CONTACT 
		if(contactPlayer == true ) {
			contactPlayer = false ;
			if(g.player.invincible == false) {
				
				g.player.HP -= baseDamage*strength - g.player.defense ;
				g.player.invincible = true ;
				
			}
			
		}
		//INVINCIBILITY FOR MONSTERS MAINLY
		if(invincible == true) {
			
			invincibilityCounter ++ ;
			
			if(invincibilityCounter >= 10) {
				
				invincibilityCounter = 0;	
				invincible = false;
				
			}
		}
		
	}
	public void GetEntityImg(String imagePath) {
		
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_up_1.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_down_1.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_right_1.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_left_1.png"));
			
			up2 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_up_2.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_down_2.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_right_2.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream(imagePath +"_left_2.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
			}
	}
	
	public void Draw( Graphics2D g1 ) {
		
	
		BufferedImage image = null;
		int RelativeX = worldX - g.player.worldX + g.player.RelativeX ;
		int RelativeY = worldY - g.player.worldY + g.player.RelativeY ;
		if(worldX > g.player.worldX - g.player.RelativeX - g.TileSize && worldX < g.player.worldX + g.player.RelativeX + g.TileSize 
				&& worldY > g.player.worldY - g.player.RelativeY - g.TileSize && worldY < g.player.worldY + g.player.RelativeY + g.TileSize ) {
			
			
			switch (Direction) {
			
			case "up": image = (mvtFps < 10 ) ? up1 : up2 ;
			break;
			
			case "down": image = (mvtFps < 10 ) ? down1 : down2 ;
			break;
			
			case "right": image = (mvtFps < 10 ) ? right1 : right2 ;
			break;
			
			case "left": image = (mvtFps < 10 ) ? left1 : left2 ;
			break;
			
			default : image = down1;
			
			}
			//DRAW INVINCIBILITY OVERLAY
			if(invincible == true ) {
				g1.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.5f));
			}
			
			//DEATH ANIMATION
			if(HP <= 0 && entityType == 2) {
				
				Speed = 0;
				deathCounter ++ ;
				int DeathAnimation = 10 ;
				if(deathCounter <= DeathAnimation ) {
					
					if(deathCounter >= DeathAnimation/2) {
						g1.drawImage(dyingImage1, RelativeX, RelativeY, dyingImage1.getWidth(), dyingImage1.getHeight(), null);
					}
					else {
						g1.drawImage(dyingImage2, RelativeX, RelativeY, dyingImage2.getWidth(), dyingImage2.getHeight(), null);
					}
				}
				else {
				
				deathCounter = 0;
				alive = false;
				checkDrop();
				}
			}
			

			g1.drawImage(image, RelativeX, RelativeY, image.getWidth()*3, image.getHeight()*3, null);
			g1.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
			
			//MONSTER HP BAR
			if (entityType == 2 && HPBarOn == true) {
				float oneScale = (float)(g.TileSize / MaxHP );
				float HPBarValue = oneScale * HP ;
				
				g1.setColor(new Color(35,35,35));
				g1.fillRect(RelativeX-1, RelativeY-16, g.TileSize+2, 12);
				g1.setColor(Color.RED);
				g1.fillRect(RelativeX, RelativeY-15, (int)HPBarValue, 10);
			}
			//DEBUG
			
			//g1.setColor(Color.white);
			//g1.fillRect(RelativeX, RelativeY, hitBox.width, hitBox.height);
			
			//int tempScreenX = RelativeX + hitBox.x;
			//int tempScreenY = RelativeY + hitBox.y;		
							
			//g1.setColor(Color.red);
			//g1.setStroke(new BasicStroke(1));
			//g1.drawRect(tempScreenX, tempScreenY, hitBox.width, hitBox.height);
			}
		
		}
	}
