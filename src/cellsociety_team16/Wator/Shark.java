package cellsociety_team16.Wator;

public class Shark extends Animal {

	private final int STARVE;
	private int myDaysLeftToDieStarving;
	
	public Shark(int breedDays, int starveDays) {
		super(breedDays);
		STARVE = starveDays;
		resetDaysToDieStarve();
	}
	
	private final void resetDaysToDieStarve() {
		myDaysLeftToDieStarving = STARVE;
    }
	
	public final void eat() {
		myDaysLeftToDieStarving = -1;
	}
	
	public final boolean isDeadStarving() {
		return myDaysLeftToDieStarving == 0;
	}
	
	@Override
	public void step() {
		super.step();
		if (myDaysLeftToDieStarving < 0) {
			/* ate */
			resetDaysToDieStarve();
		} else if (myDaysLeftToDieStarving > 0) {
			/* another day passed */
			myDaysLeftToDieStarving--;
		} else {
			/* well, it's dead and should be removed from the cell */
		}
	}
}
