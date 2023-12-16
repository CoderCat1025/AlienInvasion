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

		this.width = width;
		this.height = height;

		speed = 10;

	}

	void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect(x, y, width, height);
	}

	void update() {
		if (x >= 300) {
			x-= speed;
		}
		else if(x < 300) {
			x+= speed;
		}

		if (y >= 300) {
			y-= speed;
		}
		else if (y<300) {
			y-=speed;
		} 
	}

	void updateCollision() {
		collisionBox.setBounds(x, y, width, height);
	}
}
