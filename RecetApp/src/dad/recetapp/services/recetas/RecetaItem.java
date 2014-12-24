package dad.recetapp.services.recetas;

import java.util.Date;

public class RecetaItem {
	
	private Integer id;
	private String nombre;
	private Date fechaCreacion;
	private Integer cantidad;
	private String para;
	private Integer tiempoTotal;
	private Integer tiempoThermomix;
	private Integer idCategoria;
	
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
	
}
