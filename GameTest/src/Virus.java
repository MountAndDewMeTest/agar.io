import java.awt.Color;
import java.util.Random;

public class Virus {
	public int x;
	public int y;
	public static int rad;
	private int r, g, b;
	private Color c;
	public EZCircle virus;
	public static final int MAX_VIRUS = 10;
	public static double mass = 50; 
	
	public Virus(int x, int y, int rad) {
		this.x = x;
		this.y = y;
		this.rad = rad;
		
		if(isRGBNotWhite()) {
			drawVirus();
		}
		
	}
	public void drawVirus() {
		virus = EZ.addCircle(x, y, rad*2, rad*2, Color.black, true);
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
	
	public int getVirusXCenter() {
		return virus.getXCenter();
	}
	
	public int getVirusYCenter() {
		return virus.getYCenter();
	}
	
	public boolean isVirusPointInElement(int x, int y) {
		return virus.isPointInElement(x, y);
	}
	

}
