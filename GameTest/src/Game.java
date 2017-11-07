import java.util.ArrayList;
import java.awt.Color;
import java.util.Random;
import java.util.Scanner;

public class Game {
	
	//Screen Width and Height
	public static final int SCREEN_WIDTH = 1366;
	public static final int SCREEN_HEIGHT = 768;

	public static ArrayList<Blob> blobsArrayList = new ArrayList<Blob>();
	public static ArrayList<Dot> dotsArrayList = new ArrayList<Dot>();
	public static Player player;
	public static Dot dot;
	
	//Constants
	public static final int MAX_DOTS = 400; //Maximum amount of dots allowed on the screen
	public static final int DOT_RAD = 5; //Set constant for the radius of dots
	
	public static boolean running = false;

	public static void main(String[] args) {
		EZ.initialize(SCREEN_WIDTH, SCREEN_HEIGHT);
		player = new Player(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 5);
		
		running = true;
		while(running) {
			movePlayer();
			drawDots();
			
			//Player-Dot Collision
			for(int i = 0; i < dotsArrayList.size(); i++) {
				Dot eachDot;
				if(player.isPlayerPointInElement(dotsArrayList.get(i).getDotXCenter(), dotsArrayList.get(i).getDotYCenter())) {
					eachDot = dotsArrayList.get(i);
					eachDot.removeDot();
					dotsArrayList.remove(eachDot);
					
					Player.mass += dot.mass;
					player.updatePlayer();
					System.out.println(Player.mass);
				}
			}
			
			//Player-Blob Collision
			
			//Player-Virus Collision
			
			
			EZ.refreshScreen();
		}
		
	}

	private static void movePlayer() {
		Player.desX = EZInteraction.getXMouse();
		Player.desY = EZInteraction.getYMouse();
		
		player.move();
	}

	private static void drawDots() {
		Random rand = new Random();
		
		//Constantly populate the screen with dots until MAX_DOTS
		if(dotsArrayList.size() < MAX_DOTS) {
			int x = rand.nextInt(SCREEN_WIDTH);
			int y = rand.nextInt(SCREEN_HEIGHT);
			dot = new Dot(x, y, DOT_RAD);
			dotsArrayList.add(dot);

//			x = rand.nextInt(SCREEN_WIDTH);
//			y = rand.nextInt(SCREEN_HEIGHT);
//			for(int i = 0; i < dotsArrayList.size(); i++) {
//				while(dotsArrayList.get(i).isPointInElement(x, y)) {
//					x = rand.nextInt(SCREEN_WIDTH);
//					y = rand.nextInt(SCREEN_HEIGHT);
//				} 
//				dot = new Dot(x, y, rad);
//				dotsArrayList.add(dot);
//			}

		}

	}

//	private static void inputName() {
//
//		Scanner s = new Scanner(System.in); //Create a scanner
//
//		System.out.println("Enter your first name: "); //Tells the player to enter their first name in the console
//
//		String inputName;
//		inputName = s.next(); //Reads the next String that the user has entered and assigns it to 'name'
//
//		//We want to make sure that the name entered by the user is not too long that it goes off the screen, so we set a limit so that the length of 'name' is 9 or less characters
//		while(inputName.length() > 9) { //As long as the length of 'name' is longer than 9, keep making the user re-enter a different name
//			System.out.println("The name is too long! Must be 9 or less characters. Re-enter a new name:"); //Warn the user that the name they have entered is too long
//			inputName = s.next(); //Reads the next line of String that the user has entered and reassigns it to 'name'
//		}
//		s.close(); //Close 's' Scanner
//
//		EZText namePos = EZ.addText(player.getPlayerXCenter(), player.getPlayerYCenter(), "", Color.white);
//		namePos.setMsg(inputName);
//		while(true) {
//			namePos.translateTo(player.getPlayerXCenter(), player.getPlayerYCenter());
//		}
//	}

}
