package model.players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.board.Tradeable;
import model.enums.PlayerEnums;

/**
 * Class for the Player object 
 * 
 * @author: Adam Durning & Dylan Boland
 * @Date: 27/12/2021
 * 
 */
public class Player implements Tradeable {
	// Setting up the Player variables.
	private String name;
	private PlayerEnums colour;
	private Map<String, Integer> resources = new HashMap<String, Integer>();
	private Map<String, Integer> cocoTiles = new HashMap<String, Integer>();
	private Integer initialNum = 0;
	private List<String> shipAssets = new ArrayList<String>();
	private List<String> lairAssets = new ArrayList<String>();
	private String age;
	private boolean skipResourcesCheck = false; // Used for when the player gets a Coco tile that allows them to
												// immediately build a lair or a ship...

	/**
	 * The Player Constructor
	 * 
	 * @param: name - The players name.
	 * @param: colour - The players colour {RED, WHITE, ORANGE, BLUE}
	 * @param: age - The players age (For deciding the order of the player turns.)
	 * */
	public Player(String name, PlayerEnums colour, String age) { // The constructor...
		this.name = name;
		this.colour = colour;
		this.age = age;
		this.initializeResources();
		this.initializeAssets(); // Setting up the player's starting lair and ship locations...
	}

	/**
	 * This method initializes the resources and coco tiles. Called at the start of the game
	 * when the initial values are all 0.
	 * */
	@Override
	public void initializeResources() {
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

	// This method initializes the Player's assets according to the colour they chose.
	public void initializeAssets() {
		switch (this.colour) {
			case RED :
				// The two initial lair locations for the red player
				this.addLairAsset(" 5 ");
				this.addLairAsset(" 15 ");
				// And their two ship locations...
				this.addShipAsset(" 4 - 5 ");
				this.addShipAsset(" 15 - 16 ");
				break;
			case WHITE :
				this.addLairAsset(" 2 ");
				this.addLairAsset(" 18 ");
				this.addShipAsset(" 2 - 3 ");
				this.addShipAsset(" 17 - 18 ");
				break;
			case ORANGE :
				this.addLairAsset(" 9 ");
				this.addLairAsset(" 25 ");
				this.addShipAsset(" 9 - 10 ");
				this.addShipAsset(" 24 - 25 ");
				break;
			case BLUE :
				this.addLairAsset(" 12 ");
				this.addLairAsset(" 22 ");
				this.addShipAsset(" 11 - 12 ");
				this.addShipAsset(" 22 - 23 ");
				break;
		}
	}
	
	// This method adds a ship to the Player's ship assets.
	public void addShipAsset(String asset) {
		this.shipAssets.add(asset);
	}

	// This method adds a lair to the Player's lair assets.
	public void addLairAsset(String asset) {
		this.lairAssets.add(asset);
	}

	// This method returns a string representation of the Player's lair and ship assets.
	public String assetsToString() {
		return ("You own these lairs: " + this.lairAssets.toString() + "\nYou own these ships: "
				+ this.shipAssets.toString());
	}
	
	/** 
	 * This method checks if a player has a particular number of a resource. Used when building and trading.
	 * 
	 * @param: resourceName - The resource being checked
	 * @param: number - The amount of the resource needed.
	 * 
	 * @return: A boolean indicating if the player has at least the number of the resource. 
	 * */
	@Override
	public boolean isAvailable(String resourceName, int number) {
		if ((this.resources.containsKey(resourceName))
				&& (this.resources.get(resourceName) >= number)) {
			return true;
		} else {
			return false;
		}
	}

	/** 
	 * This method is used when trading with the stockpile.
	 * 
	 * @param: tileIn - The resource received from the stockpile
	 * @param: numIn - The number of the received resource
	 * @param: tileOut - The resource given to the stockpile
	 * @param: numOut - The number of the given resource
	 * 
	 * @return: A string indicating the resources that were given and received.
	 * */
	@Override
	public String trade(String tileIn, int numIn, String tileOut, int numOut) {
		if (!this.isAvailable(tileOut, numOut)) {
			return String.format("There are no '%1$s' tiles in the stockpile to trade with.", tileOut);
		}
		int currNumTileIn = this.resources.get(tileIn);
		int currNumTileOut = this.resources.get(tileOut);
		// Now we update the values associated with the keys...
		this.resources.replace(tileIn, currNumTileIn + numIn);
		this.resources.replace(tileOut, currNumTileOut - numOut);
		return String.format("You've traded a %1$d %2$s for %3$d %4$s.", numIn, tileIn, numOut, tileOut);
	}

	@Override
	public void update(String resource, int num) { // We're using primitive integers here: they are lighter and quicker in most cases...
		this.resources.put(resource, this.resources.get(resource) + (num)); // 'num' could be negative...
	}

	// This method gets the number of Coco tiles that the player has.
	public Integer getNumOfCocoTiles() {
		int num = 0;
		for (Integer value : this.getCocoTiles().values()) {
			num += value;
		}
		return num;
	}

	//This method adds a Coco tile to the Players Map of Coco tiles 
	public void addCocoTile(String cocoTileType) {
		this.cocoTiles.put(cocoTileType, this.cocoTiles.get(cocoTileType) + 1);
	}

	// This method returns a string representation of the Player attributes.
	@Override
	public String toString() {
		String playerResources = "";
		for (String resource : this.resources.keySet()) {
			playerResources = playerResources + "\n" + resource + ": "
					+ this.resources.get(resource);
		}
		return "\nName: " + this.name + "\n\t" + "Colour: " + this.colour + "\n\t" + this.name + "'s lairs:"
				+ this.lairAssets.toString() + "\n\t" + this.name + "'s ships:" + this.shipAssets.toString();
	}

	// 'get' and 'set' methods:
	public String getName() {
		return name;
	}

	public boolean skipResourcesCheckStatus() {
		return this.skipResourcesCheck;
	}

	public void setResourcesCheckStatus(boolean status) {
		this.skipResourcesCheck = status;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PlayerEnums getColour() {
		return this.colour;
	}

	public String getColourIcon() {
		return String.valueOf(this.getColour().getColour().charAt(0)).toLowerCase();
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

	public List<String> getLairAssets() {
		return this.lairAssets;
	}

	public List<String> getShipAssets() {
		return this.shipAssets;
	}
}
