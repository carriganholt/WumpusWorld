import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

public class DrBsGui extends JFrame{
	private static final long serialVersionUID = 1L;

	private Screen screen;

	public DrBsGui() {
		setTitle("Wumpus World");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		screen = new Screen(4, 4);
		this.add(screen);

		pack();

		setVisible(true);
	}
	
	public static void main(String [] args){
		new DrBsGui();
	}
}
