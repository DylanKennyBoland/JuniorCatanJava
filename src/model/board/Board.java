package model.board;

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
	private Integer currentMaxCocoTiles = 0;
	private Player playerWithMostCocoTiles = null;
	private Islands ghostIsland;

	public static Board getInstance() {
		if (gameBoard == null) {
			gameBoard = new Board();
		}
		return gameBoard;
	}

	private Board() {
		this.initializeLairLocations();
//		this.shipSites.add(" 3 - 4 ");
//		this.shipSites.add(" 2 - 3 ");
//		this.shipSites.add(" 27 - 28 ");
//		this.shipSites.add(" 9 - 10 ");
//		this.shipSites.add(" 4 - 5 ");
//		this.shipSites.add(" 5 - 6 ");
//		this.shipSites.add(" 15 - 16 ");
//		this.shipSites.add(" 14 - 15 ");
		this.islands = new ArrayList<Islands>();
		this.marketplace = new Marketplace("The marketplace");
		this.stockpile = new Stockpile("The stockpile");
		this.playerList = PlayerList.getInstance();
		this.setUpCocoTiles();
	}

	private void initializeLairLocations() {
		for (int i = 1; i < 33; i++) {
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
//		this.cocoTiles.put("Build", 0);
//		this.cocoTiles.put("Resource Combination 1", 0);
//		this.cocoTiles.put("Resource Combination 2", 0);
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
//		List<String> usedLairSitesAsInts = new ArrayList<String>();
//		for (String lair : usedLairSites) {
//			usedLairSitesAsInts.add(Integer.valueOf(lair.replace(" ", "")));
//		}
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
