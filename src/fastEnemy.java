import java.awt.Color;

public class fastEnemy extends Enemy {
	//this is a faster enemy that gives more points when defeated.
	public fastEnemy(int width, int height) {
		super(width, height);
		speed = 5;
		pointsForDeath = 3;
		enemyColor = Color.GREEN;
		enemyID = 2;
	}


}
