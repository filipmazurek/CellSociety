package cellsociety_team16.Life;

import java.util.List;

import cellsociety_team16.Cell.BaseCell;
import cellsociety_team16.Cell.Cell;
import cellsociety_team16.Cell.CellState;

class LifeCell extends BaseCell {
	

	protected LifeCell(CellState state) {
		super(state);
	}

	@Override
	public void step(List<Cell> neighbors) {
		int lifeCount = 0;
		for (Cell cell : neighbors) {
			if (cell.getState().equals(LifeState.ALIVE)) {
				lifeCount++;
			}
		}
		if (getState().equals(LifeState.DEAD)) {
			/* currently dead */
			if (lifeCount == 3) {
				/* Exactly 3 neighbors, become alive */ 
				setNextState(LifeState.ALIVE);
			} else {
				setNextState(LifeState.DEAD);
			}
		} else {
			/* currently alive */
			if (lifeCount < 2 || lifeCount > 3) {
				/* Fewer than 2 or more than 3, become dead */
				setNextState(LifeState.DEAD);
			} else {
				setNextState(LifeState.ALIVE);
			}
		}
	}
}
