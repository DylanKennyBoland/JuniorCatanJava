package model.enums;

public enum IslandEnums {
	PASTURES("Pastures"),
	CAVES("Caves"),
	RIVERS("Rivers"),
	SUGAR("Sugar"),
	FOREST("Forest"),
	SPOOKY("Spooky");
	
	private final String type;
	
	private IslandEnums(String type) {
		this.type = type;
	}
	
	public String getIslandType() {
		return this.type;
	}
}
