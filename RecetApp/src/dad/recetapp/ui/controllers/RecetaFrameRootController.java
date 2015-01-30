package dad.recetapp.ui.controllers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import dad.recetapp.utils.Logs;
import dad.recetapp.utils.SpinnerStringConverter;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;

import dad.recetapp.ui.model.items.RecetaListItemFX;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.CategoriaItem;

import dad.recetapp.services.items.MedidaItem;
import dad.recetapp.services.items.RecetaItem;
import dad.recetapp.services.items.RecetaListItem;
import dad.recetapp.services.items.TipoAnotacionItem;
import dad.recetapp.services.items.TipoIngredienteItem;
import dad.recetapp.ui.AlertFactory;
import dad.recetapp.ui.ItemDialog;
import dad.recetapp.ui.ItemDialogFactory;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.converter.DateStringConverter;
import jfxtras.scene.control.ListSpinner;
import jfxtras.scene.control.ListSpinnerIntegerList;
import jfxtras.util.StringConverterFactory;

public class RecetaFrameRootController {
	// Spinners
	private ListSpinner<Integer> minutosRecetaSpinner;
	private ListSpinner<Integer> segundosRecetaSpinner;

	// ventana pricipal
	@FXML
	private Label cantidadRecetaLabel;
	@FXML
	private TabPane rootTabPane;
	// pestaï¿½a Recetas
	@FXML
	private Tab recetasTab;
	@FXML
	private GridPane anyadirRecetaPanel;

	@FXML
	private TableView<RecetaListItemFX> recetTable;
	@FXML
	private TableColumn<RecetaListItemFX, String> recetaNombreColumn;
	@FXML
	private TableColumn<RecetaListItemFX, String> recetaParaColumn;
	@FXML
	private TableColumn<RecetaListItemFX, String> recetaTiempoTotalColumn;
	@FXML
	private TableColumn<RecetaListItemFX, Date> recetaFechaCreacionColumn;
	@FXML
	private TableColumn<RecetaListItemFX, String> recetaCategoriaColumn;

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

	@FXML
	private Button anyadirRecetaButton;
	@FXML
	private Button eliminarRecetaButton;
	@FXML
	private Button editarRecetaButton;

	private ObservableList<RecetaListItemFX> recetData;

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

	// pestaï¿½a ingredientes
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

	// pestaï¿½a medidas
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

	// pestaï¿½a anotaciones
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
			Logs.log(e);
		}
		anotacionData = FXCollections.observableArrayList(anotaciones);

	}

	private void cargarMedidaData() {
		MedidaItem[] medidas = new MedidaItem[0];
		try {
			// cargo las recetas
			medidas = ServiceLocator.getMedidasService().listarMedidas();
		} catch (ServiceException e) {
			Logs.log(e);
		}
		medidaData = FXCollections.observableArrayList(medidas);
	}

	private void cargarIngreData() {
		TipoIngredienteItem[] ingredientes = new TipoIngredienteItem[0];
		// cargo las categorias
		try {
			ingredientes = ServiceLocator.getTiposIngredientesService()
					.listarTipoIngredientes();

		} catch (ServiceException e) {
			Logs.log(e);
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
		initCantidadRecetasLabel();
		initValidationCategorias();
		initValidationIngredientes();
		initValidationMedidas();
		initValidationAnotaciones();
	}
	

	

	

	private void initValidationCategorias() {
		ValidationSupport validationSupport = new ValidationSupport();
		validationSupport.setValidationDecorator(new GraphicValidationDecoration());
		validationSupport.registerValidator(descripCatTextField, Validator.createEmptyValidator("Introduzca un descripciï¿½n para la categorï¿½a"));
		validationSupport.invalidProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue && !newValue)
				anyadirCateButton.setDisable(false);
			else
				anyadirCateButton.setDisable(true);
		});
	}
	
	private void initValidationIngredientes() {
		ValidationSupport validationSupport = new ValidationSupport();
		validationSupport.setValidationDecorator(new GraphicValidationDecoration());
		validationSupport.registerValidator(nombreIngreTextField, Validator.createEmptyValidator("Introduzca un nombre para el ingrediente"));
		validationSupport.invalidProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue && !newValue)
				anyadirIngreButton.setDisable(false);
			else
				anyadirIngreButton.setDisable(true);
		});
	}
	private void initValidationMedidas() {
		ValidationSupport validationSupport = new ValidationSupport();
		validationSupport.setValidationDecorator(new GraphicValidationDecoration());
		validationSupport.registerValidator(nombreMedidaTextField, Validator.createEmptyValidator("Introduzca un nombre para la medida"));
		validationSupport.invalidProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue && !newValue)
				anyadirMedidaButton.setDisable(false);
			else
				anyadirMedidaButton.setDisable(true);
		});
	}
	
	private void initValidationAnotaciones() {
		ValidationSupport validationSupport = new ValidationSupport();
		validationSupport.setValidationDecorator(new GraphicValidationDecoration());
		validationSupport.registerValidator(descripAnotaTextField, Validator.createEmptyValidator("Introduzca un descripciï¿½n para la categorï¿½a"));
		validationSupport.invalidProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue && !newValue)
				anyadirAnotaButton.setDisable(false);
			else
				anyadirAnotaButton.setDisable(true);
		});
	}
	
	

	private void initCantidadRecetasLabel() {
		cantidadRecetaLabel.setText(Integer.toString(recetData.size()));
	}

	private void initTabRecetas() {
		initRecetaButtons();
		nombreRecetaTextField.setTooltip(new Tooltip("Introduzca el nombre de la receta a buscar"));
		initSpinners();
		cargarDatosRecetTable();
		initRecetTable();
		initCombosReceta();
		
	}

	private void initRecetaButtons() {
		anyadirRecetaButton.setTooltip(new Tooltip("A\u00F1ada una nueva receta"));
		eliminarRecetaButton.setTooltip(new Tooltip("Elimine la receta seleccionada"));
		editarRecetaButton.setTooltip(new Tooltip("Edita la receta seleccionada"));
	}

	
	private void initSpinners() {
		minutosRecetaSpinner = new ListSpinner<>(new ListSpinnerIntegerList(0,
				240)).withEditable(true).withCyclic(true)
				.withStringConverter(new SpinnerStringConverter());
		minutosRecetaSpinner.setBackground(Background.EMPTY);
		minutosRecetaSpinner.setTooltip(new Tooltip("Introduzca el tiempo m\u00E1ximo de la receta a buscar en minutos(m\u00E1x 240 min)"));
			minutosRecetaSpinner.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				onBuscarRecetasEvent();
			
			}
		});
		minutosRecetaSpinner.setOnKeyReleased(new EventHandler<KeyEvent>() {
		    public void handle(KeyEvent key) {
		    	onBuscarRecetasEvent();
		      
		    }
		});
		segundosRecetaSpinner = new ListSpinner<>(new ListSpinnerIntegerList(0,
				59)).withEditable(true).withCyclic(true)
				.withStringConverter(new SpinnerStringConverter());
		segundosRecetaSpinner.setTooltip(new Tooltip("Introduzca duraciï¿½n de la receta en segundos(mï¿½x 59 seg)"));
		
		segundosRecetaSpinner.setBackground(Background.EMPTY);
		segundosRecetaSpinner.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				onBuscarRecetasEvent();
			}
		});
		segundosRecetaSpinner.setOnKeyReleased(new EventHandler<Event>() {
			public void handle(Event event) {
			onBuscarRecetasEvent();
				
			}
		});
		anyadirRecetaPanel.add(minutosRecetaSpinner, 3, 0);
		anyadirRecetaPanel.add(segundosRecetaSpinner, 5, 0);	
	}


	private void cargarDatosRecetTable() {
		recetTable.setItems(recetData);
	}

	private void cargarRecetData() {
		List<RecetaListItem> recetas = null;
		try {
			recetas = ServiceLocator.getRecetasService().listarRecetas();
		} catch (ServiceException e) {
			Logs.log(e);
		}
		recetData = FXCollections.observableArrayList();
		recetas.forEach(r -> recetData.add(RecetaListItemFX
				.fromRecetaListItem(r)));
	}

	private void initRecetTable() {
		recetTable.getSelectionModel().setSelectionMode(
			    SelectionMode.MULTIPLE
			);
		recetaNombreColumn.setCellValueFactory(cell -> cell.getValue()
				.nombreProperty());
		recetaNombreColumn
				.setCellFactory(TextFieldTableCell
						.<RecetaListItemFX, String> forTableColumn(StringConverterFactory
								.forString()));
	
		recetaNombreColumn.setOnEditCommit(cellEditEvent -> cellEditEvent
				.getRowValue().setNombre(cellEditEvent.getNewValue()));

		recetaParaColumn.setCellValueFactory(cell -> cell.getValue()
				.paraProperty());
		recetaParaColumn
				.setCellFactory(TextFieldTableCell
						.<RecetaListItemFX, String> forTableColumn(StringConverterFactory
								.forString()));
		recetaParaColumn.setOnEditCommit(cellEditEvent -> cellEditEvent
				.getRowValue().setPara(cellEditEvent.getNewValue()));
		recetaTiempoTotalColumn
				.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RecetaListItemFX, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(
						CellDataFeatures<RecetaListItemFX, String> param) {
							return new ObservableValue<String>() {
							public void removeListener(InvalidationListener arg0) {}
							public void addListener(InvalidationListener arg0) {}
							public void removeListener(ChangeListener<? super String> arg0) {}
							public void addListener(ChangeListener<? super String> arg0) {}
							public String getValue() {
								Integer segundosTotales = param.getValue()
										.getTiempoTotal();
								int horas = segundosTotales / 3600;
								segundosTotales -= (horas * 3600);
								int min = segundosTotales / 60;
								segundosTotales -= (min * 60);
								int seg = segundosTotales % 60;
								String tiempoTotal = "";
								if (horas == 0) {
									if (seg == 0) {
										tiempoTotal = min + "m ";
									} else if (min == 0) {
										tiempoTotal = seg + "s ";
									} else {
										tiempoTotal = min + "m " + seg + "s ";
									}
								} else if (min == 0) {
									 if (seg == 0) {
										tiempoTotal = horas + "h ";

									} else {
										tiempoTotal = horas + "h " + seg + "s ";
									}
								} else if (seg == 0) {
									tiempoTotal = horas + "h " + min + "m";
								} else {
									tiempoTotal = horas + "h " + min + "m "
											+ seg + "s ";
								}
								return tiempoTotal;
							}
						};
					}
				});

		recetaFechaCreacionColumn
				.setCellValueFactory(new PropertyValueFactory<RecetaListItemFX, Date>(
						"fechaCreacion"));
		recetaFechaCreacionColumn
				.setCellFactory(TextFieldTableCell
						.<RecetaListItemFX, Date> forTableColumn(new DateStringConverter(
								"dd/MM/yyyy")));
		recetaFechaCreacionColumn
				.setOnEditCommit(cellEditEvent -> cellEditEvent.getRowValue()
						.setFechaCreacion(cellEditEvent.getNewValue()));

		recetaCategoriaColumn
				.setCellValueFactory(new PropertyValueFactory<RecetaListItemFX, String>(
						"categoria"));
		recetaCategoriaColumn
				.setCellFactory(TextFieldTableCell
						.<RecetaListItemFX, String> forTableColumn(StringConverterFactory
								.forString()));
		recetaCategoriaColumn.setOnEditCommit(cellEditEvent -> cellEditEvent
				.getRowValue().setPara(cellEditEvent.getNewValue()));
	}

	private void initCombosReceta() {
		Task<ObservableList<CategoriaItem>> task = new Task<ObservableList<CategoriaItem>>() {
			@Override
			protected ObservableList<CategoriaItem> call() {
				CategoriaItem[] c = new CategoriaItem[0];
				try {
					c = ServiceLocator.getCategoriasService().listarCategoria();
				} catch (ServiceException e) {
					Logs.log(e);
				}

				return FXCollections.observableArrayList(Arrays.asList(c));
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				CategoriaItem ci = new CategoriaItem();
				ci.setDescripcion("<Todas>");
				categoriaItemCombo.getItems().add(ci);
				categoriaItemCombo.getItems().addAll(getValue());
				categoriaItemCombo.setValue(ci);

			}
		};
		task.run();
	}

	private void initTabCategorias() {
		descripCatTextField.setTooltip(new Tooltip("Introduzca el nombre de la categor\u00EDa que desea a\u00F1adir"));
		initCategoriaButtons();
		cargarDatosCateTable();
		initCateTable();

	}

	private void initCategoriaButtons() {
		anyadirCateButton.setTooltip(new Tooltip("A\u00F1ada una nueva categor\u00EDa"));
		eliminarCateButton.setTooltip(new Tooltip("Elimine la categor\u00EDa seleccionada"));
	}

	private void cargarDatosCateTable() {
		cateTable.setItems(cateData);

	}

	private void cargarCateData() {
		CategoriaItem[] c = new CategoriaItem[0];
		try {
			c = ServiceLocator.getCategoriasService().listarCategoria();
		} catch (ServiceException e) {
			Logs.log(e);
		}

		cateData = FXCollections.observableArrayList(Arrays.asList(c));
	}

	private void initCateTable() {
		cateTable.setEditable(true);
		cateTable.getSelectionModel().setSelectionMode(
			    SelectionMode.MULTIPLE
			);
		cateDescripColumn
				.setCellValueFactory(new PropertyValueFactory<CategoriaItem, String>(
						"descripcion"));
		cateDescripColumn.setCellFactory(TextFieldTableCell
				.<CategoriaItem, String> forTableColumn(StringConverterFactory
						.forString()));
		cateDescripColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CategoriaItem,String>>() {
			public void handle(CellEditEvent<CategoriaItem, String> event) {
			 event.getRowValue().setDescripcion(event.getNewValue());
			try {
				ServiceLocator.getCategoriasService().modificarCategoria(event.getRowValue());
			} catch (ServiceException e) {
				mensajeError(e);
				Logs.log(e);
			}
			}
		});
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
		nombreIngreTextField.setTooltip(new Tooltip("Introduzca el nombre del ingrediente que desea a\u00F1adir"));
		initIngredienteButtons();
		cargarDatosIngreTable();
		initIngreTable();
	}
	
	private void initIngredienteButtons() {
		anyadirIngreButton.setTooltip(new Tooltip("A\u00F1ada un nuevo ingrediente"));
		eliminarIngreButton.setTooltip(new Tooltip("Elimine el ingrediente seleccionado"));
	}

	private void cargarDatosIngreTable() {
		ingreTable.setItems(ingreData);

	}

	private void initIngreTable() {
		ingreTable.setEditable(true);
		ingreTable.getSelectionModel().setSelectionMode(
			    SelectionMode.MULTIPLE
			);
		ingreNombreColumn
				.setCellValueFactory(new PropertyValueFactory<TipoIngredienteItem, String>(
						"nombre"));
		ingreNombreColumn
				.setCellFactory(TextFieldTableCell
						.<TipoIngredienteItem, String> forTableColumn(StringConverterFactory
								.forString()));
	
		ingreNombreColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TipoIngredienteItem,String>>() {

			public void handle(CellEditEvent<TipoIngredienteItem, String> event) {
				event.getRowValue().setNombre(event.getNewValue());
				try {
					ServiceLocator.getTiposIngredientesService().modificarTipoIngrediente(event.getRowValue());
				} catch (ServiceException e) {
					mensajeError(e);
					Logs.log(e);
				}
			}
		});
	}
	@FXML
	public void onAnotacionesTabSelected() {
		if (anotacionesTab.isSelected()) {
			initTabAnotaciones();
		}
	}

	private void initTabAnotaciones() {
		descripAnotaTextField.setTooltip(new Tooltip("Introduzca el nombre de la anotaci\u00f3n que desea a\u00F1adir"));
		initAnotaButtons();
		cargarDatosAnotacionesTable();
		initAnotacionesTable();

	}

	private void initAnotaButtons() {
		anyadirAnotaButton.setTooltip(new Tooltip("A\u00F1ada una nueva anotaci\u00f3n"));
		eliminarAnotaButton.setTooltip(new Tooltip("Elimine la anotaci\u00f3n seleccionada"));
	}

	private void cargarDatosAnotacionesTable() {
		anotacionesTable.setItems(anotacionData);

	}

	private void initAnotacionesTable() {
		anotacionesTable.setEditable(true);
		anotacionesTable.getSelectionModel().setSelectionMode(
			    SelectionMode.MULTIPLE
			);
	
		anotaDescripColumn
				.setCellValueFactory(new PropertyValueFactory<TipoAnotacionItem, String>(
						"descripcion"));
		anotaDescripColumn
				.setCellFactory(TextFieldTableCell
						.<TipoAnotacionItem, String> forTableColumn(StringConverterFactory
								.forString()));
		
		anotaDescripColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TipoAnotacionItem,String>>() {
			public void handle(CellEditEvent<TipoAnotacionItem, String> event) {
				event.getRowValue().setDescripcion(event.getNewValue());
				try {
					ServiceLocator.getTiposAnotacionesService().crearTipoAnotacion(event.getRowValue());
				} catch (ServiceException e) {
					mensajeError(e);
					Logs.log(e);
				}
				
			}
		});
	}

	@FXML
	public void onMedidasTabSelected() {
		if (medidasTab.isSelected()) {
			initTabMedidas();
		}
	}

	private void initTabMedidas() {
		nombreMedidaTextField.setTooltip(new Tooltip("Introduzca el nombre de la medida que desea a\u00F1adir"));
		abrevMedidaTextField.setTooltip(new Tooltip("Introduzca la abreviatura de la medida que desea a\u00F1adir"));
		initMedidaButtons();
		cargarDatosMedidasTable();
		initMedidasTable();

	}

	private void initMedidaButtons() {
		anyadirMedidaButton.setTooltip(new Tooltip("A\u00F1ada una nueva medida"));
		eliminarMedidaButton.setTooltip(new Tooltip("Elimine la medida seleccionada"));

	}

	private void cargarDatosMedidasTable() {
		medidasTable.setItems(medidaData);

	}

	private void initMedidasTable() {
		medidasTable.setEditable(true);
		medidasTable.getSelectionModel().setSelectionMode(
			    SelectionMode.MULTIPLE
			);

		medidaNombreColumn
				.setCellValueFactory(new PropertyValueFactory<MedidaItem, String>(
						"nombre"));
		medidaNombreColumn.setCellFactory(TextFieldTableCell
				.<MedidaItem, String> forTableColumn(StringConverterFactory
						.forString()));

		medidaNombreColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MedidaItem,String>>() {
			
			@Override
			public void handle(CellEditEvent<MedidaItem, String> event) {
				event.getRowValue().setNombre(event.getNewValue());
				try {
					ServiceLocator.getMedidasService().modificarMedida(event.getRowValue());
				} catch (ServiceException e) {
					mensajeError(e);
					Logs.log(e);
				}
				
			}
		});

		medidaAbreviaturaColumn
				.setCellValueFactory(new PropertyValueFactory<MedidaItem, String>(
						"abreviatura"));
		medidaAbreviaturaColumn.setCellFactory(TextFieldTableCell
				.<MedidaItem, String> forTableColumn(StringConverterFactory
						.forString()));
		medidaAbreviaturaColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MedidaItem,String>>() {
			public void handle(CellEditEvent<MedidaItem, String> event) {
				event.getRowValue().setAbreviatura(event.getNewValue());
				try {
					ServiceLocator.getMedidasService().modificarMedida(event.getRowValue());
				} catch (ServiceException e) {
					mensajeError(e);
					Logs.log(e);
				}
				
			}
		});
	}
	@FXML
	private void onAnyadirRecetaButtonClick() {
		ItemDialog<RecetaItem> dialog = ItemDialogFactory.forRecetaItem();
		dialog.showModal();
		dialog.getItem().ifPresent(item -> {
			try {
				ServiceLocator.getRecetasService().crearReceta(item);
			} catch (ServiceException e) {
				mensajeError(e);
				Logs.log(e);
			}
			recetData.add(RecetaListItemFX.fromRecetaListItem(item
							.toRecetaListItem()));
		});
		initCantidadRecetasLabel();
	}
	
	@FXML
	private void onEliminarRecetaButtonClick() {
		ObservableList<RecetaListItemFX> seleccionados = recetTable.getSelectionModel().getSelectedItems();
		if (!seleccionados.isEmpty()) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Alert alert = AlertFactory.createDeleteAlert(
							"Eliminar receta",
							"Se va a proceder a eliminar las siguientes recetas: "
									+ mostrarSeleccionados(seleccionados),
							"\u00bfDesea continuar?\nNo se podr\u00e1 recuperar los cambios");
					java.util.Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						
						Thread eliminarRecetHilo = new java.lang.Thread() {
							public void run() {
								try {
									for (RecetaListItemFX item: seleccionados) {
										ServiceLocator.getRecetasService().eliminarReceta(
												item.getId());
									}
									
									Platform.runLater(new Runnable() {
										public void run() {
											recetData.removeAll(seleccionados);
											initCantidadRecetasLabel();
										}
									});
								} catch (ServiceException e) {
									mensajeError(e);
									Logs.log(e);
								}
							};
						};
						eliminarRecetHilo.start();
					} else {
						alert.close();
					}

					}

				private String mostrarSeleccionados(
						ObservableList<RecetaListItemFX> seleccionados) {
					String mostrar = "\n";
					for (RecetaListItemFX item : seleccionados) {
						mostrar+="-"+item.getNombre()+"\n";
					}
					return mostrar;
				}
			});
			
				
			}else{
				Alert alert = AlertFactory.createInfoAlert("Eliminar Receta", "No hay ninguna receta seleccionada, para eliminarla primero debe seleccionarla en la tabla");
				alert.show();
			}
		}


	@FXML
	private void onEditarRecetaButtonClick() {
		int selectedIndex = recetTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex!=-1) {
			RecetaListItem recetaEditar = recetTable.getSelectionModel()
					.getSelectedItem();
			RecetaItem receta = null;
			try {
				receta = ServiceLocator.getRecetasService().obtenerReceta(
						recetaEditar.getId());
			} catch (ServiceException e) {
				mensajeError(e);
				Logs.log(e);
			}
			ItemDialog<RecetaItem> dialog = ItemDialogFactory.forRecetaItem(receta);
			dialog.showModal();
			dialog.getItem().ifPresent(
					item -> {
						try {
							ServiceLocator.getRecetasService()
									.modificarReceta(item);
							int i = recetTable.getSelectionModel()
									.getSelectedIndex();
							recetData.set(i, RecetaListItemFX.fromRecetaListItem(item
											.toRecetaListItem()));
						} catch (ServiceException e) {
							mensajeError(e);
							Logs.log(e);
						}

					});
			initCantidadRecetasLabel();
		}else{
			Alert alert = AlertFactory.createInfoAlert("Editar receta", "No hay ninguna receta seleccionada, para editarla primero debe seleccionarla en la tabla");
			alert.show();
		}
		
	}

	@FXML
	private void onBuscarRecetasEvent() {
		Thread filtroRecetHilo = new java.lang.Thread() {
			private List<RecetaListItem> recetasFiltradas;	
			public void run() {
				try {
					String campoNombre = nombreRecetaTextField.getText();
					if (campoNombre.equals(""))campoNombre=null;
					Integer campoTiempo = (minutosRecetaSpinner.getValue()* 60 )+ segundosRecetaSpinner.getValue();
					if(campoTiempo==0)campoTiempo=null;
					recetasFiltradas = ServiceLocator.getRecetasService().buscarRecetas(campoNombre,campoTiempo,
										categoriaItemCombo.getSelectionModel()
												.getSelectedItem().getId());
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							recetData.clear();
							for (RecetaListItem recetaFiltrada : recetasFiltradas) {
								recetData.add(RecetaListItemFX
										.fromRecetaListItem(recetaFiltrada));
							
							}
							initCantidadRecetasLabel();
						}
					});
				} catch (ServiceException e) {
					mensajeError(e);
					Logs.log(e);
				}
			};
		};
		filtroRecetHilo.start();
	}
	
	@FXML
	private void onAnyadirCategoriaButtonClick() {
		CategoriaItem cateNueva=new CategoriaItem();
		cateNueva.setDescripcion(descripCatTextField.getText());
		Thread anyadirCateHilo = new java.lang.Thread() {
			public void run() {
				try {
					Long id = ServiceLocator.getCategoriasService().crearCategoria(cateNueva);
					cateNueva.setId(id);
				} catch (ServiceException e) {
					mensajeError(e);
					Logs.log(e);
				}
			};
		};
			anyadirCateHilo.start();
			cateData.add(cateNueva);
			descripCatTextField.setText("");
	}
	
	
	@FXML
	private void onEliminarCategoriaButtonClick() {
		 ObservableList<CategoriaItem> seleccionados = cateTable.getSelectionModel().getSelectedItems();
		if (!seleccionados.isEmpty()) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Alert alert = AlertFactory.createDeleteAlert(
							"Eliminar categor\u00eda",
							"Se va a proceder a eliminar las siguientes categor\u00edas: "
									+ mostrarSeleccionados(seleccionados),
							"\u00bfDesea continuar?\nNo se podr\u00e1 recuperar los cambios");
					java.util.Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						
						Thread eliminarCateHilo = new java.lang.Thread() {
							public void run() {
								try {
									for (CategoriaItem item: seleccionados) {
										ServiceLocator.getCategoriasService().eliminarCategoria(item.getId());
									}
									
									Platform.runLater(new Runnable() {
										public void run() {
											cateData.removeAll(seleccionados);	
										}
									});
								} catch (ServiceException e) {
									mensajeError(e);
									Logs.log(e);
								}
							};
						};
						eliminarCateHilo.start();
					} else {
						alert.close();
					}

					}

				private String mostrarSeleccionados(
						ObservableList<CategoriaItem> seleccionados) {
					String mostrar = "\n";
					for (CategoriaItem item : seleccionados) {
						mostrar+="-"+item.getDescripcion()+"\n";
					}
					return mostrar;
				}
			});
			
				
			}else{
				Alert alert = AlertFactory.createInfoAlert("Eliminar Categor\u00eda", "No hay ninguna categor\u00eda seleccionada, para eliminarla primero debe seleccionarla en la tabla");
				alert.show();
			}
		}

	
	@FXML
	private void onAnyadirIngredienteButtonClick() {
		TipoIngredienteItem ingreNuevo=new TipoIngredienteItem();
		ingreNuevo.setNombre(nombreIngreTextField.getText());
		Thread anyadirIngreHilo = new java.lang.Thread() {
			public void run() {
				try {
					Long id = ServiceLocator.getTiposIngredientesService().crearTipoIngrediente(ingreNuevo);
					ingreNuevo.setId(id);
					ingreData.add(ingreNuevo);
				} catch (ServiceException e) {
					mensajeError(e);
					Logs.log(e);
				}
			};
		};
		anyadirIngreHilo.start();
		nombreIngreTextField.setText("");
	}
	
	@FXML
	private void onEliminarIngredienteButtonClick() {
		 ObservableList<TipoIngredienteItem> seleccionados = ingreTable.getSelectionModel().getSelectedItems();
		if (!seleccionados.isEmpty()) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Alert alert = AlertFactory.createDeleteAlert(
							"Eliminar ingrediente",
							"Se va a proceder a eliminar los siguientes ingredientes: "
									+ mostrarSeleccionados(seleccionados),
							"\u00bfDesea continuar?\nNo se podr\u00e1 recuperar los cambios");
					java.util.Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						
						Thread eliminarCateHilo = new java.lang.Thread() {
							public void run() {
								try {
									for (TipoIngredienteItem item: seleccionados) {
										ServiceLocator.getTiposIngredientesService().eliminarTipoIngrediente(item.getId());
									}
									
									Platform.runLater(new Runnable() {
										public void run() {
											ingreData.removeAll(seleccionados);	
										}
									});
								} catch (ServiceException e) {
									mensajeError(e);
									Logs.log(e);
								}
							};
						};
						eliminarCateHilo.start();
					} else {
						alert.close();
					}

					}

				private String mostrarSeleccionados(
						ObservableList<TipoIngredienteItem> seleccionados) {
					String mostrar = "\n";
					for (TipoIngredienteItem item : seleccionados) {
						mostrar+="-"+item.getNombre()+"\n";
					}
					return mostrar;
				}
			});
			
				
			}else{
				Alert alert = AlertFactory.createInfoAlert("Eliminar Ingrediente", "No hay ning\u00fan ingrediente seleccionado, para eliminarlo primero debe seleccionarlo en la tabla");
				alert.show();
			}	
	}
	
	@FXML
	private void onAnyadirMedidasButtonClick() {
		MedidaItem medidaNueva=new MedidaItem();
		medidaNueva.setNombre(nombreMedidaTextField.getText());
		if (!abrevMedidaTextField.equals("")) {
			medidaNueva.setAbreviatura(abrevMedidaTextField.getText());
		}
		Thread anyadirMedidaHilo = new java.lang.Thread() {
			public void run() {
				try {
					Long id = ServiceLocator.getMedidasService().crearMedida(medidaNueva);
					medidaNueva.setId(id);
					medidaData.add(medidaNueva);	
				} catch (ServiceException e) {
					mensajeError(e);
					Logs.log(e);
				}
			};
		};
		anyadirMedidaHilo.start();
		nombreMedidaTextField.setText("");
		abrevMedidaTextField.setText("");
	
	}
	
	@FXML
	private void onEliminarMedidaButtonClick() {
		ObservableList<MedidaItem> seleccionados = medidasTable.getSelectionModel().getSelectedItems();
			if (!seleccionados.isEmpty()) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Alert alert = AlertFactory.createDeleteAlert(
								"Eliminar medida",
								"Se va a proceder a eliminar las siguientes medidas: "
										+ mostrarSeleccionados(seleccionados),
								"\u00bfDesea continuar?\nNo se podr\u00e1 recuperar los cambios");
						java.util.Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK) {
							
							Thread eliminarMedidaHilo = new java.lang.Thread() {
								public void run() {
									try {
										for (MedidaItem item: seleccionados) {
											ServiceLocator.getMedidasService().eliminarMedida(item.getId());
										}
										
										Platform.runLater(new Runnable() {
											public void run() {
												medidaData.removeAll(seleccionados);	
											}
										});
									} catch (ServiceException e) {
										mensajeError(e);
										Logs.log(e);
									}
								};
							};
							eliminarMedidaHilo.start();
						} else {
							alert.close();
						}

						}

					private String mostrarSeleccionados(
							ObservableList<MedidaItem> seleccionados) {
						String mostrar = "\n";
						for (MedidaItem item : seleccionados) {
							mostrar+="-"+item.getNombre()+"\n";
						}
						return mostrar;
					}
				});
					
				}else{
					Alert alert = AlertFactory.createInfoAlert("Eliminar medida", "No hay ninguna medida seleccionada, para eliminarla primero debe seleccionarla en la tabla");
					alert.show();
				}
			}
	
	@FXML
	private void onAnyadirAnotaButtonClick() {
		TipoAnotacionItem anotacionNueva=new TipoAnotacionItem();
		anotacionNueva.setDescripcion(descripAnotaTextField.getText());
		Thread anyadirAnotaHilo = new java.lang.Thread() {
			public void run() {
				try {
					Long id = ServiceLocator.getTiposAnotacionesService().crearTipoAnotacion(anotacionNueva);
					anotacionNueva.setId(id);
					anotacionData.add(anotacionNueva);	
				} catch (ServiceException e) {
					mensajeError(e);
					Logs.log(e);
				}
			};
		};
		anyadirAnotaHilo.start();
		descripAnotaTextField.setText("");
	
	}
	
	@FXML
	private void onEliminarAnotaButtonClick() {
		ObservableList<TipoAnotacionItem> seleccionados = anotacionesTable.getSelectionModel().getSelectedItems();
		if (!seleccionados.isEmpty()) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Alert alert = AlertFactory.createDeleteAlert(
							"Eliminar anotaci\u00f3n",
							"Se va a proceder a eliminar las siguientes anotaciones: "
									+ mostrarSeleccionados(seleccionados),
							"\u00bfDesea continuar?\nNo se podr\u00e1 recuperar los cambios");
					java.util.Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						
						Thread eliminarAnotaHilo = new java.lang.Thread() {
							public void run() {
								try {
									for (TipoAnotacionItem item: seleccionados) {
										ServiceLocator.getTiposAnotacionesService().eliminarTipoAnotacion(item.getId());
									}
									
									Platform.runLater(new Runnable() {
										public void run() {
											anotacionData.removeAll(seleccionados);	
										}
									});
								} catch (ServiceException e) {
									mensajeError(e);
									Logs.log(e);
								}
							};
						};
						eliminarAnotaHilo.start();
					} else {
						alert.close();
					}

					}

				private String mostrarSeleccionados(
						ObservableList<TipoAnotacionItem> seleccionados) {
					String mostrar = "\n";
					for (TipoAnotacionItem item : seleccionados) {
						mostrar+="-"+item.getDescripcion()+"\n";
					}
					return mostrar;
				}
			});
				
			}else{
				Alert alert = AlertFactory.createInfoAlert("Eliminar anotaci\u00f3n", "No hay ninguna anotaci\u00f3n seleccionada, para eliminarla primero debe seleccionarla en la tabla");
				alert.show();
			}
		}
		
	
	
	private void mensajeError(Exception e) {
		Platform.runLater(new Runnable() {
			public void run() {
			Alert alert =AlertFactory.createErrorAlert("ERROR", e.getMessage());
				alert.show();
			}
			
		});
	}

}
