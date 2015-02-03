package dad.recetapp.ui;

import dad.recetapp.ui.images.Iconos;
import javafx.scene.control.Alert;
import javafx.stage.Stage;



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

    public static Alert createDeleteAlert(String title,String header, String content){
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    	stage.getIcons().add(Iconos.DELETE_ICON);
    	alert.setTitle(title);
    	alert.setHeaderText(header);
    	alert.setContentText(content);
    	return alert;

    }
    
    public static Alert createConfirmAlert(String title,String header){
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    	stage.getIcons().add(Iconos.LOGO_ICON);
    	alert.setTitle(title);
    	alert.setHeaderText(header);
    	return alert;
    }
}
