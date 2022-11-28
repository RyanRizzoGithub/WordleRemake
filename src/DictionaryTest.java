import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DictionaryTest {

	@Test
	void createDictionary() {
		Dictionary test = new Dictionary();
		
		assertNotNull(test);
	}
	
	@Test
	void validWord() {
		Dictionary test = new Dictionary();
		
		assertTrue(test.isValidGuess("apple"));
		assertTrue(test.isValidGuess("pears"));
		assertTrue(test.isValidGuess("telco"));
		assertTrue(test.isValidGuess("juror"));
		assertTrue(test.isValidGuess("waurs"));
		assertTrue(test.isValidGuess("yates"));
		assertTrue(test.isValidGuess("zowee"));
		assertTrue(test.isValidGuess("perch"));
		assertTrue(test.isValidGuess("guess"));
		assertTrue(test.isValidGuess("quest"));
	}
	
	@Test
	void randomWord() {
		Dictionary test = new Dictionary();
		
		for (int i = 0; i < 20; i++) {
			String word = test.getRandomWord();
			System.out.println(word);
			
		}
	}

}