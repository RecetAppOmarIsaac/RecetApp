package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dad.recetapp.db.DataBase;
import dad.recetapp.services.ITiposAnotacionesService;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.items.TipoAnotacionItem;
import dad.recetapp.utils.Logs;


public class TiposAnotacionesService implements ITiposAnotacionesService {

	

	@Override
	public Long crearTipoAnotacion(TipoAnotacionItem tipo)
			throws ServiceException {
		Long id=null;
		if (tipo==null)throw new IllegalArgumentException("Debe especificar un tipo de anotacion para crearlo");
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement(
					"insert into tipos_anotaciones (descripcion)"
							+"values(?)",Statement.RETURN_GENERATED_KEYS);
					
			stmt.setString(1, tipo.getDescripcion());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys(); 
            if (rs.next()) {
                id = rs.getLong(1);
            }
            rs.close();
			stmt.close();
		} catch (SQLException e) {
			throw new ServiceException("No se ha podido crear el tipo de anotacion"+ e);
		}
		return id;

	}

	@Override
	public void modificarTipoAnotacion(TipoAnotacionItem tipo)
			throws ServiceException {
		if (tipo == null) throw new IllegalArgumentException("Debe especificar un tipo de anotacion para modificarlo");
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("update tipos_anotaciones "
					+ "set descripcion =? where id=?");
			stmt.setString(1, tipo.getDescripcion());
			stmt.setLong(2, tipo.getId());
			if (stmt.executeUpdate()==0) {
				throw new ServiceException("El tipo de anotacion con id "+tipo.getId()+" no existe en la base de datos");
			}
			stmt.close();
		} catch (SQLException e) {
			throw new ServiceException("No se ha podido modificar el tipo de anotacion "+ e);
		}
	}

	@Override
	public void eliminarTipoAnotacion(Long id) throws ServiceException {
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("delete from tipos_anotaciones where id=?");
			stmt.setLong(1,id);
			if (stmt.executeUpdate()==0) {
				throw new ServiceException("El tipo de anotacion con id "+id+" no existe en la base de datos");
			}
			stmt.close();
		} catch (SQLException e) {
			throw new ServiceException("No se ha podido eliminar el tipo de anotacion "+ e);
		}

	}

	@Override
	public TipoAnotacionItem[] listarTiposAnotaciones() throws ServiceException {
		TipoAnotacionItem[] tipos=new TipoAnotacionItem[tamanyoListaTiposAnotaciones()];
		int i=0;
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("select * from tipos_anotaciones");
			ResultSet rs =stmt.executeQuery();
			while(rs.next()){
				TipoAnotacionItem tipo=new TipoAnotacionItem();
				tipo.setId(rs.getLong("id"));
				tipo.setDescripcion(rs.getString("descripcion"));
				tipos[i]=tipo;
				i++;

			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Logs.log(e);
		}
		return tipos;
	}


	private int tamanyoListaTiposAnotaciones() {
		int count = 0;
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("select count(*) from tipos_anotaciones");
			ResultSet rs =stmt.executeQuery();
			while(rs.next()){
				count = rs.getInt(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Logs.log(e);
		}
		return count;
	}

	@Override
	public TipoAnotacionItem obtenerTipoAnotacion(Long id)
			throws ServiceException {
		TipoAnotacionItem tipo=new TipoAnotacionItem();
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("select * from tipos_anotaciones where id = ?");
			stmt.setLong(1,id);
			ResultSet rs =stmt.executeQuery();
			if (!rs.next()) {
				throw new ServiceException("No existe ningun tipo de anotacion con ese id ");
			} else {
				rs.previous();
				while(rs.next()){
					tipo.setId(rs.getLong("id"));
					tipo.setDescripcion(rs.getString("descripcion"));
				}
			}
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				Logs.log(e);
			}
			return tipo;
		}

}
