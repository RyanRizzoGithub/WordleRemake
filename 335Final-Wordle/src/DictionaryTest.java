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
