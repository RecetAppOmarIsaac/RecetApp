package dad.recetapp.tests;

import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.AnotacionItem;
import dad.recetapp.services.items.IngredienteItem;
import dad.recetapp.services.items.InstruccionItem;
import dad.recetapp.services.items.RecetaItem;
import dad.recetapp.services.items.RecetaListItem;
import dad.recetapp.services.items.SeccionItem;

public class TestReceta {
    public static void main(String[] args) throws SQLException {
        crearReceta();
        eliminarReceta();
        buscarReceta();
        listarRecetas();

    }

    private static void buscarReceta() {
        try {
            List<RecetaListItem> recetas = ServiceLocator.getRecetasService()
                    .buscarRecetas("p", 2, 1L);
            System.out.println(recetas);
        } catch (Exception e) {
            System.err.println("Ha ocurrido un error:\n" + e.getMessage());
        }
    }

    private static void eliminarReceta() {
        try {
            ServiceLocator.getRecetasService().eliminarReceta(2L);
        } catch (Exception e) {
            System.err.println("Ha ocurrido un error:\n" + e.getMessage());
        }
    }

    private static void listarRecetas() {
        try {
            List<RecetaListItem> recetas = ServiceLocator.getRecetasService()
                    .listarRecetas();
            System.out.println(recetas);
        } catch (Exception e) {
            System.err.println("Ha ocurrido un error:\n" + e.getMessage());
        }
    }

    private static void crearReceta() {
        try {
            RecetaItem receta = new RecetaItem();
            AnotacionItem anotacion = new AnotacionItem();
            anotacion
                    .setAnotaciones("No olvide precalentar el horno solo la parte de arriba");
            anotacion.setTipo(ServiceLocator.getTiposAnotacionesService()
                    .obtenerTipoAnotacion(1L));
            receta.setNombre("Pollo al curry");
            receta.setFechaCreacion(new GregorianCalendar(2014, 3, 14)
                    .getTime());
            receta.setCantidad(4);
            receta.setCategoria(ServiceLocator.getCategoriasService()
                    .obtenerCategoria(1L));
            receta.setPara("4 personas");
            receta.setTiempoTotal(50);
            receta.setTiempoThermomix(25);
            receta.getAnotaciones().add(anotacion);
            IngredienteItem ingrediente = new IngredienteItem();
            ingrediente.setCantidad(1);
            ingrediente.setMedida(ServiceLocator.getMedidasService()
                    .obtenerMedida(1L));
            ingrediente.setTipo(ServiceLocator.getTiposIngredientesService()
                    .obtenerTipoIngrediente(1L));
            InstruccionItem instruccion = new InstruccionItem();
            instruccion.setDescripcion("Limpiar el pollo");
            instruccion.setOrden(1);
            SeccionItem seccion = new SeccionItem();
            seccion.setNombre("Salsa de curry");
            seccion.getIngredientes().add(ingrediente);
            seccion.getInstrucciones().add(instruccion);
            receta.getSecciones().add(seccion);

            ServiceLocator.getRecetasService().crearReceta(receta);
        } catch (ServiceException e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }
}
