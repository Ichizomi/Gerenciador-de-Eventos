package gerenciador.model;

// Uma sala.
public abstract class Room {
	
	// Vars
	private String name; // Nome da Sala
	private int maxCapacity; // Lotação Máxima	
	
	// Método Construtor
	public Room(String name, int maxCapacity) {
		super();
		this.name = name;
		this.maxCapacity = maxCapacity;
	}
	
	// Getters & Setters
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
	
}
