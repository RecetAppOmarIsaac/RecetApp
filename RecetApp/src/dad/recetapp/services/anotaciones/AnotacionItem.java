package dad.recetapp.services.anotaciones;


public class AnotacionItem {
	private Long id;
	private String anotaciones;
	private TipoAnotacionItem tipoAnotacion;
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getAnotaciones() {
		return anotaciones;
	}


	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}

	
	@Override
	public String toString() {
		
		return tipoAnotacion.getDescripcion()+" "+anotaciones;
	}


	public TipoAnotacionItem getTipoAnotacion() {
		return tipoAnotacion;
	}


	public void setTipoAnotacion(TipoAnotacionItem tipoAnotacion) {
		this.tipoAnotacion = tipoAnotacion;
	}
}
