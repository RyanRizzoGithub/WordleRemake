import java.util.ArrayList;

/**
 * Keeps track of statistics throughout all games played.
 * @author katelentellez
 */

public class WordleStatistics {

	// stats variables
	private int[] stats = {0,0,0,0,0,0,0,0,0,0};
	private int played;
	private int winStat;
	private int curStreak;
	private int maxStreak;
	private int wins;
	private ArrayList<Boolean> winLossRecord;
	private int[] guessDistr = {0,0,0,0,0,0};

	public WordleStatistics() {
		
		played = 0;
		winStat = 0;
		curStreak = 0;
		maxStreak = 0;
		winLossRecord = new ArrayList<Boolean>();
		wins = 0;
	}
	
	public void addPlayed() {
		played+=1;
	}
	
	private void calcWins() {
		
		int w = 0;
		for(boolean game: winLossRecord) {
			if(game)
				w++;
		}
		wins = w;
	}
	
	public void calcWinStat() {
		calcWins();
		winStat = (wins/played) * 100;
	}
	
	public void calcCurStreak() {
		
		int streak = 0;
		
		// false = loss, true = win
		for(boolean game: winLossRecord) {
			if(game)
				streak++;
			else
				streak = 0;
		}
		
		curStreak = streak;
	}
	
	public void calcMaxStreak() {
		
		int max = 0;
		int cur = 0;
		
		for(boolean game : winLossRecord) {
			
			if(game)
				cur++;
			else
				cur = 0;
			
			if(cur > max)
				max = cur;
			
		}
		
		maxStreak = max;
	}
	
	public void addWinningGuess(int guessNum) {
		// guessNum is 1-6
		guessDistr[guessNum-1] += 1;
	}
	
	public void updateStats() {
		stats[0] = played;
		stats[1] = winStat;
		stats[2] = curStreak;
		stats[3] = maxStreak;
		stats[4] = guessDistr[0];
		stats[5] = guessDistr[1];
		stats[6] = guessDistr[2];
		stats[7] = guessDistr[3];
		stats[8] = guessDistr[4];
		stats[9] = guessDistr[5];
	}
	
	public void addGame(boolean result) {
		// 1 = win, 0 = loss
		winLossRecord.add(result);
	}

	public int[] getStats() {
		return stats;
	}
}