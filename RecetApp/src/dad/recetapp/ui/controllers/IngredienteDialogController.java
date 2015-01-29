package dad.recetapp.ui.controllers;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.IngredienteItem;
import dad.recetapp.services.items.MedidaItem;
import dad.recetapp.services.items.TipoIngredienteItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;

import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;


public class IngredienteDialogController implements IDialogController<IngredienteItem> {
	public static final String NEW_CAPTION = "A\u00f1adir";
	public static final String EDIT_CAPTION = "Guardar cambios";

	@FXML private Parent rootPane;
	@FXML private TextField cantidadTextField;
	@FXML private ComboBox<MedidaItem> medidaCombo;
	@FXML private ComboBox<TipoIngredienteItem> tipoCombo;
	@FXML private Button aceptarButton;

	private ValidationSupport validationSupport;
	private Optional<IngredienteItem> ingrediente = Optional.empty();

	private void initCombos() {
		MedidaItem mi = new MedidaItem();
		mi.setNombre("<Seleccione una medida>");
		medidaCombo.setValue(mi);

		Task<ObservableList<MedidaItem>> medidaTask = new Task<ObservableList<MedidaItem>>() {
			@Override
			protected ObservableList<MedidaItem> call() {
				MedidaItem[] medidaArray = new MedidaItem[0];
				try {
					medidaArray = ServiceLocator.getMedidasService().listarMedidas();
				}
				catch (ServiceException e) {
					System.err.println("MedidaService Error: " + e.getMessage() + " Cause: " + e.getCause());
				}
				ObservableList<MedidaItem> medidaList = FXCollections.observableArrayList(Arrays.asList(medidaArray));
				return medidaList;
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				medidaCombo.getItems().addAll(getValue());
			}
		};
		medidaTask.run();

		TipoIngredienteItem tii = new TipoIngredienteItem();
		tii.setNombre("<Seleccione un ingrediente>");
		tipoCombo.setValue(tii);
		Task<ObservableList<TipoIngredienteItem>> tipoTask = new Task<ObservableList<TipoIngredienteItem>>() {
			@Override
			protected ObservableList<TipoIngredienteItem> call() {
				TipoIngredienteItem[] tipoArray = new TipoIngredienteItem[0];
				try {
					tipoArray = ServiceLocator.getTiposIngredientesService().listarTipoIngredientes();
				}
				catch (ServiceException e) {
					System.err.println("TipoIngredientesService Error: " + e.getMessage() + " Cause: " + e.getCause());
				}
				ObservableList<TipoIngredienteItem> tipoList = FXCollections.observableArrayList(Arrays.asList(tipoArray));
				return tipoList;
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				tipoCombo.getItems().addAll(getValue());
			}
		};
		tipoTask.run();
	}

	private void initValidation() {
		validationSupport = new ValidationSupport();
		validationSupport.setValidationDecorator(new GraphicValidationDecoration());
		validationSupport.registerValidator(cantidadTextField, Validator.createRegexValidator("No es un numero v\u00e1lido", "[+-]?\\d+", Severity.ERROR));
		validationSupport.registerValidator(medidaCombo, Validator.createEqualsValidator("Elija una medida", Severity.ERROR, medidaCombo.getItems()));
		validationSupport.registerValidator(tipoCombo, Validator.createEqualsValidator("Elija un ingrediente", Severity.ERROR, tipoCombo.getItems()));
		validationSupport.invalidProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue && !newValue)
				aceptarButton.setDisable(false);
			else
				aceptarButton.setDisable(true);
		});
	}

	@FXML
	public void onAceptarButtonClick() {
		IngredienteItem ii = ingrediente.orElse(new IngredienteItem());
		ii.setCantidad(Integer.valueOf(cantidadTextField.getText()));
		ii.setMedida(medidaCombo.getValue());
		ii.setTipo(tipoCombo.getValue());
		ingrediente = Optional.of(ii);
		Stage s = (Stage) rootPane.getScene().getWindow();
		s.close();
	}

	@FXML
	public void onCancelarButtonClick() {
		Stage s = (Stage) rootPane.getScene().getWindow();
		s.close();
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initCombos();
		initValidation();
		aceptarButton.setText(NEW_CAPTION);
	}

	@Override
	public void setItem(Optional<IngredienteItem> item) {
		IngredienteItem ii = item.get();
		ingrediente = Optional.of(ii);
		aceptarButton.setText(EDIT_CAPTION);
		cantidadTextField.setText(ii.getCantidad().toString());
		medidaCombo.setValue(ii.getMedida());
		tipoCombo.setValue(ii.getTipo());
	}

	@Override
	public Optional<IngredienteItem> getItem() {
		return ingrediente;
	}
}
