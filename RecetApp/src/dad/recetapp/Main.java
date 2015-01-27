package dad.recetapp;

import java.io.IOException;


import dad.recetapp.ui.Splashscreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		Splashscreen sp = new Splashscreen();
		sp.setOnFinished(ae -> lanzar().show());
		sp.show(4000);
	}
	
	private  Stage lanzar() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/dad/recetapp/ui/fxml/recetaFrameRoot.fxml"));

		Parent root = null;
		try {
			root = loader.load();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);

		Stage stage = new Stage();
		stage.setTitle("RecetApp");
		stage.getIcons().add(dad.recetapp.ui.images.Iconos.LOGO_ICON);
		stage.setScene(scene);
		return stage;
	}
	
	
	
}

