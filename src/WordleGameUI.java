import java.util.LinkedList;
import java.util.Random;
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

public class WordleGameUI {
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
	private int [] guessResults = null;
	private int[][] allGuesses = new int [6][5];
	private int guessNum = 0;
		
	
	public WordleGameUI(WordleGame game) {
		display = Display.getDefault();
		shell = new Shell(display);
	    shell.setText("Wordle");
		shell.setSize(600,1000);
		shell.setLayout( new GridLayout());	
		shell.setBackground(new Color(0, 0, 0));
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
	}
	
	public void start() {
		Composite upperComp = new Composite(shell, SWT.NO_FOCUS);
		upperComp.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
	    //Composite lowerComp = new Composite(gameShell, SWT.NO_FOCUS);
		
		canvas = new Canvas(upperComp, SWT.NONE);
		canvas.setSize(600, 1000);
		canvas.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
	
		
		canvas.addPaintListener(e -> {
			// Set the color of the background
			e.gc.setBackground(display.getSystemColor(SWT.COLOR_DARK_GRAY));
			
			// Draw the title
			Font font = new Font(shell.getDisplay(), new FontData("Times New Roman", 40, SWT.BOLD));
			e.gc.setFont(font);
			e.gc.drawText("Wordle", 234, 5, true);
			
			drawInputRectangles(e);
			drawUserInput(e);
			drawKeyboard(e);
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
	        					
	        				}
	        				
	        				// Check if word is in guess dictionary
	        				else if (dic.guesses.contains(guess)) {
		        				rowSubmitted[row] = true;
		        				row++;
		        				col = 0;
		        				
		        				// @Katelen Tellez added
		        				// process input in the WORDLE class
		        				guessResults = game.makeAGuess(input);
		        				for(int i = 0; i < 5; i ++) {
		        					allGuesses[guessNum][i] = guessResults[i];
		        				}
		        				guessNum++;
		        				
		        				System.out.println("Guess Results: ");
		        				for(int g = 0; g < 5; g++) {
		        					System.out.println(guessResults[g]);
		        				}
		        				
		        				System.out.print("All Guesses: ");
		        				for(int g = 0; g < 6; g++) {
		        					
		        					for(int c = 0; c < 5; c++) {
		        						System.out.print(allGuesses[g][c] + " ");
		        					}
		        					System.out.println();
		        				}
		        				System.out.println();
	        				} else {
	        					System.out.println("Invalid word: " + guess);
	        					// TODO: shake animation and warning
	        				}
	        				
	        			}
	        			
	        		// If DELETE key
	        		} else if (e.keyCode == 8) {
	        			if (col > 0) {
	        				col--;
	        				input[col][row] = '?';
	        			}
	        			
	        		// If CHARACTER
	        		} else {
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
				if (e.y > 500 && e.y < 552 && e.x > 65 && e.x < 512) {
					index = ((e.x - 65) / 45);
				}
				
				// If A-L
				if (e.y > 560 && e.y < 612 && e.x > 88 && e.x < 582) {
					index = 10 + ((e.x - 88) / 45);
				}
				
				// If Z-M
				if (e.y > 622 && e.y < 670 && e.x > 134 && e.x < 444) {
					index = 19 + ((e.x - 134) / 45);
				}
				
				// If ENTER key
				if (e.y > 622 && e.y < 670 && e.x > 65 && e.x < 128) {
					if (col != 5) {
        				// TODO: Warning message
        				System.out.println("Not enough letters");
        			} else {
        				rowSubmitted[row] = true;
        				row++;
        				col = 0;
        			}
				}
				
				// If DELETE key
				if (e.y > 622 && e.y < 670 && e.x > 450 && e.x < 512) {
					if (col > 0) {
        				col--;
        				input[col][row] = '?';
        			}
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
		
		// Event loop
		shell.open();
		while( !shell.isDisposed())
			if(!display.readAndDispatch()) {		
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
		// Save color
		Color currColor = e.gc.getForeground();
		
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
		// Restore color
		e.gc.setForeground(currColor);
	}
	
	
	/* - - - - - - DRAW INPUT RECTANGLES - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
	 * This function is responsible displaying the rectangles in which each user-inputed character
	 * is housed
	 * 
	 * @param e, the paint event which calls this function
	 * (Ryan Rizzo)
	 */
	private void drawInputRectangles(PaintEvent e) {
		// Save color
		Color currColor = e.gc.getForeground();
		
		// Iterate over 5 columns and 6 rows
		for (int i=0; i<5; i++) {
			for (int j=0; j<6; j++) {
				Image empty = new Image(shell.getDisplay(), "./images/empty.png");
				e.gc.drawImage(empty, 120 + (70 * i), 60 + (70 * j));
			}
		}
		// Restore color
		e.gc.setForeground(currColor);
		
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
			Color charColor = display.getSystemColor(SWT.COLOR_DARK_GRAY);
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
			e.gc.setForeground(display.getSystemColor(SWT.COLOR_GRAY));
			e.gc.fillRoundRectangle((45 * x) + offset, 500 + (60 * y), 40, 50, 10, 10);
			e.gc.drawRoundRectangle((45 * x) + offset, 500 + (60 * y), 40, 50, 10, 10);
			
			e.gc.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
			e.gc.drawText(qwerty[i], 15 + (45 * x) + offset, 515 + (60 * y), true);
			x++;
		}
		// Setup font for Enter & Delete
		font = new Font(shell.getDisplay(), new FontData("Times New Roman", 14, SWT.BOLD));
		e.gc.setFont(font);
		
		// Draw the Enter key
		e.gc.setForeground(display.getSystemColor(SWT.COLOR_GRAY));
		e.gc.setBackground(display.getSystemColor(SWT.COLOR_DARK_GRAY));
		e.gc.fillRoundRectangle(64, 620, 64, 50, 10, 10);
		e.gc.drawRoundRectangle(64, 620, 64, 50, 10, 10);
		
		
		e.gc.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
		e.gc.drawText("ENTER", 72, 638, true);
		
		
		// Draw the Delete key
		e.gc.setForeground(display.getSystemColor(SWT.COLOR_GRAY));
		e.gc.setBackground(display.getSystemColor(SWT.COLOR_DARK_GRAY));
		e.gc.fillRoundRectangle(448, 620, 64, 50, 10, 10);
		e.gc.drawRoundRectangle(448, 620, 64, 50, 10, 10);
		
		e.gc.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
		e.gc.drawText("DELETE", 452, 638, true);
	}
	
	/* - - - - - - DRAW ANIMATION - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * This function is responsible displaying background falling letters animation
	 * 
	 * @param e, the paint event which calls this function
	 * (Ryan Rizzo)
	 */
	@SuppressWarnings("unused")
	private void drawAnimation(PaintEvent e) {
		new Thread(new Runnable() {
			public void run() {
				Random rand = new Random();
				String[] qwerty = {"q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h",
						"j","k","l","z","x","c","v","b","n","m","`"};
				LinkedList<String[]> memo = new LinkedList<String[]>();
				for (int i = 0; i < 100; i++) {
					String selectChar = qwerty[rand.nextInt(0, 27)];
					String selectX = String.valueOf(rand.nextInt(0, 600));
					String order = String.valueOf(i);
					String[] memoEntry = {selectChar, selectX, "0", order};
					memo.add(memoEntry);
				}
			}
		}).start();
	}
}