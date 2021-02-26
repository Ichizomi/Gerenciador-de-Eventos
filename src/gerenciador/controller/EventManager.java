package gerenciador.controller;

import gerenciador.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Classe principal controlador do sistema. Inclui funcionalidade para gerênciar um evento de treinamento de uma empresa fictícia.
public class EventManager {

	private static ConnectDB conn = new ConnectDB(); // Conexão com o Banco.
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		//Estabelece conexão com o banco.
		conn.connect();	

		ShowMenu0(); // Carrega Menu Inicial (Por Texto)
		
		// Encerra conexão com o banco.
		conn.disconnect();
		
		// Limpar todas as tabelas do banco.
		//conn.ClearDatabase(conn);
	}
	
	// Menu de interação inicial do sistema. (Por Texto)
	private static void ShowMenu0() {
		System.out.println("--- Sistema de Gerênciamento de Eventos ---");		
		System.out.println("1) Consultar/Listar Dados");
		System.out.println("2) Cadastrar/Alterar/Remover Dados");
		System.out.println("--- ----------------------------------- ---");
		System.out.print("Digite o número da opção desejada: ");
		int choice = ReadOption();
		System.out.println();
		switch(choice) {
			case 1:
				System.out.println("1 escolhido!");
				Person.ListPeople(conn);
				ShowMenu0();
				break;
			case 2:
				ShowMenu2();
				break;
			default:
				System.out.println("Opção inválida! Tente novamente");
				ShowMenu0();
				break;
		}				
	}
	
	// Menu para Opção 2 - Cadastro de Dados (Por Texto)
	private static void ShowMenu2() {
		System.out.println("--- Cadastrar/Alterar/Remover Dados ---");		
		System.out.println("1) Cadastrar Sala");
		System.out.println("2) Editar Sala");
		System.out.println("3) Remover Sala");
		System.out.println("4) Cadastrar Pessoa");
		System.out.println("5) Editar Pessoa");
		System.out.println("6) Remover Pessoa");
		System.out.println("7) Voltar ao menu inicial...");
		System.out.println("--- ----------------------------------- ---");
		System.out.print("Digite o número da opção desejada: ");
		int choice = ReadOption();
		System.out.println();
		scan.nextLine(); //Limpa Scanner
		int subchoice;
		int rId;
		Person p = new Person();
		String firstName;
		String surName;
		switch(choice) {
			case 1:
				System.out.println("1 escolhido!");
				break;
			case 2:
				System.out.println("2 escolhido!");
				break;
			case 3:
				System.out.println("3 escolhido!");
				break;
			case 4:
				System.out.println("--- Cadastrar Pessoa ---");
				System.out.print("Entre com o primeiro nome: ");
				firstName = scan.nextLine();
				System.out.print("Entre com o sobrenome: ");
				surName = scan.nextLine();
				p = new Person(firstName, surName);
				p.SavePerson(conn, p); // Salvar no banco.	
				ShowMenu2();
				break;
			case 5:
				System.out.println("--- Editar Pessoa ---");
				System.out.println("Escolher o método utilizado para a editar os dados de uma pessoa: ");
				System.out.println("1) Informar o ID diretamente");
				System.out.println("2) Listar pessoas e escolher entre elas");
				System.out.println("3) Buscar pessoa");
				subchoice = ReadOption();
				rId = -1;				
				switch(subchoice) {
					case 1:
						System.out.print("Entre com o ID: ");
						rId = ReadOption();
						scan.nextLine(); // Limpa Scanner.
						if(rId != -1) {
							System.out.print("Entre com novo nome: ");
							firstName = scan.nextLine();
							//scan.nextLine(); // Limpa Scanner.
							System.out.print("Entre com novo sobrenome: ");
							surName = scan.nextLine();
							p = new Person(rId, firstName, surName);
							System.out.println("Alterando para: " + firstName + " " + surName);
							Person.UpdatePerson(conn, rId, p);
						} else {
							System.out.println("ID Inválido");	
							ShowMenu0();
						}
						ShowMenu2();
						break;
					case 2:
						rId = ListFromDBAndPickOption("--- Digite o ID da pessoa que deseja remover: ");
						if(rId != -1) {
							System.out.print("Entre com novo nome: ");
							firstName = scan.nextLine();
							System.out.print("Entre com novo sobrenome: ");
							surName = scan.nextLine();
							p = new Person(rId, firstName, surName);
							System.out.println("Alterando para: " + firstName + " " + surName);
							Person.UpdatePerson(conn, rId, p);
						}
						ShowMenu2();						
						break;
					case 3:
						p = SearchPersonMenu();
						if(p.getId() != -1) {
							rId = p.getId();
							System.out.println("Encontrado: [" + p.getId() + "] "+ p.getFirstName() +" - " + p.getSurName() + "");
							System.out.print("Entre com novo nome: ");
							firstName = scan.nextLine();
							System.out.print("Entre com novo sobrenome: ");
							surName = scan.nextLine();
							p = new Person(rId, firstName, surName);
							System.out.println("Alterando para: " + firstName + " " + surName);
							Person.UpdatePerson(conn, rId, p);
						} else {
							System.out.println("Falha na busca. ID Inválido");
						}			
						ShowMenu2();
						break;
					default:
						System.out.println("Opção inválida! Tente novamente");
						System.out.println("ID Inválido");
						ShowMenu0();
						break;
				}
				break;
			case 6:
				System.out.println("--- Remover Pessoa ---");
				System.out.println("Escolher o método utilizado para a remoção da pessoa: ");
				System.out.println("1) Informar o ID diretamente");
				System.out.println("2) Listar pessoas e escolher entre elas");
				System.out.println("3) Buscar pessoa");
				subchoice = ReadOption();
				rId = -1;
				switch(subchoice) {
					case 1:
						System.out.print("Entre com o ID: ");
						rId = ReadOption();
						if(rId != -1) {
							Person.RemovePersonByID(conn, rId);
						} else {
							System.out.println("ID Inválido");							
						}
						ShowMenu2();
						break;
					case 2:
						rId = ListFromDBAndPickOption("--- Digite o ID da pessoa que deseja remover: ");
						if(rId != -1) {
							Person.RemovePersonByID(conn, rId);
						}
						ShowMenu2();						
						break;
					case 3:
						p = SearchPersonMenu();
						if(p.getId() != -1) {
							rId = p.getId();
							System.out.println("Encontrado: [" + p.getId() + "] "+ p.getFirstName() +" - " + p.getSurName() + "");
							Person.RemovePersonByID(conn, rId);
						} else {
							System.out.println("Falha na busca. ID Inválido");
						}			
						ShowMenu2();
						break;
					default:
						System.out.println("Opção inválida! Tente novamente");
						System.out.println("ID Inválido");
						ShowMenu0();
						break;
				}
				break;
			case 7:
				ShowMenu0();
				break;
			default:
				System.out.println("Opção inválida! Tente novamente");
				ShowMenu0();
				break;
		}
	}
	
	// Leitura de uma Opção de Menu (Inteiro)
	private static int ReadOption() {
		if(scan.hasNextInt()) {
			return scan.nextInt();
		} else {
			scan.nextLine(); // Limpa scanner.
			return -1;
		}
	}
	
	// Leitura de um Campo de Texto
	private static String ReadText(int maxSize) {
		if(scan.hasNext()) {
			return scan.nextLine();
		} else {
			scan.nextLine(); // Limpa scanner.
			return "Invalido";
		}
	}
	
	// Lista dados do banco de dados e permite escolher um ID entre as opções listadas.
	private static int ListFromDBAndPickOption(String operationMessage){
		
		// Listagem de Opções
		List<Person> listp = Person.ListPeople(conn);
		List<Integer> options = new ArrayList<>();
		System.out.println("--- Listagem de Pessoas ---");		
		System.out.println("[ID] Nome - Sobrenome");
		for(int i=0; i < listp.size(); i++) {
			System.out.println("[" + listp.get(i).getId() + "] "+ listp.get(i).getFirstName() +" - " + listp.get(i).getSurName() + "");
			options.add(listp.get(i).getId());
		}				
		
		// Validação da Opção
		int choice = -1;
		if(!options.isEmpty()) {
			System.out.print(operationMessage);
			choice = ReadOption();
			scan.nextLine(); // Limpa Scanner.
		} else {
			System.out.println("--- Lista Vazia... ---");
		}		
		return choice;
	}	
	
	// Abre menu para busca de uma pessoa (por nome ou sobrenome).
	private static Person SearchPersonMenu() {
		Person p = new Person();
		System.out.println("Escolha o método da busca: ");
		System.out.println("1) Buscar por primeiro nome");
		System.out.println("2) Buscar por sobrenome");
		System.out.println("3) Voltar ao menu inicial...");
		int subchoice = ReadOption();
		switch(subchoice) {
			case 1:
				System.out.print("Entre com o primeiro nome: ");
				scan.nextLine(); // Limpa o scanner.
				String firstName = scan.nextLine();
				p = Person.SearchPersonByFirstName(conn, firstName);
				if(p.hasValidId()) {
					return p;
				} else {
					System.out.println("Pessoa não encontrada...");
					ShowMenu0();
				}
				break;
			case 2:
				System.out.print("Entre com o sobrenome: ");
				scan.nextLine(); // Limpa o scanner.
				String surName = scan.nextLine();
				p = Person.SearchPersonByFirstName(conn, surName);
				if(p.hasValidId()) {
					return p;
				} else {
					System.out.println("Pessoa não encontrada...");
					ShowMenu0();
				}
				break;
			case 3:
				ShowMenu0();
				break;
			default:
				System.out.println("Opção inválida! Tente novamente");
				ShowMenu0();
				break;
		}
		return p;
	}
	
}
