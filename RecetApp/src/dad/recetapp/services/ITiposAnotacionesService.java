package dad.recetapp.services;

import dad.recetapp.services.items.TipoAnotacionItem;

public interface ITiposAnotacionesService {
	public Long crearTipoAnotacion(TipoAnotacionItem tipo)throws ServiceException;
	public void modificarTipoAnotacion(TipoAnotacionItem tipo)throws ServiceException;
	public void eliminarTipoAnotacion(Long id)throws ServiceException;
	public TipoAnotacionItem[] listarTiposAnotaciones()throws ServiceException;
	public TipoAnotacionItem obtenerTipoAnotacion(Long id)throws ServiceException;

}
