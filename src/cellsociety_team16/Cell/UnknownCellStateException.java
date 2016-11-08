// This entire file is part of my masterpiece.
// Jay Wang zw48
/*
 * I chose this file as one of my code masterpieces is because
 * it gives an elegant way to handle unknown state that a cell might have.
 */
package cellsociety_team16.Cell;

/**
 * Thrown to indicate that a cell possesses a state that is unknown
 * or undefined for the simulation.
 * 
 * @author Jay
 */
public class UnknownCellStateException extends RuntimeException {
	
	private static final long serialVersionUID = -4481866825064032023L;

	/**
	 * Constructs an UnknownCellStateException with the
     * specified cell name and the unknown cell state.
	 * @param cellName name of the cell.
	 * @param cellState the unknown state.
	 */
	public UnknownCellStateException(String cellName, CellState cellState) {
		super(cellName + " has an unknown state value " + cellState.getStateValue());
	}

}
