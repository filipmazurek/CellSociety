package cellsociety_team16.Renderer;

import cellsociety_team16.Grid.Grid;
import cellsociety_team16.Manager.SimHandler;
import cellsociety_team16.Util.*;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;

public class HexagonRenderer extends Renderer{

	/**
	 * Calculates the correct size of hexagons and their position, then puts them on the 
	 * Group. The shapes are then ready to be filled with color.
	 * 
	 * @param grid
	 * @param canvas
	 * @param simHandler
	 */
	public HexagonRenderer(Grid grid, Group canvas, SimHandler simHandler) {
		super(grid, canvas, simHandler);

		shapeArray = new Polygon[NUM_ROW][NUM_COL];

		double sideLengthWidthwise = WIDTH / ((double)NUM_COL + .5) / Math.sqrt(3);
		double sideLengthHeightwise = HEIGHT/ ((((NUM_ROW + 1) / 2) * 2) + (NUM_ROW / 2));

		double sideLength = (sideLengthWidthwise < sideLengthHeightwise) ? sideLengthWidthwise : sideLengthHeightwise;

		double offsetSide = Math.sqrt(3) / 2 * sideLength;
		double offsetHeight = sideLength / 2;
		double hexWidth = 2 * offsetSide;

		double y0 = 0;
		double y1 = offsetHeight;
		double y2 = offsetHeight + sideLength;
		double y3 = 2 * offsetHeight + sideLength;

		double[] x0 = new double[NUM_COL];
		double[] x1 = new double[NUM_COL];

		x0[0] = offsetSide;
		x1[0] = 0;

		for(int col = 1; col < NUM_COL; col++) {
			x0[col] = x0[col-1] + 2 * offsetSide;
			x1[col] = x1[col-1] + hexWidth;
		}

		Polygon oneHexagon = new Polygon();

		for(int row = 0; row < NUM_ROW; row++) {
			for(int col = 0; col < NUM_COL; col++) {
				if(row % 2 == 0) {
					oneHexagon = new Polygon(
							x0[col], y0,
							x1[col], y1, 
							x1[col], y2,
							x0[col], y3,
							x1[col] + hexWidth, y2,
							x1[col] + hexWidth, y1);
				}
				
				if(row % 2 == 1) {
					oneHexagon = new Polygon(
							x0[col] + offsetSide, y0,
							x1[col] + offsetSide, y1,
							x1[col] + offsetSide, y2,
							x0[col] + offsetSide, y3,
							x1[col] + hexWidth + offsetSide, y2,
							x1[col] + hexWidth + offsetSide, y1);
				}
				shapeArray[row][col] = oneHexagon;
				final int r = row;
				final int c = col;
				oneHexagon.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
					Pos pos = new Pos(r,c);
					simHandler.onChangeStateOf(pos);
				});
				canvas.getChildren().add(oneHexagon);
			}
			y0 += (offsetHeight + sideLength);
			y1 += (sideLength + offsetHeight);
			y2 += (offsetHeight + sideLength);
			y3 += (sideLength + offsetHeight);
		}
	}
}