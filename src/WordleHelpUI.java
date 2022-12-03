import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

public class WordleHelpUI {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			WordleHelpUI window = new WordleHelpUI();
			window.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void start() {
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
		shell.setSize(475, 550);
		shell.setText("HELP PAGE");
		shell.setLayout(new GridLayout(1, false));
		
		Label lblHowToPlay = new Label(shell, SWT.NONE);
		lblHowToPlay.setText("HOW TO PLAY...");
		
		Label lblYourGoal = new Label(shell, SWT.NONE);
		GridData gd_lblYourGoal = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblYourGoal.widthHint = 444;
		lblYourGoal.setLayoutData(gd_lblYourGoal);
		lblYourGoal.setText("- Your goal in this game is to guess the secret 5-letter word\n- "
				+ "You will have 5 separate guesses\n- With each guess the game reveals information "
				+ "for each character\n- If a character which you guessed is not in the solution, "
				+ "this\nsquare will remain gray.\n- If a character which you guessed, is in the solution,"
				+ " but a \ndifferent index, this square will be yellow.\n- If a character which you guessed "
				+ "is in the solution, and the \ncurrent index, the square will be green.\n- If you guess "
				+ "the word within 6 guesses, you win!\n- If you do not guess the word, you lose.");
		
		Label label = new Label(shell, SWT.NONE);
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setText("CONTROLS...");
		
		Label lblUseYour = new Label(shell, SWT.NONE);
		lblUseYour.setText("- Use your keyboard, or the on-screen keyboard to input each character\n-"
				+ " Once you have filled out a row and are confident, type ENTER or use \nthe on-screen "
				+ "keyboard to submit the word");
		
		Label label_1 = new Label(shell, SWT.NONE);
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setText("MODES...");
		
		Label lblWordOfThe = new Label(shell, SWT.NONE);
		lblWordOfThe.setText("WOTD:  \t    Classic type of Wordle. Each day a new unique word is selected.\nSurvival:"
				+ "   The game continues to supply new words, how many levels can you beat?\nDordle:   "
				+ "  A spin-off the original game where the user has 7 guesses, but two words.");
		GridData gd_lblWordOfThe = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblWordOfThe.widthHint = 445;
		lblWordOfThe.setLayoutData(gd_lblWordOfThe);
		new Label(shell, SWT.NONE);
		
		Label lblThemes = new Label(shell, SWT.NONE);
		lblThemes.setText("THEMES...");
		
		Label lblDarkTheme = new Label(shell, SWT.NONE);
		lblDarkTheme.setText("1. Dark Theme\n2. Light Theme\n3. Night Theme\n4. Sun Theme\n5. Color Theme");

	}

}
