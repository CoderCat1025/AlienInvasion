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

@SuppressWarnings("serial")
public class ProjectileDemo extends JPanel implements MouseListener, ActionListener, KeyListener {

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;

	int score = 0;
	private ArrayList<Integer> scores = new ArrayList<>();
	int highScore = 0;

	int enemyType = 0;

	boolean invincible = false;
	boolean pierces = false;
	int enemySpeed = 0;
	boolean showSettings = false;

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
			scores.add(score);
			highScore = score;
			for (int i = 0; i < scores.size(); i++) {
				if (scores.get(i) > highScore) {
					highScore = scores.get(i);
				}
			}
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
		g.drawString("High Score: " + highScore, 100, 300);
	}

	public void drawGameState(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		loadImage("space.png");
		g.drawImage(image, 0, 0, ProjectileDemo.WIDTH, ProjectileDemo.HEIGHT, null);

		player.draw(g);
		for(Projectile p : projectiles) {
			p.draw(g);
			p.update();
		}
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}

		g.setColor(Color.WHITE);
		g.setFont(subtitleFont);
		g.drawString("score:" + String.valueOf(score), 500, 50);
		
		if (!showSettings) {
			g.setColor(Color.WHITE);
			g.setFont(subtitleFont);
			g.drawString("Press S for settings", 50, 50);
		}

		if (showSettings) {
			g.setColor(Color.WHITE);
			g.setFont(subtitleFont);
			g.drawString("(E) Enemy Type: " + String.valueOf(enemyType), 50, 50);
			g.drawString("(I) Invincible: " + String.valueOf(invincible), 50, 100);
			g.drawString("High Score: " + String.valueOf(highScore), 50, 150);
			g.drawString("(C) Enemy Speed: " + String.valueOf(enemySpeed), 50, 200);
			g.drawString("(P) Piercing Bullets: " + String.valueOf(pierces), 50, 250);
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
		g.drawString("Score: " + score, 400, 300);
		g.drawString("High Score: " + highScore, 350, 350);
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
			for (int i = 0; i < enemies.size(); i++) {
				if (enemies.get(i).enemyID == 1) {
					if (enemySpeed == 0) {
						enemies.get(i).speed = 3;
					}
					if (enemySpeed == 1) {
						enemies.get(i).speed = 4;
					}
					if (enemySpeed == -1) {
						enemies.get(i).speed = 2;
					}
				}
				if (enemies.get(i).enemyID == 2) {
					if (enemySpeed == 0) {
						enemies.get(i).speed = 4;
					}
					if (enemySpeed == 1) {
						enemies.get(i).speed = 5;
					}
					if (enemySpeed == -1) {
						enemies.get(i).speed = 3;
					}
				}
				if (enemies.get(i).enemyID == 3) {
					if (enemySpeed == 0) {
						enemies.get(i).speed = 2;
					}
					if (enemySpeed == 1) {
						enemies.get(i).speed = 3;
					}
					if (enemySpeed == -1) {
						enemies.get(i).speed = 1;
					}
				}
			}
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
				gameStop();
			}
			else if (currentState == 3) {
				currentState = 1;
				player = new Player();
				score = 0;
			}
		}

		if (e.getKeyCode()==KeyEvent.VK_SPACE) {
			if (currentState == 1) {
				JOptionPane.showMessageDialog(null, "Aliens are invading! Click to shoot in the mouse's direction. \n"
						+ "Shooting enemies will give you points, and getting hit by an enemy will end the game. \n"
						+ "Some enemies are special, indicated by their color. Shooting them will give you more points. \n"
						+ "There is a faster version of the normal enemy (green) and a slower version that will spawn \n"
						+ "more enemies when killed (pink).");
			}
		}

		if (e.getKeyCode()==KeyEvent.VK_S) {
			if (showSettings) {
				showSettings = false;
			}
			else {
				showSettings = true;
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

		//enemy spawn settings
		if (e.getKeyCode()==KeyEvent.VK_E) {
			if (enemyType < 3) {
				enemyType++;
			}
			else if (enemyType == 3) {
				enemyType = 0;
			}
			else {
				enemyType = 0;
			}
		}

		//toggle invincibility
		if (e.getKeyCode() == KeyEvent.VK_I) {
			if (invincible) {
				invincible = false;
			}
			else {
				invincible = true;
			}
		}

		//change game speed
		if (e.getKeyCode() == KeyEvent.VK_C) {
			if (enemySpeed == 0) {
				enemySpeed = 1;
			}
			else if (enemySpeed == 1) {
				enemySpeed = -1;
			}
			else if (enemySpeed == -1) {
				enemySpeed = 0;
			}
		}

		//piercing bullets
		if (e.getKeyCode() == KeyEvent.VK_P) {
			if (pierces) {
				pierces = false;
			}
			else {
				pierces = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}

	void gameStart() {
		alienSpawn = new Timer (1000, this);
		alienSpawn.start();
	}

	void gameStop() {
		alienSpawn.stop();
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).pointsForDeath = 0;
			enemies.get(i).enemyID = 0;
			enemies.get(i).isActive = false;
			pierces = false;
			invincible = false;
			enemyType = 0;
			enemySpeed = 0;
			showSettings = false;
		}
		currentState = 3;
	}

	void addAlien() {
		if (enemyType == 0) {
			if (new Random().nextInt(10) > 1) {
				enemies.add(new Enemy(90, 90));
			} else if (new Random().nextInt(3) > 1) {
				enemies.add(new fastEnemy(60, 60));
			}
			else {
				enemies.add(new weirdEnemy(125, 125));
			}
		}
		else if (enemyType == 1) {
			enemies.add(new Enemy(90, 90));
		}
		else if (enemyType == 2) {
			enemies.add(new fastEnemy(60, 60));
		}
		else if (enemyType == 3) {
			enemies.add(new weirdEnemy(125, 125));
		}
	}

	void checkCollision() {
		player.update();
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update();

			if (enemies.get(i).collisionBox.intersects(player.collisionBox)) {
				enemies.get(i).isActive = false;
				if (!invincible) {
					player.isActive = false;
					gameStop();
				}
			}

			for (int e = 0; e < projectiles.size(); e++) {
				projectiles.get(e).update();
				if (enemies.get(i).collisionBox.intersects(projectiles.get(e).collisionBox)) {
					enemies.get(i).isActive = false;
					if (!pierces) {
						projectiles.get(e).isActive = false;
					}
				}
			}
		}
	}

	void purgeObjects() {
		for (int i = 0; i < enemies.size(); i++) {
			if (!enemies.get(i).isActive) {
				if (enemies.get(i).getEnemyID() == 3) {
					for (int e = 0; e < 2; e++) {
						if (enemyType < 3) {
							enemies.add(new Enemy(90, 90));
						}
						else {
							enemies.add(new weirdEnemy(125, 125));
						}
					}
				}
				score+=enemies.get(i).pointsForDeath;
				enemies.remove(i);
			}
		}

		for(int e = 0; e < projectiles.size(); e++) {
			if (!projectiles.get(e).isActive) {
				projectiles.remove(e);
			}
		}

		if (player.isActive = false) {
			currentState = 3;
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
				image = ImageIO.read(this.getClass().getResourceAsStream("space.png"));
				gotImage = true;
			} catch (Exception e) {

			}
			needImage = false;
		}
	}
}
