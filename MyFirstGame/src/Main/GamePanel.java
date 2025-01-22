package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Entity.Entity;
import Entity.Player;
import Entity.Projectile;
import Object.Chest;
import Object.SuperObject;

public class GamePanel extends JPanel implements Runnable{
	
	//Screen Settings 
	
	final int DefaultTileSize = 16 ;
	final int Scale = 3 ;
	final public int TileSize = DefaultTileSize*Scale ;
	final public int maxScreenCol = 20; 
	final public int maxScreenRow = 12;
	final public int ScreenWidth = maxScreenCol * TileSize;
	final public int ScreenHeight = maxScreenRow * TileSize;
	
	//FULL SCREEN
	public int fullScreenWidth = ScreenWidth ;
	public int fullScreenHeight = ScreenHeight ;
	BufferedImage tempScreen ;
	Graphics2D g2 ;
	//WorldMap Settings

	public final int maxWorldCol = 48;
	public final int maxWorldRow = 48;
	public final int worldWidth = maxWorldCol * TileSize ;
	public final int worldHeight = maxWorldRow * TileSize ;
	
	
	int FPS = 60;
	int objMaxNumber = 20 ;
	int entityMaxNumber = 10 ;
	public Keyhandler KeyH	= new Keyhandler(this);
	Thread GameThread ;
	
	//Entities
	public Entity npcsMap0[] = new Entity[entityMaxNumber];
	public Entity npcsMap1[] = new Entity[entityMaxNumber];
	public Entity npcs[] = npcsMap0;
	//monsters 
	public Entity monstersMap0[] = new Entity[entityMaxNumber];
	public Entity monstersMap1[] = new Entity[entityMaxNumber];
	public Entity monsters[] = monstersMap0;
	//Objects
	
	public SuperObject objMap0[] = new SuperObject[objMaxNumber]; 
	public SuperObject objMap1[] = new SuperObject[objMaxNumber]; 
	public SuperObject obj[] = objMap0 ;
	
	
	public AssetSetter aSetter = new AssetSetter(this); 
	//PROJECTILES 
	public Entity projectiles[] = new Projectile[entityMaxNumber];
	//PLAYER
	public Player player = new Player(this,KeyH);
	public CollisionCheck collisionCheck = new CollisionCheck(this);
	
	//Tile manager
	
	public String currentMap = "worldMap", currentWorld , destinationWorld;
	public TileManager TileM = new TileManager(this) ;
	
	// UI
	public UI UI = new UI(this);
	
	//CONFIG 
	Config config = new Config(this);
	//EVENT HANDLER
	public Event event = new Event(this);
	//Game State (0 : Title screen , 1 : Playing , 2 : Pause , 3 : Settings , 4 : Dialogue ,5 : GameOver , 6 : Inventory , 7 : Trade)
	//SubState ( 0 : Select , 1 ; Buy , 2 : Sell )
	public int gameState = 0 , subState = 0;
	public int previousGameState ;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(ScreenWidth,ScreenHeight));
		this.setDoubleBuffered(true);
		this.addKeyListener(KeyH);
		this.setFocusable(true);
		
	}
	
	public void SetupGame() {
		
		aSetter.setObject();
		aSetter.npcSetter();
		aSetter.setMonster();
		
		//setting up the full screen medium (tempScreen)
		tempScreen = new BufferedImage(ScreenWidth,ScreenHeight,BufferedImage.TYPE_INT_ARGB);	
		g2 = (Graphics2D )tempScreen.getGraphics();
		
			
	}
	public void setFullScreen() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		Main.window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		fullScreenWidth = (int) width;
		fullScreenHeight = (int) height;
       
		
	}
	public void StartGameThread() {
		GameThread = new Thread(this);
		GameThread.start();
		
	}
	
	@Override
	public void run() {
		while(GameThread != null) {
			if(UI.fullScreenOn == true) {
				setFullScreen();
				
			}
			Update();
			drawTempScreen(); //draw to the medium (tempScreen)
			drawToScreen();	//draw to the actual screen
			
			try {
				
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	public void Update() {
		if( gameState == 1 ) {
			player.Update();
			
			for (int i = 0 ; i < entityMaxNumber ; i++ ) {
				//UPDATE NPCS
				if(npcs[i] != null ) {
					npcs[i].update();
				}
				//UPDATE MONSTERS
				if(monsters[i] != null ) {
					if(monsters[i].alive == true) {
						monsters[i].update();
					}else {
						monsters[i] = null ;
					}
					
				}
				//UPDATE PROJECTILES
				if(projectiles[i] != null ) {
					
					if(projectiles[i].alive == true) {
						projectiles[i].update();
		
					}else {
						projectiles[i] = null ;
					}
					
				}
				
			}
		}
		else  {
			//nothing
			
		}
		
	
		
	}
	//NEW GAME RESTART
	public void Restart() {
		npcs = npcsMap0 ;
		obj = objMap0 ;
		monsters = monstersMap0 ;
		
		currentMap = "worldMap";
		TileM.LoadMap(currentMap);
		player.SetDefaults();
		player.clearInventory();
		aSetter.npcSetter();
		aSetter.setObject();
		aSetter.setMonster();
		
	}
	public void drawToScreen() {
		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0,0,fullScreenWidth,fullScreenHeight,null);
	}
	public void drawTempScreen() {
		g2.clearRect(0, 0, fullScreenWidth, fullScreenHeight);
		UI.g1 = g2;
		
		//DRAW TILES
		TileM.Draw(g2)	;
		
		//DRAW OBJECTS
		for (int i=0 ; i< objMaxNumber ; i++) {
			if(obj[i]!= null)
				obj[i].Draw(this, g2);
		}
		
		//DRAW NPC
		for (int i=0 ; i< npcs.length ; i++) {
			if(npcs[i]!= null)
				npcs[i].Draw( g2);
		}
		//DRAW MONSTER
		for (int i=0 ; i< monsters.length ; i++) {
			if(monsters[i]!= null)
				monsters[i].Draw( g2);
		}
		//DRAW PROJECTILES
		for (int i=0 ; i< projectiles.length ; i++) {
			if(projectiles[i] != null) {
				projectiles[i].Draw(g2);
				
			}
				
		}
		//DRAW PLAYER
		player.Draw(g2);
		g2.setColor(Color.RED);
		
		//Draw UI elements
		UI.Draw();
		
		
		if(gameState == 0 ) {
			
			UI.DrawTitle(g2);
		}
		else if(gameState == 2 ) {
			
			UI.DrawPause(g2);
			UI.DrawStats(g2);
			
		}
		else if(gameState == 3) {
			switch (previousGameState) {
				case 0 : UI.DrawTitle(g2);break;
				case 2 : UI.DrawPause(g2);break;
			}
			//TBR : TO BE REVIEWED 
			if(UI.subCommandNum == 2) {
				if( KeyH.confirmPressed == true )
					UI.settings_control();
				}else {
					UI.drawSettings();
				}
			
			
		}
		
		else if(gameState == 4 ) {
			
			UI.DrawDialogueWindow(g2);
		}
		
		else if(gameState == 7 ) {
			switch(subState){
				case 0 : UI.tradeSelect();break;
				case 1 : UI.tradeBuy();break;
				case 2 : UI.tradeSell();break;
			}
			
		}
			
			
	}
	/*This is the old method of drawing !!
	  public void paintComponent(Graphics g) {
	 
		
		super.paintComponent(g);
		Graphics2D g1 = (Graphics2D)g;
		
		g1.dispose();
		
	} */
	
	
	
}
