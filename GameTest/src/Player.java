import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

	//Screen Width and Height
	public static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public static final int SCREEN_WIDTH = gd.getDisplayMode().getWidth();
	public static final int SCREEN_HEIGHT = gd.getDisplayMode().getHeight();

	//Create Objects
	public static ArrayList<Blob> blobsArrayList = new ArrayList<Blob>();
	public static ArrayList<Dot> dotsArrayList = new ArrayList<Dot>();
	public static Player player;
	public static Dot dot;

	public static EZText namePos;

	//Constants
	public static final int MAX_DOTS = 400; //Maximum amount of dots allowed on the screen

	public static boolean running = true;
	public static boolean game = true;
	public static String status = "";

	public static void main(String[] args) {
		EZ.initialize(SCREEN_WIDTH, SCREEN_HEIGHT);
		drawGrid();
		player = new Player(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
		namePos = inputSystem();

		drawBlobs();

		while(game) {
			while(running) {
				movePlayer();
				updateNamePos();
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

	}

	private static void movePlayer() {
		Player.desX = EZInteraction.getXMouse();
		Player.desY = EZInteraction.getYMouse();

		player.move();
	}

	private static void drawBlobs() {
		Random rand = new Random();
		for(int i = 0; i < 3; i++) {
			int x = rand.nextInt(SCREEN_WIDTH);
			int y = rand.nextInt(SCREEN_HEIGHT);
			int rad = rand.nextInt(30) + 20;
			Blob blob = new Blob(x, y, rad);
			blobsArrayList.add(blob);
		}
	}

	private static void drawDots() {
		Random rand = new Random();

		//Constantly populate the screen with dots until MAX_DOTS
		if(dotsArrayList.size() < MAX_DOTS) {
			int x = rand.nextInt(SCREEN_WIDTH);
			int y = rand.nextInt(SCREEN_HEIGHT);
			dot = new Dot(x, y);
			dotsArrayList.add(dot);
		}
	}

	private static void drawGrid() {
		int x = 0; 
		int y = 0; 
		Color c = new Color(0, 0, 0, 70);

		while (x < SCREEN_WIDTH) {
			EZ.addLine(x, 0, x, SCREEN_HEIGHT, c, 1);
			x += 20;
		}

		while(y < SCREEN_HEIGHT) {
			EZ.addLine(0, y, SCREEN_WIDTH, y, c, 1);
			y += 20;
		}
	}
	private static EZText inputSystem() {

		Scanner s = new Scanner(System.in); 

		System.out.println("Enter username: ");
		String inputName;
		inputName = s.next(); 
		while(inputName.length() > 6) {
			System.out.println("The name is too long! Must be 6 or less characters. Re-enter a new name:"); 
			inputName = s.next(); 
		}

		System.out.println("Choose a color for the Player (case-sensitive): \n red (default) \n orange \n yellow \n green \n blue \n purple \n pink");
		System.out.println("Color: ");
		String inputColor;
		inputColor = s.next();

		Color c;
		switch(inputColor) {
		case "red":
			c = new Color(255, 0, 0);
			Player.c = c;
			break;
		case "orange":
			c = new Color(255, 50, 0);
			Player.c = c;
			break;
		case "yellow":
			c = new Color(255, 255, 0);
			Player.c = c;
			break;
		case "green":
			c = new Color(0, 200, 0);
			Player.c = c;
			break;
		case "blue":
			c = new Color(0, 100, 200);
			Player.c = c;
			break;
		case "purple":
			c = new Color(100, 0, 200);
			Player.c = c;
			break;
		case "pink":
			c = new Color(255, 50, 200);
			Player.c = c;
			break;
		default:
			c = new Color(255, 0, 0); 
			Player.c = c;
			break;
		}

		s.close();

		namePos = EZ.addText(player.getPlayerXCenter(), player.getPlayerYCenter(), "", Color.black);
		namePos.setMsg(inputName);
		return namePos;
	}

	private static void updateNamePos() {
		namePos.translateTo(player.getPlayerXCenter(), player.getPlayerYCenter());
		namePos.setFontSize((int)Player.mass / 4);
		//namePos.pullToFront();
	}

	private static void endGame() {
		running = false;

		EZRectangle endScreen = EZ.addRectangle(0, 0, 10000, 10000, new Color(0, 0, 0, 90), true);

		EZText tryAgain = EZ.addText(SCREEN_WIDTH / 2, (SCREEN_HEIGHT / 2) - 100, "Do you want to try again?", Color.white, 50);

		if(status.equals("lose")) {
			EZText lose = EZ.addText(tryAgain.getXCenter(), tryAgain.getYCenter() - 150, "YOU DIED", Color.white, 80);
		} else if (status.equals("win")) {
			EZText win = EZ.addText(tryAgain.getXCenter(), tryAgain.getYCenter() - 150, "YOU WIN!", Color.white, 80);
		}

		EZRectangle yesButton = EZ.addRectangle((SCREEN_WIDTH / 2) - 300, SCREEN_HEIGHT / 2, 300, 100, Color.green, true);
		EZRectangle noButton = EZ.addRectangle((SCREEN_WIDTH / 2) + 300, SCREEN_HEIGHT / 2, 300, 100, Color.red, true);

		EZText yesText = EZ.addText(yesButton.getXCenter(), yesButton.getYCenter(), "YES", Color.black, 50);
		EZText noText = EZ.addText(noButton.getXCenter(), noButton.getYCenter(), "NO", Color.black, 50);

		System.out.println("Test1");
		if(EZInteraction.wasMouseLeftButtonPressed()) {

			System.out.println("Test2");
			int clickX = EZInteraction.getXMouse();
			int clickY = EZInteraction.getYMouse();

			System.out.println("Test3");
			if(yesButton.isPointInElement(clickX, clickY)) {

				System.out.println("Test4");
				endScreen.hide();
				tryAgain.hide();
				yesButton.hide();
				noButton.hide();
				yesText.hide();
				noText.hide();

				running = true;
			} else if (noButton.isPointInElement(clickX, clickY)) {
				EZ.removeAllEZElements();
				game = false;
				EZText exit = EZ.addText(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, "GAME OVER", Color.red);
			}
		}
	}	
}
