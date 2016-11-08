package cellsociety_team16;

import cellsociety_team16.Manager.SimManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage stage){
		SimManager simManager = new SimManager();
		simManager.init(stage);
	}
	
	/**
	 * Main of the program.
	 * @param args
	 */
	public static void main (String[] args) {
        launch(args);
    }
}
