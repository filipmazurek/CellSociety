package cellsociety_team16.Config;

public class FireConfig {
	private int myNumBurn = 3;
	private int myProbCatch = 80;
	
	public int getNumBurn() {
		return myNumBurn;
	}
	public void setNumBurn(int numBurn) throws IllegalArgumentException {
		if (numBurn >= 0) {
			myNumBurn = numBurn;
		} else {
			throw new IllegalArgumentException("Number out of range: Number of burning trees must be above 0. (Fire)");
		}
	}
	public int getProbCatch() {
		return myProbCatch;
	}
	public void setProbCatch(int probCatch) throws IllegalArgumentException {
		if (probCatch <= 100 && probCatch >= 0) {
			myProbCatch = probCatch;
		} else {
			throw new IllegalArgumentException("Number out of range: Probability of catching fire is out of range (1-100). (Fire)");
		}
	}
}
