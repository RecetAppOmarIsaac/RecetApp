package dad.recetapp.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Usuario on 25/12/14.
 */
public class RecetaTabContentController implements Initializable {
	@FXML private BorderPane topBorderPane;
	@FXML private TextField seccionTextField;

	@FXML private Button addIngredienteButton;
	@FXML private Button editarIngredienteButton;
	@FXML private Button borrarIngredienteButton;
	@FXML private Button addInstruccionButton;
	@FXML private Button editarInstruccionButton;
	@FXML private Button borrarInstruccionButton;

	@FXML private TableView ingredientesTable; //TODO add <> cuando IngredienteItem exista
	@FXML private TableColumn ingredientesCantidadColumn; //TODO add <IngredienteItem, Integer>
	@FXML private TableColumn ingredientesMedidaColumn; //TODO add <>
	@FXML private TableColumn ingredientesTipoColumn; //TODO add <>

	@FXML private TableView instruccionesTable; //TODO add <InstruccionItem>
	@FXML private TableColumn instruccionesOrdenColumn; //TODO add <>
	@FXML private TableColumn instruccionesTipoColumn; //TODO add <>

	private Optional<TabPane> parentTabPane = Optional.empty();
	private Optional<Tab> parentTab = Optional.empty();


	@FXML public void onSeccionTextKeyReleased() {
		parentTab.ifPresent(tab -> tab.setText(seccionTextField.getText()));
	}

	//Acoplamiento de narices
	@FXML public void onBorrarSeccionButtonClick() {
		parentTab.ifPresent(tab -> System.out.println(tab.getText()));
	}

	@FXML public void onAddIngredienteButtonClick() {
		System.out.println("Abrir dialogo de añadir ingredientes");
	}

	@FXML public void onEditarIngredienteButtonClick() {
		System.out.println("Abrir dialogo de editar ingredientes");
	}

	@FXML public void onBorrarIngredienteButtonClick() {
		System.out.println("Eliminar filas seleccionadas en tabla de ingredientes");
	}

	@FXML public void onAddInstruccionButtonClick() {
		System.out.println("Abrir dialogo de añadir instrucciones");
	}

	@FXML public void onEditarInstruccionButtonClick() {
		System.out.println("Abrir dialogo de editar instrucciones");
	}
	@FXML public void onBorrarInstruccionButtonClick() {
		System.out.println("Eliminar filas seleccionadas en tabla de instrucciones");
	}

	public Optional<Tab> getParentTab() {
		return parentTab;
	}

	public Optional<TabPane> getParentTabPane() {
		return parentTabPane;
	}

	public void setParentTab(Tab tab) {
		parentTab = Optional.of(tab);
		parentTabPane = Optional.ofNullable(tab.getTabPane());
	}

	public void setParentTabPane(TabPane tabPane) {
		parentTabPane = Optional.of(tabPane);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//TODO controllers para los dialogos pequeños
		//TODO eventos para lanzar esos dialogos
	}
}
