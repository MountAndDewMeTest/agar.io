import java.awt.Color;

public class Menu {
	public final int SCREEN_WIDTH, SCREEN_HEIGHT;
	public final int circDiameter = 200;
	public final int horizontalGap = 100;
	
	/*
	 * Initial Phase: Overview Screen
	 * First Phase: Telling the user to enter a user name
	 * Second Phase: Telling the user to pick a color
	 */
	
	public EZText title, titleShadow, playButtonText, howToPlayButtonText, creditsText;
	public EZRectangle playButton;
	public EZRectangle howToPlayButton;
	public EZRectangle credits;	
	
	public final boolean initialPhase = true;
	public boolean initialPhaseBranchHowToPlay = false;
	public boolean initialPhaseBranchCredits = false;
	
	public EZText enterUsername;
	
	public EZCircle red, orange, yellow, green, blue, purple, pink;
	public Color cRed, cOrange, cYellow, cGreen, cBlue, cPurple, cPink;
	public EZText msg;
	
	public boolean firstPhase = false;
	public boolean secondPhase = false;
	public boolean thirdPhase = false;
	
	public Menu(int SCREEN_WIDTH, int SCREEN_HEIGHT) {
		this.SCREEN_WIDTH = SCREEN_WIDTH;
		this.SCREEN_HEIGHT = SCREEN_HEIGHT;
		
		drawInitialPhase();
	}
	
	//Create Menu Object (Game.java) -> getInputName(Game.java) -> firstPhase = true -> setColor() and draw() -> secondPhase = true -> Game Starts
	public void drawInitialPhase() {
		if(initialPhase) {
			titleShadow = EZ.addText(SCREEN_WIDTH / 2 + 10, SCREEN_HEIGHT / 2 - 190, "", Color.black, 100);
			title = EZ.addText(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2 - 200, "", Color.blue, 100);
			title.setFont("MarshyKate.ttf");
			titleShadow.setFont("MarshyKate.ttf");
			title.setMsg("bubble.ICS");
			titleShadow.setMsg("bubble.ICS");
			
			playButton = EZ.addRectangle(200, SCREEN_HEIGHT - 500, 300, 100, Color.green, true);
			howToPlayButton = EZ.addRectangle(200, SCREEN_HEIGHT - 350, 300, 100, Color.green, true);
			credits = EZ.addRectangle(200, SCREEN_HEIGHT - 200, 300, 100, Color.green, true);
			
			playButtonText = EZ.addText(playButton.getXCenter(), playButton.getYCenter(), "Play", Color.black, 20);
			howToPlayButtonText = EZ.addText(howToPlayButton.getXCenter(), howToPlayButton.getYCenter(), "How to Play", Color.black, 20);
			creditsText = EZ.addText(credits.getXCenter(), credits.getYCenter(), "Credits", Color.black, 20);
		}
	}
	
	public void initialPhaseInteract() {
		while(true) {
			int x = EZInteraction.getXMouse();
			int y = EZInteraction.getYMouse();
			
			System.out.println("testONE");
			if(EZInteraction.wasMouseLeftButtonPressed()) {
				if(playButton.isPointInElement(x, y)) {
					firstPhase = true;
					break;
				} else if(howToPlayButton.isPointInElement(x, y)) {
					initialPhaseBranchHowToPlay = true;
					break;
				} else if(credits.isPointInElement(x, y)) {
					initialPhaseBranchCredits = true;
					break;
				}
			}
		}
	}
	
	public void removeInitialPhase() {
		EZ.removeEZElement(title);
		EZ.removeEZElement(titleShadow);
		EZ.removeEZElement(playButton);
		EZ.removeEZElement(howToPlayButton);
		EZ.removeEZElement(credits);
		EZ.removeEZElement(playButtonText);
		EZ.removeEZElement(howToPlayButtonText);
		EZ.removeEZElement(creditsText);
	}
	
	public void drawFirstPhase() {
		if(firstPhase) {
			removeInitialPhase();
			enterUsername = EZ.addText(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, "", Color.red, 60);
			enterUsername.setFont("MarshyKate.ttf");
			enterUsername.setMsg("ENTER A USERNAME IN THE CONSOLE!");
		}
	}
	
	public void removeFirstPhase() {
		EZ.removeEZElement(enterUsername);
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
		int halfScreenWidth = SCREEN_WIDTH / 2;
		int halfScreenHeight = SCREEN_HEIGHT / 2;
		int halfHorizontalGap = horizontalGap / 2;
		int doubleCircDiameter = circDiameter * 2;

		msg = EZ.addText(SCREEN_WIDTH / 2, 100, "Choose a color: ", Color.black, 170);

		red = EZ.addCircle(halfScreenWidth - doubleCircDiameter - horizontalGap, SCREEN_HEIGHT / 3, circDiameter, circDiameter, cRed, true);
		orange = EZ.addCircle(halfScreenWidth - circDiameter, SCREEN_HEIGHT / 3, circDiameter, circDiameter, cOrange, true);
		yellow = EZ.addCircle(halfScreenWidth + circDiameter, SCREEN_HEIGHT / 3, circDiameter, circDiameter, cYellow, true);
		green = EZ.addCircle(halfScreenWidth + doubleCircDiameter + horizontalGap, SCREEN_HEIGHT / 3, circDiameter, circDiameter, cGreen, true);

		blue = EZ.addCircle(halfScreenWidth - (doubleCircDiameter - halfHorizontalGap), halfScreenHeight, circDiameter, circDiameter, cBlue, true);
		purple = EZ.addCircle(halfScreenWidth, halfScreenHeight, circDiameter, circDiameter, cPurple, true);
		pink = EZ.addCircle(halfScreenWidth + (doubleCircDiameter - halfHorizontalGap), halfScreenHeight, circDiameter, circDiameter, cPink, true);
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
		EZ.removeEZElement(msg);
		EZ.removeEZElement(red);
		EZ.removeEZElement(orange);
		EZ.removeEZElement(yellow);
		EZ.removeEZElement(green);
		EZ.removeEZElement(blue);
		EZ.removeEZElement(purple);
		EZ.removeEZElement(pink);
	}
}
