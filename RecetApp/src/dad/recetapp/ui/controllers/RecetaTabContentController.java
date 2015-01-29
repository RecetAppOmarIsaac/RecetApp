package dad.recetapp.ui.controllers;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.*;
import dad.recetapp.ui.ItemDialog;
import dad.recetapp.ui.ItemDialogFactory;
import dad.recetapp.ui.model.items.IngredienteItemFX;
import dad.recetapp.ui.model.items.InstruccionItemFX;
import dad.recetapp.utils.Logs;
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
import java.util.List;
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

	@FXML private TableView<IngredienteItemFX> ingredientesTable;
	@FXML private TableColumn<IngredienteItemFX, Integer> ingredientesCantidadColumn;
	@FXML private TableColumn<IngredienteItemFX, MedidaItem> ingredientesMedidaColumn;
	@FXML private TableColumn<IngredienteItemFX, TipoIngredienteItem> ingredientesTipoColumn;

	@FXML private TableView<InstruccionItemFX> instruccionesTable;
	@FXML private TableColumn<InstruccionItemFX, Integer> instruccionesOrdenColumn;
	@FXML private TableColumn<InstruccionItemFX, String> instruccionesDescColumn;

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
		dialog.getItem().ifPresent(item -> ingredientesTable.getItems().add(IngredienteItemFX.fromIngredienteItem(item)));
	}

	@FXML public void onEditarIngredienteButtonClick() {
		IngredienteItem ii = ingredientesTable.getSelectionModel().getSelectedItem();
		if (ii != null)
			ItemDialogFactory.forIngredienteItem(ii).showModal();
	}

	@FXML public void onBorrarIngredienteButtonClick() {
		IngredienteItem ii = ingredientesTable.getSelectionModel().getSelectedItem();
		if (ii != null)
			ingredientesTable.getItems().remove(ii);
	}

	@FXML public void onAddInstruccionButtonClick() {
		ItemDialog<InstruccionItem> dialog = ItemDialogFactory.forInstruccionItem();
		dialog.showModal();
		dialog.getItem().ifPresent(item -> instruccionesTable.getItems().add(InstruccionItemFX.fromInstruccionItem(item)));
	}

	@FXML public void onEditarInstruccionButtonClick() {
		InstruccionItem ii = instruccionesTable.getSelectionModel().getSelectedItem();
		if (ii != null)
			ItemDialogFactory.forInstruccionItem(ii).showModal();
	}

	@FXML public void onBorrarInstruccionButtonClick() {
		InstruccionItem ii = instruccionesTable.getSelectionModel().getSelectedItem();
		if (ii != null)
			instruccionesTable.getItems().remove(ii);
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
		initCombos();
		initTables();
	}

	private void initTables() {
		//Por algun motivo, a setCellValueFactory no le gusta nada IntegerProperty, Pero como funciona bien asi pues aceptamos magia negra
		ingredientesCantidadColumn.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
		ingredientesCantidadColumn.setCellFactory(TextFieldTableCell.<IngredienteItemFX, Integer>forTableColumn(StringConverterFactory.forInteger()));
		ingredientesCantidadColumn.setOnEditCommit(cellEditEvent -> cellEditEvent.getRowValue().setCantidad(cellEditEvent.getNewValue()));

		ingredientesMedidaColumn.setCellValueFactory(cell -> cell.getValue().medidaProperty());
		ingredientesMedidaColumn.setCellFactory(ComboBoxTableCell.<IngredienteItemFX, MedidaItem>forTableColumn(medidaComboList));
		ingredientesMedidaColumn.setOnEditCommit(cellEditEvent -> cellEditEvent.getRowValue().setMedida(cellEditEvent.getNewValue()));

		ingredientesTipoColumn.setCellValueFactory(cell -> cell.getValue().tipoProperty());
		ingredientesTipoColumn.setCellFactory(ComboBoxTableCell.<IngredienteItemFX, TipoIngredienteItem>forTableColumn(tipoIngredienteComboList));
		ingredientesTipoColumn.setOnEditCommit(event -> event.getRowValue().setTipo(event.getNewValue()));

		instruccionesOrdenColumn.setCellValueFactory(new PropertyValueFactory<>("orden"));
		instruccionesOrdenColumn.setCellFactory(TextFieldTableCell.<InstruccionItemFX, Integer>forTableColumn(StringConverterFactory.forInteger()));
		instruccionesOrdenColumn.setOnEditCommit(cellEditEvent -> cellEditEvent.getRowValue().setOrden(cellEditEvent.getNewValue()));

		instruccionesDescColumn.setCellValueFactory(cell -> cell.getValue().descripcionProperty());
		instruccionesDescColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	}

	private void initCombos() {
		MedidaItem[] categorias = new MedidaItem[0];
		try {
			categorias = ServiceLocator.getMedidasService().listarMedidas();
		} catch (ServiceException e) {
			Logs.log(e);
		}
		medidaComboList = FXCollections.observableArrayList();
		medidaComboList.addAll(categorias);

		TipoIngredienteItem[] tipoIngredientes = new TipoIngredienteItem[0];
		try {
			tipoIngredientes = ServiceLocator.getTiposIngredientesService().listarTipoIngredientes();
		} catch (ServiceException e) {
			Logs.log(e);
		}
		tipoIngredienteComboList = FXCollections.observableArrayList();
		tipoIngredienteComboList.addAll(tipoIngredientes);
	}

	@Override
	public void setItem(Optional<SeccionItem> item) {
		SeccionItem si = item.get();
		seccion = Optional.of(si);
		String nombre;
		if (si.getNombre() == null || si.getNombre().equals(""))
			nombre = "Nueva seccion";
		else
			nombre = si.getNombre();
		parentTab.ifPresent(tab -> tab.setText(nombre));

		seccionTextField.setText(si.getNombre());

		ObservableList<InstruccionItemFX> instruccionFXList = FXCollections.observableArrayList();
		si.getInstrucciones().forEach(ii -> instruccionFXList.add(InstruccionItemFX.fromInstruccionItem(ii)));
		ObservableList<IngredienteItemFX> ingredienteFXList = FXCollections.observableArrayList();
		si.getIngredientes().forEach(ii -> ingredienteFXList.add(IngredienteItemFX.fromIngredienteItem(ii)));

		ingredientesTable.setItems(ingredienteFXList);
		instruccionesTable.setItems(instruccionFXList);
	}

	private void updateItem() {
		seccion.ifPresent(item -> {
			if (parentTab.isPresent()) {
				String s = parentTab.get().getText();
				item.setNombre(s != null ? s : "");
			}
			List<IngredienteItem> itemIngredList = item.getIngredientes();
			itemIngredList.clear();
			ingredientesTable.getItems().forEach(ii -> itemIngredList.add(ii.asIngredienteItem()));
			List<InstruccionItem> itemInstList = item.getInstrucciones();
			itemInstList.clear();
			instruccionesTable.getItems().forEach(ii -> itemInstList.add(ii.asInstruccionItem()));
		});
	}

	@Override
	public Optional<SeccionItem> getItem() {
		updateItem();
		return seccion;
	}
}
