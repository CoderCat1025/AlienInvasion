import java.awt.Color;
import java.util.Random;

public class weirdEnemy extends Enemy {
	
	int hits = 0;

	public weirdEnemy(int width, int height) {
		super(width, height);

		enemyColor = Color.PINK;
		pointsForDeath = 2;
		speed = 2;
		enemyID = 3;
	}
}
