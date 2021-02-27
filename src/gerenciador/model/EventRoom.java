package gerenciador.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Uma sala de evento.
public class EventRoom extends Room{

	// Métodos Construtores.
	public EventRoom(String name, int maxCapacity) {
		super(name, maxCapacity);
	}
	public EventRoom(int id, String name, int maxCapacity) {
		super(id, name, maxCapacity);
	}
	public EventRoom() {
		super();
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
	
	// Retorna uma lista de todas as salas de eventos cadastradas no banco.
	public static List<EventRoom> ListRoom(ConnectDB connection, boolean print) {
		
			List<EventRoom> list = new ArrayList<>();
			String sql = "SELECT * FROM room WHERE roomType = 1;";	
			
			try {
			    PreparedStatement statement = connection.connect().prepareStatement(sql);
				ResultSet rs = statement.executeQuery(sql);
				if(print) System.out.println("[ID] Nome da Sala {Lotação}");
				while(rs.next()) {
					int idRoom = rs.getInt("idRoom");
					String name = rs.getString("name");
					int maxCapacity = rs.getInt("maxCapacity");					
					if(print) System.out.println("[" + idRoom + "] " + name + " {" + maxCapacity + "}");
					EventRoom r = new EventRoom(idRoom, name, maxCapacity);
					list.add(r);
				}
				if (list.isEmpty()){
					System.out.println("Nenhuma sala de evento cadastrada no sistema...");
				}
				return list;
			} catch (SQLException e) {
			    e.printStackTrace();
			    return list;
			} finally {
				connection.disconnect();
			}
		}

	// Consultar salas de evento no banco de dados (por nome).
	public static EventRoom SearchRoomByName(ConnectDB connection, String _name) {
		EventRoom r = new EventRoom();
		String sql = "SELECT * FROM room WHERE name = '" + _name + "' AND roomType = 1;";			
		try {
		    PreparedStatement statement = connection.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int idRoom = rs.getInt("idRoom");
				String name = rs.getString("name");
				int maxCapacity = rs.getInt("maxCapacity");					
				r = new EventRoom(idRoom, name, maxCapacity);
			}
			return r;
		} catch (SQLException e) {
		    e.printStackTrace();
		    return r;
		} finally {
			connection.disconnect();
		}
	}
	
	// Atualiza dados de uma sala de evento no banco de dados (por ID)
	public static void UpdateRoom(ConnectDB connection, int id, EventRoom updatedRoom) {
		String sql = "UPDATE room SET name = '" + updatedRoom.getName() + "', maxCapacity = " + updatedRoom.getMaxCapacity() + " WHERE idRoom = " + id + ";";			
		try {
		    PreparedStatement statement = connection.connect().prepareStatement(sql);
			int status = statement.executeUpdate();
			if(status != 0) {
				System.out.println("Dados atualizados.");
			}
		} catch (SQLException e) {
		    e.printStackTrace();
		} finally {
			connection.disconnect();
		}
	}
	
}
