import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

public class Dictionary {
	
	HashSet<String> answers;
	HashSet<String> guesses;
	String[] guessesArray;
	
	public Dictionary() {
		answers = new HashSet<String>();
		guesses = new HashSet<String>();
		
		loadWords("src/wordle-allowed-guesses.txt", guesses);
		loadWords("src/wordle-answers-alphabetical.txt", answers);
		loadWords("src/wordle-answers-alphabetical.txt", guesses);
		
		guessesArray = guesses.toArray(new String[guesses.size()]);
	}
	
	public String getRandomWord() {
		int size = guessesArray.length;
		Random rand = new Random();
		
		int index = rand.nextInt(size);
		
		return guessesArray[index];
	}
	
	private void loadWords(String filePath, HashSet<String> set) {
		
		File file = new File(filePath);
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       set.add(line);
		    }
		} catch (IOException e) {
			System.out.println("File read failure.");
			e.printStackTrace();
		}
	}
	
	public boolean isValidGuess(String guess) {
		return guesses.contains(guess);
	}

}


