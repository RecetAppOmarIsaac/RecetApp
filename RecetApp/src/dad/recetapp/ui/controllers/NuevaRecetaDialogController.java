package dad.recetapp.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Usuario on 24/12/14.
 */
public class NuevaRecetaDialogController implements Initializable {
	@FXML private Button aceptarButton;

	@FXML public void onAceptarButtonClick() {}
	@FXML public void onCancelarButtonClick() {}

	@FXML public void onBorrarSeccionButtonClick() {}
	@FXML public void onNewTabSelection() {}

	@FXML public void onAddIngredienteButtonClick() {}
	@FXML public void onEditarIngredienteButtonClick() {}
	@FXML public void onBorrarIngredienteButtonClick() {}
	@FXML public void onAddInstruccionButtonClick() {}
	@FXML public void onEditarInstruccionButtonClick() {}
	@FXML public void onBorrarInstruccionButtonClick() {}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
