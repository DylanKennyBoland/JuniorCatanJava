package model.enums;

public enum TradeEnums {
	BUILD_LAIR("Build lair"), BUILD_SHIP("Build ship"), BUY_COCO_TILE("Buy Coco tile"),
	COCO_TILE_GHOST_CAPTAIN("Ghost Captain Coco tile"), COCO_TILE_BUILD("Build Coco tile"),
	COCO_TILE_RESOURCE1("Resource Combo 1 Coco tile"), COCO_TILE_RESOURCE2("Resource Combo 2 Coco tile");

	private final String type;

	private TradeEnums(String type) {
		this.type = type; // String for name of Room
	}

	public String getType() {
		return this.type;
	}
}
