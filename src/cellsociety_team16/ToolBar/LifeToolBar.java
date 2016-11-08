package cellsociety_team16.ToolBar;

import cellsociety_team16.Enum.SimType;
import cellsociety_team16.Manager.SimHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class LifeToolBar extends GenToolBar {
	private final int HBOX_SPACING = 8;
	private HBox Hbox;

	private Group myParent;
	private SimHandler mySimHandler;
	private String language;
	private TextField numAliveTF;

	public LifeToolBar(Group parent, String language, SimHandler simHandler) {
		super(language);
		myParent = parent;
		this.language = language;
		mySimHandler = simHandler;
	}

	public int getNumAlv() throws IllegalArgumentException {
		String value = numAliveTF.getText();
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Input not integer.");
		}
	}

	public void init(){
		Hbox = new HBox(HBOX_SPACING);
		Label numAliveLab = makeLabel("numAlive", language);
		numAliveTF = new TextField();
		Button submit = makeButton("Submit", e -> mySimHandler.onSubmitSpec(SimType.Life));
		Hbox.getChildren().addAll(numAliveLab, numAliveTF, submit);
		myParent.getChildren().add(Hbox);
		Hbox.setLayoutY(30);
	}
}
