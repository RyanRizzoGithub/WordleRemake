import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;

public class WordleLoginUI {

	protected Shell shell;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;

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
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 334);
		shell.setText("Login");
		shell.setLayout(new GridLayout(2, false));
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		
		Label lblLogin = new Label(composite, SWT.NONE);
		lblLogin.setText("Login...");
		new Label(composite, SWT.NONE);
		
		Label lblUsername = new Label(composite, SWT.NONE);
		lblUsername.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUsername.setText("Username:");
		
		text = new Text(composite, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPassword = new Label(composite, SWT.NONE);
		lblPassword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPassword.setText("Password:");
		
		text_1 = new Text(composite, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnSubmit = new Button(composite, SWT.NONE);
		btnSubmit.setText("Submit");
		new Label(composite, SWT.NONE);
		
		Label label = new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblCreteAccount = new Label(composite, SWT.NONE);
		lblCreteAccount.setText("Crete account...");
		new Label(composite, SWT.NONE);
		
		Label lblUsername_1 = new Label(composite, SWT.NONE);
		lblUsername_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUsername_1.setText("Username:");
		
		text_2 = new Text(composite, SWT.BORDER);
		text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPassword_1 = new Label(composite, SWT.NONE);
		lblPassword_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPassword_1.setText("Password:");
		
		text_3 = new Text(composite, SWT.BORDER);
		text_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnSubmit_1 = new Button(composite, SWT.NONE);
		btnSubmit_1.setText("Submit");
		new Label(composite, SWT.NONE);
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setLayout(new FormLayout());
		
		Canvas canvas = new Canvas(composite_1, SWT.NONE);
		FormData fd_canvas = new FormData();
		fd_canvas.left = new FormAttachment(0, 26);
		fd_canvas.top = new FormAttachment(0, 10);
		fd_canvas.right = new FormAttachment(0, 162);
		canvas.setLayoutData(fd_canvas);
		canvas.setBackground(new Color(255,255,255));
		
		Composite composite_2 = new Composite(composite_1, SWT.NONE);
		fd_canvas.bottom = new FormAttachment(composite_2, -6);
		composite_2.setLayout(new GridLayout(3, false));
		FormData fd_composite_2 = new FormData();
		fd_composite_2.bottom = new FormAttachment(100, -17);
		fd_composite_2.top = new FormAttachment(0, 156);
		fd_composite_2.right = new FormAttachment(100, -53);
		fd_composite_2.left = new FormAttachment(0, 10);
		composite_2.setLayoutData(fd_composite_2);
		
		Button button_1 = new Button(composite_2, SWT.NONE);
		button_1.setText("<");
		
		Label lblFace_1 = new Label(composite_2, SWT.CENTER);
		lblFace_1.setAlignment(SWT.CENTER);
		lblFace_1.setText("     Face");
		
		Button button_2_2_1_2 = new Button(composite_2, SWT.NONE);
		button_2_2_1_2.setText(">");
		
		Button button_2_1 = new Button(composite_2, SWT.NONE);
		button_2_1.setText("<");
		
		Label lblFace = new Label(composite_2, SWT.CENTER);
		lblFace.setAlignment(SWT.CENTER);
		lblFace.setText("     Shirt");
		
		Button button_2_2_1_1 = new Button(composite_2, SWT.NONE);
		button_2_2_1_1.setText(">");
		
		Button button_2_1_1 = new Button(composite_2, SWT.NONE);
		button_2_1_1.setText("<");
		
		Label lblBackground = new Label(composite_2, SWT.NONE);
		lblBackground.setText("Background");
		
		Button button_2_2_1 = new Button(composite_2, SWT.NONE);
		button_2_2_1.setText(">");

	}
}
