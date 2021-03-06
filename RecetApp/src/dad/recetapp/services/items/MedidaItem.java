package dad.recetapp.services.items;

public class MedidaItem {
	private Long id;
	private String nombre;
	private String abreviatura;

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

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	@Override
	public String toString() {
		return nombre + (abreviatura != null? " (" + abreviatura + ")" : "");
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof MedidaItem && this.id == ((MedidaItem)o).getId())
			return true;
		else
			return false;
	}
}
