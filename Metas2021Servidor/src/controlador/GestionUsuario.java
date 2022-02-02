package controlador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

import modelo.Usuario;

public class GestionUsuario {
	public static boolean existeUsuario(Usuario user) {
		boolean resultado = false;
		PreparedStatement sentencia = null;
		ConexionBD conexionBD = new ConexionBD();
		Connection conexion = conexionBD.abrirConexion();
		String sql = "SELECT * FROM usuario WHERE username like '" + user.getUsername() + "'";
		try {
			sentencia = conexion.prepareStatement(sql);
			resultado = sentencia.executeQuery().first();
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

	public static Usuario obtenerUsuario(Usuario user) {
		Usuario resultado = null;
		PreparedStatement sentencia = null;
		ResultSet rset = null;
		ConexionBD conexionBD = new ConexionBD();
		Connection conexion = conexionBD.abrirConexion();
		String sql = "SELECT * FROM usuario WHERE username like '" + user.getUsername() + "' and password like '"
				+ user.getPassword() + "';";
		try {
			sentencia = conexion.prepareStatement(sql);
			rset = sentencia.executeQuery();
			if (rset.first()) {
				String usuario = rset.getString("username");
				String pass = rset.getString("password");
				String mail = rset.getString("e-mail");
				Boolean admin = rset.getBoolean("Admin");
				resultado = new Usuario(usuario, mail, pass, admin);
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

	public static ArrayList<Usuario> obtenerListaUsuarios() {
		ArrayList<Usuario> resultado = new ArrayList<Usuario>();
		PreparedStatement sentencia = null;
		ResultSet rset = null;
		ConexionBD conexionBD = new ConexionBD();
		Connection conexion = conexionBD.abrirConexion();
		String sql = "SELECT * FROM usuario;";
		try {
			sentencia = conexion.prepareStatement(sql);
			rset = sentencia.executeQuery();
			while (rset.next()) {
				String usuario = rset.getString("username");
				String pass = rset.getString("password");
				String mail = rset.getString("e-mail");
				Boolean admin = rset.getBoolean("Admin");
				resultado.add(new Usuario(usuario, mail, pass, admin));
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

	public static boolean nuevoUsuario(Usuario user) {
		boolean resultado = false;
		PreparedStatement sentencia = null;
		ConexionBD conexionBD = new ConexionBD();
		Connection conexion = conexionBD.abrirConexion();
		String sql = "INSERT INTO `usuario` (`username`, `e-mail`, `password`, `Admin`) VALUES (?, ?, ?, ?);";
		try {
			sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, user.getUsername());
			sentencia.setString(2, user.getEmail());
			sentencia.setString(3, user.getPassword());
			sentencia.setBoolean(4, user.isAdmin());
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

	public static boolean eliminarUsuario(Usuario user) {
		boolean resultado = false;
		PreparedStatement sentencia = null;
		ConexionBD conexionBD = new ConexionBD();
		Connection conexion = conexionBD.abrirConexion();
		String sql = "DELETE FROM usuario WHERE username like '" + user.getUsername() + "'";
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
