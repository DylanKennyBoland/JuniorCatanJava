package model.enums;

public enum ResourceEnums {
	CUTLASS("Cutlass"),
	GOATS("Goats"),
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
