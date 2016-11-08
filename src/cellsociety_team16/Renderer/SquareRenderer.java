package cellsociety_team16.Renderer;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import cellsociety_team16.Grid.Grid;
import cellsociety_team16.Manager.SimHandler;
import cellsociety_team16.Util.Pos;

/**
 * Calculates the correct size of squares and their position, then puts them on the 
 * Group. The shapes are then ready to be filled with color.
 * 
 * @param grid
 * @param canvas
 * @param simHandler
 */
public class SquareRenderer extends Renderer {

	public SquareRenderer(Grid grid, Group canvas, SimHandler simHandler) {
		super(grid, canvas, simHandler);

		shapeArray = new Rectangle[NUM_ROW][NUM_COL];;

		double sideLengthWidthwise = WIDTH /  NUM_COL;
		double sideLengthHeightwise = HEIGHT / NUM_ROW;

		double sideLength = (sideLengthWidthwise < sideLengthHeightwise) ? sideLengthWidthwise : sideLengthHeightwise;

		Rectangle oneSquare; // = new Rectangle(sideLength, sideLength);
		double xPos;
		double yPos;

		for(int row = 0; row < NUM_ROW; row++) {
			for (int col = 0; col < NUM_COL; col++) {
				xPos = sideLength * col;
				yPos = sideLength * row;
				oneSquare = new Rectangle(xPos, yPos, sideLength, sideLength);
				shapeArray[row][col] = oneSquare;
				final int r = row;
				final int c = col;
				oneSquare.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
					Pos pos = new Pos(r,c);
					simHandler.onChangeStateOf(pos);
				});
				canvas.getChildren().add(oneSquare);
			}
		}
	}
}
