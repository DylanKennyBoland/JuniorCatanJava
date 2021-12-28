package model.board;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.players.Player;
import model.players.PlayerList;

/**
 * Class for the Board Object. This is a Singleton object as there is only one board in the Game.
 * 
 * @author: Adam Durning & Dylan Boland
 * @Date: 27/12/2021
 * 
 */

public class Board {
	// Setting up the Board variables. These will be explained in comments below.
	private static Board gameBoard;
	private List<String> lairLocations = new ArrayList<String>();
	private List<String> shipSites = new ArrayList<String>();
	private List<Islands> islands;
	private Marketplace marketplace;
	private Stockpile stockpile;
	private PlayerList playerList;
	private Integer initialNumCocoTiles = 17;
	private Map<String, Integer> cocoTiles = new HashMap<String, Integer>();
	private Map<String, String> boardStatus = new HashMap<String, String>();
	private Integer currentMaxCocoTiles = 0;
	private Player playerWithMostCocoTiles = null;
	private Islands ghostIsland;
	protected String boardConfig;

	/** A method for getting the instance of the Board object. If an instance does
	 *  not exist than one is created.
	 */
	public static Board getInstance() {
		if (gameBoard == null) {
			gameBoard = new Board();
		}
		return gameBoard;
	}

	/**
	 * The Board constructor.
	 */
	private Board() {
		this.initializeLairLocations();
		this.islands = new ArrayList<Islands>();
		this.marketplace = new Marketplace("The marketplace");
		this.stockpile = new Stockpile("The stockpile");
		this.playerList = PlayerList.getInstance();
		this.setUpCocoTiles();
		try {
			this.readInBoardTemplate();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void updateBoard(String position, String status) { // Method for updating the board status map...
		this.boardStatus.replace(position, status);
	}
	
	public void initializeBoardStatus() {
		for (String lair : this.lairLocations) {
			this.boardStatus.put(lair, lair.replaceAll(" ", ""));
		}
		for (String site : this.shipSites) {
			this.boardStatus.put(site, "+");
		}
		this.boardStatus.put(" 33 ", "33");
		System.out.println(this.boardStatus.size());
		/* Updating the board configuration string with information
		 * about which lair and ship sites are occupied from the start
		 * of the game - we know the initial lair and ship sites associated
		 * with each colour.
		 */
		for (Player player : this.getPlayerList().getList()) {
			for (String lair : player.getLairAssets()) {
				this.updateBoard(lair, player.getColourIcon()); // Change the value to be a single capital letter indicating the colour...
			}
			for (String site : player.getShipAssets()) {
				this.updateBoard(site, player.getColourIcon());
			}
		}
	}
	
	// This method initializes the list of lair locations on the board.
	private void initializeLairLocations() {
		for (int i = 1; i < 33; i++) {
			this.lairLocations.add(" " + String.valueOf(i) + " ");
		}
	}

	// This method initializes the number of each Coco Tile in the game.
	public void initializeCocoTiles() {
		this.cocoTiles.put("Ghost Captain", this.initialNumCocoTiles);
		this.cocoTiles.put("Build", this.initialNumCocoTiles);
		this.cocoTiles.put("Resource Combination 1", this.initialNumCocoTiles);
		this.cocoTiles.put("Resource Combination 2", this.initialNumCocoTiles);
	}
	
	// This method updates the list of ship sites according to the ship sites for each island on the board.
	public void setupShipSites() {
		for (Islands island : this.islands) {
			this.shipSites.removeAll(island.getAttachedShipSites());
			this.shipSites.addAll(island.getAttachedShipSites());
		}
	}
	
	/**
	 * This method produces the Island Resources according to the dice number that is rolled.
	 * 
	 * @param: roll - An integer representing the number rolled that turn.
	 * 
	 * @return: A string indicating the resources given to each player and/or if an island cannot produce
	 *			if the island has the ghost captain (GC). 
	 */
	public String produceResources(Integer roll) {
		String string = "";
		for (Islands island : islands) {
			//boolean isSpooky = ;
			// Checks if the islands dice number matches the rolled number and if the island does not have the GC.
			if (island.getDiceNumber() == roll && !island.hasGhostCaptain()) {
				for (Player player : playerList.getList()) {
					List<String> playerLairAssets = player.getLairAssets();
					List<String> islandLairs = island.getAttachedLairs();
					// Checks if the Player has any lairs surrounding the Island.
					// The disjoint method checks if the two inputted lists have no elements in common.
					if (!Collections.disjoint(playerLairAssets, islandLairs)) {
						// Getting the number of lairs the Player has surrounding the Island
						Integer numOfAttachedLairs = playerLairAssets.stream().distinct().filter(islandLairs::contains)
								.collect(Collectors.toList()).size();
						String resource = island.getIslandResource();
						// The player gets as many resources as they have lairs surrounding the Island.
						player.giveResource(resource, numOfAttachedLairs);
						// Removing the resources from the stockpile
						this.stockpile.updateStockPile(resource, -numOfAttachedLairs);
						string += ("\nGiving " + resource + " to " + player.getName());
					}
				}
			// If the island has the GC, it cannot produce resources.
			} else if(island.getDiceNumber() == roll && island.hasGhostCaptain()) {
				string += ("\nIsland " + island.getName() + " cannot produce!");
			}
		}
	}
	
	public void readInBoardTemplate() throws IOException {
		try {
			/* We read this string in once at the beginning of the game, and then
			 * we format it with information about which lair and ship sites are occupied
			 * and which are free. This string is then passed to a calling method which
			 * will print it - in this way, only ONE call to System.out.print() occurs.
			 * If we wants to change something about the board, then they simply change
			 * the template file (boardTemplate.txt)...
			 */
			this.boardConfig = Files.readString(Path.of("src/model/board/boardTemplate.txt"));
			/* The replacements below create placeholders in the boardConfig
			 * string, which will then allow us to format the string.
			 */
			this.boardConfig = this.boardConfig.replace("2s", "%2s"); // Some lair locations are double digit numbers - hence the '2'...
			this.boardConfig = this.boardConfig.replace(" s", " %s");
			System.out.println(this.boardConfig);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public String getBoardConfig() {
		Map<String, String> v = this.boardStatus; // 'v' is a much shorter reference when compared to 'this.boardStatus'...
		/* In the long run, a more structured approach which allows for this to be
		 * done in a for-loop would be better.
		 */
		this.boardConfig = String.format(this.boardConfig, v.get(" 6 "), v.get(" 8 "),
				v.get(" 5 - 6 "), v.get(" 6 - 7 "), v.get(" 7 - 8 "), v.get(" 8 - 9 "),
				v.get(" 5 "), v.get(" 7 "), v.get(" 9 "), v.get(" 4 - 5 "), v.get(" 7 - 28 "),
				v.get(" 9 - 10 "), v.get(" 2 "), v.get(" 4 "), v.get(" 28 "), v.get(" 10 "), 
				v.get(" 12 "), v.get(" 1 - 2 "), v.get(" 2 - 3 "), v.get(" 3 - 4 "), v.get(" 4 - 27 "),
				v.get(" 27 - 28 "), v.get(" 28 - 29 "), v.get(" 10 - 29 "), v.get(" 10 - 11 "),
				v.get(" 11 - 12 "), v.get(" 12 - 13 "), v.get(" 1 "), v.get(" 3 "), v.get(" 27 "),
				v.get(" 29 "), v.get(" 11 "), v.get(" 13 "), v.get(" 1 - 26 "), v.get(" 3 - 24 "), v.get(" 27 - 32 "),
				v.get(" 29 - 30 "), v.get(" 11 - 16 "), v.get(" 13 - 14 "), v.get(" 26 "), v.get(" 24 "), v.get(" 32 "),
				v.get(" 30 "), v.get(" 16 "), v.get(" 14 "), v.get(" 25 - 26 "), v.get(" 24 - 25 "), v.get(" 23 - 24 "),
				v.get(" 23 - 32 "), v.get(" 31 - 32 "), v.get(" 30 - 31 "), v.get(" 17 - 30 "), v.get(" 16 - 17 "),
				v.get(" 15 - 16 "), v.get(" 14 - 15 "), v.get(" 25 "), v.get(" 23 "), v.get(" 31 "), v.get(" 17 "),
				v.get(" 15 "), v.get(" 22 - 23 "), v.get(" 20 - 31 "), v.get(" 17 - 18 "), v.get(" 22 "), v.get(" 20 "),
				v.get(" 18 "), v.get(" 21 - 22 "), v.get(" 20 - 21 "), v.get(" 19 - 20 "), v.get(" 18 - 19 "),
				v.get(" 21 "), v.get(" 19 "));
		return this.boardConfig; // returning the string...
	}

	public void setUpCocoTiles() {
		this.cocoTiles.put("Ghost Captain", this.initialNumCocoTiles);
		this.cocoTiles.put("Build", this.initialNumCocoTiles);
		this.cocoTiles.put("Resource Combination 1", this.initialNumCocoTiles);
		this.cocoTiles.put("Resource Combination 2", this.initialNumCocoTiles);
	}
	
	// This method removes a Coco tile from the boards Map of Coco tiles. Used when someone builds a Coco tile.
	public void removeCocoTile(String cocoTileType) {
		this.cocoTiles.put(cocoTileType, this.cocoTiles.get(cocoTileType) - 1);
	}

//	// This method checks if any Player has the inputted location. Used for checking if a lair site can be built on.
//	public boolean isLairAvailable(String location) {
//		for (Player player : playerList.getList()) {
//			if (player.getLairAssets().contains(location)) {
//				return false;
//			}
//		}
//		return true;
//	}

	// Increments the currentMaxCocoTiles which is a variable indicating the current largest number of Coco tiles one of the players has.
	public void incrementCurrentMaxCocoTiles() {
		this.currentMaxCocoTiles = this.currentMaxCocoTiles + 1;
	}

//	//
//	public void updateLairLocations(String location) {
//		this.lairLocations.remove(this.lairLocations.indexOf(location));
//	}
	
	// This method moves the GC to the island with the name islandName
	public void moveGhostCaptain(char islandName) {
		this.islands.get(islands.indexOf(this.ghostIsland)).setGhostCaptain(false);
		Islands newGhostIsland = getIslandByName(islandName);
		newGhostIsland.setGhostCaptain(true);
		// The ghostIsland variable contains the island that currently has the GC.
		this.ghostIsland = newGhostIsland;
	}
	
	// This method returns a list of Ship sites that have been built on.
	public List<String> getUsedShipSites() {
		List<String> usedShipSites = new ArrayList<String>();
		for (Player player : this.getPlayerList().getList()) {
			usedShipSites.addAll(player.getShipAssets());
		}
		return usedShipSites;
	}
	
	// This method returns a list of Lair sites that have been built on.
	public List<String> getUsedLairSites() {
		List<String> usedLairSites = new ArrayList<String>();
		for (Player player : this.getPlayerList().getList()) {
			usedLairSites.addAll(player.getLairAssets());
		}
		return usedLairSites;
	}
	
	public String getIslandInfo() {
		String islandInfo = "";
		for (Islands island : islands) {
			islandInfo += island.toString() + "\n";
		}
		return islandInfo;
	}
	
	// This method returns an Island object given the island Name.
	public Islands getIslandByName(char islandName) {
		for (Islands island : this.islands) {
			if (island.getName() == islandName) {
				return island;
			}
		}
		return this.ghostIsland;
	}
	
	// This method gets the number of Coco Tiles that can be built.
	public Integer getNumOfCocoTiles() {
		int num = 0;
		for (Integer value : this.getCocoTiles().values()) {
			num += value;
		}
		return num;
	}
	
	//Some Getter and Setter methods for the Board variables.
	public Map<String, Integer> getCocoTiles() {
		return this.cocoTiles;
	}
	
	public PlayerList getPlayerList() {
		return this.playerList;
	}

	public Marketplace getMarketplace() {
		return this.marketplace;
	}

	public Stockpile getStockpile() {
		return this.stockpile;
	}

	public List<Islands> getIslands() {
		return this.islands;
	}

	public List<String> getShipSites() {
		return this.shipSites;
	}

	public List<String> getLairList() {
		return this.lairLocations;
	}

	public Integer getCurrentMaxCocoTiles() {
		return this.currentMaxCocoTiles;
	}

	public Islands getGhostIsland() {
		return this.ghostIsland;
	}

	public void setIslands(List<Islands> islands) {
		this.islands = islands;
	}

	public void setGhostIsland(Islands island) {
		this.ghostIsland = island;
	}

	public void setPlayerWithMaxCocoTiles(Player player) {
		this.playerWithMostCocoTiles = player;
	}
}
