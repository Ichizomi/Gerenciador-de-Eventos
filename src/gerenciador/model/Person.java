package gerenciador.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Uma pessoa que participa do evento.
public class Person {

	// Vars
	private int id; // ID
	private String firstName; // Primeiro Nome
	private String surName; // Sobrenome
	
	// Método Construtor
	public Person(String firstName, String surName) {
		super();
		this.id = -1;
		this.firstName = firstName;
		this.surName = surName;
	}
	
	public Person(int id, String firstName, String surName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.surName = surName;
	}
	
	public Person() {
		super();
		this.id = -1;
		this.firstName = "N/A";
		this.surName = "N/A";
	}
	
	// Getters & Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public boolean hasValidId() {
		if(this.id != -1) {
			return true;
		} else {
			return false;
		}
	}
	
	// Adiciona uma pessoa ao banco de dados.
	public void SavePerson(ConnectDB connection, Person p) {
		String sql = "INSERT INTO Person (firstName, surName) VALUES ('" + p.getFirstName() +"', '" + p.getSurName() + "');";		
		try {
		    PreparedStatement statement = connection.connect().prepareStatement(sql);
			int status = statement.executeUpdate();
			if(status != 0) {
				System.out.println("Pessoa \"" + p.getFirstName() + " " + p.getSurName() + "\" adicionada com sucesso.");
			}
		} catch (SQLException e) {
		    e.printStackTrace();
		} finally {
			connection.disconnect();
		}
	}
	
	// Retorna uma lista de todas as pessoas cadastradas no banco.
	public static List<Person> ListPeople(ConnectDB connection) {
		List<Person> list = new ArrayList<>();
		String sql = "SELECT * from person;";	
		
		try {
		    PreparedStatement statement = connection.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int idPerson = rs.getInt("idPerson");
				String firstName = rs.getString("firstName");
				String surName = rs.getString("surName");
				System.out.println(idPerson + " " + firstName + " " + surName);
				Person p = new Person(idPerson, firstName, surName);
				list.add(p);
			}
			return list;
		} catch (SQLException e) {
		    e.printStackTrace();
		    return list;
		} finally {
			connection.disconnect();
		}
	}
	
	// Consultar pessoas no banco de dados (por primeiro nome).
	public static Person SearchPersonByFirstName(ConnectDB connection, String _firstName) {
		Person p = new Person();
		String sql = "SELECT * from person where firstName = '" + _firstName + "';";			
		try {
		    PreparedStatement statement = connection.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int idPerson = rs.getInt("idPerson");
				String firstName = rs.getString("firstName");
				String surName = rs.getString("surName");
				//System.out.println(idPerson + " " + firstName + " " + surName);
				p = new Person(idPerson, firstName, surName);
			}
			return p;
		} catch (SQLException e) {
		    e.printStackTrace();
		    return p;
		} finally {
			connection.disconnect();
		}
	}
	// Consultar pessoas no banco de dados (por sobrenome).
	public static Person SearchPersonByLastName(ConnectDB connection, String _surName) {
		Person p = new Person();
		String sql = "SELECT * from person where surName = '" + _surName + "';";			
		try {
		    PreparedStatement statement = connection.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int idPerson = rs.getInt("idPerson");
				String firstName = rs.getString("firstName");
				String surName = rs.getString("surName");
				//System.out.println(idPerson + " " + firstName + " " + surName);
				p = new Person(idPerson, firstName, surName);
			}
			return p;
		} catch (SQLException e) {
		    e.printStackTrace();
		    return p;
		} finally {
			connection.disconnect();
		}
	}
	
	// Remove pessoa do banco de dados (por ID).
	public static void RemovePersonByID(ConnectDB connection, int id) {
		String sql = "DELETE from person where idPerson=" + id + ";";		
		try {
		    PreparedStatement statement = connection.connect().prepareStatement(sql);
			int status = statement.executeUpdate();
			if(status != 0) {
				System.out.println("Pessoa deletada.");
			}
		} catch (SQLException e) {
		    e.printStackTrace();
		} finally {
			connection.disconnect();
		}
	}
	
	// Atualiza dados de uma pessoa no banco de dados (por ID)
	public static void UpdatePerson(ConnectDB connection, int id, Person updatedPerson) {
		String sql = "UPDATE person SET firstName = '" + updatedPerson.getFirstName() + "', surName = '" + updatedPerson.getSurName() + "' where idPerson = " + id + ";";			
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
