package dad.recetapp.services;

import dad.recetapp.services.items.TipoIngredienteItem;

public interface ITiposIngredientesService {
	public Long crearTipoIngrediente(TipoIngredienteItem tipo) throws ServiceException;
	public void modificarTipoIngrediente(TipoIngredienteItem tipo) throws ServiceException;
	public void eliminarTipoIngrediente(Long id) throws ServiceException;
	public TipoIngredienteItem[] listarTipoIngredientes() throws ServiceException;
	public  TipoIngredienteItem obtenerTipoIngrediente(Long id) throws ServiceException;
}
