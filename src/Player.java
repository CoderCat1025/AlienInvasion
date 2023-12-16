import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player {
	public int x;
	public int y;
	public int width;
	public int height;
	
	Rectangle collisionBox;
	boolean isActive;

	public Player() {
		this.x = 500;
		this.y = 500;
		this.width = 50;
		this.height = 50;
	}

	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(x - width / 2, y - height / 2, width, height);
	}
	
	void updateCollision() {
		collisionBox.setBounds(x, y, width, height);
	}
}
