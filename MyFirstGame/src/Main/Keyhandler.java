package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyhandler implements KeyListener{
	
	GamePanel g ;
	public boolean UpPressed,DownPressed,RightPressed,LeftPressed;
	public boolean shotKeyPressed,dropPressed,confirmPressed;
	public int InteractPressed = 1,InventoryPressed = 0 ;
	
	public Keyhandler (GamePanel g) {
		this.g = g ;
	}
	
	
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {

		 int code = e.getKeyCode();
		 
		 //INVENTORY STATE 
		 if(code == KeyEvent.VK_G) {
			 dropPressed  = true ;
		 }
		 if(code == KeyEvent.VK_I) {
			 if(g.gameState == 1 || g.gameState == 4 ) {	
				InventoryPressed++ ;
				g.gameState = 6;
			}
			 else if(g.gameState == 6) {
				InventoryPressed = 0 ;
				g.gameState = 1;
			}
		 }
		 	//INDICATOR CONTROLLER
		 if(g.gameState == 6 || (g.gameState == 7 && (g.subState == 1 || g.subState == 2))) {
				
				if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
					if(g.UI.slotRow != 2) {
						g.UI.slotRow ++ ;
					}
					
				}
				if(code == KeyEvent.VK_Z || code == KeyEvent.VK_UP) {
					if(g.UI.slotRow != 0) {
						g.UI.slotRow -- ;
					}
				}
				if(code == KeyEvent.VK_Q || code == KeyEvent.VK_LEFT) {
					if(g.UI.slotCol != 0) {
						g.UI.slotCol -- ;
					}
				}
				if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
					if(g.UI.slotCol != 8) {
						g.UI.slotCol ++ ;
					}
				}
				
				if(code == KeyEvent.VK_E) {
					
					if(g.gameState == 6) {
						g.player.selectItem();
					}
					else if(g.gameState == 7) {
						InteractPressed ++ ;
						System.out.println(InteractPressed);
						
					}
					
				}
				
				
			}
		//TITLE and PAUSE STATES + Settings State
		 
		 	//SettingsCommandNum cursor
			if(g.gameState == 3 ) {
				confirmPressed = false ;
				if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
					g.UI.subCommandNum ++ ;
					
				}
				else if(code == KeyEvent.VK_Z || code == KeyEvent.VK_UP) {
					g.UI.subCommandNum -- ;
					
				}
				
			//Settings Menu
				else if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE ) {
					confirmPressed = true ;
					switch(g.UI.subCommandNum) {
					case 0 : g.UI.fullScreenOn = (g.UI.fullScreenOn)? true : true ;
						break ;
					case 1 : g.UI.musicOn = (g.UI.musicOn)? false : true ;
						break ;
					case 3 : g.config.resetConfig();
							 g.config.loadConfig();
						break;
					case 4 : g.config.saveConfig(); 
						g.gameState = 1 ;
						break;
					
					}
					
				}
					
			}
			
		if(g.gameState == 0 || g.gameState == 2 ) {
			
			if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				g.UI.commandNum ++ ;
				
			}
			if(code == KeyEvent.VK_Z || code == KeyEvent.VK_UP) {
				g.UI.commandNum -- ;
				
			}
			//HANDELING THE POOL OF COMMANDNUM
			if(g.UI.commandNum == -1) {
				g.UI.commandNum = 3;
			}
			else if(g.UI.commandNum == 4) {
				g.UI.commandNum = 0;
			}
			
			
			//INPPUTS
			if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE ) {
				
				//TITLE
				if(g.gameState == 0) {
					if(g.UI.commandNum == 0) {
						
						g.Restart();
						g.gameState = 1 ;
					}
					if(g.UI.commandNum == 1) {
						g.gameState = 1 ;
					}
					if(g.UI.commandNum == 2) {
						
						g.previousGameState = g.gameState ;
						g.gameState = 3;
					}
					if(g.UI.commandNum == 3) {
						System.exit(0); 
					}
				}
				//PAUSE
				else if(g.gameState == 2) {
					
					if(g.UI.commandNum == 0) {
						g.gameState = 1 ;
					}
					else if(g.UI.commandNum == 3) {
						System.exit(0); 
					}
					else if(g.UI.commandNum == 1) {
						g.gameState = 0 ;
					}
					else if(g.UI.commandNum == 2) {
						g.previousGameState = g.gameState ;
						g.gameState = 3 ;
					}
				}
			
			}
		}
		
		//PLAY STATE
		else if(g.gameState == 1 || g.gameState == 4) {
			
			if(code == KeyEvent.VK_Z) {
				UpPressed = true;
				
			}
			if(code == KeyEvent.VK_S) {
				DownPressed = true;
		
				}
			if(code == KeyEvent.VK_D) {
				RightPressed = true;
				
			}
			if(code == KeyEvent.VK_Q) {
				LeftPressed = true;
			}
			
			if(code == KeyEvent.VK_E) {
				if (g.gameState == 4 ) {
					g.gameState = 1;
				}
				
				g.player.attacking = true ; 
				InteractPressed ++ ;
				
			}
			if(code == KeyEvent.VK_F) {
				shotKeyPressed = true ;
			}
			
		}
		
		//GAME OVER STATE
		if(g.gameState == 5) {
			
			if(code == KeyEvent.VK_Z || code == KeyEvent.VK_UP) {
				g.UI.commandNum ++;
				if(g.UI.commandNum > 1) {
					g.UI.commandNum = 0;
				}
			}
			if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				g.UI.commandNum -- ;
				if(g.UI.commandNum < 0) {
					g.UI.commandNum = 1;
				}
			}
			if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
				switch(g.UI.commandNum) {
					case 0 : g.gameState = 1 ; g.player.deathReset() ; 
						break ;
					case 1 : System.exit(0);
						break ;
				}
			}
		}
		//TRADE STATE 
		if(g.gameState == 7) {
			
			if(code == KeyEvent.VK_Z || code == KeyEvent.VK_UP) {
				g.UI.commandNum --;
				if(g.UI.commandNum < 0) {
					g.UI.commandNum = 2;
				}
			}
			if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				g.UI.commandNum ++ ;
				if(g.UI.commandNum > 2) {
					g.UI.commandNum = 0;
				}
			}
			if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
				confirmPressed = true ;
			}
		}
		
		//LOAD MAP WITHOUT RESTART
		if(code == KeyEvent.VK_T) {
			g.TileM.LoadMap(g.currentMap);
		}
		if(code == KeyEvent.VK_J) {
			g.TileM.LoadMap("TutorialDungeonMap");
		}
		if(code == KeyEvent.VK_ESCAPE) {
			
			if(g.gameState == 1) {
				
				g.gameState = 2;
			}
			else if(g.gameState == 2 || g.gameState == 4 || g.gameState == 6 ){
				g.player.dialogueMode = 0 ;
				g.gameState = 1;
				InventoryPressed = 0 ;
				g.subState = 0;
			}
			else if(g.gameState == 7) {
				if(g.subState == 0) {
					g.gameState = 1 ;
				}
				g.player.dialogueMode = 0 ;
				InventoryPressed = 0 ;
				g.subState = 0;
			}
				
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_Z) {
			UpPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			DownPressed = false;
			}
		if(code == KeyEvent.VK_D) {
			RightPressed = false;
		}
		if(code == KeyEvent.VK_Q) {
			LeftPressed = false;
		}
		if(code == KeyEvent.VK_E) {
			InteractPressed = 0 ;
		}
		if(code == KeyEvent.VK_F) {
			shotKeyPressed = false ;
		}
		if(code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
			confirmPressed = false ;
		}
		
		
	}

}
