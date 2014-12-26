package dad.recetapp.services.categorias;

public class CategoriaItem {
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	private Long id;
	private String descripcion;
	@Override
	public String toString() {
		
		return id+".- "+descripcion;
	}
}
