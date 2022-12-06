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

public class WordleModeUI {

	protected Shell shell;
	private static int modeSelected = 0;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			WordleModeUI window = new WordleModeUI();
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
		shell.setSize(431, 196);
		shell.setText("SWT Application");
		shell.setLayout(new GridLayout(2, false));
		
		Label lblMode = new Label(shell, SWT.NONE);
		lblMode.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 14, SWT.BOLD));
		lblMode.setText("Mode...");
		
		Label lblDescription = new Label(shell, SWT.NONE);
		lblDescription.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 14, SWT.BOLD));
		
		GridData gd_lblDescription = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblDescription.widthHint = 396;
		lblDescription.setLayoutData(gd_lblDescription);
		lblDescription.setText("Description...");
		
		Button btnWotd = new Button(shell, SWT.RADIO);
		btnWotd.setText("WOTD");
		
		Label lblwordOfThe = new Label(shell, SWT.NONE);
		lblwordOfThe.setText("\"Word of The Day\" is the classic wordle gamemode ");
		
		Button btnSurvival = new Button(shell, SWT.RADIO);
		btnSurvival.setText("Survival");
		
		Label lblSeeHowMany = new Label(shell, SWT.NONE);
		lblSeeHowMany.setText("See how many words you can get in a row!");
		
		Button btnMode = new Button(shell, SWT.RADIO);
		btnMode.setText("Dordle");
		
		Label lblTryToGuess = new Label(shell, SWT.NONE);
		lblTryToGuess.setText("Try to guess two words with 7 guesses");
		
		Button btnSubmit = new Button(shell, SWT.NONE);
		btnSubmit.setText("Submit");
		new Label(shell, SWT.NONE);
		
		
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
