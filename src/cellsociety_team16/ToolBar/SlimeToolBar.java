package cellsociety_team16.ToolBar;

import cellsociety_team16.Enum.SimType;
import cellsociety_team16.Manager.SimHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class SlimeToolBar extends GenToolBar {
	private final int HBOX_SPACING = 8;
	private final int TF_WIDTH = 30;
	private HBox Hbox;

	private Group myParent;
	private SimHandler mySimHandler;
	private String language;
	private TextField sniffTF;
	private TextField numAmoebaTF;
	private TextField releaseTF;


	public SlimeToolBar(Group parent, String language, SimHandler simHandler) {
		super(language);
		myParent = parent;
		this.language = language;
		mySimHandler = simHandler;
	}

	public int getSniffThres() throws IllegalArgumentException {
		String value = sniffTF.getText();
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Input not integer.");
		}
	}

	public int getNumAmoeba() throws IllegalArgumentException {
		String value = numAmoebaTF.getText();
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Input not integer.");
		}
	}

	public int getReleaseThres() throws IllegalArgumentException {
		String value = releaseTF.getText();
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Input not integer.");
		}
	}

	public void init(){
		Hbox = new HBox(HBOX_SPACING);
		Label sniffLB = makeLabel("sniffTh", language);
		sniffTF = new TextField();
		sniffTF.setPrefWidth(TF_WIDTH);
		Label numAmoebaLB = makeLabel("numAmoeba", language);
		numAmoebaTF = new TextField();
		numAmoebaTF.setPrefWidth(TF_WIDTH);
		Label releaseLB = makeLabel("releaseTh", language);
		releaseTF = new TextField();
		releaseTF.setPrefWidth(TF_WIDTH);
		Button submit = makeButton("Submit", e -> mySimHandler.onSubmitSpec(SimType.Slime));
		Hbox.getChildren().addAll(sniffLB, sniffTF, numAmoebaLB, numAmoebaTF,
				releaseLB, releaseTF, submit);
		myParent.getChildren().add(Hbox);
	}
}
