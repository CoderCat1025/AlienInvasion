import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Projectile {
	public int x;
	public int y;
	public int width;
	public int height;

	static public BufferedImage image;
	static public boolean needImage = true;
	static public boolean gotImage = false;	

	public double xSpeed;
	public double ySpeed;
	public double maxSpeed;

	public Projectile(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 50;
		this.height = 50;
		this.xSpeed = 0;
		this.ySpeed = 0;
		this.maxSpeed = 20;

		loadImage("Futuristic_Bullet.png");
	}

	public void draw(Graphics g) {
		if (!gotImage) {
			width = 20;
			height = 20;
			g.setColor(Color.RED);
			g.fillRect(x - width / 2, y - height / 2, width, height);
		}
		else {
			g.drawImage(image, x, y, width, height, null);
		}
	}

	public void update() {
		x += xSpeed * maxSpeed;
		y += ySpeed * maxSpeed;
	}

	void loadImage(String imageFile) {
		if (needImage) {
			try {
				image = ImageIO.read(this.getClass().getResourceAsStream("Futuristic_Bullet.png"));
				gotImage = true;
			} catch (Exception e) {

			}
			needImage = false;
		}
	}
}
