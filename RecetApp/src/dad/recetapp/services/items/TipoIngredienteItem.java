package dad.recetapp.services.items;

public class TipoIngredienteItem {
	private Long id;
	private String nombre;

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

	@Override
	public String toString() {
		return nombre;
	}

	//pelin feo, no?
	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof TipoIngredienteItem && this.id == ((TipoIngredienteItem)o).getId())
			return true;
		else
			return false;
	}
}