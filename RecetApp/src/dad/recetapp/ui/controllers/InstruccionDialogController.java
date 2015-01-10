package dad.recetapp.ui.controllers;

import dad.recetapp.services.receta.seccion.instruccion.InstruccionItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class InstruccionDialogController implements IDialogController<InstruccionItem> {
	public static final String NEW_CAPTION = "Añadir";
	public static final String EDIT_CAPTION = "Guardar cambios";

	@FXML private Parent rootPane;
	@FXML private TextField ordenTextField;
	@FXML private TextArea descripcionTextArea;
	@FXML private Button aceptarButton;

	private Optional<InstruccionItem> instruccion = Optional.empty();

	@FXML
	public void onAceptarButtonClick() {
		if (validate()) {
			InstruccionItem ii = instruccion.orElse(new InstruccionItem());
			ii.setOrden(Integer.valueOf(ordenTextField.getText()));
			ii.setDescripcion(descripcionTextArea.getText());
			instruccion = Optional.of(ii);
			Stage s = (Stage) rootPane.getScene().getWindow();
			s.close();
		} //TODO poner algo que diga que los datos no son validos
	}

	@FXML
	public void onCancelarButtonClick() {
		Stage s = (Stage) rootPane.getScene().getWindow();
		s.close();
	}

	private boolean validate() {
		boolean valid = true;
		try {
			Integer.parseInt(ordenTextField.getText());
		} catch (NumberFormatException e) {
			valid = false;
		}
		return valid;
	}

	@Override
	public Optional<InstruccionItem> getItem() {
		return instruccion;
	}

	@Override
	public void setItem(Optional<InstruccionItem> item) {
		InstruccionItem ii = instruccion.get();
		instruccion = Optional.of(ii);
		aceptarButton.setText(EDIT_CAPTION);
		ordenTextField.setText(ii.getOrden().toString());
		descripcionTextArea.setText(ii.getDescripcion());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
