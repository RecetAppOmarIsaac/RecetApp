package dad.recetapp.ui.controllers;

import dad.recetapp.services.IItem;
import javafx.fxml.Initializable;

import java.util.Optional;

public interface IDialogController<T extends IItem> extends Initializable {
	void setItem(Optional<T> item);
	Optional<T> getItem();

	default IDialogController editingItem(Optional<T> item) {
		setItem(item);
		return this;
	}
}
