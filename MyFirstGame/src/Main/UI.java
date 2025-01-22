package Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import Entity.Entity;
import Entity.NpcMerchant;
import Object.Coin;
import Object.Heart;
import Object.ManaCrystal;

public class UI {

	GamePanel g;
	public Graphics2D g1 ;
	
	BufferedImage Coins,FullHeart,HalfHeart,BlankHeart ,FullCrystal,BlankCrystal;
	
	//currentDialogue[i] : 0 --> DialogueBox , 1 --> quick notification , 2 --> Header notification ...
	// 0 --> DialogueBox is to be excluded as it is handled separately by DrawDialogueWindow
	public String currentDialogue[] = new String[10] ; 
		
	Font AncientModernTales ,TimesNewRoman;
	
	public int commandNum = 0,subCommandNum = 0;
	int i = 0 , Timer = 0;
	
	Coin coin = new Coin();
	
	Color blackTrans = new Color(0,0,0,200);
	
	public int slotRow = 0;
	public int slotCol = 0;
	public int itemIndex ;
	
	public boolean fullScreenOn = false ;
	public boolean musicOn = false ;
	
	public NpcMerchant merchant ;
	
	
	public UI(GamePanel g) {
		this.g=g;
		
		//HP
		Heart heart = new Heart(g);
		FullHeart = heart.image ;
		HalfHeart = heart.image1;
		BlankHeart = heart.image2;
		
		//MANA
		ManaCrystal crystal = new ManaCrystal(g);
		FullCrystal = crystal.image;
		BlankCrystal = crystal.image1;
		
		//INCLUDE CUSTOM FONTS 
		try {		
			InputStream is = getClass().getResourceAsStream("/Fonts/TheWildBreathOfZelda.ttf");
			AncientModernTales = Font.createFont(Font.TRUETYPE_FONT,is);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Draw() {
		
		drawHeartsMana();
		 
		//GameOver
		 if(g.gameState == 5 ) {
			 drawGameOver();
		 }
		//DRAW INVENTORY 
		if(g.KeyH.InventoryPressed == 1) {
				
			DrawInventory(g.player);
		}
		else {
			
			g.KeyH.InventoryPressed = 0 ;
			
		 }
		 
		 //DRAW MESSAGES
		if(g.gameState == 1) {
			 
			for(int j = 1 ; j < 10 ; j++) {
				if(currentDialogue[j] != null) {
					 
					 switch (j) {
					 case 1 :  DrawNotificationMessage( j,g.ScreenWidth/2 - g.TileSize , g.ScreenHeight/2 - g.TileSize*2 );
					 break;
					 
					 case 2 : DrawNotificationMessage(j,g.ScreenWidth/2 - g.TileSize*2,g.TileSize*2);
					 break;
					 
					 }
					 
				 }
			 }
			 
			
		 }
		 
	}
	public void drawTradeScreen(){
		
		
		
		
		
	}
	public void tradeSelect() {
		g.subState = 0;
		int x = g.TileSize*16 ;
		int y = g.TileSize*4 ;
		slotCol = 0 ;
		slotRow = 0 ; 
		DrawDialogueWindow(g1);
		g1.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		g1.setColor(blackTrans);
		g1.fillRoundRect(x,y,g.TileSize*3,g.TileSize*4,35,35);
		g1.setColor(Color.WHITE);
		g1.setStroke(new BasicStroke(4));
		g1.drawRoundRect(x,y,g.TileSize*3,g.TileSize*4,35,35);
		
		g1.drawString("Buy", x + 48, y + g.TileSize);
		y += g.TileSize;
		g1.drawString("Sell", x + 48, y + g.TileSize);
		y += g.TileSize;
		g1.drawString("Exit", x + 48, y + g.TileSize);
		
		//Cursor
		g1.setColor(Color.CYAN);
		x = g.TileSize*16;
		y = g.TileSize*4;
		
		switch(commandNum) {
			case 0 : g1.drawString("Buy", x + 48, y + g.TileSize);
				break;
			case 1 : g1.drawString("Sell", x + 48, y + g.TileSize*2);
				break;
			case 2 : g1.drawString("Exit", x + 48, y + g.TileSize*3);
				break;
		}
		if(g.KeyH.confirmPressed) {
			
			switch(commandNum) {
				case 0 : g.subState = 1 ; 
						 break ;
				case 1 : g.subState = 2;
						 break ;
				case 2 : g.gameState = 1 ; g.subState = 0 ;
					 	 break ;
			}
		}
		
	}
	public void tradeSell() {
		int x = g.TileSize*16 ;
		int y = g.TileSize*2 - 24 ;
		int Coins = g.player.Coins ;
		
		g1.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		g1.setColor(blackTrans);
		g1.fillRoundRect(x,y,g.TileSize*3,g.TileSize,35,35);
		g1.setColor(Color.WHITE);
		g1.setStroke(new BasicStroke(3));
		g1.drawRoundRect(x,y,g.TileSize*3,g.TileSize,35,35);
		g1.drawImage(coin.image,x +8,y+4,40,40,null);
	
		g1.drawString(Coins+"", x+g.TileSize, y+32);
	
		DrawInventory(g.player);
		
		//SELL OPERATION
		if(g.KeyH.InteractPressed == 1) {
			int indexItem = getItemIndex() ;
			if(g.player.inventory[indexItem] != null) {
				if( g.player.inventory[indexItem].price == -1) {
					g1.drawString("No Space In Inventory", x-g.TileSize*10, y+g.TileSize*8);
				}
				else {
					g.player.Coins += g.player.inventory[indexItem].price ;
					g.player.inventory[indexItem] = null ;
				}
				
			}
			
		}
	}
	public void tradeBuy() {
		
		int x = g.TileSize*16 ;
		int y = g.TileSize*2 - 24 ;
		int Coins = g.player.Coins ;
		
		g1.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		g1.setColor(blackTrans);
		g1.fillRoundRect(x,y,g.TileSize*3,g.TileSize,35,35);
		g1.setColor(Color.WHITE);
		g1.setStroke(new BasicStroke(3));
		g1.drawRoundRect(x,y,g.TileSize*3,g.TileSize,35,35);
		g1.drawImage(coin.image,x +8,y+4,40,40,null);
	
		g1.drawString(Coins+"", x+g.TileSize, y+32);
	
		
		DrawInventory(merchant);
		
		//BUY OPERATION
		if(g.KeyH.InteractPressed == 1) {
			if(merchant.inventory[getItemIndex()] != null) {
				if(merchant.inventory[getItemIndex()].price > g.player.Coins) {
					g1.setColor(Color.RED);
					g1.setFont(new Font("Times New Roman", Font.PLAIN, 30));
					g1.drawString(Coins+"", x+g.TileSize, y+32);
				}
				else if(g.player.emptyInventorySlot() == -1){
					g1.setColor(Color.RED);
					g1.setFont(new Font("Times New Roman", Font.PLAIN, 30));
					g1.drawString("No Space In Inventory", x+g.TileSize, y+32);
				}else {
					g.player.Coins -= merchant.inventory[getItemIndex()].price ;
					g.player.inventory[g.player.emptyInventorySlot()] = merchant.inventory[getItemIndex()] ;
				}
			}
			
		}
	}
	
	public void drawGameOver(){
		
		//FONT AND BLACK BACKGROUND
		g1.setFont(AncientModernTales);
		g1.setFont(g1.getFont().deriveFont(Font.PLAIN,48));
		g1.setColor(new Color(0,0,0,200));
		g1.fillRect(0,0,g.ScreenWidth,g.ScreenHeight);
		
		//SHADOW
		g1.setColor(Color.BLACK);
		g1.drawString("GAME OVER",  g.ScreenWidth/2 - g.TileSize*2 , g.TileSize*3);
		
		//GAMEOVER TEXT
		g1.setColor(Color.RED);
		g1.drawString("GAME OVER",  g.ScreenWidth/2 - g.TileSize*2-6 , g.TileSize*3-4);
		
		//RESTART TEXT
		g1.setFont(new Font("Times New Roman", Font.PLAIN, 36));
		g1.setColor(Color.WHITE);
		g1.drawString("Retry",   g.ScreenWidth/2 - g.TileSize+16 , g.TileSize*6);
		g1.drawString("Quit",   g.ScreenWidth/2 - g.TileSize+24 , g.TileSize*7+16);
		
		//CURSOR
		g1.setColor(Color.CYAN);
		switch(commandNum) {
			case 0 :  g1.drawString("Retry",   g.ScreenWidth/2 - g.TileSize+16 , g.TileSize*6);break;
			case 1 :  g1.drawString("Quit",   g.ScreenWidth/2 - g.TileSize+24 , g.TileSize*7+16);break;
		}
	}
	public void settings_control() {
		g1.setColor(Color.RED);
		g1.drawString( "Settings" , g.ScreenWidth - g.TileSize*8  , g.TileSize*4) ;
		g1.setFont(g1.getFont().deriveFont(Font.PLAIN,24));
		g1.setColor(Color.WHITE);
		g1.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		
		
		g1.drawString( "Attack/Interact" , g.ScreenWidth - g.TileSize*12  , (int) (g.TileSize*5.8)) ;
		g1.drawString( "Move" , g.ScreenWidth - g.TileSize*12  , (int) (g.TileSize*7.8)) ;
		g1.drawString( "Pause" , g.ScreenWidth - g.TileSize*12  , (int) (g.TileSize*9.8)) ;
		g.config.saveConfig();
	}
	public void drawSettings() {
		g1.setColor(Color.RED);
		g1.drawString( "Settings" , g.ScreenWidth - g.TileSize*8  , g.TileSize*4) ;
		g1.setFont(g1.getFont().deriveFont(Font.PLAIN,24));
		g1.setColor(Color.WHITE);
		g1.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		
		
		g1.drawString( "FullScreen" , g.ScreenWidth - g.TileSize*12  , (int) (g.TileSize*5.8)) ;
		g1.setStroke(new BasicStroke(3));
		g1.drawRect(g.ScreenWidth - g.TileSize*12 + g.TileSize*3 , (int) (g.TileSize*5.8) - 20 , 20, 20);
		if(fullScreenOn == true) {
			g1.fillRect(g.ScreenWidth - g.TileSize*12 + g.TileSize*3 , (int) (g.TileSize*5.8) - 20 , 20, 20);
		}
		
		g1.drawString( "Music" , g.ScreenWidth - g.TileSize*12  , (int) (g.TileSize*7.8)) ;
		g1.setStroke(new BasicStroke(3));
		g1.drawRect(g.ScreenWidth - g.TileSize*12 + g.TileSize*3 , (int) (g.TileSize*7.8) - 20 ,20, 20);
		if(musicOn) {
			g1.fillRect(g.ScreenWidth - g.TileSize*12 + g.TileSize*3 , (int) (g.TileSize*7.8) - 20 , 20, 20);
		}
		
		g1.drawString( "Control" , g.ScreenWidth - g.TileSize*5  , (int) (g.TileSize*5.8)) ;
		
		g1.drawString( "Reset" , g.ScreenWidth - g.TileSize*5  , (int) (g.TileSize*7.8)) ;
		
		g1.drawString( "Back" , g.ScreenWidth - g.TileSize*5  , (int) (g.TileSize*10.8)) ;
		
		//CURSOR
		g1.setColor(Color.CYAN);
		switch(subCommandNum ) {
		
		case 0 : g1.drawString( "FullScreen " , g.ScreenWidth - g.TileSize*12  , (int) (g.TileSize*5.8)) ;
			break;
		case 1 : g1.drawString( "Music" ,  g.ScreenWidth - g.TileSize*12  , (int) (g.TileSize*7.8)) ;
			break;
		case 2 : g1.drawString( "Control " ,   g.ScreenWidth - g.TileSize*5  , (int) (g.TileSize*5.8)) ;
			break;
		case 3 : g1.drawString( "Reset" , g.ScreenWidth - g.TileSize*5  , (int) (g.TileSize*7.8)) ;
			break;
		case 4 : g1.drawString("Back", g.ScreenWidth - g.TileSize*5  , (int) (g.TileSize*10.8));
		 	break;
		default : subCommandNum = 0 ;
		}
		
	}
	public int getItemIndex() {
		return slotCol + slotRow*9;
	}
	public void DrawInventory(Entity entity) {
		
		//MAIN FRAME
		int x = g.TileSize*5;
		int y = g.TileSize;
		int width = g.TileSize*10 + 30 ;
		int height = entity.inventorySize/9 * g.TileSize + g.TileSize ;
		
		
		g1.setColor(blackTrans);
		g1.fillRoundRect(x, y, width, height, 35, 35);
		g1.setStroke(new BasicStroke(5));
		g1.setColor(Color.WHITE);
		g1.drawRoundRect(x, y, width, height, 35, 35);
		
		//SLOTS
		final int slotXStart = x + 20 ;
		final int slotYStart = y + 20 ;
		int slotX = slotXStart ;
		int slotY = slotYStart ;
		int slotSize = g.TileSize + 5 ;
		
		//INDICATOR
		
		int indicatorX = slotXStart +  slotCol*slotSize  ;
		int indicatorY = slotYStart +  slotRow*slotSize ;
		
		int indicatorWidth = g.TileSize ;
		int indicatorHeight = g.TileSize ;
		
		//DRAW INDICATOR FOR NPCs ( TRADE )
		if(entity != g.player && (indicatorY > slotYStart + ((int)(entity.inventorySize / 9)-1)* slotSize)) {
			
			slotRow = ((int)(entity.inventorySize / 9)-1)*slotSize ;
			indicatorY = slotYStart +  slotRow*slotSize ;
			
		}
		//DRAW INDICATOR
		g1.setStroke(new BasicStroke(2));
		g1.drawRoundRect(indicatorX, indicatorY, indicatorWidth, indicatorHeight, 15, 15);
		
		//DRAW INVENTORY ITEMS 
		for(int i = 0 ; i<entity.inventorySize ; i++) {
			if(entity.inventory[i] != null) {
			
				g1.drawImage(entity.inventory[i].image, slotX, slotY,null);
				slotX += slotSize ;
				
			}else {
				slotX += slotSize ;
			}
			if(i== 8 || i == 9*2-1 || i == 9*3-1 ) {
				slotY += slotSize;
				slotX = slotXStart ;
			}
		}
		
		//DESCRIPTION FRAME
		int dFrameX = x + g.TileSize * 2 ;
		int dFrameY = g.TileSize * 5 + 16;
		int dFrameWidth = g.TileSize * 7 ;
		int dFrameHeight = g.TileSize * 3 ;
		itemIndex = getItemIndex() ;
		
		//Draw Description for TRADE
		if(entity != g.player) {
			dFrameY = g.TileSize * 3 + 16 ;
		}
		if(entity.inventory[itemIndex] != null) {
			g1.setColor(blackTrans);
			g1.fillRoundRect(dFrameX, dFrameY, dFrameWidth, dFrameHeight, 35, 35);
			
			//DESCRIPTION TEXT
			int textX = dFrameX + 16 ;
			int textY = dFrameY + 32 ;
			
			
			
			g1.setColor(Color.WHITE);
			g1.setFont(new Font("Times New Roman", Font.PLAIN, 24));
			
			for(String line :entity.inventory[itemIndex].description.split("\n")) {
				
				g1.drawString(line, textX  , textY  );
				textY += 40 ;
				
			}
		}
		
	}
	/*public void DrawLevelUpMessage() {
		int x =  g.ScreenWidth/2 - g.TileSize*2;
		int y =  g.TileSize*2 ;
		
		
		g1.setFont(new Font("Times New Roman", Font.PLAIN, 48));
		g1.setColor(Color.RED);
		
		
		j++;
		if(j < 200 && j > 100) {
		
			g1.drawString(currentDialogue, x, y);
		}
		else if(j>200){
			j = 0 ;
			currentDialogue = "";
		}
	
	}*/
	public void DrawNotificationMessage(int j,int x , int y){
		
		g1.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		g1.setColor(Color.WHITE);
		if(j == 2) {
			g1.setFont(new Font("Times New Roman", Font.PLAIN, 48));
			g1.setColor(Color.RED);
		}
		
		if(Timer < 50 ) {
			String message = currentDialogue[j];
			Timer++;
			g1.drawString(message, x, y);
			
		}
		else {
			Timer = 0 ;
			currentDialogue[j] = "";
		}
			
		
		
		g1.setFont(AncientModernTales);
		g1.setFont(g1.getFont().deriveFont(Font.PLAIN,24));
	}
	public void DrawPause(Graphics2D g1) {
		//THIS IS A BIT SCUFFED - THOUGHT OF USING THE DRAWTITLE() METHOD BUT ADJUSTMENTS HAVE TO BE MADE ANYWAY
		g1.setFont(g1.getFont().deriveFont(Font.PLAIN,72));
		g1.setColor(new Color(0,0,0,200));
		g1.fillRect(0,0,g.ScreenWidth,g.ScreenHeight);
		g1.setColor(Color.blue);
		g1.drawString( "MyFirstGame " , g.ScreenWidth/2 - g.TileSize*3 - 20 , g.TileSize*2) ;
		g1.setColor(Color.white);
		g1.setFont(g1.getFont().deriveFont(Font.PLAIN,48));
		g1.drawString( "Resume " ,  g.TileSize - 20 , g.TileSize*4) ;
		g1.drawString( "Title menu " ,  g.TileSize - 20 , g.TileSize*6) ;
		g1.drawString( "Settings " ,  g.TileSize - 20 , g.TileSize* 8) ;
		g1.drawString( "Quit " ,  g.TileSize - 20 , g.TileSize* 11) ;
		g1.setColor(Color.CYAN);
		switch(commandNum ) {
		
			case 0 : g1.drawString( "Resume " ,  g.TileSize - 20 , g.TileSize*4) ;
				break;
			case 1 : g1.drawString( "Title menu " ,  g.TileSize - 20 , g.TileSize*6) ;
				break;
			case 2 : g1.drawString( "Settings " ,  g.TileSize - 20 , g.TileSize* 8) ;
				break;
			case 3 : g1.drawString( "Quit" ,  g.TileSize - 20 , g.TileSize* 11) ;
				break;
			default : commandNum = 0 ;
		}
		
	}	
	public void DrawDialogueWindow(Graphics2D g1) {
		
		int x = g.TileSize*2 ;
		int y = g.TileSize/2 ;
		int width = g.ScreenWidth - g.TileSize * 4 ;
		int height = g.TileSize * 4 ;
		
		g1.setColor(blackTrans);
		g1.fillRoundRect(x, y, width, height, 35, 35);
		g1.setColor(Color.WHITE);
		g1.setFont(g1.getFont().deriveFont(Font.PLAIN,28));
		x += g.TileSize ;
		y += g.TileSize ;
		
		for(String line : currentDialogue[0].split("\n")) {
			
			g1.drawString(line, x  , y );
			y += 40 ;
			
		}
		
		
		
	}
	public void DrawStats(Graphics2D g1) {
		
		g1.setColor(Color.RED);
		g1.drawString( "Stats" , g.ScreenWidth - g.TileSize*5  , g.TileSize*3) ;
		g1.setFont(g1.getFont().deriveFont(Font.PLAIN,24));
		g1.setColor(Color.WHITE);
		g1.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		
		Coins = coin.image;
		int textX = g.ScreenWidth - g.TileSize*6 ;
		int textY = g.TileSize*4 ;

		g1.drawImage(Coins, textX - 48 , textY - 32, 48, 48,null);
		g1.drawString( "Hp" , textX  , textY) ;
		textY +=g.TileSize ;
		
		g1.drawImage(Coins, textX - 48 , textY - 32, 48, 48,null);
		g1.drawString( "BaseDamage" , textX  , textY) ;
		textY +=g.TileSize ;
		
		g1.drawImage(Coins, textX - 48 , textY - 32, 48, 48,null);
		g1.drawString( "Strength" , textX  , textY) ;
		textY +=g.TileSize ;
		
		g1.drawImage(Coins, textX - 48 , textY - 32, 48, 48,null);
		g1.drawString( "Defense" , textX  , textY) ;
		textY +=g.TileSize ;
		
		g1.drawImage(Coins, textX - 48 , textY - 32, 48, 48,null);
		g1.drawString( "Speed" , textX  , textY) ;
		textY +=g.TileSize ;
		
		g1.drawImage(Coins, textX - 48 , textY - 32, 48, 48,null);
		g1.drawString( "Level" , textX  , textY) ;
		textY += g.TileSize*2 ;
		
		
		g1.drawImage(Coins, textX - 48 , textY - 32, 48, 48,null);
		g1.drawString( "Coins" , textX  , textY) ;
		
		
		//Set VALUES
		textX = g.ScreenWidth - g.TileSize*2 ;
		textY = g.TileSize*4 ;
		String Value ;
		
		Value = String.valueOf(g.player.HP);
		g1.drawString( Value + "/" + g.player.MaxHP , textX  , textY) ;
		textY +=g.TileSize ;
		
		Value = String.valueOf(g.player.baseDamage);
		g1.drawString( Value , textX  , textY) ;
		textY +=g.TileSize ;
		
		Value = String.valueOf(g.player.strength);
		g1.drawString( Value , textX  , textY) ;
		textY +=g.TileSize ;
		
		Value = String.valueOf(g.player.defense);
		g1.drawString( Value , textX  , textY) ;
		textY +=g.TileSize ;
		
		Value = String.valueOf(g.player.Speed);
		g1.drawString( Value , textX  , textY) ;
		textY +=g.TileSize ;
		
		Value = String.valueOf(g.player.level);
		g1.drawString( Value , textX  , textY) ;
		textY +=g.TileSize*2 ;
		
		Value = String.valueOf(g.player.Coins);
		g1.drawString( Value , textX  , textY) ;
		
	}
	public void DrawTitle(Graphics2D g1) {
		
		g1.setFont(g1.getFont().deriveFont(Font.PLAIN,72));
		g1.setColor(new Color(0,0,0));
		g1.fillRect(0,0,g.ScreenWidth,g.ScreenHeight);
		g1.setColor(Color.blue);
		g1.drawString( "MyFirstGame " , g.ScreenWidth/2 - g.TileSize*3 - 20 , g.TileSize*2) ;
		g1.setColor(Color.white);
		g1.setFont(g1.getFont().deriveFont(Font.PLAIN,48));
		g1.drawString( "New Game " ,  g.TileSize - 20 , g.TileSize*4) ;
		g1.drawString( "Continue " ,  g.TileSize - 20 , g.TileSize*6) ;
		g1.drawString( "Settings " ,  g.TileSize - 20 , g.TileSize* 8) ;
		g1.drawString( "Quit " ,  g.TileSize - 20 , g.TileSize* 11) ;
		g1.setColor(Color.CYAN);
		switch(commandNum ) {
		
			case 0 : g1.drawString( "New Game " ,  g.TileSize - 20 , g.TileSize*4) ;
				break;
			case 1 : g1.drawString( "Continue " ,  g.TileSize - 20 , g.TileSize*6) ;
				break;
			case 2 : g1.drawString( "Settings " ,  g.TileSize - 20 , g.TileSize* 8) ;
				break;
			case 3 : g1.drawString( "Quit " ,  g.TileSize - 20 , g.TileSize* 11) ;
				break;
			default : commandNum = 0 ;
				
		}
		
	}
	public void drawHeartsMana() {

		//SET CUSTOM FONT
		g1.setFont(AncientModernTales);
		g1.setFont(g1.getFont().deriveFont(Font.PLAIN,24));
		
		//HP : DRAW BLANKS
		i = 0 ;
		int x =  g.TileSize;
		int y = g.ScreenHeight - g.TileSize*2 ;
		
		while(i < g.player.MaxHP /2) {
			 
			g1.drawImage(BlankHeart, x ,y,48,48,null );
			x += g.TileSize ;
			i++;
		}
		
		//HP : OVERLAY THE FULL AND HALF HEARTS
		 i = 0 ;
		 x =  g.TileSize;
		 y = g.ScreenHeight - g.TileSize*2 ;
		 while( i < (float)g.player.HP/2 ) {
			 i++;
			 
			 if(i> (float)g.player.HP/2) {
					
					g1.drawImage(HalfHeart, x ,y,48,48,null );
					x += g.TileSize ;
				}
			 
			 else {
				 
				 g1.drawImage(FullHeart, x ,y,48,48,null );
				 x += g.TileSize ;
			 }
		 }
		//DRAW MANA CRYSTALS 
		 i=0;
		 x =  g.TileSize;
		 y = g.ScreenHeight - g.TileSize*3 ;
		 while( i < g.player.maxMana ) {
			
			g1.drawImage(BlankCrystal, x ,y,48,48,null );
			if(i<g.player.mana) {
				g1.drawImage(FullCrystal, x ,y,48,48,null );
			}
			x += g.TileSize ;
			i++;
			
		}
	}
	
}
