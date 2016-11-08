package cellsociety_team16.Seg;

import java.util.List;

import cellsociety_team16.Cell.BaseCell;
import cellsociety_team16.Cell.Cell;
import cellsociety_team16.Cell.CellState;
import cellsociety_team16.Manager.DataManager;

class SegCell extends BaseCell {

	protected SegCell(CellState state) {
		super(state);
	}

	@Override
	public void step(List<Cell> neighbors) {
		/* Handled in SegGrid */
	}
	
	public boolean isSatisfied(List<Cell> neighbors) {
		int t = DataManager.get().seg().getPercentSatisfy();
		int sameTypeCount = 0;
		for (Cell nbr : neighbors) {
			if (nbr.getState().equals(getState())) {
				sameTypeCount++;
			}
		}
		return ((sameTypeCount * 1.0 / neighbors.size() * 100) >= t * 1.0);
	}
	
}
