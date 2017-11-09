import java.awt.Color;

public class Player {
	public static final int BORDER = 8;
	public static final int MAX_MASS = 250;
	public static final int PLAYER_RAD = 5;

	public static int x;
	public static int y;

	public static Color c;
	public static double mass;
	public static int desX = 0;
	public static int desY = 0;

	private static EZCircle p;
	private static EZCircle pOutline; //Black border outline for the Player EZCircle

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
		
		/*
		 * desX and desY are values from EZInteraction.getXMouse() and EZInteraction.getYMouse(), respectively
		 * Right now, when your mouse is outside the window screen, desX and desY return -1, 
		 *   which means that the Player is moving to -1, -1 when your mouse is outside the window screen
		 * As a workaround fix, we only move the Player circle if desX and desY are greater than -1
		 */
		if(Player.desX > -1 && Player.desY > -1) {
			pOutline.translateBy(moveX, moveY);
			p.setColor(c);
			p.translateBy(moveX, moveY);
		}

	}

	public void updatePlayer() {
		p.setWidth((int)mass);
		p.setHeight((int)mass);
		pOutline.setWidth((int)mass + BORDER);
		pOutline.setHeight((int)mass + BORDER);
		
		//Temporary solution until we find a suitable way to smoothly increase Player's mass
		if(Player.mass > MAX_MASS) {
			Player.mass = MAX_MASS;
		}
	}

	public static void removePlayer() {
		EZ.removeEZElement(p);
		EZ.removeEZElement(pOutline);
		EZ.removeEZElement(Game.namePos);
	}
	
	public static void hide() {
		p.hide();
		pOutline.hide();
	}
	
	public static void show() {
		p.show();
		pOutline.show();
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
