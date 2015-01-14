package dad.recetapp.ui.controllers;

import dad.recetapp.services.items.IngredienteItem;
import dad.recetapp.services.items.InstruccionItem;
import dad.recetapp.services.items.MedidaItem;
import dad.recetapp.services.items.SeccionItem;
import dad.recetapp.services.items.TipoIngredienteItem;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class RecetaTabContentController implements IDialogController<SeccionItem> {
	@FXML private BorderPane topBorderPane;
	@FXML private TextField seccionTextField;

	@FXML private Button addIngredienteButton;
	@FXML private Button editarIngredienteButton;
	@FXML private Button borrarIngredienteButton;
	@FXML private Button addInstruccionButton;
	@FXML private Button editarInstruccionButton;
	@FXML private Button borrarInstruccionButton;

	@FXML private TableView<IngredienteItem> ingredientesTable;
	@FXML private TableColumn<IngredienteItem, Integer> ingredientesCantidadColumn;
	@FXML private TableColumn<IngredienteItem, MedidaItem> ingredientesMedidaColumn;
	@FXML private TableColumn<IngredienteItem, TipoIngredienteItem> ingredientesTipoColumn;

	@FXML private TableView<InstruccionItem> instruccionesTable;
	@FXML private TableColumn<InstruccionItem, Integer> instruccionesOrdenColumn;
	@FXML private TableColumn<InstruccionItem, String> instruccionesDescColumn;

	private Optional<TabPane> parentTabPane = Optional.empty();
	private Optional<Tab> parentTab = Optional.empty();
	private Optional<SeccionItem> seccion = Optional.empty();


	@FXML public void onSeccionTextKeyReleased() {
		parentTab.ifPresent(tab -> tab.setText(seccionTextField.getText()));
	}


	@FXML public void onBorrarSeccionButtonClick() {
		parentTabPane.ifPresent(tabPane -> tabPane.getTabs().remove(parentTab.get()));
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
		parentTabPane = Optional.ofNullable(tab.getTabPane()); //A estas alturas, normalmente es nulo
	}

	public RecetaTabContentController withParentTab(Tab tab) {
		setParentTab(tab);
		return this;
	}

	public void setParentTabPane(TabPane tabPane) {
		parentTabPane = Optional.of(tabPane);
	}

	public RecetaTabContentController withParentTabPane(TabPane tabPane) {
		setParentTabPane(tabPane);
		return this;
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//TODO eventos para lanzar esos dialogos
	}

	@Override
	public void setItem(Optional<SeccionItem> item) {
		System.out.println("Tab setItem called, item: " + item + " " + item.get());
		SeccionItem si = item.get();
		seccion = Optional.of(si);

		parentTab.ifPresent(tab -> tab.setText(si.getNombre()));

		//Esto de aqui abajo no esta funcionando
		ingredientesTable.setItems(FXCollections.observableArrayList(si.getIngredientes()));
		ingredientesCantidadColumn.setCellValueFactory(new PropertyValueFactory<IngredienteItem, Integer>("cantidad"));
		ingredientesMedidaColumn.setCellValueFactory(new PropertyValueFactory<IngredienteItem, MedidaItem>("medida"));
		ingredientesTipoColumn.setCellValueFactory(new PropertyValueFactory<IngredienteItem, TipoIngredienteItem>("tipoIngrediente"));
		//instruccionesTable.setItems(FXCollections.observableArrayList(si.getInstrucciones()));
		//TODO poner datos en controles
	}

	@Override
	public Optional<SeccionItem> getItem() {
		return seccion;
	}
}
