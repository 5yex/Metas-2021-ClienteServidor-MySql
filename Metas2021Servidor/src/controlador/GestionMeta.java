package controlador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

import modelo.Meta;
import modelo.Usuario;

public class GestionMeta {

	public static boolean nuevaMeta(Meta objetivo) {
		boolean resultado = false;
		PreparedStatement sentencia = null;
		ConexionBD conexionBD = new ConexionBD();
		Connection conexion = conexionBD.abrirConexion();
		String sql = "INSERT INTO `meta` (`titulo`, `descripcion`, `prioridad`, `fechalimite`, `categoria`, `username`) VALUES (?,?,?,?,?,?);";
		try {
			sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, objetivo.getTitulo());
			sentencia.setString(2, objetivo.getDescripcion());
			sentencia.setString(3, objetivo.getPrioridad());
			sentencia.setDate(4, java.sql.Date.valueOf(objetivo.getFechalimite()));
			sentencia.setString(5, objetivo.getCategoria());
			sentencia.setString(6, objetivo.getUser().getUsername());
			resultado = (sentencia.executeUpdate() > 0);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sentencia.close();
				conexionBD.cerrarConexion();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultado;
	}

	// Tiene encuenta que la meta sea del usuario conectado, para no afectar a metas
	// del resto de usuarios accidentalmente
	public static Meta obtenerMeta(Meta meta, Usuario user) {
		Meta resultado = null;
		PreparedStatement sentencia = null;
		ResultSet rset = null;
		ConexionBD conexionBD = new ConexionBD();
		Connection conexion = conexionBD.abrirConexion();
		String sql = "SELECT * FROM meta where username like '" + user.getUsername() + "' and idMeta = "
				+ meta.getIdMeta();
		try {
			sentencia = conexion.prepareStatement(sql);
			rset = sentencia.executeQuery();
			if (rset.first()) {
				int id = rset.getInt("idMeta");
				String titulo = rset.getString("titulo");
				String descripcion = rset.getString("descripcion");
				String prioridad = rset.getString("prioridad");
				LocalDate fechalimite = LocalDate.parse(rset.getString("fechalimite"));
				String categoria = rset.getString("categoria");
				resultado = new Meta(id, titulo, descripcion, prioridad, fechalimite, categoria, user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sentencia.close();
				conexionBD.cerrarConexion();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultado;
	}

	// En este metodo no se tiene en cuenta el usuario, por que para llegar a
	// ejecutarlo, previamente se hace ObtenerMeta, que ya filtra que la meta sea
	// tuya
	public static boolean editarMeta(Meta objetivo) {
		boolean resultado = false;
		PreparedStatement sentencia = null;
		ConexionBD conexionBD = new ConexionBD();
		Connection conexion = conexionBD.abrirConexion();
		String sql = "UPDATE `meta` SET `titulo` = ?, `descripcion` = ?, `prioridad` = ?, `fechalimite` = ?, `categoria` = ?, `username` = ? WHERE `meta`.`idMeta` = ? ";
		try {
			sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, objetivo.getTitulo());
			sentencia.setString(2, objetivo.getDescripcion());
			sentencia.setString(3, objetivo.getPrioridad());
			sentencia.setDate(4, java.sql.Date.valueOf(objetivo.getFechalimite()));
			sentencia.setString(5, objetivo.getCategoria());
			sentencia.setString(6, objetivo.getUser().getUsername());
			sentencia.setInt(7, objetivo.getIdMeta());
			resultado = (sentencia.executeUpdate() > 0);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sentencia.close();
				conexionBD.cerrarConexion();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultado;
	}

	// Solo obtiene metas del usuario
	public static ArrayList<Meta> obtenerMisMetas(Usuario user) {
		ArrayList<Meta> resultado = new ArrayList<Meta>();
		PreparedStatement sentencia = null;
		ResultSet rset = null;
		ConexionBD conexionBD = new ConexionBD();
		Connection conexion = conexionBD.abrirConexion();
		String sql = "SELECT * FROM `meta` where username like '" + user.getUsername() + "'";
		try {
			sentencia = conexion.prepareStatement(sql);
			rset = sentencia.executeQuery();
			while (rset.next()) {
				int id = rset.getInt("idMeta");
				String titulo = rset.getString("titulo");
				String descripcion = rset.getString("descripcion");
				String prioridad = rset.getString("prioridad");
				LocalDate fechalimite = LocalDate.parse(rset.getString("fechalimite"));
				String categoria = rset.getString("categoria");
				resultado.add(new Meta(id, titulo, descripcion, prioridad, fechalimite, categoria, user));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sentencia.close();
				conexionBD.cerrarConexion();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultado;
	}

//Obtener todas las metas
	public static ArrayList<Meta> obtenerTodasLasMetas() {
		ArrayList<Meta> resultado = new ArrayList<Meta>();
		PreparedStatement sentencia = null;
		ResultSet rset = null;
		ConexionBD conexionBD = new ConexionBD();
		Connection conexion = conexionBD.abrirConexion();
		String sql = "SELECT * FROM `meta`";
		try {
			sentencia = conexion.prepareStatement(sql);
			rset = sentencia.executeQuery();
			while (rset.next()) {
				int id = rset.getInt("idMeta");
				String titulo = rset.getString("titulo");
				String descripcion = rset.getString("descripcion");
				String prioridad = rset.getString("prioridad");
				LocalDate fechalimite = LocalDate.parse(rset.getString("fechalimite"));
				String categoria = rset.getString("categoria");
				Usuario usuario = new Usuario(rset.getString("username"));
				resultado.add(new Meta(id, titulo, descripcion, prioridad, fechalimite, categoria, usuario));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sentencia.close();
				conexionBD.cerrarConexion();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultado;
	}
	
	
	//borra solo metas del usuario
	public static boolean eliminarMeta(Meta meta) {
		boolean resultado = false;
		PreparedStatement sentencia = null;
		ConexionBD conexionBD = new ConexionBD();
		Connection conexion = conexionBD.abrirConexion();
		String sql = "DELETE FROM `meta` WHERE `meta`.`idMeta` = " + meta.getIdMeta() + " and username like '"
				+ meta.getUser().getUsername() + "'";
		try {
			sentencia = conexion.prepareStatement(sql);
			resultado = (sentencia.executeUpdate() > 0);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sentencia.close();
				conexionBD.cerrarConexion();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultado;
	}
}
