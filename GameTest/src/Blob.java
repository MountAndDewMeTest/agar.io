import java.awt.Color;
import java.util.Random;

//Enemy Blobs
public class Blob {
	public int x;
	public int y;
	public int rad;
	public double mass;
	private int r, g, b;
	private Color c;
	public EZCircle blob;

	public Blob(int x, int y, int rad) {
		this.x = x;
		this.y = y;
		this.rad = rad;
		this.mass = rad * rad;

		if(isRGBNotWhite()) {
			drawBlob();
		}
	}
	
	public void drawBlob() {
		blob = EZ.addCircle(x, y, rad * 2, rad * 2, c, true);
	}

	public void move() {
		blob.translateBy(x, y);
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
}
