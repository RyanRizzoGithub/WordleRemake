import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
import org.eclipse.wb.swt.SWTResourceManager;

// Ryan Rizzo

public class WordleLoginUI {

	protected Shell shell;
	private Text loginUsername;
	private Text loginPassword;
	private Text createUsername;
	private Text createPassword;
	private Color[] colors;
	private int faceIndex;
	private int shirtIndex;
	private int backgroundIndex;

	public void start() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		
		faceIndex = 0;
		shirtIndex = 1;
		backgroundIndex = 2;
		
		colors = new Color[16];
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
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 334);
		shell.setText("Login");
		shell.setLayout(new GridLayout(2, false));
		
		// - - - - - - - - - - - - Left side to handle username and password - - - - - - - - - - - - // 
		Composite leftComp = new Composite(shell, SWT.NONE);
		leftComp.setLayout(new GridLayout(2, false));
		
		Label loginLabel = new Label(leftComp, SWT.NONE);
		loginLabel.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 16, SWT.BOLD));
		loginLabel.setText("Login...");
		new Label(leftComp, SWT.NONE);
		
		Label loginUsernameLabel = new Label(leftComp, SWT.NONE);
		loginUsernameLabel.setText("Username:");
		
		loginUsername = new Text(leftComp, SWT.BORDER);
		loginUsername.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label loginPasswordLabel = new Label(leftComp, SWT.NONE);
		loginPasswordLabel.setText("Password:");
		
		loginPassword = new Text(leftComp, SWT.PASSWORD);
		loginPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button submitLogin = new Button(leftComp, SWT.NONE);
		submitLogin.setText("Submit");
		new Label(leftComp, SWT.NONE);
		
		@SuppressWarnings("unused")
		Label label = new Label(leftComp, SWT.NONE);
		new Label(leftComp, SWT.NONE);
		
		Label createLabel = new Label(leftComp, SWT.NONE);
		createLabel.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 16, SWT.BOLD));
		createLabel.setText("Crete account...");
		new Label(leftComp, SWT.NONE);
		
		Label createUsernameLabel = new Label(leftComp, SWT.NONE);
		createUsernameLabel.setText("Username:");
		
		createUsername = new Text(leftComp, SWT.BORDER);
		createUsername.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label createPasswordLabel = new Label(leftComp, SWT.NONE);
		createPasswordLabel.setText("Password:");
		
		createPassword = new Text(leftComp, SWT.PASSWORD);
		createPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button submitCreate = new Button(leftComp, SWT.NONE);
		submitCreate.setText("Submit");
		new Label(leftComp, SWT.NONE);
		
		
		// - - - - - - - - - - - - Right side to handle profile image - - - - - - - - - - - - // 
		Composite rightComp = new Composite(shell, SWT.NONE);
		rightComp.setLayout(new FormLayout());
		
		Canvas canvas = new Canvas(rightComp, SWT.NONE);
		FormData fd_canvas = new FormData();
		fd_canvas.top = new FormAttachment(0, 21);
		canvas.setLayoutData(fd_canvas);
		canvas.setBackground(new Color(255,255,255));
		
		Composite controlsComp = new Composite(rightComp, SWT.NONE);
		fd_canvas.bottom = new FormAttachment(controlsComp, -6);
		fd_canvas.right = new FormAttachment(controlsComp, 0, SWT.RIGHT);
		fd_canvas.left = new FormAttachment(controlsComp, 0, SWT.LEFT);
		controlsComp.setLayout(new GridLayout(3, false));
		FormData fd_composite_2 = new FormData();
		fd_composite_2.bottom = new FormAttachment(100, -17);
		fd_composite_2.top = new FormAttachment(0, 156);
		fd_composite_2.right = new FormAttachment(100, -53);
		fd_composite_2.left = new FormAttachment(0, 10);
		controlsComp.setLayoutData(fd_composite_2);
		
		Button faceMinus = new Button(controlsComp, SWT.NONE);
		faceMinus.setText("<");
		
		Label faceLabel = new Label(controlsComp, SWT.CENTER);
		faceLabel.setAlignment(SWT.CENTER);
		faceLabel.setText("     Face");
		
		Button facePlus = new Button(controlsComp, SWT.NONE);
		facePlus.setText(">");
		
		Button shirtMinus = new Button(controlsComp, SWT.NONE);
		shirtMinus.setText("<");
		
		Label shirtLabel = new Label(controlsComp, SWT.CENTER);
		shirtLabel.setAlignment(SWT.CENTER);
		shirtLabel.setText("     Shirt");
		
		Button shirtPlus = new Button(controlsComp, SWT.NONE);
		shirtPlus.setText(">");
		
		Button backgroundMinus = new Button(controlsComp, SWT.NONE);
		backgroundMinus.setText("<");
		
		Label backgroundLabel = new Label(controlsComp, SWT.NONE);
		backgroundLabel.setText("Background");
		
		Button backgroundPlus = new Button(controlsComp, SWT.NONE);
		backgroundPlus.setText(">");
		
		
		canvas.addPaintListener(e -> {
			e.gc.setBackground(new Color(50, 50, 50));
			e.gc.fillRectangle(0, 0, 25, 150);
			e.gc.fillRectangle(165, 0, 25, 150);
			
			// Set color of background
			canvas.setBackground(colors[backgroundIndex]);
			
			// Set the color of the shirt
			e.gc.setBackground(colors[shirtIndex]);
			e.gc.fillOval(35, 80, 120, 120);
			
			// Set the color of the face
			e.gc.setBackground(colors[faceIndex]);
			e.gc.fillOval(60, 30, 70, 70);
			
		});
		
		faceMinus.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				faceIndex--;
				if (faceIndex < 0) {
					faceIndex = 15;
				}
				canvas.redraw();
			}
		});
		facePlus.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				faceIndex++;
				if (faceIndex > 15) {
					faceIndex = 0;
				}
				canvas.redraw();
			}
		});
		shirtMinus.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				shirtIndex--;
				if (shirtIndex < 0) {
					shirtIndex = 15;
				}
				canvas.redraw();
			}
		});
		shirtPlus.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				shirtIndex++;
				if (shirtIndex > 15) {
					shirtIndex = 0;
				}
				canvas.redraw();
			}
		});
		backgroundMinus.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				backgroundIndex--;
				if (backgroundIndex < 0) {
					backgroundIndex = 15;
				}
				canvas.redraw();
			}
		});
		backgroundPlus.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				backgroundIndex++;
				if (backgroundIndex > 15) {
					backgroundIndex = 0;
				}
				canvas.redraw();
			}
		});
		submitLogin.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				if (!loginUsername.getText().equals("") && !loginPassword.getText().equals("")) {
					
				} else {
					
				}
			}
		});
	}	
}
