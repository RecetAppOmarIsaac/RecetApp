package dad.recetapp.db;

import dad.recetapp.utils.Logs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DataBase {
	private static final ResourceBundle CONFIG = ResourceBundle.getBundle(DataBase.class.getPackage().getName() + ".database");
	private static Connection connection = null;

	private DataBase() {
	}

	public static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			connection = connect();
		}
		return connection;
	}

	private static final void registerDriver() {
		try {
			Class.forName(CONFIG.getString("db.driver.classname"));
		} catch (ClassNotFoundException e) {
			System.err.println("Error al cargar el driver JDBC");
		}
	}

	/**
	 * Abre una conexi�n con la base de datos especificada en los par�metros.
	 *
	 * @param url      URL de conexi�n.
	 * @param username Nombre de usuario.
	 * @param password Contrase�a.
	 * @return Conexi�n abierta.
	 * @throws SQLException En caso de no poder abrir la conexi�n por alg�n motivo.
	 */
	public static Connection connect(String url, String username, String password) throws SQLException {
		registerDriver();
		return DriverManager.getConnection(url, username, password);
	}

	/**
	 * Abre una conexi�n con la base de datos usando los par�metros del fichero
	 * de propiedades "database.properties".
	 *
	 * @return Conexi�n abierta.
	 * @throws SQLException En caso de no poder abrir la conexi�n por alg�n motivo.
	 */
	public static Connection connect() throws SQLException {
		return connect(CONFIG.getString("db.url"), CONFIG.getString("db.username"), CONFIG.getString("db.password"));
	}

	/**
	 * Cierra la conexi�n.
	 *
	 * @param connection Conexi�n abierta que queremos cerrar.
	 * @throws SQLException En caso de que no pueda cerrar la conexi�n por alg�n
	 *                      motivo.
	 */
	public static void disconnect(Connection connection) throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}

	/**
	 * Cierra la conexi�n abierta previamente mediante "{@link #getConnection()}".
	 *
	 * @throws SQLException En caso de que no pueda cerrar la conexi�n por alg�n
	 *                      motivo.
	 */
	public static void disconnect() throws SQLException {
		disconnect(connection);
		connection = null;
	}

	/**
	 * Comprueba la conexi�n con la base de datos.
	 *
	 * @return Devuelve "true" si abre y cierra la conexi�n sin errores. "false"
	 * en caso contrario.
	 */
	public static Boolean test() {
		boolean testOk = false;
		try {
			Connection c = DataBase.connect();
			DataBase.disconnect(c);
			testOk = true;
		} catch (SQLException e) {
			Logs.log(e);
		}
		return testOk;
	}

}