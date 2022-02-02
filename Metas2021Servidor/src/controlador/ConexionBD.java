package controlador;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD implements Configuracion {
	Connection conexion;

	public Connection abrirConexion() {
		try {
			conexion = (Connection) DriverManager.getConnection("jdbc:mysql://" + DBHOST + ":3306/" + DBNAME, DBUSER,
					null);
		} catch (SQLException e) {

			e.printStackTrace();
			// Utils.escribirLog(Utils.FICHERO_LOG, e.getMessage());
		}
		return conexion;
	}

	public boolean cerrarConexion() {
		// TODO
		return true;
	}

}
