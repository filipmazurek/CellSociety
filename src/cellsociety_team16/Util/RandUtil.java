package cellsociety_team16.Util;

import java.util.Random;

import cellsociety_team16.Util.Pos.Direction;

public class RandUtil {

	private static final Random myRand = new Random();
	private static final Direction[] dirsArr = Direction.values();
	public static boolean winWithPossibility(int possibility) {
		return myRand.nextInt(100) < possibility;
	}
	
	public static Pos generateRandomPos(int maxRow, int maxCol) {
		return new Pos(
			myRand.nextInt(maxRow),
			myRand.nextInt(maxCol)
		);
	}
	
	public static Pos generateRandomPos(int minRow, int maxRow, int minCol, int maxCol) {
		return new Pos(
			minRow + myRand.nextInt(maxRow - minRow),
			minCol + myRand.nextInt(maxCol - minCol)
		);
	}
	
	public static Direction generateRandomDir() {
		return dirsArr[nextInt(dirsArr.length)];
	}
	
	public static Direction getEitherAdjacentDirectionOf(Direction dir) {
		for (int i = 0; i < dirsArr.length; i++) {
			if (dirsArr[i] == dir) {
				if (nextInt(2) == 0) {
					return dirsArr[(i + 7) % 8];
				} else {
					return dirsArr[(i + 1) % 8];
				}
			}
		}
		return null;
	}
	
	public static int nextInt(int bound) {
		return myRand.nextInt(bound);
	}
	
}
