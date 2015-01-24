package dad.recetapp.ui.fxml;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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

	// TODO FALTAN LOS SPINNER DE EL TIEMPO DE LA RECETA

	@FXML
	private Button anyadirRecetaButton;
	@FXML
	private Button eliminarRecetaButton;
	@FXML
	private Button editarRecetaButton;

	private ObservableList<RecetaListItemFX> recetData;

	// pestaï¿½a categorias
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
		initCantidadRecetasLabel();
		initValidationCategorias();
	}
	private void initValidationCategorias() {
		ValidationSupport validationSupport = new ValidationSupport();
		validationSupport.setValidationDecorator(new GraphicValidationDecoration());
		validationSupport.registerValidator(descripCatTextField, Validator.createEmptyValidator("Introduzca un nombre para la receta"));
		validationSupport.invalidProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue && !newValue)
				anyadirCateButton.setDisable(false);
			else
				anyadirCateButton.setDisable(true);
		});
	}
	
	

	private void initCantidadRecetasLabel() {
		cantidadRecetaLabel.setText(Integer.toString(recetData.size()));
	}

	private void initTabRecetas() {
		initSpinners();
		cargarDatosRecetTable();
		initRecetTable();
		initCombosReceta();
	}

	private void initSpinners() {
		minutosRecetaSpinner = new ListSpinner<>(new ListSpinnerIntegerList(0,
				240)).withEditable(true).withCyclic(true)
				.withStringConverter(StringConverterFactory.forInteger());
		minutosRecetaSpinner.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				onBuscarRecetasEvent();
			
			}
		});
		minutosRecetaSpinner.setOnKeyReleased(new EventHandler<Event>() {
			public void handle(Event event) {
				onBuscarRecetasEvent();
				System.out.println("Suelto");
			}
		});
		segundosRecetaSpinner = new ListSpinner<>(new ListSpinnerIntegerList(0,
				59)).withEditable(true).withCyclic(true)
				.withStringConverter(StringConverterFactory.forInteger());
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
			e.printStackTrace();
		}
		recetData = FXCollections.observableArrayList();
		recetas.forEach(r -> recetData.add(RecetaListItemFX
				.fromRecetaListItem(r)));
	}

	private void initRecetTable() {
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
					System.err.println("CategoriaService Error: "
							+ e.getMessage() + " Cause: " + e.getCause());
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
			recetTable.getItems()
					.add(RecetaListItemFX.fromRecetaListItem(item
							.toRecetaListItem()));
		});
		initCantidadRecetasLabel();
	}

	@FXML
	private void onEliminarRecetaButtonClick() {
		int selectedIndex = recetTable.getSelectionModel().getSelectedIndex();
		RecetaListItem recetaBorrar = recetTable.getItems().get(selectedIndex);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Alert alert = AlertFactory.createConfirmationAlert(
						"Eliminar receta",
						"¿Desea eliminar la receta: '"
								+ recetTable.getItems().get(selectedIndex) + "'?",
						"No se prodra recuperar los cambios");
				java.util.Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					recetTable.getItems().remove(selectedIndex);
					Thread eliminarRecetHilo = new java.lang.Thread() {
						public void run() {
							try {
								ServiceLocator.getRecetasService().eliminarReceta(
										recetaBorrar.getId());
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
				} else {
					alert.close();
				}

				}
		});
			
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
		dialog.getItem().ifPresent(
				item -> {
					try {
						ServiceLocator.getRecetasService()
								.modificarReceta(item);
						int i = recetTable.getSelectionModel()
								.getSelectedIndex();

						recetTable.getItems().set(
								i,
								RecetaListItemFX.fromRecetaListItem(item
										.toRecetaListItem()));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
							}
						}
					});
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		};
		filtroRecetHilo.start();
	}
	
	@FXML
	private void onEliminarCategoriaButtonClick() {
		int selectedIndex = cateTable.getSelectionModel().getSelectedIndex();
		CategoriaItem cateBorrar = cateTable.getItems().get(selectedIndex);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Alert alert = AlertFactory.createConfirmationAlert(
						"Eliminar categoría",
						"¿Desea eliminar la categoría: '"
								+ cateTable.getItems().get(selectedIndex) + "'?",
						"No se prodra recuperar los cambios");
				java.util.Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					cateTable.getItems().remove(selectedIndex);
					Thread eliminarCateHilo = new java.lang.Thread() {
						public void run() {
							try {
								ServiceLocator.getCategoriasService().eliminarCategoria(
										cateBorrar.getId());
							} catch (ServiceException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
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
	private void onAnyadirCategoriaButtonClick() {
		CategoriaItem cateNueva=new CategoriaItem();
		cateNueva.setDescripcion(descripCatTextField.getText());
			try {
				Long id = ServiceLocator.getCategoriasService().crearCategoria(cateNueva);
				cateNueva.setId(id);
				System.out.println("Id creada: " + id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cateTable.getItems().add(cateNueva);
			descripCatTextField.setText("");
	}


}
