package dad.recetapp.services.receta.seccion.ingrediente;

import dad.recetapp.services.receta.seccion.ingrediente.medida.MedidaItem;

public class IngredienteItem {
	private Long id;
	private Integer cantidad;
	private MedidaItem medida;
	private TipoIngredienteItem tipoIngrediente;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	
	public MedidaItem getMedida() {
		return medida;
	}
	public void setMedida(MedidaItem medida) {
		this.medida = medida;
	}
	public TipoIngredienteItem getTipoIngrediente() {
		return tipoIngrediente;
	}
	public void setTipoIngrediente(TipoIngredienteItem tipoIngrediente) {
		this.tipoIngrediente = tipoIngrediente;
	}

	@Override
	public String toString() {
		
		return tipoIngrediente.getNombre()+" "+cantidad+" "+medida.getAbreviatura();
	}
}
