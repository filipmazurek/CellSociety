// This entire file is part of my masterpiece.
// Jay Wang zw48
/* 
 * Following are the reasons why I believe this should be a masterpiece:
 * This serves as an abstract base(super) class for the Grid model.
 * It implements the Grid interface, and provides several protected methods to be used
 * by sub classes, and defines several abstract methods that need to be implemented
 * as well by sub classes. The abstract methods differ from the methods in Grid interface
 * in that, these methods are essential for a grid to work correctly, but should NOT be exposed to
 * the outside world. Thus, a Grid interface along with this BaseGrid gives a foundation of how 
 * a grid looks like to the outside, and how it should be done internally.
 */
package cellsociety_team16.Grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cellsociety_team16.Cell.Cell;
import cellsociety_team16.Cell.CellState;
import cellsociety_team16.Enum.EdgeType;
import cellsociety_team16.Enum.ShapeType;
import cellsociety_team16.Util.Pos;
import cellsociety_team16.Util.Pos.Direction;

/**
 * A base class of Grid
 * @author Jay
 *
 */
public abstract class BaseGrid implements Grid {
	
	private static final int MIN_NUM_ROW = 10;
	private static final int MIN_NUM_COL = 10;
	private static final int MAX_NUM_ROW = 100;
	private static final int MAX_NUM_COL = 100;
	protected final Map<CellState, CellState> STATE_PATTERN_MAP;
	
	private Map<Cell, Pos> myPosMap;
	protected final int NUM_ROW;
	protected final int NUM_COL;
	protected final int NUM_CELL;
	
	protected ShapeType myShapeType = ShapeType.Square;
	protected EdgeType myEdgeType = EdgeType.finite;
	
	/**
	 * Constructs the grid with row number and col number.
	 * @param numRow number of rows.
	 * @param numCol number of cols.
	 */
	public BaseGrid(int numRow, int numCol) {
		myPosMap = new HashMap<>();
		if (
			numRow < MIN_NUM_ROW
			|| numRow > MAX_NUM_ROW
			|| numCol < MIN_NUM_COL
			|| numCol > MAX_NUM_COL
		) {
			throw new IllegalArgumentException("Invalid dimension");
		}
		NUM_ROW = numRow;
		NUM_COL = numCol;
		NUM_CELL = numRow * numCol;
		STATE_PATTERN_MAP = new HashMap<>();
	}
	
	@Override
	public void setShapeType(ShapeType shape) {
		myShapeType = shape;
	}
	
	@Override
	public void setEdgeType(EdgeType edge) {
		myEdgeType = edge;
	}
	
	@Override
	public CellState[][] toStateArray() {
		Cell[][] cellMatrix = getCellMatrix();
		CellState[][] stateMatrix = new CellState[cellMatrix.length][];
		for (int i = 0; i < cellMatrix.length; i++) {
			Cell[] cellRow = cellMatrix[i];
			/* Map the 1-D cellRow array to a 1-D state array. 
			 * Java 8 new feature 
			 */
			stateMatrix[i] = Arrays.stream(cellRow)
				.map(cell -> cell.getState())
				.toArray(size -> new CellState[cellRow.length]);
		}
		return stateMatrix;
	}
	
	@Override
	public final void step() {
		doStep();
		evolveAll();
	}
	
	/**
	 * Provides the most generic implementation of step().
	 * Different simulations should override it as needed.
	 */
	protected void doStep() {
		for (Cell[] cellRow : getCellMatrix()) {
			for (Cell cell : cellRow) {
				cell.step(getNeighborsOf(cell));
			}
		}
	}
	
	/**
	 * Iterate through the cellMatrix and evolve them.
	 * A.k.a, set the cell's currentState to the already-computed nextState.
	 * This method should always be called at the end of the step();
	 */
	protected void evolveAll() {
		for (Cell[] cellRow : getCellMatrix()) {
			for (Cell cell : cellRow) {
				cell.evolve();
			}
		}
	}
	
	/**
	 * Get the 2D Matrix of Cell.
	 * @return cellMatrix.
	 */
	protected abstract Cell[][] getCellMatrix();

	
	/**
	 * Get the neighbor cells of a cell. The concept of 'neighbor'
	 * should always be defined by the concrete grid subclass.
	 * @param cell to find the neighbors of.
	 * @return A list of its neighbor cells.
	 */
	protected abstract List<Cell> getNeighborsOf(Cell cell);
	
	/**
	 * Check whether a position is valid (within the boundaries) in the grid.
	 * @param pos position.
	 * @return valid (true) or not.
	 */
	protected boolean isPosValidInGrid(Pos pos) {
		Cell[][] cellMatrix = getCellMatrix();
		return !(
			pos.r < 0 
			|| pos.r >= cellMatrix.length 
			|| pos.c < 0
			|| pos.c >= cellMatrix[pos.r].length
		);
	}
	
	protected Pos getWrappedAroundPos(Pos pos) {
		if (!isPosValidInGrid(pos)) {
			if (pos.r < 0) {
				pos.r += NUM_ROW;
			}
			if (pos.r >= NUM_ROW) {
				pos.r -= NUM_ROW;
			}
			if (pos.c < 0) {
				pos.c += NUM_COL;
			}
			if (pos.c >= NUM_COL) {
				pos.c -= NUM_COL;
			}
		}
		return pos;
	}
	
	/**
	 * Associates a cell with a Position. This provides a 
	 * convenient way to look up a cell's coordinate within grid.
	 * Every cell should be associated with a position, failing to do
	 * so may cause the failure of finding neighbors of cells.
	 * @param cell cell.
	 * @param pos position of the cell.
	 */
	protected final void putIntoPosMap(Cell cell, Pos pos) {
		if (!isPosValidInGrid(pos)) {
			throw new IllegalArgumentException("Position is not valid.");
		}
		myPosMap.put(cell, pos);
	}
	
	/**
	 * Get the positions of a cell within the grid.
	 * If a cell is not associated with a pos, an exception will be thrown.
	 * @param cell cell.
	 * @return position of the cell.
	 */
	protected final Pos getPosOf(Cell cell) {
		Pos pos = myPosMap.get(cell);
		if (pos == null) {
			throw new IllegalArgumentException("Cell is not associated with a position");
		}
		return pos;
	}
	
	/**
	 * Clear the position map.
	 */
	protected final void clearPosMap() {
		myPosMap.clear();
	}
	
	private List<Cell> getNeighborsWithDirections(
			Cell cell,
			List<Direction> directions
		) {
			Pos pos = getPosOf(cell);
			List<Cell> neighbors = new ArrayList<>();
			for (Pos.Direction dir : directions) {
				Pos neighborPos = Pos.getPosInDirection(pos, dir);
				if (myEdgeType == EdgeType.finite) {
					/* finite */
					if (isPosValidInGrid(neighborPos)) {
						neighbors.add(getCellMatrix()[neighborPos.r][neighborPos.c]);
					}
				} else {
					/* toroidal */
					neighborPos = getWrappedAroundPos(neighborPos);
					neighbors.add(getCellMatrix()[neighborPos.r][neighborPos.c]);
				}
			}
			return neighbors;
		}
		
	/**
	 * Get the 4 neighbors directly above, below, left to and right to the cell,
	 * if there is any.
	 * @param cell the cell to find the neighbors of.
	 * @return A list of neighbor cells.
	 */
	protected final List<Cell> getFourNeighborsOf(Cell cell) {
		List<Direction> fourDirs = Arrays.asList(
			Direction.LEFT,
			Direction.UP,
			Direction.RIGHT,
			Direction.DOWN
		);
		return getNeighborsWithDirections(cell, fourDirs);
	}
		
	/**
	 * Get the 8 neighbors directly surrounding the cell,
	 * if there is any.
	 * @param cell the cell to find the neighbors of.
	 * @return A list of neighbor cells.
	 */
	protected final List<Cell> getEightNeighborsOf(Cell cell) {
		List<Direction> eightDirs = Arrays.asList(
			Direction.LEFT,
			Direction.UP_LEFT,
			Direction.UP,
			Direction.UP_RIGHT,
			Direction.RIGHT,
			Direction.DOWN_RIGHT,
			Direction.DOWN,
			Direction.DOWN_LEFT
		);
		return getNeighborsWithDirections(cell, eightDirs);
	}
	
	/**
	 * Get the 6 neighbors directly surrounding the cell in hexagonal model,
	 * if there is any.
	 * @param cell the cell to find the neighbors of.
	 * @return A list of neighbor cells.
	 */
	protected final List<Cell> getSixNeighborsOf(Cell cell) {
		List<Direction> sixDirs = new ArrayList<Direction>();
		sixDirs.add(Direction.LEFT);
		sixDirs.add(Direction.UP);
		sixDirs.add(Direction.RIGHT);
		sixDirs.add(Direction.DOWN);
		if (getPosOf(cell).r % 2 == 0) {
			sixDirs.add(Direction.UP_LEFT);
			sixDirs.add(Direction.DOWN_LEFT);
		} else {
			sixDirs.add(Direction.UP_RIGHT);
			sixDirs.add(Direction.DOWN_RIGHT);
		}
		return getNeighborsWithDirections(cell, sixDirs);
	}
	
	@Override
	public void changeStateOf(Pos pos) {
		Cell cell = getCellMatrix()[pos.r][pos.c];
		cell.setCurrentState(STATE_PATTERN_MAP.get(cell.getState()));
	}
	
}
