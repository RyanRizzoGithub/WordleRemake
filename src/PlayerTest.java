import static org.junit.jupiter.api.Assertions.*;

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

}
