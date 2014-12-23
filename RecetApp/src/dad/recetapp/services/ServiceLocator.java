package dad.recetapp.services;

import dad.recetapp.services.impl.RecetasServiceDB;


public class ServiceLocator {
	private static RecetasService recetasService=null;
	
	public static RecetasService getRecetasService(){
		if(recetasService ==null){
			recetasService=new RecetasServiceDB();
			
		}
		return recetasService;
	}

}
