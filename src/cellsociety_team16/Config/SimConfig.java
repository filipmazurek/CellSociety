package cellsociety_team16.Config;

import java.util.HashMap;
import java.util.Map;

import cellsociety_team16.Enum.EdgeType;
import cellsociety_team16.Enum.ShapeType;
import cellsociety_team16.Enum.SimType;

public class SimConfig {

	private SimType mySimType = SimType.Life;
	private ShapeType myShapeType = ShapeType.Square;
	private EdgeType myEdgeType = EdgeType.finite;
	private final Map<SimType, Integer> myNumStatesMap;
	private int myNumRow = 50;
	private int myNumCol = 50;
	private Map<Integer, String> myColorMap;

	public SimConfig() {
		myNumStatesMap = new HashMap<>();
		myNumStatesMap.put(SimType.Fire, 3);
		myNumStatesMap.put(SimType.Seg, 3);
		myNumStatesMap.put(SimType.Life, 2);
		myNumStatesMap.put(SimType.Wator, 3);
		myNumStatesMap.put(SimType.Slime, 2);
	}

	public int getNumStates() {
		return myNumStatesMap.get(mySimType);
	}
	
	public int getNumRow() {
		return myNumRow;
	}
	public void setNumRow(int numRow) throws IllegalArgumentException {
		if (numRow <= 100 && numRow >= 10) {
			myNumRow = numRow;
		} else {
			throw new IllegalArgumentException("Number out of range: Number of rows out of preferred range (10-100).");
		}
	}
	public int getNumCol() {
		return myNumCol;
	}
	public void setNumCol(int numCol) throws IllegalArgumentException {
		if (numCol <= 100 && numCol >= 10) {
			myNumCol = numCol;
		} else {
			throw new IllegalArgumentException("Number out of range: Number of columns out of preferred range (10-100).");
		}
	}

	public Map<Integer, String> getColorMap() {
		return myColorMap;
	}

	public void setColorMap(Map<Integer, String> colorMap) {
		myColorMap = colorMap;
	}

	public void setSimType(SimType simType) {
		mySimType = simType;
	}

	public SimType getSimType() {
		return mySimType;
	}

	public ShapeType getShapeType() {
		return myShapeType;
	}

	public void setShapeType(ShapeType shapeType) {
		myShapeType = shapeType;
	}

	public EdgeType getEdgeType() {
		return myEdgeType;
	}

	public void setEdgeType(EdgeType edgeType) {
		myEdgeType = edgeType;
	}

}
