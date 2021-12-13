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
//		List<String> validLairSites = new ArrayList<String>(this.validLairSites());
//		if (validLairSites.size() == 0) {
//			return("You currently have no valid lair sites to build on!\n");
//		}
//		if (!this.checkResources()) {
//			return("You do not have enough resources");
//		}
//		return("Where would you like to build a lair? Your options are: ");
//		int i = 0;
//		for ( lair : this.validLairSites()) {
//			i++;
//			return("Option " + (i) + ": " + lair);
//		}
//		boolean validInput = false;
//		while (!validInput) {
//			System.out.print("\nEnter here: ");
//			Integer option = inputScanner.nextInt();
//			inputScanner.nextLine();
//			if ((option >= 0) && (option <= this.validLairSites().size())) {
//				System.out.print("You have chosen option " + option);
//				System.out.print("Building... ");
		this.player.addLairAsset(lair);
//				System.out.print("Done!\n");
		this.player.takeResource("Cutlass", 1);
		this.player.takeResource("Molasses", 1);
		this.player.takeResource("Goats", 1);
		this.player.takeResource("Wood", 1);
		this.getBoard().getStockpile().updateStockPile("Cutlass", 1);
		this.getBoard().getStockpile().updateStockPile("Molasses", 1);
		this.getBoard().getStockpile().updateStockPile("Goats", 1);
		this.getBoard().getStockpile().updateStockPile("Wood", 1);
		return ("Done!\n");
//				validInput = true;
//			} else {
//				System.out.println(
//						"You have input " + option + " which is outside the range of options. Please re-enter.");
//			}
//		}
		// playerResources = player.getResources();
//		if (board.isLairAvailable(lairLocation) && checkResources()) {
//			swap(this.lairCost, lairLocation, "Lair");
//			System.out.println("You now own lair " + lairLocation);
	}

	public String buildShip(String ship) {
//		this.buildChoice = "Ship";
//		List<String> validShipSites = new ArrayList<String>(this.validShipSites());
//		if (validShipSites.size() == 0) {
//			System.out.println("You currently have no valid ship sites to build on!\n");
//			return;
//		}
//		if (!this.checkResources("Ship")) {
//			System.out.println("You do not have enough resources!");
//			return;
//		}
//		System.out.println("Where would you like to build a ship? Your options are: ");
//		int n = 0;
//		for (String site : validShipSites) {
//			n++;
//			System.out.println((n) + ": " + site);
//		}
>>>>>>> master
//		boolean validInput = false;
//		while (!validInput) {
//			System.out.println("\nEnter here: ");
//			Integer option = inputScanner.nextInt();
//			inputScanner.nextLine();
<<<<<<< HEAD
//			if (option == (i + 1)) {
//				System.out.println("Canceling build...");
//				validInput = true;
//				continue;
//			}
//			if ((option >= 1) && (option <= this.validLairSites().size())) {
//				System.out.println("You have chosen option " + option);
//				System.out.println("Building... ");
//				this.player.addLairAsset(String.valueOf(" " + this.validLairSites().get(option - 1) + " "));
//				System.out.println("Done!\n");
//				this.player.takeResource("Cutlass", 1);
//				this.player.takeResource("Molasses", 1);
//				this.player.takeResource("Goats", 1);
//				this.player.takeResource("Wood", 1);
//				this.getBoard().getStockpile().updateStockPile("Cutlass", 1);
//				this.getBoard().getStockpile().updateStockPile("Molasses", 1);
//				this.getBoard().getStockpile().updateStockPile("Goats", 1);
//				this.getBoard().getStockpile().updateStockPile("Wood", 1);
//				validInput = true;
//			} else {
//				System.out.println("You have input " + option
//						+ " which is outside the range of options. Please enter a valid option number.");
//			}
//		}
//		// playerResources = player.getResources();
////		if (board.isLairAvailable(lairLocation) && checkResources()) {
////			swap(this.lairCost, lairLocation, "Lair");
////			System.out.println("You now own lair " + lairLocation);
//	}

	public void buildShip() {
		this.buildChoice = "Ship";
		List<String> validShipSites = new ArrayList<String>(this.validShipSites());
		if (validShipSites.size() == 0) {
			System.out.println("You currently have no valid ship sites to build on!\n");
			return;
		}

		if (this.checkResources()) {
			return;
		}

//		if (!this.checkResources()) {
//			System.out.println("You do not have enough resources!");
//			return;
//		}

		System.out.println("Where would you like to build a ship? Your options are: ");
		int n = 0;
		for (String site : validShipSites) {
			n++;
			System.out.println((n) + ": " + site);
		}
		System.out.println("\n" + (n + 1) + ": cancel build");
		boolean validInput = false;
		while (!validInput) {
			System.out.println("\nEnter here: ");
			Integer option = inputScanner.nextInt();
			inputScanner.nextLine();
			if (option == (n + 1)) {
				System.out.println("Canceling build...");
				validInput = true;
				continue;
			}
			if ((option >= 1) && (option <= validShipSites.size())) {
				System.out.println("You have chosen option " + option);
				System.out.println("Building... ");
				this.player.addShipAsset(validShipSites.get(option - 1));
				System.out.println("Done!\n");
				this.player.takeResource("Goats", 1);
				this.player.takeResource("Wood", 1);
				this.getBoard().getStockpile().updateStockPile("Goats", 1);
				this.getBoard().getStockpile().updateStockPile("Wood", 1);
//				validInput = true;
//			} else {
//				System.out.println(
//						"You have input " + option + " which is outside the range of options. Please re-enter.");
//			}
//		}
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

//	public List<String> getMatches(List<String> lairs, List<String> freeShipSites) {
//		List<String> matches = new ArrayList<String>();
//		for (String lair : lairs) {
//			for (String shipSite : freeShipSites) {
//				if (shipSite.contains(lair)) {
//					matches.add(shipSite);
//				}
//			}
//		}
//		return matches;
//	}

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
//		List<String> validShipSites = new ArrayList<String>(
//				this.getMatches(this.player.getLairAssets(), freeShipSites));
//		return validShipSites;
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
