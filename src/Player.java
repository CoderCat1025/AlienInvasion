import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Player {
	public int x;
	public int y;
	public int width;
	public int height;

	Rectangle collisionBox;
	boolean isActive;

	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;	

	public Player() {
		this.x = 500;
		this.y = 500;
		this.width = 100;
		this.height = 100;

		collisionBox = new Rectangle();

		loadImage("spaceship.png");
	}

	public void draw(Graphics g) {
		if (!gotImage) {
			width = 50;
			height = 50;
			g.setColor(Color.RED);
			g.fillRect(x - width / 2, y - height / 2, width, height);
		}
		else {
			g.drawImage(image, x - width / 2, y - height / 2, width, height, null);

		}
	}

	void update() {
		collisionBox.setBounds(x, y, width, height);
	}

	void loadImage(String imageFile) {
		if (needImage) {
			try {
				image = ImageIO.read(this.getClass().getResourceAsStream("spaceship.png"));
				gotImage = true;
			} catch (Exception e) {

			}
			needImage = false;
		}
	}
}
