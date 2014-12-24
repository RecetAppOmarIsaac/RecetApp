package dad.recetapp.services.recetas;

import java.util.List;

import dad.recetapp.services.ServiceException;

public interface RecetasService {

	public void crearReceta(RecetaItem receta) throws ServiceException;
	public void modificarReceta(RecetaItem receta)throws ServiceException;
	public void eliminarReceta(Long id)throws ServiceException;
	public List<RecetaItem> buscarRecetas(String nombre, Integer tiempoTotal, Long idCategoria)throws ServiceException;
	public List<RecetaItem> listarRecetas()throws ServiceException;
	public RecetaItem obtenerReceta(Long id) throws ServiceException; 
}