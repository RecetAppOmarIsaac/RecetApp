package dad.recetapp.services.items;

import dad.recetapp.services.IItem;


public class IngredienteItem implements IItem {
    private Long id;
    private Integer cantidad;
    private MedidaItem medida;
    private TipoIngredienteItem tipo;

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

    public TipoIngredienteItem getTipo() {
        return tipo;
    }

    public void setTipo(TipoIngredienteItem tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof IngredienteItem && this.id == ((IngredienteItem)o).getId())
            return true;
        else
            return false;
    }
}
