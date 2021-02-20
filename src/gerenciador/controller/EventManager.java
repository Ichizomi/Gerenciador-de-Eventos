package gerenciador.controller;

import gerenciador.model.*;

// Classe principal controlador do sistema. Inclui funcionalidade para gerênciar um evento.
public class EventManager {

	
	public static void main(String[] args) {
		
		// Teste de Execução
		System.out.println("Hello World!");
		
		Person p = new Person("Lucas", "Grijó");
		System.out.println("Criado por: " + p.getFirstName() + " " + p.getSurName());
	}

}
