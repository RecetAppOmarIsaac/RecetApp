package dad.recetapp.services.receta.seccion.ingrediente;

import dad.recetapp.services.ServiceException;

public interface TiposIngredientesService {
	public void crearTipoIngrediente(TipoIngredienteItem tipo) throws ServiceException;
	public void modificarTipoIngrediente(TipoIngredienteItem tipo) throws ServiceException;
	public void eliminarTipoIngrediente(Long id) throws ServiceException;
	public TipoIngredienteItem[] listarTipoIngredientes() throws ServiceException;
	public  TipoIngredienteItem obtenerTipoIngrediente(Long id) throws ServiceException;
}
