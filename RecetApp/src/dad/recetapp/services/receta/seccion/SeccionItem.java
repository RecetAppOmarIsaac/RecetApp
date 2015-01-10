package dad.recetapp.services.receta.seccion;

import java.util.List;

import dad.recetapp.services.IItem;
import dad.recetapp.services.receta.seccion.ingrediente.IngredienteItem;
import dad.recetapp.services.receta.seccion.instruccion.InstruccionItem;

public class SeccionItem implements IItem {
	private Long id;
	private String nombre;
	private List<IngredienteItem>ingredientes;
	private List<InstruccionItem>instrucciones;
	
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
	public void setIngredientes(List<IngredienteItem> ingredientes) {
		this.ingredientes = ingredientes;
	}
	public List<InstruccionItem> getInstrucciones() {
		return instrucciones;
	}
	public void setInstrucciones(List<InstruccionItem> instrucciones) {
		this.instrucciones = instrucciones;
	}
	
}
