package model.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.enums.PlayerEnums;

public class Player implements Tradeable {
	private String name;
	private PlayerEnums colour;
	private Map<String, Integer> resources = new HashMap<String, Integer>();
	private Integer initialNum = 0;
	private List<String> assets = new ArrayList<String>();

	public Player(String name, PlayerEnums colour) { // The constructor...
		this.name = name;
		this.colour = colour;
		this.set();
		this.initializeAssets(); // Setting up the player's starting lair and ship locations...
	}
	
	public List<String> getLairAssets(){
		return this.assets;
	}
	

	@Override
	public void set() {
		this.resources.put("Wood", this.initialNum);
		this.resources.put("Cutlass", this.initialNum);
		this.resources.put("Goats", this.initialNum);
		this.resources.put("Gold", this.initialNum);
		this.resources.put("Molasses", this.initialNum);
		this.resources.put("Coco tiles", this.initialNum);
	}

	public void initializeAssets() {
		switch (this.colour) {
		case RED:
			// The two initial lair locations for the red player
			this.addAsset("5");
			this.addAsset("15");
			// And their two ship locations...
			this.addAsset(" 5 - 4 ");
			this.addAsset(" 15 - 16 ");
			break;
		case WHITE:
			this.addAsset("2");
			this.addAsset("18");
			this.addAsset(" 2 - 3 ");
			this.addAsset(" 18 - 17 ");
			break;
		case ORANGE:
			this.addAsset("9");
			this.addAsset("25");
			this.addAsset(" 9 - 10 ");
			this.addAsset(" 25 - 24 ");
			break;
		case BLUE:
			this.addAsset("12");
			this.addAsset("22");
			this.addAsset(" 12 - 11 ");
			this.addAsset(" 22 - 23 ");
			break;
		}
	}

	public void addAsset(String asset) {
		this.assets.add(asset);
	}

	public void viewAsset() {
		System.out.println(this.assets.toString());
	}

	public List<String> getAssets() {
		return this.assets;
	}

	@Override
	public boolean isAvailable(String resourceName, Integer number) {
		if ((this.resources.containsKey(resourceName)) && (this.resources.get(resourceName) >= number)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String trade(String tilein, Integer numIn, String tileout, Integer numOut) {
		if (!this.isAvailable(tileout, numOut)) {
			return String.format("There are no '%1$s' tiles in the stockpile to trade with.", tileout);
		}
		int currNumTileIn = this.resources.get(tilein);
		int currNumTileOut = this.resources.get(tileout);
		// Now we update the values associated with the keys...
		this.resources.replace(tilein, currNumTileIn + numIn);
		this.resources.replace(tileout, currNumTileOut - numOut);
		return String.format("You've traded a %1$d %2$s for %3$d %4$s.", numIn, tilein, numOut, tileout);
	}

	@Override
	public String toString() {
		String playerResources = "";
		for (String resource : this.resources.keySet()) {
			playerResources = playerResources + "\n" + resource + ": " + this.resources.get(resource);
		}
		return "Name: " + this.name + "\n" + "Colour: " + this.colour + "\n\nPlayer's Resources:" + playerResources;
	}

	// 'get' and 'set' methods:
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PlayerEnums getColour() {
		return this.colour;
	}

	public void setColour(PlayerEnums colour) {
		this.colour = colour;
	}

	public Map<String, Integer> getResources() {
		return this.resources;
	}

	public void giveResource(String resource, Integer num) {
		this.resources.put(resource, this.resources.get(resource) + num);
	}

	public void takeResource(String resource, Integer num) {
		this.resources.put(resource, resources.get(resource) - num);
	}
}
