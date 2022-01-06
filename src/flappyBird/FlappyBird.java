package flappyBird;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.JFrame;


public class FlappyBird implements ActionListener , KeyListener, MouseListener {
	
	public static FlappyBird flappyBird;
	public static int WIDTH =800, HEIGHT =800;
	public Render renderer;
	public Rectangle bird;
	public int ticks, yMotion;
	public ArrayList<Rectangle> column;
	public Random rand;
	public boolean gameOver, started;
	
	
	
	public FlappyBird () {
		JFrame jframe = new JFrame(); //makes frame
		Timer timer = new Timer (20,this);
		
		renderer = new Render ();
		rand = new Random();
		
		jframe.setTitle("Flappy Bird"); //frame specifications
		jframe.add(renderer);
		jframe.addMouseListener(this);
		jframe.addKeyListener(this);
		jframe.setSize(WIDTH,HEIGHT);
		jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jframe.setVisible(true);
		
		bird = new Rectangle (WIDTH/2 -10 , HEIGHT/2 -10, 20,20); //creates and centers bird
		column = new ArrayList<Rectangle> (); //creates array for rectangle
		
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		
		timer.start();
		
	}
	
	public void addColumn (boolean start) {
		int space = 300;
		int width = 100;
		int height = 50 + rand.nextInt(300); //300 is the max
		
		if (start) {
		column.add(new Rectangle (WIDTH + width + column.size() * 300, HEIGHT - height - 190, width, height));
		column.add(new Rectangle (WIDTH + width + ((column.size()-1) * 300), 0, width, HEIGHT - height - space));
		
		}
		else {
			column.add(new Rectangle (column.get(column.size()-1).x + 600, HEIGHT - height - 190, width, height));
			column.add(new Rectangle (column.get(column.size()-1).x, 0, width, HEIGHT - height - space));
		}
		
	}
	
	public void paintColumn (Graphics g, Rectangle column) {
		g.setColor(Color.gray);
		g.fillRect(column.x, column.y, column.width, column.height); //paints column
	}
	
	public void jump () {
		
		if (gameOver) {
			
				bird = new Rectangle (WIDTH/2 -10 , HEIGHT/2 -10, 20,20); //creates and centers bird
				
				column.clear();
				yMotion = 0;
				
				addColumn(true);
				addColumn(true);
				addColumn(true);
				addColumn(true);
				
				gameOver = false;
		}

		if (!started) {
			
			started = true;
		}
		else if (!gameOver) {
			
			if (yMotion > 0) {
				
				yMotion = 0;
			}
			
			yMotion -= 6; //bird drop/height
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (started) {
		
		int speed = 7; //speed for columns
		
		ticks++;
		
		for (int i = 0; i < column.size() ; i++) {
			
			Rectangle column2 = column.get(i);
			column2.x -= speed;
			
		}
		
		if (ticks % 4 == 0 && yMotion < 15) {
			yMotion += 2; // bird speed
		}
		
		for (int i = 0; i < column.size() ; i++) { //removes columns
			
			Rectangle column2 = column.get(i);
			
			if (column2.x + column2.width < 0) {
				
				column.remove(column2);
				
				if (column2.y== 0) {
				
				addColumn(false);
				
				}
			}
			
		}
		
		bird.y += yMotion;
		
		for (Rectangle column2 : column) {
			
			
			if (column2.intersects(bird)) {
				
				gameOver = true;
				bird.x = column2.x - bird.width;
				
			}
		}
		
		if (bird.y > HEIGHT - 190 || bird.y < 0) {
			
			gameOver = true;
		}
		
		if (bird.y + yMotion >= HEIGHT -120) {
			bird.y = HEIGHT -210;
			
		}
		
		if (gameOver) {
			bird.y = HEIGHT -210;
		}
		
		renderer.repaint(); //defined repaint ()
	}
	}
	
	public void repaint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT); //background
		
		g.setColor(Color.green);
		g.fillRect(0, HEIGHT-190, WIDTH, 50); //grass
		
		g.setColor(Color.green.darker().darker().darker());
		g.fillRect(0, HEIGHT-160, WIDTH, 120); //ground
		
		g.setColor(Color.CYAN);
		g.fillRect(bird.x, bird.y, bird.width, bird.height); //bird
		
		g.setColor(Color.white);
		g.fillOval(0, HEIGHT-800, 100, 100); //moon
		
		g.setColor(Color.white);
		g.fillRect(WIDTH-200, HEIGHT-700, 10, 10); //stars
		
		g.setColor(Color.white.darker());
		g.fillRect(WIDTH-250, HEIGHT-750, 10, 10);
		
		g.setColor(Color.white);
		g.fillRect(WIDTH-150, HEIGHT-760, 10, 10);
		
		g.setColor(Color.white.darker());
		g.fillRect(WIDTH-400, HEIGHT-710, 10, 10);
		
		g.setColor(Color.white);
		g.fillRect(WIDTH-500, HEIGHT-770, 10, 10);
		
		g.setColor(Color.white.darker());
		g.fillRect(WIDTH-600, HEIGHT-700, 10, 10);
		
		
		for (Rectangle column : column) {
			paintColumn(g,column);
		}
		
		g.setColor(Color.red);
		g.setFont(new Font ("Times New Roman", 1, 100));
		
		if (!started) {
			g.drawString("Click to Start", 75, HEIGHT/2 - 50);;
		}
		
		if (gameOver) {
			g.drawString("GAME OVER", 75, HEIGHT/2 - 50);
		}
		
		
	}
	
	
	public static void main (String [] args) {
		
		flappyBird = new FlappyBird ();
		
	}



	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			jump();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		jump ();
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
