package gerenciador.controller;

import gerenciador.model.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

// Classe principal controlador do sistema. Inclui funcionalidade para gerênciar um evento de treinamento de uma empresa fictícia.
public class EventManager {

	private static ConnectDB conn = new ConnectDB(); // Conexão com o Banco.
	private static Scanner scan = new Scanner(System.in); // Scanner para leitura da entrada de dados do usuário. (por linha de comando)
	
	// Função principal para executar o programa.
	public static void main(String[] args) throws IOException {
		
		conn.connect();	//Estabelece conexão com o banco.

		ShowMenu0(); // Carrega Menu Inicial (Por Texto)
		
		conn.disconnect(); // Encerra conexão com o banco.
				
	}
	
	// Menu de interação inicial do sistema.
	private static void ShowMenu0() throws IOException {
		System.out.println();
		System.out.println("--- Sistema de Gerênciamento de Eventos ---");		
		System.out.println("1) Consultar/Listar Dados");
		System.out.println("2) Cadastrar/Alterar/Remover Dados");
		System.out.println("3) Organizar Evento");
		System.out.println("4) Ver Cronograma do Evento");
		System.out.println("5) Limpar Banco de Dados");
		System.out.println("6) Importar Base de Dados de Exemplo");
		System.out.println("--- ----------------------------------- ---");
		System.out.print("Digite o número da opção desejada: ");
		int choice = ReadOption();
		String confirm;
		scan.nextLine(); // Limpa Scanner.
		System.out.println();
		switch(choice) {
			case 1:
				ShowMenu1();
				break;
			case 2:
				ShowMenu2();
				break;
			case 3:
				OrganizeEvent();
				break;
			case 4:
				ShowSchedule();
				ShowMenu0();
				break;
			case 5:
				System.out.println("Essa operação irá deletar os dados em todas as tabelas do banco de dados. Para continuar digite \"SIM\": ");
				confirm = scan.nextLine();
				if(confirm.contains("SIM")) {
					System.out.println("Banco de dados limpado.");
					conn.ClearDatabase(conn); // Limpar todas as tabelas do banco.
					conn.connect();	//Estabelece conexão com o banco.
				} else {
					System.out.println("Operação de limpeza cancelada.");
				}
				ShowMenu0();
				break;
			case 6:
				System.out.println("Essa operação irá deletar os dados em todas as tabelas do banco de dados antes de importar os exemplos.");
				System.out.println("Para continuar digite \"SIM\": ");
				confirm = scan.nextLine();
				if(confirm.contains("SIM")) {
					System.out.println("Banco de dados limpado.");
					conn.ClearDatabase(conn); // Limpar todas as tabelas do banco.
					conn.connect();	//Estabelece conexão com o banco.
					ImportSampleData();
				} else {
					System.out.println("Operação de limpeza cancelada.");
				}				
				ShowMenu0();
				break;
			default:
				System.out.println("Opção inválida! Tente novamente");
				ShowMenu0();
				break;
		}				
	}
	
	// Menu para Opção 1 - Consultar/Listar Dados
	private static void ShowMenu1() throws IOException  {
		System.out.println();
		System.out.println("--- Consulta/Listagem de Dados ---");		
		System.out.println("1) Lista Salas de Evento");
		System.out.println("2) Listar Salas de Café");
		System.out.println("3) Listar Pessoas");
		System.out.println("4) Voltar ao menu inicial...");
		System.out.println("--- ----------------------------------- ---");
		System.out.print("Digite o número da opção desejada: ");
		int choice = ReadOption();
		int id = -1;
		List<Integer> ids = new ArrayList<>();
		List<RoomAllocation> allocations = new ArrayList<>();
		System.out.println();
		switch(choice) {
			case 1: // 1) Lista Salas de Evento
				List<EventRoom> listER = EventRoom.ListRoom(conn, true);				
				for(int i = 0; i < listER.size(); i++) {
					ids.add(listER.get(i).getId());
				}
				System.out.println();
				System.out.println("Para ver mais detalhes de uma sala, entre com o ID: ");
				id = ReadOption();
				if(ids.contains(id)) {
					int roomType = Room.CheckRoomTypeByID(conn, id);
					if(roomType == 1) {
						System.out.println("------------ " + EventRoom.SearchRoomByID(conn, id).getName() + " ---------");
						// Etapa 1						
						System.out.println("------------ ETAPA 1 ---------");
						allocations = RoomAllocation.ListAllocation(conn, "eventPhase = 1 AND Room_idRoom = " + id);
						for(int i = 0; i < allocations.size(); i++) {
							int personID = allocations.get(i).getIdPerson();
							Person pTemp = Person.SearchPersonByID(conn, personID);
							System.out.println("(Participante) ID: " + pTemp.getId() +"  Nome: " + pTemp.getFirstName() + " " + pTemp.getSurName());
						}
						allocations.clear();
						// Etapa 2
						System.out.println("------------ ETAPA 2 ---------");
						allocations = RoomAllocation.ListAllocation(conn, "eventPhase = 2 AND Room_idRoom = " + id);
						for(int i = 0; i < allocations.size(); i++) {
							int personID = allocations.get(i).getIdPerson();
							Person pTemp = Person.SearchPersonByID(conn, personID);
							System.out.println("(Participante) ID: " + pTemp.getId() +"  Nome: " + pTemp.getFirstName() + " " + pTemp.getSurName());
						}
						System.out.println("--------------------------");
						ShowMenu1();
					} else {
						ShowMenu1();
					}
				} else {
					ShowMenu1();
				}
				break;
			case 2: // 2) Listar Salas de Café
				List<CoffeeRoom> listCR  = CoffeeRoom.ListRoom(conn, true);
				for(int i = 0; i < listCR.size(); i++) {
					ids.add(listCR.get(i).getId());
				}
				System.out.println();
				System.out.println("Para ver mais detalhes de uma sala, entre com o ID: ");
				id = ReadOption();
				if(ids.contains(id)) {
					int roomType = Room.CheckRoomTypeByID(conn, id);
					if(roomType == 2) {
						System.out.println("------------ " + CoffeeRoom.SearchRoomByID(conn, id).getName() + " ---------");
						// Etapa 1						
						System.out.println("------------ ETAPA 1 ---------");
						allocations = RoomAllocation.ListAllocation(conn, "eventPhase = 1 AND Room_idRoom = " + id);
						for(int i = 0; i < allocations.size(); i++) {
							int personID = allocations.get(i).getIdPerson();
							Person pTemp = Person.SearchPersonByID(conn, personID);
							System.out.println("(Participante) ID: " + pTemp.getId() +"  Nome: " + pTemp.getFirstName() + " " + pTemp.getSurName());
						}
						allocations.clear();
						// Etapa 2						
						System.out.println("------------ ETAPA 2 ---------");
						allocations = RoomAllocation.ListAllocation(conn, "eventPhase = 2 AND Room_idRoom = " + id);
						for(int i = 0; i < allocations.size(); i++) {
							int personID = allocations.get(i).getIdPerson();
							Person pTemp = Person.SearchPersonByID(conn, personID);
							System.out.println("(Participante) ID: " + pTemp.getId() +"  Nome: " + pTemp.getFirstName() + " " + pTemp.getSurName());
						}
						System.out.println("--------------------------");
						ShowMenu1();
					} else {
						ShowMenu1();
					}
				} else {
					ShowMenu1();
				}
				break;
			case 3: // 3) Listar Pessoas
				List<Person> listP = Person.ListPeople(conn, true);
				for(int i = 0; i < listP.size(); i++) {
					ids.add(listP.get(i).getId());
				}
				System.out.println();
				System.out.println("Para ver mais detalhes de uma pessoa, entre com o ID: ");
				id = ReadOption();
				if(ids.contains(id)) {
					
						System.out.println("------------ " + Person.SearchPersonByID(conn, id).getFirstName() + " " + Person.SearchPersonByID(conn, id).getSurName() + " ---------");
						// Etapa 1						
						System.out.println("------------ ETAPA 1 ---------");
						allocations = RoomAllocation.ListAllocation(conn, "eventPhase = 1 AND Person_idPerson = " + id);
						for(int i = 0; i < allocations.size(); i++) {
							int roomID = allocations.get(i).getIdRoom();
							int rType = Room.CheckRoomTypeByID(conn, roomID);
							if(rType == 1) {
								EventRoom erTemp = EventRoom.SearchRoomByID(conn, roomID);
								System.out.println("(Sala de Evento) ID: " + erTemp.getId() +"  Nome: " + erTemp.getName() + " Lotação: " + erTemp.getMaxCapacity());
								
							} else if (rType == 2) {
								CoffeeRoom crTemp = CoffeeRoom.SearchRoomByID(conn, roomID);
								System.out.println("(Sala de Café) ID: " + crTemp.getId() +"  Nome: " + crTemp.getName() + " Lotação: " + crTemp.getMaxCapacity());
								
							}
						}
						allocations.clear();
						// Etapa 2						
						System.out.println("------------ ETAPA 2 ---------");
						allocations = RoomAllocation.ListAllocation(conn, "eventPhase = 2 AND Person_idPerson = " + id);
						for(int i = 0; i < allocations.size(); i++) {
							int roomID = allocations.get(i).getIdRoom();
							int rType = Room.CheckRoomTypeByID(conn, roomID);
							if(rType == 1) {
								EventRoom erTemp = EventRoom.SearchRoomByID(conn, roomID);
								System.out.println("(Sala de Evento) ID: " + erTemp.getId() +"  Nome: " + erTemp.getName() + " Lotação: " + erTemp.getMaxCapacity());
								
							} else if (rType == 2) {
								CoffeeRoom crTemp = CoffeeRoom.SearchRoomByID(conn, roomID);
								System.out.println("(Sala de Café) ID: " + crTemp.getId() +"  Nome: " + crTemp.getName() + " Lotação: " + crTemp.getMaxCapacity());
								
							}
						}
						ShowMenu1();

				} else {
					ShowMenu1();
				}
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
	private static void ShowMenu2() throws IOException  {
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
	private static void ShowMenu2_2() throws IOException  {
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
	private static void ShowMenu2_3() throws IOException  {
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
	private static void ShowMenu2_5() throws IOException  {
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
	private static void ShowMenu2_6() throws IOException  {
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
	private static Person SearchPersonMenu() throws IOException  {
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
	
	// Menu para Opção 3 - Organizar um Evento.
	private static void OrganizeEvent() throws IOException {
		List<Person> participants = Person.ListPeople(conn, false);
		List<EventRoom> eventRooms = EventRoom.ListRoom(conn, false);
		List<CoffeeRoom> coffeeRooms = CoffeeRoom.ListRoom(conn, false);
		
		// Passo 0 - Remover Alocações Prévias 
		conn.ClearAllocations(conn);
		
		// Passo 1 - Verificar se há dados minimos para as duas etapas.
		System.out.print("Verificando requisitos mínimos...");		
		
		// Participantes >= 4
		if(participants.size() < 4) {
			System.out.println("Organização interrompida! É preciso ter 4 ou mais participantes cadastrados. Favor tentar novamente...");
			ShowMenu0();
		}
		
		// Salas de Evento >= 2
		if(eventRooms.size() < 2) {
			System.out.println("Organização interrompida! É preciso ter pelo menos duas salas de evento cadastradas. Favor tentar novamente...");
			ShowMenu0();
		}
		
		// Salas de Café >= 2
		if(coffeeRooms.size() < 2) {
			System.out.println("Organização interrompida! É preciso ter pelo menos duas salas de café cadastradas. Favor tentar novamente...");
			ShowMenu0();
		}
		
		// Espaço suficiente nas salas do evento.
		int totalEventRoomCapacity = 0;
		for(int i = 0; i < eventRooms.size(); i++) {
			totalEventRoomCapacity += eventRooms.get(i).getMaxCapacity();
		}
		if(totalEventRoomCapacity < participants.size()) {
			System.out.println("Organização interrompida! Há mais participantes (" + participants.size() + ") do que lugares em salas de evento(" + totalEventRoomCapacity + "). Favor tentar novamente...");
			ShowMenu0();
		}
		
		// Espaço suficiente nas salas de café. O espaço deve ser maior que o dobro do número de participantes para que as salas de café sejam distintas nas duas etapas do evento.
		int totalCoffeeRoomCapacity = 0;
		for(int i = 0; i < eventRooms.size(); i++) {
			totalCoffeeRoomCapacity += eventRooms.get(i).getMaxCapacity();
		}
		if(totalCoffeeRoomCapacity < participants.size() * 2) {
			System.out.println("Organização interrompida! Há mais participantes (" + participants.size() + ") do que espaço em salas de café(" + totalCoffeeRoomCapacity + "). Favor tentar novamente...");
			ShowMenu0();
		}
		
		System.out.println("OK!");
				
		// Passo 2 - Fazer alocações na tabela room allocation.
		// Regra de Negócio - Diferença máxima do número de participantes em cada sala é 1.
		// Regra de Negócio - Metade das pessoas em uma sala devem mudar de sala para o segundo estágio.
		
		// Alocar Salas para o Evento
		List<EventRoom> allocatedEventRooms = new ArrayList<>(); // Lista de Salas Reservadas
		int currentEventRoomAllocations = 0; // Espaços de sala já alocados.
		for(int i = 0; i < eventRooms.size(); i++) {
			// Se ainda não foram alocados espaços suficiente.
			if(totalEventRoomCapacity > currentEventRoomAllocations) {
				currentEventRoomAllocations += eventRooms.get(i).getMaxCapacity(); // Aloca espaços.
				allocatedEventRooms.add(eventRooms.get(i)); // Reserva a sala.
			} else {
				break; // Se já foram alocados espaços suficiente, não é preciso usar as salas restantes.
			}			
		}
		// Alocar Salas para intervalo de café.
		List<CoffeeRoom> allocatedCoffeeRoomsPhase1 = new ArrayList<>(); // Lista de Salas Reservadas
		List<CoffeeRoom> allocatedCoffeeRoomsPhase2 = new ArrayList<>(); // Lista de Salas Reservadas
		int currentCoffeeRoomAllocations = 0; // Espaços de sala já alocados.
		for(int i = 0; i < coffeeRooms.size(); i++) {
			// Se ainda não foram alocados espaços suficiente.
			if(totalCoffeeRoomCapacity / 2 > currentCoffeeRoomAllocations) {
				currentCoffeeRoomAllocations += coffeeRooms.get(i).getMaxCapacity(); // Aloca espaços.
				allocatedCoffeeRoomsPhase1.add(coffeeRooms.get(i)); // Reserva a sala.
			} else {
				if(totalCoffeeRoomCapacity > currentCoffeeRoomAllocations) {
					currentCoffeeRoomAllocations += coffeeRooms.get(i).getMaxCapacity(); // Aloca espaços.
					allocatedCoffeeRoomsPhase2.add(coffeeRooms.get(i)); // Reserva a sala.
				} else {
					break; // Se já foram alocados espaços suficiente, não é preciso usar as salas restantes.
				}				
			}			
		}
		
		// Etapa 1 do Evento.		
		Collections.shuffle(participants); // Randomiza participantes.
		RoomAllocation[] eventAllocationsPhase1 = new RoomAllocation[participants.size()]; // Alocações para a etapa 1 do evento.
		RoomAllocation[] coffeeAllocationsPhase1 = new RoomAllocation[participants.size()]; // Alocações para a etapa 1 do evento.
		System.out.println("Número de Participantes: " + eventAllocationsPhase1.length);
		

		System.out.println("Total de Salas de Eventos: " + eventRooms.size());
		System.out.println("Número de Salas de Evento Reservadas: " + allocatedEventRooms.size());
		
		// Distribui Participantes entre as salas reservadas (alternando em 1 para cada sala).
		System.out.print("Organizando Etapa 1...  ");
		// Salas de Evento
		int r = 0; // indice da sala. 
		RoomAllocation temp = new RoomAllocation();
		for(int i = 0; i < participants.size(); i++) {
			temp = new RoomAllocation();
			temp.setEventPhase(1);
			temp.setIdPerson(participants.get(i).getId());
			temp.setIdRoom(allocatedEventRooms.get(r).getId());	
			if(r >= allocatedEventRooms.size() - 1) {
				r = 0;
			} else {
				r++;
			}		
			eventAllocationsPhase1[i] = temp;
		}
		// Salas de Café
		r = 0; // indice da sala. 
		temp = new RoomAllocation();
		for(int i = 0; i < participants.size(); i++) {
			temp = new RoomAllocation();
			temp.setEventPhase(1);
			temp.setIdPerson(participants.get(i).getId());
			temp.setIdRoom(allocatedCoffeeRoomsPhase1.get(r).getId());	
			if(r >= allocatedCoffeeRoomsPhase1.size() - 1) {
				r = 0;
			} else {
				r++;
			}		
			coffeeAllocationsPhase1[i] = temp;
		}		
		
		// Salvar alocações da etapa 1 no banco de dados.
		for(int i = 0; i < eventAllocationsPhase1.length; i++) {
			eventAllocationsPhase1[i].SaveRoomAllocation(conn, eventAllocationsPhase1[i]);
		}
		for(int i = 0; i < coffeeAllocationsPhase1.length; i++) {
			coffeeAllocationsPhase1[i].SaveRoomAllocation(conn, coffeeAllocationsPhase1[i]);
		}
		System.out.println("Alocações da Etapa 1 realizadas!");
		
		// Etapa 2 do Evento.
		System.out.print("Organizando Etapa 2...  ");
		RoomAllocation[] eventAllocationsPhase2 = new RoomAllocation[participants.size()]; // Alocações para a etapa 2 do evento.
		RoomAllocation[] coffeeAllocationsPhase2 = new RoomAllocation[participants.size()]; // Alocações para a etapa 1 do evento.
		
		// Realizar Trocas de Sala e distribuir participantes (Metade são alocados normalmente enquanto a outra metade é alocado de traz pra frente).
		// Salas de Evento
		r = allocatedEventRooms.size() - 1; // indice da sala. 
		temp = new RoomAllocation();
		for(int i = 0; i < participants.size() / 2; i++) {
			temp = new RoomAllocation();
			temp.setEventPhase(2);
			temp.setIdPerson(participants.get(i).getId());
			temp.setIdRoom(allocatedEventRooms.get(r).getId());	
			if(r < 1) {
				r = allocatedEventRooms.size() - 1;
			} else {
				r--;
			}		
			eventAllocationsPhase2[i] = temp;
		}
		r = 0;
		for(int i = participants.size() / 2; i < participants.size(); i++) {
			temp = new RoomAllocation();
			temp.setEventPhase(2);
			temp.setIdPerson(participants.get(i).getId());
			temp.setIdRoom(allocatedEventRooms.get(r).getId());	
			if(r >= allocatedEventRooms.size() - 1) {
				r = 0;
			} else {
				r++;
			}		
			eventAllocationsPhase2[i] = temp;
		}
		// Salas de Café
		r = 0; // indice da sala. 
		temp = new RoomAllocation();
		for(int i = 0; i < participants.size(); i++) {
			temp = new RoomAllocation();
			temp.setEventPhase(2);
			temp.setIdPerson(participants.get(i).getId());
			temp.setIdRoom(allocatedCoffeeRoomsPhase2.get(r).getId());	
			if(r >= allocatedCoffeeRoomsPhase2.size() - 1) {
				r = 0;
			} else {
				r++;
			}		
			coffeeAllocationsPhase2[i] = temp;
		}
		
		// Salvar alocações da etapa 2 no banco de dados.
		for(int i = 0; i < eventAllocationsPhase2.length; i++) {
			eventAllocationsPhase2[i].SaveRoomAllocation(conn, eventAllocationsPhase2[i]);
		}
		for(int i = 0; i < coffeeAllocationsPhase2.length; i++) {
			coffeeAllocationsPhase2[i].SaveRoomAllocation(conn, coffeeAllocationsPhase2[i]);
		}
		System.out.println("Alocações da Etapa 2 realizadas!");
		
		// Mostrar Organização do Evento.
		ShowSchedule();
		
		ShowMenu0(); // Voltar Ao Menu Inicial
		
	}
	
	// Imprime o cronograma do evento.
	private static void ShowSchedule() {		
		List<Integer> allocationsPerRoomPhase1 = RoomAllocation.GetNumberOfAllocationsPerRoom(conn, 1);
		List<Integer> allocationsPerRoomPhase2 = RoomAllocation.GetNumberOfAllocationsPerRoom(conn, 2);
		List<RoomAllocation> allocationsTemp;
		EventRoom erTemp;
		CoffeeRoom crTemp;
		Person pTemp;
		System.out.println("");
		System.out.println("------- Cronograma do Evento (Visão Geral) -------");
		System.out.println("--------- ETAPA 1 ---------");
		for(int i = 0; i < allocationsPerRoomPhase1.size(); i+=2) {
			int roomID = allocationsPerRoomPhase1.get(i);
			int roomType = Room.CheckRoomTypeByID(conn, roomID);
			if(roomType == 1) {
				erTemp = EventRoom.SearchRoomByID(conn, roomID);
				System.out.println("--- Sala de Evento: " + erTemp.getName() + " Capacidade: " + erTemp.getMaxCapacity() + " ---");
			} else if(roomType == 2) {
				crTemp = CoffeeRoom.SearchRoomByID(conn, roomID);
				System.out.println("--- Sala de Café: " + crTemp.getName() + " Capacidade: " + crTemp.getMaxCapacity() + " ---");
			}
			allocationsTemp = RoomAllocation.ListAllocation(conn, "eventPhase = 1 AND Room_idRoom = " + roomID);
			for(int j = 0; j < allocationsPerRoomPhase1.get(i+1);j++) {
				int personID = allocationsTemp.get(j).getIdPerson();
				pTemp = Person.SearchPersonByID(conn, personID);
				System.out.println("(Participante) ID: " + pTemp.getId() +"  Nome: " + pTemp.getFirstName() + " " + pTemp.getSurName());
			}
		}
		System.out.println("------------ ETAPA 2 ---------");
		for(int i = 0; i < allocationsPerRoomPhase2.size(); i+=2) {
			int roomID = allocationsPerRoomPhase2.get(i);
			int roomType = Room.CheckRoomTypeByID(conn, roomID);
			if(roomType == 1) {
				erTemp = EventRoom.SearchRoomByID(conn, roomID);
				System.out.println("--- Sala de Evento: " + erTemp.getName() + " Capacidade: " + erTemp.getMaxCapacity() + " ---");
			} else if(roomType == 2) {
				crTemp = CoffeeRoom.SearchRoomByID(conn, roomID);
				System.out.println("--- Sala de Café: " + crTemp.getName() + " Capacidade: " + crTemp.getMaxCapacity() + " ---");
			}
			allocationsTemp = RoomAllocation.ListAllocation(conn, "eventPhase = 2 AND Room_idRoom = " + roomID);
			for(int j = 0; j < allocationsPerRoomPhase2.get(i+1);j++) {
				int personID = allocationsTemp.get(j).getIdPerson();
				pTemp = Person.SearchPersonByID(conn, personID);
				System.out.println("(Participante) ID: " + pTemp.getId() +"  Nome: " + pTemp.getFirstName() + " " + pTemp.getSurName());
			}
		}
		System.out.println("--------------------------------------------------");
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
	
	// Carrega no banco exemplos de dados pré-criados.
	private static void ImportSampleData() throws IOException {
		
		BufferedReader br = new BufferedReader(
		        new InputStreamReader(new FileInputStream("src/gerenciador/controller/SampleData.txt")));
		try {
		    String line;
	    	ArrayList<String> words = new ArrayList<>();
	    	String[] wordsArray;
	    	int readingMode = -1; // Modo de leitura muda quando texto atingi as tags prédefinidas [Person] e [Room].
		    while ((line = br.readLine()) != null) {
		        // process line
		    	// Quebra linha em locais com Tab.
		    	wordsArray = line.split("\t"); 
                for(String each : wordsArray){
                    if(!"".equals(each)){
                        words.add(each);
                    }
                }
                
                if(line.equals("[Person]")) {
                	readingMode = 1;
                } else if(line.equals("[Room]")) {
                	readingMode = 2;
                } else {
                	// Processar dependendo do modo de leitura.
                	if(readingMode == 1) {
                		// Ler Pessoa
                		Person temp = new Person(words.get(0), words.get(1));
                		temp.SavePerson(conn, temp);
                	} else if (readingMode == 2) {
                		// Ler Sala
                		String roomName = words.get(0);
                		int capacity = Integer.parseInt(words.get(1));
                		int roomType = Integer.parseInt(words.get(2));
                		if(roomType == 1) {
                			EventRoom erTemp = new EventRoom(roomName, capacity);
                			erTemp.SaveRoom(conn, erTemp);
                		} else if (roomType == 2) {
                			CoffeeRoom crTemp = new CoffeeRoom(roomName, capacity);
                			crTemp.SaveRoom(conn, crTemp);
                		}
                	}
                }                	           
                words = new ArrayList<>(); // Limpa palavra já lidas.
		    }
		} finally {
		    br.close();
		}
	}
	
}
