import java.awt.Color;
import java.util.Random;

public class weirdEnemy extends Enemy {

	public weirdEnemy(int width, int height) {
		super(width, height);

		enemyColor = Color.PINK;
		pointsForDeath = 2;
		speed = 4;
	}

	void update() {
		if (x > 500 && x < 600) {
			x+= speed;
		} else if (x < 500 && x > 4) {
			x -= speed;
		} else if (x > 599) {
			x -= speed;
		} else if (x < 451) {
			x += speed;
		}

		if (y > 500 && y < 600) {
			y+= speed;
		}
		else if (y < 500 && y > 400) {
			y-=speed;
		} 
		else if (y > 599) {
			y -= speed;
		}
		else if (y < 451) {
			y += speed;
		}
		collisionBox.setBounds(x, y, width, height);
	}
}
