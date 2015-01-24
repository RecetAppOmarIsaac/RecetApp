package dad.recetapp;


import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/dad/recetapp/ui/fxml/recetaFrameRoot.fxml"));

		Parent root = null;
		try {
			root = loader.load();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);

		stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
}
