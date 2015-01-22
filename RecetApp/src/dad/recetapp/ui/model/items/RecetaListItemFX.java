package dad.recetapp.ui.model.items;

import dad.recetapp.services.items.RecetaListItem;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class RecetaListItemFX extends RecetaListItem {
    private StringProperty nombreProperty = new SimpleStringProperty ();
    private StringProperty paraProperty = new SimpleStringProperty();
    private IntegerProperty tiempoTotalProperty = new SimpleIntegerProperty();

    @Override
    public void setNombre(String nombre) {
        super.setNombre(nombre);
        nombreProperty.setValue(nombre);
    }

    public StringProperty nombreProperty(){
        return nombreProperty;
    }

    public StringProperty paraProperty(){
        return paraProperty;
    }

    @Override
    public void setCantidad(Integer cantidad) {
        super.setCantidad(cantidad);
        paraProperty.setValue(cantidad + " " + getPara());
    }

    @Override
    public void setPara(String para) {
        super.setPara(para);
        paraProperty.setValue(getCantidad() + " " + para);
    }

    public IntegerProperty tiempoTotalProperty(){
        return tiempoTotalProperty;
    }

    @Override
    public void setTiempoTotal(Integer tiempoTotal) {
        super.setTiempoTotal(tiempoTotal);
        tiempoTotalProperty.setValue(tiempoTotal);
    }

    public RecetaListItem asRecetaListItem() {
        RecetaListItem rli = new RecetaListItem();
        rli.setId(this.getId());
        rli.setCantidad(this.getCantidad());
        rli.setCategoria(this.getCategoria());
        rli.setFechaCreacion(this.getFechaCreacion());
        rli.setNombre(this.getNombre());
        rli.setPara(this.getPara());
        rli.setTiempoThermomix(this.getTiempoThermomix());
        rli.setTiempoTotal(this.getTiempoTotal());
        return rli;
    }

    public static RecetaListItemFX fromRecetaListItem(RecetaListItem rli) {
        RecetaListItemFX rlifx = new RecetaListItemFX();
        rlifx.setId(rli.getId());
        rlifx.setNombre(rli.getNombre());
        rlifx.setCategoria(rli.getCategoria());
        rlifx.setCantidad(rli.getCantidad());
        rlifx.setPara(rli.getPara());
        rlifx.setFechaCreacion(rli.getFechaCreacion());
        rlifx.setTiempoThermomix(rli.getTiempoThermomix());
        rlifx.setTiempoTotal(rli.getTiempoTotal());
        return rlifx;
    }
}
