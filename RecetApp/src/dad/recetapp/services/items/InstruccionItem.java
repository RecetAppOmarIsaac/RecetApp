package dad.recetapp.services.items;

import dad.recetapp.services.IItem;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InstruccionItem implements IItem {
	private Long id;
	private IntegerProperty orden = new SimpleIntegerProperty();
	private StringProperty descripcion = new SimpleStringProperty();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOrden() {
		return orden.getValue();
	}

	public void setOrden(Integer orden) {
		this.orden.setValue(orden);
	}

	public String getDescripcion() {
		return descripcion.getValue();
	}

	public void setDescripcion(String descripcion) {
		this.descripcion.setValue(descripcion);
	}

	public IntegerProperty ordenProperty() {
		return orden;
	}

	public StringProperty descripcionProperty() {
		return descripcion;
	}
	@Override
	public String toString() {
		return descripcion.getValue();
	}

}
