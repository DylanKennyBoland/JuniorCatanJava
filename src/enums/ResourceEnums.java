package enums;

public enum ResourceEnums {
	CUTLASS("Cutlass"),
	GOAT("Goat"),
	WOOD("Wood"),
	GOLD("Gold"),
	MOLASSES("Molasses");
	
	private final String type;
	
	private ResourceEnums(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
}
