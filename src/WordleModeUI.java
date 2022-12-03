import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import com.swtdesigner.*;

public class WordleModeUI {

	protected Shell shell;

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
		btnMode.setText("Mode3");
		
		Label label = new Label(shell, SWT.NONE);
		label.setText("...");
		
		Button btnMode_1 = new Button(shell, SWT.RADIO);
		btnMode_1.setText("Mode4");
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setText("...");
		
		Button btnSubmit = new Button(shell, SWT.NONE);
		btnSubmit.setText("Submit");
		new Label(shell, SWT.NONE);

	}
}
