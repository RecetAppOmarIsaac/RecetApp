package dad.recetapp.ui.controllers;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.receta.seccion.ingrediente.TipoIngredienteItem;
import dad.recetapp.services.receta.seccion.ingrediente.medida.MedidaItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Created by Usuario on 04/01/15.
 */
//Y si pongo un setEditable() que meramente cambie el caption de aceptarButton y se evita uno dos controllers casi iguales?
public class NuevoIngredienteDialogController implements Initializable {
	@FXML private TextField cantidadTextField;
	@FXML private ComboBox<MedidaItem> medidaCombo;
	@FXML private ComboBox<TipoIngredienteItem> tipoCombo;
	@FXML private Button aceptarButton;

	private String aceptarButtonCaption = "Añadir";

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

	}

	@FXML
	public void onCancelarButtonClick() {
		//cerrar el dialog, sin mas
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initCombos();
		aceptarButton.setText(aceptarButtonCaption);
	}
}
