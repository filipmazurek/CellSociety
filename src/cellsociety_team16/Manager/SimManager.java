package cellsociety_team16.Manager;

import java.io.File;

import cellsociety_team16.Enum.EdgeType;
import cellsociety_team16.Enum.ShapeType;
import cellsociety_team16.Enum.SimType;
import cellsociety_team16.Fire.FireGrid;
import cellsociety_team16.Grid.Grid;
import cellsociety_team16.Life.LifeGrid;
import cellsociety_team16.Renderer.*;
import cellsociety_team16.Seg.SegGrid;
import cellsociety_team16.Slime.SlimeGrid;
import cellsociety_team16.ToolBar.FireToolBar;
import cellsociety_team16.ToolBar.GenToolBar;
import cellsociety_team16.ToolBar.LifeToolBar;
import cellsociety_team16.ToolBar.SegToolBar;
import cellsociety_team16.ToolBar.SlimeToolBar;
import cellsociety_team16.ToolBar.WatorToolBar;
import cellsociety_team16.Util.Pos;
import cellsociety_team16.Wator.WatorGrid;
import cellsociety_team16.Xml.XmlLoader;
import javafx.scene.Group;
import javafx.stage.Stage;

public class SimManager {
	GuiManager myGuiManager;
	Grid myGrid;
	Renderer myRenderer;
	SimHandler mySimHandler;
	GraphDisplayer myGraphDisplayer;
	XmlLoader myXmlLoader;
	GenToolBar myGenToolBar;
	FireToolBar fireTB;
	LifeToolBar lifeTB;
	SegToolBar segTB;
	SlimeToolBar slimeTB;
	WatorToolBar watorTB;

	public SimManager() {
		myGuiManager = new GuiManager();
		myXmlLoader = new XmlLoader();
		mySimHandler = new SimHandler() {

			@Override
			public void onStep() {
				step();
			}

			@Override
			public void onReset() {
				reset();
			}

			@Override
			public void onSetSimulation(SimType sim) {
				initNewSimulationWithSimType(sim);
			}

			@Override
			public void onSetEdge(EdgeType edge) {
				setEdgeType(edge);
			}

			@Override
			public void onSetShape(ShapeType shape) {
				setShapeType(shape);
			}

			@Override
			public void onChangeStateOf(Pos position) {
				changeStateOf(position);
			}

			@Override
			public void onChangeDimension() {
				setGridWithSimType(DataManager.get().sim().getSimType());
				setShapeType(DataManager.get().sim().getShapeType());
				setEdgeType(DataManager.get().sim().getEdgeType());
				setGraphDisplayer();
			}
			
			@Override
			public void onLoadExistingXML(File file) {
				/* This is never implemented. */
				System.out.println("Load existing xml file");
			}
			
			@Override
			public void onCheckBox(Boolean checked) {
				myRenderer.renderOutline(checked);
				myRenderer.render();
			}
			
			@Override
			public void onSubmitSpec(SimType simType) {
				submitSpec(simType);
			}
		};
	}

	public void init(Stage stage) {
		myGuiManager.init(stage, mySimHandler);
		/* Set the default simulation to be fire*/
		initNewSimulationWithSimType(SimType.Life);
	}

	private void step() {
		myGrid.step();
		myRenderer.render();
		myGraphDisplayer.render();
	}

	private void reset() {
		myGrid.reset();
		myRenderer.render();
		myGraphDisplayer.reset();
		myGraphDisplayer.render();
	}
	
	private void initNewSimulationWithSimType(SimType sim) {
		if (sim == null) {
			throw new NullPointerException("SimType cannot be null");
		}
		myXmlLoader.loadXmlWithSimType(sim);
		setGridWithSimType(sim);
		setShapeType(DataManager.get().sim().getShapeType());
		setEdgeType(DataManager.get().sim().getEdgeType());
		setGraphDisplayer();
	}

	private void setGridWithSimType(SimType sim) {
		myGuiManager.resetSpecTBCanvas();
		int numRow = DataManager.get().sim().getNumRow();
		int numCol = DataManager.get().sim().getNumCol();
		switch (sim) {
		case Seg:
			myGrid = new SegGrid(numRow, numCol);
			segTB = new SegToolBar(myGuiManager.getCanvasforSpecTB(), "English", mySimHandler);
			segTB.init();
			break;
		case Wator:
			myGrid = new WatorGrid(numRow, numCol);
			watorTB = new WatorToolBar(myGuiManager.getCanvasforSpecTB(), "English", mySimHandler);
			watorTB.init();
			break;
		case Fire:
			myGrid = new FireGrid(numRow, numCol);
			fireTB = new FireToolBar(myGuiManager.getCanvasforSpecTB(), "English", mySimHandler);
			fireTB.init();
			break;
		case Life:
			myGrid = new LifeGrid(numRow, numCol);
			lifeTB = new LifeToolBar(myGuiManager.getCanvasforSpecTB(), "English", mySimHandler);
			lifeTB.init();
			break;
		case Slime:
			myGrid = new SlimeGrid(numRow, numCol);
			slimeTB = new SlimeToolBar(myGuiManager.getCanvasforSpecTB(), "English", mySimHandler);
			slimeTB.init();
			break;
		default:
			throw new IllegalArgumentException("SimType not valid");
		}
	}

	private void setShapeType(ShapeType shape) {
		DataManager.get().sim().setShapeType(shape);
		Group canvas = myGuiManager.getCanvasForSimulation();
		switch (shape) {
		case Triangle:
			myRenderer = new TriangleRenderer(myGrid, canvas, mySimHandler);
			break;
		case Square:
			myRenderer = new SquareRenderer(myGrid, canvas, mySimHandler);
			break;
		case Hexagon:
			myRenderer = new HexagonRenderer(myGrid, canvas, mySimHandler);
			break;
		}
		myGrid.setShapeType(shape);
		myGrid.reset();
		myRenderer.render();
		if (myGraphDisplayer != null) {
			myGraphDisplayer.reset();
		}
	}

	private void setEdgeType(EdgeType edge) {
		DataManager.get().sim().setEdgeType(edge);
		myGrid.setEdgeType(edge);
		myGrid.reset();
		myRenderer.render();
	}

	private void changeStateOf(Pos pos) {
		myGrid.changeStateOf(pos);
		myRenderer.render();
	}
	
	private void setGraphDisplayer() {
		Group graphCanvas = myGuiManager.getCanvasforGraphDisplay();
		myGraphDisplayer = new GraphDisplayer(myGrid, graphCanvas);
		myGraphDisplayer.render();
	}

	private void submitSpec(SimType simType) {
		switch (simType) {
		case Seg:
			DataManager.get().seg().setPercentEmpty(segTB.getPercentEm());
			DataManager.get().seg().setPercentA(segTB.getPercentA());
			DataManager.get().seg().setPercentSatisfy(segTB.getPercentSat());
			break;
		case Wator:
			DataManager.get().wator().setFishBreed(watorTB.getFishBreed());
			DataManager.get().wator().setPercentEmpty(watorTB.getPercentEm());
			DataManager.get().wator().setPercentShark(watorTB.getPercentShark());
			DataManager.get().wator().setSharkBreed(watorTB.getSharkBreed());
			DataManager.get().wator().setSharkStarve(watorTB.getSharkStarve());
			break;
		case Fire:
			DataManager.get().fire().setNumBurn(fireTB.getNumBurn());
			DataManager.get().fire().setProbCatch(fireTB.getProbCatch());
			break;
		case Life:
			DataManager.get().life().setNumAlive(lifeTB.getNumAlv());
			break;
		case Slime:
			DataManager.get().slime().setNumAmoeba(slimeTB.getNumAmoeba());
			DataManager.get().slime().setReleaseValue(slimeTB.getReleaseThres());
			DataManager.get().slime().setSniffThreshold(slimeTB.getSniffThres());
			break;
		}
	}
}
