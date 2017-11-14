import java.awt.Color;
import java.util.Random;

public class Menu {
	private final int SCREEN_WIDTH, SCREEN_HEIGHT;
	private final int halfScreenWidth;
	private final int halfScreenHeight;
	private final int circDiameter = 200;
	private final int horizontalGap = 100;

	//Initial Phase Elements
	private final int MAX_BACKGROUND_CIRCS = 40;
	private final int BORDER = 8;
	private EZText backText, title, titleShadow, playButtonText, howToPlayButtonText, creditsText, optionsText;
	private EZRectangle backButton, playButton, howToPlayButton, creditsButton, optionsButton;
	private EZRectangle backOutline, playButtonOutline, howToPlayButtonOutline, creditsOutline, optionsOutline;
	private EZCircle[] backgroundCircs = new EZCircle[MAX_BACKGROUND_CIRCS];

	//Initial Phase Branches Booleans
	private boolean initialPhaseBranchHowToPlay = false;
	private boolean initialPhaseBranchCredits = false;
	private boolean initialPhaseBranchOptions = false;

	//Initial Phase - How To Play Branch Elements

	//Initial Phase - Credits Branch Elements
	private EZRectangle creditsScreen, creditsScreenOutline;
	private EZText creditsScreenTitle;

	//Initial Phase - Options Branch Elements

	//First Phase Elements
	public EZText enterUsername, enterUsernameShadow;

	//Second Phase Elements
	private EZCircle red, orange, yellow, green, blue, purple, pink;
	private Color cRed, cOrange, cYellow, cGreen, cBlue, cPurple, cPink;
	private EZText chooseColor, greeting;

	//Phase Booleans
	public boolean firstPhase = false;
	public boolean secondPhase = false;
	public boolean thirdPhase = false;

	/*
	 * Initial Phase: Overview Screen
	 * First Phase: Telling the user to enter a user name
	 * Second Phase: Telling the user to pick a color
	 * Third Phase: Run the game
	 */
	public Menu(int SCREEN_WIDTH, int SCREEN_HEIGHT) {
		this.SCREEN_WIDTH = SCREEN_WIDTH;
		this.SCREEN_HEIGHT = SCREEN_HEIGHT;
		this.halfScreenWidth = this.SCREEN_WIDTH / 2;
		this.halfScreenHeight = this.SCREEN_HEIGHT / 2;

		drawInitialPhase();
	}

	public void drawReturnToMenu() {
		backOutline = EZ.addRectangle(200, 200, 250 + BORDER, 100 + BORDER, Color.black, true);
		backButton = EZ.addRectangle(200, 200, 250, 100, Color.green, true);
		backText = EZ.addText(backButton.getXCenter(), backButton.getYCenter(), "Back", Color.black, 30);

		returnToMenuInteract();
	}

	public void removeReturnToMenu() {
		EZ.removeEZElement(backOutline);
		EZ.removeEZElement(backButton);
		EZ.removeEZElement(backText);
	}

	public void returnToMenuInteract() {
		while(true) {
			EZ.refreshScreen();
			int x = EZInteraction.getXMouse();
			int y = EZInteraction.getYMouse();

			if(EZInteraction.wasMouseLeftButtonReleased()) {
				if(backButton.isPointInElement(x, y)) {
					removeReturnToMenu();
					nukeInitialPhaseBranchElements();
					showInitialPhase();
					break;
				}
			}
		}
	}

	/**
	 * Removes all elements of the branches in initial phase
	 */
	public void nukeInitialPhaseBranchElements() {
		//initialPhaseBranchHowToPlay Elements

		//initialPhaseBranchCredits Elements
		EZ.removeEZElement(creditsScreen);
		EZ.removeEZElement(creditsScreenOutline);
		EZ.removeEZElement(creditsScreenTitle);

		//initialPhaseBranchOptions Elements

	}

	public void drawInitialPhase() {
		if(true) {
			Random rand = new Random();
			for(int i = 0; i < MAX_BACKGROUND_CIRCS; i++) {
				int x = rand.nextInt(SCREEN_WIDTH);
				int y = rand.nextInt(SCREEN_HEIGHT);
				int rad = rand.ints(30, 125).findFirst().getAsInt();

				int r = rand.nextInt(255);
				int g = rand.nextInt(255);
				int b = rand.nextInt(255);
				backgroundCircs[i] = EZ.addCircle(x, y, rad * 2, rad * 2 , new Color(r, g, b), true);
			}

			titleShadow = EZ.addText(halfScreenWidth + 10, halfScreenHeight - 390, "", Color.black, 100);
			title = EZ.addText(halfScreenWidth, halfScreenHeight - 400, "", Color.blue, 100);
			title.setFont("MarshyKate.ttf");
			titleShadow.setFont("MarshyKate.ttf");
			title.setMsg("bubble.ICS");
			titleShadow.setMsg("bubble.ICS");

			playButtonOutline =  EZ.addRectangle(200, SCREEN_HEIGHT - 600, 300 + BORDER, 100 + BORDER, Color.black, true);
			howToPlayButtonOutline = EZ.addRectangle(200, SCREEN_HEIGHT - 450, 300 + BORDER, 100 + BORDER, Color.black, true);
			creditsOutline = EZ.addRectangle(200, SCREEN_HEIGHT - 300, 300 + BORDER, 100 + BORDER, Color.black, true);
			optionsOutline = EZ.addRectangle(200, SCREEN_HEIGHT - 150, 300 + BORDER, 100 + BORDER, Color.black, true);

			playButton = EZ.addRectangle(200, SCREEN_HEIGHT - 600, 300, 100, Color.green, true);
			howToPlayButton = EZ.addRectangle(200, SCREEN_HEIGHT - 450, 300, 100, Color.green, true);
			creditsButton = EZ.addRectangle(200, SCREEN_HEIGHT - 300, 300, 100, Color.green, true);
			optionsButton = EZ.addRectangle(200, SCREEN_HEIGHT - 150, 300, 100, Color.green, true);

			playButtonText = EZ.addText(playButton.getXCenter(), playButton.getYCenter(), "Play", Color.black, 20);
			howToPlayButtonText = EZ.addText(howToPlayButton.getXCenter(), howToPlayButton.getYCenter(), "How to Play", Color.black, 20);
			creditsText = EZ.addText(creditsButton.getXCenter(), creditsButton.getYCenter(), "Credits", Color.black, 20);
			optionsText = EZ.addText(optionsButton.getXCenter(), optionsButton.getYCenter(), "Options", Color.black, 20);
		}
	}

	public void initialPhaseInteract() {
		while(true) {
			//We have to call EZ.refreshScreen() or else it will not pick up the following cursor events
			EZ.refreshScreen(); 
			int x = EZInteraction.getXMouse();
			int y = EZInteraction.getYMouse();

			if(EZInteraction.wasMouseLeftButtonPressed()) {
				if(playButton.isPointInElement(x, y)) {
					firstPhase = true;
					break;
				} else if(howToPlayButton.isPointInElement(x, y)) {
					initialPhaseBranchHowToPlay = true;
					drawInitialPhaseBranchHowToPlay();
					break;
				} else if(creditsButton.isPointInElement(x, y)) {
					initialPhaseBranchCredits = true;
					drawInitialPhaseBranchCredits();
					break;
				} else if(optionsButton.isPointInElement(x, y)) {
					initialPhaseBranchOptions = true;
					drawInitialPhaseBranchOptions();
					break;
				}
			}
		}
	}

	public void hideInitialPhase() {
		title.hide();
		playButton.hide();
		howToPlayButton.hide();
		creditsButton.hide();
		optionsButton.hide();

		titleShadow.hide();
		playButtonOutline.hide();
		howToPlayButtonOutline.hide();
		creditsOutline.hide();
		optionsOutline.hide();

		playButtonText.hide();
		howToPlayButtonText.hide();
		creditsText.hide();
		optionsText.hide();
	}

	public void showInitialPhase() {
		title.show();
		playButton.show();
		howToPlayButton.show();
		creditsButton.show();
		optionsButton.show();

		titleShadow.show();
		playButtonOutline.show();
		howToPlayButtonOutline.show();
		creditsOutline.show();
		optionsOutline.show();

		playButtonText.show();
		howToPlayButtonText.show();
		creditsText.show();
		optionsText.show();

		initialPhaseInteract();
	}

	public void removeInitialPhase() {
		EZ.removeEZElement(title);
		EZ.removeEZElement(playButton);
		EZ.removeEZElement(howToPlayButton);
		EZ.removeEZElement(creditsButton);
		EZ.removeEZElement(optionsButton);

		EZ.removeEZElement(titleShadow);
		EZ.removeEZElement(playButtonOutline);
		EZ.removeEZElement(howToPlayButtonOutline);
		EZ.removeEZElement(creditsOutline);
		EZ.removeEZElement(optionsOutline);

		EZ.removeEZElement(playButtonText);
		EZ.removeEZElement(howToPlayButtonText);
		EZ.removeEZElement(creditsText);
		EZ.removeEZElement(optionsText);
	}

	public void removeInitialPhaseBackground() {
		for(int i = 0; i < MAX_BACKGROUND_CIRCS; i++) {
			EZ.removeEZElement(backgroundCircs[i]);
		}
	}

	public void drawInitialPhaseBranchHowToPlay() {
		if(initialPhaseBranchHowToPlay) {
			hideInitialPhase();
			drawReturnToMenu();
		}
	}

	public void drawInitialPhaseBranchCredits() {
		if(initialPhaseBranchCredits) {
			hideInitialPhase();
			creditsScreenOutline = EZ.addRectangle(halfScreenWidth, halfScreenHeight, 800 + BORDER, 800 + BORDER, new Color(0, 0, 0, 120), true);
			creditsScreen = EZ.addRectangle(halfScreenWidth, halfScreenHeight, 800, 800, new Color(Color.green.getRed(), Color.green.getGreen(), Color.green.getBlue(), 200), true);

			creditsScreenTitle = EZ.addText(creditsScreen.getXCenter(), creditsScreen.getYCenter() - 350, "Credits", Color.black, 50);
			drawReturnToMenu();
		}
	}

	public void drawInitialPhaseBranchOptions() {
		if(initialPhaseBranchOptions) {
			hideInitialPhase();
			drawReturnToMenu();
		}
	}

	public void drawFirstPhase() {
		if(firstPhase) {
			removeInitialPhase();

			int shadowOffset = 4;
			enterUsernameShadow = EZ.addText(halfScreenWidth + shadowOffset, halfScreenHeight + shadowOffset, "", new Color(0, 0, 0, 150), 60);
			enterUsernameShadow.setFont("MarshyKate.ttf");
			enterUsernameShadow.setMsg("ENTER A USER NAME IN THE CONSOLE!");

			enterUsername = EZ.addText(halfScreenWidth, halfScreenHeight, "", Color.red, 60);
			enterUsername.setFont("MarshyKate.ttf");
			enterUsername.setMsg("ENTER A USER NAME IN THE CONSOLE!");
		}
	}

	public void removeFirstPhase() {
		EZ.removeEZElement(enterUsername);
		EZ.removeEZElement(enterUsernameShadow);
		removeInitialPhaseBackground();
	}

	public void initiateSecondPhase() {
		if(secondPhase) {
			EZ.removeEZElement(enterUsername);
			setColor();
			drawSecondPhase();
		}
	}


	public void setColor() {
		cRed = new Color(255, 0, 0);
		cOrange = new Color(255, 50, 0);
		cYellow = new Color(255, 255, 0);
		cGreen = new Color(0, 200, 0);
		cBlue = new Color(0, 100, 200);
		cPurple = new Color(100, 0, 200);
		cPink = new Color(255, 50, 200);
	}

	public void drawSecondPhase() {
		int halfHorizontalGap = horizontalGap / 2;
		int doubleCircDiameter = circDiameter * 2;

		chooseColor = EZ.addText(halfScreenWidth, 100, "Choose a color: ", Color.black, 170);

		red = EZ.addCircle(halfScreenWidth - doubleCircDiameter - horizontalGap, SCREEN_HEIGHT / 3, circDiameter, circDiameter, cRed, true);
		orange = EZ.addCircle(halfScreenWidth - circDiameter, SCREEN_HEIGHT / 3, circDiameter, circDiameter, cOrange, true);
		yellow = EZ.addCircle(halfScreenWidth + circDiameter, SCREEN_HEIGHT / 3, circDiameter, circDiameter, cYellow, true);
		green = EZ.addCircle(halfScreenWidth + doubleCircDiameter + horizontalGap, SCREEN_HEIGHT / 3, circDiameter, circDiameter, cGreen, true);

		blue = EZ.addCircle(halfScreenWidth - (doubleCircDiameter - halfHorizontalGap), halfScreenHeight, circDiameter, circDiameter, cBlue, true);
		purple = EZ.addCircle(halfScreenWidth, halfScreenHeight, circDiameter, circDiameter, cPurple, true);
		pink = EZ.addCircle(halfScreenWidth + (doubleCircDiameter - halfHorizontalGap), halfScreenHeight, circDiameter, circDiameter, cPink, true);

		greeting = EZ.addText(halfScreenWidth, halfScreenHeight + 300, "", Color.red, 50);
		greeting.setMsg("Hey, " + Game.playerName + "!");
	}

	public void secondPhaseInteract() {
		while(true) {
			int x = EZInteraction.getXMouse();
			int y = EZInteraction.getYMouse();

			if(EZInteraction.wasMouseLeftButtonPressed()) {
				if(red.isPointInElement(x, y)) {
					Player.c = cRed;
					thirdPhase = true;
					removeElements();
					break;
				} else if(orange.isPointInElement(x, y)) {
					Player.c = cOrange;
					thirdPhase = true;
					removeElements();
					break;
				} else if(yellow.isPointInElement(x, y)) {
					Player.c = cYellow;
					thirdPhase = true;
					removeElements();
					break;
				} else if(green.isPointInElement(x, y)) {
					Player.c = cGreen;
					thirdPhase = true;
					removeElements();
					break;
				} else if(blue.isPointInElement(x, y)) {
					Player.c = cBlue;
					thirdPhase = true;
					removeElements();
					break;
				} else if(purple.isPointInElement(x, y)) {
					Player.c = cPurple;
					thirdPhase = true;
					removeElements();
					break;
				} else if(pink.isPointInElement(x, y)) {
					Player.c = cPink;
					thirdPhase = true;
					removeElements();
					break;
				}
			}
			EZ.refreshScreen();
		}
	}

	public void removeElements() {
		EZ.removeEZElement(chooseColor);
		EZ.removeEZElement(red);
		EZ.removeEZElement(orange);
		EZ.removeEZElement(yellow);
		EZ.removeEZElement(green);
		EZ.removeEZElement(blue);
		EZ.removeEZElement(purple);
		EZ.removeEZElement(pink);
		EZ.removeEZElement(greeting);
	}
}
