package gerenciador.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomAllocation {

	// Vars
	private int idPerson;
	private int idRoom;
	private int eventPhase;
	
	// Métodos Construtores
	public RoomAllocation() {

	}	
	public RoomAllocation(int idPerson, int idRoom, int eventPhase) {
		this.idPerson = idPerson;
		this.idRoom = idRoom;
		this.eventPhase = eventPhase;
	}
	
	// Getters & Setters
	public int getIdPerson() {
		return idPerson;
	}
	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}
	public int getIdRoom() {
		return idRoom;
	}
	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}
	public int getEventPhase() {
		return eventPhase;
	}
	public void setEventPhase(int eventPhase) {
		this.eventPhase = eventPhase;
	}
		
	
	public void SaveRoomAllocation(ConnectDB connection, RoomAllocation a) {
		String sql = "INSERT INTO room_allocation (Person_idPerson, Room_idRoom, eventPhase) VALUES (" + a.getIdPerson() +", " + a.getIdRoom() + ", " + a.getEventPhase() + ");";		
		try {
		    PreparedStatement statement = connection.connect().prepareStatement(sql);
			statement.executeUpdate();
		} catch (SQLException e) {
		    e.printStackTrace();
		} finally {
			connection.disconnect();
		}
	}
	
	// Retorna uma lista de todas as salas cadastradas no banco.
	public static List<RoomAllocation> ListAllocation(ConnectDB connection, String whereClause) {
		List<RoomAllocation> list = new ArrayList<>();
		String sql = "SELECT * FROM room_allocation WHERE " + whereClause + ";";			
		try {
		    PreparedStatement statement = connection.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int idPerson = rs.getInt("Person_idPerson");
				int idRoom = rs.getInt("Room_idRoom");
				int eventPhase = rs.getInt("eventPhase");
				RoomAllocation a = new RoomAllocation(idPerson, idRoom, eventPhase);
				list.add(a);
			}
			if (list.isEmpty()){
				System.out.println("Nenhuma alocação no sistema...");
			}
			return list;
		} catch (SQLException e) {
		    e.printStackTrace();
		    return list;
		} finally {
			connection.disconnect();
		}
	}
	
	public static List<Integer> GetNumberOfAllocationsPerRoom(ConnectDB connection, int eventPhase) {
		List<Integer> list = new ArrayList<>();
		String sql = "SELECT Room_idRoom, COUNT(Room_idRoom) FROM room_allocation WHERE eventPhase = " + eventPhase + " GROUP BY Room_idRoom;";			
		try {
		    PreparedStatement statement = connection.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int roomID = rs.getInt("Room_idRoom");
				list.add(roomID);
				int count = rs.getInt("COUNT(Room_idRoom)");
				list.add(count);
			}
			if (list.isEmpty()){
				System.out.println("Nenhuma alocação no sistema...");
			}
			return list;
		} catch (SQLException e) {
		    e.printStackTrace();
		    return list;
		} finally {
			connection.disconnect();
		}
	}
	
}
