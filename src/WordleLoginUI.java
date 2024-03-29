/**
 * AUTHOR(S):	Ryan Rizzo
 * FILE:		WordleLoginUI.java
 * CLASS:		CSC 335 - Final Project
 * DATE:		12/6/22
 * PURPOSE:		Responsible for allowing users to create an account or login, as well as
 * 				edit their profile image
 */

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;

// Ryan Rizzo
public class WordleLoginUI {

	protected Shell shell;
	private Text loginUsername;
	private Text loginPassword;
	private Text createUsername;
	private Text createPassword;
	private int faceIndex;
	private int shirtIndex;
	private int backgroundIndex;

	
	/** - - - - - - START - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 *  Generates the content and displays the login screen
	 *  @author Ryan Rizzo
	 */
	public void start() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();

		if (Wordle.player == null) {
			faceIndex = 0;
			shirtIndex = 1;
			backgroundIndex = 2;
		} else {
			faceIndex = Wordle.player.getFace();
			shirtIndex = Wordle.player.getShirt();
			backgroundIndex = Wordle.player.getBackground();
		}


		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		Wordle.player.setBackground(backgroundIndex);
		Wordle.player.setFace(faceIndex);
		Wordle.player.setShirt(shirtIndex);
		Wordle.player.savePlayer();
	}

	/** - - - - - - CREATE CONTENTS - - - - - - - - - - - - - - - - - - - - - - - -
	 * Responsible for adding all of the SWT widgets to the login
	 * ui, as well as drawing the canvas which represents the player profile image
	 * @author Ryan Rizzo
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 334);
		shell.setText("Login");
		shell.setLayout(new GridLayout(2, false));
		shell.setBackground(new Color(50, 50, 50));

		// Left composite used to take user input
		Composite leftComp = new Composite(shell, SWT.NONE);
		leftComp.setLayout(new GridLayout(2, false));
		
		// Right composite used to display user
		Composite rightComp = new Composite(shell, SWT.NONE);
		rightComp.setLayout(new FormLayout());
		
		// Create the canvas
		Canvas canvas = new Canvas(rightComp, SWT.NONE);

		Label loginLabel = new Label(leftComp, SWT.NONE);
		loginLabel.setFont(new Font(shell.getDisplay(), ".AppleSystemUIFont", 16, SWT.BOLD));
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
		createLabel.setFont(new Font(shell.getDisplay(), ".AppleSystemUIFont", 16, SWT.BOLD));
		createLabel.setText("Crete account...");
		new Label(leftComp, SWT.NONE);

		Label createUsernameLabel = new Label(leftComp, SWT.NONE);
		createUsernameLabel.setText("Username:");
		
		// Add text input for username
		createUsername = new Text(leftComp, SWT.BORDER);
		createUsername.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label createPasswordLabel = new Label(leftComp, SWT.NONE);
		createPasswordLabel.setText("Password:");
		
		// Add text input for password
		createPassword = new Text(leftComp, SWT.PASSWORD);
		createPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		// Add button to submit new account
		Button submitCreate = new Button(leftComp, SWT.NONE);
		submitCreate.setText("Submit");
		new Label(leftComp, SWT.NONE);
		
		// Add format data
		FormData fd_canvas = new FormData();
		fd_canvas.top = new FormAttachment(0, 21);
		canvas.setLayoutData(fd_canvas);
		canvas.setBackground(new Color(255,255,255));
		
		// Right side composite to handle profile image
		Composite controlsComp = new Composite(rightComp, SWT.NONE);
		fd_canvas.bottom = new FormAttachment(controlsComp, -6);
		fd_canvas.right = new FormAttachment(controlsComp, 0, SWT.RIGHT);
		fd_canvas.left = new FormAttachment(controlsComp, 0, SWT.LEFT);
		controlsComp.setLayout(new GridLayout(3, false));
		
		// Add format data
		FormData fd_composite_2 = new FormData();
		fd_composite_2.bottom = new FormAttachment(100, -17);
		fd_composite_2.top = new FormAttachment(0, 156);
		fd_composite_2.right = new FormAttachment(100, -53);
		fd_composite_2.left = new FormAttachment(0, 10);
		controlsComp.setLayoutData(fd_composite_2);
		
		// Face left arrow button
		Button faceMinus = new Button(controlsComp, SWT.NONE);
		faceMinus.setText("<");

		// Face color label
		Label faceLabel = new Label(controlsComp, SWT.CENTER);
		faceLabel.setAlignment(SWT.CENTER);
		faceLabel.setText("     Face");
		
		// Face right arrow button
		Button facePlus = new Button(controlsComp, SWT.NONE);
		facePlus.setText(">");
		
		// Shirt left arrow button
		Button shirtMinus = new Button(controlsComp, SWT.NONE);
		shirtMinus.setText("<");
		
		// Shirt color label
		Label shirtLabel = new Label(controlsComp, SWT.CENTER);
		shirtLabel.setAlignment(SWT.CENTER);
		shirtLabel.setText("     Shirt");
		
		// Shirt right arrow button
		Button shirtPlus = new Button(controlsComp, SWT.NONE);
		shirtPlus.setText(">");
		
		// Background left arrow button
		Button backgroundMinus = new Button(controlsComp, SWT.NONE);
		backgroundMinus.setText("<");
		
		// Background color label
		Label backgroundLabel = new Label(controlsComp, SWT.NONE);
		backgroundLabel.setText("Background");
		
		// Background right arrow button
		Button backgroundPlus = new Button(controlsComp, SWT.NONE);
		backgroundPlus.setText(">");

		// Draw the user profile image
		canvas.addPaintListener(e -> {
			e.gc.setBackground(new Color(50, 50, 50));
			e.gc.fillRectangle(0, 0, 25, 150);
			e.gc.fillRectangle(165, 0, 60, 150);

			// Set color of background
			canvas.setBackground(WordleUI.getProfileColors()[backgroundIndex]);

			// Set the color of the shirt
			e.gc.setBackground(WordleUI.getProfileColors()[shirtIndex]);
			e.gc.fillOval(35, 80, 120, 120);

			// Set the color of the face
			e.gc.setBackground(WordleUI.getProfileColors()[faceIndex]);
			e.gc.fillOval(60, 30, 70, 70);

		});
		
		// Add listener to decrease face color index
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
		
		// Add listener to increase face color index
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
		
		// Add listener to decrease shirt color index
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
		
		// Add listener to increase shirt color index
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
		
		// Add listener to decrease background color index
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
		
		// Add listener to increase background color index
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
		
		// Add listener for login submission button
		submitLogin.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				File file = new File("./Players/" + loginUsername.getText() + ".txt");
				if (file.exists()) {
					try {
						ObjectInputStream ios = new ObjectInputStream(new FileInputStream(file));
						WordlePlayer temp = (WordlePlayer) ios.readObject();
						if (temp.checkPassword(loginPassword.getText())) {
							Wordle.player = temp;
							faceIndex = Wordle.player.getFace();
							shirtIndex = Wordle.player.getShirt();
							backgroundIndex = Wordle.player.getBackground();
							canvas.redraw();
							
						} else {
							System.out.println("WRONG PASSWORD");
						}
						
						ios.close();
					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("Username not found");
				}
				
			}
		});
		
		// Add listener for create submission button
		submitCreate.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				File file = new File("./Players/" + createUsername.getText() + ".txt");
				
				if (!file.exists()) {

					Wordle.player = new WordlePlayer(createUsername.getText(), createPassword.getText(), file);
				} else if (!loginPassword.getText().equals("")) {
					System.out.println("Username is taken");
				}
			}
		});
	}	
}