package dad.recetapp.services;

import dad.recetapp.services.impl.MedidasServiceDB;
import dad.recetapp.services.impl.RecetasServiceDB;
import dad.recetapp.services.medidas.MedidasService;
import dad.recetapp.services.recetas.RecetasService;


public class ServiceLocator {
	private static RecetasService recetasService=null;
	private static MedidasService medidasService=null;
	
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

}
