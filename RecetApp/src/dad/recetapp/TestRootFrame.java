package dad.recetapp;

import java.io.IOException;

import dad.recetapp.ui.fxml.RecetaFrameRootController;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TestRootFrame extends Application {
	private Stage primaryStage;
	private AnchorPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("RecetApp");
        this.primaryStage.getIcons().add(dad.recetapp.ui.images.Iconos.LOGO_ICON);
   
        initRootLayout();
 
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TestRootFrame.class.getResource("ui/fxml/recetaFrameRoot.fxml"));
            rootLayout = (AnchorPane) loader.load();

           
            Scene scene = new Scene(rootLayout);
            
            primaryStage.setScene(scene);
            RecetaFrameRootController controller=loader.getController();

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


   
    public Stage getPrimaryStage() {
        return primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
