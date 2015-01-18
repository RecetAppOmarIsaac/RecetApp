package dad.recetapp;

import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.*;
import dad.recetapp.ui.ItemDialog;
import dad.recetapp.ui.ItemDialogFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestRecetaDialog extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		RecetaItem ri = ServiceLocator.getRecetasService().obtenerReceta(1L);

		InstruccionItem is = new InstruccionItem();
		is.setOrden(1);
		is.setDescripcion("Instruccion test");

		TipoIngredienteItem tig = new TipoIngredienteItem();
		tig.setNombre("TipoIng test");

		MedidaItem mi = new MedidaItem();
		mi.setNombre("Medida test");
		mi.setAbreviatura("(test)");

		IngredienteItem ig = new IngredienteItem();
		ig.setTipo(tig);
		ig.setCantidad(1);
		ig.setMedida(mi);

		SeccionItem si = new SeccionItem();
		si.setNombre("Seccion test");
		si.getIngredientes().add(ig);
		si.getInstrucciones().add(is);

		ri.getSecciones().add(si);

		ItemDialogFactory.forRecetaItem(ri).show();

		ItemDialogFactory.forRecetaItem().show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
