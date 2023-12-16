import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ProjectileDemo extends JPanel implements MouseListener, ActionListener, KeyListener {

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;

	private JFrame window;
	private Timer timer;
	private Timer alienSpawn;
	private Player player;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Enemy> enemies;
	private int currentState = 1;
	Font titleFont;
	Font subtitleFont;

	//currentState 1 is the menu, 2 is the game, and 3 is game over screen

	public ProjectileDemo() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		window = new JFrame();
		timer = new Timer(1000 / 60, this);
		alienSpawn = new Timer(1000, this);
		player = new Player();
		projectiles = new ArrayList<Projectile>();
		enemies = new ArrayList<Enemy>();
		new Projectile(player.x, player.y);
		
		titleFont = new Font("Arial", Font.PLAIN, 48);
		subtitleFont = new Font("Arial", Font.PLAIN, 25);

		window.addMouseListener(this);
		window.addKeyListener(this);
		window.add(this);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		timer.start();
	}

	public static void main(String[] args) {
		new ProjectileDemo();
	}

	public void paintComponent(Graphics g) {
		if (currentState == 1) {
			drawMenuState(g);
		}
		else if (currentState == 2) {
			drawGameState(g);
		}
		else if (currentState == 3) {
			drawEndState(g);
		}
	}

	public void drawMenuState(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.BLACK);
		g.setFont(titleFont);
		g.drawString("Alien Invasion", 100, 100);
		g.setFont(subtitleFont);
		g.drawString("Press ENTER to start", 100, 150);
		g.drawString("Press SPACE for instructions", 100, 200);
	}

	public void drawGameState(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		player.draw(g);
		for(Projectile p : projectiles) {
			p.draw(g);
			p.update();
		}
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
	}

	public void drawEndState(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.BLACK);
		g.setFont(titleFont);
		g.drawString("Game Over", 100, 100);
		g.setFont(subtitleFont);
		g.drawString("You got killed by an alien", 100, 150);
		g.drawString("Press ENTER to return to menu", 100, 200);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Projectile projectile = new Projectile(player.x, player.y);
		projectile.x = player.x;
		projectile.y = player.y;

		int xdif = e.getX() - player.x;
		int ydif = e.getY() - player.y;

		double angle = Math.atan2((double) ydif, (double) xdif);

		projectile.xSpeed = Math.cos(angle);
		projectile.ySpeed = Math.sin(angle);
		projectiles.add(projectile);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			p.update();
			if(p.x > WIDTH || p.x < 0 || p.y > HEIGHT || p.y < 0) {
				projectiles.remove(i);
			}
		}
		repaint();
	}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_ENTER) {
			if (currentState == 1) {
				currentState = 2;
				gameStart();
			}
			else if (currentState == 2) {
				alienSpawn.stop();
				currentState = 3;
			}
			else if (currentState == 3) {
				currentState = 1;
			}
		}

		if (e.getKeyCode()==KeyEvent.VK_SPACE) {
			if (currentState == 1) {
				JOptionPane.showMessageDialog(null, "Aliens are invading! Click to shoot in the mouse's direction. "
						+ "Shooting enemies will give you points, and getting hit by an enemy will end the game."
						+ "some enemies may move faster. Shooting them will give you more points.");
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	void gameStart() {
		alienSpawn = new Timer (1000, null);
		alienSpawn.start();
	}
	void addAlien() {
		enemies.add(new Enemy());
	}
}
