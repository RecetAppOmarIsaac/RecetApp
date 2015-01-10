package dad.recetapp.ui;

import dad.recetapp.services.receta.RecetaItem;
import dad.recetapp.services.receta.seccion.ingrediente.IngredienteItem;
import dad.recetapp.services.receta.seccion.instruccion.InstruccionItem;
import dad.recetapp.ui.controllers.IngredienteDialogController;
import dad.recetapp.ui.controllers.InstruccionDialogController;
import dad.recetapp.ui.controllers.RecetaDialogController;

import java.io.IOException;

/**
 * Created by Usuario on 10/01/15.
 */
public class ItemDialogFactory {
	private ItemDialogFactory() {}

	public static ItemDialog<RecetaItem> forRecetaItem() {
		try {
			ItemDialog<RecetaItem> dialog = new ItemDialog<>("/dad/recetapp/ui/fxml/recetaDialogRoot.fxml", new RecetaDialogController());
			return dialog;
		}
		catch (IOException e) {
			System.err.println("recetaDialogRoot.fxml no encontrado: " + e.getMessage() + ", causa: " + e.getCause());
			return null;
		}
	}

	public static ItemDialog<RecetaItem> forRecetaItem(RecetaItem ri) {
		try {
			ItemDialog<RecetaItem> dialog = new ItemDialog<>("/dad/recetapp/ui/fxml/recetaDialogRoot.fxml", new RecetaDialogController());
			dialog.setItem(ri);
			return dialog;
		}
		catch (IOException e) {
			System.err.println("recetaDialogRoot.fxml no encontrado: " + e.getMessage() + ", causa: " + e.getCause());
			return null;
		}
	}

	public static ItemDialog<InstruccionItem> forInstruccionItem() {
		try {
			ItemDialog<InstruccionItem> dialog = new ItemDialog<>("/dad/recetapp/ui/fxml/instruccionDialogRoot.fxml", new InstruccionDialogController());
			return dialog;
		}
		catch (IOException e) {
			System.err.println("instruccionDialogRoot.fxml no encontrado: " + e.getMessage() + ", causa: " + e.getCause());
			return null;
		}
	}

	public static ItemDialog<InstruccionItem> forInstruccionItem(InstruccionItem ii) {
		try {
			ItemDialog<InstruccionItem> dialog = new ItemDialog<>("/dad/recetapp/ui/fxml/instruccionDialogRoot.fxml", new InstruccionDialogController());
			dialog.setItem(ii);
			return dialog;
		}
		catch (IOException e) {
			System.err.println("instruccionDialogRoot.fxml no encontrado: " + e.getMessage() + ", causa: " + e.getCause());
			return null;
		}
	}

	public static ItemDialog<IngredienteItem> forIngredienteItem() {
		try {
			ItemDialog<IngredienteItem> dialog = new ItemDialog<>("/dad/recetapp/ui/fxml/ingredienteDialogRoot.fxml", new IngredienteDialogController());
			return dialog;
		}
		catch (IOException e) {
			System.err.println("ingredienteDialogRoot.fxml no encontrado: " + e.getMessage() + ", causa: " + e.getCause());
			return null;
		}
	}

	public static ItemDialog<IngredienteItem> forIngredienteItem(IngredienteItem ii) {
		try {
			ItemDialog<IngredienteItem> dialog = new ItemDialog<>("/dad/recetapp/ui/fxml/ingredienteDialogRoot.fxml", new IngredienteDialogController());
			dialog.setItem(ii);
			return dialog;
		}
		catch (IOException e) {
			System.err.println("ingredienteDialogRoot.fxml no encontrado: " + e.getMessage() + ", causa: " + e.getCause());
			return null;
		}
	}
}
