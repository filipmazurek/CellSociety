package cellsociety_team16.Cell;

import java.util.List;

public interface Cell {
	CellState getState();
	CellState peepNextState();
	void clearState();
	void setCurrentState(CellState state);
	void setNextState(CellState state);
	void step(List<Cell> neighbors);
	void evolve();
}
