package gerenciador.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

// Uma sala.
public abstract class Room {
	
	// Vars
	private int id; // ID
	private String name; // Nome da Sala
	private int maxCapacity; // Lotação Máxima	
	
	// Método Construtor
	public Room(String name, int maxCapacity) {
		super();
		this.id = -1;
		this.name = name;
		this.maxCapacity = maxCapacity;
	}
	public Room(int id, String name, int maxCapacity) {
		super();
		this.id = id;
		this.name = name;
		this.maxCapacity = maxCapacity;
	}
	public Room() {
		super();
		this.id = -1;
		this.name = "N/A";
		this.maxCapacity = -1;
	}
		
	// Getters & Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMaxCapacity() {
		return maxCapacity;
	}
	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	public boolean hasValidId() {
		if(this.id != -1) {
			return true;
		} else {
			return false;
		}
	}
	
	// Remove uma sala do banco de dados (por ID).
	public static void RemoveRoomByID(ConnectDB connection, int id) {
		String sql = "DELETE FROM room where idRoom=" + id + ";";		
		try {
		    PreparedStatement statement = connection.connect().prepareStatement(sql);
			int status = statement.executeUpdate();
			if(status != 0) {
				System.out.println("Sala deletada.");
			}
		} catch (SQLException e) {
		    e.printStackTrace();
		} finally {
			connection.disconnect();
		}
	}
	
}
