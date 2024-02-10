import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

public class Enemy {
	int x;
	int y;
	int width;
	int height;
	int speed;
	int pointsForDeath;
	Color enemyColor;
	int enemyID = 1;
	String imageName;

	int e = new Random().nextInt(4);

	Rectangle collisionBox;
	boolean isActive;	

	public Enemy (int width, int height){
		if (e == 0) {
			this.x = new Random().nextInt(950);
			this.y=0;
		}
		else if (e == 1){
			this.y=new Random().nextInt(950);
			this.x=0;
		}
		else if (e == 2) {
			this.x = new Random().nextInt(950);
			this.y=950;
		}
		else if (e == 3) {
			this.y=new Random().nextInt(950);
			this.x=950;
		}

		collisionBox = new Rectangle();
		isActive = true;

		this.width = width;
		this.height = height;
		enemyColor = Color.BLUE;
		imageName = "Enemy.png";

		speed = 3;
		pointsForDeath = 1;
	}
	
	public BufferedImage image;
	public boolean needImage = true;
	public boolean gotImage = false;

	void draw(Graphics g) {
		loadImage(imageName);
		
		if (!gotImage) {
			if (enemyID == 1) {
				width = 50;
				height = 50;
			}
			else if (enemyID == 2) {
				width = 40;
				height = 40;
			}
			else if (enemyID == 3) {
				width = 60;
				height = 60;
			}
			g.setColor(enemyColor);
			g.fillRect(x, y, width, height);	
		}
		else {
			if (enemyID == 1) {
				
			}
			g.drawImage(image, x - width / 2, y - height / 2, width, height, null);
		}
	}

	void update() {
		if (x > 450 && x > 450 + speed - 1) {
			x-= speed;
		}
		else if(x < 450 && x < 446 - speed + 1) {
			x += speed;
		}

		if (y > 450 && y > 454 + speed - 1) {
			y-= speed;
		}
		else if (y < 450 && y < 446 - speed + 1) {
			y+=speed;
		} 
		collisionBox.setBounds(x, y, width, height);
	}
	
	int getEnemyID() {
		return enemyID;
	}
	
	void loadImage(String imageFile) {
		if (needImage) {
			try {
				image = ImageIO.read(this.getClass().getResourceAsStream(imageFile));
				gotImage = true;
			} catch (Exception e) {

			}
			needImage = false;
		}
	}
}
