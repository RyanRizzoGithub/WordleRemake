import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;

public class Wordle {
	public static void main(String[] args) {		
		playClip();
		WordleMenuUI menuUI = new WordleMenuUI();
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
}
