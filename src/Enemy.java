import java.awt.Color;
import java.awt.Graphics;

public class Enemy {
	int x;
	int y;
	int width;
	int height;
	int speed;

void draw(Graphics g) {
	g.setColor(Color.BLUE);
	g.drawRect(x, y, width, height);
}

void update() {
	if (x > 300) {
		x-= speed;
	}
	else if(x < 300) {
		x+= speed;
	}
	
	if (y > 300) {
		y-= speed;
	}
	else if (y<300) {
		y-=speed;
	}
}
}
