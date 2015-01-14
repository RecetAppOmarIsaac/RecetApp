package dad.recetapp.ui;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Splashscreen {
	private static final String IMAGE_PATH = "dad/recetapp/ui/images/recetapp-inicio.png";
	private static final double FADEOUT_DURATION = 300;
	private Stage stage;
	private BorderPane root;
	private Timeline fadeAndCloseTransition;
	private boolean clicked = false;

	public Splashscreen() {
		ImageView iv = new ImageView(new Image(IMAGE_PATH));
		iv.setOnMouseClicked(ae -> {
			if (!clicked) { //nos aseguramos que solo intenta lanzar la animacion UNA vez
				clicked = true;
				fadeAndCloseTransition.play();
			}
		});
		root = new BorderPane();
		root.setCenter(iv);

		stage = new Stage(StageStyle.UNDECORATED);
		stage.setScene(new Scene(root));

		fadeAndCloseTransition = new Timeline(
				new KeyFrame(Duration.ZERO, ae1 -> {
					FadeTransition fd = new FadeTransition(Duration.millis(FADEOUT_DURATION), root);
					fd.setToValue(0.4);
					fd.play();
				}),
				new KeyFrame(Duration.millis(FADEOUT_DURATION), ae2 -> {
					stage.close();
				})
		);
	}

	public void setOnFinished(EventHandler<ActionEvent> eventHandler) {
		fadeAndCloseTransition.setOnFinished(eventHandler);
	}

	public void show(double ms) {
		Timeline timer = new Timeline(new KeyFrame(Duration.millis(ms), ae -> fadeAndCloseTransition.play()));
		stage.show();
		timer.play();
	}
}
