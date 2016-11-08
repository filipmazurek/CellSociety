// This entire file is part of my masterpiece.
// Jay Wang zw48
/* 
 * Following are the reasons why I believe this should be a masterpiece:
 * It defines a set of APIs that specifically define what the outside world
 * can observe and expect from the Grid, and hide the implementation details.
 * See BaseGrid.java that implements the interface.
 */
package cellsociety_team16.Grid;

import cellsociety_team16.Cell.CellState;
import cellsociety_team16.Enum.EdgeType;
import cellsociety_team16.Enum.ShapeType;
import cellsociety_team16.Util.Pos;

/**
 * An interface for a grid model.
 * @author Jay
 */
public interface Grid {
	/**
	 * Converts the Grid's internal data to a 2D-array in the form of CellState
	 * @return CellState[][] a matrix of CellState
	 */
	CellState[][] toStateArray();
	
	/**
	 * Update into next stage
	 */
	void step();
	
	/**
	 * Reset the grid to its initial state.
	 */
	void reset();
	
	/**
	 * Change the state of a specific position of the grid.
	 * @param pos the position of the cell that needs to be changed.
	 */
	void changeStateOf(Pos pos);
	
	/**
	 * Set the shape type that should be used by the grid.
	 * This mainly determines the model the grid uses to get a cell's neighbors.
	 * E.g. whether it's 4/8 neighbors for square model, or 6 neighbors for hexagon.
	 * @param shape ShapeType enum
	 */
	void setShapeType(ShapeType shape);
	
	/**
	 * Set the edge type that should be used by the grid.
	 * This mainly determines when it comes to cells that are around the corner,
	 * whether the neighbors should be wrap-around
	 * @param edge EdgeType enum
	 */
	void setEdgeType(EdgeType edge);
}
