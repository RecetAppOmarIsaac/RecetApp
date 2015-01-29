package dad.recetapp.ui.controllers;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.SeccionItem;
import dad.recetapp.ui.SeccionTab;
import dad.recetapp.services.items.CategoriaItem;
import dad.recetapp.services.items.RecetaItem;
import dad.recetapp.utils.SpinnerStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jfxtras.scene.control.ListSpinner;
import jfxtras.scene.control.ListSpinnerIntegerList;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;


public class RecetaDialogController implements IDialogController<RecetaItem> {
	public static final String NEW_CAPTION = "A�adir";
	public static final String EDIT_CAPTION = "Guardar cambios";

	@FXML private Parent rootPane;
	@FXML private Button aceptarButton;
	@FXML private TextField paraText;
	@FXML private TextField nombreText;
	@FXML private ComboBox<String> paraCombo;
	@FXML private ComboBox<CategoriaItem> categoriaCombo;
	@FXML private GridPane topGridPane;
	@FXML private TabPane seccionTabPane;
	@FXML private Tab newTab;

	private ListSpinner<Integer> totalMinutosSpinner;
	private ListSpinner<Integer> thermoMinutosSpinner;
	private ListSpinner<Integer> totalSegundosSpinner;
	private ListSpinner<Integer> thermoSegundosSpinner;

	private Optional<RecetaItem> receta = Optional.empty();


	@FXML public void onAceptarButtonClick() {
		RecetaItem ri = receta.orElse(new RecetaItem());
		//TODO guardar cosas en ri
		ri.setNombre(nombreText.getText());
		ri.setCantidad((paraText.getText().isEmpty())? 0 : Integer.valueOf(paraText.getText()));
		ri.setPara(paraCombo.getValue());
		ri.setCategoria(categoriaCombo.getValue());
		ri.setTiempoTotal(totalMinutosSpinner.getValue() * 60 + totalSegundosSpinner.getValue());
		ri.setTiempoThermomix(thermoMinutosSpinner.getValue() * 60 + thermoSegundosSpinner.getValue());
		ri.getSecciones().removeAll(ri.getSecciones());
		seccionTabPane.getTabs().filtered(tab -> tab != newTab).forEach(tab -> {
			SeccionTab stab = (SeccionTab)tab;
			if (stab.getController().getItem().isPresent())
				ri.getSecciones().add(stab.getController().getItem().get());
		});
		receta = Optional.of(ri);
		Stage s = (Stage) rootPane.getScene().getWindow();

		//TODO quitar esto al terminar de depurar
		System.out.println("----data dump----");
		System.out.println("Nombre: " + ri.getNombre());
		System.out.println("Para " + ri.getCantidad() + " " + ri.getPara());
		System.out.println("Secciones.len: " + ri.getSecciones().size());
		ri.getSecciones().forEach(seccionItem -> {
			System.out.println("{");
			System.out.println("SeccionItem: " + seccionItem.getNombre());
			System.out.println("Ingreds.len: " + seccionItem.getIngredientes().size());
			seccionItem.getIngredientes().forEach(item -> System.out.println(item.getTipo().getNombre() + " " + item.getCantidad()));
			System.out.println("Instrucc.len: " + seccionItem.getInstrucciones().size());
			seccionItem.getInstrucciones().forEach(item -> System.out.println(item.getDescripcion()));
			System.out.println("}");
		});
		System.out.println("------------------");

		s.close();
	}

	@FXML public void onCancelarButtonClick() {
		Stage s = (Stage) rootPane.getScene().getWindow();
		s.close();
	}

	@FXML public void onNewTabSelection() {
		addTab(Optional.of(new SeccionItem()));
		seccionTabPane.getSelectionModel().selectPrevious();
	}

	private void addTab() {
		addTab(Optional.empty());
	}

	private void addTab(Optional<SeccionItem> item) {
		SeccionTab tab = new SeccionTab();
		createTabContents(tab, item);
		seccionTabPane.getTabs().add(seccionTabPane.getTabs().size() - 1, tab);
	}

	private void createTabContents(SeccionTab tab, Optional<SeccionItem> item) {
		Task<BorderPane> task = new Task<BorderPane>() {
			@Override
			protected BorderPane call() {
				BorderPane tabContents = null;
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/dad/recetapp/ui/fxml/recetaTabContent.fxml"));
				RecetaTabContentController controller = new RecetaTabContentController()
						.withParentTab(tab)
						.withParentTabPane(seccionTabPane);
				loader.setController(controller);
				try {
					tabContents = loader.load();
				}
				catch (IOException e) {
					System.err.println("FXML TabContent noped, " + e.getMessage() + " cause: " + e.getCause());
				}
				if (item.isPresent())
					controller.setItem(item);
				tab.setController(controller);
				return tabContents;
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				tab.setContent(getValue());
			}
		};

		task.run();
		//TODO Task ejecuta call() en hilo de eventos, usar otra clase. Cazar todas los usos de ServiceLocator en GUI y echarlos del hilo
	}

	/*
	Por lo pronto, parece que no se pueden asignar limites ni StringConverters via FXML, asi que hay que meterlos programaticamente.
	 */
	private void initSpinners() {
		totalMinutosSpinner = new ListSpinner<>(new ListSpinnerIntegerList(0, 240)).withEditable(true).withCyclic(true).withStringConverter(new SpinnerStringConverter());
		totalMinutosSpinner.setBackground(Background.EMPTY);
		totalSegundosSpinner = new ListSpinner<>(new ListSpinnerIntegerList(0, 59)).withEditable(true).withCyclic(true).withStringConverter(new SpinnerStringConverter());
		totalSegundosSpinner.setBackground(Background.EMPTY);
		thermoMinutosSpinner = new ListSpinner<>(new ListSpinnerIntegerList(0, 240)).withEditable(true).withCyclic(true).withStringConverter(new SpinnerStringConverter());
		thermoMinutosSpinner.setBackground(Background.EMPTY);
		thermoSegundosSpinner = new ListSpinner<>(new ListSpinnerIntegerList(0, 59)).withEditable(true).withCyclic(true).withStringConverter(new SpinnerStringConverter());
		thermoSegundosSpinner.setBackground(Background.EMPTY);
		topGridPane.add(totalMinutosSpinner, 3, 0);
		topGridPane.add(totalSegundosSpinner, 5, 0);
		topGridPane.add(thermoMinutosSpinner, 3, 1);
		topGridPane.add(thermoSegundosSpinner, 5, 1);
	}

	private void initCombos() {
		paraCombo.getItems().addAll("Personas", "Unidades", "Raciones");
		paraCombo.setValue("Personas");
		CategoriaItem ci = new CategoriaItem();
		ci.setDescripcion("<Seleccione una categoria>");
		categoriaCombo.setValue(ci);
		Task<ObservableList<CategoriaItem>> task = new Task<ObservableList<CategoriaItem>>() {
			@Override
			protected ObservableList<CategoriaItem> call() {
				CategoriaItem[] c = new CategoriaItem[0];
				try {
					c = ServiceLocator.getCategoriasService().listarCategoria();
				}
				catch (ServiceException e) {
					System.err.println("CategoriaService Error: " + e.getMessage() + " Cause: " + e.getCause());
				}
				return FXCollections.observableArrayList(Arrays.asList(c));
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				categoriaCombo.getItems().addAll(getValue());
			}
		};
		task.run();
	}

	private void initValidation() {
		ValidationSupport validationSupport = new ValidationSupport();
		validationSupport.setValidationDecorator(new GraphicValidationDecoration());
		validationSupport.registerValidator(nombreText, Validator.createEmptyValidator("Introduzca un nombre para la receta"));
		validationSupport.registerValidator(categoriaCombo, Validator.createEqualsValidator("Asigne una categoria", categoriaCombo.getItems()));
		validationSupport.invalidProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue && !newValue)
				aceptarButton.setDisable(false);
			else
				aceptarButton.setDisable(true);
		});
	}

	public void initialize(URL location, ResourceBundle resources) {
		initSpinners();
		initCombos();
		initValidation();

		newTab.setGraphic(new ImageView(getClass().getResource("/dad/recetapp/ui/images/addTabIcon.png").toString())); //intente ponerlo via CSS, no lo consegui

		aceptarButton.setText(NEW_CAPTION);
	}

	@Override
	public void setItem(Optional<RecetaItem> item) {
		RecetaItem ri = item.get();
		receta = Optional.of(ri);
		aceptarButton.setText(EDIT_CAPTION);

		paraText.setText(ri.getCantidad().toString());
		paraCombo.setValue(ri.getPara());
		nombreText.setText(ri.getNombre());

		categoriaCombo.setValue(ri.getCategoria());

		totalSegundosSpinner.setValue(ri.getTiempoTotal() % 60);
		totalMinutosSpinner.setValue(ri.getTiempoTotal() / 60);
		thermoSegundosSpinner.setValue(ri.getTiempoThermomix() % 60);
		thermoMinutosSpinner.setValue(ri.getTiempoThermomix() / 60);

		seccionTabPane.getTabs().clear();
		seccionTabPane.getTabs().add(newTab);
		if (ri.getSecciones() != null)
			ri.getSecciones().forEach(seccionItem -> addTab(Optional.of(seccionItem)));
		if (seccionTabPane.getTabs().size() > 2 && seccionTabPane.getTabs().get(0).getText() == null) { //haaaack
			seccionTabPane.getTabs().remove(0);
		}
	}

	@Override
	public Optional<RecetaItem> getItem() {
		return receta;
	}
}
