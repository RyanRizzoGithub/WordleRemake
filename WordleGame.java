import java.util.HashMap;

public class WordleGame {
	
	String word;
	private int guess;
	private boolean result;
	private boolean guessIsCorrect;
	private boolean gameOver;
	
	
	// Ryan
	private HashMap<String,Integer> charStatus;
	private WordleDictionary dic;
	private int theme;
	
	public WordleGame(int theme) {
		
		// generate random word from dictionary (no repeats)
		dic = new WordleDictionary();
		// word = dic.getRandomWord();
		word = "PARTY";
		guess = 0;
		result = false;
		gameOver = false;
		guessIsCorrect = false;
		this.theme = theme;
		
		
		charStatus = new HashMap<String, Integer>();
		String[] qwerty = {"q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h",
				"j","k","l","z","x","c","v","b","n","m"};
		for (int i=0; i<qwerty.length; i++) {
			charStatus.put(qwerty[i], -2);
		}
	}
	
	// Katelen Tellez
	public boolean gameIsOver() {
		
		// update player stats
		if(guess == 6 || guessIsCorrect) {
			Wordle.player.addGame(guessIsCorrect, guess);
		}
		
		if (guess == 6) {
			return true;
		}
		return guessIsCorrect;
	}
	
	// Katelen Tellez
	public void setGuessCorrect() {
		this.guessIsCorrect = true;
	}
	
	// Katelen Tellez
	public void addGuess() {
		this.guess++;
	}
	
	/*
	 * Ryan Rizzo
	 */
	public int checkChar(char c, int index) {
		c = Character.toUpperCase(c);
		
		int result = -1;
		for (int i=0; i<word.length(); i++) {
			if (word.charAt(i) == c) {
				result++;
				break;
			}
		} if (word.charAt(index) == c) {
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
	
	public int getTheme() {
		return this.theme;
	}
	
	public void setOver() {
		this.gameOver = true;
	}
}