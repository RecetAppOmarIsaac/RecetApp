package dad.recetapp.ui;

import dad.recetapp.services.IItem;
import dad.recetapp.ui.controllers.IDialogController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by Alumno on 09/01/2015.
 */
public class ItemDialog<T extends IItem> {
    private String fxmlPath;
    private IDialogController<T> controller;
    private Stage stage;

    public ItemDialog(String fxmlPath, IDialogController controller) throws IOException {
        if (controller == null)
            throw new NullPointerException("controller no puede ser nulo");
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

        loader.setController(controller);

        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage = new Stage();
        stage.setScene(scene);
    }

    public void show() {
        if (stage != null)
            stage.show();
    }

    public void close() {
        if (stage != null)
            stage.close();
    }

    public T getItem() {
        return controller.getItem().get();
    }

    public void setItem(T item) {
        controller.setItem(Optional.of(item));
    }
}
