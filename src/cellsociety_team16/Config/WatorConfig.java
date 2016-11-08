package cellsociety_team16.Config;

public class WatorConfig {
	
	private int myPercentEmpty = 20;
	private int myPercentShark = 10;
	private int myFishBreed = 4;
	private int mySharkBreed = 20;
	private int mySharkStarve = 5;

	public int getPercentEmpty() {
		return myPercentEmpty;
	}
	public void setPercentEmpty(int percentEmpty) throws IllegalArgumentException {
		if (percentEmpty < 100 && percentEmpty > 0) {
		myPercentEmpty = percentEmpty;
		} else {
			throw new IllegalArgumentException("Number out of range: Percentage of empty squares is out of range (1-99)."
					+ " (Wator)");
		}
	}
	public int getPercentShark() {
		return myPercentShark;
	}
	public void setPercentShark(int percentShark) {
		if (percentShark < 100 && percentShark > 0) {
			myPercentShark = percentShark;
		} else { 
			throw new IllegalArgumentException("Number out of range: Percentage of sharks is out of range (1-99). (Wator)");
		}
	}
	public int getFishBreed() {
		return myFishBreed;
	}
	public void setFishBreed(int fishBreed) throws IllegalArgumentException {
		if (fishBreed >0) {
			myFishBreed = fishBreed;
		} else {
			throw new IllegalArgumentException("Number out of range: Fish breeding days must be above 0. (Wator)");
		}
	}
	public int getSharkBreed() {
		return mySharkBreed;
	}
	public void setSharkBreed(int sharkBreed) throws IllegalArgumentException {
		if (sharkBreed > 0) {
			mySharkBreed = sharkBreed;
		} else {
			throw new IllegalArgumentException("Number out of range: Shark breeding days must be above 0. (Wator)");
		}
	}
	public int getSharkStarve() {
		return mySharkStarve;
	}
	public void setSharkStarve(int sharkStarve) throws IllegalArgumentException {
		if (sharkStarve >0) {
			mySharkStarve = sharkStarve;
		} else {
			throw new IllegalArgumentException("Number out of range: Shark starving days must be above 0. (Wator)");
		}
	}
}
