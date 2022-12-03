import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public interface WordleUI {
	// Constant for shell bounds
	public static final int SHELL_WIDTH = 600;
	public static final int SHELL_HEIGHT = 750;
	public static final int SHELL_X = (Display.getDefault().getBounds().width / 2) - 300;
	public static final int SHELL_Y = (Display.getDefault().getBounds().height / 2) - 500;
	
	// Constants for theme indices
	public static final int BACKGROUND_COLOR = 0;
	public static final int KEY_EDGE_COLOR = 1;
	public static final int KEY_FILL_COLOR = 2;
	public static final int KEY_CHAR_COLOR = 3;
	
	// Constants for theme number
	public static final int DARK_THEME = 0;
	public static final int LIGHT_THEME = 1;
	public static final int NIGHT_THEME = 2;
	public static final int SUN_THEME = 3;
	public static final int COLOR_THEME = 4;
	
	/* - - - - - - START MENU - - - - - - - - - - - - - - - - - - - - - - - - -
	 * This method is responsible for starting the menu user interface
	 * Author: Ryan Rizzo
	 */
	public static void startMenu(WordleMenuUI menuUI) {
		menuUI.start();
	}
	
	/* - - - - - - START GAME - - - - - - - - - - - - - - - - - - - - - - - - -
	 * This method is responsible for starting the game user interface
	 * Author: Ryan Rizzo
	 */
	public static void startGame(WordleGameUI gameUI) {
		gameUI.start();
		
	}
	
	/* - - - - - - START LOGIN - - - - - - - - - - - - - - - - - - - - - - - - -
	 * This method is responsible for starting the user login interface
	 * Author: Ryan Rizzo
	 */
	public static void startLogin() {
		// Create a new login ui
		WordleLoginUI loginUI = new WordleLoginUI();
		// Open the interface
		loginUI.start();
	}
	
	public static void endGame() {
		WordleEndUI endUI = new WordleEndUI();
		endUI.start();
	}
	
	/* - - - - - - START LEADERBOARD - - - - - - - - - - - - - - - - - - - - - - - - -
	 * This method is responsible for starting the user login interface
	 * Author: Gregory Jenkins
	 */
	public static void startLeaderboard() {
		// Create a new login ui
		WordleLeaderboardUI leaderboardUI = new WordleLeaderboardUI();
		// Open the interface
		leaderboardUI.start();
	}
	
	/* - - - - - - START MODE - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * This method is responsible for starting the mode selection user interface
	 * Author: Ryan Rizzo
	 */
	public static void startMode() {
		// Create a new mode selection ui
		WordleModeUI modeUI = new WordleModeUI();
		// Open the interface
		modeUI.start();
	}
	
	public static void startHelp() {
		// Create a new help page ui
		WordleHelpUI helpUI = new WordleHelpUI();
		helpUI.start();
	}
	
	/* - - - - - - GET THEME COLORS - - - - - - - - - - - - - - - - - - -
	 * This method is responsible for setting the colors for the interfaces,
	 * given the theme
	 * @param theme, int which represents which themes is being used
	 * @return colors, an array of Color objects
	 * Author: Ryan Rizzo
	 */
	public static Color[] getThemeColors(int theme) {
		Color[] colors = new Color[4];
		if (theme == DARK_THEME) {
			colors[BACKGROUND_COLOR] = new Color(0, 0, 0); 			// Black
			colors[KEY_EDGE_COLOR] = new Color(181, 181, 181);		// Dark Gray
			colors[KEY_FILL_COLOR] = new Color(120, 119, 119);		// Gray
			colors[KEY_CHAR_COLOR] = new Color(255, 255, 255);		// White
		}
		if (theme == LIGHT_THEME) {
			colors[BACKGROUND_COLOR] = new Color(255, 255, 255);	// White
			colors[KEY_EDGE_COLOR] = new Color(0, 0, 0);			// Black
			colors[KEY_FILL_COLOR] = new Color(120, 119, 119);		// Gray
			colors[KEY_CHAR_COLOR] = new Color(0, 0, 0);			// Black
		}
		if (theme == NIGHT_THEME) {
			colors[BACKGROUND_COLOR] = new Color(44, 34, 128);		// Dark Blue
			colors[KEY_EDGE_COLOR] = new Color(237, 232, 71);		// Yellow
			colors[KEY_FILL_COLOR] = new Color(125, 125, 125);		// Gray
			colors[KEY_CHAR_COLOR] = new Color(237, 232, 71);		// Yellow
		}
		if (theme == SUN_THEME) {
			colors[BACKGROUND_COLOR] = new Color(245, 212, 137);	// Light Yellow
			colors[KEY_EDGE_COLOR] = new Color(0, 0, 0);			// Black
			colors[KEY_FILL_COLOR] = new Color(120, 119, 119);		// Gray
			colors[KEY_CHAR_COLOR] = new Color(0, 0, 0);			// Black
		}
		if (theme == COLOR_THEME) {
			colors[BACKGROUND_COLOR] = new Color(155, 245, 137);	// Light Green
			colors[KEY_EDGE_COLOR] = new Color(0, 0, 0);			// Black
			colors[KEY_FILL_COLOR] = new Color(148, 246, 247);		// Light Blue
			colors[KEY_CHAR_COLOR] = new Color(213, 106, 235);		// Purple
		}
		return colors;
	}
	
	public static Color[] getProfileColors() {
		Color[] colors = new Color[16];
		colors[0] = new Color(237, 126, 119);
		colors[1] = new Color(237, 170, 119);
		colors[2] = new Color(237, 211, 119);
		colors[3] = new Color(213, 237, 119);
		colors[4] = new Color(174, 237, 119);
		colors[5] = new Color(119, 237, 150);
		colors[6] = new Color(119, 237, 196);
		colors[7] = new Color(119, 217, 237);
		colors[8] = new Color(119, 147, 237);
		colors[9] = new Color(139, 119, 237);
		colors[10] = new Color(188, 119, 237);
		colors[11] = new Color(223, 119, 237);
		colors[12] = new Color(237, 119, 215);
		colors[13] = new Color(237, 119, 176);
		colors[14] = new Color(255, 255, 255);
		colors[15] = new Color(0, 0, 0);
		return colors;
	}
}


