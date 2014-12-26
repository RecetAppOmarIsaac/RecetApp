package dad.recetapp.services.categorias;

import dad.recetapp.services.ServiceException;

public interface CategoriasService {
	public void crearCategoria(CategoriaItem categoria)throws ServiceException;
	public void modificarCategoria(CategoriaItem categoria)throws ServiceException;
	public void eliminarCategoria(Long id)throws ServiceException;
	public CategoriaItem[] listarCategoria()throws ServiceException;
	public CategoriaItem obtenerCategoria(Long id)throws ServiceException;
	
}
