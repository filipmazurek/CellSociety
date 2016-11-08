package cellsociety_team16.Fire;

import java.util.List;

import cellsociety_team16.Cell.BaseCell;
import cellsociety_team16.Cell.Cell;
import cellsociety_team16.Cell.CellState;
import cellsociety_team16.Cell.UnknownCellStateException;
import cellsociety_team16.Manager.DataManager;
import cellsociety_team16.Util.RandUtil;

class FireCell extends BaseCell {

	protected FireCell(CellState state) {
		super(state);
	}

	@Override
	public void step(List<Cell> neighbors) {
		if (getState().equals(FireState.EMPTY)) {
			/* empty -> empty */
			setNextState(FireState.EMPTY);
		} else if (getState().equals(FireState.FIRE)) {
			/* fire -> empty */
			setNextState(FireState.EMPTY);
		} else if (getState().equals(FireState.TREE)){
			/* tree -> calculate next state based on whether neighbors are on fire */
			boolean neighorsOnFire = false;
			for (Cell cell : neighbors) {
				if (cell.getState().equals(FireState.FIRE)) {
					neighorsOnFire = true;
					break;
				}
			}
			if (neighorsOnFire) {
				/* Neighbors on fire, set on fire with probCatch */
				int probCatch = DataManager.get().fire().getProbCatch();
				setNextState(
					RandUtil.winWithPossibility(probCatch)
					? FireState.FIRE
					: FireState.TREE
				);
			} else {
				setNextState(FireState.TREE);
			}
		} else {
			/* Unknown state */
			throw new UnknownCellStateException("FireCell", getState());
		}
	}

}
