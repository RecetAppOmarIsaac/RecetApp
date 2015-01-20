package dad.recetapp.ui.fxml;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.controlsfx.dialog.Dialogs;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.CategoriaItem;
import dad.recetapp.services.items.MedidaItem;
import dad.recetapp.services.items.RecetaItem;
import dad.recetapp.services.items.RecetaListItem;
import dad.recetapp.services.items.TipoAnotacionItem;
import dad.recetapp.services.items.TipoIngredienteItem;
import dad.recetapp.ui.ItemDialog;
import dad.recetapp.ui.ItemDialogFactory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.FlowPane;
import javafx.util.converter.DateStringConverter;
import jfxtras.util.StringConverterFactory;

public class RecetaFrameRootController {

	// ventana pricipal
	@FXML
	private Label cantidadRecetaLabel;
	@FXML
	private TabPane rootTabPane;
	// pestaña Recetas
	@FXML
	private Tab recetasTab;
	@FXML
	private FlowPane anyadirRecetaPanel;
	@FXML
	private TableView<RecetaListItem> recetTable;
	@FXML
	private TableColumn<RecetaListItem, String> recetaNombreColumn;
	@FXML
	private TableColumn<RecetaListItem, String> recetaParaColumn;
	@FXML
	private TableColumn<RecetaListItem, Integer> recetaTiempoTotalColumn;
	@FXML
	private TableColumn<RecetaListItem, Date> recetaFechaCreacionColumn;
	@FXML
	private TableColumn<RecetaListItem, String> recetaCategoriaColumn;

	@FXML
	private Label nombreRecetaLabel;
	@FXML
	private TextField nombreRecetaTextField;
	@FXML
	private Label hastaRecetaLabel;
	@FXML
	private Label minutoRecetaLabel;
	@FXML
	private Label segundoRecetaLabel;
	@FXML
	private Label categoriaRecetaLabel;
	@FXML
	private ComboBox<CategoriaItem> categoriaItemCombo;

	// TODO FALTAN LOS SPINNER DE EL TIEMPO DE LA RECETA

	@FXML
	private Button anyadirRecetaButton;
	@FXML
	private Button eliminarRecetaButton;
	@FXML
	private Button editarRecetaButton;

	private ObservableList<RecetaListItem> recetData;

	// pestaña categorias
	@FXML
	private Tab categoriasTab;
	@FXML
	private TableView<CategoriaItem> cateTable;
	@FXML
	private TableColumn<CategoriaItem, String> cateDescripColumn;
	@FXML
	private Label descripCateLabel;
	@FXML
	private TextField descripCatTextField;
	@FXML
	private Button anyadirCateButton;
	@FXML
	private Button eliminarCateButton;
	private ObservableList<CategoriaItem> cateData;

	// pestaña ingredientes
	@FXML
	private Tab ingredientesTab;
	@FXML
	private TableView<TipoIngredienteItem> ingreTable;
	@FXML
	private TableColumn<TipoIngredienteItem, String> ingreNombreColumn;
	@FXML
	private Label nombreIngreLabel;
	@FXML
	private TextField nombreIngreTextField;
	@FXML
	private Button anyadirIngreButton;
	@FXML
	private Button eliminarIngreButton;
	private ObservableList<TipoIngredienteItem> ingreData;

	// pestaña medidas
	@FXML
	private Tab medidasTab;
	@FXML
	private TableView<MedidaItem> medidasTable;
	@FXML
	private TableColumn<MedidaItem, String> medidaNombreColumn;
	@FXML
	private TableColumn<MedidaItem, String> medidaAbreviaturaColumn;
	@FXML
	private Label nombreMedidaLabel;
	@FXML
	private TextField nombreMedidaTextField;
	@FXML
	private Label abrevMedidaLabel;
	@FXML
	private TextField abrevMedidaTextField;
	@FXML
	private Button anyadirMedidaButton;
	@FXML
	private Button eliminarMedidaButton;
	private ObservableList<MedidaItem> medidaData;

	// pestaña anotaciones
	@FXML
	private Tab anotacionesTab;
	@FXML
	private TableView<TipoAnotacionItem> anotacionesTable;
	@FXML
	private TableColumn<TipoAnotacionItem, String> anotaDescripColumn;
	@FXML
	private Label descripAnotaLabel;
	@FXML
	private TextField descripAnotaTextField;
	@FXML
	private Button anyadirAnotaButton;
	@FXML
	private Button eliminarAnotaButton;
	private ObservableList<TipoAnotacionItem> anotacionData;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public RecetaFrameRootController() {
		cargarRecetData();
		cargarCateData();
		cargarIngreData();
		cargarMedidaData();
		cargarAnotacionData();
	}

	private void cargarAnotacionData() {
		TipoAnotacionItem[] anotaciones = new TipoAnotacionItem[0];
		try {
			anotaciones = ServiceLocator.getTiposAnotacionesService()
					.listarTiposAnotaciones();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		anotacionData = FXCollections.observableArrayList(anotaciones);

	}

	private void cargarMedidaData() {
		MedidaItem[] medidas = new MedidaItem[0];
		try {
			// cargo las recetas
			medidas = ServiceLocator.getMedidasService().listarMedidas();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		medidaData = FXCollections.observableArrayList(medidas);
	}

	private void cargarIngreData() {
		TipoIngredienteItem[] ingredientes = new TipoIngredienteItem[0];
		// cargo las categorias
		try {
			ingredientes = ServiceLocator.getTipoIngredienteService()
					.listarTipoIngredientes();

		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ingreData = FXCollections.observableArrayList(Arrays
				.asList(ingredientes));
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// initTabRecetas();

		initCantidadRecetasLabel();
	}

	private void initCantidadRecetasLabel() {
		cantidadRecetaLabel.setText(Integer.toString(recetData.size()));

	}

	private void initTabRecetas() {
		cargarDatosRecetTable();
		initRecetTable();
		initCombosReceta();
	}

	private void cargarDatosRecetTable() {
		recetTable.setItems(recetData);
	}

	private void cargarRecetData() {
		List<RecetaListItem> recetas = null;
		try {
			// cargo las recetas
			recetas = ServiceLocator.getRecetasService().listarRecetas();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		recetData = FXCollections.observableArrayList(recetas);
	}

	private void initRecetTable() {
		recetaNombreColumn.setCellValueFactory(cell -> cell.getValue()
				.nombreProperty());
		recetaNombreColumn.setCellFactory(TextFieldTableCell
				.<RecetaListItem, String> forTableColumn(StringConverterFactory
						.forString()));
		recetaNombreColumn.setOnEditCommit(cellEditEvent -> cellEditEvent
				.getRowValue().setNombre(cellEditEvent.getNewValue()));

		recetaParaColumn.setCellValueFactory(cell -> cell.getValue()
				.paraProperty());
		recetaParaColumn.setCellFactory(TextFieldTableCell
				.<RecetaListItem, String> forTableColumn(StringConverterFactory
						.forString()));
		recetaParaColumn.setOnEditCommit(cellEditEvent -> cellEditEvent
				.getRowValue().setPara(cellEditEvent.getNewValue()));

		recetaTiempoTotalColumn
				.setCellValueFactory(new PropertyValueFactory<RecetaListItem, Integer>(
						"tiempoTotal"));
		recetaTiempoTotalColumn
				.setCellFactory(TextFieldTableCell
						.<RecetaListItem, Integer> forTableColumn(StringConverterFactory
								.forInteger()));
		recetaTiempoTotalColumn.setOnEditCommit(cellEditEvent -> cellEditEvent
				.getRowValue().setTiempoTotal(cellEditEvent.getNewValue()));

		recetaFechaCreacionColumn
				.setCellValueFactory(new PropertyValueFactory<RecetaListItem, Date>(
						"fechaCreacion"));
		recetaFechaCreacionColumn.setCellFactory(TextFieldTableCell
				.<RecetaListItem, Date> forTableColumn(new DateStringConverter(
						"dd/MM/yyyy")));
		recetaFechaCreacionColumn
				.setOnEditCommit(cellEditEvent -> cellEditEvent.getRowValue()
						.setFechaCreacion(cellEditEvent.getNewValue()));

		recetaCategoriaColumn
				.setCellValueFactory(new PropertyValueFactory<RecetaListItem, String>(
						"categoria"));
		recetaCategoriaColumn.setCellFactory(TextFieldTableCell
				.<RecetaListItem, String> forTableColumn(StringConverterFactory
						.forString()));
		recetaCategoriaColumn.setOnEditCommit(cellEditEvent -> cellEditEvent
				.getRowValue().setPara(cellEditEvent.getNewValue()));
	}

	private void initCombosReceta() {
		CategoriaItem ci = new CategoriaItem();
		ci.setDescripcion("<Todas>"); // TODO modificar toString()'s de los
										// Items para que solo devuelvan su
										// campo String.
		categoriaItemCombo.setValue(ci);
		Task<ObservableList<CategoriaItem>> task = new Task<ObservableList<CategoriaItem>>() {
			@Override
			protected ObservableList<CategoriaItem> call() {
				CategoriaItem[] c = new CategoriaItem[0];
				try {
					c = ServiceLocator.getCategoriasService().listarCategoria();
				} catch (ServiceException e) {
					System.err.println("CategoriaService Error: "
							+ e.getMessage() + " Cause: " + e.getCause());
				}

				return FXCollections.observableArrayList(Arrays.asList(c));
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				categoriaItemCombo.getItems().addAll(getValue());

			}
		};
		task.run();
	}

	private void initTabCategorias() {
		cargarDatosCateTable();
		initCateTable();

	}

	private void cargarDatosCateTable() {
		cateTable.setItems(cateData);

	}

	private void cargarCateData() {
		CategoriaItem[] c = new CategoriaItem[0];
		// cargo las categorias
		try {
			c = ServiceLocator.getCategoriasService().listarCategoria();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		cateData = FXCollections.observableArrayList(Arrays.asList(c));
	}

	private void initCateTable() {
		cateDescripColumn
				.setCellValueFactory(new PropertyValueFactory<CategoriaItem, String>(
						"descripcion"));
		cateDescripColumn.setCellFactory(TextFieldTableCell
				.<CategoriaItem, String> forTableColumn(StringConverterFactory
						.forString()));
		cateDescripColumn.setOnEditCommit(cellEditEvent -> cellEditEvent
				.getRowValue().setDescripcion(cellEditEvent.getNewValue()));

	}

	@FXML
	public void onRecetasTabSelected() {
		if (recetasTab.isSelected()) {
			initTabRecetas();

		}
	}

	@FXML
	public void onCantidadTabSelected() {
		if (categoriasTab.isSelected()) {
			initTabCategorias();
		}
	}

	@FXML
	public void onIngredientesTabSelected() {
		if (ingredientesTab.isSelected()) {
			initTabIngredientes();
		}
	}

	private void initTabIngredientes() {
		cargarDatosIngreTable();
		initIngreTable();

	}

	private void cargarDatosIngreTable() {
		ingreTable.setItems(ingreData);

	}

	private void initIngreTable() {
		ingreNombreColumn
				.setCellValueFactory(new PropertyValueFactory<TipoIngredienteItem, String>(
						"nombre"));
		ingreNombreColumn
				.setCellFactory(TextFieldTableCell
						.<TipoIngredienteItem, String> forTableColumn(StringConverterFactory
								.forString()));
		ingreNombreColumn.setOnEditCommit(cellEditEvent -> cellEditEvent
				.getRowValue().setNombre(cellEditEvent.getNewValue()));

	}

	@FXML
	public void onAnotacionesTabSelected() {
		if (anotacionesTab.isSelected()) {
			initTabAnotaciones();
		}
	}

	private void initTabAnotaciones() {
		cargarDatosAnotacionesTable();
		initAnotacionesTable();

	}

	private void cargarDatosAnotacionesTable() {
		anotacionesTable.setItems(anotacionData);

	}

	private void initAnotacionesTable() {
		anotaDescripColumn
				.setCellValueFactory(new PropertyValueFactory<TipoAnotacionItem, String>(
						"descripcion"));
		anotaDescripColumn
				.setCellFactory(TextFieldTableCell
						.<TipoAnotacionItem, String> forTableColumn(StringConverterFactory
								.forString()));
		anotaDescripColumn.setOnEditCommit(cellEditEvent -> cellEditEvent
				.getRowValue().setDescripcion(cellEditEvent.getNewValue()));

	}

	@FXML
	public void onMedidasTabSelected() {
		if (medidasTab.isSelected()) {
			initTabMedidas();
		}
	}

	private void initTabMedidas() {
		cargarDatosMedidasTable();
		initMedidasTable();

	}

	private void cargarDatosMedidasTable() {
		medidasTable.setItems(medidaData);

	}

	private void initMedidasTable() {
		medidaNombreColumn
				.setCellValueFactory(new PropertyValueFactory<MedidaItem, String>(
						"nombre"));
		medidaNombreColumn.setCellFactory(TextFieldTableCell
				.<MedidaItem, String> forTableColumn(StringConverterFactory
						.forString()));
		medidaNombreColumn.setOnEditCommit(cellEditEvent -> cellEditEvent
				.getRowValue().setNombre(cellEditEvent.getNewValue()));

		medidaAbreviaturaColumn
				.setCellValueFactory(new PropertyValueFactory<MedidaItem, String>(
						"abreviatura"));
		medidaAbreviaturaColumn.setCellFactory(TextFieldTableCell
				.<MedidaItem, String> forTableColumn(StringConverterFactory
						.forString()));
		medidaAbreviaturaColumn.setOnEditCommit(cellEditEvent -> cellEditEvent
				.getRowValue().setAbreviatura(cellEditEvent.getNewValue()));

	}

	@FXML
	private void onAnyadirRecetaButtonClick() {
		ItemDialog<RecetaItem> dialog = ItemDialogFactory.forRecetaItem();
		dialog.showModal();
		dialog.getItem().ifPresent(item -> {
			try {
				Long id = ServiceLocator.getRecetasService().crearReceta(item);
				item.setId(id);
				System.out.println("Id creada: " + id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			recetTable.getItems().add(item.getRecetaListItem());
		});
		initCantidadRecetasLabel();
	}

	@FXML
	private void onEliminarRecetaButtonClick() {
		int selectedIndex = recetTable.getSelectionModel().getSelectedIndex();
		RecetaListItem recetaBorrar = recetTable.getItems().get(selectedIndex);
		recetTable.getItems().remove(selectedIndex);
		Thread eliminarRecetHilo = new java.lang.Thread() {
			public void run() {
				try {
					ServiceLocator.getRecetasService().eliminarReceta(recetaBorrar.getId());
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							initCantidadRecetasLabel();
						}
					});
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		};
		eliminarRecetHilo.start();

	}

	@FXML
	private void onEditarRecetaButtonClick() {
		System.out.println("Llamada a evento");
		RecetaListItem recetaEditar = recetTable.getSelectionModel()
				.getSelectedItem();
		System.out.println("recetaEditar.id: " + recetaEditar.getId());
		RecetaItem receta = null;
		try {
			receta = ServiceLocator.getRecetasService().obtenerReceta(
					recetaEditar.getId());
		} catch (ServiceException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			System.err.println("Excepcion: " + e1.getMessage() + " causa: "
					+ e1.getCause());
		}
		System.out.println("receta= " + receta);
		ItemDialog<RecetaItem> dialog = ItemDialogFactory.forRecetaItem(receta);
		dialog.showModal();
		dialog.getItem().ifPresent(item -> {
			try {
				ServiceLocator.getRecetasService().modificarReceta(item);
				int i = recetTable.getSelectionModel().getSelectedIndex();

				recetTable.getItems().set(i, item.getRecetaListItem());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
		initCantidadRecetasLabel();
	}

}
