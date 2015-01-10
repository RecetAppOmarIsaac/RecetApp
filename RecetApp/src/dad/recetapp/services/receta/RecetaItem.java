package dad.recetapp.services.receta;

import java.util.Date;
import java.util.List;

import dad.recetapp.services.IItem;
import dad.recetapp.services.anotaciones.AnotacionItem;
import dad.recetapp.services.receta.seccion.SeccionItem;

public class RecetaItem implements IItem {
	
	private Long id;
	private String nombre;
	private Date fechaCreacion;
	private Integer cantidad;
	private String para;
	private Integer tiempoTotal;
	private Integer tiempoThermomix;
	private Integer idCategoria;
	private List<AnotacionItem> anotaciones;
	private List<SeccionItem>secciones;
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


	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	
	
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}


	public Integer getCantidad() {
		return cantidad;
	}


	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}


	public String getPara() {
		return para;
	}


	public void setPara(String para) {
		this.para = para;
	}


	public Integer getTiempoTotal() {
		return tiempoTotal;
	}


	public void setTiempoTotal(Integer tiempoTotal) {
		this.tiempoTotal = tiempoTotal;
	}


	public Integer getTiempoThermomix() {
		return tiempoThermomix;
	}


	public void setTiempoThermomix(Integer tiempoThermomix) {
		this.tiempoThermomix = tiempoThermomix;
	}


	public Integer getIdCategoria() {
		return idCategoria;
	}


	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}


	
	@Override
	public String toString() {
		return id+ ".- "+ nombre; 
	}


	public List<AnotacionItem> getAnotaciones() {
		return anotaciones;
	}


	public void setAnotaciones(List<AnotacionItem> anotaciones) {
		this.anotaciones = anotaciones;
	}


	public List<SeccionItem> getSecciones() {
		return secciones;
	}


	public void setSecciones(List<SeccionItem> secciones) {
		this.secciones = secciones;
	}
	
}
