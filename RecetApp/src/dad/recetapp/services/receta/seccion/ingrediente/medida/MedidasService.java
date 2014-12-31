package dad.recetapp.services.receta.seccion.ingrediente.medida;

import dad.recetapp.services.ServiceException;


public interface MedidasService {
	public void  crearMedida (MedidaItem medida) throws ServiceException;
	public void modificarMedida (MedidaItem medida)throws ServiceException;
	public void eliminarMedida (Long id)throws ServiceException;
	public MedidaItem[] listarMedidas()throws ServiceException;
	public MedidaItem obtenerMedida(Long id)throws ServiceException;
	
}
