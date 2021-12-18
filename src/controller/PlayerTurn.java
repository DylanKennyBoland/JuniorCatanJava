package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.board.Board;
import model.board.Islands;
import model.board.Marketplace;
import model.board.Stockpile;
import model.gameplay.Build;
import model.gameplay.Trade;
import model.players.Player;
import model.players.PlayerList;
import view.View;

public class PlayerTurn {
	private Player player;
	private Board board;
	private Stockpile stockpile;
	private Marketplace marketplace;
	private List<Islands> islandList;
	private PlayerList playerList;
	private Scanner inputScanner;
	private Build buildOptions;
	private Trade tradeOptions;
	private Random random = new Random();
	private View view;

	public PlayerTurn(Player player, Scanner inputScanner) {
		this.player = player;
		this.board = Board.getInstance();
		this.stockpile = board.getStockpile();
		this.marketplace = board.getMarketplace();
		this.islandList = board.getIslands();
		this.inputScanner = inputScanner;
		this.buildOptions = new Build(player, inputScanner);
		this.tradeOptions = new Trade(player, inputScanner);
		this.playerList = PlayerList.getInstance();
		this.view = View.getInstance();
		rollDice();
	}

	private void rollDice() {
		boolean validChoice = false;
		Integer roll = random.nextInt(6) + 1;
		this.view.display("\nIt is " + this.player.getName() + "'s turn!");
		this.view.display("\nThe dice rolled a " + roll);
		if (roll == 6) {
			this.view.display("\nGhost Captain is on Island " + board.getGhostIsland().getName());
			this.view.display("\nWhere would you like to move it to? (Input a letter from A - M) ");
			while (!validChoice) {
				char moveTo = this.inputScanner.next().charAt(0);
				inputScanner.nextLine();
				if (moveTo < 65 || moveTo > 77) {
					this.view.display("That is not a valid location. Choose a letter between A and M.");
				} else {
					this.board.moveGhostCaptain(String.valueOf(moveTo));
					validChoice = true;
				}
			}
			return;
		}
		produceResources(roll);
	}

	private void produceResources(Integer roll) {
		for (Islands island : islandList) {
			if (island.getDiceNumber() == roll && !island.hasGhostCaptain()) {
				for (Player player : playerList.getList()) {
					List<String> playerLairAssets = player.getLairAssets();
					List<String> islandLairs = island.getAttachedLairs();
					if (!Collections.disjoint(playerLairAssets, islandLairs)) {
						Integer numOfAttachedLairs = playerLairAssets.stream().distinct().filter(islandLairs::contains)
								.collect(Collectors.toList()).size();
						String resource = island.getIslandResource();
						if (resource.contains(" ")) {
							break;
						} else {
							this.view.display("Giving " + resource + " to " + player.getName());
							player.giveResource(resource, numOfAttachedLairs);
							this.stockpile.updateStockPile(resource, -numOfAttachedLairs);
						}
					}
				}
			} else if (island.getDiceNumber() == roll && island.hasGhostCaptain()) {
				this.view.display("Island " + island.getName() + " cannot produce!");
			}
		}
	}

	public void startTurn() {
		boolean turnOver = false;
		while (!turnOver) {
			ArrayList<String> options = new ArrayList<String>();
			options.add("Build");
			options.add("Trade");
			options.add("View Resources");
			options.add("View Marketplace");
			options.add("View Board");
			options.add("End Turn");
			displayOptions(options);
			switch (inputScanner.nextLine()) {
			case "1":
				build();
				break;
			case "2":
				trade();
				break;
			case "3":
				viewResources();
				break;
			case "4":
				viewMarketplace();
				break;
			case "5":
				viewBoard();
				break;
			case "6":
				turnOver = true;
				break;
			}
		}
	}

	private void build() {
		boolean finishedBuilding = false;
		ArrayList<String> buildOptionsList = new ArrayList<String>();
		buildOptionsList.add("Lair");
		buildOptionsList.add("Ship");
		buildOptionsList.add("View Resources");
		buildOptionsList.add("View My Assets");
		buildOptionsList.add("Go Back");
		while (!finishedBuilding) {
			this.view.display("\nWhat would you like to build?");
			displayOptions(buildOptionsList);
			switch (inputScanner.nextLine()) {
			case "1":
				this.buildLair();
				break;
			case "2":
				this.buildShip();
				break;
			case "3":
				viewResources();
				break;
			case "4":
				this.view.display(player.viewAssets());
				break;
			case "5":
				finishedBuilding = true;
				break;
			}
		}
	}

	private void buildLair() {
		ArrayList<String> validLairSites = new ArrayList<String>(this.buildOptions.validLairSites());
		if (validLairSites.size() == 0) {
			this.view.display("You currently have no valid lair sites to build on!\n");
			return;
		}
		if (this.player.skipResourcesCheckStatus() == false) { // Meaning we should not skip checking their resources...
			if (!this.buildOptions.checkResources("Lair")) { // They don't have enough resources...
				this.view.display("You do not have enough resources");
				return;
			}
		}
		this.view.display("Where would you like to build a lair? Your options are: ");
		ArrayList<String> options = new ArrayList<String>(validLairSites);
		if (this.player.skipResourcesCheckStatus() == false) {
			options.add("Cancel Build");
		}
		this.displayOptions(options);
		boolean validInput = false;
		while (!validInput) {
			this.view.display("\nEnter here: ");
			if (inputScanner.hasNextInt()) {
				int option = inputScanner.nextInt();
				inputScanner.nextLine();
				if ((option >= 1) && (option <= validLairSites.size())) {
					this.view.display("You have chosen option " + option);
					this.view.display("\nBuilding... ");
					this.buildOptions.buildLair(validLairSites.get(option - 1));
					validInput = true;
				} else if (option == options.size()) {
					if (this.player.skipResourcesCheckStatus() == true) {
						this.view.display("You have input " + option
								+ " which is outside the range of options. Please re-enter.");
					} else {
						this.view.display("Canceling build ...");
						validInput = true;
					}
				} else {
					this.view.display(
							"You have input " + option + " which is outside the range of options. Please re-enter.");
				}
			} else {
				this.view.display("Please input an integer for the option number.");
				inputScanner.next();
			}
		}
	}

	private void buildShip() {
		ArrayList<String> validShipSites = new ArrayList<String>(this.buildOptions.validShipSites());
		if (validShipSites.size() == 0) {
			this.view.display("You currently have no valid ship sites to build on!\n");
			return;
		}
		if (this.player.skipResourcesCheckStatus() == false) { // Meaning we should not skip checking their resources...
			if (!this.buildOptions.checkResources("Ship")) { // They don't have enough resources...
				this.view.display("You do not have enough resources");
				return;
			}
		}
		this.view.display("Where would you like to build a ship? Your options are: ");
		ArrayList<String> options = new ArrayList<String>(validShipSites);
		// Next, we need to check if the player is currently using a build-type coco
		// tile... if they are
		// then we shouldn't give the option to cancel a build (as the rules state that
		// they must build a lair or ship immediately)
		if (this.player.skipResourcesCheckStatus() == false) {
			options.add("Cancel Build");
		}
		this.displayOptions(options);
		boolean validInput = false;
		while (!validInput) {
			this.view.display("\nEnter here: ");
			if (inputScanner.hasNextInt()) {
				Integer option = inputScanner.nextInt();
				inputScanner.nextLine();
				if ((option >= 1) && (option <= validShipSites.size())) {
					this.view.display("You have chosen option " + option);
					this.view.display("Building... ");
					this.buildOptions.buildShip(validShipSites.get(option - 1));
					validInput = true;
				} else if (option == options.size()) {
					if (this.player.skipResourcesCheckStatus() == true) {
						this.view.display("You have input " + option
								+ " which is outside the range of options. Please re-enter.");
						continue;
					}
					this.view.display("Canceling build ...");
					validInput = true;
				} else {
					this.view.display(
							"You have input " + option + " which is outside the range of options. Please re-enter.");
				}
			} else {
				this.view.display("Please input an integer for the option number.");
				inputScanner.next();
			}
		}
	}

	public Integer pickCocoTile() {
		Map<Integer, String> cocoTypes = new HashMap<Integer, String>();
		cocoTypes.put(1, "Ghost Captain");
		cocoTypes.put(2, "Build");
		cocoTypes.put(3, "Resource Combination 1");
		cocoTypes.put(4, "Resource Combination 2");
		List<Integer> validTypes = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4));
		for (int i = 1; i < 5; i++) {
			String cocoType = cocoTypes.get(i);
			if (this.board.getCocoTiles().get(cocoType) == 0) {
				validTypes.remove(Integer.valueOf(i));
			}
		}
		Integer randi = random.nextInt(validTypes.size());
		return validTypes.get(randi);
	}

	public void buyCocoTile() {
		if (!this.buildOptions.checkResources("CocoTile") && this.board.getNumOfCocoTiles() >= 1) {
			Integer cocoTileNum = this.pickCocoTile(); // A number corresponding to the type of Coco tile a player pulls
			this.player.takeResource("Cutlass", 1);
			this.player.takeResource("Molasses", 1);
			this.player.takeResource("Gold", 1);
			this.stockpile.updateStockPile("Cutlass", 1);
			this.stockpile.updateStockPile("Molasses", 1);
			this.stockpile.updateStockPile("Gold", 1);
			switch (cocoTileNum) {
			case 1:
				this.player.addCocoTile("Ghost Captain");
				this.player.getResources().put("Coco tiles", this.player.getNumOfCocoTiles());
				this.board.removeCocoTile("Ghost Captain");
				boolean validChoice = false;
				this.view.display("You picked a ghost captain coco tile!");
				this.view.display("\nGhost Captain is on Island " + board.getGhostIsland().getName());
				this.view.display("\nWhere would you like to move it to? (Input a letter from A - M) ");
				while (!validChoice) {
					char moveTo = this.inputScanner.next().charAt(0);
					inputScanner.nextLine();
					if (moveTo < 65 || moveTo > 77) {
						this.view.display("That is not a valid location. Choose a letter between A and M.");
					} else {
						this.board.moveGhostCaptain(String.valueOf(moveTo));
						validChoice = true;
					}
				}
				break;
			case 2:
				this.player.addCocoTile("Build");
				this.player.getResources().put("Coco tiles", this.player.getNumOfCocoTiles());
				this.board.removeCocoTile("Build");
				if ((this.buildOptions.validShipSites().size() != 0)
						&& (this.buildOptions.validShipSites().size() != 0)) {
					this.view.display("You can build a lair or a ship!");
					boolean validInput = false;
					while (!validInput) {
						this.view.display("Which would you like to build?");
						this.view.display("\n1: Ship\n2: Lair");
						if (inputScanner.hasNextInt()) {
							Integer option = inputScanner.nextInt();
							inputScanner.nextLine();
							if (option == 1) {
								this.player.setResourcesCheckStatus(true);
								this.buildShip();
								this.player.setResourcesCheckStatus(false);
								validInput = true;
							} else if (option == 2) {
								this.player.setResourcesCheckStatus(true);
								this.buildLair();
								this.player.setResourcesCheckStatus(false);
								validInput = true;
							} else {
								this.view.display("You have input " + option
										+ " which is outside the range of options. Please re-enter.");
							}
						} else {
							this.view.display("Please input an integer for the option number.");
							inputScanner.next();
						}
					}
				} else if (this.buildOptions.validLairSites().size() != 0) {
					this.view.display("You can buld a lair! (You currently have no valid ship sites.)");
					this.player.setResourcesCheckStatus(true);
					this.buildLair();
					this.player.setResourcesCheckStatus(false);
				} else {
					this.view.display("You can buld a ship! (You currently have no valid lair sites.)");
					this.player.setResourcesCheckStatus(true);
					this.buildShip();
					this.player.setResourcesCheckStatus(false);
				}
				break;
			case 3:
				this.player.addCocoTile("Resource Combination 1");
				this.player.getResources().put("Coco tiles", this.player.getNumOfCocoTiles());
				this.board.removeCocoTile("Resource Combination 1");
				this.view.display("You get 2 Molasses and 2 Woods!\n");
				this.player.giveResource("Molasses", 2);
				this.player.giveResource("Wood", 2);
				this.stockpile.updateStockPile("Molasses", -2);
				this.stockpile.updateStockPile("Wood", -2);
				break;
			case 4:
				this.player.addCocoTile("Resource Combination 2");
				this.player.getResources().put("Coco tiles", this.player.getNumOfCocoTiles());
				this.board.removeCocoTile("Resource Combination 2");
				this.view.display("You get 2 Goats and 2 Cutlass!\n");
				this.player.giveResource("Goats", 2);
				this.player.giveResource("Cutlass", 2);
				this.stockpile.updateStockPile("Goats", -2);
				this.stockpile.updateStockPile("Cutlass", -2);
				break;
			default:
				this.view.display("Invalid number received for coco tile type.");
				break;
			}
		} else {
			if (this.buildOptions.checkResources("CocoTile")) {
				this.view.display("You do not have enough resources for a coco tile unfortunately!");
			} else {
				this.view.display("Unfortunately there are no coco tiles left!");
			}
		}
	}
//	public void buyCocoTile() {
//		boolean purchaseDone = false;
//		this.view.display("How many Coco tiles would you like to buy? Enter 'Q' or 'q' to go back.");
//		while (!purchaseDone) {
//			this.view.display("\nEnter here: ");
//			if (inputScanner.hasNextInt()) {
//				Integer number = inputScanner.nextInt();
//				inputScanner.nextLine();
//				if (number == 0) {
//					this.view.display("You entered 0, please enter a positive integer.");
//					continue;
//				} else if ((number == 1) && (this.buildOptions.checkResources("CocoTile"))
//						&& (this.board.getNumOfCocoTiles() >= number)) {
//					this.view.display("Giving you a coco tile now!");
//				}
//			} else if (inputScanner.nextLine() == "Q" || inputScanner.nextLine() == "q") {
//				purchaseDone = true;
//				continue;
//			} else {
//				this.view.display("Please input an integer for the option number.");
//				inputScanner.next();
//			}
//		}
//	}

	private void trade() {
		boolean finishedTrading = false;
		ArrayList<String> tradeOptionsList = new ArrayList<String>();
		tradeOptionsList.add("Marketplace");
		tradeOptionsList.add("Stockpile");
		tradeOptionsList.add("Buy Coco tile");
		tradeOptionsList.add("View Resources");
		tradeOptionsList.add("View Marketplace");
		tradeOptionsList.add("View Stockpile");
		tradeOptionsList.add("Go Back");
		Marketplace marketplace = board.getMarketplace();
		while (!finishedTrading) {
			this.view.display("\nWhat would you like to trade with?");
			displayOptions(tradeOptionsList);
			switch (inputScanner.nextLine()) {
			case "1":
				if (tradeOptions.canTradeWithMarketplace()) {
					tradeWithMarketplace();
				} else {
					this.view.display("You have already traded with the Marketplace");
				}
				;
				break;
			case "2":
				tradeWithStockpile();
				break;
			case "3":
				buyCocoTile();
				break;
			case "4":
				viewResources();
				break;
			case "5":
				this.view.display(marketplace.toString());
				break;
			case "6":
				this.view.display(this.stockpile.toString());
				break;
			case "7":
				finishedTrading = true;
				break;
			}
		}
	}

	public void tradeWithMarketplace() {
		ArrayList<String> giveResource = new ArrayList<String>();
		this.view.display("What would you like?");
		ArrayList<String> marketplaceOptions = new ArrayList<String>();
		marketplaceOptions = this.marketplace.getMarketPlace();
		displayOptions(marketplaceOptions);
		switch (inputScanner.nextLine()) {
		case "1":
			giveResource = getGivenResources();
			this.view.display(tradeOptions.tradeMarketplace(marketplaceOptions.get(0), giveResource.get(0)));
			break;
		case "2":
			giveResource = getGivenResources();
			this.view.display(tradeOptions.tradeMarketplace(marketplaceOptions.get(1), giveResource.get(0)));
			break;
		case "3":
			giveResource = getGivenResources();
			this.view.display(tradeOptions.tradeMarketplace(marketplaceOptions.get(2), giveResource.get(0)));
			break;
		case "4":
			giveResource = getGivenResources();
			this.view.display(tradeOptions.tradeMarketplace(marketplaceOptions.get(3), giveResource.get(0)));
			break;
		case "5":
			giveResource = getGivenResources();
			this.view.display(tradeOptions.tradeMarketplace(marketplaceOptions.get(4), giveResource.get(0)));
			break;
		}
	}

	private ArrayList<String> getGivenResources() {
		ArrayList<String> giveResource = new ArrayList<String>();
		this.view.display("What will you give?");
		ArrayList<String> giveOptions = new ArrayList<String>();
		for (Map.Entry resource : this.player.getResources().entrySet()) {
			giveOptions.add((String) resource.getKey() + " (You have " + resource.getValue() + ")");
		}
		displayOptions(giveOptions);
		switch (inputScanner.nextLine()) {
		case "1":
			giveResource.add("Gold");
			break;
		case "2":
			giveResource.add("Molasses");
			break;
		case "3":
			giveResource.add("Wood");
			break;
		case "4":
			giveResource.add("Goats");
			break;
		case "5":
			giveResource.add("Cutlass");
			break;
		}
		return giveResource;
	}

	public void tradeWithStockpile() {
		int num;
		ArrayList<String> tradeInfo;
		this.view.display("What resource would you like?");
		ArrayList<String> stockpileOptions = new ArrayList<String>();
		stockpileOptions.add("Gold (There are " + this.stockpile.getNumOfResource("Gold") + " in the stockpile)");
		stockpileOptions
				.add("Mollasses (There are " + this.stockpile.getNumOfResource("Molasses") + " in the stockpile)");
		;
		stockpileOptions.add("Wood (There are " + this.stockpile.getNumOfResource("Wood") + " in the stockpile)");
		stockpileOptions.add("Goats (There are " + this.stockpile.getNumOfResource("Goats") + " in the stockpile)");
		stockpileOptions.add("Cutlass (There are " + this.stockpile.getNumOfResource("Cutlass") + " in the stockpile)");
		displayOptions(stockpileOptions);
		switch (inputScanner.nextLine()) {
		case "1":
			num = getNumberFromPlayer("Gold");
			tradeInfo = new ArrayList<String>(getTradeInfo("Gold", num));
			tradeOptions.tradeStockpile("Gold", num, tradeInfo);
			break;
		case "2":
			num = getNumberFromPlayer("Mollasses");
			tradeInfo = new ArrayList<String>(getTradeInfo("Mollasses", num));
			tradeOptions.tradeStockpile("Mollasses", num, tradeInfo);
			break;
		case "3":
			num = getNumberFromPlayer("Wood");
			tradeInfo = new ArrayList<String>(getTradeInfo("Wood", num));
			tradeOptions.tradeStockpile("Wood", num, tradeInfo);
			break;
		case "4":
			num = getNumberFromPlayer("Goats");
			tradeInfo = new ArrayList<String>(getTradeInfo("Goats", num));
			tradeOptions.tradeStockpile("Goats", num, tradeInfo);
			break;
		case "5":
			num = getNumberFromPlayer("Cutlass");
			tradeInfo = new ArrayList<String>(getTradeInfo("Cutlass", num));
			tradeOptions.tradeStockpile("Cutlass", num, tradeInfo);
			break;
		}

	}

	public int getNumberFromPlayer(String requestedResource) {
		boolean enough = false;
		int num = 0;
		while (!enough) {
			this.view.display("How many would you like?");
			num = Integer.parseInt(inputScanner.nextLine());
			if (this.stockpile.isAvailable(requestedResource, num)) {
				enough = true;
			} else {
				this.view.display("There is not enough " + requestedResource + " in the stockpile");
			}
		}
		return num;
	}

	public ArrayList<String> getTradeInfo(String requestedResource, int num) {
		ArrayList<String> tradeInfo = new ArrayList<String>();
		int i = 1;
		if (this.stockpile.isAvailable(requestedResource, num)) {
			boolean givingMultiple = false;
			if (num > 1) {
				this.view.display("Are you giving multiple resources?");
				String answer = inputScanner.nextLine();
				if (answer.toUpperCase().contains("Y")) {
					givingMultiple = true;
				}
			}
			while (givingMultiple && i <= num) {
				tradeInfo.addAll(getGivenResources());
				i++;
			}
			if (!givingMultiple) {
				tradeInfo.addAll(getGivenResources());
			}
		}
		return tradeInfo;
	}

	public boolean didPlayerWin() {
		if (this.player.getLairAssets().size() == 7) {
			return true;
		} else {
			return false;
		}
	}

	private void displayOptions(ArrayList<String> options) {
		int i = 1;
		this.view.display("\nYour options are: ");
		for (String option : options) {
			this.view.display("\n\t" + i + " : " + option);
			i++;
		}
	}

	private void viewMarketplace() {
		this.view.display("The Marketplace contains the following resources: ");
		this.view.display(this.marketplace.toString());
	}

	private void viewStockpile() {
		this.view.display(this.stockpile.toString());
	}

	private void viewResources() {
		this.view.display(this.player.getResources().toString());
	}

	private void viewBoard() {
		this.view.display("The board currently looks like this: ");
		for (Player player : this.playerList.getList()) {
			this.view.display(player.toString());
		}
		this.view.display("\nThe Ghost captain is on island: \t" + this.board.getGhostIsland().getName());
	}

}
