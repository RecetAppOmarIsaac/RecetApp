package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import dad.recetapp.db.DataBase;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.medidas.MedidaItem;
import dad.recetapp.services.medidas.MedidasService;

public class MedidasServiceDB implements MedidasService {

	@Override
	public void crearMedida(MedidaItem medida) throws ServiceException {
		if (medida==null)throw new IllegalArgumentException("Debe especificar una medida para crearla");
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement(
					"insert into medidas (nombre, abreviatura)"
							+"values(?,?)"
					);
			stmt.setString(1, medida.getNombre());
			stmt.setString(2, medida.getAbreviatura());
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			throw new ServiceException("No se ha podido crear la medida "+ e);
		}

	}

	@Override
	public void modificarMedida(MedidaItem medida) throws ServiceException {
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("update medidas "
					+ "set nombre =?, abreviatura=? where id=?");
			stmt.setString(1, medida.getNombre());
			stmt.setString(2, medida.getAbreviatura());
			stmt.setLong(3, medida.getId());
			if (stmt.executeUpdate()==0) {
				throw new ServiceException("La medida con id "+medida.getId()+" no existe en la base de datos");
			}
			stmt.close();
		} catch (SQLException e) {
			throw new ServiceException("No se ha podido modificar la medida "+ e);
		}

	}

	@Override
	public void eliminarMedida(Long id) throws ServiceException {
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("delete from medidas where id=?");
			stmt.setLong(1,id);
			if (stmt.executeUpdate()==0) {
				throw new ServiceException("La medida con id "+id+" no existe en la base de datos");
			}
			stmt.close();
		} catch (SQLException e) {
			throw new ServiceException("No se ha podido eliminar la medida "+ e);
		}

	}

	@Override
	public MedidaItem[] listarMedidas() throws ServiceException {
		MedidaItem[] medidas=new MedidaItem[tamanyoListaMedidas()];
		int i=0;
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("select * from medidas");
			ResultSet rs =stmt.executeQuery();
			while(rs.next()){
				MedidaItem medida=new MedidaItem();
				medida.setId(rs.getLong("id"));
				medida.setNombre(rs.getString("nombre"));
				medida.setAbreviatura(rs.getString("abreviatura"));
				medidas[i]=medida;
				i++;

			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return medidas;
	}

	private int tamanyoListaMedidas() {
		int count = 0;
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("select count(*) from medidas");
			ResultSet rs =stmt.executeQuery();
			while(rs.next()){
				count = rs.getInt(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public MedidaItem obtenerMedida(Long id) throws ServiceException {
		MedidaItem medida=new MedidaItem();
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("select * from medidas where id = ?");
			stmt.setLong(1,id);
			ResultSet rs =stmt.executeQuery();
			if (!rs.next()) {
				throw new ServiceException("No existe ninguna medida con ese id ");
			} else {
				rs.previous();
				while(rs.next()){
					medida.setId(rs.getLong("id"));
					medida.setNombre(rs.getString("nombre"));
					medida.setAbreviatura(rs.getString("abreviatura"));	
				}
			}
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return medida;
		}

}
