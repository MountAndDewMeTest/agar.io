/*
 * Rules:
 * 1. When the Player eats a DOT, increase Player's mass
 * 2. When the Player collides with a BLOB:
 *  - If the Player's mass < a Blob's mass, then the Player gets eaten by that Blob (Game Over)
 *  - If the Player's mass > a Blob's mass, then the Player eats that Blob and increase Player's mass + that Blob's mass
 * 3. When a Virus collides with a:
 *   - Player:
 *     * If the Player's mass < Virus's mass, then the Virus does nothing
 *     * If the Player's mass > Virus's mass, then the Player's mass decreases
 *   - Blob:
 *     * If the Blob's mass < Virus's mass, then the Virus does nothing
 *     * If the Blob's mass > Virus's mass, then the Player's mass decreases
 * 4. When a BLOB eats a DOT, increase that BLOB's mass
 * 5. When a BLOB collides with another BLOB:
 *  - If the Blob's mass < the other Blob's mass, then the Blob gets eaten by the other Blob
 *  - If the Blob's mass > the other Blob's mass, then the Blob eats that other Blob and increase the Blob's mass + that other Blob's mass
 * 6.The game runs forever until:
 *  - LOSE GAME: When the Player gets eaten by a BLOB or a VIRUS
 *  - WIN GAME: When the Player has eaten all the other Blobs
 */

/*
 * TODO: Code a better method for how the player follows the cursor
 * TODO: Fix endGame() to properly restart the game
 * TODO: Create an algorithm for better spawning system for blobs, dots, and viruses
 * TODO: Create AI for Blob
 */
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
	//Screen Width and Height
	public GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public final int SCREEN_WIDTH = gd.getDisplayMode().getWidth();
	public final int SCREEN_HEIGHT = gd.getDisplayMode().getHeight();

	//Objects
	private ArrayList<Blob> blobsArrayList = new ArrayList<Blob>();
	private ArrayList<Dot> dotsArrayList = new ArrayList<Dot>();
	private ArrayList<Virus> virusesArrayList = new ArrayList<Virus>();
	
	//Input Name
	private EZText namePos;
	private String playerName;
	
	//Game Loop Booleans
	private boolean running = true;
	private boolean gameLoop = true;
	private String status = "";

	//End Game Elements
	public EZRectangle endScreen, yesButton, noButton;
	public EZText tryAgain, yesText, noText;
	
	//Timer Variables
	private long startTime;
	private long time;
	
	public static void main(String[] args) {
		Game game = new Game();
		game.init();
	}

	private void init() {
		EZ.initialize(SCREEN_WIDTH, SCREEN_HEIGHT);
		Menu menu = new Menu(SCREEN_WIDTH, SCREEN_HEIGHT);
		menu.initialPhaseInteract();
		
		menu.drawFirstPhase();
		playerName = getInputName(menu);
		
		menu.initiateSecondPhase();
		menu.secondPhaseInteract();
		
		if(menu.thirdPhase) {
			run();
		}
	}

	private void run() {
		drawGrid();
		
		Player player = new Player(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
		drawInputName(playerName);
		
		drawVirus();
		
		while(gameLoop) {
			startTime = System.currentTimeMillis();

			while(running) {
				movePlayer(player);
				updateNamePos();
				drawDots();
				
				//Player-Dot Collision
				for(int i = 0; i < dotsArrayList.size(); i++) {
					Dot eachDot;
					eachDot = dotsArrayList.get(i);

					double distance = Math.hypot(Player.x - eachDot.x, Player.y - eachDot.y);

					if(distance < Player.getPlayerRad() + Dot.DOT_RAD) {
						eachDot = dotsArrayList.get(i);
						eachDot.removeDot();
						dotsArrayList.remove(eachDot);

						Player.increaseMass(Dot.MASS);
						Player.updatePlayer();
					}
				}
				
				//Player-Virus Collision
				for(int i = 0; i < virusesArrayList.size(); i++) {
					Virus eachVirus;
					eachVirus = virusesArrayList.get(i);

					double distance = Math.hypot(Player.x - eachVirus.x, Player.y - eachVirus.y);

					if(distance < Player.getPlayerRad() + eachVirus.getVirusRad() && Player.mass > eachVirus.mass) {
						
						Player.decreaseMass(10);
						Player.updatePlayer();
					}
					
				}

				EZ.refreshScreen();
			}
		}
	}

	private void drawDots() {
		Random rand = new Random();

		//Constantly populate the screen with dots until MAX_DOTS
		if(dotsArrayList.size() < Dot.MAX_DOTS) {
			int x = rand.nextInt(SCREEN_WIDTH);
			int y = rand.nextInt(SCREEN_HEIGHT);
			Dot dot = new Dot(x, y);
			dotsArrayList.add(dot);
		}
	}
	
	private void drawVirus() {
		Random rand = new Random();
		
		//Adds in 10 randomly placed viruses
		if(virusesArrayList.size() < Virus.MAX_VIRUS) {
			int x = rand.ints(180, SCREEN_WIDTH - 180).findFirst().getAsInt();
			int y = rand.ints(180, SCREEN_HEIGHT - 180).findFirst().getAsInt();
			int rad = rand.ints(50, 90).findFirst().getAsInt();
			Virus virus = new Virus(x, y, rad);
			virusesArrayList.add(virus);
		}
	}

	private void drawGrid() {
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

	private void movePlayer(Player player) {
		Player.desX = EZInteraction.getXMouse();
		Player.desY = EZInteraction.getYMouse();

		player.move();
	}

	private void removePlayer() {
		Player.remove();
		EZ.removeEZElement(namePos);
	}

	private String getInputName(Menu menu) {

		Scanner s = new Scanner(System.in); 

		System.out.println("Enter username: ");
		String inputName;
		inputName = s.next(); 
		while(inputName.length() > 6) {
			System.out.println("The name is too long! Must be 6 or less characters. Re-enter a new name:"); 
			inputName = s.next(); 
		}
		s.close();
		
		menu.removeFirstPhase();
		menu.secondPhase = true;
		return inputName;
	}

	private void drawInputName(String inputName) {
		namePos = EZ.addText(Player.getPlayerXCenter(), Player.getPlayerYCenter(), "", Color.white);
		namePos.setMsg(inputName);
	}

	private void updateNamePos() {
		namePos.translateTo(Player.getPlayerXCenter(), Player.getPlayerYCenter());
		namePos.setFontSize((int)Player.mass / 4);
		namePos.pullToFront();
	}

	private void endGame(long duration) {
		running = false;

		getDurationAlive();

		endScreen = EZ.addRectangle(0, 0, 10000, 10000, new Color(0, 0, 0, 90), true);
		tryAgain = EZ.addText(SCREEN_WIDTH / 2, (SCREEN_HEIGHT / 2) - 100, "Do you want to try again?", Color.white, 50);
		
		EZText timeAlive = EZ.addText(tryAgain.getXCenter(), tryAgain.getYCenter() - 150, "", Color.white, 60);
		timeAlive.setMsg("You were alive for " + duration + " seconds!");
				
		if(status.equals("lose")) {
			EZ.addText(tryAgain.getXCenter(), tryAgain.getYCenter() - 250, "YOU DIED", Color.white, 80);
		} else if (status.equals("win")) {
			EZ.addText(tryAgain.getXCenter(), tryAgain.getYCenter() - 250, "YOU WIN!", Color.white, 80);
		}

		yesButton = EZ.addRectangle((SCREEN_WIDTH / 2) - 300, SCREEN_HEIGHT / 2, 300, 100, Color.green, true);
		noButton = EZ.addRectangle((SCREEN_WIDTH / 2) + 300, SCREEN_HEIGHT / 2, 300, 100, Color.red, true);

		yesText = EZ.addText(yesButton.getXCenter(), yesButton.getYCenter(), "YES", Color.black, 50);
		noText = EZ.addText(noButton.getXCenter(), noButton.getYCenter(), "NO", Color.black, 50);
	}

	private long getDurationAlive() {
		long endTime = System.currentTimeMillis();
		time = ((endTime - startTime) / 1000);
		return time;
	}
}
