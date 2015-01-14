package dad.recetapp.ui;


import dad.recetapp.services.items.SeccionItem;
import dad.recetapp.ui.controllers.IDialogController;
import javafx.scene.control.Tab;


public class SeccionTab extends Tab {
	private IDialogController<SeccionItem> controller;


	public IDialogController<SeccionItem> getController() {
		return controller;
	}

	public void setController(IDialogController<SeccionItem> controller) {
		this.controller = controller;
	}
}
