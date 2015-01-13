package dad.recetapp;

import dad.recetapp.ui.ItemDialogFactory;
import dad.recetapp.ui.Splashscreen;
import javafx.application.Application;
import javafx.stage.Stage;


public class TestSplashscreen extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Splashscreen sp = new Splashscreen();
		sp.setOnFinished(ae -> ItemDialogFactory.forInstruccionItem().show());
		sp.show(4000);
	}
}
