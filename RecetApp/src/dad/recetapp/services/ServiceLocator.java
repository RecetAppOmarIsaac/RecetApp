package dad.recetapp.services;

import dad.recetapp.services.anotaciones.TiposAnotacionesService;
import dad.recetapp.services.categorias.CategoriasService;
import dad.recetapp.services.impl.CategoriasServiceDB;
import dad.recetapp.services.impl.MedidasServiceDB;
import dad.recetapp.services.impl.RecetasServiceDB;
import dad.recetapp.services.impl.TiposAnotacionesServiceDB;
import dad.recetapp.services.impl.TiposIngredientesServiceDB;
import dad.recetapp.services.receta.RecetasService;
import dad.recetapp.services.receta.seccion.ingrediente.TiposIngredientesService;
import dad.recetapp.services.receta.seccion.ingrediente.medida.MedidasService;


public class ServiceLocator {
	private static RecetasService recetasService=null;
	private static MedidasService medidasService=null;
	private static TiposIngredientesService tiposIngredientesService=null;
	private static CategoriasService categoriasService=null;
	private static TiposAnotacionesService tiposAnotacionesService=null;
	
	public static RecetasService getRecetasService(){
		if(recetasService ==null){
			recetasService=new RecetasServiceDB();
			
		}
		return recetasService;
	}
	
	public static MedidasService getMedidasService(){
		if(medidasService ==null){
			medidasService=new MedidasServiceDB();
			
		}
		return medidasService;
	}
	public static TiposIngredientesService getTipoIngredienteService(){
		if(tiposIngredientesService ==null){
			tiposIngredientesService=new TiposIngredientesServiceDB();
			
		}
		return tiposIngredientesService;
	}

	public static CategoriasService getCategoriasService(){
		if (categoriasService==null) {
			categoriasService=new CategoriasServiceDB();
			
		}
		return categoriasService;
	}
	public static TiposAnotacionesService getTiposAnotacionesService(){
		if (tiposAnotacionesService==null) {
			tiposAnotacionesService=new TiposAnotacionesServiceDB(); 
			
		}
		return tiposAnotacionesService;
	}
}
