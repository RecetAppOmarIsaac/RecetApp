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
import javafx.collections.ListChangeListener;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
	// pesta�a Recetas
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

	// TODO FALTAN LOS SPINNER DE EL TIEMPO DE LA RECETA

	@FXML
	private Button anyadirRecetaButton;
	@FXML
	private Button eliminarRecetaButton;
	@FXML
	private Button editarRecetaButton;

	private ObservableList<RecetaListItemFX> recetData;

	// pesta�a categorias
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

	// pesta�a ingredientes
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

	// pesta�a medidas
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

	// pesta�a anotaciones
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
			ingredientes = ServiceLocator.getTipoIngredienteService()
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
		validationSupport.registerValidator(descripCatTextField, Validator.createEmptyValidator("Introduzca un descripci�n para la categor�a"));
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
		validationSupport.registerValidator(descripAnotaTextField, Validator.createEmptyValidator("Introduzca un descripci�n para la categor�a"));
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
		anyadirRecetaButton.setTooltip(new Tooltip("A�ada una nueva receta"));
		eliminarRecetaButton.setTooltip(new Tooltip("Elimine la receta seleccionada"));
		editarRecetaButton.setTooltip(new Tooltip("Edita la receta seleccionada"));
		eliminarRecetaButton.setDisable(true);
    	editarRecetaButton.setDisable(true);
		initSpinners();
		cargarDatosRecetTable();
		initRecetTable();
		initCombosReceta();
		
	}

	private void initSpinners() {
		minutosRecetaSpinner = new ListSpinner<>(new ListSpinnerIntegerList(0,
				240)).withEditable(true).withCyclic(true)
				.withStringConverter(new SpinnerStringConverter());
		minutosRecetaSpinner.setBackground(Background.EMPTY);
		minutosRecetaSpinner.setTooltip(new Tooltip("Introduzca duraci�n de la receta en minutos(m�x 240 min)"));
		
		minutosRecetaSpinner.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				onBuscarRecetasEvent();
			
			}
		});
		minutosRecetaSpinner.setOnKeyReleased(new EventHandler<Event>() {
			public void handle(Event event) {
				onBuscarRecetasEvent();
			}
		});
		segundosRecetaSpinner = new ListSpinner<>(new ListSpinnerIntegerList(0,
				59)).withEditable(true).withCyclic(true)
				.withStringConverter(new SpinnerStringConverter());
		segundosRecetaSpinner.setTooltip(new Tooltip("Introduzca duraci�n de la receta en segundos(m�x 59 seg)"));
		
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
			// cargo las recetas
			recetas = ServiceLocator.getRecetasService().listarRecetas();
		} catch (ServiceException e) {
			Logs.log(e);
		}
		recetData = FXCollections.observableArrayList();
		recetas.forEach(r -> recetData.add(RecetaListItemFX
				.fromRecetaListItem(r)));
	}

	private void initRecetTable() {
		
		recetTable.getSelectionModel().getSelectedIndices().addListener(new ListChangeListener<Integer>()
			    {
			        @Override
			        public void onChanged(Change<? extends Integer> change)
			        {
			        	eliminarRecetaButton.setDisable(false);
			        	editarRecetaButton.setDisable(false);
			        }

			    });
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
							public void removeListener(InvalidationListener arg0) {}public void addListener(InvalidationListener arg0) {}
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
									if (horas == 0) {
										tiempoTotal = seg + "s ";
									} else if (seg == 0) {
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
			Logs.log(e);
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
			} catch (ServiceException e) {
				mensajeError(e);
			}
			recetData.add(RecetaListItemFX.fromRecetaListItem(item
							.toRecetaListItem()));
		});
		initCantidadRecetasLabel();
	}
	

	
	@FXML
	private void onEliminarRecetaButtonClick() {
		int selectedIndex = recetTable.getSelectionModel().getSelectedIndex();
		
		if (selectedIndex!=-1) {
			RecetaListItem recetaBorrar = recetTable.getItems().get(selectedIndex);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Alert alert = AlertFactory.createConfirmationAlert(
							"Eliminar receta",
							"�Desea eliminar la receta: '"
									+ recetTable.getItems().get(selectedIndex) + "'?",
							"No se podra recuperar los cambios");
					java.util.Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						recetData.remove(selectedIndex);
						Thread eliminarRecetHilo = new java.lang.Thread() {
							public void run() {
								try {
									ServiceLocator.getRecetasService().eliminarReceta(
											recetaBorrar.getId());
									Platform.runLater(new Runnable() {
										public void run() {
											initCantidadRecetasLabel();
										}
									});
								} catch (ServiceException e) {
									mensajeError(e);
								}
							};
						};
						eliminarRecetHilo.start();
					} else {
						alert.close();
					}

					}
			});
		}
		
			
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
		} catch (ServiceException e) {
			mensajeError(e);
		}
		System.out.println("receta= " + receta);
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
					}

				});
		initCantidadRecetasLabel();
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
							initCantidadRecetasLabel();
							}
						}
					});
				} catch (ServiceException e) {
					mensajeError(e);
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
				}
			};
		};
			anyadirCateHilo.start();
			cateData.add(cateNueva);
			descripCatTextField.setText("");
	}
	
	
	@FXML
	private void onEliminarCategoriaButtonClick() {
		int selectedIndex = cateTable.getSelectionModel().getSelectedIndex();
		CategoriaItem cateBorrar = cateTable.getItems().get(selectedIndex);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Alert alert = AlertFactory.createConfirmationAlert(
						"Eliminar categor�a",
						"�Desea eliminar la categor�a: '"
								+ cateTable.getItems().get(selectedIndex) + "'?",
						"No se podr� recuperar los cambios");
				java.util.Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					cateData.remove(selectedIndex);
					Thread eliminarCateHilo = new java.lang.Thread() {
						public void run() {
							try {
								ServiceLocator.getCategoriasService().eliminarCategoria(
										cateBorrar.getId());
							} catch (ServiceException e) {
								mensajeError(e);
							}
						};
					};
					eliminarCateHilo.start();
				} else {
					alert.close();
				}

				}
		});		
	}
	
	@FXML
	private void onAnyadirIngredienteButtonClick() {
		TipoIngredienteItem ingreNuevo=new TipoIngredienteItem();
		ingreNuevo.setNombre(nombreIngreTextField.getText());
		Thread anyadirIngreHilo = new java.lang.Thread() {
			public void run() {
				try {
					Long id = ServiceLocator.getTipoIngredienteService().crearTipoIngrediente(ingreNuevo);
					ingreNuevo.setId(id);
					ingreData.add(ingreNuevo);
				} catch (ServiceException e) {
					mensajeError(e);
				}
			};
		};
		anyadirIngreHilo.start();
		nombreIngreTextField.setText("");
	}
	
	@FXML
	private void onEliminarIngredienteButtonClick() {
		int selectedIndex = ingreTable.getSelectionModel().getSelectedIndex();
		 TipoIngredienteItem ingreBorrar = ingreTable.getItems().get(selectedIndex);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Alert alert = AlertFactory.createConfirmationAlert(
						"Eliminar ingrediente",
						"�Desea eliminar el ingrediente: '"
								+ ingreTable.getItems().get(selectedIndex) + "'?",
						"No se podr� recuperar los cambios");
				java.util.Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					ingreData.remove(selectedIndex);
					Thread eliminarIngreHilo = new java.lang.Thread() {
						public void run() {
							try {
								ServiceLocator.getTipoIngredienteService().eliminarTipoIngrediente(ingreBorrar.getId());
							} catch (ServiceException e) {
								mensajeError(e);
							}
						};
					};
					eliminarIngreHilo.start();
				} else {
					alert.close();
				}

				}
		});		
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
				}
			};
		};
		anyadirMedidaHilo.start();
		nombreMedidaTextField.setText("");
		abrevMedidaTextField.setText("");
	
	}
	
	@FXML
	private void onEliminarMedidaButtonClick() {
		int selectedIndex = medidasTable.getSelectionModel().getSelectedIndex();
		  MedidaItem medidaBorrar = medidasTable.getItems().get(selectedIndex);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Alert alert = AlertFactory.createConfirmationAlert(
						"Eliminar medida",
						"�Desea eliminar la medida: '"
								+ medidasTable.getItems().get(selectedIndex) + "'?",
						"No se podr� recuperar los cambios");
				java.util.Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					medidaData.remove(selectedIndex);
					Thread eliminarMedidaHilo = new java.lang.Thread() {
						public void run() {
							try {
								ServiceLocator.getMedidasService().eliminarMedida(medidaBorrar.getId());
							} catch (ServiceException e) {
								mensajeError(e);
							}
						};
					};
					eliminarMedidaHilo.start();
				} else {
					alert.close();
				}

				}
		});		
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
				}
			};
		};
		anyadirAnotaHilo.start();
		descripAnotaTextField.setText("");
	
	}
	
	@FXML
	private void onEliminarAnotaButtonClick() {
		int selectedIndex = anotacionesTable.getSelectionModel().getSelectedIndex();
		  TipoAnotacionItem anotaBorrar = anotacionesTable.getItems().get(selectedIndex);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Alert alert = AlertFactory.createConfirmationAlert(
						"Eliminar anotacion",
						"�Desea eliminar la anotacion: '"
								+ anotacionesTable.getItems().get(selectedIndex) + "'?",
						"No se podr� recuperar los cambios");
				java.util.Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					anotacionData.remove(selectedIndex);
					Thread eliminarAnotaHilo = new java.lang.Thread() {
						public void run() {
							try {
								ServiceLocator.getTiposAnotacionesService().eliminarTipoAnotacion(anotaBorrar.getId());
							} catch (ServiceException e) {
								mensajeError(e);
							}
						};
					};
					eliminarAnotaHilo.start();
				} else {
					alert.close();
				}

				}
		});		
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
