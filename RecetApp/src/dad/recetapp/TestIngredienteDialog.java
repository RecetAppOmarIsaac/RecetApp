package dad.recetapp;

import dad.recetapp.ui.ItemDialogFactory;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Usuario on 10/01/15.
 */
public class TestIngredienteDialog extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		ItemDialogFactory.forIngredienteItem().show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
