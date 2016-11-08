package cellsociety_team16.Wator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cellsociety_team16.Cell.Cell;
import cellsociety_team16.Cell.CellState;
import cellsociety_team16.Cell.UnknownCellStateException;
import cellsociety_team16.Enum.ShapeType;
import cellsociety_team16.Grid.BaseGrid;
import cellsociety_team16.Manager.DataManager;
import cellsociety_team16.Util.Pos;

public class WatorGrid extends BaseGrid {
	
	private WatorCell[][] myCellMatrix;
	
	public WatorGrid(int numRow, int numCol) {
		super(numRow, numCol);
		myCellMatrix = new WatorCell[numRow][numCol];
		STATE_PATTERN_MAP.put(WatorState.EMPTY, WatorState.FISH);
		STATE_PATTERN_MAP.put(WatorState.FISH, WatorState.SHARK);
		STATE_PATTERN_MAP.put(WatorState.SHARK, WatorState.EMPTY);
		reset();
	}
	
	@Override
	public void reset() {
		final int percentEmpty = DataManager.get().wator().getPercentEmpty();
		final int percentShark = DataManager.get().wator().getPercentShark();
		assert(
			0 <= percentEmpty
			&& percentEmpty < 100
			&& 0 <= percentShark
			&& percentShark <= 100
		);
		List<WatorCell> cellList = new ArrayList<>();
		
		/* Add empty cells */
		int numEmpty = (int) (percentEmpty / 100.0 * NUM_CELL);
		for (int i = 0; i < numEmpty; i++) {
			cellList.add(new WatorCell(WatorState.EMPTY, null));
		}
		
		/* Add shark cells */
		int numShark = (int) (percentShark / 100.0 * (NUM_CELL - numEmpty));
		for (int i = 0; i < numShark; i++) {
			cellList.add(new WatorCell(
				WatorState.SHARK,
				getNewShark()
			));
		}
		
		/* Add fish cells */
		int numFish = NUM_CELL - numEmpty - numShark; 
		for (int i = 0; i < numFish; i++) {
			cellList.add(new WatorCell(
				WatorState.FISH,
				getNewFish()
			));
		}
		
		/* Add to cellMatrix */
		Collections.shuffle(cellList);
		clearPosMap();
		int pointer = -1;
		for (int i = 0; i < NUM_ROW; i++) {
			for (int j = 0; j < NUM_COL; j++) {
				WatorCell newCell = cellList.get(++pointer);
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
		return getFourNeighborsOf(cell);
	}
	
	@Override
	protected void doStep() {
		/* Sharks act first */
		actWithType(ActionType.Shark);
		/* Then fishes act */
		actWithType(ActionType.Fish);
		/* At last wrap up the empty cells */
		actWithType(ActionType.Empty);
	}
	
	enum ActionType {
		Shark, Fish, Empty
	}
	
	private void actWithType(ActionType actionType) {
		for (int i = 0; i < NUM_ROW; i++) {
			for (int j = 0; j < NUM_COL; j++) {
				WatorCell cell = myCellMatrix[i][j];
				switch (actionType) {
				case Shark:
					if (cell.getState().equals(WatorState.SHARK)) {
						cell.step(getNeighborsOf(cell));
					}
					break;
				case Fish:
					if (cell.getState().equals(WatorState.FISH)) {
						cell.step(getNeighborsOf(cell));
					}
					break;
				case Empty:
					if (cell.getState().equals(WatorState.EMPTY)) {
						cell.step(null);
					}
				}
			}
		}
	}
	
	@Override
	public void changeStateOf(Pos pos) {
		super.changeStateOf(pos);
		WatorCell cell = myCellMatrix[pos.r][pos.c];
		CellState curState = cell.getState();
		if (curState.equals(WatorState.EMPTY)) {
			cell.setAnimal(null);
		} else if (curState.equals(WatorState.FISH)) {
			cell.setAnimal(getNewFish());
		} else if (curState.equals(WatorState.SHARK)) {
			cell.setAnimal(getNewShark());
		} else {
			throw new UnknownCellStateException("WatorCell", curState);
		}
	}
	
	private Fish getNewFish() {
		int fishBreedDays = DataManager.get().wator().getFishBreed();
		return new Fish(fishBreedDays);
	}
	
	private Shark getNewShark() {
		int sharkBreedDays = DataManager.get().wator().getSharkBreed();
		int sharkStarveDays = DataManager.get().wator().getSharkStarve();
		return new Shark(sharkBreedDays, sharkStarveDays);
	}
}
