package gerenciador.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Classe responsável pela coneção com o banco de dados MySQL.
public class ConnectDB {

	private static Connection connection = null; // Conexão
	// Configurações do banco de dados do MySQL Server
	private static String databaseDriver = "com.mysql.cj.jdbc.Driver";
	private static String databaseName = "eventmanagerdb";
	private static String databaseUrl = "jdbc:mysql://localhost:3306/" + databaseName;	
	private static String username = "root";
	private static String password = "tuturu";
		
	// Connect Database
	public Connection connect() {
	    if (connection == null) {
	        try {
	        	Class.forName(databaseDriver);
                connection = DriverManager.getConnection(databaseUrl, username, password);
	        } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
	}
	
	// Disconnect database
	public void disconnect() {
	    if (connection != null) {
	        try {
	            connection.close();
	            connection = null;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	// Delete database
	public void ClearDatabase(ConnectDB connection) {
		String sql1 = "DELETE FROM person;";	
		String sql2 = "DELETE FROM room;";
		String sql3 = "DELETE FROM room_allocation;";
		try {
			int status = 0;
		    PreparedStatement statement = connection.connect().prepareStatement(sql1);			
			status = statement.executeUpdate();
			statement = connection.connect().prepareStatement(sql2);			
			status = statement.executeUpdate();
			statement = connection.connect().prepareStatement(sql3);			
			status = statement.executeUpdate();
			if(status != 0) {
				System.out.println("Database deletado.");
			}
		} catch (SQLException e) {
		    e.printStackTrace();
		} finally {
			connection.disconnect();
		}
	}

}
