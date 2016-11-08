package cellsociety_team16.Cell;

/**
 * A base class of Cell
 * @author Jay
 *
 */
abstract public class BaseCell implements Cell {
	
	private CellState myState;
	private CellState myNextState;
	
	protected BaseCell(CellState state) {
		myState = state;
		myNextState = null;
	}

	@Override
	public CellState getState() {
		return myState;
	}
	
	@Override
	public CellState peepNextState() {
		return myNextState;
	}

	@Override
	public void clearState() {
		myState = null;
		myNextState = null;
	}
	
	@Override
	public void setCurrentState(CellState currentState) {
		myState = currentState;
	}
	
	@Override
	public void setNextState(CellState nextState) {
		myNextState = nextState;
	}
	
	@Override
	public void evolve() {
		if (myNextState == null) {
			throw new NullPointerException(
				"Cannot evolve with myNextState being null"
			);
		}
		myState = myNextState;
		myNextState = null;
	}

}
