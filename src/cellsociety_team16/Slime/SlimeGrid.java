package cellsociety_team16.Slime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cellsociety_team16.Cell.Cell;
import cellsociety_team16.Cell.CellState;
import cellsociety_team16.Enum.EdgeType;
import cellsociety_team16.Enum.ShapeType;
import cellsociety_team16.Grid.BaseGrid;
import cellsociety_team16.Manager.DataManager;
import cellsociety_team16.Util.Pos;
import cellsociety_team16.Util.Pos.Direction;
import cellsociety_team16.Util.RandUtil;

public class SlimeGrid extends BaseGrid {

	private SlimeCell[][] myCellMatrix;
	
	public SlimeGrid(int numRow, int numCol) {
		super(numRow, numCol);
		myCellMatrix = new SlimeCell[numRow][numCol];
		initStateColor();
		reset();
	}
	
	private void initStateColor() {
 		Map<Integer, String> colorMap = new HashMap<>();
 		String[] colorArr = {"#33ff33", "#000000", "#f7e6ff", "#efccff", "#e7b3ff", "#df99ff", "#d780ff", "#cf66ff", "#c64dff", "#be33ff", "#b61aff", "ae00ff", "9d00e6", "8b00cc", "7a00b3", "690099", "570080"};
 		for (int i = 0; i < colorArr.length; i++) {
 			colorMap.put(i, colorArr[i]);
 		}
 		DataManager.get().sim().setColorMap(colorMap);
 	}
	
	@Override
	public void reset() {
		List<SlimeCell> cellList = new ArrayList<>();
		/* Add amoeba cells */
		final int numAmoeba = DataManager.get().slime().getNumAmoeba();
		for (int i = 0; i < numAmoeba; i++) {
			cellList.add(new SlimeCell(
				SlimeState.AMOEBA,
				new Amoeba(RandUtil.generateRandomDir())
			));
		}
		/* Add empty cells */
		int numEmpty = NUM_CELL - numAmoeba;
		for (int i = 0; i < numEmpty; i++) {
			cellList.add(new SlimeCell(SlimeState.EMPTY, null));
		}
		/* Add to cellMatrix */
		Collections.shuffle(cellList);
		clearPosMap();
		int pointer = -1;
		for (int i = 0; i < NUM_ROW; i++) {
			for (int j = 0; j < NUM_COL; j++) {
				SlimeCell newCell = cellList.get(++pointer);
				myCellMatrix[i][j] = newCell;
				putIntoPosMap(newCell, new Pos(i, j));
			}
		}
		for (SlimeCell[] cellRow : myCellMatrix) {
			for (SlimeCell cell : cellRow) {
				if (cell.getState().equals(SlimeState.AMOEBA)) {
					cell.releaseCampNow(getFullNeighborsOf(cell));
				}
			}
		}
	}
	
	private List<Cell> getFullNeighborsOf(Cell cell) {
		if (myShapeType == ShapeType.Hexagon) {
			return getSixNeighborsOf(cell);
		}
		return getEightNeighborsOf(cell);
	}
	
	private List<Cell> getSensedNeighborsOf(Cell cell) {
		assert(cell.getState().equals(SlimeState.AMOEBA));
		List<Cell> nbrs = new ArrayList<>();
		SlimeCell slimeCell = (SlimeCell) cell;
		Pos[] nbrsPos;
		if (myShapeType == ShapeType.Hexagon) {
			nbrsPos = Pos.getThreePosInDirectionHexagonally(
				getPosOf(slimeCell),
				slimeCell.getAmoeba().getDir()
			);
		} else {
			nbrsPos = Pos.getThreePosInDirectionSquarelly(
				getPosOf(slimeCell),
				slimeCell.getAmoeba().getDir()
			);
		}
		for (Pos p : nbrsPos) {
			if (myEdgeType == EdgeType.finite) {
				/* finite */
				if (isPosValidInGrid(p)) {
					nbrs.add(myCellMatrix[p.r][p.c]);
				}
			} else {
				p = getWrappedAroundPos(p);
				nbrs.add(myCellMatrix[p.r][p.c]);
			}
		}
		return nbrs;
	}
	
	@Override
	protected Cell[][] getCellMatrix() {
		return myCellMatrix;
	}

	@Override
	protected List<Cell> getNeighborsOf(Cell cell) {
		if (cell.getState().equals(SlimeState.EMPTY)) {
			return null;
		} else {
			return getSensedNeighborsOf(cell);
		}
	}
	
	@Override
	protected void doStep() {
		for (SlimeCell[] cellRow : myCellMatrix) {
			for (SlimeCell cell : cellRow) {
				if (cell.getState().equals(SlimeState.EMPTY)) {
					cell.setNeighborDir(null);
					cell.step(null);
				} else {
					List<Cell> nbrs = getNeighborsOf(cell);
					Map<Cell, Direction> nbrsDirMap = new HashMap<>();
					Pos curPos = getPosOf(cell);
					for (Cell nbr : nbrs) {
						Pos nbrPos = getPosOf(nbr);
						if (myEdgeType == EdgeType.finite) {
							nbrsDirMap.put(nbr, Pos.getRelativeDirection(curPos, nbrPos));
						} else {
							nbrsDirMap.put(nbr, Pos.getRelativeDirectionWithWrappingAround(curPos, nbrPos, NUM_ROW, NUM_COL));
						}
					}
					cell.setNeighborDir(nbrsDirMap);
					cell.step(nbrs);
					SlimeCell moveToCell = cell.peepMoveToCell();
					if (moveToCell != null) {
						moveToCell.releaseCampNext(getFullNeighborsOf(moveToCell));
					}
				}
			}
		}
	}
	
	@Override
	public CellState[][] toStateArray() {
		CellState[][] stateMatrix = new CellState[myCellMatrix.length][];
		for (int i = 0; i < myCellMatrix.length; i++) {
			stateMatrix[i] = new CellState[myCellMatrix[i].length];
			for (int j = 0; j < myCellMatrix[i].length; j++) {
				SlimeCell cell = myCellMatrix[i][j];
				stateMatrix[i][j] = 
					cell.getState().equals(SlimeState.AMOEBA)
					? cell.getState()
					: new CellState((Math.min(10, ((SlimeCell) cell).getCamp() + 1)));
			}
		}
		return stateMatrix;
	}

}
