// This entire file is part of my masterpiece.
// Jay Wang zw48
/*
 * This is one actual implementation in the grid hierarchy that extends the BaseGrid.
 */
package cellsociety_team16.Life;

import java.util.List;
import cellsociety_team16.Cell.Cell;
import cellsociety_team16.Enum.ShapeType;
import cellsociety_team16.Grid.BaseGrid;
import cellsociety_team16.Manager.DataManager;
import cellsociety_team16.Util.Pos;
import cellsociety_team16.Util.RandUtil;

/**
 * Grid model for Game of Life
 * @author Jay
 */
public class LifeGrid extends BaseGrid {

	private LifeCell[][] myCellMatrix;

	public LifeGrid(int numRow, int numCol) {
		super(numRow, numCol);
		myCellMatrix = new LifeCell[numRow][numCol];
		for (int i = 0; i < numRow; i++) {
			for (int j = 0; j < numCol; j++) {
				LifeCell newCell = new LifeCell(LifeState.DEAD);
				myCellMatrix[i][j] = newCell;
				putIntoPosMap(newCell, new Pos(i, j));
			}
		}
		STATE_PATTERN_MAP.put(LifeState.ALIVE, LifeState.DEAD);
		STATE_PATTERN_MAP.put(LifeState.DEAD, LifeState.ALIVE);
		reset();
	}
	
	@Override
	protected Cell[][] getCellMatrix() {
		return myCellMatrix;
	}

	@Override
	protected List<Cell> getNeighborsOf(Cell cell) {
		if (myShapeType == ShapeType.Hexagon) {
			return getSixNeighborsOf(cell);
		}
		return getEightNeighborsOf(cell);
	}

	@Override
	public void reset() {
		for (Cell[] cellRow : myCellMatrix) {
			for (Cell cell : cellRow) {
				cell.clearState();
				cell.setCurrentState(LifeState.DEAD);
			}
		}
		final int numAlive = DataManager.get().life().getNumAlive();
		for (int i = 0; i < numAlive; i++) {
			Pos randPos = RandUtil.generateRandomPos(NUM_ROW, NUM_COL);
			myCellMatrix[randPos.r][randPos.c].setCurrentState(LifeState.ALIVE);
		}
	}
	
}
