package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dad.recetapp.db.DataBase;
import dad.recetapp.services.ICategoriasService;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.items.CategoriaItem;


public class CategoriasService implements ICategoriasService {

	@Override
	public void crearCategoria(CategoriaItem categoria) throws ServiceException {
		if (categoria==null)throw new IllegalArgumentException("Debe especificar una categoría  para crearla");
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement(
					"insert into categorias (descripcion)"
							+"values(?)"
					);
			stmt.setString(1, categoria.getDescripcion());
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			throw new ServiceException("No se ha podido crear la categoría "+ e);
		}

	}

	@Override
	public void modificarCategoria(CategoriaItem categoria)throws ServiceException {
		if (categoria==null)throw new IllegalArgumentException("Debe especificar una categoría  para modificarla");
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("update categorias "
					+ "set descripcion =? where id=?");
			stmt.setString(1, categoria.getDescripcion());
			stmt.setLong(2, categoria.getId());
			if (stmt.executeUpdate()==0) {
				throw new ServiceException("La categoría con id "+categoria.getId()+" no existe en la base de datos");
			}
			stmt.close();
		} catch (SQLException e) {
			throw new ServiceException("No se ha podido modificar la categoría "+ e);
		}
	}


	@Override
	public void eliminarCategoria(Long id) throws ServiceException {
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("delete from categorias where id=?");
			stmt.setLong(1,id);
			if (stmt.executeUpdate()==0) {
				throw new ServiceException("La categoría con id "+id+" no existe en la base de datos");
			}
			stmt.close();
		} catch (SQLException e) {
			throw new ServiceException("No se ha podido eliminar la categoría "+ e);
		}

	}

	@Override
	public CategoriaItem[] listarCategoria() throws ServiceException {
		CategoriaItem[] categorias=new CategoriaItem[tamanyoListaCategorias()];
		int i=0;
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("select * from categorias");
			ResultSet rs =stmt.executeQuery();
			while(rs.next()){
				CategoriaItem categoria=new CategoriaItem();
				categoria.setId(rs.getLong("id"));
				categoria.setDescripcion(rs.getString("descripcion"));
				categorias[i]=categoria;
				i++;

			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categorias;
	}

	private int tamanyoListaCategorias() {
		int count = 0;
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("select count(*) from categorias");
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
	public CategoriaItem obtenerCategoria(Long id) throws ServiceException {
		CategoriaItem tipo=new CategoriaItem();
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("select * from categorias where id = ?");
			stmt.setLong(1,id);
			ResultSet rs =stmt.executeQuery();
			if (!rs.next()) {
				throw new ServiceException("No existe ninguna categoría con ese id ");
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
				e.printStackTrace();
			}
			return tipo;
		}

}

