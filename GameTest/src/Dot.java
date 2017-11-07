import java.awt.Color;
import java.util.Random;

public class Dot {
	public int x;
	public int y;
	public int rad;
	public double mass;
	public int r, g, b;
	public Color c;
	public EZCircle d;

	public Dot(int x, int y, int rad) {
		this.x = x;
		this.y = y;
		this.rad = rad;
		this.mass = rad * rad;

		if(isRGBNotWhite()) {
			drawDot();
		}
	}

	public void drawDot() {
		d = EZ.addCircle(x, y, rad * 2, rad * 2, c, true);
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
