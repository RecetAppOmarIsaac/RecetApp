package dad.recetapp;

import dad.recetapp.ui.ItemDialogFactory;
import dad.recetapp.ui.Splashscreen;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

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
