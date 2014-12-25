package dad.recetapp;

import dad.recetapp.ui.controllers.EditarRecetaDialogController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by Usuario on 25/12/14.
 */
public class TestEditarRecetaDialog extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/dad/recetapp/ui/fxml/recetaDialogRoot.fxml"));
		loader.setRoot(new BorderPane());
		loader.setController(new EditarRecetaDialogController());

		Parent root = loader.load();

		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
