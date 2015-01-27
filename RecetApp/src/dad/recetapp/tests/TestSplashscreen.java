package dad.recetapp.tests;

import java.io.IOException;

import dad.recetapp.ui.Splashscreen;
import dad.recetapp.utils.Logs;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class TestSplashscreen extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Splashscreen sp = new Splashscreen();
		sp.setOnFinished(ae -> initRootFrame().show());
		sp.show(4000);
	}

	private  Stage initRootFrame() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/dad/recetapp/ui/fxml/recetaFrameRoot.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		}
		catch (IOException e) {
			Logs.log(e);
		}
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("RecetApp");
		stage.getIcons().add(dad.recetapp.ui.images.Iconos.LOGO_ICON);
		return stage;
	}
	
}
