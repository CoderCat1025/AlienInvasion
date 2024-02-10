import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class fastEnemy extends Enemy {
	//this is a faster enemy that gives more points when defeated.
	public fastEnemy(int width, int height) {
		super(width, height);
		speed = 4;
		pointsForDeath = 3;
		enemyColor = Color.GREEN;
		enemyID = 2;
		imageName = "fastEnemy.png";
	}
}
