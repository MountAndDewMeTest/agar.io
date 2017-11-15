import java.awt.Color;

public class Options {
	private final int SCREEN_WIDTH, SCREEN_HEIGHT;
	private final int halfScreenWidth, halfScreenHeight;
	private final int BORDER = 8;
	
	public EZRectangle optionsScreen, optionsScreenOutline;
	public EZText optionsScreenTitle;
	public EZText maxDots, maxBlobs, maxViruses, maxPlayerMass, maxBlobMass, dotMass;
	public EZImage up1, up2, up3, up4, up5, up6, down1, down2, down3, down4, down5, down6;
	public EZText maxDotsVal, maxBlobsVal, maxVirusesVal, maxPlayerMassVal, maxBlobMassVal, dotMassVal;
	
	public Options(int SCREEN_WIDTH, int SCREEN_HEIGHT) {
		this.SCREEN_WIDTH = SCREEN_WIDTH;
		this.SCREEN_HEIGHT = SCREEN_HEIGHT;
		this.halfScreenWidth = this.SCREEN_WIDTH / 2;
		this.halfScreenHeight = this.SCREEN_HEIGHT / 2;
	}
	
	//TODO: Default Button
	public void drawDefaultButton() {
		
		
		interactDefaultButton();
	}
	
	public void interactDefaultButton() {
		
	}
	
	public void drawOptions() {
		optionsScreenOutline = EZ.addRectangle(halfScreenWidth, halfScreenHeight, 1100 + BORDER, 900 + BORDER, new Color(0, 0, 0, 120), true);
		optionsScreen = EZ.addRectangle(halfScreenWidth, halfScreenHeight, 1100, 900, new Color(Color.green.getRed(), Color.green.getGreen(), Color.green.getBlue(), 200), true);
		optionsScreenTitle = EZ.addText(optionsScreen.getXCenter(), optionsScreen.getYCenter() - 400, "Options", Color.black, 50);
		
		int verticalGap = 100; //Vertical gap in between each option
		int fs = 30; //Font size
		Color c = Color.black;
		maxDots = EZ.addText(optionsScreenTitle.getXCenter() - 300, optionsScreenTitle.getYCenter() + 100, "Maximum Dots", c, fs);
		maxBlobs = EZ.addText(maxDots.getXCenter(), maxDots.getYCenter() + verticalGap, "Maximum Blobs", c, fs);
		maxViruses = EZ.addText(maxBlobs.getXCenter(), maxBlobs.getYCenter() + verticalGap, "Maximum Viruses", c, fs);
		maxPlayerMass = EZ.addText(maxViruses.getXCenter(), maxViruses.getYCenter() + verticalGap, "Maximum Player Mass", c, fs);
		maxBlobMass = EZ.addText(maxPlayerMass.getXCenter(), maxPlayerMass.getYCenter() + verticalGap, "Maximum Blob Mass", c, fs);
		dotMass = EZ.addText(maxBlobMass.getXCenter(), maxBlobMass.getYCenter() + verticalGap, "Dot Mass", c, fs);
		
		int leftJustified = optionsScreenTitle.getXCenter() + 50; //Line up all the down buttons 
		int horizontalGap = 350; //Horizontal gap in between the down and up buttons
		down1 = EZ.addImage("resources/buttons/down1.png", leftJustified, optionsScreenTitle.getYCenter() + 100);
		up1 = EZ.addImage("resources/buttons/up1.png", leftJustified + horizontalGap, optionsScreenTitle.getYCenter() + 100);
		down2 = EZ.addImage("resources/buttons/down2.png", leftJustified, down1.getYCenter() + verticalGap);
		up2 = EZ.addImage("resources/buttons/up2.png", leftJustified + horizontalGap, up1.getYCenter() + verticalGap);
		down3 = EZ.addImage("resources/buttons/down2.png", leftJustified, down2.getYCenter() + verticalGap);
		up3 = EZ.addImage("resources/buttons/up2.png", leftJustified + horizontalGap, up2.getYCenter() + verticalGap);
		down4 = EZ.addImage("resources/buttons/down3.png", leftJustified, down3.getYCenter() + verticalGap);
		up4 = EZ.addImage("resources/buttons/up4.png", leftJustified + horizontalGap, up3.getYCenter() + verticalGap);
		down5 = EZ.addImage("resources/buttons/down5.png", leftJustified, down4.getYCenter() + verticalGap);
		up5 = EZ.addImage("resources/buttons/up5.png", leftJustified + horizontalGap, up4.getYCenter() + verticalGap);
		down6 = EZ.addImage("resources/buttons/down6.png", leftJustified, down5.getYCenter() + verticalGap);
		up6 = EZ.addImage("resources/buttons/up6.png", leftJustified + horizontalGap, up5.getYCenter() + verticalGap);
		
		int between = (down1.getXCenter() + up1.getXCenter()) / 2;
		maxDotsVal = EZ.addText(between, down1.getYCenter(), "test", c, fs);
		maxBlobsVal = EZ.addText(between, down2.getYCenter(), "test2", c, fs);
		maxVirusesVal = EZ.addText(between, down3.getYCenter(), "test3", c, fs);
		maxPlayerMassVal = EZ.addText(between, down4.getYCenter(), "test4", c, fs);
		maxBlobMassVal = EZ.addText(between, down5.getYCenter(), "test5", c, fs);
		dotMassVal = EZ.addText(between, down6.getYCenter(), "test6", c, fs);
	}
	
	//NOTE: MAKE SURE YOU ADD A WAY TO BREAK THIS WHILE LOOP
	public void interactOptions() {
		
	}
}
