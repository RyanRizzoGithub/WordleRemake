import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class WordleMenuUI {
	private Shell shell;
	private Canvas canvas;
	private Display display;
	private boolean[] hovered;
	private int theme;
	
	public WordleMenuUI() {
		display = Display.getDefault();
		shell = new Shell(display);
		shell.setText("WordleMenu");
		shell.setLayout( new GridLayout());
		shell.setBounds(WordleUI.SHELL_X, WordleUI.SHELL_Y, WordleUI.SHELL_WIDTH, WordleUI.SHELL_HEIGHT);
		
		
		theme = 0;
		hovered = new boolean[6];
		for (int i=0; i<6; i++) {
			hovered[i] = false;
		}
		shell.setBackground(WordleUI.getThemeColors(theme)[WordleUI.BACKGROUND_COLOR]);
	}
	
	// TODO:
	/*
	public WordleMenuUI(WordlePlayer player) {
		display = Display.getDefault();
		shell = new Shell(display);
		shell.setText("WordleMenu");
		shell.setLayout( new GridLayout());
		shell.setBounds(WordleUI.SHELL_X, WordleUI.SHELL_Y, WordleUI.SHELL_WIDTH, WordleUI.SHELL_HEIGHT);
		
		
		theme = player.getTheme();
		hovered = new boolean[6];
		for (int i=0; i<6; i++) {
			hovered[i] = false;
		}
		shell.setBackground(WordleUI.getThemeColors(theme)[WordleUI.BACKGROUND_COLOR]);
	}
	*/
	
	public void start() {
		System.out.println("start");
		Composite menuComp = new Composite(shell, SWT.NO_FOCUS);
		
		canvas = new Canvas(menuComp, SWT.NONE);
		canvas.setSize(600, 1000);
		
		
		canvas.addPaintListener(e -> {
			drawAnimation(e);
			// Set the color of the background and font
			shell.setBackground(WordleUI.getThemeColors(theme)[WordleUI.BACKGROUND_COLOR]);
			canvas.setBackground(WordleUI.getThemeColors(theme)[WordleUI.BACKGROUND_COLOR]);
			e.gc.setBackground(WordleUI.getThemeColors(theme)[WordleUI.KEY_FILL_COLOR]);
			e.gc.setForeground(WordleUI.getThemeColors(theme)[WordleUI.KEY_CHAR_COLOR]);
			
			// Draw the user profile
			Font font = new Font(shell.getDisplay(), new FontData("Times New Roman", 18, SWT.NONE));
			e.gc.setFont(font);
			String name = "Guest";
			if (Wordle.player != null) {
				name = Wordle.player.getName();
			}
			e.gc.drawText("Logged in as: " + name, 228, 65, true);
			e.gc.drawRoundRectangle(250, 100, 100, 100, 15, 15);
			e.gc.drawRoundRectangle(251, 101, 98, 98, 15, 15);
			
			// Draw the title
			font = new Font(shell.getDisplay(), new FontData("Times New Roman", 40, SWT.BOLD));
			e.gc.setFont(font);
			e.gc.drawText("Wordle", 234, 5, true);
			
			// Play button
			e.gc.drawRoundRectangle(200, 230, 200, 50, 10, 10);
			if (hovered[0]) e.gc.fillRoundRectangle(201, 231, 199, 49, 10, 10);
			e.gc.drawText("Play!", 260, 230, true);
			
			font = new Font(shell.getDisplay(), new FontData("Times New Roman", 30, SWT.BOLD));
			e.gc.setFont(font);
			
			// Log in button	
			e.gc.drawRoundRectangle(210, 300, 180, 50, 10, 10);
			if (hovered[1]) e.gc.fillRoundRectangle(211, 301, 179, 49, 10, 10);
			e.gc.drawText("Login", 264, 308, true);
			
			
			// Mode button
			e.gc.drawRoundRectangle(210, 370, 180, 50, 10, 10);
			if (hovered[2]) e.gc.fillRoundRectangle(211, 371, 179, 49, 10, 10);
			e.gc.drawText("Mode", 264, 378, true);
			
			
			// Leader board button
			e.gc.drawRoundRectangle(210, 440, 180, 50, 10, 10);
			if (hovered[3]) e.gc.fillRoundRectangle(211, 441, 179, 49, 10, 10);
			e.gc.drawText("Leaderboard", 217, 448, true);
			
			
			// Theme selection button
			e.gc.drawRoundRectangle(210, 510, 180, 50, 10, 10);
			if (hovered[4]) e.gc.fillRoundRectangle(211, 511, 179, 49, 10, 10);
			e.gc.drawText("Theme", 258, 518, true);
			
			
			// Help page button
			e.gc.drawRoundRectangle(210, 580, 180, 50, 10, 10);
			if (hovered[5]) e.gc.fillRoundRectangle(211, 581, 179, 49, 10, 10);
			e.gc.drawText("Help", 270, 588, true);
			
			
		});
		
		// Add mouse move listener to highlight buttons once hovered
		canvas.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {				
				// If Play button
				if (e.x > 200 && e.x < 400 && e.y > 230 && e.y < 280) hovered[0] = true;
				
				// If Login button
				else if (e.x > 210 && e.x < 390 && e.y > 300 && e.y < 350) hovered[1] = true;
				
				// If Mode button
				else if (e.x > 210 && e.x < 390 && e.y > 370 && e.y < 420) hovered[2] = true;
				
				// If leader board button
				else if (e.x > 210 && e.x < 390 && e.y > 440 && e.y < 490) hovered[3] = true;
				
				// If theme button
				else if (e.x > 210 && e.x < 390 && e.y > 510 && e.y < 560) hovered[4] = true;
				
				// If help button
				else if (e.x > 210 && e.x < 390 && e.y > 580 && e.y < 630) hovered[5] = true;
				
				// Reset hovered array
				else {
					for (int i=0; i<6; i++) {
						hovered[i] = false;
					}
				}
			}
		});
		
		canvas.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) {
				// If play button
				if (e.x > 200 && e.x < 400 && e.y > 230 && e.y < 280) {
					setVisible(false);
					WordleGame newGame = new WordleGame(theme);
					WordleGameUI gameUI = new WordleGameUI(newGame);
					WordleUI.startGame(gameUI);
				}
				
				// If LOGIN button
				else if (e.x > 210 && e.x < 390 && e.y > 300 && e.y < 350) WordleUI.startLogin();
				
				// If MODE button
				else if (e.x > 210 && e.x < 390 && e.y > 370 && e.y < 420) WordleUI.startMode();
				
				// If THEME button
				else if (e.x > 210 && e.x < 390 && e.y > 510 && e.y < 560) theme++;

				if (theme > 4) {
					theme = 0;
				}
			}
			
			public void mouseDoubleClick(MouseEvent e) {}
			public void mouseUp(MouseEvent e) {}
		});
		
		// Event loop
		shell.open();
		while(shell.isVisible()) {
			canvas.redraw();
			if(!display.readAndDispatch()) {		
			}
		}
	}
	
	/* - - - - - - DRAW ANIMATION - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * This function is responsible displaying background falling letters animation
	 * 
	 * @param e, the paint event which calls this function
	 * (Ryan Rizzo)
	 */
	private void drawAnimation(PaintEvent e) {
		int time = Math.abs(((int) System.currentTimeMillis()/100));
		//System.out.println(WordleUI.SHELL_HEIGHT - ((time - 400) % WordleUI.SHELL_HEIGHT));
		
		Image background = new Image(shell.getDisplay(), "./images/background.png");
		e.gc.drawImage(background, 0,WordleUI.SHELL_HEIGHT - (WordleUI.SHELL_HEIGHT + time) % (WordleUI.SHELL_HEIGHT * 2));
		e.gc.drawImage(background, 0,0 - (WordleUI.SHELL_HEIGHT + time) % (WordleUI.SHELL_HEIGHT * 2));
	}
	
	public void setVisible(boolean vis) {
		shell.setVisible(vis);
	}
}
