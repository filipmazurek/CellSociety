package cellsociety_team16.ToolBar;

import cellsociety_team16.Enum.SimType;
import cellsociety_team16.Manager.SimHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class WatorToolBar extends GenToolBar {
	private final int HBOX_SPACING = 8;
	private final int TF_WIDTH = 30;
	private HBox Hbox1;
	private HBox Hbox2;

	private SimHandler mySimHandler;
	private Group myParent;
	private String language;
	private TextField percentEmTF;
	private TextField percentSharkTF;
	private TextField fishBreedTF;
	private TextField sharkBreedTF;
	private TextField sharkStarveTF;
	
	public WatorToolBar(Group parent, String language, SimHandler simHandler) {
		super(language);
		myParent = parent;
		this.language = language;
		mySimHandler = simHandler;
	}
	
	public int getPercentEm() throws IllegalArgumentException {
		String value = percentEmTF.getText();
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Input not integer.");
		}
	}

	public int getPercentShark() throws IllegalArgumentException {
		String value = percentSharkTF.getText();
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Input not integer.");
		}
	}

	public int getFishBreed() throws IllegalArgumentException {
		String value = fishBreedTF.getText();
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Input not integer.");
		}
	}
	
	public int getSharkBreed() throws IllegalArgumentException {
		String value = sharkBreedTF.getText();
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Input not integer.");
		}
	}
	
	public int getSharkStarve() throws IllegalArgumentException {
		String value = sharkStarveTF.getText();
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Input not integer.");
		}
	}

	public void init(){
		Hbox1 = new HBox(HBOX_SPACING);
		Hbox2 = new HBox(HBOX_SPACING);
		Label percentEmLb = makeLabel("percentEmpty", language);
		percentEmTF = new TextField();
		percentEmTF.setPrefWidth(TF_WIDTH);
		Label percentSharkLb = makeLabel("percentShark", language);
		percentSharkTF = new TextField();
		percentSharkTF.setPrefWidth(TF_WIDTH);
		Label fishBreedLb = makeLabel("fishBreed", language);
		fishBreedTF = new TextField();
		fishBreedTF.setPrefWidth(TF_WIDTH);
		Label sharkBreedLb = makeLabel("sharkBreed", language);
		sharkBreedTF = new TextField();
		sharkBreedTF.setPrefWidth(TF_WIDTH);
		Label sharkStarveLb = makeLabel("sharkStarve", language);
		sharkStarveTF = new TextField();
		sharkStarveTF.setPrefWidth(TF_WIDTH);
		Button submit = makeButton("Submit", e -> mySimHandler.onSubmitSpec(SimType.Wator));
		Hbox1.getChildren().addAll(percentEmLb, percentEmTF, percentSharkLb, percentSharkTF,
				fishBreedLb, fishBreedTF);
		Hbox2.getChildren().addAll(sharkBreedLb, sharkBreedTF, sharkStarveLb, sharkStarveTF, submit);
		myParent.getChildren().addAll(Hbox1, Hbox2);
		Hbox2.setLayoutY(30);
	}
}
