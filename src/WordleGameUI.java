import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


/**
 * AUTHOR(S):		Qianwen Wang, Katelen Tellez & Ryan Rizzo
 * FILE:		WordleGameUI.java
 * CLASS:		CSC 335 - Final Project
 * DATE:		12/6/22
 * PURPOSE:		Responsible for displaying the game state
 */
public class WordleGameUI {
	// @Ryan Rizzo added
	private Display display;
	private Shell shell;
	private Canvas canvas;
	private int row;
	private int col;
	private char[][] input;
	private boolean[] rowSubmitted;
	private WordleDictionary dic;

	// @Katelen Tellez added
	private WordleGame game;
	private int[] guessResults = null;
	private int[][] allGuesses = new int[7][5];
	private int guessNum = 0;

	// @Qianwen Wang added
	private Animate animate = new Animate();
	private int x = 0;
	private int curX = 0;
	
	/** - - - - - - WORDLE GAME UI - - - - - - - - - - - - - - - - - - - - - - - - 
	 * WordleGameUI Constructor
	 * @param WordleGame game
	 * @throws IOException
	 * @author Ryan Rizzo
	 */
	public WordleGameUI(WordleGame game) {
		display = Display.getDefault();
		shell = new Shell(display);
		shell.setText("Wordle");
		shell.setLayout(new GridLayout());
		shell.setBounds(WordleUI.SHELL_X, WordleUI.SHELL_Y, WordleUI.SHELL_WIDTH, WordleUI.SHELL_HEIGHT);

		dic = new WordleDictionary();

		this.game = game;
		row = 0;
		col = 0;

		input = new char[5][7];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 7; j++) {
				input[i][j] = '?';
			}
		}
		rowSubmitted = new boolean[7];
		for (int i = 0; i < 6; i++) {
			rowSubmitted[i] = false;
		}
		shell.setBackground(WordleUI.getThemeColors(game.getTheme())[WordleUI.BACKGROUND_COLOR]);
	}
	
	/** - - - - - - START - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Generates the content and displays the game state
	 * @authors Qianwen Wang & Ryan Rizzo
	 */
	public void start() {
		Composite upperComp = new Composite(shell, SWT.NO_FOCUS);
		upperComp.setBackground(WordleUI.getThemeColors(game.getTheme())[WordleUI.BACKGROUND_COLOR]);
		// Composite lowerComp = new Composite(gameShell, SWT.NO_FOCUS);

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
		});
		
		// Add key listener to take input from keyboard
		canvas.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				String[] qwerty = { "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j",
						"k", "l", "z", "x", "c", "v", "b", "n", "m", "`" };
				// If ENTER key
				if (e.keyCode == 13) {
					if (col != 5) {
						// TODO: Warning message
						System.out.println("Not enough letters");
					} else {
						String guess = "";
						for (int i = 0; i < 5; i++) {
							guess = guess + Character.toString(input[i][guessNum]).toUpperCase();
						}
						if (guess.equals(game.getWord())) {
							// @Qianwen Wang added
							// add wave, mode 4
							animate.setWin(row);
							game.setOver();
							game.setGuessCorrect();
							
							rowSubmitted[row] = true;
							row++;
							guessNum++;
							game.addGuess();
							
						}

						// Check if word is in guess dictionary
						else if (dic.guesses.contains(guess)) {

							// @Qianwen Wang added
							// Add mode 3, fold animation
							for (int i = 0; i < 5; i++) {
								animate.updateValid(i, row);
							}
							curX = x;

							rowSubmitted[row] = true;
							row++;
							col = 0;

							// @Katelen Tellez added
							// process input in the WORDLE class
							guessResults = game.makeAGuess(input);
							for (int i = 0; i < 5; i++) {
								allGuesses[guessNum][i] = guessResults[i];
							}
							guessNum++;

							System.out.println("Guess Results: ");
							for (int g = 0; g < 5; g++) {
								System.out.println(guessResults[g]);
							}

							System.out.print("All Guesses: ");
							for (int g = 0; g < 6; g++) {

								for (int c = 0; c < 5; c++) {
									System.out.print(allGuesses[g][c] + " ");
								}
								System.out.println();
							}
							System.out.println();
						} else {
							System.out.println("Invalid word: " + guess);
							// TODO: shake animation and warning

							// @Qianwen Wang added
							// Add mode 2, shake
							for (int i = 0; i < 5; i++) {
								animate.updateInvalid(i, row);
							}
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
					WordleMenuUI menuUI = new WordleMenuUI(Wordle.player.getTheme());
					WordleUI.startMenu(menuUI);
				}

				// If CHARACTER
				else {
					if (col != 5 && row != 7) {
						for (int i = 0; i < qwerty.length; i++) {
							if (e.character == qwerty[i].charAt(0)) {
								input[col][row] = e.character;

								// @Qianwen Wang added
								animate.add(col, row);
								col++;

							}
						}
					}
				}
				canvas.redraw();
			}

			public void keyReleased(KeyEvent e) {
			}
		});
		
		// Add mouse listener to take input from on-screen keyboard
		canvas.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) {
				String[] qwerty = { "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j",
						"k", "l", "z", "x", "c", "v", "b", "n", "m", "`" };
				int index = -1;
				// If Q-P
				if (e.y > 500 && e.y < 552 && e.x > 65 && e.x < 512)
					index = ((e.x - 65) / 45);

				// If A-L
				if (e.y > 560 && e.y < 612 && e.x > 88 && e.x < 582)
					index = 10 + ((e.x - 88) / 45);

				// If Z-M
				if (e.y > 622 && e.y < 670 && e.x > 134 && e.x < 444)
					index = 19 + ((e.x - 134) / 45);

				// If ENTER key
				if (e.y > 622 && e.y < 670 && e.x > 65 && e.x < 128) {
					if (col != 5) {
						// TODO: Warning message
						System.out.println("Not enough letters");
					} else {
						String guess = "";
						for (int i = 0; i < 5; i++) {
							guess = guess + Character.toString(input[i][guessNum]).toUpperCase();
						}
						if (guess.equals(game.getWord())) {
							// @Qianwen Wang added
							// add wave, mode 4
							animate.setWin(row);
							game.setOver();
							game.setGuessCorrect();
							
							rowSubmitted[row] = true;
							row++;
							guessNum++;
							game.addGuess();
							
						}

						// Check if word is in guess dictionary
						else if (dic.guesses.contains(guess)) {

							// @Qianwen Wang added
							// Add mode 3, fold animation
							for (int i = 0; i < 5; i++) {
								animate.updateValid(i, row);
							}
							curX = x;

							rowSubmitted[row] = true;
							row++;
							col = 0;

							// @Katelen Tellez added
							// process input in the WORDLE class
							guessResults = game.makeAGuess(input);
							for (int i = 0; i < 5; i++) {
								allGuesses[guessNum][i] = guessResults[i];
							}
							guessNum++;

							System.out.println("Guess Results: ");
							for (int g = 0; g < 5; g++) {
								System.out.println(guessResults[g]);
							}

							System.out.print("All Guesses: ");
							for (int g = 0; g < 6; g++) {

								for (int c = 0; c < 5; c++) {
									System.out.print(allGuesses[g][c] + " ");
								}
								System.out.println();
							}
							System.out.println();
						} else {
							System.out.println("Invalid word: " + guess);
							// TODO: shake animation and warning

							// @Qianwen Wang added
							// Add mode 2, shake
							for (int i = 0; i < 5; i++) {
								animate.updateInvalid(i, row);
							}
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
					shell.dispose();
					WordleMenuUI menuUI = new WordleMenuUI(Wordle.player.getTheme());
					WordleUI.startMenu(menuUI);
					
				}

				// Handle character
				if (index >= 0 && index < 30) {
					if (col != 5 && row != 6) {
						input[col][row] = qwerty[index].toUpperCase().charAt(0);
						
						// @Qianwen Wang added
						animate.add(col, row);
						
						col++;
					}
				}
				canvas.redraw();
			}
			public void mouseDoubleClick(MouseEvent e) {}
			public void mouseUp(MouseEvent e) {}

		});

		// Event loop
		shell.open();
		// @Qianwen Wang added
		Runnable runnable = new Runnable() {
			public void run() {
				display.timerExec(10, this);
			}
		};
		display.timerExec(10, runnable);
		
		boolean flag = true;
		while (!shell.isDisposed())
			if (!display.readAndDispatch()) {
				display.sleep();
				x++;
				if(flag && game.gameIsOver()) {
					flag = false;
					WordleUI.startEnd();
				}
				
			}
			
		display.timerExec(-1, runnable);
		display.dispose();
	}

	/** - - - - - - DRAW USER INPUT - - - - - - - - - - - - - - - - - - - - - - - -
	 * Displays each character the user has typed
	 * @param PaintEvent e
	 * @authors Qianwen Wang & Ryan Rizzo
	 */
	private void drawUserInput(PaintEvent e) {
		// Set the correct font
		Font font = new Font(shell.getDisplay(), new FontData("Times New Roman", 40, SWT.BOLD));
		e.gc.setFont(font);

		if (game.getMode() == "DORDLE") {
			// Iterate over each cell in the input array
			for (int x = 0; x < 5; x++) {
				for (int j = 0; j < 7; j++) {
					// Check if cell is occupied
					if (input[x][j] != '?') {
						// Update the left side
						Image character = new Image(shell.getDisplay(),
								"./images/" + input[x][j] + "/" + input[x][j] + "Black.png");
						if (rowSubmitted[j] == true) {
							if (game.checkChar(input[x][j], x) == -1) {
								character = new Image(shell.getDisplay(),
										"./images/" + input[x][j] + "/" + input[x][j] + "Gray.png");
							}
							// @Qianwen Wang added
							if (animate.getCurRow() == j) {
								if (this.x - curX > 4) {

									if (game.checkChar(input[x][j], x) == 0) {
										character = new Image(shell.getDisplay(),
												"./images/" + input[x][j] + "/" + input[x][j] + "Yellow.png");
									}
									if (game.checkChar(input[x][j], x) == 1) {
										character = new Image(shell.getDisplay(),
												"./images/" + input[x][j] + "/" + input[x][j] + "Green.png");
									}
								}
							}
							// @Qianwen Wang added
							else {
								if (game.checkChar(input[x][j], x) == 0) {
									character = new Image(shell.getDisplay(),
											"./images/" + input[x][j] + "/" + input[x][j] + "Yellow.png");
								}
								if (game.checkChar(input[x][j], x) == 1) {
									character = new Image(shell.getDisplay(),
											"./images/" + input[x][j] + "/" + input[x][j] + "Green.png");
								}
							}
						}

						int width = character.getImageData().width;
						int height = character.getImageData().height;

						// @Qianwen Wang
						animate.setModel(height);
						animate.draw(x, j);
						e.gc.drawImage(character, 0, 0, width, width, 
								30 + (50 * x) + animate.get1(x, j),
								60 + (50 * j) + animate.get2(x, j), 
								(int) (width * 0.8) + animate.get3(x, j),
								(int) (height * 0.8) + animate.get4(x, j));

						// Update the right side
						if (rowSubmitted[j] == true) {
							if (game.checkSecondChar(input[x][j], x) == -1) {
								character = new Image(shell.getDisplay(),
										"./images/" + input[x][j] + "/" + input[x][j] + "Gray.png");
							}

							// @Qianwen Wang added
							if (animate.getCurRow() == j) {
								if (this.x - curX > 4) {

									if (game.checkSecondChar(input[x][j], x) == 0) {
										character = new Image(shell.getDisplay(),
												"./images/" + input[x][j] + "/" + input[x][j] + "Yellow.png");
									}
									if (game.checkSecondChar(input[x][j], x) == 1) {
										character = new Image(shell.getDisplay(),
												"./images/" + input[x][j] + "/" + input[x][j] + "Green.png");
									}
								}
							}
							// @Qianwen Wang added
							else {

								if (game.checkChar(input[x][j], x) == 0) {
									character = new Image(shell.getDisplay(),
											"./images/" + input[x][j] + "/" + input[x][j] + "Yellow.png");
								}
								if (game.checkChar(input[x][j], x) == 1) {
									character = new Image(shell.getDisplay(),
											"./images/" + input[x][j] + "/" + input[x][j] + "Green.png");
								}

							}

						}

						// @Qianwen Wang
						animate.draw(x, j);
						e.gc.drawImage(character, 0, 0, width, height, 315 + (50 * x) + animate.get1(x, j),
								60 + (50 * j) + animate.get2(x, j), (int) (width * 0.8) + animate.get3(x, j),
								(int) (height * 0.8) + animate.get4(x, j));

						// e.gc.drawImage(character, 0, 0, width, height, 315 + (50 * x), 60 + (50 * j),
						// (int) (width * 0.8), (int) (height * 0.8));
					}
				}
			}
		} else {
			// Iterate over each cell in the input array
			for (int x = 0; x < 5; x++) {
				for (int j = 0; j < 6; j++) {
					// Check if cell is occupied
					if (input[x][j] != '?') {

						// Update the square
						Image character = new Image(shell.getDisplay(),
								"./images/" + input[x][j] + "/" + input[x][j] + "Black.png");
						if (rowSubmitted[j] == true) {
							if (game.checkChar(input[x][j], x) == -1) {
								character = new Image(shell.getDisplay(),
										"./images/" + input[x][j] + "/" + input[x][j] + "Gray.png");
							}
							// @Qianwen Wang added
							if (animate.getCurRow() == j) {
								if(this.x - curX > 4 ) {
									if (game.checkChar(input[x][j], x) == 0) {
										character = new Image(shell.getDisplay(),
												"./images/" + input[x][j] + "/" + input[x][j] + "Yellow.png");
									}
									if (game.checkChar(input[x][j], x) == 1) {
										character = new Image(shell.getDisplay(),
												"./images/" + input[x][j] + "/" + input[x][j] + "Green.png");
									}
								}
							}
							else {
								if (game.checkChar(input[x][j], x) == 0) {
									character = new Image(shell.getDisplay(),
											"./images/" + input[x][j] + "/" + input[x][j] + "Yellow.png");
								}
								if (game.checkChar(input[x][j], x) == 1) {
									character = new Image(shell.getDisplay(),
											"./images/" + input[x][j] + "/" + input[x][j] + "Green.png");
								}	
							}
						}
						// @Qianwen Wang
						animate.draw(x, j);
						e.gc.drawImage(character, 0, 0, 60, 60, 
								120 + (70 * x) + animate.get1(x, j),
								60 + (70 * j) + animate.get2(x, j), 
								60 + animate.get3(x, j), 
								60 + animate.get4(x, j));
						//e.gc.drawImage(character, 120 + (70 * x), 60 + (70 * j));
					}
				}
			}
		}
	}

	/** - - - - - - DRAW INPUT RECTANGLES - - - - - - - - - - - - - - - - - - - - -
	 * Displays the empty rectangles before characters are added
	 * @param PaintEvent e
	 * @authors Qianwen Wang & Ryan Rizzo
	 */
	private void drawInputRectangles(PaintEvent e) {
		if (game.getMode() == "DORDLE") {
			// Iterate over 5 columns and 6 rows
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 7; j++) {
					Image empty = new Image(shell.getDisplay(), "./images/empty.png");
					int width = empty.getImageData().width;
					int height = empty.getImageData().height;
					e.gc.drawImage(empty, 0, 0, width, height, 30 + (50 * i), 60 + (50 * j), (int) (width * 0.8),
							(int) (height * 0.8));
				}
			}
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 7; j++) {
					Image empty = new Image(shell.getDisplay(), "./images/empty.png");
					int width = empty.getImageData().width;
					int height = empty.getImageData().height;
					e.gc.drawImage(empty, 0, 0, width, height, 315 + (50 * i), 60 + (50 * j), (int) (width * 0.8),
							(int) (height * 0.8));
				}
			}
		} else {
			// Iterate over 5 columns and 6 rows
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 6; j++) {
					Image empty = new Image(shell.getDisplay(), "./images/empty.png");
					e.gc.drawImage(empty, 120 + (70 * i), 60 + (70 * j));
				}
			}
		}
		canvas.redraw();
	}

	/*
	 * - - - - - - DRAW KEYBOARD - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * - - - - - - - - - This function is responsible displaying the keyboard on the
	 * interface
	 * 
	 * @param e, the paint event which calls this function (Ryan Rizzo)
	 */
	/** - - - - - - DRAW KEYBOARD - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Displays the on-screen keyboard
	 * @param PaintEvent e
	 * @author Ryan Rizzo
	 */
	private void drawKeyboard(PaintEvent e) {
		// Create a index variable
		int y = 0;
		int x = 0;
		int offset = 65;

		// Create array containing all characters in the order they appear on keyboard
		String[] qwerty = { "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k",
				"l", "z", "x", "c", "v", "b", "n", "m" };

		// Setup the font for each character
		Font font = new Font(shell.getDisplay(), new FontData("Times New Roman", 18, SWT.BOLD));
		e.gc.setFont(font);

		for (int i = 0; i < qwerty.length; i++) {
			if (qwerty[i].equals("a")) {
				y++;
				x = 0;
				offset = 88;
			}
			if (qwerty[i].equals("z")) {
				y++;
				x = 0;
				offset = 133;
			}
			// System.out.println(game.getCharStatus().get("p"));
			Color charColor = WordleUI.getThemeColors(game.getTheme())[WordleUI.KEY_FILL_COLOR];
			if (game.getCharStatus().get(qwerty[i]) == -1) {
				charColor = new Color(58, 58, 60);
			}
			if (game.getCharStatus().get(qwerty[i]) == 0) {
				charColor = new Color(181, 158, 68);
			}
			if (game.getCharStatus().get(qwerty[i]) == 1) {
				charColor = new Color(85, 140, 81);
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

	/** - - - - - - DRAW ANIMATION - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Displays the falling letters background animation
	 * @param PaintEvent e
	 * @author Ryan Rizzo
	 */
	private void drawAnimation(PaintEvent e) {
		int time = Math.abs(((int) System.currentTimeMillis() / 100));

		Image background = new Image(shell.getDisplay(), "./images/background.png");
		e.gc.drawImage(background, 0,
				WordleUI.SHELL_HEIGHT - (WordleUI.SHELL_HEIGHT + time) % (WordleUI.SHELL_HEIGHT * 2));
		e.gc.drawImage(background, 0,
				(WordleUI.SHELL_HEIGHT - 1500) - (WordleUI.SHELL_HEIGHT + time) % (WordleUI.SHELL_HEIGHT * 2));
	}
	
	/** - - - - - - SET VISIBLE - - - - - - - - - - - - - - - - - - - - - - - -
	 * Sets the visibility of the game ui
	 * @param boolean vis
	 * @author Ryan Rizzo
	 */
	public void setVisible(boolean vis) {
		shell.setVisible(vis);
	}
	private String generateShareString() {
		String output = "Results:\n";
		
		for (int j=0; j<7; j++) {
			for (int x=0; x<5; x++) {
				System.out.println("x: " + x + " j: " + j);
				// Check if cell is occupied
				if (input[x][j] != '?') {
					System.out.println("NOT ?");

					// Update the square
					if (rowSubmitted[j] == true) {		
						System.out.println("ROW SUBMITTED");
						//Incorrect letter
						if (game.checkChar(input[x][j], x) == -1 ) {
							output += "⬜";
							System.out.println("GRAY");
						}
						//Incorrect location
						if (game.checkChar(input[x][j], x) == 0 ) {
							output += "🟨";
							System.out.println("YELLOW");
						}
						//Correct letter and location
						if (game.checkChar(input[x][j], x) == 1 ) {
							output += "🟩";
							System.out.println("GREEN");
						}
					} 
				} else {
					System.out.println(input[x][j]);
				}
			}
			output += "\n";
		}
		
		return output;
	}
	
	public void copyStats() {
		String copyString = generateShareString();
		StringSelection stringSelectionObj = new StringSelection(copyString);
		Clipboard clipboardObj = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboardObj.setContents(stringSelectionObj, null);
	}
}

/*
 * - - - - - - INPUT ANIMATOR - - - - - - - - - - - - - - - - - - - - - - - - -
 * - - - - - - - - - - This function is change the movement of keyboard mode 1:
 * enter the input -- get bigger and smaller mode 2: not valid words -- shake
 * left and right mode 3: enter valid words -- fold in the middle and
 * replace with new one that bigger from middle mode 4: when win -- wave
 * 
 * @param e, the paint event which calls this function 
 * (Qianwen Wang)
 */

class Animate {
	// 1:mode 1, 2:mode 2, 3:mode 3, 4: mode 4,
	// for mode 1 --> 5: mode 1 start to move, 6: mode 1 move back, 7: mode 1 finish
	// move
	// for mode 2 --> 8: mode 2 start to move, 9: mode 2 move right, 10: mode 2 move
	// back
	// for mode 3 --> 12: mode 2 start to fold,
	private int[][] states;
	private int[][] initialStates;
	private int[][][] movement;
	private int curRow = 0;
	private int[] count = new int[5];
	private int size = 30;

	public Animate() {
		states = new int[5][7];
		movement = new int[5][7][4];
		initialStates = new int[5][7];
	}
	/**
	 * set model for duoble screen
	 * @param height
	 */
	public void setModel(int height) {
		size = (int) (60 * 0.8) /2;
	}
	/**
	 * set the correct row
	 * @param row
	 */
	public void setWin(int row) {
		states[0][row] = 4;
		states[1][row] = 4;
		states[2][row] = 4;
		states[3][row] = 4;
		states[4][row] = 4;
		initialStates[0][row] = 4;
		curRow = row;
		count[0] = 0;
		count[1] = 0;
		count[2] = 0;
		count[3] = 0;
		count[4] = 0;
	}
	/**
	 * get current row's index
	 * @return
	 */
	public int getCurRow() {
		return curRow;
	}
	/**
	 * add input state to list and set up their movement
	 * @param col
	 * @param row
	 */
	public void add(int col, int row) {
		states[col][row] = 1;
		initialStates[col][row] = 1;
		if (states[col][row] == 1) {
			movement[col][row][0] = 0;
			movement[col][row][1] = 0;
			movement[col][row][2] = 0;
			movement[col][row][3] = 0;
		}
	}
	/**
	 * update if the word is valid
	 * @param col
	 * @param row
	 */
	public void updateValid(int col, int row) {
		states[col][row] = 3;
		initialStates[col][row] = 3;
		curRow = row;
	}
	/**
	 * update if the word is invalid
	 * @param col
	 * @param row
	 */
	public void updateInvalid(int col, int row) {
		states[col][row] = 2;
		initialStates[col][row] = 2;
		curRow = row;

	}
	/**
	 * draw the animation of each kind of movement
	 * @param col
	 * @param row
	 */
	public void draw(int col, int row) {
		// mode 1: enter input letter--> bigger and smaller
		if (states[col][row] == 1) {
			states[col][row] = 5;
		}
		if (states[col][row] == 5) {
			if (movement[col][row][0] <= -6) {
				states[col][row] = 6;
			}
			movement[col][row][0] = movement[col][row][0] - 1;
			movement[col][row][1] = movement[col][row][1] - 1;
			movement[col][row][2] = movement[col][row][2] + 2;
			movement[col][row][3] = movement[col][row][3] + 2;
		}
		if (states[col][row] == 6) {
			if (movement[col][row][0] >= 0) {
				states[col][row] = 7;
			}
			movement[col][row][0] = movement[col][row][0] + 1;
			movement[col][row][1] = movement[col][row][1] + 1;
			movement[col][row][2] = movement[col][row][2] - 2;
			movement[col][row][3] = movement[col][row][3] - 2;
		}
		// mode 2: not valid words --> shake left and right
		if (states[col][row] == 2) {
			states[col][row] = 8;
		}
		// move to left
		if (states[col][row] == 8) {
			if (movement[0][curRow][0] <= -3) {
				states[col][row] = 9;
			}
			movement[0][curRow][0] = movement[0][curRow][0] - 1;
			movement[0][curRow][1] = 0;
			movement[0][curRow][2] = 0;
			movement[0][curRow][3] = 0;

			movement[1][curRow][0] = movement[0][curRow][0] - 1;
			movement[1][curRow][1] = 0;
			movement[1][curRow][2] = 0;
			movement[1][curRow][3] = 0;

			movement[2][curRow][0] = movement[0][curRow][0] - 1;
			movement[2][curRow][1] = 0;
			movement[2][curRow][2] = 0;
			movement[2][curRow][3] = 0;

			movement[3][curRow][0] = movement[0][curRow][0] - 1;
			movement[3][curRow][1] = 0;
			movement[3][curRow][2] = 0;
			movement[3][curRow][3] = 0;

			movement[4][curRow][0] = movement[0][curRow][0] - 1;
			movement[4][curRow][1] = 0;
			movement[4][curRow][2] = 0;
			movement[4][curRow][3] = 0;

		}
		// move to right
		else if (states[col][row] == 9) {
			if (movement[0][curRow][0] >= 0) {
				states[col][row] = 10;
			}
			movement[0][curRow][0] = movement[0][curRow][0] + 1;
			movement[0][curRow][1] = 0;
			movement[0][curRow][2] = 0;
			movement[0][curRow][3] = 0;

			movement[1][curRow][0] = movement[0][curRow][0] + 1;
			movement[1][curRow][1] = 0;
			movement[1][curRow][2] = 0;
			movement[1][curRow][3] = 0;

			movement[2][curRow][0] = movement[0][curRow][0] + 1;
			movement[2][curRow][1] = 0;
			movement[2][curRow][2] = 0;
			movement[2][curRow][3] = 0;

			movement[3][curRow][0] = movement[0][curRow][0] + 1;
			movement[3][curRow][1] = 0;
			movement[3][curRow][2] = 0;
			movement[3][curRow][3] = 0;

			movement[4][curRow][0] = movement[0][curRow][0] + 1;
			movement[4][curRow][1] = 0;
			movement[4][curRow][2] = 0;
			movement[4][curRow][3] = 0;
		}
		// move back
		else if (states[col][row] == 10) {
			movement[0][curRow][0] = movement[0][curRow][0] - 1;
			movement[0][curRow][1] = 0;
			movement[0][curRow][2] = 0;
			movement[0][curRow][3] = 0;

			movement[1][curRow][0] = movement[0][curRow][0] - 1;
			movement[1][curRow][1] = 0;
			movement[1][curRow][2] = 0;
			movement[1][curRow][3] = 0;

			movement[2][curRow][0] = movement[0][curRow][0] - 1;
			movement[2][curRow][1] = 0;
			movement[2][curRow][2] = 0;
			movement[2][curRow][3] = 0;

			movement[3][curRow][0] = movement[0][curRow][0] - 1;
			movement[3][curRow][1] = 0;
			movement[3][curRow][2] = 0;
			movement[3][curRow][3] = 0;

			movement[4][curRow][0] = movement[0][curRow][0] - 1;
			movement[4][curRow][1] = 0;
			movement[4][curRow][2] = 0;
			movement[4][curRow][3] = 0;
			if (movement[0][curRow][0] <= 5) {
				states[col][row] = 11;
				// setZero();
			}
		}

		// mode 3:valid words --> fold
		if (states[col][row] == 3) {
			states[0][row] = 12;
			states[1][row] = 12;
			states[2][row] = 12;
			states[3][row] = 12;
			states[4][row] = 12;
			setZero();
		}
		if (states[col][row] == 12) {
			if (movement[0][curRow][1] >= size) {
				states[0][row] = 13;
				states[1][row] = 13;
				states[2][row] = 13;
				states[3][row] = 13;
				states[4][row] = 13;
			}
			// fold
			movement[0][curRow][0] = 0;
			movement[0][curRow][1] = movement[0][curRow][1] + 3;
			movement[0][curRow][2] = 0;
			movement[0][curRow][3] = movement[0][curRow][3] - 6;

			movement[1][curRow][0] = 0;
			movement[1][curRow][1] = movement[1][curRow][1] + 3;
			movement[1][curRow][2] = 0;
			movement[1][curRow][3] = movement[1][curRow][3] - 6;

			movement[2][curRow][0] = 0;
			movement[2][curRow][1] = movement[2][curRow][1] + 3;
			movement[2][curRow][2] = 0;
			movement[2][curRow][3] = movement[2][curRow][3] - 6;

			movement[3][curRow][0] = 0;
			movement[3][curRow][1] = movement[3][curRow][1] + 3;
			movement[3][curRow][2] = 0;
			movement[3][curRow][3] = movement[3][curRow][3] - 6;

			movement[4][curRow][0] = 0;
			movement[4][curRow][1] = movement[4][curRow][1] + 3;
			movement[4][curRow][2] = 0;
			movement[4][curRow][3] = movement[4][curRow][3] - 6;
		}
		if (states[col][row] == 13) {
			if (movement[0][curRow][1] <= 1) {
				states[0][row] = 14;
				states[1][row] = 14;
				states[2][row] = 14;
				states[3][row] = 14;
				states[4][row] = 14;
			}
			// fold
			movement[0][curRow][0] = 0;
			movement[0][curRow][1] = movement[0][curRow][1] - 3;
			movement[0][curRow][2] = 0;
			movement[0][curRow][3] = movement[0][curRow][3] + 6;

			movement[1][curRow][0] = 0;
			movement[1][curRow][1] = movement[1][curRow][1] - 3;
			movement[1][curRow][2] = 0;
			movement[1][curRow][3] = movement[1][curRow][3] + 6;

			movement[2][curRow][0] = 0;
			movement[2][curRow][1] = movement[2][curRow][1] - 3;
			movement[2][curRow][2] = 0;
			movement[2][curRow][3] = movement[2][curRow][3] + 6;

			movement[3][curRow][0] = 0;
			movement[3][curRow][1] = movement[3][curRow][1] - 3;
			movement[3][curRow][2] = 0;
			movement[3][curRow][3] = movement[3][curRow][3] + 6;

			movement[4][curRow][0] = 0;
			movement[4][curRow][1] = movement[4][curRow][1] - 3;
			movement[4][curRow][2] = 0;
			movement[4][curRow][3] = movement[4][curRow][3] + 6;
		}
		// mode 4 wave
		if (states[col][row] == 4) {
			states[0][row] = 15;
			states[1][row] = 15;
			states[2][row] = 15;
			states[3][row] = 15;
			states[4][row] = 15;
			setZero();
		}
		if (states[col][row] == 15) {
			// 1
			if (count[0] == 0) {
				movement[0][curRow][1] = movement[0][curRow][1] - 1;
				if (movement[0][curRow][1] <= -10) {
					count[0] = 1;
				}
			} else if (count[0] == 1) {
				movement[0][curRow][1] = movement[0][curRow][1] + 1;
				if (movement[0][curRow][1] >= 0) {
					count[0] = 2;
				}
			}
			// 2
			if (count[0] == 2) {
				movement[1][curRow][1] = movement[1][curRow][1] + 1;
				if (movement[1][curRow][1] >= 10) {
					count[0] = 3;
				}
			} else if (count[0] == 3) {
				movement[1][curRow][1] = movement[1][curRow][1] - 1;
				if (movement[1][curRow][1] <= 0) {
					count[0] = 4;
				}
			}
			// 3
			if (count[0] == 4) {
				movement[2][curRow][1] = movement[2][curRow][1] + 1;
				if (movement[2][curRow][1] >= 10) {
					count[0] = 5;
				}
			} else if (count[0] == 5) {
				movement[2][curRow][1] = movement[2][curRow][1] - 1;
				if (movement[2][curRow][1] <= 0) {
					count[0] = 6;
				}
			}
			// 4
			if (count[0] == 6) {
				movement[3][curRow][1] = movement[3][curRow][1] + 1;
				if (movement[3][curRow][1] >= 10) {
					count[0] = 7;
				}
			} else if (count[0] == 7) {
				movement[3][curRow][1] = movement[3][curRow][1] - 1;
				if (movement[3][curRow][1] <= 0) {
					count[0] = 8;
				}
			}
			// 5
			if (count[0] == 8) {
				movement[4][curRow][1] = movement[4][curRow][1] + 1;
				if (movement[4][curRow][1] >= 10) {
					count[0] = 9;
				}
			} else if (count[0] == 9) {
				movement[4][curRow][1] = movement[4][curRow][1] - 1;
				if (movement[4][curRow][1] <= 0) {
					count[0] = 10;
				}
			}
		}

	}
	/**
	 * set their movement to initial
	 */
	private void setZero() {
		movement[0][curRow][0] = 0;
		movement[0][curRow][1] = 0;
		movement[0][curRow][2] = 0;
		movement[0][curRow][3] = 0;

		movement[1][curRow][0] = 0;
		movement[1][curRow][1] = 0;
		movement[1][curRow][2] = 0;
		movement[1][curRow][3] = 0;

		movement[2][curRow][0] = 0;
		movement[2][curRow][1] = 0;
		movement[2][curRow][2] = 0;
		movement[2][curRow][3] = 0;

		movement[3][curRow][0] = 0;
		movement[3][curRow][1] = 0;
		movement[3][curRow][2] = 0;
		movement[3][curRow][3] = 0;

		movement[4][curRow][0] = 0;
		movement[4][curRow][1] = 0;
		movement[4][curRow][2] = 0;
		movement[4][curRow][3] = 0;

	}
	/**
	 * get desX
	 * @param col
	 * @param row
	 * @return
	 */
	public int get1(int col, int row) {
		return movement[col][row][0];
	}
	/**
	 * get setY
	 * @param col
	 * @param row
	 * @return
	 */
	public int get2(int col, int row) {
		return movement[col][row][1];
	}
	/**
	 * get desWeight
	 * @param col
	 * @param row
	 * @return
	 */
	public int get3(int col, int row) {
		return movement[col][row][2];
	}
	/**
	 * get desHeight
	 * @param col
	 * @param row
	 * @return
	 */
	public int get4(int col, int row) {
		return movement[col][row][3];
	}
}
	
