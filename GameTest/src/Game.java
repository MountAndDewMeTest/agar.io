/*
 * Rules:
 * 1. When the Player eats a DOT, increase Player's mass
 * 2. When the Player collides with a BLOB:
 *  - If the Player's mass < a Blob's mass, then the Player gets eaten by that Blob (Game Over)
 *  - If the Player's mass > a Blob's mass, then the Player eats that Blob and increase Player's mass + that Blob's mass
 * 3. When a Virus collides with a:
 *   1) Player:
 *     A) If the Player's mass < Virus's mass, then the Virus does nothing
 *     B) If the Player's mass > Virus's mass, then the Player's mass decreases and will keep decreasing if the Player is still colliding until its mass is reduced to the mass of the virus it collided with
 *   2) Blob:
 *     A) If the Blob's mass < Virus's mass, then the Virus does nothing
 *     B) If the Blob's mass > Virus's mass, then the Blob's mass decreases and will keep decreasing if the Player is still colliding until its mass is reduced to the mass of the virus it collided with
 * 4. When a BLOB eats a DOT, increase that BLOB's mass
 * 5. When a BLOB collides with another BLOB:
 *  - If the Blob's mass < the other Blob's mass, then the Blob gets eaten by the other Blob
 *  - If the Blob's mass > the other Blob's mass, then the Blob eats that other Blob and increase the Blob's mass + that other Blob's mass
 * 6.The game runs forever until:
 *  - LOSE GAME: When the Player gets eaten by a BLOB
 *  - WIN GAME: When the Player has eaten all the other Blobs
 */

//Import the following libraries to be used in the program:
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/** Main Game Handler Class
 * @author Gian Calica
 * @author Simmy Javellana-Samonte
 * @author Andrew Yagin
 * @author Devin Seals
 */
public class Game {
	/** Game Title */
	public static final String GAME_TITLE = "dots.ICS";
	
	//Screen Width and Height, based off of the user's screen resolution
	public static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	/** User's Screen Width*/
	public static final int SCREEN_WIDTH = gd.getDisplayMode().getWidth();
	/** User's Screen Height*/
	public static final int SCREEN_HEIGHT = gd.getDisplayMode().getHeight();

	//Declare the objects to be used in this game
	/** ArrayList of Blob objects */
	private ArrayList<Blob> blobsArrayList = new ArrayList<Blob>();
	/** ArrayList of Dot objects */
	private ArrayList<Dot> dotsArrayList = new ArrayList<Dot>();
	/** ArrayList of Virus objects */
	private ArrayList<Virus> virusesArrayList = new ArrayList<Virus>();
	/** Menu object */
	private Menu menu;
	/** Player object*/
	private Player player;

	//User Input Name Variables - These are the variables that keep track of the name typed in by the user at the start of the game
	/** The String variable that contains the user's input name*/
	public static String playerName;
	/** EZText that contains the input name and place it on the center of the Player EZCircle*/
	private EZText namePos;
	/** Holds the value of the length of the user's input name*/
	private int inputNameLength;

	//Variables that determines the status of the game
	/** Main boolean that determines if the main game should keep running or not.*/
	private boolean running = true;
	/** String that determines if the user has won or lost the game*/
	private String status = "";

	//End Game Screen Elements
	public EZRectangle endScreen, yesButton;
	public EZText tryAgain, yesText;

	//Variables that track how long the user has been playing one round of the game
	private long startTime, getTime;

	//EZSound Files that are used in the game
	private EZSound powerUp, hurt, whoosh, wasted, error;

	//Main method
	public static void main(String[] args) {
		Game game = new Game(); //Instantiate the Game class
		game.createWindow();
		game.drawMenu();
	}

	/** Initializes an EZ Window. */
	private void createWindow() {
		EZ.initialize(SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	/** Draws the GUI for the 'Menu' of the game. Takes in user input(user name and Player color) to be used for the game. */
	private void drawMenu() {
		menu = new Menu(); //Initialize the menu variable by creating a Menu object
		loadSoundFiles(); 

		/*
		 * Initial Phase: Overview Screen
		 * First Phase: Prompts the user to enter a user name
		 * Second Phase: Prompts the user to pick a color
		 * Third Phase: Run the game
		 */
		menu.initialPhaseInteract(); 

		menu.drawFirstPhase();
		playerName = getInputName(); //Assigns the returned String value to playerName

		menu.initiateSecondPhase(); 
		menu.secondPhaseInteract();

		if(menu.thirdPhase) {
			run(); 
		}
	}

	/** Loads all the EZSound files to be used in the game */
	private void loadSoundFiles() {
		powerUp = EZ.addSound("resources/sounds/Powerup.wav"); //When the Player eats a Blob
		hurt = EZ.addSound("resources/sounds/Hit_Hurt.wav"); //When the Player decreases mass from hitting a virus
		whoosh = EZ.addSound("resources/sounds/whoosh.wav"); //Transition sound from drawGrid() to spawning the Player object
		wasted = EZ.addSound("resources/sounds/wasted.wav"); //When the Player dies
		error = EZ.addSound("resources/sounds/error.wav"); //When the user input name is too long in length
	}

	/**
	 * Play an EZSound by resetting it first then playing
	 * @param sound EZSound to reset and play
	 * @see loadSoundFiles() for the .wav files of each sound
	 */
	private void playSound(EZSound sound) {
		if(sound == powerUp) {
			powerUp.stop();
			powerUp.play();
		} else if(sound == hurt) {
			hurt.stop();
			hurt.play();
		} else if(sound == whoosh) {
			whoosh.stop();
			whoosh.play();
		} else if(sound == wasted) {
			wasted.stop();
			wasted.play();
		} else if(sound == error) {
			error.stop();
			error.play();
		}
	}

	/** Main game loop of the game. Call back to this when the user chose to restart the game from the end game screen. */
	private void run() {
		drawGrid();

		player = new Player(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2); //Create an Player object 'player', passing the half of SCREEN_WIDTH and SCREEN_HEIGHT as its its x,y coordinates
		drawInputName(playerName); //Pass the String playerName to drawInputName

		drawViruses();
		drawBlobs();

		startTime = System.currentTimeMillis(); //Start the timer for getting the duration of how long the Player has been alive for

		while(running) {
			player.move();
			updateNamePos(); 
			drawDots();
			rotateViruses();
			moveBlobs();
			
			//Dot Collision
			for(int i = 0; i < dotsArrayList.size(); i++) { //Iterate through the dotsArraylist
				Dot eachDot; //Variable that holds one Dot object for each index
				eachDot = dotsArrayList.get(i); //Assign a Dot object at each index to eachDot

				//Dot-Player Collision
				double distPlayerDot = Math.hypot(Player.x - eachDot.x, Player.y - eachDot.y); //Distance between the Player and eachDot object

				if(distPlayerDot < Player.getPlayerRad() + Dot.DOT_RAD) { //If the distance between the Player and that dot is less than the sum of the Player's and the Dot's radius, then they are touching each other, then...
					//Remove that particular Dot, as an EZCircle Element and from the dotsArrayList
					eachDot.removeDot();
					dotsArrayList.remove(eachDot); 

					Player.increaseMass(Dot.mass); //Increase the mass of Player by mass of the Dot eaten
					Player.updatePlayer(); 
				}

				//Dot-Blob Collision
				for(int j = 0; j < blobsArrayList.size(); j++) { //Iterate through the blobsArraylist
					Blob eachBlob; //Variable that holds one Blob object for each index
					eachBlob = blobsArrayList.get(j); //Assign a Blob object at each index to eachBlob

					double distBlobDot = Math.hypot(eachBlob.x - eachDot.x, eachBlob.y - eachDot.y); //Distance between eachBlob and eachDot object

					if(distBlobDot < eachBlob.getBlobRad() + Dot.DOT_RAD) { //If the distance between that blob and that dot is less than the sum of that particular Blob's radius and the Dot's radius, then they are touching each other, then...
						//Remove that particular Dot, as an EZCircle Element and from the dotsArrayList
						eachDot.removeDot();
						dotsArrayList.remove(eachDot);

						eachBlob.increaseMass(Dot.mass); //Increase the mass of that blob by mass of the Dot eaten
						eachBlob.updateBlob();
					}
				}
			}

			//Player-Blob Collision
			for(int i = 0; i < blobsArrayList.size(); i++) { //Iterate through the blobsArrayList
				Blob eachBlob; //Variable that holds one Blob object for each index
				eachBlob = blobsArrayList.get(i); //Assign a Blob object at each index to eachBlob

				double distPlayerBlob = Math.hypot(Player.x - eachBlob.x, Player.y - eachBlob.y); //Distance between the Player and eachBlob object

				/*
				 * In the original agar.io game, it seems like the bigger mass a Player 1 gets, the harder it is to for the Player 1 to eat Player 2 that 
				 * has a similar mass to Player 1 (one player does not immediately eat another player when their edges touch)
				 * So, we impose a simplified similar functionality penalty for when our Player tries to eat a Blob. 
				 * 
				 * Algorithm for Player-Blob Collision:
				 * First, we check if the Player circle's edges touched the edges of a blob. Then we check if that blob's mass is less than the Player's mass. 
				 * If it is, we check again if the Player's mass > Player.maxMass / 2 AND that blob's mass > Player.mass / 2, then it means that both the Player 
				 * and the Blob are decently big enough and have a similar mass with each other to shrink the collision hit box from the edges of the Player and the
				 * blob's circle to the center of the Player and blob's circle.
				 * Then, Only if the Player touched the blob's center will it eat the blob.
				 * 
				 * Else, if both the Player and the Blob are not big enough in mass, then their collision hit box is just their edges. 
				 */
				if(distPlayerBlob < Player.getPlayerRad() + eachBlob.rad) { //If the distance between the Player and eachBlob is less than the sum of the Player's rad and that particular blob's rad, then they are touching each other, then...
					if(eachBlob.mass < Player.mass) { //If eachBlob's mass < Player's current mass, then it means that the Player can eat the Blob.
						if(Player.mass > Player.maxMass / 2 && eachBlob.mass > Player.mass / 2) { //We now apply the property as stated in the algorithm outline above
							if(Player.isPlayerPointInElement(blobsArrayList.get(i).getBlobXCenter(), blobsArrayList.get(i).getBlobYCenter())) { //Shrinks the collision box of both the Player and that blob to the center instead of the edges
								playSound(powerUp);

								//Remove that particular Blob, as an EZCircle Element and from the blobsArrayList
								eachBlob.removeBlob();
								blobsArrayList.remove(eachBlob);

								Player.increaseMass(eachBlob.mass); //Increase the mass of Player by mass of that blob eaten
								Player.updatePlayer();
							}
						} else { //Else, if the property above is not met, the collision box stays at the edges of the Player and that blob
							playSound(powerUp);

							//Remove that particular Blob, as an EZCircle Element and from the blobsArrayList
							eachBlob.removeBlob();
							blobsArrayList.remove(eachBlob);

							Player.increaseMass(eachBlob.mass); //Increase the mass of Player by mass of that blob eaten
							Player.updatePlayer();
						}
					}

					/*
					 * We will not have the same penalty for when a Blob tries to eat a Player as when a big Player tries to eat a Blob; to give the Player more difficulty.
					 * If the blob is simply bigger than the Player, the hit box remains at the edges of the Player's and the blob's circle, regardless of the size of the blob.
					 */
					if(eachBlob.mass > Player.mass) {
						removePlayer();
						status = "lose"; //We indicate the status of the game as "lose" through the String 'status'
						endGame(getDurationAlive()); //Pass the returned value of getDurationAlive() to endGame()
					}
				}
			}

			// Blob-Blob Collision 
			for(int i = 0; i < blobsArrayList.size(); i++) {
				Blob eachBlob, otherBlob;
				eachBlob = blobsArrayList.get(i);
				for(int j = 0; j < blobsArrayList.size(); j++) {
					otherBlob = blobsArrayList.get(j);
					double distBlobBlob = Math.hypot(eachBlob.x - otherBlob.x, eachBlob.y - otherBlob.y);

					if(distBlobBlob < eachBlob.getBlobRad() + otherBlob.getBlobRad()) {
						if(eachBlob.mass < otherBlob.mass) {
							eachBlob.removeBlob();
							blobsArrayList.remove(eachBlob);
						} else if(eachBlob.mass > otherBlob.mass) {
							otherBlob.removeBlob();
							blobsArrayList.remove(otherBlob);
						}
					}
				}
			}

			//Virus Collision
			for(int i = 0; i < virusesArrayList.size(); i++) { //Iterate through the virusesArrayList
				Virus eachVirus; //Variable that holds one Virus object for each index
				eachVirus = virusesArrayList.get(i); //Assign a Virus object at each index to eachVirus

				//Player-Virus Collision
				double distPlayerVirus = Math.hypot(Player.x - eachVirus.x, Player.y - eachVirus.y); //Distance between the Player and eachVirus object

				//We apply Rule 3.1.B of our Game(See at the very top for the rules of the game)
				if(distPlayerVirus < Player.getPlayerRad() + eachVirus.rad && eachVirus.mass < Player.mass) { //If the distance between the Player and that virus is less than the sum of the Player's rad and that virus's rad AND that virus' mass is less than the Player's mass, then...
					playSound(hurt);
					Player.decreaseMass(10);
					Player.updatePlayer();
				}

				//Blob-Virus Collison
				for(int j = 0; j < blobsArrayList.size(); j++) {
					Blob eachBlob;
					eachBlob = blobsArrayList.get(j);
					double distBlobVirus = Math.hypot(eachBlob.x - eachVirus.x, eachBlob.y - eachVirus.y);

					if(distBlobVirus < eachBlob.getBlobRad() + eachVirus.rad && eachVirus.mass < eachBlob.mass) {
						eachBlob.decreaseMass(10);
						eachBlob.updateBlob();
					}
				}
			}

			if(!checkForBlobs()) { //If there are no more blobs in the blobsArrayList, then...
				status = "win"; //We indicate the status of the game as "win" through the String 'status'
				endGame(getDurationAlive()); //Pass the returned value of getDurationAlive() to endGame()
			}

			EZ.refreshScreen(); //Refresh EZScreen
		}
	}

	private void moveBlobs() {
		Random rand = new Random();
		Blob eachBlob;
		eachBlob = blobsArrayList.get(rand.nextInt(blobsArrayList.size()));
		eachBlob.desX = rand.nextInt(Game.SCREEN_HEIGHT);
		eachBlob.desY = rand.nextInt(Game.SCREEN_HEIGHT);
		eachBlob.move();
		
	}

	/**Draws the vertical and horizontal grid lines onto the screen */
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

		playSound(whoosh);
	}

	/**
	 * Gets the String input from the user via the console
	 * @return the input name typed in by the user
	 */
	private String getInputName() {
		Scanner s = new Scanner(System.in); 

		System.out.println("Enter user name: ");
		String inputName; //String variable that will hold the user name input typed in by the user
		inputName = s.nextLine(); 
		int maxChar = 12; //Max Amount of characters legal for the inputName

		//As long as the inputName length typed in by the user is longer than maxChar, keep making the user re-enter a new user name
		while(inputName.replaceAll(" ", "").length() > maxChar) { //We used a .replaceAll() before we calculated the .length() of inputName because we do not want to count the spaces in between the inputName towards the length
			playSound(error); //Plays the EZSound error to indicate to the user that the input name is too long
			System.out.println("The name is too long! Must be " + maxChar + " or less characters. Re-enter a new username:"); 
			inputName = s.nextLine(); 
		}
		s.close();
		menu.removeFirstPhase();
		menu.secondPhase = true; //Continues the flow of the game, this indicates that secondPhase from Menu can now be started
		inputNameLength = inputName.length(); //Assigns the length of inputName(spaces included) to inputNameLength. See updateNamePos() for the use of this.
		return inputName; //Return the inputName typed in by the user
	}

	/**
	 * Draws the EZText namePos to be centered on the Player
	 * @param inputName is the returned String value from getInputName()
	 */
	private void drawInputName(String inputName) { 
		namePos = EZ.addText(Player.getPlayerXCenter(), Player.getPlayerYCenter(), "", Color.white);
		namePos.setMsg(inputName); 
	}

	/** Updates the position of namePos
	 * @see getInputName() and drawInputname()
	 */
	private void updateNamePos() {
		namePos.translateTo(Player.getPlayerXCenter(), Player.getPlayerYCenter()); //Constantly set the position of namePos centered on the Player as it moves around the screen
		namePos.setFontSize((int)Player.mass / (inputNameLength + 1)); //Scale the font size of namePos by the Player.mass / (inputNameLength + 1).
		namePos.pullToFront(); 
	}

	/** Draws the Virus objects onto the screen at random x,y coordinates within a specified range */
	private void drawViruses() {
		Random rand = new Random();
		for(int i = 0; i < Virus.maxViruses; i++) {
			int x = rand.ints(180, SCREEN_WIDTH - 180).findFirst().getAsInt();
			int y = rand.ints(180, SCREEN_HEIGHT - 180).findFirst().getAsInt();
			int rad = rand.ints(50, 91).findFirst().getAsInt(); //Generate a random value for the radius of a virus within a specified range
			Virus virus = new Virus(x, y, rad); //Create a Virus object, passing the generated x, y, rad values
			virusesArrayList.add(virus); //Add the Virus object to the virusesArrayList
		}
	}

	/** Rotates the Virus objects displayed on the screen */
	private void rotateViruses() {
		for(int i = 0; i < Virus.maxViruses; i++) {
			Virus eachVirus;
			eachVirus = virusesArrayList.get(i);
			eachVirus.rotate();
		}
	}

	/** Draws the Blob objects onto the screen at random x,y coordinates within a specified range */
	private void drawBlobs() {
		Random rand = new Random();
		for(int i = 0; i < Blob.maxBlobs; i++) {
			int x = rand.ints(160, SCREEN_WIDTH - 160).findFirst().getAsInt();
			int y = rand.ints(160, SCREEN_HEIGHT - 160).findFirst().getAsInt();
			int rad = rand.ints(40, 81).findFirst().getAsInt(); //Generate a random value for the radius of a blob within a specified range
			Blob blob = new Blob(x, y, rad); //Create a Blob object, passing the generated x, y, rad values
			blobsArrayList.add(blob); //Add the Blob object to the blobsArrayList
		}
	}

	/**
	 * Checks if there are Blob objects in the blobsArrayList
	 * @return True if there are still Blob objects in the ArrayList. Otherwise, returns false if the ArrayList is empty.
	 */
	private boolean checkForBlobs() {
		if(blobsArrayList.size() == 0) {
			return false;
		}
		return true;
	}

	/** Draws the Dot objects onto the screen at random x,y coordinates within a specified range */
	private void drawDots() {
		Random rand = new Random();
		//Constantly populate the screen with dots with a maxDots cap
		if(dotsArrayList.size() < Dot.maxDots) {
			int x = rand.nextInt(SCREEN_WIDTH);
			int y = rand.nextInt(SCREEN_HEIGHT);
			Dot dot = new Dot(x, y); //Create a Dot object, passing the generated x,y values
			dotsArrayList.add(dot); //Add the Dot object to the dotsArrayList
		}
	}


	/** Removes the Player EZCircle elements along with namePos*/
	private void removePlayer() {
		Player.remove();
		EZ.removeEZElement(namePos);
	}

	/**
	 * Method that handles the end game screen elements and actions
	 * @param duration is the value returned from getDurationAlive()
	 * @see getDurationAlive()
	 */
	private void endGame(long duration) {
		running = false; //Set running to false to stop the program
		player = null; //"Delete" the player object (So when we restart the game we are not creating a second Player object.)

		endScreen = EZ.addRectangle(0, 0, 10000, 10000, new Color(0, 0, 0, 125), true); //Creates a black transparent screen over the game
		tryAgain = EZ.addText(SCREEN_WIDTH / 2, (SCREEN_HEIGHT / 2) - 100, "", Color.white, 50); //Initializes EZText tryAgain and center on the game window

		if(status.equals("lose")) { //If the status indicates that we have lost the game, then...
			flashEndGameScreen();

			/*
			 * Since we are using the "Wasted" sound effect from GTA V, we wanted the rest of the end game elements screen to sync and only appear after a certain frame of the EZSound wasted.wav was passed. 
			 * Like how the "Wasted" text in GTA V only appears after the deep 'bell' sound is played
			 */
			int getFrame;
			int targetFrame = 108000;
			while(true) {
				getFrame = wasted.getFramePosistion();
				if(getFrame >= targetFrame) { //Once we have reached the target frame, then break the while(true) loop and continue the rest of endGame()
					EZ.addText(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2 - 400, "YOU DIED", Color.red, 80); //Tells the user that they died
					tryAgain.setMsg("Do you want to try again?");
					break;
				}
			}
		} else if (status.equals("win")) { //If the status indicates that we have lost the game, then...
			EZ.addText(tryAgain.getXCenter(), tryAgain.getYCenter() - 250, "YOU WIN!", Color.red, 80); //Tells the user that they won
			tryAgain.setMsg("Do you want to try again?");
		}

		EZText timeAlive = EZ.addText(tryAgain.getXCenter(), tryAgain.getYCenter() - 150, "", Color.white, 60);
		timeAlive.setMsg("You were alive for " + duration + " seconds!");

		yesButton = EZ.addRectangle(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 300, 100, Color.green, true);

		yesText = EZ.addText(yesButton.getXCenter(), yesButton.getYCenter(), "YES", Color.black, 50);

		endGameInteract(); 
	}

	/** Yes Button interaction from the end game screen */
	private void endGameInteract() {
		while(true) {
			EZ.refreshScreen();
			int x = EZInteraction.getXMouse();
			int y = EZInteraction.getYMouse();
			if(EZInteraction.wasMouseLeftButtonReleased()) {
				if(yesButton.isPointInElement(x, y)) {
					wasted.stop();
					nukeElements();
					running = true; //We restart the game loop
					run();
					break;
				}
			}
		}
	}

	/**
	 * Creates a camera flash effect just before the full end game screen is shown
	 * Only shown when the Player dies
	 * @see endGame()
	 */
	private void flashEndGameScreen() {
		//Min and Max Alpha
		int alpha = 0;
		int maxAlpha = 200;

		EZRectangle flashScreen = EZ.addRectangle(0, 0, 10000, 10000, new Color(255, 255, 255, alpha), true); //Main flash screen
		playSound(wasted);

		try {
			//Fading into White Flash
			for(alpha = 0; alpha <= maxAlpha; alpha++) {
				Thread.sleep(1);
				flashScreen.setColor(new Color(255, 255, 255, alpha));
			}
			//Fading out of White Flash
			for(alpha = maxAlpha; alpha >= 0; alpha--) {
				Thread.sleep(1);
				flashScreen.setColor(new Color(255, 255, 255, alpha));
			}

			EZ.removeEZElement(flashScreen);
		} catch(InterruptedException e) {
			System.out.println("Error: Flash Screen");
			e.printStackTrace();
		}
	}

	/**
	 * Tracks the duration of how long the Player has been alive for
	 * @return the duration alive
	 */
	private long getDurationAlive() {
		long endTime = System.currentTimeMillis();
		getTime = ((endTime - startTime) / 1000);
		return getTime;
	}

	/** Clears the ArrayList and removes the EZElements */
	private void nukeElements() {
		dotsArrayList.clear();
		blobsArrayList.clear();
		virusesArrayList.clear();
		EZ.removeAllEZElements();
	}
}
