import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class WordleUI {
	private Display display;
	private Shell shell;
	private Canvas canvas;
	private int row;
	private int col;
	private char[][] input;
	
	
	public WordleUI () {
		Display.setAppName("Wordle");
		display = new Display();  
		shell = new Shell(display);
	    shell.setText("Wordle");
		shell.setSize(600,1000);
		shell.setLayout( new GridLayout());	
		input = new char[5][6];
		row = 0;
		col = 0;
		for (int i=0; i<5; i++) {
			for (int j=0; j<6; j++) {
				input[i][j] = '?';
			}
		}
	}
	
	public void start() {
		Composite upperComp = new Composite(shell, SWT.NO_FOCUS);
	    Composite lowerComp = new Composite(shell, SWT.NO_FOCUS);
		
		canvas = new Canvas(upperComp, SWT.NONE);
		canvas.setSize(600, 1000);
	
		
		canvas.addPaintListener(e -> {
			// Set the color of the background
			e.gc.setBackground(display.getSystemColor(SWT.COLOR_DARK_GRAY));
			
			// Draw the title
			Font font = new Font(shell.getDisplay(), new FontData("Times New Roman", 40, SWT.BOLD));
			e.gc.setFont(font);
			e.gc.drawText("Wordle", 234, 5, true);
			
			drawUserInput(e);
			drawInputRectangles(e);
			drawKeyboard(e);
		});
		
		 canvas.addKeyListener(new KeyListener() {
	        	public void keyPressed(KeyEvent e) {      		        		
	        		// If ENTER key
	        		if (e.keyCode == 13) {
	        			if (col != 5) {
	        				// TODO: Warning message
	        				System.out.println("Not enough letters");
	        			} else {
	        				row++;
	        				col = 0;
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
	        				input[col][row] = e.character;    			
	        				col++;
	        			}
	        		}
	        		canvas.redraw();
	        	}
	        	
	        	public void keyReleased(KeyEvent e) {}
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
		// Set the correct font
		Font font = new Font(shell.getDisplay(), new FontData("Times New Roman", 40, SWT.BOLD));
		e.gc.setFont(font);		
		
		// Iterate over each cell in the input array
		for (int x=0; x<5; x++) {
			for (int j=0; j<6; j++) {
				// Check if cell is occupied
				if (input[x][j] != '?') {
					// Draw the character
					e.gc.drawText(Character.toString(input[x][j]).toUpperCase(), 136 + (71 * x), 65 + (70 * j), true);
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
		// Set the color of the background
		e.gc.setForeground(display.getSystemColor(SWT.COLOR_GRAY));
		
		// Iterate over 5 columns and 6 rows
		for (int i=0; i<5; i++) {
			for (int j=0; j<6; j++) {
				// Draw the square
				Rectangle rect = new Rectangle(120 + (70 * i), 60 + (70 * j), 60, 60);
				e.gc.drawRectangle(rect);
				// Draw a smaller square to increase line thickness
				rect = new Rectangle(121 + (70 * i), 61 + (70 * j), 58, 58);
				e.gc.drawRectangle(rect);
			}
		}
	}
	
	/* - - - - - - DRAW KEYBOARD - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * This function is responsible displaying the keyboard on the interface
	 * 
	 * @param e, the paint event which calls this function
	 * (Ryan Rizzo)
	 */
	private void drawKeyboard(PaintEvent e) {
		// Create a index variable
		int i=0;
		
		// Create array containing all characters in the order they appear on keyboard
		String[] qwerty = {"q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h",
				"j","k","l","z","x","c","v","b","n","m","`"};
		
		// Setup the font for each character
		Font font = new Font(shell.getDisplay(), new FontData("Times New Roman", 18, SWT.BOLD));
		e.gc.setFont(font);
		e.gc.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
		
		// Draw the first row of keys
		while (!qwerty[i].equals("a")) {
			e.gc.fillRoundRectangle(65 + (45 * i), 500, 40, 50, 10, 10);
			e.gc.drawText(qwerty[i], 80 + (45 * i), 515, true);
			i++;
		}
		
		// Draw the second row of keys
		while (!qwerty[i].equals("z")) {
			e.gc.fillRoundRectangle(88 + (45 * ( i- 10)), 560, 40, 50, 10, 10);
			e.gc.drawText(qwerty[i], 103 + (45 * (i - 10)), 575, true);
			i++;
		}
		
		// Draw the third row of keys
		while (!qwerty[i].equals("`")) {
			e.gc.fillRoundRectangle(133 + (45 * ( i- 19)), 620, 40, 50, 10, 10);
			e.gc.drawText(qwerty[i], 148 + (45 * (i - 19)), 635, true);
			i++;
		}
		
		// Setup font for Enter & Delete
		font = new Font(shell.getDisplay(), new FontData("Times New Roman", 14, SWT.BOLD));
		e.gc.setFont(font);
		
		// Draw the Enter key
		e.gc.fillRoundRectangle(64, 620, 64, 50, 10, 10);
		e.gc.drawText("ENTER", 72, 638, true);
		
		// Draw the Delete key
		e.gc.fillRoundRectangle(448, 620, 64, 50, 10, 10);
		e.gc.drawText("DELETE", 452, 638, true);
	}
}
