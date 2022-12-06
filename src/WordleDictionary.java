/**
 * Stores the answers and possible guesses for Wordle. Both are stored in a HashSet for fast guess checking.
 * An array is also used in order to get a random word for the answer. 
 * 
 * @author Gregory Jenkins
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

public class WordleDictionary {
	
	HashSet<String> answers;
	HashSet<String> guesses;
	String[] guessesArray;
	
	public WordleDictionary() {
		answers = new HashSet<String>();
		guesses = new HashSet<String>();
		
		loadWords("txt/wordle-allowed-guesses.txt", guesses);
		loadWords("txt/wordle-answers-alphabetical.txt", answers);
		loadWords("txt/wordle-answers-alphabetical.txt", guesses);
		
		guessesArray = guesses.toArray(new String[guesses.size()]);
	}
	
	/**
	 * Returns a random word to be used as the answer.
	 * @return String random word
	 */
	public String getRandomWord() {
		int size = guessesArray.length;
		Random rand = new Random();
		
		int index = rand.nextInt(size);
		
		return guessesArray[index];
	}
	
	/**
	 * Checks if a guess is valid
	 * @param guess being checked
	 * @return Boolean true if guess is valid
	 */
	public boolean isValidGuess(String guess) {
		return guesses.contains(guess);
	}
	
	/**
	 * Loads the words from .txt files into HashSets
	 * @param filePath File path of the files
	 * @param set Set being loaded into
	 */
	private void loadWords(String filePath, HashSet<String> set) {
		
		File file = new File(filePath);
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       set.add(line.toUpperCase());
		    }
		} catch (IOException e) {
			System.out.println("File read failure.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the word of the day.
	 * @return String word
	 * @throws IOException
	 */
	public String getWOTD() throws IOException {
		WordOfTheDay WOTD = new WordOfTheDay();
		return WOTD.getWOTD();
	}
	
	// Ryan Rizzo
	private class WordOfTheDay {
		private HashSet<String> wordsUsed;
		private String prevDate;
		private String wordOfTheDay;
		
		public WordOfTheDay() throws IOException {
			wordsUsed = new HashSet<String>();
			loadWords("txt/wordle-WOTD-used.txt", wordsUsed);
			
			FileReader dateFile = new FileReader("./txt/wordle-WOTD-date.txt");
			BufferedReader dateReader = new BufferedReader(dateFile);
			prevDate = dateReader.readLine();
			dateReader.close();
			
			if (!prevDate.equals(java.time.LocalDate.now().toString())) {
				String newWord = getRandomWord();
				while (wordsUsed.contains(newWord)) {
					newWord = getRandomWord();
				}
				FileWriter wordWriter = new FileWriter("./txt/wordle-WOTD-word.txt");
			    wordWriter.write(newWord);
			    wordWriter.close();
			    
			    FileWriter usedWriter = new FileWriter("./txt/wordle-WOTD-used.txt");
			    usedWriter.append(newWord);
			    usedWriter.close();
			    
			    FileWriter dateWriter = new FileWriter("./txt/wordle-WOTD-date.txt");
			    dateWriter.write(java.time.LocalDate.now().toString());
			    dateWriter.close();
			}
			FileReader wordFile = new FileReader("./txt/wordle-WOTD-word.txt");
			BufferedReader wordReader = new BufferedReader(wordFile);
			wordOfTheDay = wordReader.readLine();
			wordReader.close();
			
		}
		
		private String getWOTD() {
			return wordOfTheDay;
		}
		
		
	}

}
