package cellsociety_team16.Seg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import cellsociety_team16.Cell.Cell;
import cellsociety_team16.Enum.ShapeType;
import cellsociety_team16.Grid.BaseGrid;
import cellsociety_team16.Manager.DataManager;
import cellsociety_team16.Util.Pos;

public class SegGrid extends BaseGrid {
	
	private SegCell[][] myCellMatrix;
	
	public SegGrid(int numRow, int numCol) {
		super(numRow, numCol);
		myCellMatrix = new SegCell[numRow][numCol];
		STATE_PATTERN_MAP.put(SegState.A, SegState.B);
		STATE_PATTERN_MAP.put(SegState.B, SegState.EMPTY);
		STATE_PATTERN_MAP.put(SegState.EMPTY, SegState.A);
		reset();
	}
	
	@Override
	public void reset() {
		final int percentEmpty = DataManager.get().seg().getPercentEmpty();
		final int percentA = DataManager.get().seg().getPercentA();
		assert(
			0 <= percentEmpty
			&& percentEmpty < 100
			&& 0 <= percentA
			&& percentA <= 100
		);
		List<SegCell> cellList = new ArrayList<>();
		
		/* Add empty cells */
		int numEmpty = (int) (percentEmpty / 100.0 * NUM_CELL);
		for (int i = 0; i < numEmpty; i++) {
			cellList.add(new SegCell(SegState.EMPTY));
		}
		
		/* Add A-type cells */
		int numA = (int) (percentA / 100.0 * (NUM_CELL - numEmpty));
		for (int i = 0; i < numA; i++) {
			cellList.add(new SegCell(SegState.A));
		}
		
		/* Add B-type cells */
		int numB = NUM_CELL - numEmpty - numA; 
		for (int i = 0; i < numB; i++) {
			cellList.add(new SegCell(SegState.B));
		}
		
		/* Add to cellMatrix */
		Collections.shuffle(cellList);
		clearPosMap();
		int pointer = -1;
		for (int i = 0; i < NUM_ROW; i++) {
			for (int j = 0; j < NUM_COL; j++) {
				SegCell newCell = cellList.get(++pointer);
				myCellMatrix[i][j] = newCell;
				putIntoPosMap(newCell, new Pos(i, j));
			}
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
		return getEightNeighborsOf(cell);
	}
	
	@Override
	protected void doStep() {
		Queue<SegCell> emptyCells = getShuffledEmptyCells();
		for (int i = 0; i < NUM_ROW; i++) {
			for (int j = 0; j < NUM_COL; j++) {
				SegCell cell = myCellMatrix[i][j];
				if (cell.getState().equals(SegState.EMPTY)) {
					/* Empty cell */
					if (cell.peepNextState() == null) {
						cell.setNextState(SegState.EMPTY);
					}
				} else {
					/* Agent cell */
					if (cell.isSatisfied(getNeighborsOf(cell))) {
						/* Satisfied, stays */
						cell.setNextState(cell.getState());
					} else {
						/* Unsatified, move to a random cell */
						SegCell moveToCell = emptyCells.poll();
						if (moveToCell != null) {
							/* Able to find an empty cell, move there */
							assert(
								moveToCell.peepNextState() == null
								|| moveToCell.peepNextState().equals(SegState.EMPTY)
							);
							moveToCell.setNextState(cell.getState());
							cell.setNextState(SegState.EMPTY);
						} else {
							/* Unable to find an empty cell, stay */
							cell.setNextState(cell.getState());
						}
					}
				}
			}
		}
	}
	
	private Queue<SegCell> getShuffledEmptyCells() {
		List<SegCell> emptyCellList = new ArrayList<>();
		for (int i = 0; i < NUM_ROW; i++) {
			for (int j = 0; j < NUM_COL; j++) {
				SegCell cell = myCellMatrix[i][j];
				if (cell.getState().equals(SegState.EMPTY)) {
					emptyCellList.add(cell);
				}
			}
		}
		Collections.shuffle(emptyCellList);
		return new LinkedList<>(emptyCellList);
	}
	
}
