package dad.recetapp.ui.controllers;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.categorias.CategoriaItem;
import dad.recetapp.services.receta.RecetaItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import jfxtras.scene.control.ListSpinner;
import jfxtras.scene.control.ListSpinnerIntegerList;
import jfxtras.util.StringConverterFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by Usuario on 24/12/14.
 */
//TODO Crear RecetaDialogControllerBase y mover los metodos que se puedan ahi
public class EditarRecetaDialogController implements Initializable {
	@FXML private Button aceptarButton;

	@FXML private ComboBox<String> paraCombo;
	@FXML private ComboBox<CategoriaItem> categoriaCombo; //en principio, CategoriaItem

	@FXML private GridPane topGridPane;

	@FXML private TabPane seccionTabPane;
	@FXML private Tab newTab;

	private ListSpinner<Integer> totalMinutosSpinner;
	private ListSpinner<Integer> thermoMinutosSpinner;
	private ListSpinner<Integer> totalSegundosSpinner;
	private ListSpinner<Integer> thermoSegundosSpinner;

	private RecetaItem receta;

	protected String aceptarButtonCaption = "Guardar cambios";

	public RecetaItem getReceta() {
		return receta;
	}

	public void setReceta(RecetaItem receta) {
		this.receta = receta;
		totalSegundosSpinner.setValue(receta.getTiempoTotal() % 60);
		totalMinutosSpinner.setValue(receta.getTiempoTotal() / 60);
		thermoSegundosSpinner.setValue(receta.getTiempoThermomix() % 60);
		thermoMinutosSpinner.setValue(receta.getTiempoThermomix() / 60);

		seccionTabPane.getTabs().clear();
		seccionTabPane.getTabs().add(newTab);
		//TODO añadir tab por seccion y meterle datos
	}

	@FXML public void onAceptarButtonClick() {
		//TODO implementacion de verdad
		System.out.println("Hace falta implementar el modelo entero para guardar cambios");
	}

	@FXML public void onCancelarButtonClick() {
		//TODO implementacion de verdad
		System.out.println("Imagina que me cierro");
	}

	@FXML public void onNewTabSelection() {
		Tab newTab = new Tab();
		createTabContents(newTab);
		seccionTabPane.getTabs().add(seccionTabPane.getTabs().size() -1, newTab);
		seccionTabPane.getSelectionModel().selectPrevious();
	}

	private void createTabContents(Tab tab) {
		Task<BorderPane> task = new Task<BorderPane>() {
			@Override
			protected BorderPane call() {
				BorderPane tabContents = null;
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/dad/recetapp/ui/fxml/recetaTabContent.fxml"));
				RecetaTabContentController controller = new RecetaTabContentController();
				controller.setParentTab(tab);
				loader.setController(controller);
				try {
					tabContents = loader.load();
				}
				catch (IOException e) {
					System.err.println("FXML TabContent noped, " + e.getMessage() + " cause: " + e.getCause());
				}
				return tabContents;
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				tab.setContent(getValue());
			}
		};

		task.run();
	}

	/*
	Por lo pronto, parece que no se pueden asignar limites ni StringConverters via FXML, asi que hay que meterlos programaticamente.
	 */
	private void initSpinners() {
		totalMinutosSpinner = new ListSpinner<Integer>(new ListSpinnerIntegerList(0, 10000)).withEditable(true).withCyclic(true).withStringConverter(StringConverterFactory.forInteger());
		totalSegundosSpinner = new ListSpinner<Integer>(new ListSpinnerIntegerList(0, 59)).withEditable(true).withCyclic(true).withStringConverter(StringConverterFactory.forInteger());
		thermoMinutosSpinner = new ListSpinner<Integer>(new ListSpinnerIntegerList(0, 10000)).withEditable(true).withCyclic(true).withStringConverter(StringConverterFactory.forInteger());
		thermoSegundosSpinner = new ListSpinner<Integer>(new ListSpinnerIntegerList(0, 59)).withEditable(true).withCyclic(true).withStringConverter(StringConverterFactory.forInteger());

		topGridPane.add(totalMinutosSpinner, 3, 0);
		topGridPane.add(totalSegundosSpinner, 5, 0);
		topGridPane.add(thermoMinutosSpinner, 3, 1);
		topGridPane.add(thermoSegundosSpinner, 5, 1);
	}

	private void initCombos() {
		paraCombo.getItems().add("Personas"); //que mas se supone que puede ir aqui?
		paraCombo.setValue("Personas");

		CategoriaItem ci = new CategoriaItem();
		ci.setDescripcion("<Seleccione una categoria>"); //TODO modificar toString()'s de los Items para que solo devuelvan su campo String.
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
				ObservableList<CategoriaItem> ol = FXCollections.observableArrayList(Arrays.asList(c));
				return ol;
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				categoriaCombo.getItems().addAll(getValue());
			}
		};
		task.run();
	}


	public void initialize(URL location, ResourceBundle resources) {
		initSpinners();
		initCombos();

		newTab.setGraphic(new ImageView(getClass().getResource("/dad/recetapp/ui/images/addTabIcon.png").toString())); //intente ponerlo via CSS, no lo consegui

		aceptarButton.setText(aceptarButtonCaption);
	}
}
