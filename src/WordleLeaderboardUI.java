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
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import com.swtdesigner.*;


public class WordleLeaderboardUI {

	protected Shell shell;

	private Color[] colors;


	public void start() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();



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
		shell.setSize(450, 600);
		shell.setText("Leaderboard");
		shell.setLayout(new GridLayout(2, false));

		Composite comp = new Composite(shell, SWT.NONE);
		GridLayout grid = new GridLayout();
		grid.numColumns = 1;
		comp.setLayout(grid);
		
		Canvas canvas = new Canvas(comp, SWT.NONE);
		
		int playerCount = 0;
		
		for (WordlePlayer p : Wordle.players) {
			playerCount++;
			
			Label label = new Label(comp, SWT.RIGHT);
			label.setText("" + p.getName());
			
			final int innerPlayerCount = playerCount;
			
			canvas.addPaintListener(e -> {
				e.gc.setBackground(new Color(50, 50, 50));

				// Set color of background
				canvas.setBackground(colors[p.getBackground()]);

				// Set the color of the shirt
				e.gc.setBackground(colors[p.getShirt()]);
				e.gc.fillOval(35, (80 + 50 * innerPlayerCount), 120, 120);

				// Set the color of the face
				e.gc.setBackground(colors[p.getFace()]);
				e.gc.fillOval(60, 30 + (50 * innerPlayerCount), 70, 70);

			});
		}

/*
		FormData fd_canvas = new FormData();
		fd_canvas.top = new FormAttachment(0, 21);


		FormData fd_composite_2 = new FormData();
		fd_composite_2.bottom = new FormAttachment(100, -17);
		fd_composite_2.top = new FormAttachment(0, 156);
		fd_composite_2.right = new FormAttachment(100, -53);
		fd_composite_2.left = new FormAttachment(0, 10);


		canvas.addPaintListener(e -> {
			e.gc.setBackground(new Color(50, 50, 50));

			

			// Set color of background
			canvas.setBackground(colors[backgroundIndex]);

			// Set the color of the shirt
			e.gc.setBackground(colors[shirtIndex]);
			e.gc.fillOval(35, 80, 120, 120);

			// Set the color of the face
			e.gc.setBackground(colors[faceIndex]);
			e.gc.fillOval(60, 30, 70, 70);

		});
*/
	}	
}
