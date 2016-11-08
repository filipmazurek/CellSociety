package cellsociety_team16.Manager;

import cellsociety_team16.ToolBar.ToolBar;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GuiManager {
	private static final int SCREEN_WIDTH = 1000;
	private static final int SCREEN_HEIGHT = 700;
	private Stage myStage;
	private Scene myScene;
	private Group myRoot;
	private SimHandler mySimHandler;
	private Timeline myTimeline;
	private Group myRegionToolBar;
	private Group myRegionSim;
	private Group myRegionGraph; 
	private Group myRegionSpecToolBar;

	
	public void init(Stage stage, SimHandler simHandler) {
		myStage = stage;
		mySimHandler = simHandler;
		initScene();
		initTimeline();
		initToolBar();
		stage.setTitle("Cell Society");
	    stage.setScene(myScene);
	    stage.show();
	}
	
	private void initScene() {
		myRoot = new Group();
		myRegionSim = new Group();
		myRegionToolBar = new Group();
		myRegionGraph = new Group();
		myRegionSpecToolBar = new Group();
		myRegionSim.setLayoutX(30);
		myRegionSim.setLayoutY(30);
		myRegionToolBar.setLayoutX(650);
		myRegionToolBar.setLayoutY(300);
		myRegionGraph.setLayoutX(600);
		myRegionGraph.setLayoutY(50);
		myRegionSpecToolBar.setLayoutX(30);
		myRegionSpecToolBar.setLayoutY(600);
		myRoot.getChildren().addAll(myRegionSim, myRegionToolBar, myRegionGraph, myRegionSpecToolBar);
		myScene = new Scene(myRoot, SCREEN_WIDTH, SCREEN_HEIGHT);
	}
	
	private void initTimeline() {
		myTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
			mySimHandler.onStep();
		}));
		myTimeline.setCycleCount(Animation.INDEFINITE);
	}
	
	private void initToolBar(){
		ToolBar toolbar = new ToolBar(myRegionToolBar, mySimHandler, myTimeline, "English", myStage);
		toolbar.init();
	}

	public Group getCanvasforGraphDisplay(){
		return myRegionGraph;
	}
	
	/**
	 * Get the canvas where the grid should be drawn.
	 * @return pane
	 */
	public Group getCanvasForSimulation() {
		return myRegionSim;
	}
	
	public void resetSpecTBCanvas() {
		myRegionSpecToolBar.getChildren().clear();
	}
	
	public Group getCanvasforSpecTB() {
		return myRegionSpecToolBar;
	}
	
}
