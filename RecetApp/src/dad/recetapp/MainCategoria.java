package dad.recetapp;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.categorias.CategoriaItem;

public class MainCategoria {
	public static void main(String[] args) {
		crearCategoria();
		modificarCategoria();
		eliminarCategoria();
		listarCategoria();
		obtenerCategoria();

	}

	private static void modificarCategoria() {
		CategoriaItem categoria=new CategoriaItem();
		categoria.setId(1L);
		categoria.setDescripcion("Carnes");
		try{
			ServiceLocator.getCategoriasService().modificarCategoria(categoria);
		}catch(ServiceException e){
			System.out.println("Ha ocurrido un error: "+e.getMessage());
		}
	}


	private static void obtenerCategoria() {
		try {
			CategoriaItem categoria=ServiceLocator.getCategoriasService().obtenerCategoria(2L);
			System.out.println(categoria);
		} catch (Exception e) {
			System.err.println("Ha ocurrido un error:\n" +e.getMessage());
		}

	}

	private static void eliminarCategoria() {
		try {
			ServiceLocator.getCategoriasService().eliminarCategoria(6L);
		} catch (Exception e) {
			System.err.println("Ha ocurrido un error:\n" +e.getMessage());
		}
	}



	private static void listarCategoria() {
		try {
			CategoriaItem[] categorias=ServiceLocator.getCategoriasService().listarCategoria();
			for (int i = 0; i < categorias.length; i++) {
				System.out.println(categorias[i]+"; ");
			}

		} catch (Exception e) {
			System.err.println("Ha ocurrido un error:\n" +e.getMessage());
		}
	}

	private static void crearCategoria() {
		CategoriaItem categoria=new CategoriaItem();
		categoria.setDescripcion("Entremeses");
		try{
			ServiceLocator.getCategoriasService().crearCategoria(categoria);
		}catch(ServiceException e){
			System.out.println("Ha ocurrido un error: "+e.getMessage());
		}
	}

}

