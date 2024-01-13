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
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ProjectileDemo extends JPanel implements MouseListener, ActionListener, KeyListener {

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;

	int score = 0;

	private JFrame window;
	private Timer timer;
	private Timer alienSpawn;
	private Player player;

	private ArrayList<Projectile> projectiles;
	private ArrayList<Enemy> enemies;

	private int currentState = 1;
	Font titleFont;
	Font subtitleFont;
	
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;	

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
			updateGameState();
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
		
		loadImage("space backkground.png");
		g.drawImage(image, 0, 0, ProjectileDemo.WIDTH, ProjectileDemo.HEIGHT, null);

		player.draw(g);
		for(Projectile p : projectiles) {
			p.draw(g);
			p.update();
		}
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}

		g.setColor(Color.BLACK);
		g.setFont(subtitleFont);
		g.drawString("score:" + String.valueOf(score), 500, 50);
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

	public void updateGameState() {
		update();
		if (player.isActive = false) {
			currentState = 3;
		}
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
		if (e.getSource()==alienSpawn) {
			addAlien();
		} else {
			for(int i = 0; i < projectiles.size(); i++) {
				Projectile p = projectiles.get(i);
				p.update();
				if(p.x > WIDTH || p.x < 0 || p.y > HEIGHT || p.y < 0) {
					projectiles.remove(i);
				}
			}
			repaint();
		}
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
				player = new Player();
				score = 0;
			}
		}

		if (e.getKeyCode()==KeyEvent.VK_SPACE) {
			if (currentState == 1) {
				JOptionPane.showMessageDialog(null, "Aliens are invading! Click to shoot in the mouse's direction. "
						+ "Shooting enemies will give you points, and getting hit by an enemy will end the game."
						+ "Some enemies are special, indicated by their color. Shooting them will give you more points.");
			}
		}

		//cheats

		//spawn aliens
		if (e.getKeyCode()==KeyEvent.VK_0) {
			addAlien();
		}

		//add score
		if (e.getKeyCode()==KeyEvent.VK_1) {
			score++;
		}

		if (e.getKeyCode()==KeyEvent.VK_2) {
			score+=10;
		}

		if (e.getKeyCode()==KeyEvent.VK_3) {
			score+=100;
		}

		//kill all enemies
		if (e.getKeyCode()==KeyEvent.VK_K) {
			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).isActive = false;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	void gameStart() {
		alienSpawn = new Timer (1000, this);
		alienSpawn.start();
	}
	void addAlien() {
		if (new Random().nextInt(20) > 3) {
			enemies.add(new Enemy(50, 50));
		} else if (new Random().nextInt(3) > 1) {
			enemies.add(new fastEnemy(50, 50));
		}
		else {
			enemies.add(new weirdEnemy(40, 40));
		}
	}

	void checkCollision() {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update();
			player.update();

			if (enemies.get(i).collisionBox.intersects(player.collisionBox)) {
				enemies.get(i).isActive = false;
				player.isActive = false;
			}

			for (int e = 0; e < projectiles.size(); e++) {
				projectiles.get(e).update();
				if (enemies.get(i).collisionBox.intersects(projectiles.get(e).collisionBox)) {
					enemies.get(i).isActive = false;
					projectiles.get(e).isActive = false;
				}
			}
		}
	}

	void purgeObjects() {
		for (int i = 0; i < enemies.size(); i++) {
			if (!enemies.get(i).isActive) {
				score+=enemies.get(i).pointsForDeath;
				enemies.remove(i);
			}
		}

		for(int e = 0; e < projectiles.size(); e++) {
			if (!projectiles.get(e).isActive) {
				projectiles.remove(e);
			}
		}
	}

	void update() {
		for (int e = 0; e < projectiles.size(); e++) {
			projectiles.get(e).update();
			if (projectiles.get(e).getX() == 1000 || projectiles.get(e).getY() == 1000) {
				projectiles.get(e).isActive = false;
			}
		}
		checkCollision();
		purgeObjects();
	}
	
	void loadImage(String imageFile) {
		if (needImage) {
			try {
				image = ImageIO.read(this.getClass().getResourceAsStream("space background.png"));
				gotImage = true;
			} catch (Exception e) {

			}
			needImage = false;
		}
	}
}
