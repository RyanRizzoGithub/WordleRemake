import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class WordleGameUI {
	private Display display;
	private Shell shell;
	private Canvas canvas;
	private int row;
	private int col;
	private char[][] input;
	private boolean[] rowSubmitted;
	private WordleDictionary dic;

	private WordleGame game;
	private int guessNum = 0;


	public WordleGameUI(WordleGame game) {
		display = Display.getDefault();
		shell = new Shell(display);
		shell.setText("Wordle");
		shell.setLayout( new GridLayout());	
		shell.setBounds(WordleUI.SHELL_X, WordleUI.SHELL_Y, WordleUI.SHELL_WIDTH, WordleUI.SHELL_HEIGHT);

		dic = new WordleDictionary();

		this.game = game;
		row = 0;
		col = 0;

		input = new char[5][6];
		for (int i=0; i<5; i++) {
			for (int j=0; j<6; j++) {
				input[i][j] = '?';
			}
		}
		rowSubmitted = new boolean[6];
		for (int i=0; i<6; i++) {
			rowSubmitted[i] = false;
		}
		shell.setBackground(WordleUI.getThemeColors(game.getTheme())[WordleUI.BACKGROUND_COLOR]);
	}

	// TODO:
	/*
	public WordleGameUI(WordleGame game, WordlePlayer player) {
		display = Display.getDefault();
		shell = new Shell(display);
	    shell.setText("Wordle");
		shell.setLayout( new GridLayout());	
		shell.setBounds(WordleUI.SHELL_X, WordleUI.SHELL_Y, WordleUI.SHELL_WIDTH, WordleUI.SHELL_HEIGHT);

		dic = new WordleDictionary();

		this.game = game;
		row = 0;
		col = 0;

		input = new char[5][6];
		for (int i=0; i<5; i++) {
			for (int j=0; j<6; j++) {
				input[i][j] = '?';
			}
		}
		rowSubmitted = new boolean[6];
		for (int i=0; i<6; i++) {
			rowSubmitted[i] = false;
		}
		shell.setBackground(WordleUI.getThemeColors(player.getTheme())[WordleUI.BACKGROUND_COLOR]);
	}
	 */

	public void start() {
		Composite upperComp = new Composite(shell, SWT.NO_FOCUS);
		upperComp.setBackground(WordleUI.getThemeColors(game.getTheme())[WordleUI.BACKGROUND_COLOR]);
		//Composite lowerComp = new Composite(gameShell, SWT.NO_FOCUS);

		canvas = new Canvas(upperComp, SWT.NONE);
		canvas.setSize(600, 1000);
		canvas.setBackground(WordleUI.getThemeColors(game.getTheme())[WordleUI.BACKGROUND_COLOR]);


		canvas.addPaintListener(e -> {
			drawAnimation(e);

			// Draw back button
			e.gc.setForeground(WordleUI.getThemeColors(game.getTheme())[WordleUI.KEY_EDGE_COLOR]);
			e.gc.setBackground(WordleUI.getThemeColors(game.getTheme())[WordleUI.KEY_FILL_COLOR]);

			e.gc.drawRoundRectangle(15, 15, 75, 25, 10, 10);
			e.gc.fillRoundRectangle(16, 16, 74, 24, 10, 10);

			e.gc.setForeground(WordleUI.getThemeColors(game.getTheme())[WordleUI.KEY_CHAR_COLOR]);
			e.gc.drawText("Back", 38, 20);


			/*
			if (game.gameIsOver()) {
				// Set the color of the background
				e.gc.setBackground(WordleUI.getThemeColors(game.getTheme())[WordleUI.BACKGROUND_COLOR]);
				e.gc.fillRectangle(0, 0, 600, 100);
				System.out.println("Game Over");

				Font font = new Font(shell.getDisplay(), new FontData("Times New Roman", 25, SWT.BOLD));
				e.gc.setFont(font);
				e.gc.drawText("STATISTICS", 250, 25);

				font = new Font(shell.getDisplay(), new FontData("Times New Roman", 15, SWT.BOLD));
				e.gc.setFont(font);
				e.gc.drawText("Played", 50, 150);
				e.gc.drawText("Win %", 150, 150);
				e.gc.drawText("Current Streak", 250, 150);
				e.gc.drawText("Max Streak", 350, 150);

				font = new Font(shell.getDisplay(), new FontData("Times New Roman", 25, SWT.BOLD));
				e.gc.setFont(font);
				e.gc.drawText("GUESS DISTRIBUTION", 150, 200);

				font = new Font(shell.getDisplay(), new FontData("Times New Roman", 15, SWT.BOLD));
				e.gc.setFont(font);
				e.gc.drawText("1", 75, 250);
				e.gc.drawText("2", 75, 300);
				e.gc.drawText("3", 75, 350);
				e.gc.drawText("4", 75, 400);
				e.gc.drawText("5", 75, 450);
				e.gc.drawText("6", 75, 500);

				int x = 100;
				int y = 250;
				int width = 15;
				int height = 15;
				for(int i = 0; i < 6; i++) {
					e.gc.drawRectangle(x, y, width, height);
					e.gc.fillRectangle(x, y, width, height);
					y+=50;
				}

				// New Game Button
				Button newGameButton = new Button(shell, SWT.RADIO);
				newGameButton.setText("NEW GAME");
				newGameButton.setLayoutData(new GridData(75, 550));
				newGameButton.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {

						WordleGame newGame = new WordleGame(0);
						WordleGameUI gameUI = new WordleGameUI(newGame);
						WordleUI.startGame(gameUI);
					}
				});

				// Main Menu Button
				Button mainMenuButton = new Button(shell, SWT.RADIO);
				mainMenuButton.setText("MAIN MENU");
				mainMenuButton.setLayoutData(new GridData(150, 550));
				mainMenuButton.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {

						WordleMenuUI menuUI = new WordleMenuUI();
						WordleUI.startMenu(menuUI);
					}
				});

			} else {
			 */
			// Set the color of the background
			canvas.setBackground(WordleUI.getThemeColors(game.getTheme())[WordleUI.BACKGROUND_COLOR]);
			shell.setBackground(WordleUI.getThemeColors(game.getTheme())[WordleUI.BACKGROUND_COLOR]);

			e.gc.setBackground(WordleUI.getThemeColors(game.getTheme())[WordleUI.BACKGROUND_COLOR]);

			// Draw the title
			e.gc.setForeground(WordleUI.getThemeColors(game.getTheme())[WordleUI.KEY_CHAR_COLOR]);
			Font font = new Font(shell.getDisplay(), new FontData("Times New Roman", 40, SWT.BOLD));
			e.gc.setFont(font);
			e.gc.drawText("Wordle", 234, 5, true);


			drawInputRectangles(e);
			drawUserInput(e);
			drawKeyboard(e);
			//}
		});

		canvas.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { 
				String[] qwerty = {"q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h",
						"j","k","l","z","x","c","v","b","n","m","`"};
				// If ENTER key
				if (e.keyCode == 13) {
					if (col != 5) {
						// TODO: Warning message
						System.out.println("Not enough letters");
					} 
					else {
						String guess = "";
						for (int i=0; i<5; i++) {
							guess = guess + Character.toString(input[i][guessNum]).toUpperCase();
						}
						if (guess.equals(game.word)) {
							game.setOver();
							game.setGuessCorrect();
						}

						// Check if word is in guess dictionary
						else if (dic.guesses.contains(guess)) {
							rowSubmitted[row] = true;
							row++;
							col = 0;
							guessNum++;
							game.addGuess();

						} else {
							System.out.println("Invalid word: " + guess);
							// TODO: shake animation and warning
						}

					}
				} 	
				// If DELETE key
				else if (e.keyCode == 8) {
					if (col > 0) {
						col--;
						input[col][row] = '?';
					}
				}	
				// If ESCAPE key	
				else if (e.keyCode == 27) {
					setVisible(false);
					// TODO: WordleMenuUI = new WordleMenuUI(WordlePlayer player);
					WordleMenuUI menuUI = new WordleMenuUI(0);
					WordleUI.startMenu(menuUI);
				}

				// If CHARACTER
				else {
					if (col != 5 && row != 6) {
						for (int i=0; i<qwerty.length; i++) {
							if (e.character == qwerty[i].charAt(0)) {
								input[col][row] = e.character;    			
								col++;
							}
						}
					}
				}
				canvas.redraw();
			}

			public void keyReleased(KeyEvent e) {}
		});

		canvas.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) {	
				String[] qwerty = {"q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h",
						"j","k","l","z","x","c","v","b","n","m","`"};
				int index = -1;
				// If Q-P
				if (e.y > 500 && e.y < 552 && e.x > 65 && e.x < 512) index = ((e.x - 65) / 45);

				// If A-L
				if (e.y > 560 && e.y < 612 && e.x > 88 && e.x < 582) index = 10 + ((e.x - 88) / 45);

				// If Z-M
				if (e.y > 622 && e.y < 670 && e.x > 134 && e.x < 444) index = 19 + ((e.x - 134) / 45);

				// If ENTER key
				if (e.y > 622 && e.y < 670 && e.x > 65 && e.x < 128) {
					if (col != 5) {
						// TODO: Warning message
						System.out.println("Not enough letters");
					} 
					else {
						String guess = "";
						for (int i=0; i<5; i++) {
							guess = guess + Character.toString(input[i][guessNum]).toUpperCase();
						}
						if (guess.equals(game.word)) {
							game.setOver();
							game.setGuessCorrect();
						}

						// Check if word is in guess dictionary
						else if (dic.guesses.contains(guess)) {
							rowSubmitted[row] = true;
							row++;
							col = 0;
							guessNum++;
							game.addGuess();

						} else {
							System.out.println("Invalid word: " + guess);
							// TODO: shake animation and warning
						}

					}
				}

				// If DELETE key
				if (e.y > 622 && e.y < 670 && e.x > 450 && e.x < 512) {
					if (col > 0) {
						col--;
						input[col][row] = '?';
					}
				}

				// If BACK button
				if (e.y > 15 && e.y < 40 && e.x > 15 && e.x < 90) {
					setVisible(false);
					// TODO: WordleMenuUI = new WordleMenuUI(WordlePlayer player);
					WordleMenuUI menuUI = new WordleMenuUI(0);
					WordleUI.startMenu(menuUI);
				}

				// Handle character
				if (index >= 0 && index < 30) {
					if (col != 5 && row != 6) {
						input[col][row] = qwerty[index].toUpperCase().charAt(0);    			
						col++;
					}
				}
				canvas.redraw();
			}
			public void mouseDoubleClick(MouseEvent e) {}
			public void mouseUp(MouseEvent e) {}

		});

		boolean flag = true;

		// Event loop
		shell.open();
		while( !shell.isDisposed())
			if(!display.readAndDispatch()) {
				if(flag && game.gameIsOver()) {
					flag = false;
					WordleUI.endGame();
					String copyString = generateShareString();
					StringSelection stringSelectionObj = new StringSelection(copyString);
					Clipboard clipboardObj = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboardObj.setContents(stringSelectionObj, null);
				}
			}
		display.dispose();
	}


	/* - - - - - - DRAW USER INPUT - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * This function is responsible displaying each word that the user has guessed on the
	 * interface
	 * 
	 * @param e, the paint event which calls this function
	 * (Ryan Rizzo)
	 */
	private void drawUserInput(PaintEvent e) {
		// Set the correct font
		Font font = new Font(shell.getDisplay(), new FontData("Times New Roman", 40, SWT.BOLD));
		e.gc.setFont(font);		


		// Iterate over each cell in the input array
		for (int x=0; x<5; x++) {
			for (int j=0; j<6; j++) {
				// Check if cell is occupied
				if (input[x][j] != '?') {

					// Update the square
					Image character = new Image(shell.getDisplay(), "./images/" + input[x][j] + "/" + input[x][j] + "Black.png");
					if (rowSubmitted[j] == true) {		
						if (game.checkChar(input[x][j], x) == -1 ) {
							character = new Image(shell.getDisplay(), "./images/" + input[x][j] + "/" + input[x][j] + "Gray.png");
						}
						if (game.checkChar(input[x][j], x) == 0 ) {
							character = new Image(shell.getDisplay(), "./images/" + input[x][j] + "/" + input[x][j] + "Yellow.png");
						}
						if (game.checkChar(input[x][j], x) == 1 ) {
							character = new Image(shell.getDisplay(), "./images/" + input[x][j] + "/" + input[x][j] + "Green.png");
						}
					} 
					e.gc.drawImage(character, 120 + (70 * x), 60 + (70 * j));
				}
			}
		}
	}


	/* - - - - - - DRAW INPUT RECTANGLES - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
	 * This function is responsible displaying the rectangles in which each user-inputed character
	 * is housed
	 * 
	 * @param e, the paint event which calls this function
	 * (Ryan Rizzo)
	 */
	private void drawInputRectangles(PaintEvent e) {
		// Iterate over 5 columns and 6 rows
		for (int i=0; i<5; i++) {
			for (int j=0; j<6; j++) {
				Image empty = new Image(shell.getDisplay(), "./images/empty.png");
				e.gc.drawImage(empty, 120 + (70 * i), 60 + (70 * j));
			}
		}
		canvas.redraw();
	}

	/* - - - - - - DRAW KEYBOARD - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * This function is responsible displaying the keyboard on the interface
	 * 
	 * @param e, the paint event which calls this function
	 * (Ryan Rizzo)
	 */
	private void drawKeyboard(PaintEvent e) {		
		// Create a index variable
		int y=0;
		int x=0;
		int offset=65;

		// Create array containing all characters in the order they appear on keyboard
		String[] qwerty = {"q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h",
				"j","k","l","z","x","c","v","b","n","m"};

		// Setup the font for each character
		Font font = new Font(shell.getDisplay(), new FontData("Times New Roman", 18, SWT.BOLD));
		e.gc.setFont(font);

		for (int i=0; i<qwerty.length; i++) {
			if (qwerty[i].equals("a")) {
				y++;
				x=0;
				offset = 88;
			}
			if (qwerty[i].equals("z")) {
				y++;
				x=0;
				offset = 133;
			}
			//System.out.println(game.getCharStatus().get("p"));
			Color charColor = WordleUI.getThemeColors(game.getTheme())[WordleUI.KEY_FILL_COLOR];
			if (game.getCharStatus().get(qwerty[i]) == -1) {
				charColor = new Color(58,58,60);
			}
			if (game.getCharStatus().get(qwerty[i]) == 0) {
				charColor = new Color(181,158,68);
			}
			if (game.getCharStatus().get(qwerty[i]) == 1) {
				charColor = new Color(85,140,81);
			}

			e.gc.setBackground(charColor);
			e.gc.setForeground(WordleUI.getThemeColors(game.getTheme())[WordleUI.KEY_EDGE_COLOR]);
			e.gc.fillRoundRectangle((45 * x) + offset, 500 + (60 * y), 40, 50, 10, 10);
			e.gc.drawRoundRectangle((45 * x) + offset, 500 + (60 * y), 40, 50, 10, 10);

			e.gc.setForeground(WordleUI.getThemeColors(game.getTheme())[WordleUI.KEY_CHAR_COLOR]);
			e.gc.drawText(qwerty[i], 15 + (45 * x) + offset, 515 + (60 * y), true);
			x++;
		}
		// Setup font for Enter & Delete
		font = new Font(shell.getDisplay(), new FontData("Times New Roman", 14, SWT.BOLD));
		e.gc.setFont(font);

		// Draw the Enter key
		e.gc.setForeground(WordleUI.getThemeColors(game.getTheme())[WordleUI.KEY_EDGE_COLOR]);
		e.gc.setBackground(WordleUI.getThemeColors(game.getTheme())[WordleUI.KEY_FILL_COLOR]);
		e.gc.fillRoundRectangle(64, 620, 64, 50, 10, 10);
		e.gc.drawRoundRectangle(64, 620, 64, 50, 10, 10);

		e.gc.setForeground(WordleUI.getThemeColors(game.getTheme())[WordleUI.KEY_CHAR_COLOR]);
		e.gc.drawText("ENTER", 72, 638, true);


		// Draw the Delete key
		e.gc.setForeground(WordleUI.getThemeColors(game.getTheme())[WordleUI.KEY_EDGE_COLOR]);
		e.gc.setBackground(WordleUI.getThemeColors(game.getTheme())[WordleUI.KEY_FILL_COLOR]);
		e.gc.fillRoundRectangle(448, 620, 64, 50, 10, 10);
		e.gc.drawRoundRectangle(448, 620, 64, 50, 10, 10);

		e.gc.setForeground(WordleUI.getThemeColors(game.getTheme())[WordleUI.KEY_CHAR_COLOR]);
		e.gc.drawText("DELETE", 452, 638, true);
	}

	/* - - - - - - DRAW ANIMATION - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * This function is responsible displaying background falling letters animation
	 * 
	 * @param e, the paint event which calls this function
	 * (Ryan Rizzo)
	 */

	private void drawAnimation(PaintEvent e) {
		int time = Math.abs(((int) System.currentTimeMillis()/100));
		System.out.println(WordleUI.SHELL_HEIGHT - ((time - 400) % WordleUI.SHELL_HEIGHT));

		Image background = new Image(shell.getDisplay(), "./images/background.png");
		e.gc.drawImage(background, 0,WordleUI.SHELL_HEIGHT - (WordleUI.SHELL_HEIGHT + time) % (WordleUI.SHELL_HEIGHT * 2));
		e.gc.drawImage(background, 0,(WordleUI.SHELL_HEIGHT - 1500) - (WordleUI.SHELL_HEIGHT + time) % (WordleUI.SHELL_HEIGHT * 2));
	}

	public void setVisible(boolean vis) {
		shell.setVisible(vis);
	}
	
	private String generateShareString() {
		String output = "Results:\n";
		
		for (int j=0; j<6; j++) {
			for (int x=0; x<5; x++) {
				// Check if cell is occupied
				if (input[x][j] != '?') {

					// Update the square
					if (rowSubmitted[j] == true) {		
						//Incorrect letter
						if (game.checkChar(input[x][j], x) == -1 ) {
							output += "⬜";
						}
						//Incorrect location
						if (game.checkChar(input[x][j], x) == 0 ) {
							output += "🟨";
						}
						//Correct letter and location
						if (game.checkChar(input[x][j], x) == 1 ) {
							output += "🟩";
						}
					} 
				}
			}
			output += "\n";
		}
		
		return output;
	}
}