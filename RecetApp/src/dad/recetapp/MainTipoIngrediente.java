package dad.recetapp;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.TipoIngredienteItem;


public class MainTipoIngrediente {

	public static void main(String[] args) {
		crearTipoIngrediente();
		modificarTipoIngrediente();
		eliminarTipoIngrediente();
		listarTipoIngrediente();
		obtenerTipoIngrediente();

	}

	private static void modificarTipoIngrediente() {
		TipoIngredienteItem tipo=new TipoIngredienteItem();
		tipo.setId(1L);
		tipo.setNombre("cebolla");
		try{
			ServiceLocator.getTipoIngredienteService().modificarTipoIngrediente(tipo);
		}catch(ServiceException e){
			System.out.println("Ha ocurrido un error: "+e.getMessage());
		}
	}


	private static void obtenerTipoIngrediente() {
		try {
			TipoIngredienteItem tipo = ServiceLocator.getTipoIngredienteService().obtenerTipoIngrediente(12L);
			System.out.println(tipo);
		} catch (Exception e) {
			System.err.println("Ha ocurrido un error:\n" +e.getMessage());
		}

	}

	private static void eliminarTipoIngrediente() {
		try {
			ServiceLocator.getTipoIngredienteService().eliminarTipoIngrediente(14L);
		} catch (Exception e) {
			System.err.println("Ha ocurrido un error:\n" +e.getMessage());
		}
	}



	private static void listarTipoIngrediente() {
		try {
			TipoIngredienteItem[] tipos=ServiceLocator.getTipoIngredienteService().listarTipoIngredientes();
			for (int i = 0; i < tipos.length; i++) {
				System.out.println(tipos[i]+"; ");
			}

		} catch (Exception e) {
			System.err.println("Ha ocurrido un error:\n" +e.getMessage());
		}
	}

	private static void crearTipoIngrediente() {
		TipoIngredienteItem tipo=new TipoIngredienteItem();
		tipo.setNombre("vinagre");
		try{
			ServiceLocator.getTipoIngredienteService().crearTipoIngrediente(tipo);
		}catch(ServiceException e){
			System.out.println("Ha ocurrido un error: "+e.getMessage());
		}
	}

}