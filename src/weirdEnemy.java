import java.awt.Color;

public class weirdEnemy extends Enemy {
	//this is an enemy that spawns 2 more enemies when defeated.
	
	int hits = 0;

	public weirdEnemy(int width, int height) {
		super(width, height);

		enemyColor = Color.PINK;
		pointsForDeath = 2;
		speed = 2;
		enemyID = 3;
	}
}
