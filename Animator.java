import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/* - - - - - - INPUT ANIMATOR - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 * This function is change the movement of keyboard
 * mode 1: enter the input -- get bigger and smaller
 * mode 2: not valid words -- shake left and right 5 times
 * mode 3: enter valid words -- fold in the middle and replace with new one that bigger from middle
 * mode 4: when win -- wave
 * @param e, the paint event which calls this function
 * (Qianwen Wang)
 */
public class Animator {
	// The timer interval in milliseconds
	private static final int TIMER_INTERVAL = 10;
	// The movement of input
	// mode 1
	private int w = 0;
	private int h = 0;
	private int states1 = 0;
	// mode 2
	private int w2 = 0;
	private int states2 = 0;
	private int count2 = 0;
	// mode 3
	private int w3 = 0;
	// mode 4
	private int w4 = 0;
	private int w42 = 0;
	private int w43 = 0;
	private int s4 = 0;
	private int count4 = 1;
	// We draw everything on this canvas
	private Canvas canvas;

	/* Runs the application */
	public void run() {
		final Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Animator");
		shell.setLayout(new FillLayout());
		// Create the canvas for drawing
		canvas = new Canvas(shell, SWT.NO_BACKGROUND);
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent event) {
				// Draw the background
				event.gc.fillRectangle(canvas.getBounds());
				// Set the color of the ball
				event.gc.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_RED));
				// draw the images
				Image character = new Image(shell.getDisplay(), "./images/" + "a" + "/" + "a" + "Black.png");
				Image character2 = new Image(shell.getDisplay(), "./images/" + "a" + "/" + "a" + "Yellow.png");
				// event.gc.drawImage(character, 100+x, 100+y);
				// mode 1
				event.gc.drawImage(character, 0, 0, 60, 60, 100 - (w / 3), 100 - (h / 3), 60 + (w / 2), 60 + (h / 2));
				event.gc.drawImage(character, 0, 0, 60, 60, 100 - (w / 3) + 100, 100 - (h / 3), 60 + (w / 2),
						60 + (h / 2));
				event.gc.drawImage(character, 0, 0, 60, 60, 100 - (w / 3) + 200, 100 - (h / 3), 60 + (w / 2),
						60 + (h / 2));
				// mode 2
				event.gc.drawImage(character, 0, 0, 60, 60, 100 - w2, 100 + 100, 60, 60);
				event.gc.drawImage(character, 0, 0, 60, 60, 100 - w2 + 100, 100 + 100, 60, 60);
				event.gc.drawImage(character, 0, 0, 60, 60, 100 - w2 + 200, 100 + 100, 60, 60);
				// mode 3
				if (w3 <= 30) {
					event.gc.drawImage(character, 0, 0, 60, 60, 100, 100 + 200 + w3, 60, 60 - w3 * 2);
					event.gc.drawImage(character, 0, 0, 60, 60, 100 + 100, 100 + 200 + w3, 60, 60 - w3 * 2);
					event.gc.drawImage(character, 0, 0, 60, 60, 100 + 200, 100 + 200 + w3, 60, 60 - w3 * 2);
				} else {
					event.gc.drawImage(character2, 0, 0, 60, 60, 100, 100 + 200 + 30 - w3 / 2, 60, 0 + w3);
					event.gc.drawImage(character2, 0, 0, 60, 60, 100 + 100, 100 + 200 + 30 - w3 / 2, 60, 0 + w3);
					event.gc.drawImage(character2, 0, 0, 60, 60, 100 + 200, 100 + 200 + 30 - w3 / 2, 60, 0 + w3);
				}
				// mode 4
				event.gc.drawImage(character, 0, 0, 60, 60, 100, 100 + 300 - w4*2, 60, 60);
				event.gc.drawImage(character, 0, 0, 60, 60, 100 + 100, 100 + 300 - w42*2, 60, 60);
				event.gc.drawImage(character, 0, 0, 60, 60, 100 + 200, 100 + 300 - w43*2, 60, 60);
			}
		});
		shell.open();
		// Set up the timer for the animation
		Runnable runnable = new Runnable() {
			public void run() {
				animate();
				display.timerExec(TIMER_INTERVAL, this);
			}
		};
		// Launch the timer
		display.timerExec(TIMER_INTERVAL, runnable);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		// Kill the timer
		display.timerExec(-1, runnable);
		display.dispose();
	}

	/**
	 * Animates the next frame
	 */
	public void animate() {
		// mode 1
		if (w >= 20) {
			w -= 1;
			h -= 1;
			states1 = 1;
		} else if (states1 == 1) {
			if (w == 0) {
				states1 = 2;
			}
			w -= 1;
			h -= 1;
		} else if (states1 != 2) {
			w += 1;
			h += 1;
		}
		// mode 2
		if (w2 >= 5) {
			states2 = 1;
		} else if (w2 < 0) {
			states2 = 0;
			count2 += 1;
		}
		if (states2 == 0 && count2 <= 3) {
			w2 += 1;
		} else if (states2 == 1 && count2 <= 3) {
			w2 -= 1;
		}
		// mode 3
		if (w3 <= 60) {
			w3++;
		}
		// mode 4
		if (count4 == 1) {
			if (s4 == 0) {
				w4++;
				if (w4 >= 10)
					s4 = 1;
			} else {
				w4--;
				if (w4 <= 0) {
					s4 = 0;
					count4 = 2;
				}
			}
		}else if (count4 == 2) {
			if (s4 == 0) {
				w42++;
				if (w42 >= 10)
					s4 = 1;
			} else {
				w42--;
				if (w42 <= 0) {
					s4 = 0;
					count4 = 3;
				}
			}
			
		}else if (count4 == 3){
			if (s4 == 0) {
				w43++;
				if (w43 >= 10)
					s4 = 1;
			} else {
				w43--;
				if (w43 <= 0) {
					s4 = 0;
					count4 = 4;
				}
			}	
		}
		// Force a redraw
		canvas.redraw();
	}

	/**
	 * main
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		new Animator().run();
	}
}