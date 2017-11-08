import java.awt.Color;
import java.util.Random;

public class Dot {
	
	private static int DOT_RAD = 5;
	
	public int x;
	public int y;
	
	private int r, g, b;
	public double mass;
	
	private Color c;
	private EZCircle d;

	public Dot(int x, int y) {
		this.x = x;
		this.y = y;
		
		mass = DOT_RAD * 2;
		
		if(isRGBNotWhite()) {
			drawDot();
		}
	}

	public void drawDot() {
		d = EZ.addCircle(x, y, DOT_RAD * 2, DOT_RAD * 2, c, true);
	}

	private boolean isRGBNotWhite() {
		Random rand = new Random();

		r = rand.nextInt(255);
		g = rand.nextInt(255);
		b = rand.nextInt(255);
		
		while(r > 220 && g > 220 && b > 220) {
			r = rand.nextInt(255);
			g = rand.nextInt(255);
			b = rand.nextInt(255);
		}
		
		this.c = new Color(r, g, b);
		return true;
	}
	
	public int getDotXCenter() {
		return d.getXCenter();
	}
	
	public int getDotYCenter() {
		return d.getYCenter();
	}
	
	public boolean isDotPointInElement(int x, int y) {
		return d.isPointInElement(x, y);
	}
	
	public void removeDot() {
		EZ.removeEZElement(d);
	}
}
