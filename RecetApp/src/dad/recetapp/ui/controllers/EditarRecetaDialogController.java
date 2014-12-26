package dad.recetapp.ui.controllers;

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
import java.util.ResourceBundle;

/**
 * Created by Usuario on 24/12/14.
 */
public class EditarRecetaDialogController implements Initializable {
	@FXML private Button aceptarButton;

	@FXML private GridPane topGridPane;

	@FXML private TabPane seccionTabPane;
	@FXML private Tab emptyDefaultTab;
	@FXML private Tab newTab;

	private ListSpinner<Integer> totalMinutosSpinner;
	private ListSpinner<Integer> thermoMinutosSpinner;
	private ListSpinner<Integer> totalSegundosSpinner;
	private ListSpinner<Integer> thermoSegundosSpinner;


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
		BorderPane tabContents = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/dad/recetapp/ui/fxml/recetaTabContent.fxml"));
			RecetaTabContentController controller = new RecetaTabContentController();
			controller.setParentTab(tab);
			loader.setController(controller);
			tabContents = loader.load();
		} catch (IOException e) {
			System.err.println("FXML TabContent noped, " + e.getMessage() + " cause: " + e.getCause());
		}
		if (tabContents != null)
			tab.setContent(tabContents);
	}

	/*
	Por lo pronto, parece que no se pueden asignar limites ni StringConverters via FXML, asi que hay que meterlos programaticamente.
	 */
	private void initSpinners() {
		totalMinutosSpinner = new ListSpinner<Integer>(new ListSpinnerIntegerList(0, 59)).withEditable(true).withCyclic(true).withStringConverter(StringConverterFactory.forInteger());
		totalSegundosSpinner = new ListSpinner<Integer>(new ListSpinnerIntegerList(0, 59)).withEditable(true).withCyclic(true).withStringConverter(StringConverterFactory.forInteger());
		thermoMinutosSpinner = new ListSpinner<Integer>(new ListSpinnerIntegerList(0, 59)).withEditable(true).withCyclic(true).withStringConverter(StringConverterFactory.forInteger());
		thermoSegundosSpinner = new ListSpinner<Integer>(new ListSpinnerIntegerList(0, 59)).withEditable(true).withCyclic(true).withStringConverter(StringConverterFactory.forInteger());

		topGridPane.add(totalMinutosSpinner, 3, 0);
		topGridPane.add(totalSegundosSpinner, 5, 0);
		topGridPane.add(thermoMinutosSpinner, 3, 1);
		topGridPane.add(thermoSegundosSpinner, 5, 1);
	}


	public void initialize(URL location, ResourceBundle resources) {
		//TODO cargar comboboxes, a la espera de TipoIngredienteItem, etc
		//TODO el iconito de la aplicacion en la esquina
		initSpinners();

		newTab.setGraphic(new ImageView(getClass().getResource("/dad/recetapp/ui/images/addTabIcon.png").toString())); //intente ponerlo via CSS, no lo consegui
		createTabContents(emptyDefaultTab);

		aceptarButton.setText("Guardar cambios");
	}
}
