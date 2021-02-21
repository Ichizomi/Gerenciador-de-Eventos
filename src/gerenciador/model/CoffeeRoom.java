package gerenciador.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

// Um espaço de café
public class CoffeeRoom extends Room {

	public CoffeeRoom(String name, int maxCapacity) {
		super(name, maxCapacity);
	}

	// Adiciona uma sala de café no banco. Salas de café tem roomType 2 no banco.
	public void SaveRoom(ConnectDB connection, EventRoom r) {
		String sql = "INSERT INTO Room (name, maxCapacity, roomType) VALUES ('" + r.getName() +"', '" + r.getMaxCapacity() + "', '" + "2" + "');";		
		try {
		    PreparedStatement statement = connection.connect().prepareStatement(sql);
			int status = statement.executeUpdate();
			if(status != 0) {
				System.out.println("Sala de café adicionada com sucesso.");
			}
		} catch (SQLException e) {
		    e.printStackTrace();
		} finally {
			connection.disconnect();
		}
	}
	
}
