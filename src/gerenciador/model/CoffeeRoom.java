package gerenciador.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Um espaço de café
public class CoffeeRoom extends Room {

	// Métodos Construtores
	public CoffeeRoom(String name, int maxCapacity) {
		super(name, maxCapacity);
	}
	public CoffeeRoom(int id, String name, int maxCapacity) {
		super(id, name, maxCapacity);
	}
	public CoffeeRoom() {
		super();
	}

	// Adiciona uma sala de café no banco. Salas de café tem roomType 2 no banco.
	public void SaveRoom(ConnectDB connection, CoffeeRoom r) {
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
		
	// Retorna uma lista de todas as salas de café cadastradas no banco.
	public static List<CoffeeRoom> ListRoom(ConnectDB connection, boolean print) {
		
			List<CoffeeRoom> list = new ArrayList<>();
			String sql = "SELECT * FROM room WHERE roomType = 2;";	
			
			try {
			    PreparedStatement statement = connection.connect().prepareStatement(sql);
				ResultSet rs = statement.executeQuery(sql);
				if(print) System.out.println("[ID] Nome da Sala {Lotação}");
				while(rs.next()) {
					int idRoom = rs.getInt("idRoom");
					String name = rs.getString("name");
					int maxCapacity = rs.getInt("maxCapacity");					
					if(print) System.out.println("[" + idRoom + "] " + name + " {" + maxCapacity + "}");
					CoffeeRoom r = new CoffeeRoom(idRoom, name, maxCapacity);
					list.add(r);
				}
				if (list.isEmpty()){
					System.out.println("Nenhuma sala de café cadastrada no sistema...");
				}
				return list;
			} catch (SQLException e) {
			    e.printStackTrace();
			    return list;
			} finally {
				connection.disconnect();
			}
		}

	// Consultar salas de café no banco de dados (por nome).
	public static CoffeeRoom SearchRoomByName(ConnectDB connection, String _name) {
		CoffeeRoom r = new CoffeeRoom();
		String sql = "SELECT * FROM room WHERE name = '" + _name + "' AND roomType = 2;";			
		try {
		    PreparedStatement statement = connection.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int idRoom = rs.getInt("idRoom");
				String name = rs.getString("name");
				int maxCapacity = rs.getInt("maxCapacity");					
				r = new CoffeeRoom(idRoom, name, maxCapacity);
			}
			return r;
		} catch (SQLException e) {
		    e.printStackTrace();
		    return r;
		} finally {
			connection.disconnect();
		}
	}
	
	// Atualiza dados de uma sala de café no banco de dados (por ID)
	public static void UpdateRoom(ConnectDB connection, int id, CoffeeRoom updatedRoom) {
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
