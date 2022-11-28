public interface WordleUI {
	public static void startMenu() {
		WordleMenuUI menuUI = new WordleMenuUI();
		menuUI.start();
	}
	
	public static void startGame() {
		WordleGame game = new WordleGame();
		WordleGameUI gameUI = new WordleGameUI(game);
		gameUI.start();
	}
	
	public static void startLogin() {
		WordleLoginUI loginUI = new WordleLoginUI();
		loginUI.start();
	}
	
	
}


