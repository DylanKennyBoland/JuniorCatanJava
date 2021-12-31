package model.board;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.players.Player;
import model.players.PlayerList;

public class Board {

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
	protected String boardTemplate;

	public static Board getInstance() {
		if (gameBoard == null) {
			gameBoard = new Board();
		}
		return gameBoard;
	}

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
		this.getBoardStatus().replace(position, status);
	}
	
	public void initializeBoardStatus() {
		for (String lair : this.lairLocations) {
			this.boardStatus.put(lair, lair.replaceAll(" ", ""));
		}
		for (String site : this.shipSites) {
			this.boardStatus.put(site, "+");
		}
		this.boardStatus.put(" 33 ", "33");
		/* Updating the board configuration string with information
		 * about which lair and ship sites are occupied from the start
		 * of the game - we know the initial lair and ship sites associated
		 * with each colour.
		 */
		for (Player player : this.getPlayerList().getList()) {
			for (String lair : player.getLairAssets()) {
				this.updateBoard(lair, player.getColourIcon()); // Change the value to be a single capital letter indicating the player's colour...
			}
			for (String site : player.getShipAssets()) {
				this.updateBoard(site, player.getColourIcon().toUpperCase());
			}
		}
	}
	
	public void readInBoardTemplate() throws IOException {
		try {
			/* We read this string in once at the beginning of the game, and then
			 * we format it with information about which lair and ship sites are occupied
			 * and which are free. This string is then passed to a calling method which
			 * will print it - in this way, only ONE call to System.out.print() occurs.
			 * If one wants to change something about the board, then they simply change
			 * the template file (boardTemplate.txt)...
			 */
			this.boardTemplate = Files.readString(Path.of("src/model/board/boardTemplate.txt"));
			/* The replacements below create placeholders in the boardConfig
			 * string, which will then allow us to format the string.
			 */
			this.boardTemplate = this.boardTemplate.replace("2s", "%2s"); // Some lair locations are double digit numbers - hence the '2'...
			this.boardTemplate = this.boardTemplate.replace(" s", " %s");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public String getBoardConfig() {
		Map<String, String> v = this.getBoardStatus(); // 'v' is a much shorter reference when compared to 'this.boardStatus'...
		/* In the long run, a more structured approach which allows for this to be
		 * done in a for-loop would be better.
		 */
		String boardConfig = this.boardTemplate;
		boardConfig = String.format(boardConfig, v.get(" 6 "), v.get(" 8 "),
				v.get(" 5 - 6 "), v.get(" 6 - 7 "), v.get(" 7 - 8 "), v.get(" 8 - 9 "),
				v.get(" 5 "), v.get(" 7 "), v.get(" 9 "), v.get(" 4 - 5 "), v.get(" 7 - 28 "),
				v.get(" 9 - 10 "), v.get(" 2 "), v.get(" 4 "), v.get(" 28 "), v.get(" 10 "), 
				v.get(" 12 "), v.get(" 1 - 2 "), v.get(" 2 - 3 "), v.get(" 3 - 4 "), v.get(" 4 - 27 "),
				v.get(" 27 - 28 "), v.get(" 28 - 29 "), v.get(" 10 - 29 "), v.get(" 10 - 11 "),
				v.get(" 11 - 12 "), v.get(" 12 - 13 "), v.get(" 1 "), v.get(" 3 "), v.get(" 27 "),
				v.get(" 29 "), v.get(" 11 "), v.get(" 13 "), v.get(" 1 - 26 "), v.get(" 3 - 24 "), v.get(" 27 - 32 "),
				v.get(" 29 - 30 "), v.get(" 11 - 16 "), v.get(" 13 - 14 "), v.get(" 33 "), v.get(" 26 "), v.get(" 24 "), v.get(" 32 "),
				v.get(" 30 "), v.get(" 16 "), v.get(" 14 "), v.get(" 25 - 26 "), v.get(" 24 - 25 "), v.get(" 23 - 24 "),
				v.get(" 23 - 32 "), v.get(" 31 - 32 "), v.get(" 30 - 31 "), v.get(" 17 - 30 "), v.get(" 16 - 17 "),
				v.get(" 15 - 16 "), v.get(" 14 - 15 "), v.get(" 25 "), v.get(" 23 "), v.get(" 31 "), v.get(" 17 "),
				v.get(" 15 "), v.get(" 22 - 23 "), v.get(" 20 - 31 "), v.get(" 17 - 18 "), v.get(" 22 "), v.get(" 20 "),
				v.get(" 18 "), v.get(" 21 - 22 "), v.get(" 20 - 21 "), v.get(" 19 - 20 "), v.get(" 18 - 19 "),
				v.get(" 21 "), v.get(" 19 "));
		return boardConfig; // returning the string...
	}
	
	public Map<String, String> getBoardStatus() {
		return this.boardStatus;
	}
	
	private void initializeLairLocations() {
		for (int i = 1; i < 34; i++) {
			this.lairLocations.add(" " + String.valueOf(i) + " ");
		}
	}

	public void setupShipSites() {
		for (Islands island : this.islands) {
			this.shipSites.removeAll(island.getAttachedShipSites());
			this.shipSites.addAll(island.getAttachedShipSites());
		}
	}

	public void setUpCocoTiles() {
		this.cocoTiles.put("Ghost Captain", this.initialNumCocoTiles);
		this.cocoTiles.put("Build", this.initialNumCocoTiles);
		this.cocoTiles.put("Resource Combination 1", this.initialNumCocoTiles);
		this.cocoTiles.put("Resource Combination 2", this.initialNumCocoTiles);
	}

	public List<String> getUsedShipSites() {
		List<String> usedShipSites = new ArrayList<String>();
		for (Player player : this.getPlayerList().getList()) {
			usedShipSites.addAll(player.getShipAssets());
		}
		return usedShipSites;
	}

	public List<String> getOccupiedLairs() {
		List<String> usedLairSites = new ArrayList<String>();
		for (Player player : this.getPlayerList().getList()) {
			usedLairSites.addAll(player.getLairAssets());
		}
		return usedLairSites;
	}
	
	public void setIslands(List<Islands> islands) {
		this.islands = islands;
	}

	public void setGhostIsland(Islands island) {
		this.ghostIsland = island;
	}

	public Map<String, Integer> getCocoTiles() {
		return this.cocoTiles;
	}

	public void removeCocoTile(String cocoTileType) {
		this.cocoTiles.put(cocoTileType, this.cocoTiles.get(cocoTileType) - 1);
	}

	public Islands getGhostIsland() {
		return this.ghostIsland;
	}

	public boolean isLairAvailable(String location) {
		for (Player player : playerList.getList()) {
			if (player.getLairAssets().contains(location)) {
				return false;
			}
		}
		return true;
	}

	public void incrementCurrentMaxCocoTiles() {
		this.currentMaxCocoTiles = this.currentMaxCocoTiles + 1;
	}
	
	public Player getPlayerWithMaxCocoTiles() {
		return this.playerWithMostCocoTiles;
	}

	public void setPlayerWithMaxCocoTiles(Player player) {
		this.playerWithMostCocoTiles = player;
	}

	public void updateLairLocations(String location) {
		this.lairLocations.remove(this.lairLocations.indexOf(location));
	}

	public Integer getCurrentMaxCocoTiles() {
		return this.currentMaxCocoTiles;
	}

	public String getIslandInfo() {
		String islandInfo = "";
		for (Islands island : islands) {
			islandInfo += island.toString() + "\n";
		}
		return islandInfo;
	}

	public void moveGhostCaptain(String islandName) {
		this.islands.get(islands.indexOf(this.ghostIsland)).setGhostCaptain(false);
		Islands newGhostIsland = getIslandByName(islandName);
		newGhostIsland.setGhostCaptain(true);
		this.ghostIsland = newGhostIsland;
	}

	public Islands getIslandByName(String islandName) {
		for (Islands island : this.islands) {
			if (island.getName().contains(islandName)) {
				return island;
			}
		}
		return this.ghostIsland;
	}

	public Integer getNumOfCocoTiles() {
		int num = 0;
		for (Integer value : this.getCocoTiles().values()) {
			num += value;
		}
		return num;
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
}
