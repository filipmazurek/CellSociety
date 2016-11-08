package cellsociety_team16.Config;

public class SegConfig {
	private int myPercentEmpty = 40;
	private int myPercentA = 40;
	private int myPercentSatisfy = 40;
	
	public int getPercentEmpty() {
		return myPercentEmpty;
	}
	public void setPercentEmpty(int percentEmpty) throws IllegalArgumentException {
		if (percentEmpty > 0 && percentEmpty < 100) {
			myPercentEmpty = percentEmpty;
		} else {
			throw new IllegalArgumentException("Number out of range: Percent empty is out of range "
					+ "(1-100). (Seg)");
		}
	}
	public int getPercentA() {
		return myPercentA;
	}
	public void setPercentA(int percentA) throws IllegalArgumentException {
		if (percentA > 0 && percentA <= 99) {
			myPercentA = percentA;
		} else {
			throw new IllegalArgumentException("Number out of range: Percent of agents is out of range (1-99). (Seg)");
		}
	}
	public int getPercentSatisfy() {
		return myPercentSatisfy;
	}
	public void setPercentSatisfy(int percentSatisfy) throws IllegalArgumentException {
		if (percentSatisfy >= 0 && percentSatisfy <= 100) {
			myPercentSatisfy = percentSatisfy;
		} else {
			throw new IllegalArgumentException("Number out of range: Percent satisfaction is out of range (0-100). (Seg)");
		}
	}
	
}
