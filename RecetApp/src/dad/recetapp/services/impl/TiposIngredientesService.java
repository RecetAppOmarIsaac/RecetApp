package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dad.recetapp.db.DataBase;
import dad.recetapp.services.ITiposIngredientesService;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.items.TipoIngredienteItem;
import dad.recetapp.utils.Logs;


public class TiposIngredientesService implements ITiposIngredientesService {

	@Override
	public Long crearTipoIngrediente(TipoIngredienteItem tipo) throws ServiceException {
		Long id=null;
		if (tipo==null)throw new IllegalArgumentException("Debe especificar una tipo de ingrediente para crearlo");
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement(
					"insert into tipos_ingredientes (nombre) values(?)",Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, tipo.getNombre());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys(); 
			if (rs.next()) {
                id = rs.getLong(1);
            }
            rs.close();
			stmt.close();
		} catch (SQLException e) {
			throw new ServiceException("No se ha podido crear la medida "+ e);
		}
		
		return id;

	}

	@Override
	public void modificarTipoIngrediente(TipoIngredienteItem tipo) throws ServiceException {
		if (tipo == null)
			throw new IllegalArgumentException("Debe especificar una tipo de ingrediente para modificarlo");
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("update tipos_ingredientes "
					+ "set nombre =? where id=?");
			stmt.setString(1, tipo.getNombre());
			stmt.setLong(2, tipo.getId());
			if (stmt.executeUpdate()==0) {
				throw new ServiceException("El tipo ingrediente con id "+tipo.getId()+" no existe en la base de datos");
			}
			stmt.close();
		} catch (SQLException e) {
			throw new ServiceException("No se ha podido modificar el tipo de ingrediente "+ e);
		}

	}

	@Override
	public void eliminarTipoIngrediente(Long id) throws ServiceException {
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("delete from tipos_ingredientes where id=?");
			stmt.setLong(1,id);
			if (stmt.executeUpdate()==0) {
				throw new ServiceException("El tipo ingrediente con id "+id+" no existe en la base de datos");
			}
			stmt.close();
		} catch (SQLException e) {
			throw new ServiceException("No se ha podido eliminar el tipo de ingrediente "+ e);
		}

	}


	@Override
	public TipoIngredienteItem[] listarTipoIngredientes() throws ServiceException {
		TipoIngredienteItem[] tiposIngredientes=new TipoIngredienteItem[tamanyoListaTiposIngredientes()];
		int i=0;
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("select * from tipos_ingredientes");
			ResultSet rs =stmt.executeQuery();
			while(rs.next()){
				TipoIngredienteItem tipo=new TipoIngredienteItem();
				tipo.setId(rs.getLong("id"));
				tipo.setNombre(rs.getString("nombre"));
				tiposIngredientes[i]=tipo;
				i++;

			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Logs.log(e);
		}
		return tiposIngredientes;
	}
	private int tamanyoListaTiposIngredientes() {
		int count = 0;
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("select count(*) from tipos_ingredientes");
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
	public TipoIngredienteItem obtenerTipoIngrediente(Long id) throws ServiceException {
		TipoIngredienteItem tipo=new TipoIngredienteItem();
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("select * from tipos_ingredientes where id = ?");
			stmt.setLong(1,id);
			ResultSet rs =stmt.executeQuery();
			if (!rs.next()) {
				throw new ServiceException("No existe ningu tipo ingrediente con ese id ");
			} else {
				rs.previous();
				while(rs.next()){
					tipo.setId(rs.getLong("id"));
					tipo.setNombre(rs.getString("nombre"));
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

