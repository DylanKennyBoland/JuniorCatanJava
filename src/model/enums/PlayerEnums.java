package model.enums;

public enum PlayerEnums {
	RED("Red"),
	WHITE("White"),
	ORANGE("Orange"),
	BLUE("Blue");
	
	private final String colour;
	
	private PlayerEnums(String colour) {
		this.colour = colour;
	}
	
	public String getColour() {
		return this.colour;
	}
}
