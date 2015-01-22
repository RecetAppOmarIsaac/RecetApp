package dad.recetapp.ui.model.items;

import dad.recetapp.services.items.IngredienteItem;
import dad.recetapp.services.items.MedidaItem;
import dad.recetapp.services.items.TipoIngredienteItem;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class IngredienteItemFX extends IngredienteItem {
    private IntegerProperty cantidadProperty = new SimpleIntegerProperty();
    private ObjectProperty<MedidaItem> medidaProperty = new SimpleObjectProperty<>();
    private ObjectProperty<TipoIngredienteItem> tipoProperty = new SimpleObjectProperty<>();

    public IntegerProperty cantidadProperty() {
        return cantidadProperty;
    }

    public ObjectProperty<MedidaItem> medidaProperty() {
        return medidaProperty;
    }

    public ObjectProperty<TipoIngredienteItem> tipoProperty() {
        return tipoProperty;
    }

    @Override
    public void setCantidad(Integer cantidad) {
        super.setCantidad(cantidad);
        cantidadProperty.setValue(cantidad);
    }

    @Override
    public void setMedida(MedidaItem medida) {
        super.setMedida(medida);
        medidaProperty.setValue(medida);
    }

    @Override
    public void setTipo(TipoIngredienteItem tipo) {
        super.setTipo(tipo);
        tipoProperty.setValue(tipo);
    }

    public IngredienteItem asIngredienteItem() {
        IngredienteItem ii = new IngredienteItem();
        ii.setId(this.getId());
        ii.setMedida(this.getMedida());
        ii.setCantidad(this.getCantidad());
        ii.setTipo(this.getTipo());
        return ii;
    }

    public static IngredienteItemFX fromIngredienteItem(IngredienteItem ii) {
        IngredienteItemFX iifx = new IngredienteItemFX();
        iifx.setId(ii.getId());
        iifx.setMedida(ii.getMedida());
        iifx.setCantidad(ii.getCantidad());
        iifx.setTipo(ii.getTipo());
        return iifx;
    }
}
