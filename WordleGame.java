import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class WordleGame {

	// Katelen Tellez
	private Dictionary dictionary;
	private String word;
	private int guess;
	private Statistics stats;
	private boolean result;
	
	// Ryan
	private HashMap<String, Integer> charStatus;

	// @Katelen Tellez added constructor
	public WordleGame(Statistics statistics) {

		// Katelen Tellez
		dictionary = new Dictionary();
		word = dictionary.getRandomWord().toUpperCase();
		System.out.println("Word: " + word);
		guess = 0;
		stats = statistics;
		result = false; // loss by default
		
		// Ryan
		charStatus = new HashMap<String, Integer>();
		String[] qwerty = { "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k",
				"l", "z", "x", "c", "v", "b", "n", "m" };
		for (int i = 0; i < qwerty.length; i++) {
			charStatus.put(qwerty[i], -2);
		}
	}

	// Katelen Tellez
	public void addGuess() {
		guess += 1;
	}
	
	public void checkEndGame() {
		
		if(gameIsOver()) {
			System.out.println("Game is over.");
			
			System.out.println("Statistics:");
			stats.addGame(result);
			stats.addPlayed();
			stats.calcCurStreak();
			stats.calcMaxStreak();
			stats.calcWinStat();
			if(result)
				stats.addWinningGuess(guess);
			stats.updateStats();
			
			System.out.println("Played: " +stats.getStats()[0]);
			System.out.println("Win %: " +stats.getStats()[1]+ "%");
			System.out.println("Current Streak: " +stats.getStats()[2]);
			System.out.println("Max Streak: " +stats.getStats()[3]);
			System.out.println("Guess Distribution:");
			System.out.println("1: " +stats.getStats()[4]);
			System.out.println("2: " +stats.getStats()[5]);
			System.out.println("3: " +stats.getStats()[6]);
			System.out.println("4: " +stats.getStats()[7]);
			System.out.println("5: " +stats.getStats()[8]);
			System.out.println("6: " +stats.getStats()[9]);
			
			System.exit(0);
		}
	}

	// Katelen Tellez
	public boolean gameIsOver() {
		
		if (guess > 6) {
			return true;
		}
		return guessIsCorrect();
	}

	// Katelen Tellez
	public boolean guessIsCorrect() {

		int charsCorrect = 0;
		String lowerWord = word.toLowerCase();
		
		for(int i = 0; i < 5; i++) {
			
			char c = lowerWord.charAt(i);
			
			if(charStatus.get(String.valueOf(c)) == 1) {
				charsCorrect++;
			}
			
		}
		
		if(charsCorrect == 5) {
			result = true;
			return true;
		}
		
		return false;
	}

	/*
	 * Ryan Rizzo
	 */
	public int checkChar(char c, int index) {
		c = Character.toUpperCase(c);

		int result = -1;
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == c) {
				result++;
				break;
			}
		}

		if (word.charAt(index) == c) {
			result++;
		}

		// Update charStatus
		if (result > charStatus.get(String.valueOf(c).toLowerCase())) {
			charStatus.put(String.valueOf(c).toLowerCase(), result);
		}
		// System.out.println("char: " + c + " index: " + index + " result: " + result);
		return result;
	}

	public HashMap<String, Integer> getCharStatus() {
		return this.charStatus;
	}
}
