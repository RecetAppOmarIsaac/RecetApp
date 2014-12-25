package dad.recetapp.ui.controllers;

import dad.recetapp.services.recetas.RecetaItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import jfxtras.scene.control.ListSpinner;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Usuario on 24/12/14.
 */
public class EditarRecetaDialogController implements Initializable {
	@FXML private Button aceptarButton;

	@FXML private ListSpinner<Integer> totalMinutosSpinner;
	@FXML private ListSpinner<Integer> thermoMinutosSpinner;
	@FXML private ListSpinner<Integer> totalSegundosSpinner;
	@FXML private ListSpinner<Integer> thermoSegundosSpinner;

	@FXML private TabPane seccionTabPane;
	@FXML private Tab newTab;

	@FXML private TextField seccionTextField;

	@FXML private TableView ingredientesTable; //TODO add <> cuando IngredienteItem exista
	@FXML private TableColumn ingredientesCantidadColumn; //TODO add <IngredienteItem, Integer>
	@FXML private TableColumn ingredientesMedidaColumn; //TODO add <>
	@FXML private TableColumn ingredientesTipoColumn; //TODO add <>

	@FXML private TableView instruccionesTable; //TODO add <InstruccionItem>
	@FXML private TableColumn instruccionesOrdenColumn; //TODO add <>
	@FXML private TableColumn instruccionesTipoColumn; //TODO add <>



	@FXML public void onAceptarButtonClick() {}
	@FXML public void onCancelarButtonClick() {}

	@FXML public void onBorrarSeccionButtonClick() {}
	@FXML public void onNewTabSelection() {}

	@FXML public void onSeccionTextKeyReleased() {}

	@FXML public void onAddIngredienteButtonClick() {}
	@FXML public void onEditarIngredienteButtonClick() {}
	@FXML public void onBorrarIngredienteButtonClick() {}
	@FXML public void onAddInstruccionButtonClick() {}
	@FXML public void onEditarInstruccionButtonClick() {}
	@FXML public void onBorrarInstruccionButtonClick() {}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		aceptarButton.setText("Guardar cambios");
	}
}
