package cellsociety_team16.ToolBar;

import cellsociety_team16.Enum.*;
import cellsociety_team16.Manager.DataManager;
import cellsociety_team16.Manager.SimHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ToolBar extends GenToolBar {
	private final int VBOX_SPACING = 8;
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private VBox myVbox1;
	private VBox myVbox2;

	private Group myParent;
	private SimHandler mySimHandler;
	private Timeline myTimeline;
	private String language;
	private ResourceBundle myResources;
	private Stage myStage;

	public ToolBar(Group parent, SimHandler simHandler, Timeline timeline, String language, Stage stage) {
		super(language);
		myParent = parent;
		mySimHandler = simHandler;
		myTimeline = timeline;
		this.language = language;
		myStage = stage;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
	}
	
	public Slider makeSlider(int min, int max, int curr, int tickUnit) {
		Slider slider = new Slider(min, max, curr);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(tickUnit);
		return slider;
	}
	
	public CheckBox makeCheckBox(String label) {
		CheckBox cb = new CheckBox(myResources.getString(label));
		return cb;
	}
	
	private void initFileChooser() throws IllegalArgumentException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open XML File");
		File file = fileChooser.showOpenDialog(myStage);
		if (!file.getName().contains(".xml")) {
			System.err.println("Wrong File: Files with XML extension only");
			IllegalArgumentException ia = new IllegalArgumentException();
			throw ia;
		} else {
			if (file != null) {
				mySimHandler.onLoadExistingXML(file);
			}
		}
	}
	
	// reset, step, start, stop, Simulation
	public void init() {
		myVbox1 = new VBox(VBOX_SPACING);
		myVbox2 = new VBox(VBOX_SPACING);
		Button reset = makeButton("ResetCommand", event -> {
			mySimHandler.onReset();
			if (myTimeline.getStatus() == Animation.Status.RUNNING) {
				myTimeline.pause();
			}
		});
		Button next = makeButton("NextCommand", event -> mySimHandler.onStep());
		Label shapeLabel = makeLabel("ChooseShape", language);
		List<ShapeType> shapeList = new ArrayList<ShapeType>(Arrays.asList(ShapeType.Square, ShapeType.Triangle, ShapeType.Hexagon));
		ChoiceBox<ShapeType> chooseShape = new ChoiceBox<ShapeType>(FXCollections.observableArrayList(shapeList));
		chooseShape.getSelectionModel().selectFirst();
		chooseShape.getSelectionModel().selectedIndexProperty().addListener(
				new ChangeListener<Number>() {
					public void changed(ObservableValue<? extends Number> ov, Number value, Number new_value) {
						mySimHandler.onSetShape(shapeList.get(new_value.intValue()));
						}
					});
		Label simLabel = makeLabel("ChooseSim", language);
		List<SimType> list = new ArrayList<SimType>(Arrays.asList(SimType.Life, SimType.Fire, SimType.Wator,
				SimType.Seg, SimType.Slime));
		ChoiceBox<SimType> chooseSimulation = new ChoiceBox<SimType>(FXCollections.observableArrayList(list));
		chooseSimulation.getSelectionModel().select(0);
		chooseSimulation.getSelectionModel().selectedIndexProperty().addListener(
				new ChangeListener<Number>() {
					public void changed(ObservableValue<? extends Number> ov, Number value, Number new_value) {
						mySimHandler.onSetSimulation(list.get(new_value.intValue()));
					}
				});
		Label edgeLabel = makeLabel("ChooseEdge", language);
		List<EdgeType> edgeList = new ArrayList<EdgeType>(Arrays.asList(EdgeType.finite, EdgeType.toroidal));
		ChoiceBox<EdgeType> chooseEdge = new ChoiceBox<EdgeType>(FXCollections.observableArrayList(edgeList));
		chooseEdge.getSelectionModel().selectFirst();
		chooseEdge.getSelectionModel().selectedIndexProperty().addListener(
				new ChangeListener<Number>() {
					public void changed(ObservableValue<? extends Number> ov, Number value, Number new_value) {
						mySimHandler.onSetEdge(edgeList.get(new_value.intValue()));
					}
				});
		Button start = makeButton("StartTimeline", e -> myTimeline.play());
		Button stop = makeButton("StopTimeline", e -> {
			try {
				myTimeline.pause();
			}catch (NullPointerException ex) {
				System.out.println("Error: Cannot stop if simulation is not playing.");
			}
		});
		Label speedLab = makeLabel("Rate", language);
		Slider speedSlider = makeSlider(1, 20, 10, 5);
		speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val,
					Number new_val) {
				myTimeline.setRate(new_val.doubleValue());
			}
		});
		Label rowNum = makeLabel("RowNum", language);
		Slider rowSlider = makeSlider(10, 100, 50, 15);
		rowSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val,
					Number new_val) {
				DataManager.get().sim().setNumRow((int) new_val.doubleValue());
				mySimHandler.onChangeDimension();
			}
		});
		Label colNum = makeLabel("ColNum", language);
		Slider colSlider = makeSlider(10, 100, 50, 15);
		colSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val,
					Number new_val) {
				DataManager.get().sim().setNumCol((int) new_val.doubleValue());
				mySimHandler.onChangeDimension();
			}
		});
		Button fileButton = makeButton("ChooseFile", e -> initFileChooser());
		CheckBox showBorders = makeCheckBox("ShowBorders");
		showBorders.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> ov,
					Boolean old_val, Boolean new_val) {
				mySimHandler.onCheckBox(new_val);
			}
		});
		myVbox1.getChildren().addAll(reset, next, simLabel, chooseSimulation, 
				shapeLabel, chooseShape, edgeLabel, chooseEdge, fileButton);
		myVbox2.getChildren().addAll(start, stop, showBorders, speedLab, speedSlider,
				rowNum, rowSlider, colNum, colSlider);
		myParent.getChildren().addAll(myVbox1, myVbox2);
		myVbox1.setLayoutX(0);
		myVbox2.setLayoutX(180);
	}


}
