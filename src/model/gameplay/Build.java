package model.gameplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.board.Board;
import model.board.Player;

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
		// getBuildOptions();
		this.lairCost.put("Wood", 1);
		this.lairCost.put("Cutlass", 1);
		this.lairCost.put("Molasses", 1);
		this.lairCost.put("Goats", 1);
		this.shipCost.put("Goats", 1);
		this.shipCost.put("Wood", 1);
	}

//	private void getBuildOptions(){
//		ArrayList<String> playerAssets = this.player.getAssets();
//
//
//		buildOptions.add()
//
//	}

	public boolean checkResources() {
		if (player.isAvailable("Wood", 1) && player.isAvailable("Goats", 1)) {
			if (this.buildChoice.contains("Ship")) {
				return true;
			} else if (player.isAvailable("Cutlass", 1) && player.isAvailable("Molasses", 1)) {
				return true;
			}
		}
		System.out.println("you do not have enough resources");
		return false;
	}

	public void buildLair() {
		System.out.println("Where would you like to build a lair? (1-32)");
		String lairLocation = inputScanner.nextLine();
		this.buildChoice = "Lair";
		// playerResources = player.getResources();
		if (board.isLairAvailable(lairLocation) && checkResources()) {
			swap(this.lairCost, lairLocation, "Lair");
			System.out.println("You now own lair " + lairLocation);
		}
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

	public List<String> getMatches(List<String> lairs, List<String> freeShipSites) {
		List<String> matches = new ArrayList<String>();
		for (String lair : lairs) {
			for (String shipSite : freeShipSites) {
				if (shipSite.contains(lair)) {
					matches.add(shipSite);
				}
			}
		}
		return matches;
	}

	public List<String> availableShipLocations() {
		List<String> allShipSites = new ArrayList<String>(this.getBoard().getShipLocations());
		allShipSites.removeAll(this.getBoard().getUsedShipSites());
		List<String> freeShipSites = new ArrayList<String>(allShipSites);
		List<String> validShipSites = new ArrayList<String>(
				this.getMatches(this.player.getLairAssets(), freeShipSites));
		return validShipSites;
	}

	public void buildShip() {
		List<String> validShipSites = new ArrayList<String>(this.availableShipLocations());
		if (validShipSites.size() == 0) {
			System.out.println("You currently have no valid ship sites to build on!");
			return;
		}
		System.out.println("Where would you like to build a ship? Your options are: ");
		int n = 0;
		for (String site : validShipSites) {
			n++;
			System.out.println((n) + ": " + site);
		}
		this.buildChoice = "Ship";
		boolean validInput = false;
		while (!validInput) {
			System.out.println("\nEnter here: ");
			Integer shipSite = inputScanner.nextInt();
			inputScanner.nextLine();
			if ((shipSite >= 0) && (shipSite <= validShipSites.size())) {
				System.out.println("You have chosen ship site " + shipSite);
				System.out.println("Building... ");
				this.player.addShipAsset(validShipSites.get(shipSite - 1));
				System.out.println("Done!");
				validInput = true;
			} else {
				System.out.println(
						"You have input " + shipSite + " which is outside the range of options. Please reenter.");
			}
		}
		System.out.println("Method done.");
	}

	public Board getBoard() {
		return this.board;
	}
}
