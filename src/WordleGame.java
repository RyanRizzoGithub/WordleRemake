import java.io.IOException;
import java.util.HashMap;


/**
 * AUTHOR(S):		Katelen Tellez & Ryan Rizzo
 * FILE:		WordleGame.java
 * CLASS:		CSC 335 - Final Project
 * DATE:		12/6/22
 * PURPOSE:		Responsible for storing the game state, and well as handling user input
 */
public class WordleGame {
	private static WordleGameUI gameUI = null;
	private String word;
	private String secondWord;
	private String guess;
	private boolean guessCorrect;
	private int [] guessResults;
	private boolean [] charFoundStatus = new boolean [5];
	private boolean gameOver;
	private boolean guessIsCorrect;
	private int guessNum;
	private boolean oneFound;
	private HashMap<String,Integer> charStatus;
	private WordleDictionary dic;
	private String mode;
	private int theme;
	
	
	/** - - - - - - WORDLE GAME - - - - - - - - - - - - - - - - - - - - - - - - 
	 * WordleGame Constructor
	 * @param mode
	 * @throws IOException
	 * @author Katelen Tellez & Ryan Rizzo
	 */
	public WordleGame(String mode) throws IOException {
		
		// generate random word from dictionary (no repeats)
		dic = new WordleDictionary();
		guess = "";
		guessCorrect = false;
		gameOver = false;
		this.theme = Wordle.player.getTheme();
		this.mode = mode;
		this.guessIsCorrect = false;
		this.guessNum = 0;
		
		if (mode.equals("SURVIVAL")) {
			word = dic.getRandomWord();
		}
		if (mode.equals("WOTD")) {
			word = dic.getWOTD();
		}
		if (mode.equals("DORDLE")) {
			word = dic.getRandomWord();
			secondWord = dic.getRandomWord();
		}
		word = "PARTY";
		gameUI = new WordleGameUI(this);
		
		// prevents double chars from being found when there are none
		for(int i = 0; i < 5; i++)
			charFoundStatus[i] = false;
		
		//  0 indicates character is in the word and in the right index
		//  1 indicates character is in the word but not in the right index
		// -1 indicates character is not in the word
		guessResults = new int [5];
		charStatus = new HashMap<String, Integer>();
		String[] qwerty = {"q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h",
				"j","k","l","z","x","c","v","b","n","m"};
		for (int i=0; i<qwerty.length; i++) {
			charStatus.put(qwerty[i], -2);
		}
	}
	
	
	/** - - - - - - MAKE A GUESS - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Handles game function when user submits a word
	 * @param input
	 * @return int[] guessResult
	 * @author Katelen Tellez
	 */
	public int[] makeAGuess(char[][] input) {
		
		// convert input chars into a string
		for (int i=0; i<5; i++) {
			
			// input at row guessNum so we know which is the current row to look at
			guess = guess + Character.toString(input[i][guessNum]).toUpperCase();
		}
		
		System.out.println("Guess: " + guess);
		
		// did the user guess the word correctly?
		if(guess.equals(word)) {
			guessCorrect = true;
			for(int i = 0; i < 5; i++) {
				guessResults[i] = 0;
				charFoundStatus[i] = true;
			}
		}
		
		// if they did not, compare each character of the guess to the word
		else {
		
			for(int guessIdx = 0; guessIdx < 5; guessIdx++) {
				
				char guessChar = guess.charAt(guessIdx);
				
				for(int wordIdx = 0; wordIdx < 5; wordIdx++) {
					
					char wordChar = word.charAt(wordIdx);
    				
					if((wordChar == guessChar) && (wordIdx == guessIdx)) {
						
						if(charFoundStatus[wordIdx] == false) {
							guessResults[guessIdx] = 0;
							charFoundStatus[wordIdx] = true;
							break;
						}
					}
					else if(wordChar == guessChar) {
						
						if(charFoundStatus[wordIdx] == false) {
							guessResults[guessIdx] = 1;
							charFoundStatus[wordIdx] = true;
							break;
						}
					}
					else if(charFoundStatus[wordIdx] == false){
						guessResults[guessIdx] = -1;
					}
							
				}
				
			}
			
		}
		guessNum++;
		
		// reset the found status of each character
		for(int i = 0; i < 5; i++)
			charFoundStatus[i] = false;
		
		// reset the guess
		guess = "";
		
		// return the results of the guess
		return guessResults;
	}
	
	/** - - - - - - GAME IS OVER - - - - - - - - - - - - - - - - - - - - - - -
	 * Returns true if the game is over
	 * @return boolean guessIsCorrect
	 * @author Katelen Tellez
	 */
	public boolean gameIsOver() {
		
		// update player stats
		if(guessNum == 6 || guessIsCorrect) {
			Wordle.player.addGame(guessIsCorrect, guessNum);
		}
		
		if (guessNum == 6) {
			return true;
		}
		return guessIsCorrect;
	}
	
	public boolean guessIsCorrect() {
		return guessCorrect;
	}
	
	/** - - - - - - CHECK CHAR - - - - - - - - - - - - - - - - - - - - - - - -
	 * Checks the validity of a single character for the first word
	 * @param char c
	 * @param int index
	 * @return int result
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
		return result;
	}
	
	/** - - - - - - CHECK SECOND CHAR - - - - - - - - - - - - - - - - - - - - - 
	 * Checks the validity of a single character for the second word
	 * @param char c
	 * @param int index
	 * @return int result
	 * @author Ryan Rizzo
	 */
	public int checkSecondChar(char c, int index) {
		c = Character.toUpperCase(c);
		
		int result = -1;
		for (int i=0; i<secondWord.length(); i++) {
			if (secondWord.charAt(i) == c) {
				result++;
				break;
			}
		} if (secondWord.charAt(index) == c) {
			result++;
		}
		
		// Update charStatus
		if (result > charStatus.get(String.valueOf(c).toLowerCase())) {
			charStatus.put(String.valueOf(c).toLowerCase(), result);
		}
		return result;
	}
	
	/** - - - - - - GET CHAR STATUS - - - - - - - - - - - - - - - - - - - - - -
	 * Returns the status of each character in the word
	 * @return HashMap<String, Integer> charStatus
	 * @author Ryan Rizzo
	 */
	public HashMap<String, Integer> getCharStatus() {
		return this.charStatus;
	}
	
	/** - - - - - - GET THEME - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Returns the current theme being used in the game
	 * @return int theme
	 * @author Ryan Rizzo
	 */
	public int getTheme() {
		return this.theme;
	}
	
	/** - - - - - - GET MODE - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Returns the current mode being used in the game
	 * @return String mode
	 * @author Ryan Rizzo
	 */
	public String getMode() {
		return this.mode;
	}
	
	/** - - - - - - SET OVER - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Sets the state of the game to over
	 * @author Ryan Rizzo
	 */
	public void setOver() {
		this.gameOver = true;
	}
	
	/** - - - - - - GET WORD - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Returns the current word being solved in the game
	 * @return String word
	 * @author Ryan Rizzo
	 */
	public String getWord() {
		return word;
	}
	
	/** - - - - - - GET SECOND WORD - - - - - - - - - - - - - - - - - - - - - -
	 * Returns the current word being solved in the game
	 * @return String word
	 * @author Ryan Rizzo
	 */
	public String getSecondWord() {
		return secondWord;
	}
	
	/** - - - - - - SET GAME UI - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Sets the WordleGameUI object which will display this game
	 * @param WordleGameUI g
	 * @author Ryan Rizzo
	 */
	public void setGameUI(WordleGameUI g) {
        gameUI = g;
    	}
	
	/** - - - - - - GET GAME UI - - - - - - - - - - - - - - - - - - - - - - - - -
	 * REturns the WordleGameUI object which is displaying this game
	 * @param WordleGameUI g
	 * @author Ryan Rizzo
	 */
    	public static WordleGameUI getGameUI() {
        	return gameUI;
    	}
    	
	/** - - - - - - SET GUESS CORRECT - - - - - - - - - - - - - - - - - - - - - -
	 * Sets the state of the guess to correct or true
	 * @author Katelen Tellex
	 */
    	public void setGuessCorrect() {
    		this.guessIsCorrect = true;
    	}
    	
	/** - - - - - - ADD GUESS - - - - - - - - - - - - - - - - - - - - - - - - -
   	 * Increases the guess counter
   	 * @author Katelen Tellex
   	 */
    	public void addGuess() {
    		this.guessNum++;
    	}
    	
	/** - - - - - - GET ONE FOUND - - - - - - - - - - - - - - - - - - - - - - - -
   	 * Returns the state for one of two words being found
   	 * @return boolean oneFound
   	 * @author Ryan Rizzo
   	 */
    	public boolean getOneFound() {
    		return oneFound;
    	}
    	
	/** - - - - - - SET ONE FOUND - - - - - - - - - - - - - - - - - - - - - - - -
    	 * Sets the state for one of two words being found
   	 * @author Ryan Rizzo
     	 */
    	public void setOneFound() {
    		oneFound = true;
    	}
	
	/** - - - - - - GUESS IS CORRECT - - - - - - - - - - - - - - - - - - - - - - - -
     	* Returns the state of the guess
     	* @return Katelen Tellez
     	*/
    	public boolean guessIsCorrect() {
		return guessCorrect;
	}
}
