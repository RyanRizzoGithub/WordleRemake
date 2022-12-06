import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Wordle {
	
	protected static WordlePlayer player;
	protected static ArrayList<WordlePlayer> players = new ArrayList<>();
	
	public static void main(String[] args) {	
		player = new WordlePlayer("Guest",  "Password",new File( "./txt/guest.txt"));
		player.setBackground(0);
		player.setShirt(1);
		player.setFace(2);
		playClip();
		loadPlayers();
		printPlayers();
		WordleMenuUI menuUI = new WordleMenuUI(WordleUI.DARK_THEME);
		WordleUI.startMenu(menuUI);
	}
	

	public static void playClip() {
		new Thread(new Runnable() {
			public void run() {
		    	try {
		    		File f = new File("./Audio/Theme.wav");
		    		AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());  
		    		Clip clip = AudioSystem.getClip();
		    		clip.open(audioIn);
		    		clip.loop(Clip.LOOP_CONTINUOUSLY);
		    	} catch (Exception e){
		    		
		    	}
			}
		}).start();
    }
	
	/**
	 * Loads all the player profiles
	 * @author Gregory Jenkins
	 */
	public static void loadPlayers() {
		File dir = new File("./Players/");
		File[] files = dir.listFiles();
		
		if (files != null) {
			for(File file : files) {
				ObjectInputStream ios;
				try {
					ios = new ObjectInputStream(new FileInputStream(file));
					WordlePlayer temp = (WordlePlayer) ios.readObject();
					players.add(temp);
				} catch (IOException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		Collections.sort(players);
	}
	
	/**
	 * Prints the stats of all players to the console.
	 * Used for testing.
	 */
	public static void printPlayers() {
		for (WordlePlayer p : players) {
			p.printStats();
		}
	}
}