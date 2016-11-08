package cellsociety_team16.Slime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cellsociety_team16.Cell.BaseCell;
import cellsociety_team16.Cell.Cell;
import cellsociety_team16.Cell.CellState;
import cellsociety_team16.Manager.DataManager;
import cellsociety_team16.Util.Pos;
import cellsociety_team16.Util.RandUtil;

public class SlimeCell extends BaseCell {
	
	private int myCamp;
	private int myNextCamp;
	private Amoeba myAmoeba;
	private Amoeba myNextAmoeba;
	private Map<Cell, Pos.Direction> myNeighborsDir;
	private SlimeCell myMoveToCell;
	
	protected SlimeCell(CellState state, Amoeba amoeba) {
		super(state);
		myCamp = 0;
		myAmoeba = amoeba;
	}
	
	public Amoeba getAmoeba() {
		return myAmoeba;
	}
	
	public int getCamp() {
		return myCamp;
	}
	
	public void setNeighborDir(Map<Cell, Pos.Direction> neighborsDir) {
		myNeighborsDir = neighborsDir;
	}

	@Override
	public void step(List<Cell> neighbors) {
		myNextCamp += Math.max(myCamp - 1, 0);
		if (getState().equals(SlimeState.EMPTY)) {
			if (peepNextState() == null) {
				setNextState(SlimeState.EMPTY);
			}
		} else {
			assert(myAmoeba != null);
			List<SlimeCell> emptyNbrs = getEmptyNeighbors(neighbors);
			List<SlimeCell> sniffableNbrs = getHigherNeighbors(
				emptyNbrs,
				DataManager.get().slime().getSniffThreshold()
			);
			List<SlimeCell> higherSniffableNbrs = getHigherNeighbors(sniffableNbrs, myCamp);
			if (higherSniffableNbrs.size() > 0) {
				/* Move uphill to a random neighbor cell */
				moveToRandomNeighbor(higherSniffableNbrs);
			} else if (sniffableNbrs.size() > 0) {
				/* No higher neighbors, move to a sniffable empty cell */
				moveToRandomNeighbor(sniffableNbrs);
			} else if (emptyNbrs.size() > 0) {
				/* No sniffable neighbors, move to a random empty cell */
				if (myCamp >= DataManager.get().slime().getSniffThreshold()) {
					if (RandUtil.nextInt(100) < 90) {
						stayStill();
					} else {
						moveToRandomNeighbor(emptyNbrs);
					}
				} else {
					moveToRandomNeighbor(emptyNbrs);
				}
			} else {
				/* No place to move to, simply adjust amoeba's direction */
				stayStill();
			}
		}
	}
	
	private void stayStill() {
		myMoveToCell = this;
		myAmoeba.randomlyAdjustDir();
		setNextState(SlimeState.AMOEBA);
		myNextAmoeba = myAmoeba;
	}
	
	private List<SlimeCell> getEmptyNeighbors(List<Cell> neighbors) {
		List<SlimeCell> emptyNbrs = new ArrayList<>();
		for (Cell n : neighbors) {
			SlimeCell nbr = (SlimeCell) n;
			if (
				nbr.getState().equals(SlimeState.EMPTY)
				&& (
					nbr.peepNextState() == null
					|| nbr.peepNextState().equals(SlimeState.EMPTY)
			)) {
				emptyNbrs.add(nbr);
			}
		}
		return emptyNbrs;
	}
	
	private List<SlimeCell> getHigherNeighbors(List<SlimeCell> neighbors, int campLine) {
		List<SlimeCell> higherNbrs = new ArrayList<>();
		for (SlimeCell nbr : neighbors) {
			if (nbr.myCamp >= campLine) {
				higherNbrs.add(nbr);
			}
		}
		return higherNbrs;
	}
	
	private void moveToRandomNeighbor(List<SlimeCell> neighbors) {
		SlimeCell moveToNbr = neighbors.get(
			RandUtil.nextInt(neighbors.size())
		);
		myMoveToCell = moveToNbr;
		myAmoeba.setDir(myNeighborsDir.get(moveToNbr)); //set amoeba's direction based on where it moves.
		moveToNbr.setNextState(SlimeState.AMOEBA);
		setNextState(SlimeState.EMPTY);
		moveToNbr.myNextAmoeba = myAmoeba;
		myNextAmoeba = null;
	}
	
	public SlimeCell peepMoveToCell() {
		return myMoveToCell;
	}
	
	@Override
	public void evolve() {
		super.evolve();
		myCamp = myNextCamp;
		myNextCamp = 0;
		myAmoeba = myNextAmoeba;
		myNextAmoeba = null;
	}
	
	public void releaseCampNow(List<Cell> neighbors) {
		final int releaseValue = DataManager.get().slime().getReleaseValue();
		myCamp += releaseValue;
		for (Cell nbr : neighbors) {
			((SlimeCell) nbr).myCamp += releaseValue;
		}
	}
	
	public void releaseCampNext(List<Cell> neighbors) {
		final int releaseValue = DataManager.get().slime().getReleaseValue();
		myNextCamp += releaseValue;
		for (Cell nbr : neighbors) {
			((SlimeCell) nbr).myNextCamp += releaseValue;
		}
	}
	
}
