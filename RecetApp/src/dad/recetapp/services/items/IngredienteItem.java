package dad.recetapp.services.items;

import dad.recetapp.services.IItem;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class IngredienteItem implements IItem {
    private Long id;
    private IntegerProperty cantidad = new SimpleIntegerProperty();
    private ObjectProperty<MedidaItem> medida = new SimpleObjectProperty<>();
    private ObjectProperty<TipoIngredienteItem> tipo = new SimpleObjectProperty<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad.getValue();
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad.setValue(cantidad);
    }

    public MedidaItem getMedida() {
        return medida.getValue();
    }

    public void setMedida(MedidaItem medida) {
        this.medida.setValue(medida);
    }

    public TipoIngredienteItem getTipo() {
        return tipo.getValue();
    }

    public void setTipo(TipoIngredienteItem tipo) {
        this.tipo.setValue(tipo);
    }

    public IntegerProperty cantidadProperty() {
        return cantidad;
    }

    public ObjectProperty<MedidaItem> medidaProperty() {
        return medida;
    }

    public ObjectProperty<TipoIngredienteItem> tipoProperty() {
        return tipo;
    }
}
