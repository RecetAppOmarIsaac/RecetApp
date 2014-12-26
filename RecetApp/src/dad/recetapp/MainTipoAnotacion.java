package dad.recetapp;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.anotaciones.TipoAnotacionItem;


public class MainTipoAnotacion {

//	public static void main(String[] args) {
//		crearTipoAnotacion();
//		modificarTipoAnotacion();
//		eliminarTipoAnotacion();
//		listarTipoAnotacion();
//		obtenerTipoAnotacion();
//		
//
//	}

	private static void modificarTipoAnotacion() {
	TipoAnotacionItem tipo=new TipoAnotacionItem();
		tipo.setId(5L);
		tipo.setDescripcion("Importante");
		try{
			ServiceLocator.getTiposAnotacionesService().modificarTipoAnotacion(tipo);
		}catch(ServiceException e){
			System.out.println("Ha ocurrido un error: "+e.getMessage());
		}
	}


	private static void obtenerTipoAnotacion() {
		try {
			TipoAnotacionItem tipo =ServiceLocator.getTiposAnotacionesService().obtenerTipoAnotacion(2L);
			System.out.println(tipo);
		} catch (Exception e) {
			System.err.println("Ha ocurrido un error:\n" +e.getMessage());
		}

	}

	private static void eliminarTipoAnotacion() {
		try {
			ServiceLocator.getTiposAnotacionesService().eliminarTipoAnotacion(5L);
		} catch (Exception e) {
			System.err.println("Ha ocurrido un error:\n" +e.getMessage());
		}
	}



	private static void listarTipoAnotacion() {
		try {
			TipoAnotacionItem[] tipos=ServiceLocator.getTiposAnotacionesService().listarTiposAnotaciones();
			for (int i = 0; i < tipos.length; i++) {
				System.out.println(tipos[i]+"; ");
			}

		} catch (Exception e) {
			System.err.println("Ha ocurrido un error:\n" +e.getMessage());
		}
	}

	private static void crearTipoAnotacion() {
		TipoAnotacionItem tipo=new TipoAnotacionItem();
		tipo.setDescripcion("Apunte");
		try{
			ServiceLocator.getTiposAnotacionesService().crearTipoAnotacion(tipo);
		}catch(ServiceException e){
			System.out.println("Ha ocurrido un error: "+e.getMessage());
		}
	}

}

