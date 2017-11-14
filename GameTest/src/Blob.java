import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

public class Blob {

	private static final int MAX_BLOBS = 3; // total amount of blobs
	//private static final int MAX_BLOB_MASS;
	private ArrayList<Blob> blobArray = new ArrayList<Blob>(); // create an array to
														// store the blobs
	static int initialX; // variable to track x value of blob center
	static int initialY; // variable to track y value of blob center
	static int blobX; // variable for x movement
	static int blobY; // variable for y movement
	static int r = 10; // radius of blob
	static Random randomNumber = new Random(); // random number generator to
												// randomize blob movement
	static int mass;

	// constructor of a blob
	// create a blob and an array of blobs
	public Blob(int x, int y, int rad) {
		blobX = x;
		blobY = y;
		r = rad;
	}

	// create a function of blob to set up blob
	public void blob() {
		initialX = randomNumber.nextInt(1000); // work on how to get spawn
												// anywhere but where player is
		initialY = randomNumber.nextInt(400);
		blobArray.add(1, Blob());
		blobArray.add(2, Blob());
		blobArray.add(3, Blob());
		mass = r * 2;
	}

	// have blobs move and chase player
	void blobMvmt() {
		// leave stationary for now
		// work on AI for final submission
	}

	void blobGrowth() {
		Dots(d).getXCenter;
		Dots(d).getYCenter;
			// blob grows as it collides with the dots
		for(int i=0; i<MAX_BLOBS; i++){
		
			// blob grows if it eats the player
			if(Blob[i].isPointInElemetn(Player.x,Player.y)){//blob is bigger than player
				if(Blob.mass>Player.mass){
					Blob[i].mass += Player.mass				//player is eaten, blob gains mass of player
				}
				else{										
					if(Blob.mass<Player.mass){				//blob is smaller than player
					Player.mass += Blob.mass;			//blob is eaten, player gains mass of blob
					}
				}
				
			}

		}
		
	}
	
}
