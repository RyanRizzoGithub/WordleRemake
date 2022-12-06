/**
 * UI for the leaderboard. Shows the top 10 player profiles.
 * 
 * @author Gregory Jenkins
 */

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class WordleLeaderboardUI {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			WordleLeaderboardUI window = new WordleLeaderboardUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 600);
		shell.setText("Leaderboard");
		shell.setLayout(null);
		
		Wordle.loadPlayers();
		
		// Top 10
		Label lblTop = new Label(shell, SWT.NONE);
		lblTop.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 20, SWT.NORMAL));
		lblTop.setAlignment(SWT.CENTER);
		lblTop.setBounds(181, 44, 87, 24);
		lblTop.setText("Top 10");
		
		// 1-10 labels
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 15, SWT.NORMAL));
		label_1.setBounds(61, 102, 29, 24);
		label_1.setText("1");
		
		Label label_1_1 = new Label(shell, SWT.NONE);
		label_1_1.setText("2");
		label_1_1.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 15, SWT.NORMAL));
		label_1_1.setBounds(61, 132, 29, 24);
		
		Label label_1_2 = new Label(shell, SWT.NONE);
		label_1_2.setText("3");
		label_1_2.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 15, SWT.NORMAL));
		label_1_2.setBounds(61, 162, 29, 24);
		
		Label label_1_3 = new Label(shell, SWT.NONE);
		label_1_3.setText("4");
		label_1_3.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 15, SWT.NORMAL));
		label_1_3.setBounds(61, 192, 29, 24);
		
		Label label_1_4 = new Label(shell, SWT.NONE);
		label_1_4.setText("5");
		label_1_4.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 15, SWT.NORMAL));
		label_1_4.setBounds(61, 222, 29, 24);
		
		Label label_1_5 = new Label(shell, SWT.NONE);
		label_1_5.setText("6");
		label_1_5.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 15, SWT.NORMAL));
		label_1_5.setBounds(61, 252, 29, 24);
		
		Label label_1_6 = new Label(shell, SWT.NONE);
		label_1_6.setText("7");
		label_1_6.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 15, SWT.NORMAL));
		label_1_6.setBounds(61, 282, 29, 24);
		
		Label label_1_7 = new Label(shell, SWT.NONE);
		label_1_7.setText("8");
		label_1_7.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 15, SWT.NORMAL));
		label_1_7.setBounds(61, 312, 29, 24);
		
		Label label_1_8 = new Label(shell, SWT.NONE);
		label_1_8.setText("9");
		label_1_8.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 15, SWT.NORMAL));
		label_1_8.setBounds(61, 342, 29, 24);
		
		Label label_1_9 = new Label(shell, SWT.NONE);
		label_1_9.setText("10");
		label_1_9.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 15, SWT.NORMAL));
		label_1_9.setBounds(61, 372, 29, 24);
		
		String playerName = "";
		
		// Checks if a player is in the list, and draws it to the screen
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 15, SWT.NORMAL));
		lblNewLabel.setBounds(96, 102, 296, 24);
		if (Wordle.players.size() >= 1) {
			playerName = Wordle.players.get(0).getName();
		} else {
			playerName = "";
		}
		lblNewLabel.setText(playerName);
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		if (Wordle.players.size() >= 2) {
			playerName = Wordle.players.get(1).getName();
		} else {
			playerName = "";
		}
		lblNewLabel_1.setText(playerName);
		lblNewLabel_1.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 15, SWT.NORMAL));
		lblNewLabel_1.setBounds(96, 132, 296, 24);
		
		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		if (Wordle.players.size() >= 3) {
			playerName = Wordle.players.get(2).getName();
		} else {
			playerName = "";
		}
		lblNewLabel_2.setText(playerName);
		lblNewLabel_2.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 15, SWT.NORMAL));
		lblNewLabel_2.setBounds(96, 162, 296, 24);
		
		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		
		if (Wordle.players.size() >= 4) {
			playerName = Wordle.players.get(3).getName();
		} else {
			playerName = "";
		}
		lblNewLabel_3.setText(playerName);
		lblNewLabel_3.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 15, SWT.NORMAL));
		lblNewLabel_3.setBounds(96, 192, 296, 24);
		
		Label lblNewLabel_4 = new Label(shell, SWT.NONE);
		if (Wordle.players.size() >= 5) {
			playerName = Wordle.players.get(4).getName();
		} else {
			playerName = "";
		}
		lblNewLabel_4.setText(playerName);
		lblNewLabel_4.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 15, SWT.NORMAL));
		lblNewLabel_4.setBounds(96, 222, 296, 24);
		
		Label lblNewLabel_5 = new Label(shell, SWT.NONE);
		if (Wordle.players.size() >= 6) {
			playerName = Wordle.players.get(5).getName();
		} else {
			playerName = "";
		}
		lblNewLabel_5.setText(playerName);
		lblNewLabel_5.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 15, SWT.NORMAL));
		lblNewLabel_5.setBounds(96, 252, 296, 24);
		
		Label lblNewLabel_6 = new Label(shell, SWT.NONE);
		if (Wordle.players.size() >= 7) {
			playerName = Wordle.players.get(6).getName();
		} else {
			playerName = "";
		}
		lblNewLabel_6.setText(playerName);
		lblNewLabel_6.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 15, SWT.NORMAL));
		lblNewLabel_6.setBounds(96, 282, 296, 24);
		
		Label lblNewLabel_7 = new Label(shell, SWT.NONE);
		if (Wordle.players.size() >= 8) {
			playerName = Wordle.players.get(7).getName();
		} else {
			playerName = "";
		}
		lblNewLabel_7.setText(playerName);
		lblNewLabel_7.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 15, SWT.NORMAL));
		lblNewLabel_7.setBounds(96, 312, 296, 24);
		
		Label lblNewLabel_8 = new Label(shell, SWT.NONE);
		if (Wordle.players.size() >= 9) {
			playerName = Wordle.players.get(8).getName();
		} else {
			playerName = "";
		}
		lblNewLabel_8.setText(playerName);
		lblNewLabel_8.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 15, SWT.NORMAL));
		lblNewLabel_8.setBounds(96, 342, 296, 24);
		
		Label lblNewLabel_9 = new Label(shell, SWT.NONE);
		if (Wordle.players.size() >= 10) {
			playerName = Wordle.players.get(9).getName();
		} else {
			playerName = "";
		}
		lblNewLabel_9.setText(playerName);
		lblNewLabel_9.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 15, SWT.NORMAL));
		lblNewLabel_9.setBounds(96, 372, 296, 24);
		
		


	}
}