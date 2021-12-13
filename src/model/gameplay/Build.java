package model.gameplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.board.Board;
import model.players.Player;

public class Build {
	private Player player;
	private Board board;
	private String buildChoice;
	private HashMap<String, Integer> playerResources;
	private HashMap<String, Integer> lairCost = new HashMap<String, Integer>();
	private HashMap<String, Integer> shipCost = new HashMap<String, Integer>();
	private ArrayList<String> buildOptions;
	private Scanner inputScanner;

	public Build(Player player, Scanner inputScanner) {
		this.player = player;
		this.board = Board.getInstance();
		this.inputScanner = inputScanner;
		this.buildOptions = new ArrayList<String>();
		this.lairCost.put("Wood", 1);
		this.lairCost.put("Cutlass", 1);
		this.lairCost.put("Molasses", 1);
		this.lairCost.put("Goats", 1);
		this.shipCost.put("Goats", 1);
		this.shipCost.put("Wood", 1);
	}

	public boolean checkResources(String buildChoice) {
		if (buildChoice.contains("Ship")) {
			if (player.isAvailable("Wood", 1) && player.isAvailable("Goats", 1)) {
				return true;
			} else {
				return false;
			}
		} else if (buildChoice.contains("Lair")) {
			if (player.isAvailable("Cutlass", 1) && player.isAvailable("Molasses", 1) && player.isAvailable("Goats", 1)
					&& player.isAvailable("Wood", 1)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public String buildLair(String lair) {
		this.buildChoice = "Lair";
		this.player.addLairAsset(lair);
		this.player.takeResource("Cutlass", 1);
		this.player.takeResource("Molasses", 1);
		this.player.takeResource("Goats", 1);
		this.player.takeResource("Wood", 1);
		this.getBoard().getStockpile().updateStockPile("Cutlass", 1);
		this.getBoard().getStockpile().updateStockPile("Molasses", 1);
		this.getBoard().getStockpile().updateStockPile("Goats", 1);
		this.getBoard().getStockpile().updateStockPile("Wood", 1);
		return("Done!\n");
	}

	public String buildShip(String ship) {
		this.player.addShipAsset(ship);
		this.player.takeResource("Goats", 1);
		this.player.takeResource("Wood", 1);
		this.getBoard().getStockpile().updateStockPile("Goats", 1);
		this.getBoard().getStockpile().updateStockPile("Wood", 1);
		return("Done!\n");
	}

	private void swap(HashMap<String, Integer> costs, String asset, String type) {
		if (type == "Ship") {
			player.addShipAsset(asset);
		} else {
			player.addLairAsset(asset);
		}
		for (Map.Entry cost : costs.entrySet()) {
			player.takeResource((String) cost.getKey(), (Integer) cost.getValue());
		}

	}

	public List<String> validLairSites() {
		List<String> allLairSites = new ArrayList<String>(this.getBoard().getLairList());
		allLairSites.removeAll(this.getBoard().getOccupiedLairs());
		List<String> validLairSites = new ArrayList<String>();
		for (String lair : allLairSites) {
			for (String shipSite : this.player.getShipAssets()) {
				if (shipSite.contains(lair)) {
					validLairSites.add(lair);
				}
			}
		}
		return validLairSites;
	}

	public List<String> validShipSites() {
		List<String> allShipSites = new ArrayList<String>(this.getBoard().getShipSites());
		allShipSites.removeAll(this.getBoard().getUsedShipSites());
		List<String> freeShipSites = new ArrayList<String>(allShipSites);
		List<String> validShipSites = new ArrayList<String>();
		for (String lair : this.player.getLairAssets()) {
			for (String shipSite : freeShipSites) {
				if (shipSite.contains(lair)) {
					validShipSites.add(shipSite);
				}
			}
		}
		return validShipSites;
	}

	public Board getBoard() {
		return this.board;
	}

	private boolean canBuild(String location) {
		for (String asset : player.getLairAssets()) {
			if (asset.contains(" " + location + " ")) {
				return true;
			}
		}
		return false;
	}
}
