package cellsociety_team16.Renderer;

import cellsociety_team16.Cell.CellState;
import cellsociety_team16.Manager.*;
import cellsociety_team16.Grid.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;


public abstract class Renderer {
	
	protected Shape[][] shapeArray;
	
	protected static final int WIDTH = 540;
	protected static final int HEIGHT = 540;
	
	protected Grid myGrid; /* The associated grid to render */
	protected Group myCanvas; /* The canvas (a certain area of the entire screen) to draw the simulation on */
	protected SimHandler mySimHandler;
	
	protected final int NUM_ROW;
	protected final int NUM_COL;
	
	protected Renderer(Grid grid, Group canvas, SimHandler simHandler) {
		canvas.getChildren().clear();
		myGrid = grid;
		myCanvas = canvas;
		mySimHandler = simHandler;
		NUM_ROW = DataManager.get().sim().getNumRow();
		NUM_COL = DataManager.get().sim().getNumCol();
	}
	
	public void setGrid(Grid grid) {
		myGrid = grid;
	}
	
	/**
	 * update all the colors of the shapes in the array
	 */
	public void render() {
		CellState[][] array = myGrid.toStateArray();

		for(int i = 0; i < NUM_ROW; i++) {
			for(int j = 0; j < NUM_COL; j++) {
				int stateValue = array[i][j].getStateValue();
				shapeArray[i][j].setFill(Paint.valueOf(
					DataManager.get().sim().getColorMap().get(stateValue)
				));
			}
		}
	}
	
	public void renderOutline(boolean on) {
		for(int i = 0; i < NUM_ROW; i++) {
			for(int j = 0; j < NUM_COL; j++) {
				if(on) {
					shapeArray[i][j].setStroke(Color.WHITE);
				}
				else {
					shapeArray[i][j].setStroke(null); // turn off outline
				}
			}
		}
	}
}
