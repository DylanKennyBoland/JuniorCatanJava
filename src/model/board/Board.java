package model.board;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

	private static Board gameBoard;
	private List<Integer> lairLocations;
	private List<String> shipLocations = new ArrayList<String>();
	private List<Islands> islands;
	private Marketplace marketplace;
	private Stockpile stockpile;
	private PlayerList playerList;

	public static Board getInstance() {
		if (gameBoard == null) {
			gameBoard = new Board();
		}
		return gameBoard;
	}

	private Board() {
		this.lairLocations = IntStream.rangeClosed(1, 32).boxed().collect(Collectors.toList());
		this.shipLocations.add(" 3 - 4 ");
		this.shipLocations.add(" 2 - 3 ");
		this.shipLocations.add(" 27 - 28 ");
		this.shipLocations.add(" 9 - 10 ");
		this.shipLocations.add(" 4 - 5 ");
		this.shipLocations.add(" 5 - 6 ");
		this.shipLocations.add(" 15 - 16 ");
		this.shipLocations.add(" 14 - 15 ");
		this.islands = new ArrayList<Islands>();
		this.marketplace = new Marketplace("The marketplace");
		this.stockpile = new Stockpile("The stockpile");
		this.playerList = PlayerList.getInstance();
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

	public boolean isLairAvailable(String location) {
		for (Player player : playerList.getList()) {
			if (player.getLairAssets().contains(location)) {
				System.out.println("Lair location is not available");
				return false;
			}
		}
		return true;
	}

	public void updateLairLocations(String location) {
		this.lairLocations.remove(this.lairLocations.indexOf(location));
	}

	public String getIslandInfo() {
		String islandInfo = "";
		for (Islands island : islands) {
			islandInfo += island.toString() + "\n";
		}
		return islandInfo;
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

	public List<String> getShipLocations() {
		return this.shipLocations;
	}

	public List<Integer> getLairList() {
		return this.lairLocations;
	}
}
