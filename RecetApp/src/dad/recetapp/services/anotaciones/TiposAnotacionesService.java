package dad.recetapp.services.anotaciones;

import dad.recetapp.services.ServiceException;

public interface TiposAnotacionesService {
	public void crearTipoAnotacion(TipoAnotacionItem tipo)throws ServiceException;
	public void modificarTipoAnotacion(TipoAnotacionItem tipo)throws ServiceException;
	public void eliminarTipoAnotacion(Long id)throws ServiceException;
	public TipoAnotacionItem[] listarTiposAnotaciones()throws ServiceException;
	public TipoAnotacionItem obtenerTipoAnotacion(Long id)throws ServiceException;

}
