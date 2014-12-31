package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dad.recetapp.db.DataBase;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.receta.RecetaItem;
import dad.recetapp.services.receta.RecetasService;

public class RecetasServiceDB implements RecetasService {

	@Override
	public void crearReceta(RecetaItem receta) throws ServiceException {
		if (receta==null)throw new IllegalArgumentException("Debe especificar una receta para crearla");
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement(
					"insert into recetas (nombre, fecha_creacion, cantidad, para, tiempo_total,tiempo_thermomix, id_categoria)"
							+"values(?,?,?,?,?,?,?)"
					);
			stmt.setString(1, receta.getNombre());
			stmt.setDate(2, new java.sql.Date(receta.getFechaCreacion().getTime()));
			stmt.setInt(3, receta.getCantidad());
			stmt.setString(4, receta.getPara());
			stmt.setInt(5, receta.getTiempoTotal());
			stmt.setInt(6, receta.getTiempoThermomix());
			stmt.setInt(7, receta.getIdCategoria());
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			throw new ServiceException("No se ha podido crear la receta "+ e);
		}

	}


	@Override
	public void modificarReceta(RecetaItem receta) throws ServiceException {
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("update recetas "
					+ "set nombre =?, fecha_creacion=?, cantidad=?, para=?, tiempo_total=?,tiempo_thermomix=?, id_categoria=? where id=?");
			stmt.setString(1, receta.getNombre());
			stmt.setDate(2, new java.sql.Date(receta.getFechaCreacion().getTime()));
			stmt.setInt(3, receta.getCantidad());
			stmt.setString(4, receta.getPara());
			stmt.setInt(5, receta.getTiempoTotal());
			stmt.setInt(6, receta.getTiempoThermomix());
			stmt.setInt(7, receta.getIdCategoria());
			stmt.setLong(8, receta.getId());
			if (stmt.executeUpdate()==0) {
				throw new ServiceException("La receta con id "+receta.getId()+" no existe en la base de datos");
			}
			stmt.close();
		} catch (SQLException e) {
			throw new ServiceException("No se ha podido modificar la receta "+ e);
		}

	}


	@Override
	public void eliminarReceta(Long id) throws ServiceException {
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("delete from recetas where id=?");
			stmt.setLong(1,id);
			if (stmt.executeUpdate()==0) {
				throw new ServiceException("La receta con id "+id+" no existe en la base de datos");
			}
			stmt.close();
		} catch (SQLException e) {
			throw new ServiceException("No se ha podido eliminar la receta "+ e);
		}

	}

	@Override
	public List<RecetaItem> buscarRecetas(String nombre, Integer tiempoTotal,
			Long idCategoria) throws ServiceException {
		List<RecetaItem>recetas=new ArrayList<RecetaItem>();
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("select * from recetas where nombre like ? or tiempo_total =? or id_categoria =?");
			stmt.setString(1,nombre);
			stmt.setInt(2, tiempoTotal);
			stmt.setLong(3, idCategoria);
			ResultSet rs =stmt.executeQuery();
			while(rs.next()){
				RecetaItem receta=new RecetaItem();
				receta.setId(rs.getLong("id"));
				receta.setNombre(rs.getString("nombre"));
				receta.setFechaCreacion(rs.getDate("fecha_creacion"));
				receta.setCantidad(rs.getInt("cantidad"));
				receta.setPara(rs.getString("para"));
				receta.setTiempoTotal(rs.getInt("tiempo_total"));
				receta.setTiempoThermomix(rs.getInt("tiempo_thermomix"));
				receta.setIdCategoria(rs.getInt("id_categoria"));
				recetas.add(receta);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recetas;
	}

	@Override
	public List<RecetaItem> listarRecetas() throws ServiceException {
		List<RecetaItem>recetas=new ArrayList<RecetaItem>();
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("select * from recetas");
			ResultSet rs =stmt.executeQuery();
			while(rs.next()){
				RecetaItem receta=new RecetaItem();
				receta.setId(rs.getLong("id"));
				receta.setNombre(rs.getString("nombre"));
				receta.setFechaCreacion(rs.getDate("fecha_creacion"));
				receta.setCantidad(rs.getInt("cantidad"));
				receta.setPara(rs.getString("para"));
				receta.setTiempoTotal(rs.getInt("tiempo_total"));
				receta.setTiempoThermomix(rs.getInt("tiempo_thermomix"));
				receta.setIdCategoria(rs.getInt("id_categoria"));
				recetas.add(receta);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recetas;
	}

	@Override
	public RecetaItem obtenerReceta(Long id) throws ServiceException {
		RecetaItem receta=new RecetaItem();
		try {
			Connection conn=DataBase.getConnection();
			PreparedStatement stmt=conn.prepareStatement("select * from recetas where id = ?");
			stmt.setLong(1,id);
			ResultSet rs =stmt.executeQuery();
			if (!rs.next()) {
				throw new ServiceException("No existe ninguna receta con ese id ");
			} else {
				rs.previous();
				while(rs.next()){
					receta.setId(rs.getLong("id"));
					receta.setNombre(rs.getString("nombre"));
					receta.setFechaCreacion(rs.getDate("fecha_creacion"));
					receta.setCantidad(rs.getInt("cantidad"));
					receta.setPara(rs.getString("para"));
					receta.setTiempoTotal(rs.getInt("tiempo_total"));
					receta.setTiempoThermomix(rs.getInt("tiempo_thermomix"));
					receta.setIdCategoria(rs.getInt("id_categoria"));
				}
			}
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return receta;
		}

	}
