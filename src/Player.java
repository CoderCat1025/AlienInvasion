import java.awt.Color;
import java.awt.Graphics;

public class Player {
	public int x;
	public int y;
	public int width;
	public int height;

	public Player() {
		this.x = 300;
		this.y = 300;
		this.width = 50;
		this.height = 50;
	}

	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(x - width / 2, y - height / 2, width, height);
	}
}
