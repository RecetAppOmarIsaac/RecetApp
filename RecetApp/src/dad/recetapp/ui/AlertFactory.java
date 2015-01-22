package dad.recetapp.ui;

import javafx.scene.control.Alert;

public class AlertFactory {
    private AlertFactory() {}

    public static Alert createInfoAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        return alert;
    }

    public static Alert createErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        return alert;
    }
}
