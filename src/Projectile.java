import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

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

	Rectangle collisionBox;
	boolean isActive;

	public Projectile(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 20;
		this.height = 20;
		this.xSpeed = 0;
		this.ySpeed = 0;
		this.maxSpeed = 10;

		loadImage("betterBullet.png");
		
		collisionBox = new Rectangle();
		isActive = true;
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
		collisionBox.setBounds(x, y, width, height);
	}

	void loadImage(String imageFile) {
		if (needImage) {
			try {
				image = ImageIO.read(Projectile.class.getResourceAsStream(imageFile));

				gotImage = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			needImage = false;
		}
	}

	int getX() {
	return x;	
	}
	
	int getY() {
		return y;
	}
}
