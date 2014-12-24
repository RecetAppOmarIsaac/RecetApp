package dad.recetapp.services.medidas;

public class MedidaItem {
	private Integer id;
	private String nombre;
	private String abreviatura;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
		return id+".- "+nombre+" ("+abreviatura+")";
	}

}
