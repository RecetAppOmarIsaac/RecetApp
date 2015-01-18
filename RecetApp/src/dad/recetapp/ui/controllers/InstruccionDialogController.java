package dad.recetapp.ui.controllers;

import dad.recetapp.services.items.InstruccionItem;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;

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

	private ValidationSupport validationSupport;

	private Optional<InstruccionItem> instruccion = Optional.empty();

	private void initValidation() {
		validationSupport = new ValidationSupport();
		validationSupport.setValidationDecorator(new GraphicValidationDecoration());
		validationSupport.registerValidator(ordenTextField, Validator.createRegexValidator("No es un numero valido", "[+-]?\\d+", Severity.ERROR));
		validationSupport.invalidProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue && !newValue)
				aceptarButton.setDisable(false);
			else
				aceptarButton.setDisable(true);
		});
	}

	@FXML
	public void onAceptarButtonClick() {
		InstruccionItem ii = instruccion.orElse(new InstruccionItem());
		ii.setOrden(Integer.valueOf(ordenTextField.getText()));
		ii.setDescripcion(descripcionTextArea.getText());
		instruccion = Optional.of(ii);
		Stage s = (Stage) rootPane.getScene().getWindow();
		s.close();
	}

	@FXML
	public void onCancelarButtonClick() {
		Stage s = (Stage) rootPane.getScene().getWindow();
		s.close();
	}

	@Override
	public Optional<InstruccionItem> getItem() {
		return instruccion;
	}

	@Override
	public void setItem(Optional<InstruccionItem> item) {
		InstruccionItem ii = item.get();
		instruccion = Optional.of(ii);
		aceptarButton.setText(EDIT_CAPTION);
		ordenTextField.setText(ii.getOrden().toString());
		descripcionTextArea.setText(ii.getDescripcion());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initValidation();
		aceptarButton.setText(NEW_CAPTION);
	}
}
