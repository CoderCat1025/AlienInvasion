import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Enemy {
	int x;
	int y;
	int width;
	int height;
	int speed;
	int pointsForDeath;
	Color enemyColor;

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

		speed = 3;
		pointsForDeath = 1;

	}

	void draw(Graphics g) {
		g.setColor(enemyColor);
		g.fillRect(x, y, width, height);
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
}
