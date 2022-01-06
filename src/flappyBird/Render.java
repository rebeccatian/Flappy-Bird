package flappyBird;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Render extends JPanel {

	private static final long serialVersionUID = -8716459427985969207L;
	
	
	protected void paintComponent (Graphics g) {
		super.paintComponent(g);
		FlappyBird.flappyBird.repaint(g);
	}
	


}
