package dad.recetapp;


import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.*;
import dad.recetapp.ui.ItemDialogFactory;
import dad.recetapp.ui.controllers.RecetaDialogController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.List;

public class TestRecetaDialog extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {

		RecetaItem recetaItem = ServiceLocator.getRecetasService().obtenerReceta(1L);
		ItemDialogFactory.forRecetaItem(recetaItem).show();

		/*
		RecetaItem ri = new RecetaItem();
		ri.setNombre("Receta test");
		ri.setIdCategoria(1);
		ri.setCantidad(4);
		ri.setPara("gentes");
		ri.setTiempoThermomix(1000);
		ri.setTiempoTotal(1000);

		InstruccionItem is = new InstruccionItem();
		is.setOrden(1);
		is.setDescripcion("Instruccion test");

		TipoIngredienteItem tig = new TipoIngredienteItem();
		tig.setNombre("TipoIng test");

		MedidaItem mi = new MedidaItem();
		mi.setNombre("Medida test");
		mi.setAbreviatura("(test)");

		IngredienteItem ig = new IngredienteItem();
		ig.setTipoIngrediente(tig);
		ig.setCantidad(1);
		ig.setMedida(mi);

		SeccionItem si = new SeccionItem();
		si.setNombre("Seccion test");
		si.setIngredientes(Collections.singletonList(ig));
		si.setInstrucciones(Collections.singletonList(is));

		ri.setSecciones(Collections.singletonList(si));

		ItemDialogFactory.forRecetaItem(ri).show();
		*/
		//ItemDialogFactory.forRecetaItem().show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
