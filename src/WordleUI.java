import org.eclipse.swt.graphics.Color;

public interface WordleUI {
	// Constants for theme indices
	static final int BACKGROUND_COLOR = 0;
	static final int KEY_EDGE_COLOR = 1;
	static final int KEY_FILL_COLOR = 2;
	static final int KEY_CHAR_COLOR = 3;
	
	// Constants for theme num
	static final int DARK_THEME = 0;
	static final int LIGHT_THEME = 1;
	static final int NIGHT_THEME = 2;
	static final int SUN_THEME = 3;
	static final int COLOR_THEME = 4;
	
	public static void startMenu() {
		WordleMenuUI menuUI = new WordleMenuUI();
		menuUI.start();
	}
	
	public static void startGame(int theme) {
		WordleGame game = new WordleGame(theme);
		WordleGameUI gameUI = new WordleGameUI(game);
		gameUI.start();
	}
	
	public static void startLogin() {
		WordleLoginUI loginUI = new WordleLoginUI();
		loginUI.start();
	}
	
	public static void startMode() {
		WordleModeUI modeUI = new WordleModeUI();
		modeUI.start();
	}
	
	public static Color[] getThemeColors(int theme) {
		Color[] colors = new Color[4];
		if (theme == DARK_THEME) {
			colors[BACKGROUND_COLOR] = new Color(0, 0, 0);
			colors[KEY_EDGE_COLOR] = new Color(181, 181, 181);
			colors[KEY_FILL_COLOR] = new Color(120, 119, 119);
			colors[KEY_CHAR_COLOR] = new Color(255, 255, 255);
		}
		if (theme == LIGHT_THEME) {
			colors[BACKGROUND_COLOR] = new Color(255, 255, 255);
			colors[KEY_EDGE_COLOR] = new Color(0, 0, 0);
			colors[KEY_FILL_COLOR] = new Color(120, 119, 119);
			colors[KEY_CHAR_COLOR] = new Color(0, 0, 0);
		}
		if (theme == NIGHT_THEME) {
			colors[BACKGROUND_COLOR] = new Color(44, 34, 128);
			colors[KEY_EDGE_COLOR] = new Color(237, 232, 71);
			colors[KEY_FILL_COLOR] = new Color(125, 125, 125);
			colors[KEY_CHAR_COLOR] = new Color(237, 232, 71);
		}
		if (theme == SUN_THEME) {
			colors[BACKGROUND_COLOR] = new Color(245, 212, 137);
			colors[KEY_EDGE_COLOR] = new Color(0, 0, 0);
			colors[KEY_FILL_COLOR] = new Color(120, 119, 119);
			colors[KEY_CHAR_COLOR] = new Color(0, 0, 0);
		}
		if (theme == COLOR_THEME) {
			colors[BACKGROUND_COLOR] = new Color(155, 245, 137);
			colors[KEY_EDGE_COLOR] = new Color(0, 0, 0);
			colors[KEY_FILL_COLOR] = new Color(148, 246, 247);
			colors[KEY_CHAR_COLOR] = new Color(213, 106, 235);
		}
		
		return colors;
	}
	
}


