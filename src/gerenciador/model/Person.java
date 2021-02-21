package gerenciador.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

// Uma pessoa que participa do evento.
public class Person {

	// Vars
	private String firstName; // Primeiro Nome
	private String surName; // Sobrenome
	
	// Método Construtor
	public Person(String firstName, String surName) {
		super();
		this.firstName = firstName;
		this.surName = surName;
	}
	
	// Getters & Setters
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
	
	// Adiciona uma pessoa ao banco de dados.
	public void SavePerson(ConnectDB connection, Person p) {
		String sql = "INSERT INTO Person (firstName, surName) VALUES ('" + p.getFirstName() +"', '" + p.getSurName() + "');";		
		try {
		    PreparedStatement statement = connection.connect().prepareStatement(sql);
			int status = statement.executeUpdate();
			if(status != 0) {
				System.out.println("Pessoa adicionada com sucesso.");
			}
		} catch (SQLException e) {
		    e.printStackTrace();
		} finally {
			connection.disconnect();
		}
	}
	
	// Consultar pessoas no banco de dados.
	public void SearchPersonByFirstName(String firstName) {
		//To-Do
	}
	public void SearchPersonByLastName(String lastName) {
		//To-Do
	}
	public void SearchPersonByFullName(String fullName) {
		//To-Do
	}
	
}
