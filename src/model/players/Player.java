package model.players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.board.Tradeable;
import model.enums.PlayerEnums;

public class Player implements Tradeable {
	private String name;
	private PlayerEnums colour;
	private Map<String, Integer> resources = new HashMap<String, Integer>();
	private Map<String, Integer> cocoTiles = new HashMap<String, Integer>();
	private Integer initialNum = 0;
	private List<String> shipAssets = new ArrayList<String>();
	private List<String> lairAssets = new ArrayList<String>();
	private String age;
	
	public Player(String name, PlayerEnums colour, String age) { // The constructor...
		this.name = name;
		this.colour = colour;
		this.age = age;
		this.set();
		this.initializeAssets(); // Setting up the player's starting lair and ship locations...
	}

	@Override
	public void set() {
		this.resources.put("Wood", 1);
		this.resources.put("Cutlass", this.initialNum);
		this.resources.put("Goats", this.initialNum);
		this.resources.put("Gold", this.initialNum);
		this.resources.put("Molasses", 1);
		this.resources.put("Coco tiles", this.initialNum);
		this.cocoTiles.put("Ghost Captain", this.initialNum);
		this.cocoTiles.put("Build", this.initialNum);
		this.cocoTiles.put("Resource Combination 1", this.initialNum);
		this.cocoTiles.put("Resource Combination 2", this.initialNum);
	}
	
	public Integer getAge() {
		return Integer.parseInt(this.age);
	}
	
	public void initializeAssets() {
		switch (this.colour) {
		case RED:
			// The two initial lair locations for the red player
			this.addLairAsset(" 5 ");
			this.addLairAsset(" 15 ");
			// And their two ship locations...
			this.addShipAsset(" 4 - 5 ");
			this.addShipAsset(" 15 - 16 ");
			break;
		case WHITE:
			this.addLairAsset(" 2 ");
			this.addLairAsset(" 18 ");
			this.addShipAsset(" 2 - 3 ");
			this.addShipAsset(" 17 - 18 ");
			break;
		case ORANGE:
			this.addLairAsset(" 9 ");
			this.addLairAsset(" 25 ");
			this.addShipAsset(" 9 - 10 ");
			this.addShipAsset(" 24 - 25 ");
			break;
		case BLUE:
			this.addLairAsset(" 12 ");
			this.addLairAsset(" 22 ");
			this.addShipAsset(" 11 - 12 ");
			this.addShipAsset(" 22 - 23 ");
			break;
		}
	}

	public void addShipAsset(String asset) {
		this.shipAssets.add(asset);
	}

	public void addLairAsset(String asset) {
		this.lairAssets.add(asset);
	}

	public void viewAssets() {
		System.out.println(this.lairAssets.toString());
		System.out.println(this.shipAssets.toString());
	}

	public List<String> getLairAssets() {
		return this.lairAssets;
	}

	public List<String> getShipAssets() {
		return this.shipAssets;
	}

	@Override
	public boolean isAvailable(String resourceName, Integer number) {
		if ((this.resources.containsKey(resourceName)) && (this.resources.get(resourceName) >= number)) {
			System.out.println("Player has " + resources.get(resourceName) + " " + resourceName);
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

	public Integer getNumOfCocoTiles() {
		int num = 0;
		for (Integer value : this.getCocoTiles().values()) {
			num += value;
		}
		return num;
	}

	@Override
	public String toString() {
		String playerResources = "";
		for (String resource : this.resources.keySet()) {
			playerResources = playerResources + "\n" + resource + ": " + this.resources.get(resource);
		}
		return "\nName: " + this.name + "\n\t" + "Colour: " + this.colour + "\n\tPlayer's lairs:" + this.lairAssets.toString() + "\n\tPlayer's ships:" + this.shipAssets.toString();
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

	public Map<String, Integer> getCocoTiles() {
		return this.cocoTiles;
	}

	public void giveResource(String resource, Integer num) {
		this.resources.put(resource, this.resources.get(resource) + num);
	}

	public void takeResource(String resource, Integer num) {
		this.resources.put(resource, resources.get(resource) - num);
	}
}
