package dad.recetapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DataBase {
	private static final ResourceBundle CONFIG = 
			ResourceBundle.getBundle(DataBase.class.getPackage().getName()+".database");
	
	private static Connection connection=null;
	
	private DataBase(){}
	
	public static Connection getConnection() throws SQLException{
		if (connection==null || connection.isClosed()){
			connection=connect();
		}
		return connection;
	}
	
	private static final void registerDriver(){
		try {
			Class.forName(CONFIG.getString("db.driver.classname"));
		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar el driver JDBC");
		}
		
	}
	private static Connection connect(String url,String username, String password) throws SQLException{
		registerDriver();
		return DriverManager.getConnection(url,username,password);
	}
	public static Connection connect() throws SQLException{
		return connect(CONFIG.getString("db.url"),CONFIG.getString("db.username"),CONFIG.getString("db.password"));
		
	}
	public static void disconnected(Connection connection) throws SQLException{
		if(connection!=null && !connection.isClosed()){
			connection.close();
		}
	}
	public static void disconnected() throws SQLException{
		disconnected(connection);
		connection=null;
	}
}
