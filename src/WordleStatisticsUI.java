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


public class WordleStatisticsUI {

	protected Shell shell;

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

	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 600);
		shell.setText("Statistics");
		shell.setLayout(new GridLayout(2, false));

		Composite comp = new Composite(shell, SWT.CENTER);
		GridLayout grid = new GridLayout();
		grid.numColumns = 1;
		comp.setLayout(grid);
		
		int[] guessDistribution = Wordle.player.getGuessDistribution();
		
		
		Label name = new Label(comp, SWT.CENTER);
		name.setText("Name:\t" + Wordle.player.getName());
		
		Label gamesPlayed = new Label(comp, SWT.CENTER);
		gamesPlayed.setText("Games Played:\t" + Wordle.player.getGamesPlayed() );
		
		Label currentStreak = new Label(comp, SWT.CENTER);
		currentStreak.setText("Current Streak:\t" + Wordle.player.getCurrentStreak());
		
		Label maxStreak = new Label(comp, SWT.CENTER);
		maxStreak.setText("Max Streak:\t" + Wordle.player.getMaxStreak() );
		
		Label guesses = new Label(comp, SWT.CENTER);
		guesses.setText("Guesses:");
		
		Label one = new Label(comp, SWT.CENTER);
		one.setText("1:\t" + guessDistribution[0] );
		
		Label two = new Label(comp, SWT.CENTER);
		two.setText("2:\t" + guessDistribution[1] );
		
		Label three = new Label(comp, SWT.CENTER);
		three.setText("3:\t" + guessDistribution[2] );
		
		Label four = new Label(comp, SWT.CENTER);
		four.setText("4:\t" + guessDistribution[3] );
		
		Label five = new Label(comp, SWT.CENTER);
		five.setText("5:\t" + guessDistribution[4] );
		
		Label six = new Label(comp, SWT.CENTER);
		six.setText("6:\t" + guessDistribution[5] );


	}	
}




