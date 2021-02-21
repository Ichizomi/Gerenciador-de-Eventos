package gerenciador.controller;

import gerenciador.model.*;

// Classe principal controlador do sistema. Inclui funcionalidade para gerênciar um evento de treinamento de uma empresa fictícia.
public class EventManager {

	private static ConnectDB conn = new ConnectDB(); // Conexão com o Banco.
	
	public static void main(String[] args) {
		
		//Estabelece conexão com o banco.
		conn.connect();
		
		
		// Teste de Execução
		System.out.println("Hello World!");
		
		Person p = new Person("Lucas", "Grijó");
		System.out.println("Criado por: " + p.getFirstName() + " " + p.getSurName());
		
		
		// Salvar no banco.
		p.SavePerson(conn, p);
		
		// Limpar todas as tabelas do banco.
		//conn.ClearDatabase(conn);
		
		// Encerra conexão com o banco.
		conn.disconnect();
		
	}

}
