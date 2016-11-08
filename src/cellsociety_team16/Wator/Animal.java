package cellsociety_team16.Wator;

public class Animal {
    private final int BREED; /* Total days needed to breed */
    private int myDaysLeftToBreed; /* Count the days before breeding */

    public Animal(int breedDays) {
        BREED = breedDays;
        resetDaysToBreed();
    }

    private void resetDaysToBreed() {
    	myDaysLeftToBreed = BREED;
    }

    public final boolean isReadyToBreed() {
    	return myDaysLeftToBreed == 0;
    }
    
    /**
     * Breed, mark myDaysLeftToBreed as -1.
     */
    public final void breed() {
    	myDaysLeftToBreed = -1;
    }
    
    public void step() {
    	if (myDaysLeftToBreed < 0) {
    		/* breeded */
    		resetDaysToBreed();
    	} else if (myDaysLeftToBreed > 0) {
    		/* another day passed */
    		myDaysLeftToBreed -= 1;
    	} else {
    		/* equals 0, ready to breed */
    	}
    }
}
