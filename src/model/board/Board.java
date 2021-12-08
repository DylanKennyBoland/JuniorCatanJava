package model.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import model.players.Player;
import model.players.PlayerList;

public class Board {

	private static Board gameBoard;
	private List<Integer> lairLocations;
	private List<String> shipSites = new ArrayList<String>();
	private List<Islands> islands;
	private Marketplace marketplace;
	private Stockpile stockpile;
	private PlayerList playerList;
	private Integer initialNumCocoTiles = 18;
	private Map<String, Integer> cocoTiles = new HashMap<String, Integer>();
	private Integer currentMaxCocoTiles = 0;
	private Player playerWithMostCocoTiles;
	private Islands ghostIsland;

	public static Board getInstance() {
		if (gameBoard == null) {
			gameBoard = new Board();
		}
		return gameBoard;
	}

	private Board() {
		this.lairLocations = IntStream.rangeClosed(1, 32).boxed().collect(Collectors.toList());
		this.shipSites.add(" 3 - 4 ");
		this.shipSites.add(" 2 - 3 ");
		this.shipSites.add(" 27 - 28 ");
		this.shipSites.add(" 9 - 10 ");
		this.shipSites.add(" 4 - 5 ");
		this.shipSites.add(" 5 - 6 ");
		this.shipSites.add(" 15 - 16 ");
		this.shipSites.add(" 14 - 15 ");
		this.islands = new ArrayList<Islands>();
		this.shipLocations = new ArrayList<String>();
		this.marketplace = new Marketplace("The marketplace");
		this.stockpile = new Stockpile("The stockpile");
		this.playerList = PlayerList.getInstance();
		this.setUpCocoTiles();
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

	public List<Integer> getOccupiedLairs() {
		List<String> usedLairSites = new ArrayList<String>();
		for (Player player : this.getPlayerList().getList()) {
			usedLairSites.addAll(player.getLairAssets());
		}
		List<Integer> usedLairSitesAsInts = new ArrayList<Integer>();
		for (String lair : usedLairSites) {
			usedLairSitesAsInts.add(Integer.valueOf(lair.replace(" ", "")));
		}
		return usedLairSitesAsInts;
	}

	public void setIslands(List<Islands> islands) {
		this.islands = islands;
	}
	
	public void setGhostIsland(Islands island) {
		this.ghostIsland = island;
	}
	
	public Islands getGhostIsland() {
		return this.ghostIsland;
	}

	public boolean isLairAvailable(String location) {
		for (Player player : playerList.getList()) {
			if (player.getLairAssets().contains(location)) {
				System.out.println("Lair location is not available");
				return false;
			}
		}
		return true;
	}

	public void incrementCurrentMaxCocoTiles() {
		this.currentMaxCocoTiles = this.currentMaxCocoTiles + 1;
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

	public void setShipLocations() {
		for(Islands island: islands) {
			this.shipLocations.removeAll(island.getAttachedShipSites());
			this.shipLocations.addAll(island.getAttachedShipSites());
		}
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
		for(Islands island: this.islands) {
			if(island.getName().contains(islandName)){
				return island;
			}
		}
		return this.ghostIsland;
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

	public List<Integer> getLairList() {
		return this.lairLocations;
	}
}
