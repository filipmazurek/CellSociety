package cellsociety_team16.Util;

public class Pos {
	public int r;
	public int c;
	
	public static Direction[] DIRS;
	public static Direction[] HEX_ODD_DIRS;
	public static Direction[] HEX_EVEN_DIRS;
	
	static {
		DIRS = Direction.values();
		HEX_ODD_DIRS = new Direction[]{
			Direction.LEFT,
			Direction.UP,
			Direction.UP_RIGHT,
			Direction.RIGHT,
			Direction.DOWN_RIGHT,
			Direction.DOWN
		};
		HEX_EVEN_DIRS = new Direction[]{
			Direction.LEFT,
			Direction.UP_LEFT,
			Direction.UP,
			Direction.RIGHT,
			Direction.DOWN,
			Direction.DOWN_LEFT
		};
		
	}
	
	public static enum Direction {
		LEFT, UP_LEFT, UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT
	}
	
	/**
	 * Using square model, get three adjacent positions of the given directions.
	 */
	public static Pos[] getThreePosInDirectionSquarelly(Pos pos, Direction dir) {
		final int len = DIRS.length;
		for (int i = 0; i < len; i++) {
			if (DIRS[i] == dir) {
				return new Pos[]{
					getPosInDirection(pos, DIRS[(i + len - 1) % len]),
					getPosInDirection(pos, dir),
					getPosInDirection(pos, DIRS[(i + 1) % len])
				};
			}
		}
		throw new IllegalArgumentException("Direction is not valid.");
	}
	
	/**
	 * Using hexagon model, get three adjacent positions of the given directions.
	 */
	public static Pos[] getThreePosInDirectionHexagonally(Pos pos, Direction dir) {
		Direction[] hexDirs;
		Direction actualDir = dir;
		if (pos.r % 2 == 0) {
			hexDirs = HEX_EVEN_DIRS;
			if (dir == Direction.UP_RIGHT) actualDir = Direction.UP;
			else if (dir == Direction.DOWN_RIGHT) actualDir = Direction.DOWN;
		} else {
			hexDirs = HEX_ODD_DIRS;
			if (dir == Direction.UP_LEFT) actualDir = Direction.UP;
			else if (dir == Direction.DOWN_LEFT) actualDir = Direction.DOWN;
		}
		final int len = hexDirs.length;
		for (int i = 0; i < len; i++) {
			if (hexDirs[i] == actualDir) {
				return new Pos[]{
					getPosInDirection(pos, hexDirs[(i + len - 1) % len]),
					getPosInDirection(pos, actualDir),
					getPosInDirection(pos, hexDirs[(i + 1) % len])
				};
			}
		}
		throw new IllegalArgumentException("Direction is not valid.");
	}
	
	private static Direction getRelativeDirectionWithDeltas(int dr, int dc) {
		if (dr == -1 && dc == -1) return Direction.UP_LEFT;
		if (dr == -1 && dc == 0) return Direction.UP;
		if (dr == -1 && dc == 1) return Direction.UP_RIGHT;
		if (dr == 0 && dc == -1) return Direction.LEFT;
		if (dr == 0 && dc == 1) return Direction.RIGHT;
		if (dr == 1 && dc == -1) return Direction.DOWN_LEFT;
		if (dr == 1 && dc == 0) return Direction.DOWN;
		if (dr == 1 && dc == 1) return Direction.DOWN_RIGHT;
		throw new IllegalArgumentException("No relative directions found.");
	}
	
	/**
	 * Get direction of PosB relative to posA
	 */
	public static Direction getRelativeDirection(Pos posA, Pos posB) {
		int dr = posB.r - posA.r;
		int dc = posB.c - posA.c;
		return getRelativeDirectionWithDeltas(dr, dc);
	}
	
	/**
	 * Get direction of PosB relative to posA with potential wrapping around.
	 */
	public static Direction getRelativeDirectionWithWrappingAround(
		Pos posA, Pos posB, int numRow, int numCol
	) {
		int dr = posB.r - posA.r;
		int dc = posB.c - posA.c;
		if (dr == numRow - 1) dr = -1;
		else if (dr == 1 - numRow) dr = 1;
		if (dc == numCol - 1) dc = -1;
		else if (dc == 1 - numCol) dc = 1;
		return getRelativeDirectionWithDeltas(dr, dc);
	}
	
	public static Pos getPosInDirection(Pos pos, Direction dir) {
		switch (dir) {
		case LEFT:
			return new Pos(pos.r, pos.c - 1);
		case RIGHT:
			return new Pos(pos.r, pos.c + 1);
		case UP:
			return new Pos(pos.r - 1, pos.c);
		case DOWN:
			return new Pos(pos.r + 1, pos.c);
		case UP_LEFT:
			return new Pos(pos.r - 1, pos.c - 1);
		case UP_RIGHT:
			return new Pos(pos.r - 1, pos.c + 1);
		case DOWN_LEFT:
			return new Pos(pos.r + 1, pos.c - 1);
		case DOWN_RIGHT:
			return new Pos(pos.r + 1, pos.c + 1);
		default:
			throw new IllegalArgumentException("Direction is not valid.");
		}
	}
	
	public Pos(int r, int c) {
		this.r = r;
		this.c = c;
	}
	
	@Override
	public String toString() {
		return "(" + r + ", " + c + ")";
	}
}
