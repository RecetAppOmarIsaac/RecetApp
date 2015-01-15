package dad.recetapp.ui;

import dad.recetapp.services.IItem;
import dad.recetapp.ui.controllers.IDialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;

public class ItemDialog<T extends IItem> {
    private IDialogController<T> controller;
    private Stage stage;

    public ItemDialog(String fxmlPath, IDialogController controller) throws IOException {
        if (controller == null)
            throw new NullPointerException("controller no puede ser nulo");
        this.controller = controller;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

        loader.setController(controller);

        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
    }

    public void show() {
        if (stage != null)
            stage.show();
    }

    public void showModal() {
        if (stage != null) {
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
    }

    public void close() {
        if (stage != null)
            stage.close();
    }

    public Optional<T> getItem() {
        return controller.getItem();
    }

    public void setItem(T item) {
        controller.setItem(Optional.of(item));
    }
}
