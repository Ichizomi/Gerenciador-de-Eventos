package gerenciador.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

// Uma sala de evento.
public class EventRoom extends Room{

	public EventRoom(String name, int maxCapacity) {
		super(name, maxCapacity);
	}

	// Adiciona uma sala de eventos no banco. Salas de Evento tem roomType 1 no banco.
	public void SaveRoom(ConnectDB connection, EventRoom r) {
		String sql = "INSERT INTO Room (name, maxCapacity, roomType) VALUES ('" + r.getName() +"', '" + r.getMaxCapacity() + "', '" + "1" + "');";		
		try {
		    PreparedStatement statement = connection.connect().prepareStatement(sql);
			int status = statement.executeUpdate();
			if(status != 0) {
				System.out.println("Sala de eventos adicionada com sucesso.");
			}
		} catch (SQLException e) {
		    e.printStackTrace();
		} finally {
			connection.disconnect();
		}
	}
	
}
