import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

class PlayerTest {

	@Test
	void createPlayer() {
		Player test = new Player("TESTNAME");
		assertTrue(test.getName().compareTo("TESTNAME") == 0);
	}
	
	@Test
	void testWin() {
		Player test = new Player("TESTNAME");
		test.addGame(true, 1);
		
		assertTrue(test.getGamesPlayed() == 1);
		assertTrue(test.getCurrentStreak() == 1);
		assertTrue(test.getMaxStreak() == 1);
		assertEquals(100.0, test.getWinPercentage());
		
	}
	
	@Test
	void testLose() {
		Player test = new Player("TESTNAME");
		test.addGame(false, 6);
		
		assertTrue(test.getGamesPlayed() == 1);
		assertTrue(test.getCurrentStreak() == 0);
		assertTrue(test.getMaxStreak() == 0);
		assertEquals(0.0, test.getWinPercentage());
		
	}
	
	@Test
	void testWinLose() {
		Player test = new Player("TESTNAME");
		test.addGame(true, 1);
		test.addGame(false, 6);
		
		assertTrue(test.getGamesPlayed() == 2);
		assertTrue(test.getCurrentStreak() == 0);
		assertTrue(test.getMaxStreak() == 1);
		assertEquals(50.0, test.getWinPercentage());
		
	}
	
	@Test
	void sortList() {
		ArrayList<Player> test = new ArrayList<>();
		
		Player test1 = new Player("test1");
		Player test2 = new Player("test2");
		Player test3 = new Player("test3");
		Player test4 = new Player("test4");
		Player test5 = new Player("test5");
		
		for (int i = 0; i < 5; i++) {
			test1.addGame(true, i);
		}
		
		for (int i = 0; i < 4; i++) {
			test2.addGame(true, i);
		}
		
		for (int i = 0; i < 3; i++) {
			test3.addGame(true, i);
		}
		
		for (int i = 0; i < 2; i++) {
			test4.addGame(true, i);
		}
		
		for (int i = 0; i < 1; i++) {
			test5.addGame(true, i);
		}
		
		test.add(test4);
		test.add(test1);
		test.add(test5);
		test.add(test3);
		test.add(test2);
		
		
		
		Collections.sort(test);
		
		assertEquals(test5, test.get(0));
		assertEquals(test4, test.get(1));
		assertEquals(test3, test.get(2));
		assertEquals(test2, test.get(3));
		assertEquals(test1, test.get(4));

	}

}
