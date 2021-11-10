package enums;

public enum PlayerEnums {
	RED("Red"),
	WHITE("White"),
	ORANGE("Orange"),
	BLUE("Blue");
	
	private final String color;
	
	private PlayerEnums(String color) {
		this.color = color;
	}
	
	public String getColor() {
		return this.color;
	}
}
