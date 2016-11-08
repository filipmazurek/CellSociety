package cellsociety_team16.ToolBar;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

abstract public class GenToolBar {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	
	private ResourceBundle myResources;
	
	protected GenToolBar(String language) {
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
	}
	
	public Button makeButton (String label, EventHandler<ActionEvent> event) {
		Button button = new Button();
		button.setText(myResources.getString(label));
		button.setOnAction(event);
		return button;
	}
	
	public Label makeLabel(String name, String language) {
		Label label = new Label(myResources.getString(name));
		return label;
	}
	
}
