
/*
 * This class displays the player statistics, can start a new game, 
 * can return to the main menu, and can utilize the share function.
 * 
 * @author Katelen Tellez
 */
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;

public class WordleEndUI {

	protected Shell shell;
	private WordleGameUI gameUI;
	private Canvas canvas;

	public void start() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();

		gameUI = WordleGame.getGameUI();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/*
	 * This method creates the displayed contents of the statistics page.
	 */
	protected void createContents() {

		shell = new Shell();
		shell.setSize(500, 500);
		shell.setText("GAME OVER");
		shell.setLayout(new GridLayout(2, false));

		canvas = new Canvas(shell, SWT.NONE);
		canvas.setSize(500, 500);

		displayStats();
		displayGuessDistr();
		createButtons();

	}

	/*
	 * This method displays the statistics of the current player.
	 */
	private void displayStats() {
		Font font = new Font(shell.getDisplay(), new FontData("Arial", 15, SWT.BOLD));

		// statistics label
		Label statsLabel = new Label(canvas, SWT.FILL);
		statsLabel.setText("STATISTICS");
		statsLabel.setBounds(200, 20, 500, 50);
		statsLabel.setFont(font);

		font = new Font(shell.getDisplay(), new FontData("Arial", 30, SWT.BOLD));

		// - - - - - - - - - - - - - player statistic numbers - - - - - - - - - - -
		// player played stat
		Label gamesPlayed = new Label(canvas, SWT.NONE);
		gamesPlayed.setText("" + Wordle.player.getGamesPlayed());
		Wordle.player.printStats();
		System.out.println("GAMES PLAYED: " + Wordle.player.getGamesPlayed());
		gamesPlayed.setBounds(120, 50, 50, 50);
		gamesPlayed.setFont(font);

		// player win %
		Label winPercentage = new Label(canvas, SWT.NONE);
		winPercentage.setText("" + Wordle.player.getWinPercentage());
		winPercentage.setBounds(170, 50, 125, 50);
		winPercentage.setFont(font);

		// player current streak
		Label curStreak = new Label(canvas, SWT.NONE);
		curStreak.setText("" + Wordle.player.getCurrentStreak());
		curStreak.setBounds(270, 50, 50, 50);
		curStreak.setFont(font);

		// player max streak
		Label maxStreak = new Label(canvas, SWT.NONE);
		maxStreak.setText("" + Wordle.player.getGamesPlayed());
		maxStreak.setBounds(345, 50, 50, 50);
		maxStreak.setFont(font);

		// - - - - - - - - - - - - - player statistic labels - - - - - - - - - - -
		font = new Font(shell.getDisplay(), new FontData("Arial", 10, SWT.NONE));

		// played label
		Label playedLabel = new Label(canvas, SWT.NONE);
		playedLabel.setText("Played");
		playedLabel.setBounds(115, 80, 50, 50);
		playedLabel.setFont(font);

		// win percentage label
		Label winPercLabel = new Label(canvas, SWT.NONE);
		winPercLabel.setText("Win %");
		winPercLabel.setBounds(200, 80, 50, 50);
		winPercLabel.setFont(font);

		// curStreak label
		Label curStreakLabel = new Label(canvas, SWT.NONE);
		curStreakLabel.setText("Current \n Streak");
		curStreakLabel.setBounds(265, 80, 50, 50);
		curStreakLabel.setFont(font);

		// maxStreak label
		Label maxStreakLabel = new Label(canvas, SWT.NONE);
		maxStreakLabel.setText("   Max \n Streak");
		maxStreakLabel.setBounds(330, 80, 50, 50);
		maxStreakLabel.setFont(font);
	}

	// - - - - - - - - - - guess distribution labels - - - - - - - - - - - - - -
	/*
	 * This method creates the labels for the guess distribution.
	 */
	private void displayGuessDistr() {

		Font font = new Font(shell.getDisplay(), new FontData("Arial", 15, SWT.BOLD));

		// statistics label
		Label guessDistrLabel = new Label(canvas, SWT.FILL);
		guessDistrLabel.setText("GUESS DISTRIBUTION");
		guessDistrLabel.setBounds(160, 120, 500, 50);
		guessDistrLabel.setFont(font);

		// guess 1 label
		Label guess1 = new Label(canvas, SWT.FILL);
		guess1.setText("1");
		guess1.setBounds(50, 150, 50, 50);
		guess1.setFont(font);

		// guess 2 label
		Label guess2 = new Label(canvas, SWT.FILL);
		guess2.setText("2");
		guess2.setBounds(50, 180, 50, 50);
		guess2.setFont(font);

		// guess 3 label
		Label guess3 = new Label(canvas, SWT.FILL);
		guess3.setText("3");
		guess3.setBounds(50, 210, 50, 50);
		guess3.setFont(font);

		// guess 4 label
		Label guess4 = new Label(canvas, SWT.FILL);
		guess4.setText("4");
		guess4.setBounds(50, 240, 50, 50);
		guess4.setFont(font);

		// guess 5 label
		Label guess5 = new Label(canvas, SWT.FILL);
		guess5.setText("5");
		guess5.setBounds(50, 270, 50, 50);
		guess5.setFont(font);

		// guess 6 label
		Label guess6 = new Label(canvas, SWT.FILL);
		guess6.setText("6");
		guess6.setBounds(50, 300, 50, 50);
		guess6.setFont(font);

		// - - - - - - - - guess distribution bars - - - - - - - - - - - - - - - - -
		drawGuessDistrBars();
	}

	/*
	 * This method creates and displays the bars for the guess distribution.
	 */
	private void drawGuessDistrBars() {

		Font font = new Font(shell.getDisplay(), new FontData("Arial", 15, SWT.BOLD | SWT.COLOR_WHITE));

		int x = 65;
		int y = 150;
		int width = 20;
		int height = 20;
		int guessNum = 1;

		for (int guess : Wordle.player.getGuessDistribution()) {

			for (int i = guess; i > 0; i--) {
				width += 20;
			}

			Label guessBar = new Label(canvas, SWT.RIGHT);
			guessBar.setBounds(x, y, width, height);
			guessBar.setText("" + guess);
			guessBar.setFont(font);
			guessBar.setForeground(canvas.getDisplay().getSystemColor(SWT.COLOR_WHITE));

			if (Wordle.player.wonLastGame() && Wordle.player.lastGameMoves() == guessNum)
				guessBar.setBackground(canvas.getDisplay().getSystemColor(SWT.COLOR_GREEN));
			else
				guessBar.setBackground(canvas.getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));

			y += 30;
			width = 20;
			guessNum++;
		}
	}

	// - - - - - - - main menu, new game, & share buttons - - - - - - - - - - - -
	/*
	 * This method creates the main menu, new game, and share buttons.
	 */
	private void createButtons() {

		// Main Menu button
		Button mainMenuButton = new Button(canvas, SWT.PUSH | SWT.CENTER);
		mainMenuButton.setText("Main Menu");
		mainMenuButton.setBounds(50, 400, 100, 50);
		mainMenuButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				gameUI.setVisible(false);
				WordleMenuUI menuUI = new WordleMenuUI(0);
				shell.dispose();
				WordleUI.startMenu(menuUI);
			}
		});

		// New Game Button
				Button newGameButton = new Button(canvas, SWT.PUSH | SWT.CENTER);
				newGameButton.setText("New Game");
				newGameButton.setBounds(175, 400, 100, 50);
				newGameButton.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						WordleGame newGame;
						try {
							newGame = new WordleGame("WOTD");
							WordleGameUI gameUI = new WordleGameUI(newGame);
							shell.dispose();
							WordleUI.startGame(gameUI);
						} catch (IOException e) {
							e.printStackTrace();
						}
						
					}
				});

		// Share Button
		Button shareButton = new Button(canvas, SWT.PUSH | SWT.CENTER);
		shareButton.setText("Share");
		shareButton.setBounds(300, 400, 100, 50);
		shareButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				gameUI.copyStats();
			}
		});
	}
}