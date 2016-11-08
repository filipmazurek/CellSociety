package cellsociety_team16.Config;

public class LifeConfig {
	private int myNumAlive = 100;
	
	public int getNumAlive() {
		return myNumAlive;
	}
	
	public void setNumAlive(int numAlive) throws IllegalArgumentException {
		if (numAlive >= 0) {
			myNumAlive = numAlive;
		} else {
			throw new IllegalArgumentException("Number out of bounds: Number of living things must be more than 0."
					+ " (Life)");
		}
	}
}
