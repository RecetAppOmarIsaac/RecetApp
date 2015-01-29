package dad.recetapp.services.items;

import java.util.ArrayList;
import java.util.List;

import dad.recetapp.services.IItem;

public class SeccionItem implements IItem {
	private Long id;
	private String nombre;
	private List<IngredienteItem> ingredientes = new ArrayList<IngredienteItem>();
	private List<InstruccionItem> instrucciones = new ArrayList<InstruccionItem>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<IngredienteItem> getIngredientes() {
		return ingredientes;
	}

	public List<InstruccionItem> getInstrucciones() {
		return instrucciones;
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof SeccionItem && this.id == ((SeccionItem)o).getId())
			return true;
		else
			return false;
	}

	@Override
	public String toString() {
		return nombre;
	}

}