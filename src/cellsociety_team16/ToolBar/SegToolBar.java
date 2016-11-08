package cellsociety_team16.ToolBar;

import cellsociety_team16.Enum.SimType;
import cellsociety_team16.Manager.SimHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class SegToolBar extends GenToolBar {
	private final int HBOX_SPACING = 8;
	private final int TF_WIDTH = 30;
	private HBox Hbox;

	private Group myParent;
	private SimHandler mySimHandler;
	private String language;
	private TextField percentEmTF;
	private TextField percentATF;
	private TextField percentSatTF;

	public SegToolBar(Group parent,String language, SimHandler simHandler) {
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

	public int getPercentA() throws IllegalArgumentException {
		String value = percentATF.getText();
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Input not integer.");
		}
	}

	public int getPercentSat() throws IllegalArgumentException {
		String value = percentSatTF.getText();
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Input not integer.");
		}
	}

	public void init(){
		Hbox = new HBox(HBOX_SPACING);
		Label percentEmLb = makeLabel("percentEmpty", language);
		percentEmTF = new TextField();
		percentEmTF.setPrefWidth(TF_WIDTH);
		Label percentALb = makeLabel("percentAlive", language);
		percentATF = new TextField();
		percentATF.setPrefWidth(TF_WIDTH);
		Label percentSatLb = makeLabel("percentSatisfaction", language);
		percentSatTF = new TextField();
		percentSatTF.setPrefWidth(TF_WIDTH);
		Button submit = makeButton("Submit", e -> mySimHandler.onSubmitSpec(SimType.Seg));
		Hbox.getChildren().addAll(percentEmLb, percentEmTF, percentALb, percentATF,
				percentSatLb, percentSatTF, submit);
		myParent.getChildren().add(Hbox);
	}
}
