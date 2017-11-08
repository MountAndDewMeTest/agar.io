import java.awt.Color;

public class Player {
	public static final int BORDER = 8;
	public static final int MAX_MASS = 150;
	public static final int PLAYER_RAD = 5;
	
	public static int x;
	public static int y;
	
	public static Color c;
	public static double mass;
	public static int desX = 0;
	public static int desY = 0;

	private EZCircle p;
	private EZCircle pOutline; //Black border outline for the Player EZCircle
	
	public Player(int x, int y) {
		Player.x = x;
		Player.y = y;
		
		mass = PLAYER_RAD * 2;
		
		p = EZ.addCircle(x, y, PLAYER_RAD * 2,  PLAYER_RAD * 2,  c, true);
		pOutline = EZ.addCircle(x, y, PLAYER_RAD * 2 + BORDER, PLAYER_RAD * 2 + BORDER, Color.black, true);
		pOutline.pullToFront();
		p.pullToFront();
	}

	public void move() {
		Player.x = p.getXCenter();
		Player.y = p.getYCenter();
		
		float moveX = (float)(desX - x) / (float)mass;
		float moveY = (float)(desY - y) / (float)mass;
		
		pOutline.translateBy(moveX, moveY);
		p.setColor(c);
		p.translateBy(moveX, moveY);
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
