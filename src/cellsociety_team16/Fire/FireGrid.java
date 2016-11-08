package cellsociety_team16.Fire;

import java.util.List;
import cellsociety_team16.Cell.Cell;
import cellsociety_team16.Enum.ShapeType;
import cellsociety_team16.Grid.BaseGrid;
import cellsociety_team16.Manager.DataManager;
import cellsociety_team16.Util.Pos;
import cellsociety_team16.Util.RandUtil;

public class FireGrid extends BaseGrid {

	private FireCell[][] myCellMatrix;

	public FireGrid(int numRow, int numCol) {
		super(numRow, numCol);
		myCellMatrix = new FireCell[numRow][numCol];
		for (int i = 0; i < numRow; i++) {
			for (int j = 0; j < numCol; j++) {
				FireCell newCell = new FireCell(FireState.TREE);
				myCellMatrix[i][j] = newCell;
				putIntoPosMap(newCell, new Pos(i, j));
			}
		}
		STATE_PATTERN_MAP.put(FireState.EMPTY, FireState.TREE);
		STATE_PATTERN_MAP.put(FireState.TREE, FireState.FIRE);
		STATE_PATTERN_MAP.put(FireState.FIRE, FireState.EMPTY);
		reset();
	}
	
	@Override
	public void reset() {
		// Initialize the empty boundary
		for (int i = 0; i < NUM_ROW; i++) {
			myCellMatrix[i][0].setCurrentState(FireState.EMPTY);
			myCellMatrix[i][NUM_COL - 1].setCurrentState(FireState.EMPTY);
		}
		for (int j = 0; j < NUM_COL; j++) {
			myCellMatrix[0][j].setCurrentState(FireState.EMPTY);
			myCellMatrix[NUM_ROW-1][j].setCurrentState(FireState.EMPTY);
		}
		
		for (int i = 1; i < NUM_ROW - 1; i++) {
			for (int j = 1; j < NUM_COL - 1; j++) {
				myCellMatrix[i][j].setCurrentState(FireState.TREE);
			}
		}
		final int numBurn = DataManager.get().fire().getNumBurn();
		for (int i = 0; i < numBurn; i++) {
			Pos randPos = RandUtil.generateRandomPos(1, NUM_ROW - 1, 1, NUM_COL - 1);
			myCellMatrix[randPos.r][randPos.c].setCurrentState(FireState.FIRE);
		}
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
		return getFourNeighborsOf(cell);
	}

}
