import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * AUTHOR(S):	Ryan Rizzo
 * FILE:		WordleModeUI.java
 * CLASS:		CSC 335 - Final Project
 * DATE:		12/6/22
 * PURPOSE:		Responsible for changing the game mode
 */
public class WordleModeUI {
	protected Shell shell;
	private static int modeSelected = 0;

	/** - - - - - - START - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 *  Generates the content and displays the mode selection screen
	 *  @author Ryan Rizzo
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

	/** - - - - - - CREATE CONTENTS - - - - - - - - - - - - - - - - - - - - - - - -
	 * Responsible for adding all of the SWT widgets to the mode selection ui
	 * @author Ryan Rizzo
	 */
	protected void createContents() {
		// Setup the shell
		shell = new Shell();
		shell.setSize(431, 196);
		shell.setText("SWT Application");
		shell.setLayout(new GridLayout(2, false));
		
		// Add label for mode
		Label lblMode = new Label(shell, SWT.NONE);
		lblMode.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 14, SWT.BOLD));
		lblMode.setText("Mode...");
		
		// Add label for description
		Label lblDescription = new Label(shell, SWT.NONE);
		lblDescription.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 14, SWT.BOLD));
		
		GridData gd_lblDescription = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblDescription.widthHint = 396;
		lblDescription.setLayoutData(gd_lblDescription);
		lblDescription.setText("Description...");
		
		// Add radio button for Word of The Day
		Button btnWotd = new Button(shell, SWT.RADIO);
		btnWotd.setText("WOTD");
		
		// Add label for Word of The Day
		Label lblwordOfThe = new Label(shell, SWT.NONE);
		lblwordOfThe.setText("\"Word of The Day\" is the classic wordle gamemode ");
		
		// Add radio button for survival
		Button btnSurvival = new Button(shell, SWT.RADIO);
		btnSurvival.setText("Survival");
		
		// Add label for survival description
		Label lblSeeHowMany = new Label(shell, SWT.NONE);
		lblSeeHowMany.setText("See how many words you can get in a row!");
		
		// Add button for dordle
		Button btnMode = new Button(shell, SWT.RADIO);
		btnMode.setText("Dordle");
		
		// Add label for dordle description
		Label lblTryToGuess = new Label(shell, SWT.NONE);
		lblTryToGuess.setText("Try to guess two words with 7 guesses");
		
		// Add button to submit selection
		Button btnSubmit = new Button(shell, SWT.NONE);
		btnSubmit.setText("Submit");
		new Label(shell, SWT.NONE);
		
		// Add selection listener to the submission button
		btnSubmit.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				if (btnWotd.getSelection()) {
					modeSelected = 0;
				}
				if (btnSurvival.getSelection()) {
					modeSelected = 1;
				}
				if (btnMode.getSelection()) {
					modeSelected = 2;
				}
				shell.dispose();
			}
		});

	}
	
	/** - - - - - - GET MODE - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * Returns the mode selected by the user
	 * @return String mode
	 * @author Ryan Rizzo
	 */
	public static String getMode() {
		String mode = "WOTD";
		if (modeSelected == 1) {
			mode = "SURVIVAL";
		}
		if (modeSelected == 2) {
			mode = "DORDLE";
		}
		return mode;
	}
}
