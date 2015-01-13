package dad.recetapp.ui.controllers;

import dad.recetapp.services.items.InstruccionItem;
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

public class InstruccionDialogController implements Initializable {
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
		}
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

	public Optional<InstruccionItem> getInstruccion() {
		return instruccion;
	}

	public void setInstruccion(InstruccionItem item) {
		instruccion = Optional.of(item);
		aceptarButton.setText(EDIT_CAPTION);
		InstruccionItem ii = instruccion.get();
		ordenTextField.setText(ii.getOrden().toString());
		descripcionTextArea.setText(ii.getDescripcion());
	}

	public InstruccionDialogController editingInstruccion(InstruccionItem item) {
		setInstruccion(item);
		return this;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
