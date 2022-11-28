import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import  java.io.*;
import java.net.URL;
public class Wordle {
	public static void main(String[] args) {
		
		Statistics stats = new Statistics();
		WordleGame game = new WordleGame(stats);
		WordleUI ui = new WordleUI(game);
		
		playClip();
		ui.startMenu();
		ui.startGame();
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
}