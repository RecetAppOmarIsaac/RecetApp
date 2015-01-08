package dad.recetapp.ui.controllers;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.receta.seccion.ingrediente.IngredienteItem;
import dad.recetapp.services.receta.seccion.ingrediente.TipoIngredienteItem;
import dad.recetapp.services.receta.seccion.ingrediente.medida.MedidaItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;


public class IngredienteDialogController implements Initializable {
	public static final String NEW_CAPTION = "Añadir";
	public static final String EDIT_CAPTION = "Guardar cambios";

	@FXML private Parent rootPane;
	@FXML private TextField cantidadTextField;
	@FXML private ComboBox<MedidaItem> medidaCombo;
	@FXML private ComboBox<TipoIngredienteItem> tipoCombo;
	@FXML private Button aceptarButton;
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

		Task<ObservableList<TipoIngredienteItem>> tipoTask = new Task<ObservableList<TipoIngredienteItem>>() {
			@Override
			protected ObservableList<TipoIngredienteItem> call() {
				TipoIngredienteItem[] tipoArray = new TipoIngredienteItem[0];
				try {
					tipoArray = ServiceLocator.getTipoIngredienteService().listarTipoIngredientes();
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

	@FXML
	public void onAceptarButtonClick() {
		if (validate()) {
			IngredienteItem ii = ingrediente.orElse(new IngredienteItem());
			ii.setCantidad(Integer.valueOf(cantidadTextField.getText()));
			ii.setMedida(medidaCombo.getValue());
			ii.setTipoIngrediente(tipoCombo.getValue());
			ingrediente = Optional.of(ii);
			Stage s = (Stage) rootPane.getScene().getWindow();
			s.close();
		}
	}

	@FXML
	public void onCancelarButtonClick() {
		Stage s = (Stage) rootPane.getScene().getWindow();
		s.close();
	}

	public Optional<IngredienteItem> getIngrediente() {
		return ingrediente;
	}

	/**
	 * Asume que si se pone un Item es para editarlo y cambiara el texto del boton.
	 * @param item
	 */
	public void setIngrediente(IngredienteItem item) {
		ingrediente = Optional.of(item);
		aceptarButton.setText(EDIT_CAPTION);
		IngredienteItem ii = ingrediente.get();
		cantidadTextField.setText(ii.getCantidad().toString());
		medidaCombo.setValue(ii.getMedida());
		tipoCombo.setValue(ii.getTipoIngrediente());
	}

	public IngredienteDialogController editingIngrediente(IngredienteItem item) {
		setIngrediente(item);
		return this;
	}

	private boolean validate() {
		boolean valid = true;
		try {
			Integer.parseInt(cantidadTextField.getText());
		} catch (NumberFormatException e) {
			valid = false;
		}
		if (!medidaCombo.getItems().contains(medidaCombo.getValue()))
			valid = false;
		if (!tipoCombo.getItems().contains(tipoCombo.getValue()))
			valid = false;
		return valid;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initCombos();
		aceptarButton.setText(NEW_CAPTION);
	}
}
