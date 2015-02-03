package dad.recetapp;

import java.io.IOException;
import dad.recetapp.ui.AlertFactory;
import dad.recetapp.ui.Splashscreen;
import dad.recetapp.utils.Logs;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
			Logs.log(e);
		}
		Scene scene = new Scene(root);

		Stage stage = new Stage();
		stage.setTitle("RecetApp");
		stage.getIcons().add(dad.recetapp.ui.images.Iconos.LOGO_ICON);
		stage.setScene(scene);
		stage.setOnCloseRequest(event -> {
            Alert alert = AlertFactory.createConfirmAlert("Salir", "\u00bfDesea salir de la aplicaci\u00f4n?");
            java.util.Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.CANCEL) {
                event.consume();
            }
        });
		return stage;
	}
}

