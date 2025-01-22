package Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
	GamePanel g;
	public Config(GamePanel g) {
		this.g = g ;
	}
	public void saveConfig() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
			
			//set FullScreen on/off
			if(g.UI.fullScreenOn == true ) {
				bw.write("ON");
			}else {
				bw.write("OFF");
			}
			bw.newLine();
			//set Music on/off
			if(g.UI.musicOn == true ) {
				bw.write("ON");
			}else {
				bw.write("OFF");
			}
			bw.newLine();
			
			bw.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void loadConfig() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("config.txt"));
			String line = br.readLine();
			
			//fullScreen
			if(line.equals("ON") ) {
				g.UI.fullScreenOn = true ;
				
			}else if(line.equals("OFF")){
				g.UI.fullScreenOn = false ;
				
			}
			
			//music
			line = br.readLine();
			if(line.equals("ON")) {
				g.UI.musicOn = true ;
				
			}else if(line.equals("OFF")){
				g.UI.musicOn = false ;
				
			}
			
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void resetConfig() {
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

			bw.write("OFF");
			bw.newLine();
			bw.write("OFF");
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
