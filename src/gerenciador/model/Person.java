package gerenciador.model;

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
	
}
