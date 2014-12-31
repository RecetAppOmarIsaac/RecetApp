package dad.recetapp;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.receta.seccion.ingrediente.medida.MedidaItem;


public class MainMedida {

	public static void main(String[] args) {
		crearMedida();
		modificarMedida();
		eliminarMedida();
		listarMedidas();
		obtenerMedida();
	
		

	}

	private static void modificarMedida() {
		MedidaItem medida=new MedidaItem();
		medida.setId(10L);
		medida.setNombre("Kilogramo(s)");
		medida.setAbreviatura("kg");
		try{
			ServiceLocator.getMedidasService().modificarMedida(medida);
		}catch(ServiceException e){
			System.out.println("Ha ocurrido un error: "+e.getMessage());
		}
	}


	private static void obtenerMedida() {
		try {
			MedidaItem medida = ServiceLocator.getMedidasService().obtenerMedida(3L);
			System.out.println(medida);
		} catch (Exception e) {
			System.err.println("Ha ocurrido un error:\n" +e.getMessage());
		}
		
	}

	private static void eliminarMedida() {
		try {
			ServiceLocator.getMedidasService().eliminarMedida(11L);
		} catch (Exception e) {
			System.err.println("Ha ocurrido un error:\n" +e.getMessage());
		}
	}
		
	

	private static void listarMedidas() {
		try {
			MedidaItem[] medidas=ServiceLocator.getMedidasService().listarMedidas();
			for (int i = 0; i < medidas.length; i++) {
				System.out.println(medidas[i]+"; ");
			}
			
		} catch (Exception e) {
			System.err.println("Ha ocurrido un error:\n" +e.getMessage());
		}
	}

	private static void crearMedida() {
		MedidaItem medida=new MedidaItem();
		medida.setNombre("Litros");
		medida.setAbreviatura("l");
		try{
			ServiceLocator.getMedidasService().crearMedida(medida);
		}catch(ServiceException e){
			System.out.println("Ha ocurrido un error: "+e.getMessage());
		}
	}

}
