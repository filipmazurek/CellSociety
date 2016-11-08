package cellsociety_team16.Renderer;

import cellsociety_team16.Cell.CellState;
import cellsociety_team16.Grid.Grid;
import cellsociety_team16.Manager.DataManager;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.HashMap;
import java.util.Queue;

import org.apache.commons.collections4.queue.CircularFifoQueue;
// TODO: Help with adding the apache commons jar to the build path

public class GraphDisplayer {

	private Queue<HashMap<Integer, Double>> graphItemsQueue;
	private Grid myGrid;
	private Group myCanvas;
	private double myCirclePointRadius;
	private static final double CANVAS_HEIGHT = 180;
	private static final double CANVAS_WIDTH = 300;

	public GraphDisplayer(Grid grid, Group canvas) {
		myGrid = grid;
		myCanvas = canvas;
		myCirclePointRadius = CANVAS_HEIGHT / 100; // point radius is 1% of canvas height

		int numPointsWidthwise = (int) (CANVAS_WIDTH / myCirclePointRadius);
		graphItemsQueue = new CircularFifoQueue<HashMap<Integer, Double>>(numPointsWidthwise);
	}

	private void updateQueue() {
		CellState[][] statesArray = myGrid.toStateArray();
		
		int numStates = DataManager.get().sim().getNumStates();
		int gridRows = statesArray.length;
		int gridCols = statesArray[0].length;
		double totalCells = gridRows * gridCols;
		
		double heightPerCell = CANVAS_HEIGHT / totalCells;
		
		HashMap<Integer, Double> givenInstance = new HashMap<Integer, Double>();

		for(int i = 0; i < numStates; i++) {
			givenInstance.put(i, 0.0);
		}

		for(int row = 0; row < gridRows; row++) {
			for(int col = 0; col < gridCols; col++) {
				int cellState = statesArray[row][col].getStateValue();
				if (givenInstance.containsKey(cellState)) {
					givenInstance.put(cellState, givenInstance.get(cellState) + heightPerCell);
				}
			}
		}
		graphItemsQueue.add(givenInstance);
	}

	private void displayGraph() {
		int numStates = DataManager.get().sim().getNumStates();
		
		myCanvas.getChildren().clear(); // clear all points because need to account for moving graph

		double xPos = 0;

		HashMap<Integer, Double> previousYMap = new HashMap<Integer, Double>();
		
		for(int i = 0; i < numStates; i++) {
			previousYMap.put(i, -1.0);
		}
		
		for(HashMap<Integer, Double> graphableItem : graphItemsQueue) {
			for(int cellStateKey : graphableItem.keySet()) {
				double yPos  = CANVAS_HEIGHT - (graphableItem.get(cellStateKey));
				Circle oneGraphPoint = new Circle(xPos, yPos, myCirclePointRadius, 
						Paint.valueOf(DataManager.get().sim().getColorMap().get(cellStateKey)));
				myCanvas.getChildren().add(oneGraphPoint);
				
				if(previousYMap.get(cellStateKey) >=0) {
					Line oneLine = new Line(xPos - myCirclePointRadius, previousYMap.get(cellStateKey), xPos, yPos);
					oneLine.setStroke(Paint.valueOf(DataManager.get().sim().getColorMap().get(cellStateKey)));
					myCanvas.getChildren().add(oneLine);
				}
				
				previousYMap.put(cellStateKey, yPos);
			}
			xPos += myCirclePointRadius;
		}
	}

	public void reset() {
		graphItemsQueue.clear();
	}
	
	public void setGrid(Grid grid) {
		myGrid = grid;
		reset();
	}
	
	public void render() {
		updateQueue();
		displayGraph();
	}
}
