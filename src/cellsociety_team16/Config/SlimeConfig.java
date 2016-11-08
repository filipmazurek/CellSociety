package cellsociety_team16.Config;

public class SlimeConfig {

	private int mySniffThreshold = 10;
	private int myNumAmoeba = 1;
	private int myReleaseValue = 2;

	public int getSniffThreshold() {
		return mySniffThreshold;
	}

	public void setSniffThreshold(int sniffThreshold) throws IllegalArgumentException {
		if (sniffThreshold > 0) {
			mySniffThreshold = sniffThreshold;
		} else {
			throw new IllegalArgumentException("Number out of range: Sniff threshold must be above 0. (Slime)");
		}
	}

	public int getNumAmoeba() {
		return myNumAmoeba;
	}

	public void setNumAmoeba(int numAmoeba) throws IllegalArgumentException {
		if (numAmoeba > 0) {
			myNumAmoeba = numAmoeba;
		} else {
			throw new IllegalArgumentException("Number out of range: Number of amoeba out of preferred range (1-50). (Slime)");
		}
	}

	public int getReleaseValue() {
		return myReleaseValue;
	}

	public void setReleaseValue(int releaseValue) throws IllegalArgumentException {
		if (releaseValue > 0) {
			myReleaseValue = releaseValue;
		} else {
			throw new IllegalArgumentException("Number out of range: Release threshold must be above 0. (Slime)");
		}
	}

}
