package dad.recetapp.ui.controllers;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.*;
import dad.recetapp.ui.ItemDialog;
import dad.recetapp.ui.ItemDialogFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import jfxtras.util.StringConverterFactory;

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

	private ObservableList<MedidaItem> medidaComboList;
	private ObservableList<TipoIngredienteItem> tipoIngredienteComboList;

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
		ItemDialog<IngredienteItem> dialog = ItemDialogFactory.forIngredienteItem();
		dialog.showModal();
		dialog.getItem().ifPresent(item -> ingredientesTable.getItems().add(item));
	}

	@FXML public void onEditarIngredienteButtonClick() {
		System.out.println("Abrir dialogo de editar ingredientes");
	}

	@FXML public void onBorrarIngredienteButtonClick() {
		System.out.println("Eliminar filas seleccionadas en tabla de ingredientes");
	}

	@FXML public void onAddInstruccionButtonClick() {
		ItemDialog<InstruccionItem> dialog = ItemDialogFactory.forInstruccionItem();
		dialog.showModal();
		dialog.getItem().ifPresent(item -> instruccionesTable.getItems().add(item));
	}

	@FXML public void onEditarInstruccionButtonClick() {
		//ItemDialog<InstruccionItem> dialog = ItemDialogFactory.forInstruccionItem(instruccionesTable.getSelectionModel().getSelectedItem());
		//dialog.showModal();
		//dialog.getItem().ifPresent();
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
		initCombos();
		initTables();
	}

	private void initTables() {
		ingredientesCantidadColumn.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
		ingredientesCantidadColumn.setCellFactory(TextFieldTableCell.<IngredienteItem, Integer>forTableColumn(StringConverterFactory.forInteger()));
		ingredientesCantidadColumn.setOnEditCommit(cellEditEvent -> cellEditEvent.getRowValue().setCantidad(cellEditEvent.getNewValue()));

		ingredientesMedidaColumn.setCellValueFactory(new PropertyValueFactory<>("medida"));
		ingredientesMedidaColumn.setCellFactory(ComboBoxTableCell.<IngredienteItem, MedidaItem>forTableColumn(medidaComboList));
		ingredientesMedidaColumn.setOnEditCommit(cellEditEvent -> cellEditEvent.getRowValue().setMedida(cellEditEvent.getNewValue()));

		ingredientesTipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
		ingredientesTipoColumn.setCellFactory(ComboBoxTableCell.<IngredienteItem, TipoIngredienteItem>forTableColumn(tipoIngredienteComboList));
		ingredientesTipoColumn.setOnEditCommit(event -> event.getRowValue().setTipo(event.getNewValue()));

		instruccionesOrdenColumn.setCellValueFactory(new PropertyValueFactory<>("orden"));
		instruccionesOrdenColumn.setCellFactory(TextFieldTableCell.<InstruccionItem, Integer>forTableColumn(StringConverterFactory.forInteger()));
		instruccionesOrdenColumn.setOnEditCommit(cellEditEvent -> cellEditEvent.getRowValue().setOrden(cellEditEvent.getNewValue()));

		instruccionesDescColumn.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
		instruccionesDescColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	}

	private void initCombos() {
		//TODO esto hace llorar al niño jesus
		MedidaItem[] categorias = new MedidaItem[0];
		try {
			categorias = ServiceLocator.getMedidasService().listarMedidas();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		medidaComboList = FXCollections.observableArrayList();
		medidaComboList.addAll(categorias);

		TipoIngredienteItem[] tipoIngredientes = new TipoIngredienteItem[0];
		try {
			tipoIngredientes = ServiceLocator.getTipoIngredienteService().listarTipoIngredientes();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		tipoIngredienteComboList = FXCollections.observableArrayList();
		tipoIngredienteComboList.addAll(tipoIngredientes);
	}

	@Override
	public void setItem(Optional<SeccionItem> item) {
		SeccionItem si = item.get();
		seccion = Optional.of(si);
		parentTab.ifPresent(tab -> tab.setText(si.getNombre()));

		seccionTextField.setText(si.getNombre());

		ingredientesTable.setItems(FXCollections.observableArrayList(si.getIngredientes()));
		instruccionesTable.setItems(FXCollections.observableArrayList(si.getInstrucciones()));
	}

	@Override
	public Optional<SeccionItem> getItem() {
		return seccion;
	}
}
