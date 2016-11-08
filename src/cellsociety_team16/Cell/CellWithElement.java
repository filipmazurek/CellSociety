package cellsociety_team16.Cell;

public interface CellWithElement extends Cell {
	CellElement getElement();
	void setElement();
	boolean moveElementTo(CellWithElement anotherCell);
}
