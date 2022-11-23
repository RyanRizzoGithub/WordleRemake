import java.util.HashMap;

// import java.util.ArrayList;

// @Katelen Tellez added (below)
public class WordleGame {
	
	// private HashMap dictionary;
	private String word;
	// private ArrayList usedWords;
	private String guess;
	private int guessNum;
	private boolean guessCorrect;
	private int [] guessResults;
	private boolean [] charFoundStatus = new boolean [5];;
	
	// Ryan
	private HashMap<String,Integer> charStatus;
	
	public WordleGame() {
		
		// initialize dictionary?
		// generate random word from dictionary (no repeats)
		
		word = "PARTY";
		guess = "";
		guessNum = 0;
		guessCorrect = false;
		
		// prevents double chars from being found when there are none
		for(int i = 0; i < 5; i++)
			charFoundStatus[i] = false;
		
		// a 0 indicates character is in the word and in the right index
		// a 1 indicates character is in the word but not in the right index
		// a -1 indicates character is not in the word
		guessResults = new int [5];
		charStatus = new HashMap<String, Integer>();
		String[] qwerty = {"q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h",
				"j","k","l","z","x","c","v","b","n","m"};
		for (int i=0; i<qwerty.length; i++) {
			charStatus.put(qwerty[i], -2);
		}
	}
	
	/*
	private String getRandWord(HashMap dict) {
		// generate random word from dictionary
		// check if it's been used in this session
		// if not, add to usedWords
		// if it has, generate new word
		// return generated word
	}
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
	
	public boolean gameIsOver() {
		
		if(guessNum == 5) {
			return true;
		}
		return guessCorrect;
	}
	
	public boolean guessIsCorrect() {
		return guessCorrect;
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
		System.out.println("char: " + c + " index: " + index + " result: " + result);
		return result;
	}
	
	public HashMap<String, Integer> getCharStatus() {
		return this.charStatus;
	}
}