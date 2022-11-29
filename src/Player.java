/**
 * Player object class. Stores the players name, game history, and preferences.
 * 
 * @author Gregory Jenkins
 */

import java.util.ArrayList;
import java.util.Random;

public class Player implements Comparable<Player> {
	private String name;
	private int gamesPlayed;
	private double winPercentage;
	private int currentStreak;
	private int maxStreak;
	private int theme;
	private ArrayList<Boolean> historyWins;
	private ArrayList<Integer> historyMoves;
	private Random rand;
	
	private int shirt;
	private int face;
	private int background;
	
	public Player(String name) {
		this.historyWins = new ArrayList<>();
		this.historyMoves = new ArrayList<>();
		this.name = name;
		this.winPercentage = 0.0;
		this.currentStreak = 0;
		this.maxStreak = 0;
		this.theme = 0;
		
		this.rand = new Random();
		this.setRandomPrefs();
		
	}
	
	private void setRandomPrefs() {
		this.shirt = rand.nextInt(16);
		this.face = rand.nextInt(16);
		this.background = rand.nextInt(16);
	}
	

	/**
	 * Adds a game to the player history
	 * 
	 * @param result
	 * @param moves
	 */
	public void addGame(boolean result, int moves) {
		this.historyMoves.add(moves);
		this.historyWins.add(result);
		this.gamesPlayed += 1;
		
		if (result) {
			increaseStreak();
		} else {
			resetStreak();
		}
		setWinPercentage();
	}
	
	public int[] getGuessDistribution() {
		int[] guessDistr = {0,0,0,0,0,0};
		
		for (int num : historyMoves) {
			guessDistr[num - 1]++;
		}
		return guessDistr;
	}
	
	
	/**
	 * Calculates the win percentage and updates winPercentage
	 */
	private void setWinPercentage() {
		int wins = 0;
		for (Boolean r : historyWins) {
			if (r) {
				wins++;
			}
		}
		if (wins == 0) {
			this.winPercentage = 0.0;
		} else {

			this.winPercentage = ((double)wins / (double)this.historyWins.size()) * 100.0;
		}
	}
	
	public void savePlayer() {
		
	}
	
	/**
	 * Sets the dark mode boolean
	 * @param flag
	 */
	public void setDarkModePreference(int theme) {
		this.theme = theme;
	}
	
	public void setFace(int face) {
		this.face = face;
	}
	
	public void setShirt(int shirt) {
		this.shirt = shirt;
	}
	
	public void setBackground(int background) {
		this.background = background;
	}
	
	public int getBackground() {
		return this.background;
	}
	
	public int getFace() {
		return this.face;
	}
	
	public int getShirt() {
		return this.shirt;
	}
	
	/**
	 * Resets the current streak to 0
	 */
	public void resetStreak() {
		this.currentStreak = 0;
	}
	
	/**
	 * Increases the current and max streak
	 */
	public void increaseStreak() {
		this.currentStreak += 1;
		if (currentStreak > maxStreak) {
			this.maxStreak = this.currentStreak;
		}
	}
	
	/**
	 * @return Name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return Dark Mode boolean
	 */
	public int getThemePreference() {
		return this.theme;
	}
	
	/**
	 * @return Number of Games Played
	 */
	public int getGamesPlayed() {
		return this.gamesPlayed;
	}
	
	/**
	 * @return Current Streak
	 */
	public int getCurrentStreak() {
		return this.currentStreak;
	}
	
	/**
	 * @return Max Streak number
	 */
	public int getMaxStreak() {
		return this.maxStreak;
	}
	
	/**
	 * @return Win Percentage
	 */
	public double getWinPercentage() {
		return this.winPercentage;
	}


	@Override
	public int compareTo(Player o) {
		if (this.getMaxStreak() > o.getMaxStreak()) {
			return 1;
		}
		if (this.getMaxStreak() < o.getMaxStreak()) {
			return -1;
		}
		//If max streak is a tie, compare win percentage 
		if (this.getMaxStreak() == o.getMaxStreak()) {
			if (this.getWinPercentage() > o.getWinPercentage()) {
				return 1;
			}
			if (this.getWinPercentage() < o.getWinPercentage()) {
				return -1;
			}
		}
		return 0;
	}
	

}
