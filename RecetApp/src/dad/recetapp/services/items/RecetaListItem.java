package dad.recetapp.services.items;

import java.util.Date;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RecetaListItem {
    private Long id;
    private StringProperty nombre=new SimpleStringProperty();
    private Date fechaCreacion;
    private Integer cantidad;
    private String para;
    private StringProperty paraProperty=new SimpleStringProperty();
    private IntegerProperty tiempoTotal= new SimpleIntegerProperty();
    private Integer tiempoThermomix;
    private String categoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre.getValue();
    }

    public void setNombre(String nombre) {
        this.nombre.setValue(nombre);
    }
    
    public StringProperty nombreProperty(){
    	return nombre;
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
    	paraProperty.setValue(cantidad+" "+para);
        this.cantidad = cantidad;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
    	paraProperty.setValue(cantidad+" "+para);
        this.para = para;
    }
    
    public StringProperty paraProperty(){
    	return paraProperty;
    }

    public Integer getTiempoTotal() {
        return tiempoTotal.getValue();
    }

    public void setTiempoTotal(Integer tiempoTotal) {
        this.tiempoTotal.setValue(tiempoTotal);
    }
    
    public IntegerProperty tiempoTotalProperty(){
    	return tiempoTotal;
    }

    public Integer getTiempoThermomix() {
        return tiempoThermomix;
    }

    public void setTiempoThermomix(Integer tiempoThermomix) {
        this.tiempoThermomix = tiempoThermomix;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RecetaListItem) {
            RecetaListItem tipo = (RecetaListItem) obj;
            return tipo.getId() == this.id;
        }
        return false;
    }

    @Override
    public String toString() {
        return nombre.getValue();
    }

}
