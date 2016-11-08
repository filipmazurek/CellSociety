package cellsociety_team16.Renderer;

import cellsociety_team16.Grid.Grid;
import cellsociety_team16.Manager.SimHandler;
import cellsociety_team16.Util.Pos;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;

/**
 * Calculates the correct size of triangles and their position, then puts them on the 
 * Group. The shapes are then ready to be filled with color.
 * 
 * @param grid
 * @param canvas
 * @param simHandler
 */
public class TriangleRenderer extends Renderer {

	public TriangleRenderer(Grid grid, Group canvas, SimHandler simHandler) {
		super(grid, canvas, simHandler);

		shapeArray = new Polygon[NUM_ROW][NUM_COL];

		double sideLengthWidthwise = WIDTH /  (NUM_COL / 2 + 1);
		double sideLengthHeightwise = 2 / Math.sqrt(3) * (HEIGHT / NUM_ROW);

		double sideLength = (sideLengthWidthwise < sideLengthHeightwise) ? sideLengthWidthwise : sideLengthHeightwise;
		double offset = sideLength / 2;
		double triangleHeight = Math.sqrt(3) / 2 * sideLength;

		int numTopPoints = NUM_COL / 2 + 1;
		int numBotPoints = (NUM_COL + 1) / 2 + 1;

		double[] topPointsX = new double[numTopPoints];
		topPointsX[0] = offset;
		for(int i = 1; i < numTopPoints; i++) {
			topPointsX[i] = topPointsX[i-1] + sideLength;
		}

		double[] botPointsX = new double[numBotPoints];
		botPointsX[0] = 0;
		for(int i = 1; i < numBotPoints; i++) {
			botPointsX[i] = botPointsX[i-1] + sideLength;
		}

		Polygon oneTriangle = new Polygon();

		double yTop = 0;
		double yBot = triangleHeight;

		for(int row = 0; row < NUM_ROW; row++) {
			for (int col = 0; col < NUM_COL; col++) {
				if((row % 2 == 0) && (col % 2 == 0)) {
					oneTriangle = new Polygon(
							topPointsX[col / 2], yTop,
							botPointsX[col / 2], yBot, 
							botPointsX[col / 2 + 1], yBot);
				}

				if((row % 2 == 0) && (col % 2 == 1)) {
					oneTriangle = new Polygon(
							topPointsX[col / 2], yTop,
							topPointsX[col / 2 + 1], yTop,
							botPointsX[col / 2 + 1], yBot);
				}

				if((row % 2 == 1) && (col % 2 == 0)) {
					oneTriangle = new Polygon(
							topPointsX[col / 2], yTop,
							topPointsX[col / 2 + 1], yTop,
							botPointsX[col / 2], yBot);
				}
				if((row % 2 == 1) && (col % 2 == 1)) {
					oneTriangle = new Polygon(
							topPointsX[col / 2 + 1], yTop,
							botPointsX[col / 2], yBot,
							botPointsX[col / 2 + 1], yBot);
				}
				shapeArray[row][col] = oneTriangle;
				final int r = row;
				final int c = col;
				oneTriangle.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
					Pos pos = new Pos(r,c);
					simHandler.onChangeStateOf(pos);
				});
				canvas.getChildren().add(oneTriangle);
			}
			yTop += triangleHeight;
			yBot += triangleHeight;

			double[] xTempList = topPointsX;
			topPointsX = botPointsX;
			botPointsX = xTempList;
		}
	}
}