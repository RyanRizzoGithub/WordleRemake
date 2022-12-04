/**
 * Player object class. Stores the players name, game history, and preferences.
 * 
 * @author Gregory Jenkins
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class WordlePlayer implements Comparable<WordlePlayer>, Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String password;
	private int gamesPlayed;
	private double winPercentage;
	private int currentStreak;
	private int maxStreak;
	private int theme;
	private ArrayList<Boolean> historyWins;
	private ArrayList<Integer> historyMoves;
	private Random rand;
	private File file;
	
	private int shirt;
	private int face;
	private int background;
	
	public WordlePlayer(String name, String password, File file) {
		this.file = file;
		
		if (name.compareTo("Guest") == 0) {
			file.delete();
		}
		
		try {
			if ((name.compareTo("guest") != 0 || name.compareTo("Guest") != 0) && file.createNewFile()) {
				this.historyWins = new ArrayList<>();
				this.historyMoves = new ArrayList<>();
				this.name = name;
				this.winPercentage = 0.0;
				this.currentStreak = 0;
				this.maxStreak = 0;
				this.theme = 0;
				this.password = password;
				
				this.rand = new Random();
				this.setRandomPrefs();
				this.savePlayer();
			}
		} catch (IOException e) {
			System.out.println("TRYBLOCK ERROR");
			e.printStackTrace();
		}
		

		
	}
	
	private void setRandomPrefs() {
		this.shirt = rand.nextInt(16);
		this.face = rand.nextInt(16);
		this.background = rand.nextInt(16);
	}
	
	public boolean checkPassword(String password) {
		return this.password.equals(password);
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
		savePlayer();
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
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.file));
	        oos.writeObject(this);
	        oos.flush();
	        oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	/**
	 * Sets the dark mode boolean
	 * @param flag
	 */
	public void setTheme(int theme) {
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
	public int getTheme() {
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
	
	public void printStats() {
		System.out.println("NAME: " + name);
		System.out.println("GAMES PLAYED: " + gamesPlayed);
		System.out.println("WIN PERCENTAGE: " + winPercentage);
		System.out.println("CURRENT STREAK: " + currentStreak);
		System.out.println("MAX STREAK: " + maxStreak);
		System.out.println("GAME HISTORY: " + historyWins);
		System.out.println("MOVE HISTORY: " + historyMoves);
	}


	@Override
	public int compareTo(WordlePlayer o) {
		if (this.getMaxStreak() < o.getMaxStreak()) {
			return 1;
		}
		if (this.getMaxStreak() > o.getMaxStreak()) {
			return -1;
		}
		//If max streak is a tie, compare win percentage 
		if (this.getMaxStreak() == o.getMaxStreak()) {
			if (this.getWinPercentage() < o.getWinPercentage()) {
				return 1;
			}
			if (this.getWinPercentage() > o.getWinPercentage()) {
				return -1;
			}
		}
		return 0;
	}
	

}