package dad.recetapp.tests;

import dad.recetapp.ui.ItemDialogFactory;
import dad.recetapp.ui.controllers.InstruccionDialogController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestInstruccionDialog extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		ItemDialogFactory.forInstruccionItem().show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
