package cellsociety_team16.ToolBar;

import cellsociety_team16.Enum.SimType;
import cellsociety_team16.Manager.SimHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class FireToolBar extends GenToolBar {
	private final int HBOX_SPACING = 8;
	private HBox Hbox;

	private Group myParent;
	private SimHandler mySimHandler;
	private String language;
	private TextField burnTF;
	private TextField probTF;

	public FireToolBar(Group parent, String language, SimHandler simHandler) {
		super(language);
		myParent = parent;
		this.language = language;
		mySimHandler = simHandler;
	}

	public int getNumBurn() throws IllegalArgumentException {
		String value = burnTF.getText();
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Input not integer.");
		}
	}

	public int getProbCatch() throws IllegalArgumentException {
		String value = probTF.getText();
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Input not integer.");
		}
	}


	public void init(){
		Hbox = new HBox(HBOX_SPACING);
		Label numBurnLab = makeLabel("numBurn", language);
		burnTF = new TextField();
		Label probCatchLab = makeLabel("probCatch", language);
		probTF = new TextField();
		Button submit = makeButton("Submit", e -> mySimHandler.onSubmitSpec(SimType.Fire));
		Hbox.getChildren().addAll(numBurnLab, burnTF, probCatchLab, probTF, submit);
		myParent.getChildren().add(Hbox);
	}

}
