package dad.recetapp.ui.model.items;

import dad.recetapp.services.items.InstruccionItem;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InstruccionItemFX extends InstruccionItem {
    private IntegerProperty ordenProperty = new SimpleIntegerProperty();
    private StringProperty descripcionProperty = new SimpleStringProperty();

    public IntegerProperty ordenProperty() {
        return ordenProperty;
    }

    public StringProperty descripcionProperty() {
        return descripcionProperty;
    }

    @Override
    public void setOrden(Integer orden) {
        super.setOrden(orden);
        ordenProperty.setValue(orden);
    }

    @Override
    public void setDescripcion(String descripcion) {
        super.setDescripcion(descripcion);
        descripcionProperty.setValue(descripcion);
    }

    public InstruccionItem asInstruccionItem() {
        InstruccionItem ii = new InstruccionItem();
        ii.setId(this.getId());
        ii.setOrden(this.getOrden());
        ii.setDescripcion(this.getDescripcion());
        return ii;
    }

    public static InstruccionItemFX fromInstruccionItem(InstruccionItem ii) {
        InstruccionItemFX iifx = new InstruccionItemFX();
        iifx.setId(ii.getId());
        iifx.setOrden(ii.getOrden());
        iifx.setDescripcion(ii.getDescripcion());
        return iifx;
    }
}
