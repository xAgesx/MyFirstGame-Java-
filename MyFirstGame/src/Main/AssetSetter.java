package Main;

import Entity.NpcMerchant;
import Entity.NpcWarrior;
import Monster.BlueSlime;
import Monster.WhiteRat;
import Object.Chest;
import Object.Coin;
import Object.DoubleDoor;
import Object.Key;
import Object.LavaTrap;
import Object.Potion;
import Object.Sword;

public class AssetSetter {
	
	GamePanel g ;
	
	public AssetSetter(GamePanel g) {
		this.g = g ;
		
	}
	
	
	public void npcSetter() {
		
		//Map 0 : worldMap
		g.npcsMap0[0] = new NpcWarrior(g);
		g.npcsMap0[0].worldX = 12 * g.TileSize;
		g.npcsMap0[0].worldY = 12 * g.TileSize;
		
		g.npcsMap0[1] = new NpcWarrior(g);
		g.npcsMap0[1].worldX = 12 * g.TileSize;
		g.npcsMap0[1].worldY = 14 * g.TileSize;
		
		//Map 1 : TutorialDungeonMap
		g.npcsMap1[0] = new NpcMerchant(g) ;
		g.npcsMap1[0].worldX = 8 * g.TileSize;
		g.npcsMap1[0].worldY = 14 * g.TileSize;
	}public void setObject() {
		
		//Map 0 : worldMap
		g.objMap0[0] = new Key();	
		g.objMap0[0].worldX = 9 * g.TileSize;
		g.objMap0[0].worldY = 10 * g.TileSize;
		
		g.objMap0[1] = new Potion(g);	
		g.objMap0[1].worldX = 6 * g.TileSize;
		g.objMap0[1].worldY = 17 * g.TileSize;
		
		g.objMap0[2] = new Coin();
		g.objMap0[2].worldX = 7 * g.TileSize;
		g.objMap0[2].worldY = 10 * g.TileSize;
		
		g.objMap0[3] = new Chest();
		g.objMap0[3].worldX = 10 * 48;
		g.objMap0[3].worldY = 14 * 48;
		
		g.objMap0[5] = new LavaTrap(g);
		g.objMap0[5].worldX = 12 * 48;
		g.objMap0[5].worldY = 16 * 48;
		
		g.objMap0[4] = new Sword();
		g.objMap0[4].worldX = 8 * g.TileSize;
		g.objMap0[4].worldY = 19 * g.TileSize;
		
		g.objMap0[15] = new DoubleDoor();
		g.objMap0[15].worldX = 6 * g.TileSize;
		g.objMap0[15].worldY = 18 * g.TileSize;
		
		//Map 1 : TutorialDungeonMap
		g.objMap1[0] = new DoubleDoor();
		g.objMap1[0].worldX = 6 * g.TileSize;
		g.objMap1[0].worldY = 18 * g.TileSize;
	}
	
	public void setMonster() {
		
		//Map 0 : worldMap
		g.monstersMap0[0] = new BlueSlime(g);
		g.monstersMap0[0].worldX = 8 * 48;
		g.monstersMap0[0].worldY = 14 * 48;
		
		g.monstersMap0[1] = new BlueSlime(g);
		g.monstersMap0[1].worldX = 9 * 48;
		g.monstersMap0[1].worldY = 13 * 48;
		
		//Map 1 : TutorialDungeonMap
		g.monstersMap1[0] = new BlueSlime(g);
		g.monstersMap1[0].worldX = 7 * 48;
		g.monstersMap1[0].worldY = 16 * 48;
	}
}
