import java.awt.Color;
import java.util.Random;

public class Virus {
	public static final int MAX_VIRUS = 10;
	public static final int BORDER = 8;
	public int x;
	public int y;
	public int rad;
	public int mass;
	
	private EZCircle virus, virusOutline;
	
	public Virus(int x, int y, int rad) {
		this.x = x;
		this.y = y;
		this.rad = rad;
		this.mass = rad * 2;
		
		virusOutline = EZ.addCircle(x, y, rad * 2 + BORDER, rad * 2 + BORDER, Color.yellow, true);
		virus = EZ.addCircle(x, y, rad * 2, rad * 2, Color.green, true);
		
	}
	
	public int getVirusRad() {
		return virusOutline.getWidth() / 2;
	}
	
}
