import java.awt.Color;
import java.awt.MouseInfo;

public class Player {

	public static int x;
	public static int y;
	public static double rad;
	public static double mass;
	
	public static final int BORDER = 8;
	public static final int MAX_MASS = 150;
	
	public static int desX = 0;
	public static int desY = 0;

	public EZCircle p;
	public EZCircle pOutline; //Black border outline for the Player EZCircle
	
	public Player(int x, int y, int rad) {
		Player.x = x;
		Player.y = y;
		Player.rad = rad;
		Player.mass = rad;
		
		int width = rad * 2;
		int height = rad * 2;
		p = EZ.addCircle(x, y, width,  height,  Color.blue, true);
		pOutline = EZ.addCircle(x, y, width + BORDER, height + BORDER, Color.black, true);
	}

	public void move() {
		Player.x = p.getXCenter();
		Player.y = p.getYCenter();
		
		float moveX = (float)(desX - x) / (float)mass;
		float moveY = (float)(desY - y) / (float)mass;
		
		pOutline.translateBy(moveX, moveY);
		pOutline.pullToFront();
		p.translateBy(moveX, moveY);
		p.pullToFront();
		
	}
	
	public void updatePlayer() {
		p.setWidth((int)mass);
		p.setHeight((int)mass);
		pOutline.setWidth((int)mass + BORDER);
		pOutline.setHeight((int)mass + BORDER);
		
		if(Player.mass > MAX_MASS) {
			Player.mass = MAX_MASS;
		}
	}
	
	public int getPlayerXCenter() {
		return p.getXCenter();
	}

	public int getPlayerYCenter() {
		return p.getYCenter();
	}

	public boolean isPlayerPointInElement(int x, int y) {
		return p.isPointInElement(x, y);
	}
}
