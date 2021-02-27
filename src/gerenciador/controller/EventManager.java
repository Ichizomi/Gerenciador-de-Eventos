package gerenciador.controller;

import gerenciador.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Classe principal controlador do sistema. Inclui funcionalidade para gerênciar um evento de treinamento de uma empresa fictícia.
public class EventManager {

	private static ConnectDB conn = new ConnectDB(); // Conexão com o Banco.
	private static Scanner scan = new Scanner(System.in); // Scanner para leitura da entrada de dados do usuário. (por linha de comando)
	
	// Função principal para executar o programa.
	public static void main(String[] args) {
		
		conn.connect();	//Estabelece conexão com o banco.

		ShowMenu0(); // Carrega Menu Inicial (Por Texto)
		
		conn.disconnect(); // Encerra conexão com o banco.
				
		//conn.ClearDatabase(conn); // Limpar todas as tabelas do banco.
	}
	
	// Menu de interação inicial do sistema.
	private static void ShowMenu0() {
		System.out.println();
		System.out.println("--- Sistema de Gerênciamento de Eventos ---");		
		System.out.println("1) Consultar/Listar Dados");
		System.out.println("2) Cadastrar/Alterar/Remover Dados");
		System.out.println("--- ----------------------------------- ---");
		System.out.print("Digite o número da opção desejada: ");
		int choice = ReadOption();
		System.out.println();
		switch(choice) {
			case 1:
				ShowMenu1();
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
	
	// Menu para Opção 1 - Consultar/Listar Dados
	private static void ShowMenu1() {
		System.out.println();
		System.out.println("--- Consulta/Listagem de Dados ---");		
		System.out.println("1) Lista Salas de Evento");
		System.out.println("2) Listar Salas de Café");
		System.out.println("3) Listar Pessoas");
		System.out.println("4) Voltar ao menu inicial...");
		System.out.println("--- ----------------------------------- ---");
		System.out.print("Digite o número da opção desejada: ");
		int choice = ReadOption();
		System.out.println();
		switch(choice) {
			case 1: // 1) Lista Salas de Evento
				EventRoom.ListRoom(conn, true);
				ShowMenu1();
				break;
			case 2: // 2) Listar Salas de Café
				CoffeeRoom.ListRoom(conn, true);
				ShowMenu1();
				break;
			case 3: // 3) Listar Pessoas
				Person.ListPeople(conn, true);
				ShowMenu1();
				break;
			case 4: // 4) Voltar ao menu inicial...
				ShowMenu0();
				break;
			default:
				System.out.println("Opção inválida! Tente novamente");
				ShowMenu1();
				break;
		}	
	}
	
	// Menu para Opção 2 - Cadastro/Alteração/Remoção de Dados
	private static void ShowMenu2() {
		System.out.println();
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
		int roomType = -1;
		EventRoom er = new EventRoom();
		CoffeeRoom cr = new CoffeeRoom();
		String name;
		int maxCapacity;
		Person p = new Person();
		String firstName;
		String surName;
		switch(choice) {
			case 1: // 1) Cadastrar Sala
				System.out.println("--- Cadastrar Sala ---");
				roomType = AskRoomType();
				if(roomType == 1) { // Tipo = Sala de Evento
					System.out.print("Entre com o nome da sala: ");
					name = scan.nextLine();
					System.out.print("Entre com a lotação da sala: ");
					maxCapacity = ReadOption();
					scan.nextLine(); //Limpa Scanner
					er = new EventRoom(name, maxCapacity);
					er.SaveRoom(conn, er); // Salvar no banco
				} else if (roomType == 2) { // Tipo = Sala de Café
					System.out.print("Entre com o nome da sala: ");
					name = scan.nextLine();
					System.out.print("Entre com a lotação da sala: ");
					maxCapacity = ReadOption();
					scan.nextLine(); //Limpa Scanner
					cr = new CoffeeRoom(name, maxCapacity);
					cr.SaveRoom(conn, cr); // Salvar no banco
				} else {
					System.out.print("Tipo de sala inválido, tente novamente!");
				}
				ShowMenu2();
				break;
			case 2: // 2) Editar Sala
				ShowMenu2_2();
				break;
			case 3: // 3) Remover Sala
				ShowMenu2_3();
				break;
			case 4: // 4) Cadastrar Pessoa
				System.out.println("--- Cadastrar Pessoa ---");
				System.out.print("Entre com o primeiro nome: ");
				firstName = scan.nextLine();
				System.out.print("Entre com o sobrenome: ");
				surName = scan.nextLine();
				p = new Person(firstName, surName);
				p.SavePerson(conn, p); // Salvar no banco.	
				ShowMenu2();
				break;
			case 5: // 5) Editar Pessoa
				ShowMenu2_5();				
				break;
			case 6: // 6) Remover Pessoa
				ShowMenu2_6();
				break;
			case 7: // 7) Voltar ao menu inicial...
				ShowMenu0();
				break;
			default:
				System.out.println("Opção inválida! Tente novamente");
				ShowMenu0();
				break;
		}
	}
	
	// Menu para Opção 2-2 - Editar Sala
	private static void ShowMenu2_2() {
		System.out.println("--- Editar Sala ---");
		int roomType = AskRoomType();
		if(roomType != 1 && roomType != 2) {
			System.out.print("Tipo de sala inválido, tente novamente!");
			ShowMenu2();
		}
		System.out.println("Escolher o método utilizado para a editar os dados de uma sala: ");
		System.out.println("1) Informar o ID diretamente");
		System.out.println("2) Listar salas e escolher entre elas");
		System.out.println("3) Buscar sala");
		int choice = ReadOption();
		scan.nextLine(); // Limpa Scanner.
		int rId = -1;		
		EventRoom er = new EventRoom();
		CoffeeRoom cr = new CoffeeRoom();
		String name;
		int maxCapacity;
		switch(choice) {
			case 1: // 1) Informar o ID diretamente
				System.out.print("Entre com o ID: ");
				rId = ReadOption();
				scan.nextLine(); // Limpa Scanner.
				if(rId != -1) {
					System.out.print("Entre com novo nome: ");
					name = scan.nextLine();
					//scan.nextLine(); // Limpa Scanner.
					System.out.print("Entre com a nova lotação máxima da sala: ");
					maxCapacity = ReadOption();
					scan.nextLine(); // Limpa scanner.
					if(roomType == 1) {
						er = new EventRoom(rId, name, maxCapacity);
						System.out.println("Alterando para: " + name + " {" + maxCapacity + "}");
						EventRoom.UpdateRoom(conn, rId, er);
					} else if (roomType == 2) {
						cr = new CoffeeRoom(rId, name, maxCapacity);
						System.out.println("Alterando para: " + name + " {" + maxCapacity + "}");
						CoffeeRoom.UpdateRoom(conn, rId, cr);
					}
				} else {
					System.out.println("ID Inválido");	
					ShowMenu0();
				}
				ShowMenu2();
				break;
			case 2: // 2) Listar salas e escolher entre elas
				rId = ListRoomFromDBAndPickOption("--- Digite o ID da sala que deseja editar: ", roomType);
				if(rId != -1) {
					System.out.print("Entre com novo nome: ");
					name = scan.nextLine();
					//scan.nextLine(); // Limpa Scanner.
					System.out.print("Entre com a nova lotação máxima da sala: ");
					maxCapacity = ReadOption();
					scan.nextLine(); // Limpa scanner.
					if(roomType == 1) {
						er = new EventRoom(rId, name, maxCapacity);
						System.out.println("Alterando para: " + name + " {" + maxCapacity + "}");
						EventRoom.UpdateRoom(conn, rId, er);
					} else if (roomType == 2) {
						cr = new CoffeeRoom(rId, name, maxCapacity);
						System.out.println("Alterando para: " + name + " {" + maxCapacity + "}");
						CoffeeRoom.UpdateRoom(conn, rId, cr);
					}
				}
				ShowMenu2();						
				break;
			case 3: // 3) Buscar Sala
				if(roomType == 1) {
					System.out.print("Entre com o nome da sala:");
					name = scan.nextLine();
					er = EventRoom.SearchRoomByName(conn, name);
					if(er.hasValidId()) {
						if(er.getId() != -1) {
							rId = er.getId();
							System.out.println("Encontrado: [" + er.getId() + "] "+ er.getName() +" {" + er.getMaxCapacity() + "}");
							System.out.print("Entre com novo nome: ");
							name = scan.nextLine();
							System.out.print("Entre com a nova lotação: ");
							maxCapacity = ReadOption();
							scan.nextLine(); // Limpa scanner.
							er = new EventRoom(rId, name, maxCapacity);
							System.out.println("Alterando para: " + name + " {" + maxCapacity + "}");
							EventRoom.UpdateRoom(conn, rId, er);
						} else {
							System.out.println("Falha na busca. ID Inválido");
						}	
					} else {
						System.out.println("Sala não encontrada...");
						ShowMenu0();
					}
				} else if (roomType == 2) {
					System.out.print("Entre com o nome da sala:");
					name = scan.nextLine();
					cr = CoffeeRoom.SearchRoomByName(conn, name);
					if(cr.hasValidId()) {
						if(cr.getId() != -1) {
							rId = cr.getId();
							System.out.println("Encontrado: [" + cr.getId() + "] "+ cr.getName() +" {" + cr.getMaxCapacity() + "}");
							System.out.print("Entre com novo nome: ");
							name = scan.nextLine();
							System.out.print("Entre com a nova lotação: ");
							maxCapacity = ReadOption();
							scan.nextLine(); // Limpa scanner.
							cr = new CoffeeRoom(rId, name, maxCapacity);
							System.out.println("Alterando para: " + name + " {" + maxCapacity + "}");
							CoffeeRoom.UpdateRoom(conn, rId, cr);
						} else {
							System.out.println("Falha na busca. ID Inválido");
						}	
					} else {
						System.out.println("Sala não encontrada...");
						ShowMenu0();
					}
				}						
				ShowMenu2();
				break;
			default:
				System.out.println("Opção inválida! Tente novamente");
				System.out.println("ID Inválido");
				ShowMenu0();
				break;
		}
	}
	
	// Menu para Opção 2-3 - Remover Sala
	private static void ShowMenu2_3() {
		System.out.println("--- Remover Sala ---");
		int roomType = AskRoomType();
		if(roomType != 1 && roomType != 2) {
			System.out.print("Tipo de sala inválido, tente novamente!");
			ShowMenu2();
		}
		System.out.println("Escolher o método utilizado para a remoção da sala: ");
		System.out.println("1) Informar o ID diretamente");
		System.out.println("2) Listar salas e escolher entre elas");
		System.out.println("3) Buscar sala");
		int choice = ReadOption();
		scan.nextLine(); // Limpa Scanner.
		int rId = -1;
		EventRoom er = new EventRoom();
		CoffeeRoom cr = new CoffeeRoom();
		String name;
		switch(choice) {
			case 1: // 1) Informar o ID diretamente
				System.out.print("Entre com o ID: ");
				rId = ReadOption();
				if(rId != -1) {
					Room.RemoveRoomByID(conn, rId);
				} else {
					System.out.println("ID Inválido");							
				}
				ShowMenu2();
				break;
			case 2: // 2) Listar Salas e escolher entre elas
				rId = ListRoomFromDBAndPickOption("--- Digite o ID da sala que deseja remover: ", roomType);
				if(rId != -1) {
					Room.RemoveRoomByID(conn, rId);
				}
				ShowMenu2();						
				break;
			case 3: // 3) Buscar sala
				if(roomType == 1) {
					System.out.print("Entre com o nome da sala:");
					name = scan.nextLine();
					er = EventRoom.SearchRoomByName(conn, name);
					if(er.hasValidId()) {
						if(er.getId() != -1) {
							rId = er.getId();
							System.out.println("Encontrado: [" + er.getId() + "] "+ er.getName() +" {" + er.getMaxCapacity() + "}");
							Room.RemoveRoomByID(conn, rId);
						} else {
							System.out.println("Falha na busca. ID Inválido");
						}	
					} else {
						System.out.println("Sala não encontrada...");
						ShowMenu0();
					}
				} else if (roomType == 2) {
					System.out.print("Entre com o nome da sala:");
					name = scan.nextLine();
					cr = CoffeeRoom.SearchRoomByName(conn, name);
					if(cr.hasValidId()) {
						if(cr.getId() != -1) {
							rId = cr.getId();
							System.out.println("Encontrado: [" + cr.getId() + "] "+ cr.getName() +" {" + cr.getMaxCapacity() + "}");
							Room.RemoveRoomByID(conn, rId);
						} else {
							System.out.println("Falha na busca. ID Inválido");
						}	
					} else {
						System.out.println("Sala não encontrada...");
						ShowMenu0();
					}
				}										
				ShowMenu2();							
				break;
			default:
				System.out.println("Opção inválida! Tente novamente");
				ShowMenu0();
				break;
		}
	}
	
	// Menu para Opção 2-5 - Editar Pessoa
	private static void ShowMenu2_5() {
		System.out.println("--- Editar Pessoa ---");
		System.out.println("Escolher o método utilizado para a editar os dados de uma pessoa: ");
		System.out.println("1) Informar o ID diretamente");
		System.out.println("2) Listar pessoas e escolher entre elas");
		System.out.println("3) Buscar pessoa");
		int choice = ReadOption();
		int rId = -1;		
		Person p = new Person();
		String firstName;
		String surName;
		switch(choice) {
			case 1: // 1) Informar o ID diretamente
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
			case 2: // 2) Listar pessoas e escolher entre elas
				rId = ListPersonFromDBAndPickOption("--- Digite o ID da pessoa que deseja remover: ");
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
			case 3: // 3) Buscar pessoa
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
	}
		
	// Menu para Opção 2-6 - Remover Pessoa
	private static void ShowMenu2_6() {
		System.out.println("--- Remover Pessoa ---");
		System.out.println("Escolher o método utilizado para a remoção da pessoa: ");
		System.out.println("1) Informar o ID diretamente");
		System.out.println("2) Listar pessoas e escolher entre elas");
		System.out.println("3) Buscar pessoa");
		int choice = ReadOption();
		int rId = -1;
		Person p = new Person();
		switch(choice) {
			case 1: // 1) Informar o ID diretamente
				System.out.print("Entre com o ID: ");
				rId = ReadOption();
				if(rId != -1) {
					Person.RemovePersonByID(conn, rId);
				} else {
					System.out.println("ID Inválido");							
				}
				ShowMenu2();
				break;
			case 2: // 2) Listar pessoas e escolher entre elas
				rId = ListPersonFromDBAndPickOption("--- Digite o ID da pessoa que deseja remover: ");
				if(rId != -1) {
					Person.RemovePersonByID(conn, rId);
				}
				ShowMenu2();						
				break;
			case 3: // 3) Buscar pessoa
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
				ShowMenu0();
				break;
		}
	}
	
	// Menu para o usuário escolher entre o tipo da sala.
	private static int AskRoomType() {
		System.out.println("Escolha o tipo da sala: ");
		System.out.println("1) Sala de Evento");
		System.out.println("2) Sala de Café");
		int choice = ReadOption();
		scan.nextLine(); //Limpa Scanner
		return choice;
	}
	
	// Lista uma sala do banco de dados e permite escolher um ID entre as opções listadas.
	private static int ListRoomFromDBAndPickOption(String operationMessage, int roomType) {
		// Listagem de Opções
		List<Integer> options = new ArrayList<>();
		if(roomType == 1) {			
			List<EventRoom> lister = EventRoom.ListRoom(conn, false);
			System.out.println("--- Listagem de Salas de Evento ---");		
			System.out.println("[ID] Nome {Lotação}");
			for(int i=0; i < lister.size(); i++) {
				System.out.println("[" + lister.get(i).getId() + "] "+ lister.get(i).getName() +" {" + lister.get(i).getMaxCapacity() + "}");
				options.add(lister.get(i).getId());
			}	
		} else if (roomType == 2) {
			List<CoffeeRoom> listcr = CoffeeRoom.ListRoom(conn, false);			
			System.out.println("--- Listagem de Salas de Café ---");		
			System.out.println("[ID] Nome {Lotação}");
			for(int i=0; i < listcr.size(); i++) {
				System.out.println("[" + listcr.get(i).getId() + "] "+ listcr.get(i).getName() +" {" + listcr.get(i).getMaxCapacity() + "}");
				options.add(listcr.get(i).getId());
			}	
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
	
	
	// Lista uma pessoa do banco de dados e permite escolher um ID entre as opções listadas.
	private static int ListPersonFromDBAndPickOption(String operationMessage){
		
		// Listagem de Opções
		List<Person> listp = Person.ListPeople(conn, false);
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
			case 1: // 1) Buscar por primeiro nome
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
			case 2: // 2) Buscar por sobrenome
				System.out.print("Entre com o sobrenome: ");
				scan.nextLine(); // Limpa o scanner.
				String surName = scan.nextLine();
				p = Person.SearchPersonByLastName(conn, surName);
				if(p.hasValidId()) {
					return p;
				} else {
					System.out.println("Pessoa não encontrada...");
					ShowMenu0();
				}
				break;
			case 3: // 3) Voltar ao menu inicial...
				ShowMenu0();
				break;
			default:
				System.out.println("Opção inválida! Tente novamente");
				ShowMenu0();
				break;
		}
		return p;
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
}
